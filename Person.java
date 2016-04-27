import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.jena.query.QuerySolution;

//An extension of the general KBQuery class that holds the specific templates for when asking about a Person of some sort.
public class Person extends KBQuery{
	
	//A template that returns more information about a person. Returns the persons name, its birth date and place, its occupation and an
	//abstract in English. 
	public ArrayList<Carrier> personTemp(ArrayList<Carrier> carriers){
		
		String [] topics = {"dbo:Person", "dbo:abstract", "dbo:birthDate", "dbp:birthDate","dbo:deathDate", "dbp:deathDate", "dbo:birthPlace", "dbp:birthPlace", "dbo:occupation", "dbo:nationality"};
		Carrier return_carrier = null ;
		for(int i = 0; i<carriers.size();i++){
			Carrier carrier = carriers.get(i);
			carrier.setTopics(topics);
			String entity = carrier.getID();
			
			String query =
				"SELECT ?abstract ?birthDate ?birthPlace ?occupation ?nationality ?deathDate WHERE {"+
				"OPTIONAL{"+entity+" dbo:birthDate ?birthDate.}"+
				"OPTIONAL{"+entity+" dbp:birthDate ?birthDate.}"+
				"OPTIONAL{"+entity+" dbo:abstract ?abstract."+
				"FILTER (lang(?abstract) = 'en')}"+
				"OPTIONAL{"+entity+" dbo:birthPlace ?birthPlace.}"+
				"OPTIONAL{"+entity+" dbp:birthPlace ?birthPlace.}"+
				"OPTIONAL{"+entity+" dbo:deathDate ?deathDate.}"+
				"OPTIONAL{"+entity+" dbp:deathDate ?deathDate.}"+
				"OPTIONAL{"+entity+" dbo:nationality ?nationality.}"+
				"OPTIONAL{"+entity+" dbo:occupation ?occupation}"
				+"}";
			
			//Runs the query and adds the results to the Carrier.
			resultFormatter(query, carrier);
			
			
		}
		
		return carriers;
		
	}
	
	/*
	 * A function to get additional information about an entity. Currently just otherPersons implemented.
	 */
	public Map<String, String> additionalInfo(Carrier carrier){
		Map<String, String> return_map = new HashMap<String, String>();
		otherPersons(carrier, return_map);
		return return_map;
	}
	
	/*
	 * Queries the knowledge base for persons that are connected to the entity. Adds the connected persons
	 * to the Map, along with the property connecting them.
	 */
	private void otherPersons(Carrier carrier, Map<String,String> return_map){
		String query = "SELECT * WHERE{"+
		carrier.getID()+" ?x ?y."+
		"?y rdf:type dbo:Person"+
		"}";
		
		List<QuerySolution> result= runQuery(query);
		
		for (QuerySolution q : result){
			String[] values = q.toString().split(" \\) \\( ");
			String key = values[1].split("x = ")[1];
			key = key.substring(0, key.length()-2);
			String value = values[0].substring(7, values[0].length());
					
			if (!return_map.containsKey(key)){
				return_map.put(key, value);
			} else {
				return_map.put(key, return_map.get(key) + "!!split!!" + value);
			}
		}
	}

}