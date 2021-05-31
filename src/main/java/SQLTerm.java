
public class SQLTerm {
	String _strTableName;
	String _strColumnName;
	String _strOperator;
	Object _objValue;
	public SQLTerm()
	{

	}

	@Override
	public String toString() {
		return "SQLTerm{" +
				"_strTableName='" + _strTableName + '\'' +
				", _strColumnName='" + _strColumnName + '\'' +
				", _strOperator='" + _strOperator + '\'' +
				", _objValue=" + _objValue +
				'}';
	}
}
