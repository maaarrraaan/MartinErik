import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.jena.query.QuerySolution;

//An extension of the general KBQuery class that holds the specific templates for when asking about an organisation of some sort.
public class Organisation extends KBQuery{
	
	//A template for gaining general information about an organisation. Returns the organisations full name, the founding date of the organisation,
	//the founding place and an abstract in English.
	public ArrayList<Carrier>orgTemp(ArrayList<Carrier> carriers){
		Carrier return_carrier = null ;
		String[] topics = {"dbo:Organisation", "dbo:abstract", "dbo:foundingDate", "dbo:foundationPlace", "dbo:assets", "dbo:foundedBy", "dbo:keyPerson", "dbo:industry", "dbo:parentCompany"};
		
		for(Carrier carrier : carriers){
			
			carrier.setTopics(topics);
			String entity = carrier.getID();
			String query = "SELECT ?abstract ?foundingDate ?foundationPlace ?assets ?foundedBy ?keyPerson ?industry ?parentCompany WHERE {"+
				"OPTIONAL{"+entity+" dbo:foundingDate ?foundingDate.}"+
				"OPTIONAL{"+entity+" dbo:abstract ?abstract."+
				"FILTER (lang(?abstract) = 'en')}"+
				"OPTIONAL{"+entity+" dbo:foundationPlace ?foundationPlace. }"+
				"OPTIONAL{"+entity+" dbo:assets ?assets. }"+
				"OPTIONAL{"+entity+" dbo:foundedBy ?foundedBy. }"+
				"OPTIONAL{"+entity+" dbo:keyPerson ?keyPerson. }"+
				"OPTIONAL{"+entity+" dbo:industry ?industry. }"+
				"OPTIONAL{"+entity+" dbo:parentCompany ?parentCompany. }"+
				"}";

			//Runs the query and adds the results to the Carrier.
			resultFormatter(query,carrier);
		
		}
		return carriers;
	}
	
	public Map<String, String> additionalInfo(Carrier carrier){
		Map<String, String> return_map = new HashMap<String, String>();
		otherOrganisations(carrier, return_map);
		return return_map;
	}
	
	public void otherOrganisations(Carrier carrier, Map<String,String> return_map){
		String query = "SELECT * WHERE{"+
				carrier.getID()+" ?x ?y."+
			"?y rdf:type dbo:Organisation"+
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
