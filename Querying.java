import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.jena.query.QuerySolution;

/*
 * A class for handling the communication between our implemented knowledge base system and an graphical interface or some other system.
 */
public class Querying {
	
	private KBQuery q;
    private Location l;
    private Person p;
    private Organisation o;
	
	Querying(){
		q = new KBQuery();
	    l = new Location();
	    p = new Person();
	    o = new Organisation();
	}
	
	/*
	 * The function called to run a query. search_indata is the entity (thing) you want to know something more about. The context list
	 * is a list of possible words that could help in the search, some sort of context. type is a variable to help the system search faster
	 * and specifies which type the search_indata is. For instance if it is a person then the type should be 'Person', is it a city or 
	 * some location it should be 'Location'.
	 * 
	 * Depending solely on the type it chooses a template to call and executes the query.
	 */
	Carrier querying(String search_indata, String[] context, String type) {
		
			double uncertainty_limit = 0.8;
			int number_of_entities = 50;
			
		    ArrayList<Carrier> filled_results = new ArrayList<Carrier>();
		    
		    Carrier carrier = new Carrier();
		    ArrayList<Carrier> results = q.topEntities(search_indata, context,type, number_of_entities);
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
		    ArrayList<Carrier> final_results = filled_results;
		    final_results.sort(null);
		    
		    
		    if (!final_results.isEmpty()){
		    	if (!(final_results.size()==1)){
		    		}
		    	if (final_results.size()==1){
		    		carrier = final_results.get(0);
		    	}else if (final_results.get(1).getScore()/final_results.get(0).getScore()<uncertainty_limit){
		    		
		    		
		    		carrier = final_results.get(0);
		    	}
		    }
		    
		    carrier.setResults(final_results);
		    return carrier;
		}
	
	public Carrier getAdditionalInfo(Carrier carrier){
		Map<String,String> add_info = new HashMap<String,String>();

		if (carrier.getID().equals("")){
			
		} else if (carrier.getType().equals("dbo:Organisation")){
			add_info = o.additionalInfo(carrier);
		} else if (carrier.getType().equals("dbo:Person")){
			add_info = p.additionalInfo(carrier);
		} else if (carrier.getType().equals("dbo:Location")){
			add_info = l.additionalInfo(carrier);
		} else if (carrier.getType().equals("dbo:Country")){
			add_info = l.additionalInfo(carrier);
		}

		
		
		carrier.setAdditionalInfo(add_info);
		
		return carrier;
	}
	/*
	 * Returns three most likely answers. 
	 
	ArrayList<Carrier> getTopThree(){
		ArrayList<Carrier> return_list = new ArrayList<Carrier>();
		if(final_results.size()>=3){
			return_list.add(final_results.get(0));
			return_list.add(final_results.get(1));
			return_list.add(final_results.get(2));
			}
		return return_list;
	}*/
	
}
