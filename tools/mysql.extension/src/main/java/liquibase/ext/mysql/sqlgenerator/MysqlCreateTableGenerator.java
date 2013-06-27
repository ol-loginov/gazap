package liquibase.ext.mysql.sqlgenerator;

import liquibase.database.Database;
import liquibase.sql.Sql;
import liquibase.sqlgenerator.SqlGeneratorChain;
import liquibase.sqlgenerator.core.CreateTableGenerator;
import liquibase.statement.core.CreateTableStatement;

public class MysqlCreateTableGenerator extends CreateTableGenerator {
    @Override
    public Sql[] generateSql(CreateTableStatement statement, Database database, SqlGeneratorChain sqlGeneratorChain) {
        Sql[] sql = super.generateSql(statement, database, sqlGeneratorChain);
        if (statement instanceof MysqlCreateTableStatement) {
        }
        return sql;
    }
}
