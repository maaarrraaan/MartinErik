public class InformationSpliter {
	public static void main(String[] args) {
		
		/*String test_string = "( ?gov = <http://dbpedia.org/resource/Constitutional_monarchy> ) ( ?abs = \"Sweden is great!\" ) ( ?country = \"Sweden\" ) ( ?currency = \"KR\n )"
				+"( ?gov = <http://dbpedia.org/resource/Unitary_state> ) ( ?abs = \"Sweden is great!\" ) ( ?country = \"Sweden\" ) ( ?currency = \"KR\n )"
				+"( ?abs = \"Sweden is great!\" ) ( ?country = \"New Sweden\" ) ( ?currency = \"KR\n )";
		*/
		
		String test_string = "( ?gov = <http://dbpedia.org/resource/Constitutional_monarchy> ) ( ?abs = \"Sweden (/?swi?d?n/ SWEE-d?n; Swedish: Sverige [?sværj?] (13px )), officially the Kingdom of Sweden (Swedish: About this sound Konungariket Sverige ), is a Scandinavian country in Northern Europe. Sweden borders Norway and Finland, and is connected to Denmark by a bridge-tunnel across the Øresund. At 450,295 square kilometres (173,860 sq mi), Sweden is the third-largest country in the European Union by area, with a total population of about 9.7 million. Sweden has a low population density of 21 inhabitants per square kilometre (54/sq mi), with the population mostly concentrated in the southern half of the country. About 85% of the population lives in urban areas. Southern Sweden is predominantly agricultural, while the north is heavily forested.Germanic peoples have inhabited Sweden since prehistoric times, emerging into history as the Goths/Geats and Swedes/Svear and contributing to the sea peoples known as the Vikings.  Sweden emerged as an independent and unified country during the Middle Ages. In the 17th century, the country expanded its territories to form the Swedish Empire. The empire grew to be one of the great powers of Europe in the 17th and early 18th centuries. Most of the conquered territories outside the Scandinavian Peninsula were lost during the 18th and 19th centuries. The eastern half of Sweden, present-day Finland, was lost to Russia in 1809. The last war in which Sweden was directly involved was in 1814, when Sweden by military means forced Norway into a personal union. Since then, Sweden has been at peace, remaining a largely neutral nation. The union with Norway was peacefully dissolved in 1905. Sweden played a role in humanitarian efforts during World Wars I and II, taking in refugees from German-occupied Europe. With the ending of the Cold War, Sweden joined the European Union, but declined NATO membership.Today, Sweden is a constitutional monarchy and a parliamentary democracy, with the Monarch as the head of state. The capital city is Stockholm, which is also the most populous city in the country. Legislative power is vested in the 349 member unicameral Riksdag. Executive power is exercised by the Government, chaired by the Prime Minister. Sweden is a unitary state, currently divided into 21 counties and 290 municipalities.Sweden maintains a Nordic social welfare system that provides universal health care and tertiary education for its citizens. It has the world's eighth-highest per capita income and ranks highly in numerous comparisons of national performance, including quality of life, health, education, protection of civil liberties, economic competitiveness, equality, prosperity and human development. Sweden has been a member of the European Union since 1 January 1995, but remains outside the Eurozone. It is also a member of the United Nations, the Nordic Council, Council of Europe, the World Trade Organization and the Organisation for Economic Co-operation and Development (OECD).\"@en ) ( ?country = <http://dbpedia.org/resource/Sweden> ) ( ?currency = <http://dbpedia.org/resource/Swedish_krona> )"+
		"( ?gov = <http://dbpedia.org/resource/Unitary_state> ) ( ?abs = \"Sweden (/?swi?d?n/ SWEE-d?n; Swedish: Sverige [?sværj?] (13px )), officially the Kingdom of Sweden (Swedish: About this sound Konungariket Sverige ), is a Scandinavian country in Northern Europe. Sweden borders Norway and Finland, and is connected to Denmark by a bridge-tunnel across the Øresund. At 450,295 square kilometres (173,860 sq mi), Sweden is the third-largest country in the European Union by area, with a total population of about 9.7 million. Sweden has a low population density of 21 inhabitants per square kilometre (54/sq mi), with the population mostly concentrated in the southern half of the country. About 85% of the population lives in urban areas. Southern Sweden is predominantly agricultural, while the north is heavily forested.Germanic peoples have inhabited Sweden since prehistoric times, emerging into history as the Goths/Geats and Swedes/Svear and contributing to the sea peoples known as the Vikings.  Sweden emerged as an independent and unified country during the Middle Ages. In the 17th century, the country expanded its territories to form the Swedish Empire. The empire grew to be one of the great powers of Europe in the 17th and early 18th centuries. Most of the conquered territories outside the Scandinavian Peninsula were lost during the 18th and 19th centuries. The eastern half of Sweden, present-day Finland, was lost to Russia in 1809. The last war in which Sweden was directly involved was in 1814, when Sweden by military means forced Norway into a personal union. Since then, Sweden has been at peace, remaining a largely neutral nation. The union with Norway was peacefully dissolved in 1905. Sweden played a role in humanitarian efforts during World Wars I and II, taking in refugees from German-occupied Europe. With the ending of the Cold War, Sweden joined the European Union, but declined NATO membership.Today, Sweden is a constitutional monarchy and a parliamentary democracy, with the Monarch as the head of state. The capital city is Stockholm, which is also the most populous city in the country. Legislative power is vested in the 349 member unicameral Riksdag. Executive power is exercised by the Government, chaired by the Prime Minister. Sweden is a unitary state, currently divided into 21 counties and 290 municipalities.Sweden maintains a Nordic social welfare system that provides universal health care and tertiary education for its citizens. It has the world's eighth-highest per capita income and ranks highly in numerous comparisons of national performance, including quality of life, health, education, protection of civil liberties, economic competitiveness, equality, prosperity and human development. Sweden has been a member of the European Union since 1 January 1995, but remains outside the Eurozone. It is also a member of the United Nations, the Nordic Council, Council of Europe, the World Trade Organization and the Organisation for Economic Co-operation and Development (OECD).\"@en ) ( ?country = <http://dbpedia.org/resource/Sweden> ) ( ?currency = <http://dbpedia.org/resource/Swedish_krona> )"+
		"( ?abs = \"New Sweden (Swedish: Nya Sverige, Finnish: Uusi Ruotsi, Latin: Nova Svecia) was a Swedish colony along the lower reaches of Delaware River in North America from 1638 to 1655 in the present-day American Mid-Atlantic states of Delaware, New Jersey, and Pennsylvania. Fort Christina, now in Wilmington, Delaware, was the first settlement. Along with Swedes and Finns, a number of the settlers were Dutch. New Sweden was conquered by the Dutch in 1655, during the Second Northern War, and incorporated into New Netherland.\"@en ) ( ?country = <http://dbpedia.org/resource/New_Sweden> ) ( ?currency = <http://dbpedia.org/resource/Swedish_riksdaler> )";

		test_string = test_string.substring(1,test_string.length()-1);
		
		String[] list_of_topics = {"?gov", "?abs", "?country","?currency"};
		
		String[] split_list = test_string.split("\\)\\(");
		
		String indata = "Sweden";
		
		Carrier return_carrier = new Carrier();
		
		
		
		for (String s : split_list){
			
			String[] s_str = s.split(" \\) \\( ");
			
			
			for (int i = 0; i < s_str.length; i++){
				
				if (s_str[i].toLowerCase().contains("country") && s_str[i].split(" = ",2)[1].toLowerCase().equals("<http://dbpedia.org/resource/"+indata.toLowerCase()+">")){
					
					for (String s1 : s_str){
						String[] s1_str = s1.split(" = ",2);
						
						if (s1_str[1].length()>6){
							if (s1_str[1].substring(0, 6).equals("<http:")){
								s1_str[1] = rename_http(s1_str[1]);
							}
						}
						
						return_carrier.setSubject(s1_str);
						
						/*
						if (s1_str[0].equals("?gov") || s1_str[0].equals(" ?gov")){
							return_carrier.setGov(s1_str[1]);
						}
						if (s1_str[0].equals("?abs") || s1_str[0].equals(" ?abs")){
							return_carrier.setAbs(s1_str[1]);
						}
						if (s1_str[0].equals("?country") || s1_str[0].equals(" ?country")){
							return_carrier.setCountry(s1_str[1]);
						}
						if (s1_str[0].equals("?city") || s1_str[0].equals(" ?city")){
							return_carrier.setCity(s1_str[1]);
						}
						if (s1_str[0].equals("?currency") || s1_str[0].equals(" ?currency")){
							return_carrier.setCurrency(s1_str[1].trim().replaceAll("^\"|\"$", "").replaceAll("^'|'$", ""));
						}
						*/
					}
				}
			}
		
		
	}
		System.out.println(return_carrier);	

}
	static String rename_http(String http_str){
		String return_str = "";
		
		String[] splitted_http = http_str.split("/");
		return_str = splitted_http[splitted_http.length-1];
		return_str = return_str.substring(0, return_str.length()-1).replaceAll("_", " ");
		
		return return_str;
	}
}