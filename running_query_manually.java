/*
 * A function used during the development to run the program. 
 */
import java.util.Map;

public class running_query_manually {

	public static void main(String[] args) {
		
		Querying q = new Querying();
		
		//The context used for the search, write each word as a string in the list. 
		String[] context = {""};
		
		//Change the values in q.querying() to change the search. 
		Carrier carrier = q.querying("Obama", context,"Person");
		
		System.out.println(carrier);
		
		//Returns related entities to the carrier. 
		q.getAdditionalInfo(carrier);
		
		System.out.println("Related entities:\n" + carrier.AdditionalInfotoString());
	}

}
