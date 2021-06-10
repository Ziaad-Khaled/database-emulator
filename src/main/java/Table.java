
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Table {
	
	String name;
	String clusteringKey;
	String clusteringKeyDataType;
	String path;
	boolean isEmpty = true;
	int arity = 1;
	Vector<String> pagesPath = new Vector<String>();
	//Keep track of metadata for the table.
	Hashtable<String, String> metadata = new Hashtable<String, String>();
	Vector<Object[]> indices = new Vector<>(); //[Grid1,Vector2,Vector3,Vector4]
	Vector<Vector<String>> indicesColNames = new Vector<>(); //["id,age,name,age,email","name,id","",""]
	Hashtable<String,Pair[]> ranges = new Hashtable<>(); // ranges of each column


	public Table(String tableName, String clusteringKey, Hashtable<String, String> colNameType,
		Hashtable<String, String> colNameMin, Hashtable<String, String> colNameMax) throws IOException {
		metadata = colNameType;
		path = "./src/main/resources/data/" + tableName;
		name = tableName;
		this.clusteringKey = clusteringKey;
		File file = new File(path);
	      //Creating the directory
	      boolean bool = file.mkdir();
	      //System.out.println(path);
	      if(bool)
	         System.out.println("Directory created successfully");
	      else { // throw an exception the table already exists.
			 System.out.println("The table was created previously");
		  }
	      Set<String> setOfColumns = colNameType.keySet();
		try {
			FileWriter myWriter = new FileWriter("./src/main/resources/metadata.csv", true);
			myWriter.append(tableName + "," + clusteringKey + "," + colNameType.get(clusteringKey) + "," +"True" + ","+ "False," + colNameMin.get(clusteringKey) + "," + colNameMax.get(clusteringKey) + "\n");
			clusteringKeyDataType = colNameType.get(clusteringKey);
			ranges.put(clusteringKey,getRanges(colNameMin.get(clusteringKey), colNameMax.get(clusteringKey), colNameType.get(clusteringKey)));
			for(String key : setOfColumns) {
				arity++;
				if(key.equals(clusteringKey))
					continue;
				myWriter.append(tableName + "," + key + "," + colNameType.get(key) + "," + "False" + "," + "False," + colNameMin.get(key) + "," + colNameMax.get(key) + "\n");
				ranges.put(key,getRanges(colNameMin.get(key), colNameMax.get(key), colNameType.get(key)));

			}
			myWriter.close();
			System.out.println("Successfully wrote to the file.");
		}
		catch (IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}

	}
	public static int getIndexOfGrid(Object value, Pair[] ranges){

		if(value instanceof Date)
		{
			String date = ((Date)value).toString();
			StringTokenizer st = new StringTokenizer(date);
			st.nextToken();
			st.nextToken();
			value = Integer.parseInt(st.nextToken());
		}


		for(int i=0; i<ranges.length; i++)
		{
			Pair range = ranges[i];
			if(range.withinRange(value))
				return i;
		}
		return -1;

	}
	public static Pair[] getRanges(String min, String max, String colType){
		Pair[] ranges = new Pair[10];
		if(colType.equals("java.lang.Integer")){
			int minInteger = Integer.parseInt(min);
			int maxInteger = Integer.parseInt(max);
			double rangeStep = (maxInteger-minInteger)/10.0;
			int x=minInteger;
			int j=9;
			if(rangeStep<1)
				j = (int)(rangeStep*10-1);
			for(int i=0; i<=j-1; i++){
				rangeStep = (maxInteger-x)/(10-i);
				if(rangeStep==0)
					rangeStep++;
				ranges[i]=new Pair(x,(int)(x+rangeStep));
				x=(int)(x+rangeStep);
			}
			ranges[j] = new Pair(x,maxInteger+1);

		}
		if(colType.equals("java.lang.Double")){
			double minDouble = Double.parseDouble(min);
			double maxDouble = Double.parseDouble(max);
			double rangeStep = (maxDouble-minDouble)/10.0;
			double x=minDouble;
			for(int i=0; i<=8; i++){
				ranges[i]=new Pair(x,x+rangeStep);
				x=x+rangeStep;
			}
			ranges[9] = new Pair(x,maxDouble+1);
		}


		if(colType.equals("java.util.Date")){//31-12-1990  31-12-2020
			int minDay = 1;
			int maxDay = 31;
			int rangeStep = (maxDay-minDay)/10;
			int x=minDay;
			for(int i=0; i<=8; i++){
				rangeStep=(maxDay-x)/(10-i);
				ranges[i]=new Pair(x,x+rangeStep);
				x=x+rangeStep;
			}
			ranges[9]=new Pair(x,maxDay+1);
		}
		if(colType.equals("java.lang.String")){//Sasshbwshbxhwb Sanxnenixe //Sas San
			int firstDiffIndex = firstDifferentChar(min,max);
			String minString = min.substring(0, firstDiffIndex);
			String maxString = max.substring(0, firstDiffIndex);
			ranges = generateRangesForStrings(minString,maxString);
		}
		return ranges;

	}
	public static Pair[] generateRangesForStrings(String min, String max){
		System.out.println("entered");
		char m = max.charAt(max.length()-1);
		char n = min.charAt(min.length()-1);
		String remainingSimilar = min.substring(0,min.length()-1);
		Pair[] ranges = new Pair[10];
		char x = n;
		int j=9;
		char y=n;
		double rangeStep = ((int)m-(int)x)/(10.0);
		if(rangeStep<1)
		{
			System.out.println(j);
			j = (int)(rangeStep*10)-1;
			System.out.println(j);
		}

		for(int i=0; i<=j-1; i++){//n  q
			rangeStep = ((int)m-(int)x)/(10-i);
			if(rangeStep==0)
				rangeStep++;
			y = (char) (x + rangeStep);
			String rangeMin = remainingSimilar + x;
			String rangeMax = remainingSimilar + y;
			ranges[i]=new Pair(rangeMin,rangeMax);
			x= (char) (x+rangeStep);
		}
		String rangeMin = remainingSimilar + x;
		y = (char) (x + rangeStep+1);
		String rangeMax = remainingSimilar + y;
		System.out.println(j);
		ranges[j]=new Pair(rangeMin,rangeMax);

		return ranges;
	}
	public static int firstDifferentChar(String min, String max){
		int lowerLength = min.length()<max.length()? min.length():max.length();
		for(int i=0; i<lowerLength; i++)
			if(min.charAt(i)!=max.charAt(i))
				return i+1;
		return -1;

	}


	
	public String toString() {
		String res = "";
		for(String path : this.pagesPath) {
			Vector<Tuple> tuple = DBApp.deserialize(path);
			res += tuple.toString() + "\n";
		}
		return res;
	}
	
	public boolean isValidTuple(Tuple tuple) throws DBAppException {
		Hashtable<String, String> columnType = new Hashtable<String, String>();
		Hashtable<String, String> columnMin = new Hashtable<String, String>();
		Hashtable<String, String> columnMax = new Hashtable<String, String>();
		
		try {
			File myObj = new File("src/main/resources/metadata.csv");
			Scanner myReader = new Scanner(myObj);
			myReader.nextLine();
			while (myReader.hasNextLine()) {
				String line = myReader.nextLine();
				String[] arr = line.split(",");
				if (!arr[0].equals(this.name))
					continue;
				columnType.put(arr[1], arr[2]);
				columnMin.put(arr[1], arr[5]);
				columnMax.put(arr[1], arr[6]);	
			}
			myReader.close();


		} catch (FileNotFoundException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
		for(String key: tuple.data.keySet()) {
			if(key.equals(tuple.clusteringKey)) // Compare Strings using equals()
				continue;
			if(columnType.containsKey(key)) {
				if(!tuple.data.get(key).getClass().getName().equals(columnType.get(key)))
					return false;
				
				Object min, max;

				if(columnType.get(key).equals("java.lang.Integer")) {
					min = Integer.parseInt(columnMin.get(key));
					max = Integer.parseInt(columnMax.get(key));
				}
				else if(columnType.get(key).equals("java.lang.Double")) {
					min = Double.parseDouble(columnMin.get(key));
					max = Double.parseDouble(columnMax.get(key));
				}
				else if(columnType.get(key).equals("java.util.Date")) {
					SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd");
					try {
						min = sdformat.parse(columnMin.get(key));
						max = sdformat.parse(columnMax.get(key));

					} catch (ParseException e) {
						e.printStackTrace();
						return false;
					}
				}
				else {
					min = columnMin.get(key);
					max = columnMax.get(key);
				}
				Tuple maxPair = new Tuple();
				maxPair.clusteringKey= tuple.clusteringKey;
				maxPair.data.put(key,max);
				Tuple minPair = new Tuple();
				minPair.clusteringKey= tuple.clusteringKey;
				minPair.data.put(key,min);



				if(DBApp.comparison(tuple.data.get(key), maxPair.data.get(key)) > 0 || DBApp.comparison(tuple.data.get(key), minPair.data.get(key)) < 0)
				{
					System.out.println(tuple);
					System.out.println(maxPair);
					System.out.println(minPair);

					return false;
				}

			}
			//Heere
			else throw new DBAppException("Inserted data types are invalid or minimum and maximum constraints were not satisfied! Update failed.");

		}
		return true;
	}



	
	public static void main(String[] args) throws IOException, DBAppException {

		Vector<String> v = new Vector<>();
		v.add("Mohamed");
		System.out.println(v.indexOf("ddd"));




		DBApp dbApp = new DBApp();
		String tableName = "students";

		Hashtable<String, String> htblColNameType = new Hashtable<String, String>();
		htblColNameType.put("id", "java.lang.String");
		htblColNameType.put("first_name", "java.lang.String");
		htblColNameType.put("last_name", "java.lang.String");
		htblColNameType.put("dob", "java.util.Date");
		htblColNameType.put("gpa", "java.lang.Double");

		Hashtable<String, String> minValues = new Hashtable<>();
		minValues.put("id", "43-0000");
		minValues.put("first_name", "AAAAAA");
		minValues.put("last_name", "AAAAAA");
		minValues.put("dob", "1990-01-01");
		minValues.put("gpa", "0.7");

		Hashtable<String, String> maxValues = new Hashtable<>();
		maxValues.put("id", "99-9999");
		maxValues.put("first_name", "zzzzzz");
		maxValues.put("last_name", "zzzzzz");
		maxValues.put("dob", "2000-12-31");
		maxValues.put("gpa", "5.0");

		dbApp.createTable(tableName, "id", htblColNameType, minValues, maxValues);
		Hashtable<String,Pair[]> ht = dbApp.tables.get(tableName).ranges;
		for(String key: ht.keySet())
		{
			System.out.println(key + " " +  Arrays.toString(ht.get(key)));
		}
	}


	
}