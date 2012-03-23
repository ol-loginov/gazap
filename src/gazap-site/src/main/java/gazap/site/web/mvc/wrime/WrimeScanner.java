package gazap.site.web.mvc.wrime;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WrimeScanner {
    public static interface Receiver {
        void startResource(ScriptResource resource) throws WrimeException;

        void finishResource() throws WrimeException;

        void text(String text) throws WrimeException;

        void exprStart() throws WrimeException;

        void exprFinish() throws WrimeException;

        void exprListOpen() throws WrimeException;

        void exprListClose() throws WrimeException;

        void exprName(String name) throws WrimeException;

        void exprLiteral(String literal) throws WrimeException;

        void exprDelimiter(String value) throws WrimeException;
    }

    private static final Pattern EAT_SPACE = Pattern.compile("^\\s*(.*)\\s*$");

    private Map<WrimeEngine.Scanner, String> options = new HashMap<WrimeEngine.Scanner, String>();

    public void configure(Map<WrimeEngine.Scanner, String> options) {
        this.options.clear();
        this.options.putAll(options);
    }

    public void parse(ScriptResource resource, Receiver receiver) throws WrimeException {
        receiver.startResource(resource);

        InputStream in = null;
        try {
            in = resource.getInputStream();
            parse(receiver, new InputStreamReader(in, "utf-8"), resource.getPath());
        } catch (UnsupportedEncodingException e) {
            throw new WrimeException("UTF-8 is N/A", e);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    // silent one
                }
            }
        }
    }

    public void parse(Receiver receiver, Reader reader, String path) throws WrimeException {
        ScannerWrapper scanner = new ScannerWrapper(reader);
        Token token = new Token();
        while (scanner.next(token)) {
            try {
                accept(receiver, token);
                if (token.type == TokenType.EOF) {
                    return;
                }
            } catch (WrimeException e) {
                throw new WrimeException(e.getMessage(), e, path, token.line + 1, token.column + 1);
            }
        }
    }

    private void accept(Receiver receiver, Token token) throws WrimeException {
        switch (token.type) {
            case EOF:
                receiver.finishResource();
                return;
            case TEXT:
                receiver.text(eatSpace(token.value));
                break;
            case EXPR_START:
                receiver.exprStart();
                break;
            case EXPR_END:
                receiver.exprFinish();
                break;
            case EXPR_LIST_OPEN:
                receiver.exprListOpen();
                break;
            case EXPR_LIST_CLOSE:
                receiver.exprListClose();
                break;
            case EXPR_NAME:
                receiver.exprName(token.value);
                break;
            case EXPR_LITERAL:
                receiver.exprLiteral(token.value);
                break;
            case EXPR_DELIMITER:
                receiver.exprDelimiter(token.value);
                break;
            default:
                throw new IllegalStateException("this situation is not supported");
        }
    }

    private String eatSpace(String value) {
        if (!options.containsKey(WrimeEngine.Scanner.EAT_SPACE)) {
            return value;
        }
        Matcher matcher = EAT_SPACE.matcher(value);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return value;
    }

    private static class ScannerWrapper {
        private ScriptScanner scanner;
        private Expectation expectation;

        private ScannerWrapper(Reader reader) {
            scanner = new ScriptScanner(reader);
            expect(Expectation.TOKEN_MARK);
        }

        private void expect(Expectation expectation) {
            this.expectation = expectation;
        }

        public boolean next(Token token) throws WrimeException {
            token.clear();
            while (token.type == TokenType.INCOMPLETE) {
                expect(consume(token));
            }
            return token.type != TokenType.INCOMPLETE;
        }

        private Expectation consume(Token token) throws WrimeException {
            token.line = scanner.line();
            token.column = scanner.column();
            token.value = scanner.waitDelimiter(expectation.pattern());
            if (token.value == null) {
                token.type = TokenType.EOF;
                return Expectation.TOKEN_MARK;
            }

            switch (expectation) {
                case TOKEN_MARK:
                    if (scanner.lookingAt()) {
                        token.type = TokenType.EXPR_START;
                        return Expectation.EXPR_DELIMITER;
                    } else {
                        token.type = TokenType.TEXT;
                        return Expectation.TOKEN_MARK;
                    }
                case EXPR_QUOTE_BOUND:
                    token.type = TokenType.EXPR_LITERAL;
                    if (scanner.lookingAt()) {
                        token.value = "";
                    } else {
                        scanner.skip(expectation.pattern());
                    }
                    return Expectation.EXPR_DELIMITER;
                case EXPR_DQUOTE_BOUND:
                    token.type = TokenType.EXPR_LITERAL;
                    if (scanner.lookingAt()) {
                        token.value = "";
                    } else {
                        scanner.skip(expectation.pattern());
                    }
                    return Expectation.EXPR_DELIMITER;
                case EXPR_DELIMITER:
                    if ("'".equals(token.value)) {
                        token.type = TokenType.INCOMPLETE;
                        return Expectation.EXPR_QUOTE_BOUND;
                    } else if ("\"".equals(token.value)) {
                        token.type = TokenType.INCOMPLETE;
                        return Expectation.EXPR_DQUOTE_BOUND;
                    } else if ("}".equals(token.value)) {
                        token.type = TokenType.EXPR_END;
                        return Expectation.TOKEN_MARK;
                    }

                    if ("(".equals(token.value)) {
                        token.type = TokenType.EXPR_LIST_OPEN;
                    } else if (")".equals(token.value)) {
                        token.type = TokenType.EXPR_LIST_CLOSE;
                    } else if (",".equals(token.value)) {
                        token.type = TokenType.EXPR_DELIMITER;
                    } else if (".".equals(token.value)) {
                        token.type = TokenType.EXPR_DELIMITER;
                    } else if ("*".equals(token.value)) {
                        token.type = TokenType.EXPR_DELIMITER;
                    } else if (":".equals(token.value)) {
                        token.type = TokenType.EXPR_DELIMITER;
                    } else if (" ".equals(token.value)) {
                        token.type = TokenType.INCOMPLETE;
                    } else {
                        token.type = TokenType.EXPR_NAME;
                    }
                    return Expectation.EXPR_DELIMITER;

                default:
                    throw new IllegalStateException("this situation is not supported");
            }
        }
    }

    private static enum Expectation {
        TOKEN_MARK("(?<!\\$)\\$\\{"),
        EXPR_DELIMITER("\\*| |,|:|\\(|\\)|'|\\\"|\\.|}"),
        EXPR_QUOTE_BOUND("\\\'"),
        EXPR_DQUOTE_BOUND("\\\"");

        private final Pattern pattern;

        public Pattern pattern() {
            return pattern;
        }

        private Expectation(String pattern) {
            this.pattern = Pattern.compile(pattern);
        }
    }

    private static enum TokenType {
        INCOMPLETE,
        EOF,

        TEXT,
        EXPR_START,
        EXPR_END,
        EXPR_NAME,
        EXPR_LIST_OPEN,
        EXPR_LIST_CLOSE,
        EXPR_LITERAL,
        EXPR_DELIMITER
    }

    private static class Token {
        private TokenType type;
        private String value;
        private String lastValue;

        private int line;
        private int column;

        public Token() {
            clear();
        }

        public void clear() {
            type = TokenType.INCOMPLETE;
            value = "";
            lastValue = "";
            line = 0;
            column = 0;
        }
    }
}
