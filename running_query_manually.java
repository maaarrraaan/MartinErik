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
		
		System.out.println(carrier+"\nGet top three: \n");
		for (Carrier c : q.getTopThree()){
			System.out.println(c);
		}
		//System.out.println(carrier.returnRaw());
		//new Interface();
		
		
		//If it is a person search and a person is returned call the additionalInfo() function.
		Person p = new Person();
	
		if(!(carrier.getScore() == null && carrier.getType().equals("Person"))){
			Map<String,String> info = p.additionalInfo(carrier);
			
			for(String key : info.keySet()){
				System.out.println(key + "    " + info.get(key));
			}
		}
		
	}

}
