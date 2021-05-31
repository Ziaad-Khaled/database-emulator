
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Vector;

@SuppressWarnings("rawtypes")
public class Tuple implements Serializable, Comparable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Hashtable<String,Object> data;
	String clusteringKey;
	Tuple() {
		data = new Hashtable<>();
	}
	
	public String toString() {
		return data.toString();
	}

	@Override
	public int compareTo(Object arg0) {
		Tuple t = (Tuple) arg0;

		if(this.data.get(clusteringKey) instanceof Integer && t.data.get(clusteringKey) instanceof Integer)
			return (int)this.data.get(clusteringKey) - (int)t.data.get(clusteringKey);

		if(this.data.get(clusteringKey) instanceof String && t.data.get(clusteringKey) instanceof String)
			return ((String)this.data.get(clusteringKey)).compareTo((String)t.data.get(clusteringKey));

		if(this.data.get(clusteringKey) instanceof Double && t.data.get(clusteringKey) instanceof Double)
			return ((Double) this.data.get(clusteringKey)).compareTo((double)t.data.get(clusteringKey));

		if(this.data.get(clusteringKey) instanceof Date && t.data.get(clusteringKey) instanceof Date)
			return ((Date)this.data.get(clusteringKey)).compareTo((Date)t.data.get(clusteringKey));

		return 0;
	}


}

@SuppressWarnings("rawtypes")
class Pair implements Serializable, Comparable{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	Object x;
	Object y;

	Pair(Object x, Object y) {
		this.x = x;
		this.y = y;
	}
	public boolean withinRange(Object o){
		if(o instanceof Integer)
		{
			int value = (Integer) o;
			int lower = (int) this.x;
			int higher = (int) this.y;
			return value>=lower && value<higher;
		}
		if(o instanceof Double)
		{
			Double value = (Double) o;
			Double lower = (Double) this.x;
			Double higher = (Double) this.y;
			return value>=lower && value<higher;
		}
		if(o instanceof String)
		{
			String value = (String) o;
			String lower = (String) this.x;
			String higher = (String) this.y;
			return value.compareTo(lower)>=0 && value.compareTo(higher)<0;
		}
//		if(o instanceof Date)
//		{
//			int value = Integer.parseInt(((String) o).substring(8));
//			int lower = Integer.parseInt(((String) this.x).substring(8));
//			int higher = Integer.parseInt(((String) this.y).substring(8));
//			return value>lower && value<=higher;
//		}
		return false;
	}

	@Override
	public int compareTo(Object arg0) {
		Pair p = (Pair) arg0;
		if(p.y instanceof Integer && this.y instanceof Integer) 
			return (int)this.y - (int)p.y;
		
		if(p.y instanceof String && this.y instanceof String) 
			return ((String)this.y).compareTo((String)p.y);
		
		if(p.y instanceof Double && this.y instanceof Double) 
			return (int)((double)this.y - (double)p.y);
		
		if(p.y instanceof Date && this.y instanceof Date) 
			return ((Date)this.y).compareTo((Date)p.y);
		
		return 0;
	}
	
	
	
	public String toString() {
		return "(" + this.x + ", " + this.y + ")";
	}
	
	
	
	public static void main(String[] args) {
		HashSet<String> hashSet1 = new HashSet<>();
		HashSet<String> hashSet2 = new HashSet<>();
		hashSet1.add("2");
		hashSet1.add("4");
		hashSet2.add("2");
		hashSet2.add("1");
		System.out.println(hashSet1);
		System.out.println(hashSet2);
		hashSet1.addAll(hashSet2);
		System.out.println(hashSet1);

	}
	
}
