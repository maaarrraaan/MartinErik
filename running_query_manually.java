import java.util.Map;

public class running_query_manually {

	public static void main(String[] args) {
		
		Querying q = new Querying();
		String[] context = {""};
		Carrier carrier = q.querying("Agdash", context,"Location");
		
		System.out.println(carrier);
		for (Carrier c : q.getTopThree()){
			System.out.println(c);
		}
		//System.out.println(carrier.returnRaw());
		/*
		for (Carrier c : q.getTopThree()){
			System.out.println(c);
		}
		*/
		//new Interface();
		
		Person p = new Person();
		
	
		if(!(carrier.getScore() == null)){
			Map<String,String> info = p.additionalInfo(carrier);
			
			for(String key : info.keySet()){
				System.out.println(key + "    " + info.get(key));
			}
		}
		
	}

}
