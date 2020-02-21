package orm;

import annotations.Column;
import annotations.Entity;
import annotations.Id;
import annotations.Unique;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;
import java.util.stream.Collectors;

public class EntityManager<E> implements DbContext<E> {
    private static final String SELECT = "SELECT {0} FROM {1}";
    private static final String SELECT_WHERE = "SELECT {0} FROM {1} WHERE {2}";
    private static final String INSERT_INTO = "INSERT INTO {0} ({1}) VALUES({2})";
    private static final String DELETE = "DELETE FROM {0} WHERE {1}";

    private Connection connection;
    private Class<E> clazz;
    private String tableName;

    public EntityManager(Connection connection, Class<E> clazz) throws SQLException {
        this.connection = connection;
        this.clazz = clazz;
        this.setTableName();
        this.getTableData();

        if (this.isTableExist()){
            this.checkIfFieldsExistInDatabase();
        } else {
            this.createTable();
        }
    }

    private boolean isTableExist() throws SQLException {
        DatabaseMetaData dbm = this.connection.getMetaData();
        ResultSet resultSet = dbm.getTables(Connector.getSchema(), null, this.tableName, null);
        return resultSet.next();
    }

    // check and alter database table
    private void checkIfFieldsExistInDatabase() throws SQLException {

        // database columns
        Set<String> databaseColumns = this.getTableData();

        // class fields
        Field[] columns = Arrays.stream(this.clazz.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(Column.class))
                .toArray(Field[]::new);

        // check
        StringBuilder query = new StringBuilder("ALTER TABLE " + Connector.getSchema()+ "." + this.tableName);
        boolean alter = false;

        int countClass = columns.length;
        int countDatabase =  databaseColumns.size();

        // add new columns to query
        for (Field field : columns) {
            if (!databaseColumns.contains(field.getAnnotation(Column.class).name())){
                query
                    .append(alter ? "," : " ")
                    .append("ADD COLUMN ")
                    .append(this.fieldToSQLColumn(field));
                alter = true;
                countDatabase++;
            }
        }

        // add drop columns to query
        if (countClass != countDatabase){
            Set<String> namesClass = Arrays.stream(columns)
                    .map(field -> field.getAnnotation(Column.class).name())
                    .collect(Collectors.toSet());

            for (String column: databaseColumns) {
                if (!namesClass.contains(column)){
                    query
                        .append(alter ? ", " : " ")
                        .append("DROP ")
                        .append(column);
                    alter = true;
                }
            }
        }

        // alter table
        if (alter){
            try {
                Statement statement = connection.createStatement();
                statement.executeUpdate(query.toString());
                System.out.println("Altered table : " + this.tableName);

            } catch (SQLException e){
                throw new SQLException("Unable to alter table : " + this.tableName);
            }
        }
    }

    // get database table
    private Set<String> getTableData() throws SQLException {
        DatabaseMetaData dbm = this.connection.getMetaData();
        ResultSet resultSet = dbm.getColumns
                (Connector.getSchema(), null, this.tableName, null);
        Set<String> columns = new LinkedHashSet<>();

        while (resultSet.next()){
            String columnName = resultSet.getString("COLUMN_NAME");
            int dataType = resultSet.getInt("DATA_TYPE");
            int columnSize = resultSet.getInt("COLUMN_SIZE");
            String decimalDigits = resultSet.getString("DECIMAL_DIGITS");
            String isNullable = resultSet.getString("IS_NULLABLE");
            String isAutoincrement = resultSet.getString("IS_AUTOINCREMENT");

            switch (dataType){
                case Types.INTEGER :
                case Types.VARCHAR :
                case Types.TIMESTAMP :
                case Types.DATE :
                case Types.DOUBLE : break;
                default :
                    throw new UnsupportedOperationException("Unsupported sql type : " + dataType);
            }
            columns.add(columnName);
        }
        return columns;
    }

    // create database table
    private void createTable(){
        System.out.println("Create table: " + this.tableName);
        StringBuilder stringBuilder = new StringBuilder("CREATE TABLE ");
        stringBuilder
                .append(this.tableName)
                .append("(");

        Field[] fields = this.clazz.getDeclaredFields();
        int count = 0;

        for (Field field: fields) {

            String column = this.fieldToSQLColumn(field);
            if(!column.isBlank()){
                stringBuilder
                    .append(count++ > 0 ? "," : "")
                    .append(column);
            }
        }
        stringBuilder.append(")");

        try {
            Statement statement = this.connection.createStatement();
            statement.executeUpdate(stringBuilder.toString());

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private String fieldToSQLColumn(Field field){
        StringBuilder stringBuilder = new StringBuilder();

        if (field.isAnnotationPresent(Column.class)) {
            stringBuilder
                    .append(field.getAnnotation(Column.class).name())
                    .append(" ")
                    .append(dbType(field));
            if (field.isAnnotationPresent(Id.class)){
                stringBuilder.append(" primary key auto_increment");
            }
            if (field.isAnnotationPresent(Unique.class)){
                stringBuilder.append(" unique");
            }
        }
        return stringBuilder.toString();
    }

    private String dbType(Field field) {
        String type = field == null ? "" : field.getType().getSimpleName();
        switch (type){
            case "int":
            case "Integer":
                return "INT(11)";
            case "String":
                return "VARCHAR(50)";
            case "double":
            case "Double":
                return "DOUBLE";
            case "Date":
                return "DATETIME";
            default:
                throw new UnsupportedOperationException("Unsupported sql type : " + type);
        }
    }

    // insert or update entity into database
    @Override
    public boolean persist(E entity) throws IllegalAccessException, SQLException {

        // unique field
        Field field;
        Field[] fields = this.getColumns(entity, Unique.class);
        String uniqueColumn = null;
        if (fields.length > 0){
            field = fields[0];
            uniqueColumn = field.getAnnotation(Column.class).name();
        } else {
            throw new IllegalArgumentException("Entity doesn't have unique annotation.");
        }

        // value of the unique field
        String value;
        field.setAccessible(true);
        if (field.getType() == int.class || field.getType() == Integer.class) {
            value = String.valueOf(field);
        }
        else if (field.getType() == String.class){
            value = "'" + field.get(entity) + "'";
        } else {
            throw new UnsupportedOperationException("Unsupported unique field type : " + field.getType());
        }

        // check if Unique value exist
        String query = MessageFormat.format(SELECT_WHERE, "*", this.tableName, uniqueColumn + "=" + value);

        // UPDATABLE statement
        Statement statement = connection
                .createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

        ResultSet resultSet = statement.executeQuery(query);

        if (resultSet.next()){
            return this.doUpdate(entity, resultSet);
        } else {
            return this.doInsert(entity);
        }
    }

    // update user
    private boolean doUpdate(E entity, ResultSet resultSet) {
        try {
            if (resultSet == null || resultSet.isBeforeFirst() && !resultSet.next()) {
                throw new SQLException("Empty result set.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Arrays.stream(this.getColumns(entity, Column.class))
                .filter(field -> !field.isAnnotationPresent(Id.class))
                .forEach(field -> {

                    field.setAccessible(true);
                    String columnName = field.getAnnotation(Column.class).name();
                    assert(columnName == null);
                    Object type = field.getType();

                    boolean isUpdate = false;
                    try {
                        if (type == int.class ||type == Integer.class){
                            int value = (int) field.get(entity);
                            int databaseValue = resultSet.getInt(columnName);

                            // update to resultSet
                            if (value != databaseValue) {
                                resultSet.updateInt(columnName, value);
                                isUpdate = true;
                            }
                        }
                        else if (type == String.class) {
                            String value = String.valueOf(field.get(entity));
                            String databaseValue = resultSet.getString(columnName);

                            // update to resultSet
                            if (!value.equals(databaseValue)){
                                resultSet.updateString(columnName, value);
                                isUpdate = true;
                            }
                        }
                        else if (type == double.class) {
                            double value = (double) field.get(entity);
                            double databaseValue = resultSet.getDouble(columnName);

                            // update to resultSet
                            if (value != databaseValue) {
                                resultSet.updateDouble(columnName, value);
                                isUpdate = true;
                            }
                        }
                        else if (type == Date.class){
                            // TODO: 20.2.2020 г. Date
                        }
                        else {
                            throw new UnsupportedOperationException("Unsupported datatype : " + type);
                        }
                        // update database
                        if (isUpdate){
                            resultSet.updateRow();
                        }
                    } catch (IllegalAccessException | SQLException e) {
                        e.printStackTrace();
                    }
                });
        return true;
    }

    // add new user_name to database
    private boolean doInsert(E entity) throws SQLException, IllegalAccessException {
        StringBuilder columns = new StringBuilder();
        StringBuilder values = new StringBuilder();

        int count = 0;
        for (Field field: this.getColumns(entity, Column.class)) {
            if (!field.isAnnotationPresent(Id.class)){
                field.setAccessible(true);

                Object value = field.get(entity);
                Object type = field.getType();
                String columnName = field.getAnnotation(Column.class).name();

                columns
                    .append(count++ > 0 ? "," : "")
                    .append("`")
                    .append(columnName)
                    .append("`");

                boolean isString = (type == String.class || type == Date.class);

                if (type == Date.class) {
                    value = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(value);
                }

                if (count > 1){ values.append(","); }
                if (isString){ values.append("'"); }
                values.append(value);
                if (isString){ values.append("'"); }
            }
        }

        String query = MessageFormat.format(INSERT_INTO, this.tableName, columns.toString(), values.toString());
        return connection.prepareStatement(query).execute();
    }

    // create and map entity
    private E mapEntity (ResultSet resultSet) {

        E entity = null;
        try {
            entity = this.clazz.getConstructor().newInstance();

        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return mapEntity(entity, resultSet);
    }

    // map entity
    private E mapEntity (E entity, ResultSet resultSet){
        try {
            if (resultSet == null || resultSet.isBeforeFirst() && !resultSet.next()) {
                throw new SQLException("Empty result set.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Arrays
                .stream(this.clazz.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(Column.class))
                .forEach(field -> {
                    field.setAccessible(true);
                    try {
                        String columnName = field.getAnnotation(Column.class).name();
                        Object type = field.getType();

                        if (type == int.class || type == Integer.class) {
                            field.set(entity, resultSet.getInt(columnName));
                        }
                        else if (type == String.class){
                            field.set(entity, resultSet.getString(columnName));
                        }
                        else if (type == Date.class){
                            field.set(entity, resultSet.getTimestamp(columnName));
                        }
                        else if (type == double.class){
                            field.set(entity, resultSet.getDouble(columnName));
                        }
                        else {
                            throw new UnsupportedOperationException("Unsupported datatype : " + type);
                        }
                    } catch (SQLException | IllegalAccessException e) {
                        e.printStackTrace();
                    }
                });
        return entity;
    }

    public void listEntity(E entity) {
        Arrays
                .stream(this.clazz.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(Column.class))
                .forEach(field -> {
                    field.setAccessible(true);
                    System.out.printf("%n%-20s | ", field.getAnnotation(Column.class).name());
                    try {
                        System.out.print(field.get(entity));
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                });
        System.out.print(System.lineSeparator());
    }

    private void setTableName() {
        String name = "";
        Entity entity = this.clazz.getAnnotation(Entity.class);

        if (entity != null){
            name = entity.name();
        }
        if (name.isEmpty()) {
            name = this.clazz.getSimpleName();
        }
        this.tableName = name;
    }

    private String getColumnName(Field field) {
        Column column = field.getAnnotation(Column.class);
        String columnName = column.name();
        if (columnName.isEmpty()){
            columnName = field.getName();
        }
        return columnName;
    }


    private Field[] getColumns(E entity,  Class<? extends Annotation> annotation) {
        return
            Arrays
                .stream(entity.getClass().getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(annotation))
                .toArray(Field[]:: new);
    }

    @Override
    public Iterable<E> find(Class<E> table) {
        return find(table, null);
    }

    @Override
    public Iterable<E> find(Class<E> table, String where) {
        String query = MessageFormat.format
                (SELECT_WHERE, "*", this.tableName, where == null ? "1" : where);

        ArrayList<E> entities = new ArrayList<>();
        try {
            ResultSet resultSet = connection.prepareStatement(query).executeQuery();

            while (resultSet.next()) {
                entities.add(this.mapEntity(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return entities;
    }

    @Override
    public E findFirst(Class<E> table) {
        return findFirst(table, null);
    }

    @Override
    public E findFirst(Class<E> table, String where) {
        String query = MessageFormat.format
                (SELECT_WHERE, "*", this.tableName, where == null ? "1" : where)
                + " LIMIT 1";
        try {
            ResultSet resultSet = connection.prepareStatement(query).executeQuery();
            return this.mapEntity(resultSet);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


}
