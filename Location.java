import java.util.ArrayList;
import java.util.List;

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
			
			//Runs the query and formats the results in to a Carrier which is added to a list of carriers.
			resultFormatter(query,carrier);
		}
		
		for(Carrier carrier : carriers){
			if (carrier.getValue("dbo:country").equals("")&&carrier.getCount() != null){
				updateCountry(carrier);
			}
		}
		/*
		//Returns the carrier with the highest score.
		return_carrier = highScore(carriers,return_carrier,topics);
		*/
		return carriers;
		}
		
		
	//Template for asking for more information about a country. Returning the currency of the country, their government type, their total
	//population and an abstract in English.
	public ArrayList<Carrier> countryTemp(ArrayList<Carrier> carriers){
		Carrier return_carrier = null ;
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
			
			//Runs the query and formats the results in to a Carrier which is added to a list of carriers.
			resultFormatter(query,carrier);
		}
		/*
		//Returns the carrier with the highest score.
		return_carrier = highScore(carriers,return_carrier,topics);
		*/
		return carriers;	
		}
	

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
}


	
