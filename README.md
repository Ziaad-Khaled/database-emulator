## Project Description

This project involves building a small database engine with support for Grid Index. The main functionalities to be implemented include:

1. Creating tables
2. Inserting tuples into tables
3. Deleting tuples from tables
4. Linear search in tables
5. Creating a Grid index
6. Searching using the Grid index

The project is a simplified database engine, focusing on the mentioned functionalities. Foreign keys and referential integrity constraints are not required to be implemented.

## Methods

The main class `DBApp.java` have the following methods with specified signatures:

- `public void init();`
- `public void createTable(String strTableName, String strClusteringKeyColumn, Hashtable<String, String> htblColNameType, Hashtable<String, String> htblColNameMin, Hashtable<String, String> htblColNameMax) throws DBAppException`
- `public void createIndex(String strTableName, String[] strarrColName) throws DBAppException`
- `public void insertIntoTable(String strTableName, Hashtable<String, Object> htblColNameValue) throws DBAppException`
- `public void updateTable(String strTableName, String strClusteringKeyValue, Hashtable<String, Object> htblColNameValue) throws DBAppException`
- `public void deleteFromTable(String strTableName, Hashtable<String, Object> htblColNameValue) throws DBAppException`
- `public Iterator selectFromTable(SQLTerm[] arrSQLTerms, String[] strarrOperators) throws DBAppException`

## SQL Parser

- Support for processing SQL statements using a SQL parser.
- Only accept SQL statements that can be executed within the mini database engine.
- Implement a method `public Iterator parseSQL(StringBuffer strbufSQL) throws DBAppException`
- Before trying to parse sql using parseSql method,You have to disable scala plugin that is added lately in intellij for that go to file->settings->plugins->(search for scala plugin and disable it)
