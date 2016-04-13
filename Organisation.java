import java.util.ArrayList;

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

			//Runs the query and formats the results in to a Carrier which is added to a list of carriers.
			resultFormatter(query,carrier);
		
		}
		/*
		//Returns the carrier with the highest score.
		return_carrier = highScore(carriers,return_carrier,topics);
		*/
		return carriers;
	}

}
