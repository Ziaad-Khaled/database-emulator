import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.io.*;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.sql.Array;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.*;


public class DBApp implements DBAppInterface, Serializable {

	private static final long serialVersionUID = 1L;

	static Hashtable<String, Table> tables = new Hashtable<>();

	@Override
	public void init() {

		File file = new File("./src/main/resources/data");
		//Creating the directory

		boolean bool = false;
		if(!file.exists())
			bool = file.mkdir();
		//System.out.println(path);
		if(bool)
			System.out.println("Directory created successfully");
		else { // throw an exception the table already exists.
			System.out.println("The table was created previously");
		}
	}

	public static int readConfigBuckets() throws IOException {
		FileReader reader = new FileReader("./target/classes/DBApp.config");

		// create properties object
		Properties p = new Properties();

		// Add a wrapper around reader object
		p.load(reader);

		// access properties data

		return Integer.parseInt(p.getProperty("MaximumKeysCountinIndexBucket"));
	}

	public static int readConfigRows() {
		try {
			FileReader reader = new FileReader("./target/classes/DBApp.config");

			// create properties object
			Properties p = new Properties();

			// Add a wrapper around reader object
			p.load(reader);
			return Integer.parseInt(p.getProperty("MaximumRowsCountinPage"));

		} catch (IOException e) {
			System.out.println("Exception in readConfig");
			return -1;
		}

		// access properties data

	}

	@Override
	public void createTable(String tableName, String clusteringKey, Hashtable<String, String> colNameType,
			Hashtable<String, String> colNameMin, Hashtable<String, String> colNameMax)
			throws DBAppException{
		// init is already called in the test
		if (tables.isEmpty()) {

			// Ziad: creates a new metadata file

			try {
				FileWriter myWriter = new FileWriter("src/main/resources/metadata.csv");
				myWriter.write("Table Name,Column Name,Column Type,ClusteringKey,Indexed,min,max\n");
				myWriter.close();
				System.out.println("Successfully wrote to the file.");
			} catch (IOException e) {
				System.out.println("An error occurred.");
				e.printStackTrace();
			}
		}
		Table t = null;
		try {
			t = new Table(tableName, clusteringKey, colNameType, colNameMin, colNameMax);
		} catch (IOException e) {
			e.printStackTrace();
		}
		tables.put(tableName, t);
	}
	public static void indexToMetadata(String tableName, String[] colName) throws DBAppException{
		String filePath = "./src/main/resources/metadata.csv";
		String input = null;
		Scanner sc;
		try
		{
			sc = new Scanner(new File(filePath));
		}
		catch(FileNotFoundException e)
		{
			throw new DBAppException("Table file not found");
		}
		StringBuffer sb = new StringBuffer();
		sb.append(sc.nextLine() + "\n");
		while (sc.hasNextLine()) {
			input = sc.nextLine();
			String[] arr = input.split(",");
			while(arr[0].equals(tableName))
			{
				if(Arrays.asList(colName).contains(arr[1]))
					arr[4]="True";
				sb.append(String.join(",", arr) + "\n");
				if(sc.hasNextLine())
				{
					input = sc.nextLine();
					arr = input.split(",");
				}
				else
					break;
			}
			if(!arr[0].equals(tableName))
			{
				sb.append(input + "\n");
				continue;
			}
		}
		PrintWriter writer;
		try
		{
			writer = new PrintWriter(new File(filePath));
		}
		catch(FileNotFoundException e)
		{
			throw new DBAppException("Table file not found");
		}
		writer.append(sb);
		writer.flush();
	}

	@Override
	public void createIndex(String tableName, String[] columnNames) throws DBAppException {

		Table table = tables.get(tableName);
		Vector<Object[]> tableIndices = table.indices;
		Vector<Vector<String>> indicesColNames = table.indicesColNames;
		boolean found = false;
		for(int i=0; i<indicesColNames.size(); i++)//loop on each index <col1,col2,col3>
		{
			found=true;
			for(int j=0; j<columnNames.length; j++)
				if(!(indicesColNames.get(i)).contains(columnNames[j]))
				{
					found=false;
					break;
				}
			if(found && columnNames.length==indicesColNames.get(i).size())
				throw new DBAppException("This column already has an index!");
		}


		indexToMetadata(tableName,  columnNames);
		HashSet<String> colNames = new HashSet<>();
		Hashtable<String,String> colMins = new Hashtable<>();
		Hashtable<String, String> colMaxs = new Hashtable<>();
		Hashtable<String, String> colType = new Hashtable<String, String>();

		try {
			File myObj = new File("src/main/resources/metadata.csv");
			Scanner myReader = new Scanner(myObj);
			myReader.nextLine();
			while (myReader.hasNextLine()) {
				String line = myReader.nextLine();
				String[] arr = line.split(",");
				if (!arr[0].equals(tableName))
					continue;
				colNames.add(arr[1]);
				colMins.put(arr[1],arr[5]);
				colMaxs.put(arr[1],arr[6]);
				colType.put(arr[1],arr[2]);
			}
			myReader.close();


		} catch (FileNotFoundException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}

		System.out.println(Arrays.toString(columnNames));
		System.out.println(colNames);
		for(int i=0; i<columnNames.length; i++) {
			if(!colNames.contains(columnNames[i]))
			{
				System.out.println(Arrays.toString(columnNames));
				System.out.println(colNames);
				throw new DBAppException("Invalid column Name! Cannot create an index!");
			}
		}


		String indiciesPath="";
		for(int i=0;i<columnNames.length;i++)
		{
			indiciesPath+= columnNames[i];
			if(i!=columnNames.length-1)
			{
				indiciesPath+= "-";
			}

		}
		if(table.indices.isEmpty())
			createDirectory(table.path+ "/indices");
		createDirectory(table.path+"/indices/" + indiciesPath);
		String indexPath = table.path+"/indices/" + indiciesPath +"/";
		Object[] grid = multiDimensionalArray(columnNames.length, 10, indexPath);
		//BEGIN HERE : INITIALIZE VECTOR OF BUCKETS PATH
		tableIndices.add(grid);
		Vector<String> colNamesVector = new Vector<>();
		for(int i=0; i<columnNames.length; i++)
			colNamesVector.add(columnNames[i]);
		indicesColNames.add(colNamesVector);

		Object value = null;

		boolean flag = false;
		for(String path : table.pagesPath)
		{ // We loop on all pages in the table.

			Vector<Tuple> page = deserialize(path);
			for(Tuple tuple : page)
			{
				// We loop on all tuples in a page.

				Object[] pointer = grid;
				for(int i=0; i<columnNames.length;i++)
				{ // access grid layers.
					String colName=columnNames[i];
					value = tuple.data.get(colName);
					int indexOfRange = table.getIndexOfGrid(value,table.ranges.get(colName));

					if(i==columnNames.length-1)
					{// This is the last layer.

						Vector<Vector<String>> vectorOfBucket = deserializeBucket((String)(pointer[indexOfRange]));
						boolean pageFound  = false;
						for(int j = 0 ; j < vectorOfBucket.size() ; j++){ // Check if the desired page is in any of the buckets.
							if(vectorOfBucket.get(j).contains(path))
							{
								pageFound = true;
								break;
							}
						}
						if(!pageFound){// if the page is not in any bucket, we add it to a bucket.
							try {
								if(readConfigBuckets() == vectorOfBucket.get(vectorOfBucket.size()-1).size()){
									// The last bucket is full, so we create a new one.
									vectorOfBucket = addNewBucket(vectorOfBucket, path);
								}
								// the last bucket has space so we add the page to it.
								else vectorOfBucket.get(vectorOfBucket.size()-1).add(path);
							} catch (IOException e) {
								e.printStackTrace();
							}

						}

						//check bucket size
						serializeBucket((String)pointer[indexOfRange], vectorOfBucket);
						break;
					}
					pointer=(Object[])pointer[indexOfRange];
				}
			}
		}
	}
	public static Vector<Vector<String>> addNewBucket(Vector<Vector<String>> vectorOfBucket, String path) {
		Vector<String> bucket = new Vector<>();
		bucket.add(path);
		vectorOfBucket.add(bucket);
		return vectorOfBucket;
	}
	public static Object[] multiDimensionalArray(int dimensions, int size, String path) {
		if (dimensions < 1) {
			throw new IndexOutOfBoundsException("non-positive dimensionality");
		} else if (dimensions == 1) {
			return intializeBucketPath(path);
		} else {
			Object[] array = new Object[size];
			for (int i = 0; i<size; ++i) {
				array[i] = multiDimensionalArray(dimensions-1, size,path);
			}
			return array;
		}
	}
	//src/resources/courses/index261323255
	public static String[] intializeBucketPath(String path){

		String[] bucketsPath = new String[10];
		for (int i=0;i<10;i++)
		{
			//Vector<String> bucket = new Vector<>();
			Vector<Vector<String>> vectorOfBuckets = new Vector<>();
			Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			long fileName = timestamp.getTime();
			double random= Math.random();
			bucketsPath[i]=path+fileName+random;
			Vector<String> bucket = new Vector<>();

			vectorOfBuckets.add(bucket);
			serializeBucket(bucketsPath[i], vectorOfBuckets) ;
		}
		return bucketsPath;
	}
	public static void createDirectory(String path){

		File file = new File(path);

		boolean bool = file.mkdir();
		if(bool)
			System.out.println("Directory created successfully");
		else { // throw an exception the table already exists.
			System.out.println("The table was created previously");
		}
	}
	public static Date convertDate(String date){
		int year = Integer.parseInt(date.trim().substring(0, 4));
		int month = Integer.parseInt(date.trim().substring(5, 7));
		int day = Integer.parseInt(date.trim().substring(8));

		Date dob = new Date(year - 1900, month - 1, day);
		return dob;
	}
	public static int comparison(Object x, Object y) {

		if(x instanceof String)
		{
			String x1 = x.toString();
			String y1 = y.toString();
			try {
				return Integer.parseInt(x1) - Integer.parseInt(y1);
			} catch (Exception e) {

			}
			try {
				return (int) (Double.parseDouble(x1) - Double.parseDouble(y1));
			} catch (Exception e) {

			}

			return x1.compareTo(y1);
		}


		try {
			String y1 = (String) y;
			int year = Integer.parseInt(y1.trim().substring(0, 4));
			int month = Integer.parseInt(y1.trim().substring(5, 7));
			int day = Integer.parseInt(y1.trim().substring(8));

			Date dob2 = new Date(year - 1900, month - 1, day);

			Date dob1 = (Date) x;
			return dob1.compareTo(dob2);


		} catch (Exception e) {
		}
		return 0;
	}
	public static double comparisonForSelect(Object x, Object y){
		//y mosalem
		try {//3.5 3
			return  ((Double)x - (Double) y);
		} catch (Exception e) {

		}
		try {
			return (Integer) x - (Integer) y;
		} catch (Exception e) {

		}

		try {
			String y1 = (String) y;
			int year = Integer.parseInt(y1.trim().substring(0, 4));
			int month = Integer.parseInt(y1.trim().substring(5, 7));
			int day = Integer.parseInt(y1.trim().substring(8));

			Date dob2 = new Date(year - 1900, month - 1, day);
			Date dob1 = (Date) x;
			return dob1.compareTo(dob2);


		}catch(Exception e){

		}




		return (x.toString()).compareTo(y.toString());

	}
	@Override
	public void insertIntoTable(String tableName, Hashtable<String, Object> colNameValue) throws DBAppException {

		Table table = tables.get(tableName);
		Tuple tuple = new Tuple();
		tuple.clusteringKey=table.clusteringKey;
		String clusteringKeyMin = "", clusteringKeyMax = "", clusteringKeyType = "";
		Object clusteringKeyValue = colNameValue.get(table.clusteringKey);

		if (table.arity < colNameValue.size())
			throw new DBAppException("The inserted values are more than the number of columns!");

		if (!colNameValue.containsKey(table.clusteringKey))
			throw new DBAppException("The inserted values does not contain the primary key!");

		Vector<String[]> metadata = new Vector<>();

		try {
			File myObj = new File("src/main/resources/metadata.csv");
			Scanner myReader = new Scanner(myObj);
			myReader.nextLine();
			while (myReader.hasNextLine()) {
				String line = myReader.nextLine();
				String[] arr = line.split(",");
				if (!arr[0].equals(tableName))
					continue;
				metadata.add(arr);
				if(arr[1].equals(table.clusteringKey)){
					clusteringKeyMin = arr[5];
					clusteringKeyMax = arr[6];
					clusteringKeyType = arr[2];
				}
			}
			myReader.close();
		} catch (FileNotFoundException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
	try {
		if (!colNameValue.containsKey(metadata.get(0)[1]))
			throw new DBAppException("Primary key needed!!");
	}
	catch(Exception E)
	{
		throw new DBAppException("Unexpected exception type thrown ==> expected: <DBAppException> but was: <java.lang.ArrayIndexOutOfBoundsException>");
	}

		for (String[] arr : metadata) {

			if (colNameValue.containsKey(arr[1])) {

				if (!arr[2].equals(colNameValue.get(arr[1]).getClass().getName()))
					throw new DBAppException("Invalid data types!");

				Object value = colNameValue.get(arr[1]);

				if (comparison(value, arr[5]) < 0)
					throw new DBAppException("The inserted value is less than the minimum value");

				if (comparison(value, arr[6]) > 0)
					throw new DBAppException("The inserted value is more than the maximum value ");

				tuple.data.put(arr[1], colNameValue.get(arr[1]));
				colNameValue.remove(arr[1]);

			} else {
				tuple.data.put(arr[1], null);
			}

		}
		if (!colNameValue.isEmpty()) {
			tuple.data.clear();
			throw new DBAppException("Invalid column name!");
		}
		if (table.isEmpty) {
			Vector<Tuple> page = new Vector<Tuple>();
			page.add(tuple);
			Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			long fileName = timestamp.getTime();
			serialize(table.path + "/page" + fileName, page);
			table.pagesPath.add(table.path + "/page" + fileName);


		} else {

			Object[] grid = null;
			int dimension = 0;
			for(int i = 0 ; i < table.indices.size() ; i++){
				if(table.indicesColNames.get(i).get(0).equals(table.clusteringKey)){
					grid = table.indices.get(i);
					dimension = table.indicesColNames.get(i).size();
				}
				if(table.indicesColNames.get(i).size() == 1  && table.indicesColNames.get(i).get(0).equals(table.clusteringKey))
				{
					dimension = 1;
					break;
				}

			}
			if(grid != null) {
				Pair[] ranges = Table.getRanges(clusteringKeyMin, clusteringKeyMax, clusteringKeyType);
				int index = Table.getIndexOfGrid(tuple.data.get(tuple.clusteringKey), ranges);
				System.out.println(Arrays.toString(ranges));
				System.out.println(index);

				String path = "";
				if(dimension == 1)
				{
					String bucketPath = (String) grid[index];

					path = searchBucket(bucketPath, tuple, table);
					//System.out.println(path);
				}

				else
				{
					Object[] toBeSearched = (Object[]) grid[index];
					System.out.println(colNameValue);
					prepareForSearch();
					path = searchLayers(toBeSearched, clusteringKeyValue, dimension, table);
					System.out.println(clusteringKeyValue + " " + path);
					System.out.println(table);
				}
				if(path.equals("")){
					int pageIndex = searchForPage(table, tuple);
					path = table.pagesPath.get(pageIndex);
				}
				Vector<Tuple> page = deserialize(path);
				int indexInPage = searchInPage(page, tuple);
				System.out.println(page + " " + indexInPage);
				try {
					if (tuple.compareTo(page.get(indexInPage)) == 0) {
						throw new DBAppException("This primary key already exists");
					}
				} catch (ArrayIndexOutOfBoundsException e) {
				}
				indexInPage++;

				if (page.size() == readConfigRows()) { // page 0,1
					page.insertElementAt(tuple, indexInPage);
					Tuple last = page.remove(page.size() - 1);
					serialize(path, page);   // 1 - remove page 1 fro
					String newPath = shiftPage(table, path, last);
					//milestone2
					indexAfterInserting(path,tuple,table);
					indexAfterInserting(newPath, last, table);
					indexAfterDeletion(path,last, table,page);
				} else {
					page.insertElementAt(tuple, indexInPage);
					serialize(path, page);
					//milestone2
					indexAfterInserting(path,tuple,table);
				}
				return;
			}


			int page_index = searchForPage(table, tuple);
			String path = "";
			try {
				path = table.pagesPath.get(page_index);
			} // New page needed
			catch (ArrayIndexOutOfBoundsException e) {
				Vector<Tuple> newPage = new Vector<Tuple>();
				newPage.add(tuple);
				Timestamp timestamp = new Timestamp(System.currentTimeMillis());
				long fileName = timestamp.getTime();
				String newPath = table.path + "/page" + fileName;
				table.pagesPath.add(newPath);
				serialize(newPath, newPage);
				//milestone2
				indexAfterInserting(newPath,tuple,table);
				return;
			}

			int indexInPage = searchInPage(deserialize(path), tuple);
			Vector<Tuple> page = deserialize(path);
			// You can insert a duplicate of the clustring key.
			try {
				if (tuple.compareTo(page.get(indexInPage)) == 0) {
				
					throw new DBAppException("This primary key already exists");
				}
			} catch (ArrayIndexOutOfBoundsException e) {

			}

			indexInPage++;

			if (page.size() == readConfigRows()) { // page 0,1
				page.insertElementAt(tuple, indexInPage);
				Tuple last = page.remove(page.size() - 1);
				serialize(path, page);   // 1 - remove page 1 fro
				String newPath = shiftPage(table, path, last);
				//milestone2
				indexAfterInserting(path,tuple,table);
				indexAfterInserting(newPath, last, table);
				indexAfterDeletion(path,last, table,page);
			} else {
				page.insertElementAt(tuple, indexInPage);
				serialize(path, page);
				//milestone2
				indexAfterInserting(path,tuple,table);

			}

		}

		table.isEmpty = false;

	}
	// bucket [1-10] [10-20] [20-30] ......
	/// [1,3] [5]
	// [[1,2] [3,5]     //[1,3,5] [6,7,8]  /// [1,2,3] [5] [6,7,8]
	public void indexAfterDeletion(String path,Tuple tuple, Table table, Vector<Tuple> page) {
		for(int i=0;i<table.indices.size();i++)
		{// We loop on all the indices in the table.
			Object[] grid = table.indices.get(i);

			Vector<String> gridColNames = table.indicesColNames.get(i);
			Object[] pointer = grid;
			for(int j=0;j<gridColNames.size();j++)
			{// Loop over the grid layers.

				Object value = tuple.data.get(gridColNames.get(j));
				int indexOfRange = table.getIndexOfGrid(value,table.ranges.get(gridColNames.get(j)));
				if(j==gridColNames.size()-1)
				{// Last layer
					Vector<Vector<String>> vectorOfBucket = deserializeBucket((String)(pointer[indexOfRange]));
					Pair[] ranges = table.ranges.get(gridColNames.get(j)); //[1-10] [10-20].......
					Pair range = ranges[indexOfRange];
					int bucketIndex;
					int index = 0;
					for(bucketIndex = 0 ; bucketIndex < vectorOfBucket.size() ; bucketIndex++){
						index = vectorOfBucket.get(bucketIndex).indexOf(path);
						if(index != -1)
							break;
					}


					boolean shouldBeDeleted = true;
					for(int k=0;k<page.size();k++)
					{
						Tuple tupleInPage = page.get(k);
						Object object = tupleInPage.data.get(gridColNames.get(j));
						if(range.withinRange(object)) {
							shouldBeDeleted = false;
							break;
						}
					}
					if(shouldBeDeleted)
					{
						vectorOfBucket = deletePageFromBucket(vectorOfBucket, bucketIndex, index);
						serializeBucket((String)pointer[indexOfRange], vectorOfBucket);
					}
					break;


				}
				pointer=(Object[])pointer[indexOfRange];
			}
		}
	}
	public static Vector<Vector<String>> deletePageFromBucket(Vector<Vector<String>> vectorOfBucket, int bucketIndex, int index) {
		vectorOfBucket.get(bucketIndex).remove(index);
		Vector<String> a5erBucket = vectorOfBucket.get(vectorOfBucket.size()-1);
		try{

			String a5erMasla7a = a5erBucket.get(a5erBucket.size()-1);
			a5erBucket.remove(a5erMasla7a);
			vectorOfBucket.get(bucketIndex).add(a5erMasla7a);
		} catch(ArrayIndexOutOfBoundsException e){

		}
		if(vectorOfBucket.size() > 1 && a5erBucket.size() == 0){
			vectorOfBucket.remove(a5erBucket);
		}
		return vectorOfBucket;
	}
	public static void indexAfterInserting(String pagePath,Tuple tuple, Table table){

		for(int i=0;i<table.indices.size();i++)
		{
			Object[] grid = table.indices.get(i);
			Object[] pointer = grid;
			Vector<String> gridColNames = table.indicesColNames.get(i);
			for(int j=0;j<gridColNames.size();j++) // [id,name]
			{
				Object value = tuple.data.get(gridColNames.get(j));
				int indexOfRange = table.getIndexOfGrid(value,table.ranges.get(gridColNames.get(j)));
				if(j==gridColNames.size()-1)
				{
					Vector<Vector<String>> vectorOfBucket = deserializeBucket((String)(pointer[indexOfRange]));
					boolean pageFound  = false;
					for(int k = 0 ; k < vectorOfBucket.size() ; k++){ // Check if the desired page is in any of the buckets.
						if(vectorOfBucket.get(k).contains(pagePath))
						{
							pageFound = true;
							break;
						}
					}
					if(!pageFound){// if the page is not in any bucket, we add it to a bucket.
						try {
							if(readConfigBuckets() == vectorOfBucket.get(vectorOfBucket.size()-1).size()){
								// The last bucket is full, so we create a new one.
								vectorOfBucket = addNewBucket(vectorOfBucket, pagePath);
							}
							// the last bucket has space so we add the page to it.
							else vectorOfBucket.get(vectorOfBucket.size()-1).add(pagePath);
						} catch (IOException e) {
							e.printStackTrace();
						}

					}

					//check bucket size
					serializeBucket((String)pointer[indexOfRange], vectorOfBucket);
					break;
				}
				pointer=(Object[])pointer[indexOfRange];

			}
		}
	}
	public static String shiftPage(Table table, String path, Tuple last) {
		Vector<Tuple> nextPage = new Vector<Tuple>();
		//get the next page path
		int nextPathIndex = table.pagesPath.indexOf(path) + 1;
		//deserlialize it if it exists
		if(nextPathIndex<table.pagesPath.size())
		{
			nextPage = deserialize(table.pagesPath.get(nextPathIndex));
			//if not full, insert at the first index
			if (nextPage.size() < readConfigRows()) {
				nextPage.insertElementAt(last, 0);
				serialize(table.pagesPath.get(nextPathIndex), nextPage);
				//milestone2
				return table.pagesPath.get(nextPathIndex);
			}

		}
		//if full, create a new page in between
		Vector<Tuple> newPage = new Vector<Tuple>();
		newPage.add(last);
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		long fileName = timestamp.getTime();
		//remember to add timestamp at the first page
		String newPath = table.path + "/page" + fileName;
		table.pagesPath.insertElementAt(newPath,nextPathIndex);
		serialize(table.pagesPath.get(nextPathIndex), newPage);
		//milestone2
		return table.pagesPath.get(nextPathIndex);
	}
	// If the record is found its index will be returned and if not the preceding
	// index will be returned
	public static int searchForPage(Table table, Tuple tuple) throws DBAppException { // [1,5],[9,13]
		int lo = 0, hi = table.pagesPath.size() - 1;
		int middle = (lo + hi) / 2;
		Vector<Tuple> page;
		// Ziad: it tries to access the folder but no page was inserted so it gives another exception type and the method expects a DBAppException
		try
		{
			page = deserialize(table.pagesPath.get(middle));
		}
		catch(Exception E)
		{
			throw new DBAppException("No page is already added");
		}

		Vector<Tuple> nextPage, prevPage;

		while (lo <= hi) {

			if (page.get(page.size() - 1).compareTo(tuple) < 0) { // the inserted is bigger than
																	// the biggest value in the
																	// page
				if (page.size() < readConfigRows()) { // the page is not full

					if (middle != table.pagesPath.size() - 1) { // the page we have is not the last one
						nextPage = deserialize(table.pagesPath.get(middle + 1));
						if (nextPage.get(0).compareTo(tuple) > 0) // the first value in the next
																	// page is smaller than the
																	// inserted value
							return middle;
						else
							lo = middle + 1;
					} else
						return middle;
				} else {
					if (middle != table.pagesPath.size() - 1) {
						nextPage = deserialize(table.pagesPath.get(middle + 1));
						if (nextPage.get(0).compareTo(tuple) > 0)
							return middle + 1;
						else
							lo = middle + 1;
					} else
						return middle + 1;
				}

			} else if (page.get(0).compareTo(tuple) > 0) {
				if (page.size() < readConfigRows()) {
					if (middle != 0) {
						prevPage = deserialize(table.pagesPath.get(middle - 1));
						if (prevPage.get(prevPage.size() - 1).compareTo(tuple) < 0)
							return middle;
						else
							hi = middle - 1;
					} else
						return middle;
				} else {
					hi = middle - 1;
				}

			} else
				return middle;

			middle = (lo + hi) / 2;
			page = deserialize(table.pagesPath.get(middle));
		}

		Vector<Tuple> firstPage = deserialize(table.pagesPath.get(0));
		// Vector<Tuple> lastPage =
		// deserialize(table.pagesPath.get(table.pagesPath.size()-1));

		if (firstPage.get(0).compareTo(tuple) > 0)
			return 0;
		else
			return table.pagesPath.size();
	}
	public static int searchInPage(Vector<Tuple> page, Tuple tuple) {

		if (tuple.compareTo(page.get(0)) < 0)
			return -1;
		if (tuple.compareTo(page.get(page.size() - 1)) > 0)
			return page.size() - 1;

		int lo = 0, hi = page.size() - 1, middle = (hi + lo) / 2;
		while (lo <= hi) {
			if (page.get(middle).compareTo(tuple) < 0)
				lo = middle;
			else if (page.get(middle).compareTo(tuple) == 0)
				return middle;
			else
				hi = middle;

			if (lo + 1 == hi) {
				if (page.get(hi).compareTo(tuple) == 0)
					return hi;
				else
					return lo;
			}
			if (lo == hi)
				return lo;

			middle = (hi + lo) / 2;
		}
		return 0;
	}
	@Override
	public void updateTable(String tableName, String clusteringKeyValue, Hashtable<String, Object> columnNameValue)
			throws DBAppException {

		Table table = tables.get(tableName);
		Tuple tuple = new Tuple();
		tuple.clusteringKey = table.clusteringKey;
		tuple.data = columnNameValue;
		try {
			if (table.clusteringKeyDataType.compareTo("java.lang.Integer") == 0) {
				tuple.data.put(table.clusteringKey, Integer.parseInt(clusteringKeyValue));
			} else if (table.clusteringKeyDataType.compareTo("java.lang.Double") == 0) {
				tuple.data.put(table.clusteringKey, Double.parseDouble(clusteringKeyValue));
			} else if (table.clusteringKeyDataType.compareTo("java.util.Date") == 0) {


				int year = Integer.parseInt(clusteringKeyValue.trim().substring(0, 4));
				int month = Integer.parseInt(clusteringKeyValue.trim().substring(5, 7));
				int day = Integer.parseInt(clusteringKeyValue.trim().substring(8));

				Date dob2 = new Date(year - 1900, month - 1, day);



				tuple.data.put(table.clusteringKey, dob2);
			}
			else
				tuple.data.put(table.clusteringKey, clusteringKeyValue);
		} catch (Exception e) {

			System.out.println("Incompatible primary key data type");
			e.printStackTrace();
			return;
//			throw new DBAppException("Incompatible primary key data type");
		}
		if(!table.isValidTuple(tuple))
		{
			throw new DBAppException("Invalid Tuple");
		}

		Object[] grid = null;
		int dimension = 0;
		for(int i = 0 ; i < table.indices.size() ; i++){
			if(table.indicesColNames.get(i).get(0).equals(table.clusteringKey)){
				grid = table.indices.get(i);
				dimension = table.indicesColNames.get(i).size();
			}
			if(table.indicesColNames.get(i).size() == 1 && table.indicesColNames.get(i).get(0).equals(table.clusteringKey))
			{
				dimension = 1;
				break;
			}
		}

		String clusteringKeyMin = "", clusteringKeyMax = "", clusteringKeyType = "";
		try {
			File myObj = new File("src/main/resources/metadata.csv");
			Scanner myReader = new Scanner(myObj);
			myReader.nextLine();
			while (myReader.hasNextLine()) {
				String line = myReader.nextLine();
				String[] arr = line.split(",");
				if (!arr[0].equals(tableName))
					continue;
				if(arr[1].equals(table.clusteringKey)){
					clusteringKeyMin = arr[5];
					clusteringKeyMax = arr[6];
					clusteringKeyType = arr[2];
				}
			}
			myReader.close();
		} catch (FileNotFoundException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}


		if(grid != null) {
			Pair[] ranges = Table.getRanges(clusteringKeyMin, clusteringKeyMax, clusteringKeyType);
			int index = Table.getIndexOfGrid(tuple.data.get(tuple.clusteringKey), ranges);

			String pagePath = "";
			if (dimension == 1) {
				String bucketPath = (String) grid[index];
				pagePath = searchBucket(bucketPath, tuple, table);
			}
			else
			{
				Object[] toBeSearched = (Object[]) grid[index];
				prepareForSearch();
				pagePath = searchLayers(toBeSearched, (Object) tuple,dimension,table);
			}
			if(pagePath.equals(""))
			{
				System.out.println("This primary key is not present in the table");
				return;
			}
			else
			{
				Vector<Tuple> page = deserialize(pagePath);
				int indexInPage = searchInPage(page,tuple);
				if(indexInPage==-1)
				{
					System.out.println("This primary key is not present in the table");
				}
				else
				{
					Tuple updatedTuple = page.get(indexInPage);
					//milestone 2
					//-------
					page.remove(indexInPage);
					serialize(pagePath,page);
					indexAfterDeletion(pagePath,updatedTuple,table,page);
					page.insertElementAt(updatedTuple,indexInPage);
					//-------
					Set<String> setOfColumns = columnNameValue.keySet();
					for (String key: setOfColumns) {
						//DO we need to check the validity of updated tuple??????
						updatedTuple.data.put(key,columnNameValue.get(key));
					}
					serialize(pagePath, page);
					// milestone 2
					indexAfterInserting(pagePath,updatedTuple,table);
				}
			}
		}
		else
		{
			int pageIndex = searchForPage(table, tuple);
			Vector<Tuple> page;
			int indexInPage;
			if (pageIndex == -1 || pageIndex == table.pagesPath.size()){
				System.out.println("This primary key is not present in the table");
				return;
			}

			String pagePath = table.pagesPath.get(pageIndex);
			page = deserialize(pagePath);

			indexInPage = searchInPage(page, tuple);

			if (indexInPage == -1)
			{
				System.out.println("This primary key is not present in the table");
				return;
			}


			if (tuple.compareTo(page.get(indexInPage)) != 0)
			{
				System.out.println("This primary key is not present in the table");
				return;
			}

			Tuple updatedTuple = page.get(indexInPage);
			//milestone 2
			//-------
			page.remove(indexInPage);
			serialize(pagePath,page);
			indexAfterDeletion(pagePath,updatedTuple,table,page);
			page.insertElementAt(updatedTuple,indexInPage);
			//-------
			Set<String> setOfColumns = columnNameValue.keySet();
			for (String key: setOfColumns) {
				//DO we need to check the validity of updated tuple??????
				updatedTuple.data.put(key,columnNameValue.get(key));
			}
			serialize(table.pagesPath.get(pageIndex), page);
			// milestone 2
			indexAfterInserting(pagePath,updatedTuple,table);
		}

	}
	static String searchLayersResult;
	static String maxBeforeValuePath = "";
	static String minAfterValuePath = "";
	static Object maxBeforeValue = null;
	static Object minAfterValue = null;
	public static void prepareForSearch(){
		searchLayersResult = "";
		maxBeforeValuePath = "";
	 	minAfterValuePath = "";
 		maxBeforeValue = null;
 		minAfterValue = null;
	}
	public static String searchLayers(Object grid, Object value, int dimension, Table table){
		Vector<Tuple> firstPage = deserialize(table.pagesPath.get(0));
		Vector<Tuple> lastPage = deserialize(table.pagesPath.get(table.pagesPath.size()-1));
		Object minValueInTable = firstPage.get(0).data.get(table.clusteringKey);
		Object maxValueInTable = lastPage.get(lastPage.size()-1).data.get(table.clusteringKey);
		if(comparison(value, minValueInTable) < 0)
			return table.pagesPath.get(0);
		if(comparison(value, maxValueInTable) > 0)
			return table.pagesPath.get(table.pagesPath.size()-1);
		Object grid1 = null;
		if(grid instanceof Object[])
			grid1 = (Object[])grid;
		else
			grid1 = (String)grid;
		if(dimension == 1){

			String path = ((String)grid1);
			Vector<Vector<String>> buckets = deserializeBucket(path);
			for(int j = 0 ; j < buckets.size() ; j++)
			{
				for(int k = 0 ; k < buckets.get(j).size() ; k++){

					String pagePath = buckets.get(j).get(k);
					Vector<Tuple> tuples = deserialize(pagePath);

					for(int indexOfTuple = 0 ; indexOfTuple < tuples.size() ; indexOfTuple++)
					{
						Tuple tupleInHand = tuples.get(indexOfTuple);
						Object valueOfTuple = tupleInHand.data.get(tupleInHand.clusteringKey);

						//el tuple howa ely f eedy

						if(comparison(valueOfTuple, value) == 0)
							searchLayersResult = pagePath;

						//el tuple as8ar mn ely f eedy
						if(comparison(valueOfTuple, value) < 0)
						{
							if(maxBeforeValue == null){
								maxBeforeValue = valueOfTuple;
								maxBeforeValuePath = pagePath;
							}

							else if(comparison(valueOfTuple, maxBeforeValue) > 0)
							{
								maxBeforeValue = valueOfTuple;
								maxBeforeValuePath = pagePath;
							}
						}

						//el tuple akbar mn ely f eedy
						else
						{
							if(minAfterValue == null)
							{
								minAfterValue = valueOfTuple;
								minAfterValuePath = pagePath;
							}
							else if(comparison(valueOfTuple, minAfterValue) < 0){
								minAfterValue = valueOfTuple;
								minAfterValuePath = pagePath;
							}
						}
					}
				}
				//}
			}

			searchLayersResult = maxBeforeValuePath;
		}
		else
			for(Object arr : (Object[])grid1)
				searchLayers(arr, value, dimension-1, table);
		return searchLayersResult;
	}
	public String searchBucket(String bucketPath, Tuple tuple, Table table) {
		Vector<Tuple> firstPage = deserialize(table.pagesPath.get(0));
		Vector<Tuple> lastPage = deserialize(table.pagesPath.get(table.pagesPath.size()-1));
		Object minValueInTable = firstPage.get(0).data.get(table.clusteringKey);
		Object maxValueInTable = lastPage.get(lastPage.size()-1).data.get(table.clusteringKey);
		Object value = tuple.data.get(tuple.clusteringKey);
		if(comparison(value, minValueInTable) < 0)
			return table.pagesPath.get(0);
		if(comparison(value, maxValueInTable) > 0)
			return table.pagesPath.get(table.pagesPath.size()-1);
		Object maxBeforeValue = null;
		Object minAfterValue = null;
		String maxBeforeValuePath = "";
		String minAfterValuePath = "";

		Vector<Vector<String>> buckets = deserializeBucket(bucketPath);


		for(int i = 0 ; i < buckets.size() ; i++)
		{
			//if(buckets.get(i).size() == 0)
				for(int j = 0 ; j < buckets.get(i).size() ; j++)
				{
					//System.out.println(buckets.get(i).size());
					Vector<Tuple> page = deserialize(buckets.get(i).get(j));
					String pagePath = buckets.get(i).get(j);
					for(int k = 0 ; k < page.size() ; k++)
					{
						Tuple tupleInHand = page.get(k);
						Object valueOfTuple = tupleInHand.data.get(tupleInHand.clusteringKey);

						//el tuple howa ely f eedy
						if(comparison(valueOfTuple, value) == 0){

							return pagePath;
						}


						//el tuple as8ar mn ely f eedy
						//System.out.println(valueOfTuple + " " + value + " ff");
						//System.out.println(valueOfTuple + " " + maxBeforeValue + "gg");
						if(maxBeforeValue != null)
							System.out.println(comparison(valueOfTuple, maxBeforeValue) > 0);
						if(comparison(valueOfTuple, value) < 0)
						{

							if(maxBeforeValue == null){
								maxBeforeValue = valueOfTuple;
								maxBeforeValuePath = pagePath;

							}

							else if(comparison(valueOfTuple, maxBeforeValue) > 0)
							{
								maxBeforeValue = valueOfTuple;
								maxBeforeValuePath = pagePath;
							}
						}

						//el tuple akbar mn ely f eedy
						else
						{
							if(minAfterValue == null)
							{

								minAfterValue = valueOfTuple;
								minAfterValuePath = pagePath;
							}
							else if(comparison(valueOfTuple, minAfterValue) < 0){
								minAfterValue = valueOfTuple;
								minAfterValuePath = pagePath;
							}
						}
					}
				}

		}

//		System.out.println(table);
//		System.out.println(maxBeforeValuePath + " dkdk");
//		System.out.println(tuple);
		return maxBeforeValuePath;
	}
	@Override
	public void deleteFromTable(String tableName, Hashtable<String, Object> columnNameValue) throws DBAppException {
		// id, 7
		// name, "x"
		Table table = tables.get(tableName);
		String pagePath;
		// we have two cases
		// first case: the primary key exists so we will search by the primary key ( binary search)  and we will get the only one value and we will delete it
		if(columnNameValue.get(table.clusteringKey)!= null)
		{
			Tuple tuple = new Tuple();
			tuple.clusteringKey=table.clusteringKey;
			tuple.data.put(table.clusteringKey, columnNameValue.get(table.clusteringKey)); // adding the value of the primary key to the tuple
			Object[] grid = null;
			int dimension = 0;
			for(int i = 0 ; i < table.indices.size() ; i++){
				if(table.indicesColNames.get(i).get(0).equals(table.clusteringKey)){
					grid = table.indices.get(i);
					dimension = table.indicesColNames.get(i).size();
				}
				if(table.indicesColNames.get(i).size() == 1 && table.indicesColNames.get(i).get(0).equals(table.clusteringKey))
				{
					dimension = 1;
					break;
				}
			}

			String clusteringKeyMin = "", clusteringKeyMax = "", clusteringKeyType = "";
			try {
				File myObj = new File("src/main/resources/metadata.csv");
				Scanner myReader = new Scanner(myObj);
				myReader.nextLine();
				while (myReader.hasNextLine()) {
					String line = myReader.nextLine();
					String[] arr = line.split(",");
					if (!arr[0].equals(tableName))
						continue;
					if(arr[1].equals(table.clusteringKey)){
						clusteringKeyMin = arr[5];
						clusteringKeyMax = arr[6];
						clusteringKeyType = arr[2];
					}
				}
				myReader.close();
			} catch (FileNotFoundException e) {
				System.out.println("An error occurred.");
				e.printStackTrace();
			}

			if(grid != null) {
			Pair[] ranges = Table.getRanges(clusteringKeyMin, clusteringKeyMax, clusteringKeyType);
			int index = Table.getIndexOfGrid(tuple.data.get(tuple.clusteringKey), ranges);

			pagePath = "";
			if (dimension == 1) {
				String bucketPath = (String) grid[index];
				pagePath = searchBucket(bucketPath, tuple, table);
			}
			else
			{
				Object[] toBeSearched = (Object[]) grid[index];
				prepareForSearch();
				pagePath = searchLayers(toBeSearched, (Object) tuple,dimension,table);
			}
			if(pagePath.equals(""))
			{
				System.out.println("This primary key is not present in the table");
				return;
			}
			else
			{
				Vector<Tuple> page = deserialize(pagePath);
				int indexInPage = searchInPage(page,tuple);
				Tuple removedTuple = page.remove(indexInPage);
				serialize(pagePath, page);
				indexAfterDeletion(pagePath,removedTuple,table,page);
				// check if the page is empty to remove it and then deseralize
				if(page.isEmpty())
				{
					table.pagesPath.remove(pagePath);
					Path path = FileSystems.getDefault().getPath(pagePath); //delete the file from disk
					try {
						Files.delete(path);
					} catch (NoSuchFileException x) {
						System.err.format("%s: no such" + " file or directory%n", path);
					} catch (IOException x) {
						System.err.println(x);
					}
				}
			}
		}
		else
		{
			Vector<Tuple> page;
			int pageIndex = searchForPage(table, tuple);  // searching for the index
			try{
				pagePath= table.pagesPath.get(pageIndex);
			}
			catch (ArrayIndexOutOfBoundsException exception)
			{
				System.out.println("This primary key is not present in the table");
				return;
			}
			page = deserialize(pagePath);
			int indexInPage = searchInPage(page, tuple);

			// if index is found then delete and run the method to check if the page is empty to delete it / else : return
			try {
				if (tuple.compareTo(page.get(indexInPage)) == 0) //found
				{
					for(String key: columnNameValue.keySet())
					{

						if(!page.get(indexInPage).data.get(key).equals(columnNameValue.get(key))){
							System.out.println("Primary key is found but the record is not");
							return;
						}


					}

					Tuple removedTuple = page.remove(indexInPage);
					serialize(pagePath, page);
					indexAfterDeletion(pagePath,removedTuple,table,page);
					// check if the page is empty to remove it and then deseralize
					if(page.isEmpty())
					{
						table.pagesPath.remove(pageIndex);
						Path path = FileSystems.getDefault().getPath(pagePath); //delete the file from disk
						try {
							Files.delete(path);
						} catch (NoSuchFileException x) {
							System.err.format("%s: no such" + " file or directory%n", path);
						} catch (IOException x) {
							System.err.println(x);
						}
					}

				}
				else // not found
				{
					System.out.println("This primary key is not present in the table");
					return;
				}
			} catch (ArrayIndexOutOfBoundsException e) {
				System.out.println("This primary key is not present in the table");
				return;
			}
			catch (NullPointerException e)
			{
				throw new DBAppException("The column name is invalid");
			}
			// not ended yet still we want to check for the tuple values so we need to implement the tuple as a hashtable to enhance the efficiency

		}

		}
		else // I search by other values (not the primary key)
		{
			SQLTerm[] sqlTerms = makeMySqlTerms(tableName,columnNameValue); // generate SqlTerms for the hashtable
			Vector<String> vectorArrayOperators = new Vector<>();
			for(int i=1;i<sqlTerms.length;i++)
			{
				vectorArrayOperators.add("AND");
			}
			String[] arrayOperators = vectorArrayOperators.toArray(new String[vectorArrayOperators.size()]);

			Vector<Object[]> tableIndicies= table.indices;
			Vector<Vector<String>> indiciesColNames = table.indicesColNames;

			//Collect all the column names in the select statement
			Vector<String> sqlColNames = new Vector<>();
			for(int i=0; i<sqlTerms.length; i++)
				sqlColNames.add(sqlTerms[i]._strColumnName);//[gpa,age]

			Vector<Tuple> result = new Vector<>();//the final result, tuples that will be in the iterator
			int maxColumnsMatch = 0; //the maximum number of columns in an index that match with the select statement
			Object[] maxIndex = null; //the index that has the maximum number of column match with the select
			Vector<String> maxIndexColumnsNames = null;
			//iterate over all the column names of the indicies to get the index that has the max columns match
			for(int i=0; i<indiciesColNames.size(); i++)
			{
				Vector<String> indexColumnsNames = indiciesColNames.get(i);//[first_name,gpa]
				int columnsMatch = 0;
				//iterate on the columns of the select statements to check how many match with this index
				for(int j=0; j<sqlTerms.length; j++)
				{
					if(indexColumnsNames.contains(sqlColNames.get(j)))
						columnsMatch++;
				}
				if(columnsMatch>maxColumnsMatch)
				{
					maxColumnsMatch=columnsMatch;
					maxIndex=tableIndicies.get(i);
					maxIndexColumnsNames = indexColumnsNames;
				}
			}
			if(maxIndex==null)//No indices matches so we will loop all the table (old code)
			{
				for (int i = 0; i < table.pagesPath.size(); i++) { //looping on the pages using i
					pagePath = table.pagesPath.get(i);
					Vector<Tuple> page = deserialize(table.pagesPath.get(i));
					for (int j = 0; j < page.size(); j++) { //looping on the tuples using j
						boolean flag = true;
						for (String key : columnNameValue.keySet()) {

							if (!page.get(j).data.get(key).equals(columnNameValue.get(key))) {
								flag = false;
								break;
							}
						}
						if (flag) {
							Tuple removedTuple = page.get(j);
							page.remove(page.get(j));
							serialize(pagePath, page);
							j--;
							//milestone 2
							indexAfterDeletion(pagePath, removedTuple, table, page);
							// check if the page is empty to remove it and then deseralize
							if (page.isEmpty()) {
								table.pagesPath.remove(i);
								i--;
								Path path = FileSystems.getDefault().getPath(pagePath); //delete the file from disk
								try {
									Files.delete(path);
								} catch (NoSuchFileException x) {
									System.err.format("%s: no such" + " file or directory%n", path);
								} catch (IOException x) {
									System.err.println(x);
								}
							}
						}

					}
				}
			}
			else// the new code of using the index
			{
				HashSet<String> filteredBuckets= getAllFilteredBuckets(maxIndexColumnsNames,sqlTerms,arrayOperators,maxIndex,table);
				HashSet<String> pages = new HashSet<>();
				for(String s : filteredBuckets) //looping on the vector of buckets
				{
					Vector<Vector<String>> vectorOfBucket= deserializeBucket(s);
					for(int j=0;j<vectorOfBucket.size();j++) // looping on every bucket on the vector of buckets
					{
						Vector<String> bucket = vectorOfBucket.get(j);
						for(int k=0;k<bucket.size();k++) //looping on every page
						{
							pagePath= bucket.get(k);
							Vector<Tuple> page = deserialize(bucket.get(k)); //now I have the page in my hand
							if(!pages.add(pagePath)) // I looped on the page before
							{
								continue;
							}
							for(int l=0; l< page.size();l++)
							{
								Tuple tupleInHand = page.get(l);
								//check which conditions in the query are satisfied on tupleInHand
								Vector<Boolean> conditionsSatisfied = new Vector();
								for(int m=0; m<sqlTerms.length; m++)
								{
									String columnName = sqlTerms[m]._strColumnName;//column name of the condition
									Object value= sqlTerms[m]._objValue;//the value in the query
									conditionsSatisfied.add(comparisonForSelect(tupleInHand.data.get(columnName),value)==0);
								}
								//I finished checking. If conditions and operators result to true, add it to result
								if(conditionsResult(conditionsSatisfied,arrayOperators)) {
									Tuple removedTuple = page.get(l);
									page.remove(page.get(l));
									l--;
									serialize(pagePath, page);
									//milestone 2
									indexAfterDeletion(pagePath, removedTuple, table, page);
									// check if the page is empty to remove it and then deseralize
									if (page.isEmpty()) {
										table.pagesPath.remove(page);
										Path path = FileSystems.getDefault().getPath(pagePath); //delete the file from disk
										try {
											Files.delete(path);
										} catch (NoSuchFileException x) {
											System.err.format("%s: no such" + " file or directory%n", path);
										} catch (IOException x) {
											System.err.println(x);
										}
										}
									}
								}
						}
					}
				}

			}



		}

	}
	public SQLTerm[] makeMySqlTerms(String tableName, Hashtable<String, Object> columnNameValue) {
		Vector<SQLTerm> v = new Vector<>();
		for(String key: columnNameValue.keySet())
		{
			SQLTerm sqlTerm = new SQLTerm();
			sqlTerm._strTableName= tableName;
			sqlTerm._strColumnName=key;
			sqlTerm._strOperator="=";
			sqlTerm._objValue=columnNameValue.get(key);
			v.add(sqlTerm);
		}

		SQLTerm[] sqlTerms = v.toArray(new SQLTerm[v.size()]);
		return sqlTerms;
	}
	public static void checkValidInput(SQLTerm[] sqlTerms, String [] arrayOperators) throws DBAppException {

		//check that number of operators is less than the number of conditions by exactly 1
		if(sqlTerms.length-arrayOperators.length!=1)
			throw new DBAppException("Incompatible number of sqlTerms and operators");
		String tableName = sqlTerms[0]._strTableName;
		Table table = tables.get(tableName);

		//store all columns names in a vector
		Hashtable<String,String> colNames_Types = new Hashtable<>();
		try {
			File myObj = new File("src/main/resources/metadata.csv");
			Scanner myReader = new Scanner(myObj);
			myReader.nextLine();
			while (myReader.hasNextLine()) {
				String line = myReader.nextLine();
				String[] arr = line.split(",");
				colNames_Types.put(arr[1],arr[2]);
			}
			myReader.close();


		} catch (FileNotFoundException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
		for(int i=0; i<sqlTerms.length; i++){
			SQLTerm sqlInHand = sqlTerms[i];
			//check that all sql terms have same table name
			if(!sqlInHand._strTableName.equals(tableName))
				throw new DBAppException("Table Name must be the same in all sqlterms");
			//check that all column names are valid column names that exist in the table
			if(!colNames_Types.containsKey(sqlInHand._strColumnName))
				throw new DBAppException("There is no column in the table with the name " + sqlInHand._strColumnName);
			//check that all operators are valid
			if(!sqlInHand._strOperator.equals(">") && !sqlInHand._strOperator.equals(">=") &&
					!sqlInHand._strOperator.equals("<") && !sqlInHand._strOperator.equals("<=") &&
					!sqlInHand._strOperator.equals("=") && !sqlInHand._strOperator.equals("!="))
				throw new DBAppException("Invalid operator: " + sqlInHand._strOperator);
			String colType = colNames_Types.get(sqlInHand._strColumnName);
			//check that the value entered has same type as the column
			if(!("class " + colType).equals(sqlInHand._objValue.getClass().toString()))
			{
				if(!(colType.equals("java.lang.Double") && sqlInHand._objValue.getClass().toString().equals("class java.lang.Integer")))
					throw new DBAppException("Invalid column type --> column name: " + sqlInHand._strColumnName + " | column Type: "
							+ colType + " | type entered: " + sqlInHand._objValue.getClass().toString());
			}
		}

		//check that all operators are valid
		for(int i=0; i<arrayOperators.length; i++)
		{
			if(!arrayOperators[i].equals("AND") && !arrayOperators[i].equals("OR") && !arrayOperators[i].equals("XOR"))
				throw new DBAppException("Invalid operator: " + arrayOperators[i]);
		}

	}
	@Override
	public Iterator <?> selectFromTable(SQLTerm[] sqlTerms, String[] arrayOperators) throws DBAppException {
		// TODO Auto-generated method stub

		//make sure that sqlTerms not empty
		if(sqlTerms.length==0)
			throw new DBAppException("No SQL Terms and no table entered to select from!");

		checkValidInput(sqlTerms,arrayOperators);

		//get the table on which you execute select
		Table table = tables.get(sqlTerms[0]._strTableName);
		Vector<Object[]> tableIndicies= table.indices;
		Vector<Vector<String>> indiciesColNames = table.indicesColNames;

		//Very special case: All of sql terms are on the primary key
		//All sql terms have or between them
		//Do not need an index, just do binary searches

		boolean allPrimary = true;
		for(int i=0; i<sqlTerms.length; i++)
		{
			if(!sqlTerms[i]._strColumnName.equals(table.clusteringKey))
			{
				allPrimary=false;
				break;
			}
		}
		if(allPrimary)//all sql terms are on primary key
		{
			HashSet<Tuple> resultSpecialCase = new HashSet<>();
			Vector<Object> resultPrimaryKeyValue = new Vector<>();//to compare because resultSpecialCase sees same tuples as different ones
			for(int i=0; i<sqlTerms.length;i++)
			{
				SQLTerm primaryKeyCondition = sqlTerms[i];
				Tuple tuple = new Tuple();//temporary tuple containing only primary key to search
				tuple.clusteringKey=table.clusteringKey;
				Vector<Tuple> pagePrimaryKey;
				tuple.data.put(table.clusteringKey, primaryKeyCondition._objValue); // adding the value of the primary key to the tuple
				//search for this tuple
				int pageIndexInTable = searchForPage(table, tuple);  // searching for the index of the page
				if(pageIndexInTable==-1)
					pageIndexInTable=0;
				String pagePrimaryKeyPath= table.pagesPath.get(pageIndexInTable);
				pagePrimaryKey = deserialize(pagePrimaryKeyPath); //page containing the primary key
				int indexInPage = searchInPage(pagePrimaryKey, tuple); //the index of the required tuple in the page
				if(indexInPage==-1)
					indexInPage=0;
				Tuple primaryKeyTuple = pagePrimaryKey.get(indexInPage);//the tuple containing the primary key
				//Now, apply condition on only the primary key
				Object primaryKeyValue = primaryKeyCondition._objValue;
				//primary key column = table.clusteringkey
				loopALLPrimaryKey(primaryKeyCondition,table,pageIndexInTable,indexInPage,sqlTerms,resultSpecialCase,arrayOperators,resultPrimaryKeyValue);
			}
			return resultSpecialCase.iterator();

		}
		else // Not all of the sql terms are primary
		{
			//Collect all the column names in the select statement
			Vector<String> sqlColNames = new Vector<>();
			for(int i=0; i<sqlTerms.length; i++)
				sqlColNames.add(sqlTerms[i]._strColumnName);//[gpa,age]

			HashSet<Tuple> result = new HashSet<>();//the final result, tuples that will be in the iterator
			int maxColumnsMatch = 0; //the maximum number of columns in an index that match with the select statement
			Object[] maxIndex = null; //the index that has the maximum number of column match with the select
			Vector<String> maxIndexColumnsNames = null;
			//iterate over all the column names of the indicies to get the index that has the max columns match
			for(int i=0; i<indiciesColNames.size(); i++)
			{
				Vector<String> indexColumnsNames = indiciesColNames.get(i);//[first_name,gpa]
				int columnsMatch = 0;
				//iterate on the columns of the select statements to check how many match with this index
				for(int j=0; j<sqlTerms.length; j++)
				{
					if(indexColumnsNames.contains(sqlColNames.get(j)))
						columnsMatch++;
				}
				if(columnsMatch>maxColumnsMatch)
				{
					maxColumnsMatch=columnsMatch;
					maxIndex=tableIndicies.get(i);
					maxIndexColumnsNames = indexColumnsNames;
				}
			}

			if(maxIndex==null)//the table had no indicies that have columns matching any of those in the select
			{
				//select directly from table
				// first case: the primary key exists so we will search by the primary key ( binary search)  and we will get the only one value and we will select it
				if(sqlColNames.contains(table.clusteringKey))
				{
					boolean existsOR_XOR = false;
					for(int i=0; i<arrayOperators.length; i++)
						if(arrayOperators[i].equals("OR") || arrayOperators[i].equals("XOR"))
						{
							existsOR_XOR=true;break;
						}
					if (!existsOR_XOR) {//do binary search
						//get the sqlTerm that has the primary key in order to have access to its value
						SQLTerm primaryKeyCondition = null;
						for(int i=0; i<sqlTerms.length; i++) {
							if (sqlTerms[i]._strColumnName.equals(table.clusteringKey)) {
								primaryKeyCondition=sqlTerms[i];
								break;
							}
						}
						Tuple tuple = new Tuple();//temporary tuple containing only primary key to search
						tuple.clusteringKey=table.clusteringKey;
						Vector<Tuple> pagePrimaryKey;
						tuple.data.put(table.clusteringKey, primaryKeyCondition._objValue); // adding the value of the primary key to the tuple
						//search for this tuple
						int pageIndexInTable = searchForPage(table, tuple);  // searching for the index of the page
						String pagePrimaryKeyPath= table.pagesPath.get(pageIndexInTable);
						pagePrimaryKey = deserialize(pagePrimaryKeyPath); //page containing the primary key
						int indexInPage = searchInPage(pagePrimaryKey, tuple); //the index of the required tuple in the page
						Tuple primaryKeyTuple = pagePrimaryKey.get(indexInPage);//the tuple containing the primary key
						//Now, apply condition on only the primary key
						Object primaryKeyValue = primaryKeyCondition._objValue;
						//primary key column = table.clusteringkey
						loopPrimaryKey(primaryKeyCondition,table,pageIndexInTable,pagePrimaryKey,indexInPage,sqlTerms,result,primaryKeyTuple);
					}
					else //OR and XOR exist, loop on all the table
					{
						result = queryAllTheTable(table,sqlTerms,arrayOperators);
					}

				}
				else //second case: the primary key not included so will search in the whole table
				{
					result = queryAllTheTable(table,sqlTerms,arrayOperators);
				}
			}
			else //the table contained an index that has at least one column that matches with the query conditions
			{
				HashSet<String> filteredBuckets= getAllFilteredBuckets(maxIndexColumnsNames,sqlTerms,arrayOperators,maxIndex,table);
				//loop on all buckets and get your tuples using queryAllBuckets
				result = queryAllBuckets(table,sqlTerms,arrayOperators,filteredBuckets);
			}
			Vector<Tuple> finalResult = new Vector<>();
			for(Tuple tupleInHand : result)
			{
				//check which conditions in the query are satisfied on tupleInHand
				Vector<Boolean> conditionsSatisfied = new Vector();
				for(int k=0; k<sqlTerms.length; k++)
				{
					String columnName = sqlTerms[k]._strColumnName;//column name of the condition
					Object value= sqlTerms[k]._objValue;//the value in the query
					switch(sqlTerms[k]._strOperator)
					{
						case ">":
							conditionsSatisfied.add(comparisonForSelect(tupleInHand.data.get(columnName),value)>0);
							break;
						case ">=":
							conditionsSatisfied.add(comparisonForSelect(tupleInHand.data.get(columnName),value)>=0);
							break;
						case "<":
							conditionsSatisfied.add(comparisonForSelect(tupleInHand.data.get(columnName),value)<0);
							break;
						case "<=":
							conditionsSatisfied.add(comparisonForSelect(tupleInHand.data.get(columnName),value)<=0);
							break;
						case "!=":
							conditionsSatisfied.add(comparisonForSelect(tupleInHand.data.get(columnName),value)!=0);
							break;
						case "=":
							conditionsSatisfied.add(comparisonForSelect(tupleInHand.data.get(columnName),value)==0);
							break;
						default:
							System.out.println("Operator not valid");
					}

				}
				//I finished checking. If conditions and operators result to true, add it to result

				if(conditionsResult(conditionsSatisfied,arrayOperators))
					finalResult.add(tupleInHand);

			}


			// we will do something to convert from vector to Iterator
			return finalResult.iterator();
		}
	}
	public static void loopALLPrimaryKey(SQLTerm primaryKeyCondition, Table table, int pageIndexInTable, int indexInPage, SQLTerm[] sqlTerms, HashSet<Tuple> result, String[] arrayOperators, Vector<Object> resultPrimaryKeyValue ){
		Vector<Tuple> pagePrimaryKey = deserialize(table.pagesPath.get(pageIndexInTable));
		Tuple primaryKeyTuple = pagePrimaryKey.get(indexInPage);
		switch (primaryKeyCondition._strOperator)
		{
			case ">":
				//Will check all the conditions on all tuples after this primary key tuple
				//loop on the table beginning from the tuple after the primary key
				for(int i=pageIndexInTable; i<table.pagesPath.size();i++)
				{
					Vector <Tuple> page = deserialize(table.pagesPath.get(i));
					//loop on each page
					for(int j=0; j<page.size();j++)
					{
						//if this is the primary key page, begin from the primary key index
						if(page==pagePrimaryKey)
							j=indexInPage+1;//begin from the first tuple after the primary key that I have
						//if primary key is the last element in page, .get(j+1) will cause an exception, go next page
						Tuple tupleInHand = null;
						try{
							tupleInHand = page.get(j);
						}
						catch (IndexOutOfBoundsException e)
						{
							break;
						}
						if(!resultPrimaryKeyValue.contains(tupleInHand.data.get(table.clusteringKey)))
						{
							//check that all conditions in the query are satisfied on tupleInHand
							Vector<Boolean> conditionsSatisfied = new Vector<>();
							for(int k=0; k<sqlTerms.length; k++)
							{
								String columnName = sqlTerms[k]._strColumnName;
								Object value= sqlTerms[k]._objValue;//the value in the query
								switch(sqlTerms[k]._strOperator)
								{
									case ">":
										conditionsSatisfied.add(comparisonForSelect(tupleInHand.data.get(columnName),value)>0);
										break;
									case ">=":
										conditionsSatisfied.add(comparisonForSelect(tupleInHand.data.get(columnName),value)>=0);
										break;
									case "<":
										conditionsSatisfied.add(comparisonForSelect(tupleInHand.data.get(columnName),value)<0);
										break;
									case "<=":
										conditionsSatisfied.add(comparisonForSelect(tupleInHand.data.get(columnName),value)<=0);
										break;
									case "!=":
										conditionsSatisfied.add(comparisonForSelect(tupleInHand.data.get(columnName),value)!=0);
										break;
									case "=":
										conditionsSatisfied.add(comparisonForSelect(tupleInHand.data.get(columnName),value)==0);
										break;
									default:
										System.out.println("Operator not valid");
								}
							}
							//I finished checking. If all conditions satisfied, add it to result
							if(conditionsResult(conditionsSatisfied,arrayOperators))
							{
								result.add(tupleInHand);
								resultPrimaryKeyValue.add(tupleInHand.data.get(table.clusteringKey));
							}
						}

					}
				}
				break;
			case ">=":
				//Will check all the conditions on all tuples on this primary key tuple and after this primary key tuple
				//loop on table from primary key till the end
				for(int i=pageIndexInTable; i<table.pagesPath.size();i++)
				{
					String pagePath= table.pagesPath.get(i);
					Vector <Tuple> page = deserialize(table.pagesPath.get(i));
					//loop on each page
					for(int j=0; j<page.size();j++)
					{
						//if this is the primary key page, begin from the primary key index
						if(page==pagePrimaryKey)
							j=indexInPage;//begin from the the primary key tuple that I have
						Tuple tupleInHand = page.get(j);
						if(!resultPrimaryKeyValue.contains(tupleInHand.data.get(table.clusteringKey)))
						{
							//check that all conditions in the query are satisfied on tupleInHand
							Vector<Boolean> conditionsSatisfied = new Vector<>();
							for(int k=0; k<sqlTerms.length; k++)
							{
								String columnName = sqlTerms[k]._strColumnName;
								Object value= sqlTerms[k]._objValue;//the value in the query
								switch(sqlTerms[k]._strOperator)
								{
									case ">":
										conditionsSatisfied.add(comparisonForSelect(tupleInHand.data.get(columnName),value)>0);
										break;
									case ">=":
										conditionsSatisfied.add(comparisonForSelect(tupleInHand.data.get(columnName),value)>=0);
										break;
									case "<":
										conditionsSatisfied.add(comparisonForSelect(tupleInHand.data.get(columnName),value)<0);
										break;
									case "<=":
										conditionsSatisfied.add(comparisonForSelect(tupleInHand.data.get(columnName),value)<=0);
										break;
									case "!=":
										conditionsSatisfied.add(comparisonForSelect(tupleInHand.data.get(columnName),value)!=0);
										break;
									case "=":
										conditionsSatisfied.add(comparisonForSelect(tupleInHand.data.get(columnName),value)==0);
										break;
									default:
										System.out.println("Operator not valid");
								}
							}
							//I finished checking. If all conditions satisfied, add it to result
							if(conditionsResult(conditionsSatisfied,arrayOperators))
							{
								result.add(tupleInHand);
								resultPrimaryKeyValue.add(tupleInHand.data.get(table.clusteringKey));
							}
						}

					}
				}
				break;
			case "<":
				//Will check all the conditions on all tuples before this primary key tuple
				//loop on the table from beginning till the page that contains the primary key tuple
				for(int i=0; i<=pageIndexInTable;i++)
				{
					String pagePath= table.pagesPath.get(i);
					Vector <Tuple> page = deserialize(table.pagesPath.get(i));
					//loop on each page
					for(int j=0; j<page.size();j++)
					{
						Tuple tupleInHand = page.get(j);
						//if I reached the primary key tuple, do not add it and break
						if(tupleInHand.data.get(table.clusteringKey)==primaryKeyTuple.data.get(table.clusteringKey))
							break;
						if(!resultPrimaryKeyValue.contains(tupleInHand.data.get(table.clusteringKey)))
						{
							//check that all conditions in the query are satisfied on tupleInHand
							Vector<Boolean> conditionsSatisfied = new Vector<>();
							for(int k=0; k<sqlTerms.length; k++)
							{
								String columnName = sqlTerms[k]._strColumnName;
								Object value= sqlTerms[k]._objValue;//the value in the query
								switch(sqlTerms[k]._strOperator)
								{
									case ">":
										conditionsSatisfied.add(comparisonForSelect(tupleInHand.data.get(columnName),value)>0);
										break;
									case ">=":
										conditionsSatisfied.add(comparisonForSelect(tupleInHand.data.get(columnName),value)>=0);
										break;
									case "<":
										conditionsSatisfied.add(comparisonForSelect(tupleInHand.data.get(columnName),value)<0);
										break;
									case "<=":
										conditionsSatisfied.add(comparisonForSelect(tupleInHand.data.get(columnName),value)<=0);
										break;
									case "!=":
										conditionsSatisfied.add(comparisonForSelect(tupleInHand.data.get(columnName),value)!=0);
										break;
									case "=":
										conditionsSatisfied.add(comparisonForSelect(tupleInHand.data.get(columnName),value)==0);
										break;
									default:
										System.out.println("Operator not valid");
								}
							}
							//I finished checking. If all conditions satisfied, add it to result
							if(conditionsResult(conditionsSatisfied,arrayOperators))
							{
								result.add(tupleInHand);
								resultPrimaryKeyValue.add(tupleInHand.data.get(table.clusteringKey));
							}
						}

					}
				}
				break;
			case "<=":
				//Will check all the conditions on this primary key tuple and before this primary key tuple
				for(int i=0; i<=pageIndexInTable;i++)
				{
					String pagePath= table.pagesPath.get(i);
					Vector <Tuple> page = deserialize(table.pagesPath.get(i));
					//loop on each page
					for(int j=0; j<page.size();j++)
					{
						Tuple tupleInHand = page.get(j);
						if(!resultPrimaryKeyValue.contains(tupleInHand.data.get(table.clusteringKey)))
						{
							//check that all conditions in the query are satisfied on tupleInHand
							Vector<Boolean> conditionsSatisfied = new Vector<>();
							for(int k=0; k<sqlTerms.length; k++)
							{
								String columnName = sqlTerms[k]._strColumnName;
								Object value= sqlTerms[k]._objValue;//the value in the query
								switch(sqlTerms[k]._strOperator)
								{
									case ">":
										conditionsSatisfied.add(comparisonForSelect(tupleInHand.data.get(columnName),value)>0);
										break;
									case ">=":
										conditionsSatisfied.add(comparisonForSelect(tupleInHand.data.get(columnName),value)>=0);
										break;
									case "<":
										conditionsSatisfied.add(comparisonForSelect(tupleInHand.data.get(columnName),value)<0);
										break;
									case "<=":
										conditionsSatisfied.add(comparisonForSelect(tupleInHand.data.get(columnName),value)<=0);
										break;
									case "!=":
										conditionsSatisfied.add(comparisonForSelect(tupleInHand.data.get(columnName),value)!=0);
										break;
									case "=":
										conditionsSatisfied.add(comparisonForSelect(tupleInHand.data.get(columnName),value)==0);
										break;
									default:
										System.out.println("Operator not valid");
								}
							}
							//I finished checking. If all conditions satisfied, add it to result
							if(conditionsResult(conditionsSatisfied,arrayOperators))
							{
								result.add(tupleInHand);
								resultPrimaryKeyValue.add(tupleInHand.data.get(table.clusteringKey));
							}
						}

						//if I reached the primary key tuple, checked conditions on it, break
						if(tupleInHand.data.get(table.clusteringKey)==primaryKeyTuple.data.get(table.clusteringKey))
							break;
					}
				}
				break;
			case "!=":
				//Will check all conditions on all tuples except this primary key tuple
				//loop on the table
				for(int i=0; i<table.pagesPath.size();i++)
				{
					String pagePath= table.pagesPath.get(i);
					Vector <Tuple> page = deserialize(table.pagesPath.get(i));
					//loop on each page
					for(int j=0; j<page.size();j++)
					{
						Tuple tupleInHand = page.get(j);
						if(tupleInHand.data.get(table.clusteringKey)==primaryKeyTuple.data.get(table.clusteringKey))
							continue;
						if(!resultPrimaryKeyValue.contains(tupleInHand.data.get(table.clusteringKey)))
						{
							//check that all conditions in the query are satisfied on tupleInHand
							Vector<Boolean> conditionsSatisfied = new Vector<>();
							for(int k=0; k<sqlTerms.length; k++)
							{
								String columnName = sqlTerms[k]._strColumnName;
								Object value= sqlTerms[k]._objValue;//the value in the query
								switch(sqlTerms[k]._strOperator)
								{
									case ">":
										conditionsSatisfied.add(comparisonForSelect(tupleInHand.data.get(columnName),value)>0);
										break;
									case ">=":
										conditionsSatisfied.add(comparisonForSelect(tupleInHand.data.get(columnName),value)>=0);
										break;
									case "<":
										conditionsSatisfied.add(comparisonForSelect(tupleInHand.data.get(columnName),value)<0);
										break;
									case "<=":
										conditionsSatisfied.add(comparisonForSelect(tupleInHand.data.get(columnName),value)<=0);
										break;
									case "!=":
										conditionsSatisfied.add(comparisonForSelect(tupleInHand.data.get(columnName),value)!=0);
										break;
									case "=":
										conditionsSatisfied.add(comparisonForSelect(tupleInHand.data.get(columnName),value)==0);
										break;
									default:
										System.out.println("Operator not valid");
								}
							}
							//I finished checking. If all conditions satisfied, add it to result
							if(conditionsResult(conditionsSatisfied,arrayOperators))
							{
								result.add(tupleInHand);
								resultPrimaryKeyValue.add(tupleInHand.data.get(table.clusteringKey));
							}
						}
					}
				}
				break;
			case "=":
				//Will check all conditions on only this primary key tuple
				Vector<Boolean> conditionsSatisfied = new Vector<>();
				if(!resultPrimaryKeyValue.contains(primaryKeyTuple.data.get(table.clusteringKey)))
				{
					for(int k=0; k<sqlTerms.length; k++)
					{
						String columnName = sqlTerms[k]._strColumnName;
						Object value= sqlTerms[k]._objValue;//the value in the query
						switch(sqlTerms[k]._strOperator)
						{
							case ">":
								conditionsSatisfied.add(comparisonForSelect(primaryKeyTuple.data.get(columnName),value)>0);
								break;
							case ">=":
								conditionsSatisfied.add(comparisonForSelect(primaryKeyTuple.data.get(columnName),value)>=0);
								break;
							case "<":
								conditionsSatisfied.add(comparisonForSelect(primaryKeyTuple.data.get(columnName),value)<0);
								break;
							case "<=":
								conditionsSatisfied.add(comparisonForSelect(primaryKeyTuple.data.get(columnName),value)<=0);
								break;
							case "!=":
								conditionsSatisfied.add(comparisonForSelect(primaryKeyTuple.data.get(columnName),value)!=0);
								break;
							case "=":
								conditionsSatisfied.add(comparisonForSelect(primaryKeyTuple.data.get(columnName),value)==0);
								break;
							default:
								System.out.println("Operator not valid");
						}
					}
					//I finished checking. If all conditions satisfied, add it to result
					if(conditionsResult(conditionsSatisfied,arrayOperators))
					{
						result.add(primaryKeyTuple);
						resultPrimaryKeyValue.add(primaryKeyTuple.data.get(table.clusteringKey));
					}
				}
				break;
			default:
				System.out.println("Operator not valid");
		}
	}

	public static void loopPrimaryKey(SQLTerm primaryKeyCondition, Table table, int pageIndexInTable, Vector<Tuple> pagePrimaryKey, int indexInPage, SQLTerm[] sqlTerms, HashSet<Tuple> result, Tuple primaryKeyTuple){
		switch (primaryKeyCondition._strOperator)
		{
			case ">":
				//Will check all the conditions on all tuples after this primary key tuple
				//loop on the table beginning from the tuple after the primary key
				for(int i=pageIndexInTable; i<table.pagesPath.size();i++)
				{
					Vector <Tuple> page = deserialize(table.pagesPath.get(i));
					//loop on each page
					for(int j=0; j<page.size();j++)
					{
						//if this is the primary key page, begin from the primary key index
						if(page==pagePrimaryKey)
							j=indexInPage+1;//begin from the first tuple after the primary key that I have
						boolean conditionsSatisfied = true;//assume all conditions satisfied on this tuple
						//if primary key is the last element in page, .get(j+1) will cause an exception, go next page
						Tuple tupleInHand = null;
						try{
							tupleInHand = page.get(j);
						}
						catch (IndexOutOfBoundsException e)
						{
							break;
						}
						//check that all conditions in the query are satisfied on tupleInHand
						for(int k=0; k<sqlTerms.length; k++)
						{
							String columnName = sqlTerms[k]._strColumnName;
							Object value= sqlTerms[k]._objValue;//the value in the query
							switch(sqlTerms[k]._strOperator)
							{
								case ">":
									if(!(comparisonForSelect(tupleInHand.data.get(columnName),value)>0))
										conditionsSatisfied=false;
									break;
								case ">=":
									if(!(comparisonForSelect(tupleInHand.data.get(columnName),value)>=0))
										conditionsSatisfied=false;
									break;
								case "<":
									if(!(comparisonForSelect(tupleInHand.data.get(columnName),value)<0))
										conditionsSatisfied=false;
									break;
								case "<=":
									if(!(comparisonForSelect(tupleInHand.data.get(columnName),value)<=0))
										conditionsSatisfied=false;
									break;
								case "!=":
									if(!(comparisonForSelect(tupleInHand.data.get(columnName),value)!=0))
										conditionsSatisfied=false;
									break;
								case "=":
									if(!(comparisonForSelect(tupleInHand.data.get(columnName),value)==0))
										conditionsSatisfied=false;
									break;
								default:
									System.out.println("Operator not valid");
							}
							//if one condition is not satisfied, I do not need to continue checking on this tuple
							if(!conditionsSatisfied)
								break;
						}
						//I finished checking. If all conditions satisfied, add it to result
						if(conditionsSatisfied)
							result.add(tupleInHand);
					}
				}
				break;
			case ">=":
				//Will check all the conditions on all tuples on this primary key tuple and after this primary key tuple
				//loop on table from primary key till the end
				for(int i=pageIndexInTable; i<table.pagesPath.size();i++)
				{
					String pagePath= table.pagesPath.get(i);
					Vector <Tuple> page = deserialize(table.pagesPath.get(i));
					//loop on each page
					for(int j=0; j<page.size();j++)
					{
						//if this is the primary key page, begin from the primary key index
						if(page==pagePrimaryKey)
							j=indexInPage;//begin from the the primary key tuple that I have
						boolean conditionsSatisfied = true;//assume all conditions satisfied on this tuple
						Tuple tupleInHand = page.get(j);
						//check that all conditions in the query are satisfied on tupleInHand
						for(int k=0; k<sqlTerms.length; k++)
						{
							String columnName = sqlTerms[k]._strColumnName;
							Object value= sqlTerms[k]._objValue;//the value in the query
							switch(sqlTerms[k]._strOperator)
							{
								case ">":
									if(!(comparisonForSelect(tupleInHand.data.get(columnName),value)>0))
										conditionsSatisfied=false;
									break;
								case ">=":
									if(!(comparisonForSelect(tupleInHand.data.get(columnName),value)>=0))
										conditionsSatisfied=false;
									break;
								case "<":
									if(!(comparisonForSelect(tupleInHand.data.get(columnName),value)<0))
										conditionsSatisfied=false;
									break;
								case "<=":
									if(!(comparisonForSelect(tupleInHand.data.get(columnName),value)<=0))
										conditionsSatisfied=false;
									break;
								case "!=":
									if(!(comparisonForSelect(tupleInHand.data.get(columnName),value)!=0))
										conditionsSatisfied=false;
									break;
								case "=":
									if(!(comparisonForSelect(tupleInHand.data.get(columnName),value)==0))
										conditionsSatisfied=false;
									break;
								default:
									System.out.println("Operator not valid");
							}
							//if one condition is not satisfied, I do not need to continue checking on this tuple
							if(!conditionsSatisfied)
								break;
						}
						//I finished checking. If all conditions satisfied, add it to result
						if(conditionsSatisfied)
							result.add(tupleInHand);
					}
				}
				break;
			case "<":
				//Will check all the conditions on all tuples before this primary key tuple
				//loop on the table from beginning till the page that contains the primary key tuple
				for(int i=0; i<=pageIndexInTable;i++)
				{
					String pagePath= table.pagesPath.get(i);
					Vector <Tuple> page = deserialize(table.pagesPath.get(i));
					//loop on each page
					for(int j=0; j<page.size();j++)
					{
						boolean conditionsSatisfied = true;//assume all conditions satisfied on this tuple
						Tuple tupleInHand = page.get(j);
						//if I reached the primary key tuple, do not add it and break
						if(tupleInHand.data.get(table.clusteringKey)==primaryKeyTuple.data.get(table.clusteringKey))
							break;
						//check that all conditions in the query are satisfied on tupleInHand
						for(int k=0; k<sqlTerms.length; k++)
						{
							String columnName = sqlTerms[k]._strColumnName;
							Object value= sqlTerms[k]._objValue;//the value in the query
							switch(sqlTerms[k]._strOperator)
							{
								case ">":
									if(!(comparisonForSelect(tupleInHand.data.get(columnName),value)>0))
										conditionsSatisfied=false;
									break;
								case ">=":
									if(!(comparisonForSelect(tupleInHand.data.get(columnName),value)>=0))
										conditionsSatisfied=false;
									break;
								case "<":
									if(!(comparisonForSelect(tupleInHand.data.get(columnName),value)<0))
										conditionsSatisfied=false;
									break;
								case "<=":
									if(!(comparisonForSelect(tupleInHand.data.get(columnName),value)<=0))
										conditionsSatisfied=false;
									break;
								case "!=":
									if(!(comparisonForSelect(tupleInHand.data.get(columnName),value)!=0))
										conditionsSatisfied=false;
									break;
								case "=":
									if(!(comparisonForSelect(tupleInHand.data.get(columnName),value)==0))
										conditionsSatisfied=false;
									break;
								default:
									System.out.println("Operator not valid");
							}
							//if one condition is not satisfied, I do not need to continue checking on this tuple
							if(!conditionsSatisfied)
								break;
						}
						//I finished checking. If all conditions satisfied, add it to result
						if(conditionsSatisfied)
							result.add(tupleInHand);
					}
				}
				break;
			case "<=":
				//Will check all the conditions on this primary key tuple and before this primary key tuple
				for(int i=0; i<=pageIndexInTable;i++)
				{
					String pagePath= table.pagesPath.get(i);
					Vector <Tuple> page = deserialize(table.pagesPath.get(i));
					//loop on each page
					for(int j=0; j<page.size();j++)
					{
						boolean conditionsSatisfied = true;//assume all conditions satisfied on this tuple
						Tuple tupleInHand = page.get(j);
						//check that all conditions in the query are satisfied on tupleInHand
						for(int k=0; k<sqlTerms.length; k++)
						{
							String columnName = sqlTerms[k]._strColumnName;
							Object value= sqlTerms[k]._objValue;//the value in the query
							switch(sqlTerms[k]._strOperator)
							{
								case ">":
									if(!(comparisonForSelect(tupleInHand.data.get(columnName),value)>0))
										conditionsSatisfied=false;
									break;
								case ">=":
									if(!(comparisonForSelect(tupleInHand.data.get(columnName),value)>=0))
										conditionsSatisfied=false;
									break;
								case "<":
									if(!(comparisonForSelect(tupleInHand.data.get(columnName),value)<0))
										conditionsSatisfied=false;
									break;
								case "<=":
									if(!(comparisonForSelect(tupleInHand.data.get(columnName),value)<=0))
										conditionsSatisfied=false;
									break;
								case "!=":
									if(!(comparisonForSelect(tupleInHand.data.get(columnName),value)!=0))
										conditionsSatisfied=false;
									break;
								case "=":
									if(!(comparisonForSelect(tupleInHand.data.get(columnName),value)==0))
										conditionsSatisfied=false;
									break;
								default:
									System.out.println("Operator not valid");
							}
							//if one condition is not satisfied, I do not need to continue checking on this tuple
							if(!conditionsSatisfied)
								break;
						}
						//I finished checking. If all conditions satisfied, add it to result
						if(conditionsSatisfied)
							result.add(tupleInHand);
						//if I reached the primary key tuple, checked conditions on it, break
						if(tupleInHand.data.get(table.clusteringKey)==primaryKeyTuple.data.get(table.clusteringKey))
							break;
					}
				}
				break;
			case "!=":
				//Will check all conditions on all tuples except this primary key tuple
				//loop on the table
				for(int i=0; i<table.pagesPath.size();i++)
				{
					String pagePath= table.pagesPath.get(i);
					Vector <Tuple> page = deserialize(table.pagesPath.get(i));
					//loop on each page
					for(int j=0; j<page.size();j++)
					{
						boolean conditionsSatisfied = true;//assume all conditions satisfied
						Tuple tupleInHand = page.get(j);
						if(tupleInHand.data.get(table.clusteringKey)==primaryKeyTuple.data.get(table.clusteringKey))
							continue;
						//check that all conditions in the query are satisfied on tupleInHand
						for(int k=0; k<sqlTerms.length; k++)
						{
							String columnName = sqlTerms[k]._strColumnName;
							Object value= sqlTerms[k]._objValue;//the value in the query
							switch(sqlTerms[k]._strOperator)
							{
								case ">":
									if(!(comparisonForSelect(tupleInHand.data.get(columnName),value)>0))
										conditionsSatisfied=false;
									break;
								case ">=":
									if(!(comparisonForSelect(tupleInHand.data.get(columnName),value)>=0))
										conditionsSatisfied=false;
									break;
								case "<":
									if(!(comparisonForSelect(tupleInHand.data.get(columnName),value)<0))
										conditionsSatisfied=false;
									break;
								case "<=":
									if(!(comparisonForSelect(tupleInHand.data.get(columnName),value)<=0))
										conditionsSatisfied=false;
									break;
								case "!=":
									if(!(comparisonForSelect(tupleInHand.data.get(columnName),value)!=0))
										conditionsSatisfied=false;
									break;
								case "=":
									if(!(comparisonForSelect(tupleInHand.data.get(columnName),value)==0))
										conditionsSatisfied=false;
									break;
								default:
									System.out.println("Operator not valid");
							}
							//if one condition is not satisfied, I do not need to continue checking on this tuple
							if(!conditionsSatisfied)
								break;
						}
						//I finished checking. If all conditions satisfied, add it to result
						if(conditionsSatisfied)
							result.add(tupleInHand);
					}
				}
				break;
			case "=":
				//Will check all conditions on only this primary key tuple
				boolean conditionsSatisfied = true;
				for(int k=0; k<sqlTerms.length; k++)
				{
					String columnName = sqlTerms[k]._strColumnName;
					Object value= sqlTerms[k]._objValue;//the value in the query
					switch(sqlTerms[k]._strOperator)
					{
						case ">":
							if(!(comparisonForSelect(primaryKeyTuple.data.get(columnName),value)>0))
								conditionsSatisfied=false;
							break;
						case ">=":
							if(!(comparisonForSelect(primaryKeyTuple.data.get(columnName),value)>=0))
								conditionsSatisfied=false;
							break;
						case "<":
							if(!(comparisonForSelect(primaryKeyTuple.data.get(columnName),value)<0))
								conditionsSatisfied=false;
							break;
						case "<=":
							if(!(comparisonForSelect(primaryKeyTuple.data.get(columnName),value)<=0))
								conditionsSatisfied=false;
							break;
						case "!=":
							if(!(comparisonForSelect(primaryKeyTuple.data.get(columnName),value)!=0))
								conditionsSatisfied=false;
							break;
						case "=":
							if(!(comparisonForSelect(primaryKeyTuple.data.get(columnName),value)==0))
								conditionsSatisfied=false;
							break;
						default:
							System.out.println("Operator not valid");
					}
					//if one condition is not satisfied, I do not need to continue checking on this tuple
					if(!conditionsSatisfied)
						break;
				}
				if(conditionsSatisfied)
					result.add(primaryKeyTuple);
				break;
			default:
				System.out.println("Operator not valid");
		}
	}
	//////////////////////////
	// to get the buckets of each statment
	public static HashSet <String> getAllFilteredBuckets(Vector<String>maxIndexColumnsNames, SQLTerm[] sqlTerms,String[] arrayOperators, Object[] maxIndex, Table table){
		HashSet<String> filteredBuckets=null;
		Stack<Object> stack = new Stack<>();
		stack.add(sqlTerms[0]);
		for(int i = 0 ; i < arrayOperators.length ; i++) {
			if(arrayOperators[i].equals("AND"))
			{
				stack.add("AND");
			}
			else
			{
				evaluateStack(stack, maxIndexColumnsNames, arrayOperators, maxIndex, table);
				stack.push(arrayOperators[i]);
			}
			stack.push(sqlTerms[i+1]);
		}
		evaluateStack(stack, maxIndexColumnsNames, arrayOperators, maxIndex, table);
		stack = orKiller(stack);
		xorKiller(stack);
		return (HashSet<String>) stack.pop();
	}
	public static Stack<Object> orKiller(Stack<Object> stack){
		Stack<Object> res = new Stack<>();

		while(!stack.isEmpty()){
			HashSet<String> tmp1 = (HashSet<String>) stack.pop();
			if(stack.isEmpty()){
				res.push(tmp1);
				break;
			}
			if(stack.peek().equals("XOR"))
			{
				res.push(tmp1);
				res.push(stack.pop());
			}
			else
			{
				stack.pop();
				tmp1.addAll((HashSet<String>)stack.pop());
				stack.push(tmp1);
			}
		}
		return res;
	}
	public static void xorKiller(Stack<Object> stack){

		while (!stack.isEmpty()){
			HashSet<String> tmp1 = (HashSet<String>) stack.pop();
			if(stack.isEmpty()){
				stack.push(tmp1);
				break;
			}
			else
			{
				stack.pop();
				HashSet<String> tmp2 = (HashSet<String>) stack.pop();
				HashSet<String> res = xor(tmp1, tmp2);
				stack.push(res);
			}


		}

	}
	public static HashSet<String> xor(HashSet<String> set1, HashSet<String> set2){
		HashSet<String> res = new HashSet<>();
		for(String string : set1){
			if(!set2.contains(string))
				res.add(string);
		}

		for(String string : set2){
			if(!set1.contains(string))
				res.add(string);
		}
		return res;
	}
	public static void evaluateStack(Stack<Object> stack, Vector<String>maxIndexColumnsNames, String[] arrayOperators, Object[] maxIndex, Table table){

//		System.out.println("Stack : " + stack);
		Vector<SQLTerm> v = new Vector<>();
		while(!stack.isEmpty())
		{
			v.add((SQLTerm) stack.pop());
			if(stack.isEmpty())
				break;

			if(stack.peek().equals("AND"))
			{
				stack.pop();
			}
			else
				break;
		}

		SQLTerm[] sqlTerms = v.toArray(new SQLTerm[v.size()]);
		Vector<SQLTerm>[] layersSqlTerms = getTheLayersSqlTerms(maxIndexColumnsNames, sqlTerms);
		HashSet<String> filteredBucktes = getFilteredBucktes(layersSqlTerms,maxIndex,maxIndexColumnsNames, table);
		stack.push(filteredBucktes);
	}
	public static Vector<SQLTerm>[] getTheLayersSqlTerms(Vector<String> maxIndexColumnsNames, SQLTerm[] sqlTerms){
		//use this index that we found
		//for each layer of the index, specify which sqlTerm is applied on it
		//ex: index layers: [gpa, id] []
		//    layerSqlTerms :[[gpa>3.12],[id<56-1523]
		Vector<SQLTerm>[] layersSqlTerms = new Vector[maxIndexColumnsNames.size()];
		for(int i=0;i<layersSqlTerms.length;i++)
		{
			layersSqlTerms[i]= new Vector<>();
		}

		//[gpa,date]
		for(int j=0; j<sqlTerms.length; j++)//gpa>3.12 AND id==50 AND first_name ="kkkkk" OR date > 1800 AND ------ OR ---AND----
		{
			//if the index has a layer for this column insert the sql term to vector at the same index
			if(maxIndexColumnsNames.contains(sqlTerms[j]._strColumnName))
			{
				int layerNumber = maxIndexColumnsNames.indexOf(sqlTerms[j]._strColumnName);
				Vector<SQLTerm> destinationLayer = layersSqlTerms[layerNumber];
				destinationLayer.add(sqlTerms[j]);//then add the sql term on this layer
			}
		}
//		System.out.println("layersSql : " + Arrays.toString(layersSqlTerms));

		return layersSqlTerms;
	}
	public static HashSet<Tuple> queryAllBuckets(Table table,SQLTerm[] sqlTerms, String[] arrayOperators, HashSet<String> filteredBuckets){
		HashSet<Tuple> result = new HashSet<>();
		HashSet<String> pages = new HashSet<>();
		for(String s : filteredBuckets) //looping on the vector of buckets
		{
			Vector<Vector<String>> vectorOfBucket= deserializeBucket(s);
			for(int j=0;j<vectorOfBucket.size();j++) // looping on every bucket on the vector of buckets
			{
				Vector<String> bucket = vectorOfBucket.get(j);
				for(int k=0;k<bucket.size();k++) //looping on every page
				{
					String pagePath= bucket.get(k);
					Vector<Tuple> page = deserialize(bucket.get(k)); //now I have the page in my hand
					if(!pages.add(pagePath)) // I looped on the page before
					{
						continue;
					}

					{
						for(int l=0; l<page.size();l++)
						{
							Tuple tupleInHand = page.get(l);
							//check which conditions in the query are satisfied on tupleInHand
							Vector<Boolean> conditionsSatisfied = new Vector();
							for(int m=0; m<sqlTerms.length; m++)
							{
								String columnName = sqlTerms[m]._strColumnName;//column name of the condition
								Object value= sqlTerms[m]._objValue;//the value in the query
								switch(sqlTerms[m]._strOperator)
								{
									case ">":
										conditionsSatisfied.add(comparisonForSelect(tupleInHand.data.get(columnName),value)>0);
										break;
									case ">=":
										conditionsSatisfied.add(comparisonForSelect(tupleInHand.data.get(columnName),value)>=0);
										break;
									case "<":
										conditionsSatisfied.add(comparisonForSelect(tupleInHand.data.get(columnName),value)<0);
										break;
									case "<=":
										conditionsSatisfied.add(comparisonForSelect(tupleInHand.data.get(columnName),value)<=0);
										break;
									case "!=":
										conditionsSatisfied.add(comparisonForSelect(tupleInHand.data.get(columnName),value)!=0);
										break;
									case "=":
										conditionsSatisfied.add(comparisonForSelect(tupleInHand.data.get(columnName),value)==0);
										break;
									default:
										System.out.println("Operator not valid");
								}
							}
							//I finished checking. If conditions and operators result to true, add it to result
							if(conditionsResult(conditionsSatisfied,arrayOperators))
								result.add(tupleInHand);
						}
					}
				}
			}
		}
		return result;
	}
	public static HashSet<Tuple> queryAllTheTable(Table table,SQLTerm[] sqlTerms, String[] arrayOperators){
		HashSet<Tuple> result = new HashSet<>();
		//loop on the table
		for(int i=0; i<table.pagesPath.size();i++)
		{
			Vector <Tuple> page = deserialize(table.pagesPath.get(i));
			//loop on each page
			for(int j=0; j<page.size();j++)
			{
				Tuple tupleInHand = page.get(j);
				//check which conditions in the query are satisfied on tupleInHand
				Vector<Boolean> conditionsSatisfied = new Vector();
				for(int k=0; k<sqlTerms.length; k++)
				{
					String columnName = sqlTerms[k]._strColumnName;//column name of the condition
					Object value= sqlTerms[k]._objValue;//the value in the query
					switch(sqlTerms[k]._strOperator)
					{
						case ">":
							conditionsSatisfied.add(comparisonForSelect(tupleInHand.data.get(columnName),value)>0);
							break;
						case ">=":
							conditionsSatisfied.add(comparisonForSelect(tupleInHand.data.get(columnName),value)>=0);
							break;
						case "<":
							conditionsSatisfied.add(comparisonForSelect(tupleInHand.data.get(columnName),value)<0);
							break;
						case "<=":
							conditionsSatisfied.add(comparisonForSelect(tupleInHand.data.get(columnName),value)<=0);
							break;
						case "!=":
							conditionsSatisfied.add(comparisonForSelect(tupleInHand.data.get(columnName),value)!=0);
							break;
						case "=":
							conditionsSatisfied.add(comparisonForSelect(tupleInHand.data.get(columnName),value)==0);
							break;
						default:
							System.out.println("Operator not valid");
					}

				}
				//I finished checking. If conditions and operators result to true, add it to result
				if(conditionsResult(conditionsSatisfied,arrayOperators))
					result.add(tupleInHand);
			}
		}
		return result;
	}
	public static boolean conditionsResult(Vector<Boolean>conditionsSatisfied, String[]arrayOperators){
		//[true,false,true] [OR,AND] [true, OR, false,
		//order operators on an array based on priority
		String[] operatorsOrdered = new String[3];
		operatorsOrdered[0]="AND";
		operatorsOrdered[1]="OR";
		operatorsOrdered[2]="XOR";
		// put all operands and operators in the same vector [true, "AND", false, "OR", true, "XOR", true]
		Vector<Object> conditionsResult = new Vector<>();
		conditionsResult.add(conditionsSatisfied.get(0));
		for(int i=1; i<conditionsSatisfied.size(); i++)//[false,OR,false
		{
			conditionsResult.add(arrayOperators[i-1]);
			conditionsResult.add(conditionsSatisfied.get(i));
		}
		//loop on operators from highest to lowest priority
		for(int i=0; i<operatorsOrdered.length; i++){//OR,XOR
			String highestOperator = operatorsOrdered[i];//operator with highest priority in the ones remaining
			for(int j=1; j<conditionsResult.size(); j+=2)
			{
				String operatorInHand = (String) conditionsResult.get(j);
				if(operatorInHand.equals(highestOperator)){//if this is the highest priority operator, apply it
					switch (highestOperator){
						case "AND": {
							boolean newAdded = (Boolean) conditionsResult.get(j-1) && (Boolean) conditionsResult.get(j+1);
							conditionsResult.remove(j-1);
							conditionsResult.remove(j-1);
							conditionsResult.remove(j-1);
							conditionsResult.insertElementAt(newAdded,j-1);
							j-=2;
							break;
						}
						case "OR": {
							boolean newAdded = (Boolean) conditionsResult.get(j-1) || (Boolean) conditionsResult.get(j+1);
							conditionsResult.remove(j-1);
							conditionsResult.remove(j-1);
							conditionsResult.remove(j-1);
							conditionsResult.insertElementAt(newAdded,j-1);
							j-=2;
							break;
						}
						case "XOR": {
							boolean newAdded = (Boolean) conditionsResult.get(j-1) ^ (Boolean) conditionsResult.get(j+1);
							conditionsResult.remove(j-1);
							conditionsResult.remove(j-1);
							conditionsResult.remove(j-1);
							conditionsResult.insertElementAt(newAdded,j-1);
							j-=2;
							break;
						}
					}
				}

			}

		}
		//finally the vector will contain only one boolean value containing the result
		return (Boolean) conditionsResult.get(0);
	}
	public static HashSet<String> getFilteredBucktes(Vector<SQLTerm>[] layersSqlTerms, Object[] gridLayer, Vector<String> indexColumnNames, Table table){
		Vector<Vector<Integer>> layerIndiciesNeeded = new Vector();
		HashSet<String> result= new HashSet<>();
		for(int i=0; i<indexColumnNames.size(); i++)
		{

			Object[] pointer = gridLayer;
			String layerName = indexColumnNames.get(i); //get the name of the column in this layer
			Vector<SQLTerm> sqlTermsInLayer = layersSqlTerms[i]; //get the sql terms applied on this column
			Vector<Integer> needed = new Vector();
			if(sqlTermsInLayer.isEmpty())//if there are no sql terms on this layer
			{
				//adding all buckets that are needed 1 to 10
				for(int j=0;j<10;j++)
					needed.add(j);
			}
			else //if there are sql terms on this layer
			{
			//	System.out.println(sqlTermsInLayer.get(0)+ " "+ sqlTermsInLayer.get(1));
				//loop on all sql terms applied on this layer
				for(int j=0; j<sqlTermsInLayer.size(); j++){//[false]
					SQLTerm conditionInHand = sqlTermsInLayer.get(j);
					String operator = conditionInHand._strOperator;
					Object value = conditionInHand._objValue;

					switch (operator)
					{
						case ">":
						{//5-9  >10
							//check all the arrays that have ranges with maximum greater than condition value
							Pair[] ranges = table.ranges.get(layerName); //ranges of this layer
							//loop on all ranges that are not null
							for(int k=0; k<ranges.length && ranges[k]!=null; k++)
							{
								Object min = ranges[k].x;
								Object max = ranges[k].y;
								if(comparisonForSelect(max,value)>0)
									needed.add(k);
							}
							break;
						}
						case ">=":
						{
							//check all the arrays that have ranges with maximum greater than or equal condition value
							Pair[] ranges = table.ranges.get(layerName); //ranges of this layer
							//loop on all ranges that are not null
							for(int k=0; k<ranges.length && ranges[k]!=null; k++)
							{
								Object min = ranges[k].x;
								Object max = ranges[k].y;
								if(comparisonForSelect(max,value)>=0)
									needed.add(k);
							}
							break;
						}
						case "<":
						{
							//check all the arrays that have ranges with minimum less than condition value
							Pair[] ranges = table.ranges.get(layerName); //ranges of this layer
							//loop on all ranges that are not null
							for(int k=0; k<ranges.length && ranges[k]!=null; k++)
							{
								Object min = ranges[k].x;
								Object max = ranges[k].y;
								if(comparisonForSelect(min,value)<0)
									needed.add(k);
							}
							break;
						}
						case "<=":
						{
							//check all the arrays that have ranges with minimum less than condition value
							Pair[] ranges = table.ranges.get(layerName); //ranges of this layer
							//loop on all ranges that are not null
							for(int k=0; k<ranges.length && ranges[k]!=null; k++)
							{
								Object min = ranges[k].x;
								Object max = ranges[k].y;
								if(comparisonForSelect(min,value)<=0)
									needed.add(k);
							}
							break;
						}
						case "!=":
						{
							//take all the arrays
							Pair[] ranges = table.ranges.get(layerName); //ranges of this layer
							//loop on all ranges that are not null
							for(int k=0; k<ranges.length && ranges[k]!=null; k++)
							{
								needed.add(k);
							}
							break;
						}
						case "=":
						{
							//check all the arrays that have ranges with minimum less than or equal condition value
							//AND all the arrays that have ranges with maximum value greater than or equal condition value
							Pair[] ranges = table.ranges.get(layerName); //ranges of this layer
							//loop on all ranges that are not null
							for(int k=0; k<ranges.length && ranges[k]!=null; k++)
							{
								Object min = ranges[k].x;
								Object max = ranges[k].y;
								if(comparisonForSelect(min,value)<=0 && comparisonForSelect(max,value)>=0)
									needed.add(k);
							}
							break;
						}
					}

				}

				}

			layerIndiciesNeeded.insertElementAt(needed,i);

			if(i==indexColumnNames.size()-1)
			{
				//now I am in the last Layer and know what buckets exactly do I need on each layer  // gridLayer [id,firstname,lastname]
																					// layerIndiciesNeeded= {{1,2,3...10},{2,3},{4,5}}
				result = getFilteredBucktesHelper( gridLayer,indexColumnNames,0,layerIndiciesNeeded);
				return result;
			}

			pointer = (Object[]) gridLayer[0];

		}
		return result;


	}
	public static HashSet<String> getFilteredBucktesHelper( Object[] gridLayer, Vector<String> indexColumnNames, int grid, Vector<Vector<Integer>> eachBucketNeededOnEveryLayer){
		HashSet<String> result = new HashSet<>();
		//base case: (I'm in the last layer) just return what bucket on the gridLayer
		if(grid==indexColumnNames.size()-1)
		{
			for(int i=0;i<eachBucketNeededOnEveryLayer.get(grid).size();i++)
			{
				int j = eachBucketNeededOnEveryLayer.get(grid).get(i); // gridLayer [id,firstname,lastname]  for example: now I am in lastname and I get
				// layerIndiciesNeeded= {{1,2,3...10},{2,3},{4,5}} 		 the "4","5" buckets
				result.add((String)gridLayer[j]);
			}
			return result;
		}
		for(int i=0;i<eachBucketNeededOnEveryLayer.get(grid).size();i++)
		{
			int j = eachBucketNeededOnEveryLayer.get(grid).get(i); // gridLayer [id,firstname,lastname]  for example: now I am in firstName and I got
												// layerIndiciesNeeded= {{1,2,3...10},{2,3},{4,5}} 		 the "2"
			//
			result.addAll(getFilteredBucktesHelper((Object[])gridLayer[j],indexColumnNames,grid+1,eachBucketNeededOnEveryLayer));
		}
		return result;
	}
	public static void serialize(String path, Vector<Tuple> page) {
		try {
			FileOutputStream fileOut = new FileOutputStream(path);
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(page);
			out.close();
			fileOut.close();
		} catch (IOException i) {
			i.printStackTrace();
		}
	}
	public static Vector<Tuple> deserialize(String path) {

		Vector<Tuple> page = new Vector<Tuple>();
		try {
			FileInputStream fileIn = new FileInputStream(path);
			ObjectInputStream in = new ObjectInputStream(fileIn);

			page = (Vector<Tuple>) in.readObject();
			in.close();
			fileIn.close();

		} catch (IOException i) {
			i.printStackTrace();
			return null;
		} catch (ClassNotFoundException c) {
			System.out.println("Page is not found!");
			c.printStackTrace();
			return null;
		}
		return page;

	}
	public static Vector<Vector<String>> deserializeBucket(String path) {

		Vector<Vector<String>> bucket = new Vector<>();
		try {
			FileInputStream fileIn = new FileInputStream(path);
			ObjectInputStream in = new ObjectInputStream(fileIn);

			bucket = (Vector<Vector<String>>) in.readObject();
			in.close();
			fileIn.close();

		} catch (IOException i) {
			i.printStackTrace();
			return null;
		} catch (ClassNotFoundException c) {
			System.out.println("Page is not found!");
			c.printStackTrace();
			return null;
		}

		return bucket;

	}
	public static void serializeBucket(String path, Vector<Vector<String>> bucket) {
		try {
			FileOutputStream fileOut = new FileOutputStream(path);
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(bucket);
			out.close();
			fileOut.close();
		} catch (IOException i) {
			i.printStackTrace();
		}
	}
	public Iterator parseSQL( StringBuffer strbufSQL ) throws
			DBAppException{
		// Before looking at this method ,Look at Mylistener.java file as it is subclass of MySqlParserBaseListener.java
		// in Mylistener.java you will find instance variables I called here and methods of entering grammar rules I implemented that overrides the super class MySqlParserBaseListener.java
		//If you will call this method,You have to disable scala plugin that it is added lately in intellij ,for that go to file->settings->plugins->(search for scala and disable it)
		MySqlLexer mySqlLexer = new MySqlLexer(CharStreams.fromString(strbufSQL.toString()));
		CommonTokenStream tokens = new CommonTokenStream(mySqlLexer);
		MySqlParser mySqlParser = new MySqlParser(tokens);
		ParseTree tree = mySqlParser.sqlStatement();
		ParseTreeWalker walker = new ParseTreeWalker();
		MyListener listener=new MyListener();
		ParseTreeWalker.DEFAULT.walk(listener, tree);

		Hashtable<String, Object> colNameVal=new Hashtable<>();
		String theTable="";
		String cluster="";

		switch(MyListener.statement)
		{
			case "select":
				colNameVal=new Hashtable<>();
				int sizeOfArray=MyListener.term.size();
				SQLTerm[] sqltermsArray = new SQLTerm[sizeOfArray];
				for(int i=0;i<sizeOfArray;i++){
					sqltermsArray[i]=MyListener.term.get(i);

				}
				int sizeOfOperators=MyListener.operators.size();
				String[] logicalOperators=new String[sizeOfOperators];
				for(int j=0;j<sizeOfOperators;j++){
					logicalOperators[j]=MyListener.operators.get(j);

				}
				//DBAPP select method here
                return this.selectFromTable(sqltermsArray,logicalOperators);

			case "delete":

				for(int i=0;i<MyListener.term.size();i++)
				{
					colNameVal.put(MyListener.term.get(i)._strColumnName,(MyListener.term.get(i)._objValue));

				}
				theTable=MyListener.tableName;
				this.deleteFromTable(theTable,colNameVal);
				break;
			case "insert":
				colNameVal=new Hashtable<>();
				theTable=MyListener.tableName;
				for(int i=0;i<MyListener.columns.size();i++)
				{
					Object vv=MyListener.getObj(theTable,MyListener.columns.get(i),MyListener.values.get(i));
					colNameVal.put(MyListener.columns.get(i),vv);

				}
               this.insertIntoTable(theTable,colNameVal);
				break;

			case "update":
				colNameVal=new Hashtable<>();
				theTable=MyListener.tableName;
				for(int i=0;i<MyListener.columns.size();i++)
				{ Object vv=MyListener.getObj(theTable,MyListener.columns.get(i),MyListener.values.get(i));
					colNameVal.put(MyListener.columns.get(i),vv);
					}
				for(int i=0;i<MyListener.term.size();i++)
				{
					cluster=(MyListener.term.get(i)._objValue).toString();
				}

              this.updateTable(theTable,cluster,colNameVal);

				break;
			case "createIndex":
				theTable=MyListener.tableName;

				int arraySize=MyListener.columns.size();
				String[] col = new String[arraySize];
				for(int i=0;i<arraySize;i++){
					col[i]=MyListener.columns.get(i);
				}
               this.createIndex(theTable,col);

				break;
			case "createTable" :
				theTable=MyListener.tableName;

				this.createTable(theTable,MyListener.clustering,MyListener.colNameTypes,MyListener.minColumnValues,MyListener.maxColumnValues);

            break;

		}


                return null;
	}
	public static void main(String[] args) throws DBAppException, IOException, ParseException {
		DBApp dbApp = new DBApp();
		dbApp.init();
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
		Table table = tables.get("students");

		BufferedReader studentsTable = new BufferedReader(new FileReader("src/main/resources/students_table.csv"));
		String record;
		int c = 10;

		Hashtable<String, Object> row = new Hashtable<>();
		while (c > 0 && (record = studentsTable.readLine()) != null) {
			String[] fields = record.split(",");

			row.put("id", fields[0]);
			row.put("first_name", fields[1]);
			row.put("last_name", fields[2]);

			int year = Integer.parseInt(fields[3].trim().substring(0, 4));
			int month = Integer.parseInt(fields[3].trim().substring(5, 7));
			int day = Integer.parseInt(fields[3].trim().substring(8));

			Date dob = new Date(year - 1900, month - 1, day);
			row.put("dob", dob);

			double gpa = Double.parseDouble(fields[4].trim());

			row.put("gpa", gpa);

			dbApp.insertIntoTable("students", row);
			row.clear();
			c--;
		}
		//studentsTable.close();
		String[] column = new String[2];
		column[0]="first_name";
		column[1]="gpa";
		dbApp.createIndex("students",column);
		Object[] grid = table.indices.get(0);

		String[] column2 = new String[1];
		column2[0]="id";
//		column2[1]="gpa";
		//dbApp.createIndex("students",column2);



		//dbApp.parseSQL(sql);


//


//		SQLTerm sqlTerm1 = new SQLTerm();
//		sqlTerm1._strTableName = "students";
//		sqlTerm1._strColumnName =  "id";
//		sqlTerm1._strOperator= ">";
//		sqlTerm1._objValue	= "66-1766";
//		SQLTerm sqlTerm2 = new SQLTerm();
//		sqlTerm2._strTableName = "students";
//		sqlTerm2._strColumnName =  "id";
//		sqlTerm2._strOperator= "<";
//		sqlTerm2._objValue	= 	"50-7952";
//		SQLTerm sqlTerm3 = new SQLTerm();
//		sqlTerm3._strTableName = "students";
//		sqlTerm3._strColumnName =  "gpa";
//		sqlTerm3._strOperator= ">=";
//		sqlTerm3._objValue	= 	3.63;
//
//		SQLTerm[] arrayOfSql = new SQLTerm[2];
//		arrayOfSql[0]= sqlTerm1;
//		arrayOfSql[1]= sqlTerm2;
////		arrayOfSql[2]= sqlTerm3;
//		String[] operators = new String[1];
//		operators[0]="OR";
////		operators[1]="OR";
//		Iterator itr = dbApp.selectFromTable(arrayOfSql,operators);
//		int count = 0;
//		while(itr.hasNext()) {
//			Object element = itr.next();
//			System.out.println(element + " ");
//			count++;
//		}
//		System.out.println("Iterator: " + count);


//		for(int j=0; j<10; j++) {
//			Vector<Vector<String>> pagesPath = deserializeBucket((String) (grid[j]));
//			System.out.println(table.ranges.get("id")[j]);
//			for (int l = 0; l < pagesPath.size(); l++) {
//				for (int k = 0; k < pagesPath.get(l).size(); k++) {
//					System.out.println(deserialize(pagesPath.get(l).get(k)));
//				}
//				System.out.println("/////////////////////");
//			}
//		}
//		Hashtable<String, Object> toBeUpdated = new Hashtable<>();
//		toBeUpdated.put("gpa", new Double(0.88));


		Hashtable<String,Object> toBeUpdated = new Hashtable<>();
		toBeUpdated.put("first_name",new String("FFFFFF"));
		toBeUpdated.put("last_name", new String("khal"));
		toBeUpdated.put("gpa", new Double(0.88));

		Hashtable<String,Object> toBeDeleted = new Hashtable<>();
		toBeDeleted.put("id", new String("82-8772"));

		for(int i=0;i<10;i++)
		{
			Arrays.toString(grid);
			Object[] secondLayer =  (Object[]) grid[i];
			for(int j=0; j<10; j++) {
				Vector<Vector<String>> pagesPath = deserializeBucket((String) (secondLayer[j]));
				//System.out.println(table.ranges.get("first_name")[i] + " " + table.ranges.get("gpa")[j]);
				for (int l = 0; l < pagesPath.size(); l++) {
					for (int k = 0; k < pagesPath.get(l).size(); k++) {
						//System.out.println(deserialize(pagesPath.get(l).get(k)));
					}
					//System.out.println("/////////////////////");
				}
			}
		}

		for(int i=0;i<table.pagesPath.size();i++)
		{
			Vector<Tuple> page = deserialize(table.pagesPath.get(i));
			for(int j=0;j<page.size();j++)
			{
				System.out.println(page.get(j));
			}
			System.out.println("ana page");
		}

		System.out.println("************************************************************************************************8");



		//dbApp.parseSQL(new StringBuffer("CREATE TABLE hotel(age INT CHECK (age BETWEEN 5 AND 16) PRIMARY KEY ,id VARCHAR(50) CHECK (id BETWEEN '46-1848' AND '46-5404'),name VARCHAR(50) CHECK (name BETWEEN 'AAAA' AND 'zzzz'), money INT CHECK (money BETWEEN 15 AND 76), mydate date CHECK (mydate BETWEEN '1910-10-25' AND '1990-10-25'))"));
		//dbApp.parseSQL(new StringBuffer("create index theindex on hotel(age,id)"));
		//dbApp.parseSQL(new StringBuffer("delete from students where id='82-8772'"));
		//dbApp.parseSQL(new StringBuffer("insert into students(id,gpa,dob,first_name,last_name) values('95-5555','0.9','1995-11-25','AHMED','sobeih') "));
		//dbApp.parseSQL(new StringBuffer("UPDATE students set first_name='marwan' where id='67-5025'"));
		//dbApp.parseSQL(new StringBuffer("delete from students where id='67-5025'"));
		/*Iterator itr = dbApp.parseSQL(new StringBuffer("select * from students where id>'59-8196' OR id<'52-2476'"));
		while(itr.hasNext()) {
			Object element = itr.next();
			System.out.println(element + " ");
	}*/

		dbApp.updateTable("students","82-8772", toBeUpdated);
		//dbApp.deleteFromTable("students",toBeDeleted);
		//dbApp.deleteFromTable("students",toBeDeleted);
		//dbApp.deleteFromTable("students",toBeDeleted);
		dbApp.updateTable("students","52-2476", toBeUpdated);
		Hashtable<String, Object> toBeDeleted2 = new Hashtable<>();
		//toBeDeleted2.put("first_name", new String("DVLYsf"));
		//dbApp.deleteFromTable("students",toBeDeleted2);

		Hashtable<String,Object> toBeInserted = new Hashtable<>();
		toBeInserted.put("id", new String("90-5425"));
		toBeInserted.put("first_name", "YQigxr");
		toBeInserted.put("last_name", "mIBWZv");

		Date dob = new Date(2000-1900, 01-1, 31);
		toBeInserted.put("dob", dob);

		double gpa = 3.63;

		toBeInserted.put("gpa", gpa);

		dbApp.insertIntoTable("students", toBeInserted);

		//UPDAAAAAAAAAAAAATE

		Hashtable<String,Object> toBeUpdated2 = new Hashtable<>();
		toBeUpdated2.put("first_name",new String("VVVVV"));
		toBeUpdated2.put("last_name", new String("khal"));
		toBeUpdated2.put("gpa", new Double(0.88));
		dbApp.updateTable("students","90-5425", toBeUpdated2);

//		c = 10;
//		while (c > 0 && (record = studentsTable.readLine()) != null) {
//			String[] fields = record.split(",");

//			row.put("id", fields[0]);
//			row.put("first_name", fields[1]);
//			row.put("last_name", fields[2]);
//
//			int year = Integer.parseInt(fields[3].trim().substring(0, 4));
//			int month = Integer.parseInt(fields[3].trim().substring(5, 7));
//			int day = Integer.parseInt(fields[3].trim().substring(8));
//
//			Date dob = new Date(year - 1900, month - 1, day);
//			row.put("dob", dob);
//
//			double gpa = Double.parseDouble(fields[4].trim());
//
//			row.put("gpa", gpa);
//
//			dbApp.insertIntoTable("students", row);
//			row.clear();
//			c--;
//		}




		for(int i=0;i<table.pagesPath.size();i++)
		{
			Vector<Tuple> page = deserialize(table.pagesPath.get(i));
			for(int j=0;j<page.size();j++)
			{
				System.out.println(page.get(j));
			}
			System.out.println("ana page");
		}

//		for(int j=0; j<10; j++) {
//			Vector<Vector<String>> pagesPath = deserializeBucket((String) (grid[j]));
//			System.out.println(table.ranges.get("id")[j]);
//			for (int l = 0; l < pagesPath.size(); l++) {
//				for (int k = 0; k < pagesPath.get(l).size(); k++) {
//					System.out.println(deserialize(pagesPath.get(l).get(k)));
//				}
//				System.out.println("/////////////////////");
//			}
//		}


		for(int i=0;i<10;i++)
		{
			Arrays.toString(grid);
			Object[] secondLayer =  (Object[]) grid[i];
			for(int j=0; j<10; j++) {
				Vector<Vector<String>> pagesPath = deserializeBucket((String) (secondLayer[j]));
				//System.out.println(table.ranges.get("first_name")[i] + " " + table.ranges.get("gpa")[j]);
				for (int l = 0; l < pagesPath.size(); l++) {
					for (int k = 0; k < pagesPath.get(l).size(); k++) {
					//	System.out.println(deserialize(pagesPath.get(l).get(k)));
					}
					//System.out.println("/////////////////////");
				}
			}
		}

	}
}



