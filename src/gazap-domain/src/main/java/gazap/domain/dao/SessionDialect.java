package gazap.domain.dao;

import org.hibernate.dialect.MySQL5Dialect;
import org.hibernate.exception.ViolatedConstraintNameExtracter;

import java.sql.SQLException;

public class SessionDialect extends MySQL5Dialect {
    @Override
    public ViolatedConstraintNameExtracter getViolatedConstraintNameExtracter() {
        return CONSTRAINT_NAME_EXTRACT;
    }

    private static final ViolatedConstraintNameExtracter CONSTRAINT_NAME_EXTRACT = new ViolatedConstraintNameExtracter() {
        @Override
        public String extractConstraintName(SQLException e) {
            if (e.getErrorCode() != 1062) {
                return null;
            }
            String message = e.getMessage();

            final String marker = " key ";
            int idx = message.lastIndexOf(marker);
            if (idx == -1) {
                return null;
            }

            String constraintName = message.substring(idx + marker.length());
            int nameStart = constraintName.startsWith("'") ? 1 : 0;
            int nameEnd = constraintName.length();

            if (constraintName.endsWith("'")) {
                nameEnd -= 1;
            }
            return constraintName.substring(nameStart, nameEnd);
        }
    };
}
