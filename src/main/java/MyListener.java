//This class will have methods I want to override from MySqlParserBaseListener for grammar rules I Enter

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class MyListener extends MySqlParserBaseListener{

    public static String statement="";
    public static Vector<String> operators=new Vector<>();
    public static String tableName ="";
    public static Vector<SQLTerm> term=new Vector<>();
    public static Vector<String> values =new Vector<>();
    public static Vector<String> columns =new Vector<>();

    public static Hashtable<String,String> colNameTypes =new Hashtable<>();
    public static Hashtable<String,String> minColumnValues =new Hashtable<>();
    public static Hashtable<String,String> maxColumnValues =new Hashtable<>();

    public static String clustering ="";

    public static Object getObj(String tableN,String columnN,String v)
    {

        Object toBeReturned=null;
        try {
            File myObj = new File("src/main/resources/metadata.csv");
            Scanner myReader = new Scanner(myObj);
            myReader.nextLine();
            while (myReader.hasNextLine()) {
                String line = myReader.nextLine();

                String[] arr = line.split(",");
                if (arr[0].equals(tableN))
                {

                    if(arr[1].equals(columnN)){

                        switch(arr[2])
                        {
                            case "java.lang.Integer":
                                toBeReturned=Integer.parseInt(v);
                                break;
                            case "java.lang.String":toBeReturned=new String(v);

                                break;
                            case "java.lang.Double":toBeReturned=Double.parseDouble(v);break;
                            case "java.util.Date":

                                int year = Integer.parseInt(v.trim().substring(0, 4));
                                int month = Integer.parseInt(v.substring(5, 7));
                                int day = Integer.parseInt(v.substring(8));

                                toBeReturned = new Date(year - 1900, month - 1, day);

                                break;

                        }


                    }

                }

            }
            myReader.close();


        } catch (FileNotFoundException e) {
             System.out.println("An error occurred.");
             e.printStackTrace();
        }
          return toBeReturned;
    }


    @Override public void enterSqlStatement(MySqlParser.SqlStatementContext ctx) {
        statement="";
        operators=new Vector<>();
        tableName ="";
        term=new Vector<>();
        values =new Vector<>();
        columns =new Vector<>();

        colNameTypes =new Hashtable<>();
        minColumnValues =new Hashtable<>();
        maxColumnValues =new Hashtable<>();

        clustering ="";



    }
    @Override public void enterSimpleSelect(MySqlParser.SimpleSelectContext ctx) {
        statement="select";
        System.out.println("entered se");

    }
    @Override public void enterLogicalOperator(MySqlParser.LogicalOperatorContext ctx) {
        operators.add(ctx.getText());
    }
    @Override public void enterTableName(MySqlParser.TableNameContext ctx) {
        tableName=ctx.getText();
    }
    @Override public void enterBinaryComparasionPredicate(MySqlParser.BinaryComparasionPredicateContext ctx) {
        String columnName= ctx.getChild(0).getText();
        String operator=ctx.getChild(1).getText();
        String value=ctx.getChild(2).getText();
        int firstChar=(int)(value.charAt(0));
        if(firstChar==39){
            value=value.substring(1, value.length()-1);
        }
        Object val=getObj(tableName,columnName,value);
        SQLTerm theTerm=new SQLTerm();
        theTerm._strTableName= tableName;
        theTerm._strColumnName=columnName;
        theTerm._strOperator=operator;
        theTerm._objValue=val;
        term.add(theTerm);
    }

    @Override public void enterDeleteStatement(MySqlParser.DeleteStatementContext ctx) {
        statement="delete";
        tableName ="";
        term=new Vector<>();

    }
    @Override public void enterInsertStatement(MySqlParser.InsertStatementContext ctx) {
        statement="insert";

    }

    @Override public void enterUpdateStatement(MySqlParser.UpdateStatementContext ctx) {
        statement="update";
    }
    @Override public void enterExpressionOrDefault(MySqlParser.ExpressionOrDefaultContext ctx){
        //Obj vv=getObj(tableName,column)
        String val=ctx.getText();

        int firstChar=(int)(val.charAt(0));
        if(firstChar==39){
            val=val.substring(1,val.length()-1);
        }
        values.add(val);

    }
    @Override public void enterUid(MySqlParser.UidContext ctx) {
        if(ctx.getParent().getClass().getName().equals("MySqlParser$UidListContext")){
            columns.add(ctx.getText());
        }

    }
    @Override public void enterUpdatedElement(MySqlParser.UpdatedElementContext ctx) {
        String columnName= ctx.getChild(0).getText();
        String value=ctx.getChild(2).getText();
        int firstChar=(int)(value.charAt(0));
        if(firstChar==39) {
            value=value.substring(1,value.length()-1);
        }

        columns.add(columnName);
        values.add(value);
    }
    @Override public void enterCreateIndex(MySqlParser.CreateIndexContext ctx) {
        statement="createIndex";
    }
    @Override public void enterIndexColumnName(MySqlParser.IndexColumnNameContext ctx) {
        columns.add(ctx.getText());
    }
    @Override public void enterColumnCreateTable(MySqlParser.ColumnCreateTableContext ctx) {
        statement="createTable";
    }
    @Override public void enterColumnDeclaration(MySqlParser.ColumnDeclarationContext ctx) {
        String type=ctx.getChild(1).getChild(0).getText().toLowerCase();
        //varchar,char,date,int,float
      String csvType="";
        switch(type.substring(0,3))
        {
            case "int" :csvType="java.lang.Integer" ;     break;
            case "cha" :csvType="java.lang.String";      break;
            case "dat" :csvType="java.util.Date";      break;
            case "var" :csvType="java.lang.String";      break;
            case "flo" :csvType="java.lang.Double";      break;
        }
      colNameTypes.put(ctx.getChild(0).getText(),csvType);

    }
    @Override public void enterBetweenPredicate(MySqlParser.BetweenPredicateContext ctx) {
        String maxx=ctx.getChild(4).getText();
        String minn=ctx.getChild(2).getText();

        int char1=(int)(minn.charAt(0));
        if(char1==39){
            minn=minn.substring(1, minn.length()-1);
        }
        int char2=(int)(maxx.charAt(0));
        if(char2==39){
            maxx=maxx.substring(1, maxx.length()-1);
        }


     minColumnValues.put(ctx.getChild(0).getText(),minn);
     maxColumnValues.put(ctx.getChild(0).getText(),maxx);
    }
    @Override public void enterPrimaryKeyColumnConstraint(MySqlParser.PrimaryKeyColumnConstraintContext ctx) {
        clustering=ctx.getParent().getParent().getChild(0).getText();
    }

}
