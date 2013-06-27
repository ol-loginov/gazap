package liquibase.ext.mysql.sqlgenerator;

import liquibase.change.ChangeMetaData;
import liquibase.change.DatabaseChange;
import liquibase.change.core.CreateTableChange;
import liquibase.database.Database;
import liquibase.statement.SqlStatement;
import liquibase.statement.core.CreateTableStatement;

@DatabaseChange(name = "createTable", description = "Create Table", priority = ChangeMetaData.PRIORITY_DEFAULT)
public class MysqlCreateTableChange extends CreateTableChange {
    private String engine;
    private String collation;

    public String getEngine() {
        return engine;
    }

    public void setEngine(String engine) {
        this.engine = engine;
    }

    public String getCollation() {
        return collation;
    }

    public void setCollation(String collation) {
        this.collation = collation;
    }

    @Override
    public SqlStatement[] generateStatements(Database database) {
        SqlStatement[] statements = super.generateStatements(database);
        for (int i = 0; statements != null && i < statements.length; ++i) {
            if (statements[i] instanceof CreateTableStatement) {
                statements[i] = convertToMysql((CreateTableStatement) statements[i]);
            }
        }
        return statements;
    }

    private MysqlCreateTableStatement convertToMysql(CreateTableStatement statement) {
        MysqlCreateTableStatement clone = new MysqlCreateTableStatement(statement);
        clone.setCollation(getCollation());
        clone.setEngine(getEngine());
        return clone;
    }
}
