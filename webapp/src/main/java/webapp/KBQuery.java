package webapp;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFormatter;

/*
 * A general class for queries, which have several subclasses that add some more specific information for those cases. Contains several
 * functions that these subclasses share.
 */
public class KBQuery {
	
	/*
	 * Runs any well formated and valid SPARQL query given as a string. Adds some prefixes that is the same for all of the queries.
	 * Returns a list of QuerySolutions. 
	 */
	public List<QuerySolution> runQuery(String sparqlQueryString1){   
		String sparqlquery =  "PREFIX owl: <http://www.w3.org/2002/07/owl#>"+
				"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"+
				"PREFIX dbo: <http://dbpedia.org/ontology/>"+
				"PREFIX dbr: <http://dbpedia.org/resource/>"+
				"PREFIX dct: <http://purl.org/dc/terms/>"+
				"PREFIX dbp: <http://dbpedia.org/property/>"+sparqlQueryString1;
		Query query = QueryFactory.create(sparqlquery);
		QueryExecution qexec = QueryExecutionFactory.sparqlService("http://dbpedia.org/sparql", query);
		ResultSet results = qexec.execSelect();
		List<QuerySolution> results_of_query = ResultSetFormatter.toList(results);
		qexec.close() ;
		return(results_of_query);
	 }	
	/*
	 * Functions that counts number of links from the given entity. Returns a list of strings (each string are the values connected to one
	 * entity). If the context is empty it only returns the result with the highest number of links, otherwise returns the top 10.
	 */
	public ArrayList<Carrier> Count(String indata,String [] context,String entityType){ 
		
		Boolean redirect = true;
		
		indata = indata.replaceAll(" ", "_");
		/*
		try {
			indata = URLEncoder.encode(indata, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			System.out.println("Error on converting from indata from UTF-8 to URL. Keeps indata as: " + indata);
		}
		*/
		ArrayList<Carrier> carriers = new ArrayList<Carrier>();
		
		String redirects = "SELECT (count(?y) as ?count) ?"+entityType+" WHERE{"
				+ "dbr:"+indata+" dbo:wikiPageRedirects ?"+entityType+"."
				+ "?"+entityType+" ?y ?x."
				+ "} GROUP BY (?"+entityType+") ORDER BY DESC (?count) LIMIT 10";
		
		List<QuerySolution> possible_entities = runQuery(redirects);
		
		if (possible_entities.isEmpty()){
			redirect = false;
			String count =
					"SELECT (count(?y) as ?count) ?"+entityType+" WHERE {"+
					"?"+entityType+" rdf:type dbo:"+entityType+" ."+
					"FILTER regex(?"+entityType+", \""+indata+"\", \"i\")"+
					"?"+entityType+" ?y ?x."+
					"} GROUP BY (?"+entityType+") ORDER BY DESC (?count) LIMIT 50";
			
			possible_entities = runQuery(count);
		}
		
		/*//Loop to present all the results from count
		for (QuerySolution q : possible_entities){
			System.out.println(q.toString());
		}*/	
		
		
		if (possible_entities.isEmpty()){
		}else{													//If context exists then add all entities to list for later ranking in function highScore().
			
			for(QuerySolution entity : possible_entities){
				Carrier carrier = new Carrier(indata, context, "dbo:"+entityType, entity.get("?count").toString(), "<"+entity.get("?"+entityType).toString()+">");
				
				
				String entity_name = carrier.rename_http(carrier.getID().toLowerCase());
				String[] split_entity_name = entity_name.split(" ");
				
				if (redirect){
					carriers.add(carrier);
				}else if (entity_name.equals(indata.toLowerCase()) || entity_name.equals(indata.toLowerCase() + ", inc.") || entity_name.equals(indata.toLowerCase()+ " inc.")){
					carriers = new ArrayList<Carrier>();
					carriers.add(carrier);
					break;
				}else if (indata.contains("_")){
					carriers.add(carrier);
				}else{
					for (String part_of_name : split_entity_name){
						if (indata.toLowerCase().equals(part_of_name)){
							carriers.add(carrier);
						}
					}
				}
				
			}
			/*for (Carrier c: carriers){
				System.out.println(c +"Score: " +  c.getCount());
			}*/
			
		}
		return carriers;
	}
	/*
	 * A function that ranks entities according to score from bestResult(). Returns carrier with highest score.
	 */
	



	/*
	 *  A function that does some simple formating of the query result and assigns it to a carrier. Since the queries are executed with
	 *  one specified entity at the time only one is returned. The for-loop loops over the different results for this entity and converts
	 *  the results to strings which are added to a list. The list is then passed on to setResult which returns a carrier.
	 */
	void resultFormatter(String query, Carrier carrier){
	
		List<QuerySolution> runned_query = runQuery(query);
		Carrier return_carrier;
		ArrayList<String> list_of_results = new ArrayList<String>();
		

		
		if(runned_query.size()!=1 && !runned_query.get(0).equals("")){
			for(QuerySolution query_result : runned_query){
				String result = query_result.toString();
				list_of_results.add(result.substring(2, result.length()-2));
			}
		}
		return_carrier = this.setResult(list_of_results, carrier);
		
		
	}
	/*
	 * A function that first sets the values given by the list results and then assigns it a score based on the context. 
	 * Returns a carrier with a score and all the values. If no values are in the results string an empty carrier is returned. 
	 */
	Carrier setResult(ArrayList<String> results, Carrier carrier){ 
		
		if (!results.isEmpty()){
			for (String result : results){
				String[] topic_and_value = result.split(" \\) \\( ");
				
				for (String s : topic_and_value ){
					String[] splitted_topic_and_value = s.split(" = ",2);
					
					for (String topic : carrier.getTopics()){
						if (topic.contains(splitted_topic_and_value[0].substring(1,splitted_topic_and_value[0].length()))){
							splitted_topic_and_value[0] = topic;
						}
					}
					
					carrier.setSubject(splitted_topic_and_value);
				}
			
			}
		}
		double score = 0.0;
		
		//Score base on number of appearances of context in abstract
		for (String con : carrier.getContext()){
			int num_of_occurence = StringUtils.countMatches(carrier.getValue("dbo:abstract").toLowerCase(),con.toLowerCase());
			score = score+num_of_occurence*2;
	
		}
		double count = Double.parseDouble(carrier.getCount());
		//System.out.println(carrier.getValue("dbo:Person")+"Count value as double: " + count);
		double count_scr = 4*count/1000;
		score = score + count_scr;
		//System.out.println(carrier.getValue("dbo:Person")+"Score value as double: " + score + "\n");
		
		carrier.setScore(score);
		
		return carrier;
		
		
		
	}
}
