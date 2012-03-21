package gazap.site.web.mvc.wrime;

import org.springframework.core.io.Resource;

import java.io.InputStream;
import java.util.Scanner;
import java.util.regex.Pattern;

public class WrimeScanner {
    public static interface Receiver {
        void startResource(Resource resource) throws Exception;

        void finishResource() throws Exception;

        void text(String text) throws Exception;

        void startExpression();

        void completeExpression();
    }

    public void parse(Resource resource, Receiver receiver) throws Exception {
        receiver.startResource(resource);

        ScannerWrapper scanner = new ScannerWrapper(resource.getInputStream());
        Token token = new Token();
        while (scanner.next(token)) {
            switch (token.type) {
                case TEXT:
                    receiver.text(token.value);
                    break;
                case EXPR_START:
                    receiver.startExpression();
                    break;
                case EXPR_END:
                    receiver.completeExpression();
                    break;
            }
        }

        receiver.finishResource();
    }

    private static class ScannerWrapper {
        private Scanner scanner;
        private ScannerExpectation expectation;

        private ScannerWrapper(InputStream in) {
            scanner = new Scanner(in, "utf-8");
            expect(ScannerExpectation.TOKEN_MARK);
        }

        private void expect(ScannerExpectation expectation) {
            this.expectation = expectation;
            this.scanner.useDelimiter(expectation.pattern());
        }

        public boolean hasNext() {
            return scanner.hasNext();
        }

        public boolean next(Token token) {
            token.value = "";
            token.type = TokenType.INCOMPLETE;
            while (token.type == TokenType.INCOMPLETE && hasNext()) {
                expect(consume(token));
            }
            return token.type != TokenType.INCOMPLETE;
        }

        private ScannerExpectation consume(Token token) {
            switch (expectation) {
                case TOKEN_MARK:
                    token.value = scanner.next();
                    if ("${".equals(token.value)) {
                        token.type = TokenType.EXPR_START;
                        return ScannerExpectation.EXPR_DELIMITER;
                    } else {
                        token.type = TokenType.TEXT;
                        return ScannerExpectation.TOKEN_MARK;
                    }
                case EXPR_QUOTE:
                    token.type = TokenType.EXPR_LITERAL;
                    token.value = scanner.next();
                    scanner.skip(expectation.pattern());
                    return ScannerExpectation.EXPR_DELIMITER;
                case EXPR_DQUOTE:
                    token.type = TokenType.EXPR_LITERAL;
                    token.value = scanner.next();
                    scanner.skip(expectation.pattern());
                    return ScannerExpectation.EXPR_DELIMITER;
                case EXPR_DELIMITER:
                    token.value = scanner.next();
                    if ("}".equals(token.value)) {
                        token.type = TokenType.EXPR_END;
                        return ScannerExpectation.TOKEN_MARK;
                    }
                    if ("'".equals(token.value)) {
                        token.type = TokenType.EXPR_NAME;
                        return ScannerExpectation.EXPR_QUOTE;
                    }
                    if ("\"".equals(token.value)) {
                        token.type = TokenType.EXPR_NAME;
                        return ScannerExpectation.EXPR_DQUOTE;
                    }
                    if ("(".equals(token.value)) {
                        token.type = TokenType.EXPR_OPEN;
                        return ScannerExpectation.EXPR_DQUOTE;
                    }
                    if (")".equals(token.value)) {
                        token.type = TokenType.EXPR_CLOSE;
                        return ScannerExpectation.EXPR_DQUOTE;
                    }
                    if (",".equals(token.value)) {
                        token.type = TokenType.EXPR_CLOSE;
                        return ScannerExpectation.EXPR_DQUOTE;
                    }
                    return ScannerExpectation.EXPR_DELIMITER;

                default:
                    throw new IllegalStateException("this situation is not supported");
            }
        }
    }

    private static enum ScannerExpectation {
        TOKEN_MARK("(?<!\\$)\\$\\{"),
        EXPR_DELIMITER(" |,|:|\\(|\\)|'|\\\"|}"),
        EXPR_QUOTE("(?<=\\')"),
        EXPR_DQUOTE("(?<=\\\")");

        private final Pattern pattern;

        public Pattern pattern() {
            return pattern;
        }


        private ScannerExpectation(String pattern) {
            this.pattern = Pattern.compile(pattern);
        }
    }

    private static enum TokenType {
        INCOMPLETE,

        TEXT,
        EXPR_START,
        EXPR_END,
        EXPR_NAME,
        EXPR_OPEN,
        EXPR_CLOSE,
        EXPR_LITERAL,
        EXPR_COMMA,
        EXPR_COLON
    }

    private static class Token {
        private TokenType type;
        private String value;
    }
}
