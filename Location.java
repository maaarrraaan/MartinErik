import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.jena.query.QuerySolution;


//An extension of the general KBQuery class that holds the specific templates for when asking about a location of some sort.
public class Location extends KBQuery{
	
	//Template for asking for more information about a location. Returning the country the location is in, the total population of the spot,
	//and the abstract in English. 
	public  ArrayList<Carrier> cityTemp(ArrayList<Carrier> carriers){
		Carrier return_carrier = null ;
		String[] topics = {"dbo:Location", "dbo:country", "dbo:abstract", "dbo:populationTotal", "dbp:leaderName", "dbp:establishedDate", "dbo:areaLand"};
		
		for(Carrier carrier : carriers){
			carrier.setTopics(topics);
			String entity = carrier.getID();
			
			String query = 
					"SELECT ?abstract ?populationTotal ?country ?leaderName ?establishedDate ?areaLand WHERE {"
					+ "OPTIONAL{"+entity+" dbo:country ?country.}"+
					"OPTIONAL{"+entity+" dbo:abstract ?abstract."+
					"FILTER (lang(?abstract) = 'en')}" +
					"OPTIONAL{"+entity+" dbp:leaderName ?leaderName}"+
					"OPTIONAL{"+entity+" dbp:establishedDate ?establishedDate}"
					+
					"OPTIONAL{"+entity+" dbo:areaLand ?areaLand}"
					+ "OPTIONAL{"+entity+" dbo:populationTotal ?populationTotal}"+
					"}";
			
			//Runs the query and adds the results to the Carrier.
			resultFormatter(query,carrier);
		}
		
		for(Carrier carrier : carriers){
			if (carrier.getValue("dbo:country").equals("")&&carrier.getCount() != null){
				updateCountry(carrier);
			}
		}
		
		return carriers;
		}
		
		
	//Template for asking for more information about a country. Returning the currency of the country, their government type, their total
	//population and an abstract in English.
	public ArrayList<Carrier> countryTemp(ArrayList<Carrier> carriers){

		String[] topics = {"dbo:country", "dbo:abstract", "dbo:currency", "dbo:governmentType", "dbo:populationTotal"};
		
		for(Carrier carrier : carriers){
			
			carrier.setTopics(topics);
			String entity = carrier.getID();
			
			String query =
				"SELECT ?abstract ?currency ?governmentType ?populationTotal WHERE {"+
				"OPTIONAL{"+entity+" dbo:abstract ?abstract.}"+
				"OPTIONAL{"+entity+" dbo:currency ?currency.}"+
				"OPTIONAL{"+entity+" dbo:governmentType ?governmentType.}"+ 
				"OPTIONAL{"+entity+" dbo:populationTotal ?populationTotal.}"+
				"FILTER (lang(?abstract) = 'en')"+
				"}";
			
			//Runs the query and adds the results to the Carrier.
			resultFormatter(query,carrier);
		}
		
		return carriers;	
		}
	
/* 
 * Used in some cases when the country isn't added. If all the connected entities have the same nationality
 * the country is added.
 */
	public void updateCountry(Carrier carrier){
		
		String query = "SELECT distinct ?country WHERE{"+
			carrier.getID()+" ?x ?y."+
			"?y <http://dbpedia.org/ontology/country> ?country}";
		
		List<QuerySolution> executed_query = runQuery(query);
		
		if (executed_query.size() == 1){
			String country = executed_query.get(0).toString().split(" = ", 2)[1];
			country = country.substring(0, country.length()-2);
			String[] country_tuple = {"dbo:country", country};
			carrier.setSubject(country_tuple);
		}
		
	}
	
	public Map<String, String> additionalInfo(Carrier carrier){
		Map<String, String> return_map = new HashMap<String, String>();
		otherLocations(carrier, return_map);
		return return_map;
	}
	
	private void otherLocations(Carrier carrier, Map<String,String> return_map){
		String query = "SELECT * WHERE{"+
		carrier.getID()+" ?x ?y."+
		"?y rdf:type dbo:Location"+
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


	
