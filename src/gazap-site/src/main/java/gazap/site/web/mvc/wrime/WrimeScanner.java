package gazap.site.web.mvc.wrime;

import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;
import java.util.regex.Pattern;

public class WrimeScanner {
    public static interface Receiver {
        void startResource(Resource resource);

        void finishResource();

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

    public void parse(Resource resource, Receiver receiver) throws IOException {
        receiver.startResource(resource);

        ScannerWrapper scanner = new ScannerWrapper(resource.getInputStream());
        Token token = new Token();
        while (scanner.next(token)) {
            switch (token.type) {
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
                case EXPR_COLON:
                    receiver.exprColon();
                    break;
                default:
                    throw new IllegalStateException("this situation is not supported");
            }
        }

        receiver.finishResource();
    }

    private static class ScannerWrapper {
        private Scanner scanner;
        private ScannerExpectation expectation;

        private Token nextToken;

        private ScannerWrapper(InputStream in) {
            scanner = new Scanner(in, "utf-8");
            expect(ScannerExpectation.TOKEN_MARK);
        }

        private void expect(ScannerExpectation expectation) {
            this.expectation = expectation;
            this.scanner.useDelimiter(expectation.pattern());
        }

        public boolean next(Token token) {
            if (nextToken != null) {
                nextToken.copyTo(token);
                setNextToken(null, null);
                return true;
            }

            token.clear();
            while (token.type == TokenType.INCOMPLETE) {
                expect(consume(token));
            }
            return token.type != TokenType.INCOMPLETE;
        }

        private ScannerExpectation consume(Token token) {
            token.value += scanner.next();

            String delimiter;
            switch (expectation) {
                case TOKEN_MARK:
                    token.type = TokenType.TEXT;

                    delimiter = scanner.findWithinHorizon(expectation.pattern(), 0);
                    if ("${".equals(delimiter)) {
                        setNextToken(TokenType.EXPR_START, delimiter);
                        return ScannerExpectation.EXPR_DELIMITER;
                    } else {
                        return ScannerExpectation.TOKEN_MARK;
                    }
                case EXPR_QUOTE:
                case EXPR_DQUOTE:
                    token.type = TokenType.EXPR_LITERAL;

                    scanner.skip(expectation.pattern());
                    return ScannerExpectation.EXPR_DELIMITER;
                case EXPR_DELIMITER:
                    token.type = TokenType.EXPR_NAME;

                    delimiter = scanner.findWithinHorizon(expectation.pattern(), 0);
                    if ("'".equals(delimiter)) {
                        return ScannerExpectation.EXPR_QUOTE;
                    }
                    if ("\"".equals(delimiter)) {
                        return ScannerExpectation.EXPR_DQUOTE;
                    }
                    if ("}".equals(delimiter)) {
                        setNextToken(TokenType.EXPR_END, delimiter);
                        return ScannerExpectation.TOKEN_MARK;
                    }
                    if ("(".equals(delimiter)) {
                        setNextToken(TokenType.EXPR_LIST_OPEN, delimiter);
                    } else if (")".equals(delimiter)) {
                        setNextToken(TokenType.EXPR_LIST_CLOSE, delimiter);
                    } else if (",".equals(delimiter)) {
                        setNextToken(TokenType.EXPR_COMMA, delimiter);
                    } else if (":".equals(delimiter)) {
                        setNextToken(TokenType.EXPR_COLON, delimiter);
                    }
                    return ScannerExpectation.EXPR_DELIMITER;

                default:
                    throw new IllegalStateException("this situation is not supported");
            }
        }

        private void setNextToken(TokenType type, String value) {
            nextToken = type == null ? null : new Token(type, value);
        }
    }

    private static enum ScannerExpectation {
        TOKEN_MARK("(?<!\\$)\\$\\{", 2),
        EXPR_DELIMITER(" |,|:|\\(|\\)|'|\\\"|}", 1),
        EXPR_QUOTE("(?<=\\')", 1),
        EXPR_DQUOTE("(?<=\\\")", 1);

        private final Pattern pattern;
        private final int limit;

        public Pattern pattern() {
            return pattern;
        }

        public int limit() {
            return limit;
        }

        private ScannerExpectation(String pattern, int limit) {
            this.pattern = Pattern.compile(pattern);
            this.limit = limit;
        }
    }

    private static enum TokenType {
        INCOMPLETE,

        TEXT,
        EXPR_START,
        EXPR_END,
        EXPR_NAME,
        EXPR_LIST_OPEN,
        EXPR_LIST_CLOSE,
        EXPR_LITERAL,
        EXPR_COMMA,
        EXPR_COLON
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
