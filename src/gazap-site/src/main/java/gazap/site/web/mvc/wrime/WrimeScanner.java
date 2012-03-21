package gazap.site.web.mvc.wrime;

import java.io.*;
import java.util.regex.Pattern;

public class WrimeScanner {
    public static interface Receiver {
        void startResource(ScriptResource resource) throws WrimeException;

        void finishResource() throws WrimeException;

        void text(String text);

        void exprStart();

        void exprFinish();

        void exprListOpen();

        void exprListClose();

        void exprName(String name);

        void exprLiteral(String literal);

        void exprComma();

        void exprColon();

        void exprDot();
    }

    public void parse(ScriptResource resource, Receiver receiver) throws WrimeException {
        receiver.startResource(resource);

        InputStream in = null;
        try {
            in = resource.getInputStream();
            parse(receiver, new InputStreamReader(in, "utf-8"));
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

    public void parse(Receiver receiver, Reader reader) throws WrimeException {
        ScannerWrapper scanner = new ScannerWrapper(reader);
        Token token = new Token();
        while (scanner.next(token)) {
            switch (token.type) {
                case EOF:
                    receiver.finishResource();
                    return;
                case TEXT:
                    receiver.text(token.value);
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
                case EXPR_COMMA:
                    receiver.exprComma();
                    break;
                case EXPR_DOT:
                    receiver.exprDot();
                    break;
                case EXPR_COLON:
                    receiver.exprColon();
                    break;
                default:
                    throw new IllegalStateException("this situation is not supported");
            }
        }
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
                case EXPR_QUOTE:
                case EXPR_DQUOTE:
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
                        return Expectation.EXPR_QUOTE;
                    } else if ("\"".equals(token.value)) {
                        token.type = TokenType.INCOMPLETE;
                        return Expectation.EXPR_DQUOTE;
                    } else if ("}".equals(token.value)) {
                        token.type = TokenType.EXPR_END;
                        return Expectation.TOKEN_MARK;
                    }

                    if ("(".equals(token.value)) {
                        token.type = TokenType.EXPR_LIST_OPEN;
                    } else if (")".equals(token.value)) {
                        token.type = TokenType.EXPR_LIST_CLOSE;
                    } else if (",".equals(token.value)) {
                        token.type = TokenType.EXPR_COMMA;
                    } else if (".".equals(token.value)) {
                        token.type = TokenType.EXPR_DOT;
                    } else if (":".equals(token.value)) {
                        token.type = TokenType.EXPR_COLON;
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
        EXPR_DELIMITER(" |,|:|\\(|\\)|'|\\\"|\\.|}"),
        EXPR_QUOTE("(?<=\\')"),
        EXPR_DQUOTE("(?<=\\\")");

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
        EXPR_COMMA,
        EXPR_COLON,
        EXPR_DOT
    }

    private static class Token {
        private TokenType type;
        private String value;

        public Token() {
            clear();
        }

        public Token(TokenType type, String value) {
            this.type = type;
            this.value = value;
        }

        public void copyTo(Token token) {
            token.value = value;
            token.type = type;
        }

        public void clear() {
            type = TokenType.INCOMPLETE;
            value = "";
        }
    }
}
