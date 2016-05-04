import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/*
 * A carrier class used to carry the information from the knowledge base to the user. Contains a HashMap that stores all the information
 * with the variable name from the query as key.
 * 
 * Have methods to setSubject (add an additional variable and its value), getValue (get the value associated to a certain variable),
 * toString and returnRaw (return all the information stored in the carrier as a string with some different formating), rename_http and
 * reformat_numers (changes the format of the returned value to better suit a human user).
 */
public class Carrier implements Comparable<Carrier>{
	
	private Map<String, String> subjects;			// A Map to contain topics and it's value.
	private String indata;					// Stores the question.
	private String type;					// Each question ask must contain a type, which is stored here.
	private String[] context;				// Saves the context in which the indata is found, if any.
	private String ID;						// The unique ID for each Carrier.
	private String count;					// Stores a count value for each Carrier. Count is the sum of the number of objects.
	private String[] topics;				// In the subjects map each value is of a certain topic. The topics are stored here
	private Double score;					// A double to store the score of each Carrier.
	private ArrayList<Carrier> results;		//A list that stores the result of the returned carrier. Only added to the carrier with the highest score
	private Map<String, String> add_info;	//Used to store additional info if that is wanted.
	/*
	 * A constructor for empty Carriers used
	 */
	Carrier (){
		subjects = new HashMap<String, String>();
	}
	/*
	 * Creates carriers when there is a sucsessful result. new_indata, new_context and new_type are data connected to the question
	 * asked. new_count and new_ID are connected to each different possible entity returned. The ID is used to identify the different
	 * Carriers. 
	 */
	Carrier (String new_indata, String[] new_context, String new_type, String new_count, String new_ID){
		
		subjects = new HashMap<String, String>();
		ID = new_ID;
		indata = new_indata;
		context = new_context;
		count = new_count.split("\\^\\^")[0];
		type = new_type;
	}
	
	/*
	 * Function to get the ID of the carrier. Returns an empty string if there is no ID.
	 */
	String getID(){
		if (ID == null){
			ID = "";
		}
		return ID;
	}
	
	/*
	 * Function to get the Score of the carrier.
	 */
	Double getScore(){
		return score;
	}
	/*
	 * Function to get the Type of the carrier.
	 */
	String getType(){
		return type;
	}
	
	/*
	 * Function to get the Indata of the carrier.
	 */
	String getIndata(){
		return indata;
	}
	
	/*
	 * Function to get the Count of the carrier.
	 */
	String getCount(){
		return count;
	}
	
	/*
	 * Function to get the Context of the carrier.
	 */
	String[] getContext(){
		return context;
	}
	
	/*
	 * Function to get the Topics of the carrier.
	 */
	String[] getTopics(){
		return topics;
	}
	/*
	 * Function to get the results as a list of Carriers.
	 */
	ArrayList<Carrier> getResults(){
		return results;
	}
	/*
	 * Function to set Score with a new score. Takes a double as input. 
	 */
	void setScore (Double new_score){
		score = new_score;
	}
	
	/*
	 * Function to set Topics with a new list of topics. Takes a List of Strings as input. 
	 */
	void setTopics(String[] new_topics){
		topics = new_topics;
	}
	
	/*
	 * Function to set the results variable. Takes a ArrayList<Carrier> as input.
	 */
	void setResults(ArrayList<Carrier> new_results){
		results = new_results;
	}
	
	void setAdditionalInfo(Map<String, String> new_add_info){
		add_info = new_add_info;
	}
	/*
	 * Takes a list of strings containing two strings with the first spot containing the key (or variable) and the second spot containing the value.
	 * If the subject dosn't exist, it simply adds the tuple to the Map.
	 * If the subject exists it checks if the value already have been. If the value have been added it does nothing. If there is a new value
	 * the new value is added at the end with '!!split!!' as a splitter.
	 */
	
	public void setSubject(String[] subject){
		if (!subjects.containsKey(subject[0])){
			subjects.put(subject[0], subject[1]);
		}
		
		else{
			String[] values = subjects.get(subject[0]).split("!!split!!");
			Boolean is_in = true;
			for (String value : values){
				if (value.equals(subject[1])){
					is_in = false;
				}
			}
			
			if (is_in){
				String old_value = subjects.get(subject[0]);
				String new_value = old_value  + "!!split!!" +subject[1];
				subjects.put(subject[0], new_value);
			}
		}
	}
	
	/*
	 * Takes a key as an argument and returns the value of that key if it exists. Otherwise it returns an empty string.
	 */
	public String getValue(String key){
		
		String return_str = "";
		if (subjects.containsKey(key)){
			return_str = subjects.get(key);
		}
		return return_str;
	}
	
	/*
	 * Returns everything thats stored in the Map without doing any formating.
	 */
	public String returnRaw(){
		String return_str = type+": " +ID+"\n";
		
		if (!subjects.isEmpty()){
			for (String key: subjects.keySet()){
				return_str = return_str + key + ": " + subjects.get(key) +"\n";
			}
		}
		return return_str;
	}
	
	/*
	 * A function used to change a http-link to just presenting the value of the entity. For example:
	 * Spain is return as a link to the DBpedia page about Spain <http://dbpedia.org/resource/Spain>. Instead of presenting this to the user,
	 * this function removes the additional link information and simply returns 'Spain'.
	 * 
	 * Can also handle several links by splitting on '!!split!!' and return those separated by a comma. Also changes any underscores in the link to
	 * whitespaces. 
	 */
	String rename_http(String http_str){
		
		String return_str =http_str;
		
		if (http_str.length()>6){
			return_str = "";
			
			String[] splitted_http1 = http_str.split("!!split!!");
			
			for (String string : splitted_http1){
				if (string.length()>6){
					String additional_str = "";
					String[] splitted_http = string.split("/");
					additional_str = splitted_http[splitted_http.length-1];
					additional_str = additional_str.substring(0, additional_str.length()-1) + ", ";
					
					return_str = return_str +  additional_str;
				}
			}
			return_str = return_str.substring(0, return_str.length()-2).replaceAll("_", " ");
		}
		return return_str;
	}
	
	/*
	 * A function to reformat the numerical values returned by DBpedia. Numbers are typically followed by what type it is, for instance
	 * "1985-04-07"^^xsd:date or "9716962"^^xsd:nonNegativeInteger. This functions removes everything that follows the '^^' and removes 
	 * the quotation marks, and just returns the number.
	 */
	String reformat_numbers (String raw_number){
		String numbers= raw_number;
		if (raw_number.length()>0){
			String[] list_of_raw_numbers = raw_number.split("!!split!!");
			numbers = "";
			for (String number : list_of_raw_numbers){
				if (!number.equals("")){
					number = number.split("\\^\\^")[0];
					numbers = numbers + number.substring(1, number.length()-1) + ", ";
				}
			}
			
			numbers = numbers.substring(0,numbers.length()-2);
		}
		return numbers;
	}
	
	/*
	 * Returns the map that stores all the values. 
	 */
	public Map<String,String> returnSubjects(){
		return subjects;
	}
	
	/*
	 * Returns all the keys used to store values in the map.
	 */
	public Set<String>  returnKeys(){
		return subjects.keySet();
	}
	
	/*
	 * A re-definition of the toString() method. Returns all the values stored in the map in a more user friendly way by removing information
	 * related to the DBpedia setup. 
	 * @see java.lang.Object#toString()
	 */
	public String toString(){
		if (ID == null) {
			return "\n";
		}
		String return_str = type+ ": "+ rename_http(ID) + "\n";
		
		for (String key : subjects.keySet()){
			String subject = subjects.get(key);
			
			//If the value is presented as a http-link its changed to a text format.
			if (subject.startsWith("http:")){			
				subject = "<" + subject + ">";
			}	
			if (subject.startsWith("<http:")){
				subject = rename_http(subject);
			}
			
			//If the value is numerical, reformat it.
			else if (subject.contains("xsd:")&&!key.equals("?count")){
				subject = reformat_numbers(subject);
			}
			
			//Reformat the abstract to remove language and quotation marks.
			else if (key.equals("dbo:abstract")){
				subject = subject.substring(1, subject.length()-4);
			}
			
			try {
				subject = URLDecoder.decode(subject, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				System.out.println("Couldn't decode the URL. Presenting result as URL instead of UTF-8");
			} catch (IllegalArgumentException e){
				System.out.println(e.getMessage());
			}
			return_str = return_str + key + ": " + subject + "\n";
		}
		
		return return_str;
	}

	/*
	 * Returns the map that stores all the additional information. 
	 */
	public Map<String,String> returnAdditionalInfo(){
		return add_info;
	}
	
	/*
	 * Returns all the keys used to store additional information in the map.
	 */
	public Set<String>  returnAdditionalInfosKeys(){
		return add_info.keySet();
	}
	
	/*
	 * Returns the value of the additional info as a string. Some formating done due to the format of
	 * the returns of the DBpedia query.
	 */
	public String AdditionalInfotoString(){
		if (add_info.size() == 0) {
			return "\n";
		}
		String return_str = "";
		for (String key : add_info.keySet()){
			String subject = add_info.get(key);
			
			//If the value is presented as a http-link its changed to a text format.
			if (subject.startsWith("http:")){			
				subject = "<" + subject + ">";
			} 
			if (subject.startsWith("<http:")){
				subject = rename_http(subject);
			}
			
			System.out.println("Key: " + key);
			
			if (key.startsWith("http:")){			
				key = "<" + key + ">";
			}
			if (key.startsWith("<http:")){
				key = rename_http(key);
			}
			key = key.substring(0, 1).toUpperCase() + key.substring(1, key.length());
			try {
				subject = URLDecoder.decode(subject, "UTF-8");
				key = URLDecoder.decode(key, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				System.out.println("Couldn't decode the URL. Presenting result as URL instead of UTF-8");
			} catch (IllegalArgumentException e){
				System.out.println(e.getMessage());
			}
			return_str = return_str + key + ": " + subject + "\n";
		}
		
		return return_str;
	}

	
	
	@Override
	/*
	 * Redefined compareTo function to compare two carriers. The carrier with the higher score is ranked as higher. 
	 */
	public int compareTo(Carrier other_carrier) {
		return -Double.compare(this.getScore(), other_carrier.getScore());
	}
}
