package liquibase.ext.mysql.sqlgenerator;

import liquibase.statement.core.CreateTableStatement;

public class MysqlCreateTableStatement extends CreateTableStatement {
    private String engine;
    private String collation;

    public MysqlCreateTableStatement(CreateTableStatement other) {
        super(other.getCatalogName(), other.getSchemaName(), other.getTableName());

        setTablespace(other.getTablespace());
        getColumns().addAll(other.getColumns());
        getAutoIncrementConstraints().addAll(other.getAutoIncrementConstraints());
        getColumnTypes().putAll(other.getColumnTypes());

        getNotNullColumns().addAll(other.getNotNullColumns());
        getForeignKeyConstraints().addAll(other.getForeignKeyConstraints());
        getUniqueConstraints().addAll(other.getUniqueConstraints());
    }

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
}
