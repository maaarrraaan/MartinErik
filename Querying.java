import java.util.ArrayList;

import org.apache.jena.query.QuerySolution;

/*
 * A class for handling the communication between our implemented knowledge base system and an graphical interface or some other system.
 */
public class Querying {
	
	/*
	 * The function called to run a query. search_indata is the entity (thing) you want to know something more about. The context list
	 * is a list of possible words that could help in the search, some sort of context. type is a variable to help the system search faster
	 * and specifies which type the search_indata is. For instance if it is a person then the type should be 'Person', is it a city or 
	 * some location it should be 'Location'.
	 * 
	 * Depending solely on the type it chooses a template to call and executes the query.
	 */
	private ArrayList<Carrier> final_results;
	
	Carrier querying(String search_indata, String[] context, String type) {
			System.getProperties().put( "proxySet", "true" );
			System.getProperties().put( "proxyHost", "www-gw.foi.se" );
			System.getProperties().put( "proxyPort", "8080" );
			KBQuery q = new KBQuery();
		    Location l = new Location();
		    Person p = new Person();
		    Organisation o = new Organisation();
		    ArrayList<Carrier> filled_results = new ArrayList<Carrier>();
		    
		    Carrier carrier = new Carrier();
		    ArrayList<Carrier> results = q.Count(search_indata, context,type);
		    if (results.isEmpty()){
		    	System.out.println("Couldn't find anything on that searchterm.");
		    }else if (type.equals("Person")){
		    	filled_results = p.personTemp(results);
		    }else if (type.equals("Country")){
		    	filled_results = l.countryTemp(results);
			}else if (type.equals("Location")){
				filled_results = l.cityTemp(results);
			}else if (type.equals("Organisation")){
				filled_results = o.orgTemp(results);
			}
		    final_results = filled_results;
		    final_results.sort(null);
		    /*
		    for (Carrier c: final_results){
		    	System.out.println(c);
		    }
		    */
		    if (!final_results.isEmpty()){
		    	if (!(final_results.size()==1)){
		    		System.out.println("Score quota : " + final_results.get(1).getScore()/final_results.get(0).getScore());
		    	}
		    	if (final_results.size()==1){
		    		carrier = final_results.get(0);
		    	}else if (final_results.get(1).getScore()/final_results.get(0).getScore()<0.91){
		    		
		    		
		    		carrier = final_results.get(0);
		    	}
		    }
		    
		    
		    return carrier;
		}
	ArrayList<Carrier> getTopThree(){
		ArrayList<Carrier> return_list = new ArrayList<Carrier>();
		if(final_results.size()>=3){
			return_list.add(final_results.get(0));
			return_list.add(final_results.get(1));
			return_list.add(final_results.get(2));
			}
		return return_list;
	}
}
