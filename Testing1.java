import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;


/*	
 *  Testing our knowledge base implementation by running several questions, in form of parameters, towards the knowledge base.
 * 	The result could be evaluated to be correct or partially correct. This is done by comparing a pre-defined answer toward the presented
 * 	answer. The test file should be composed like this:
 * 
 * 				Entity%%Type of Entity%%Context split with an ';'%%Correct answer
 * 
 *  The double %% is used to split the different values. To comment out an entire row simply start the row with three dashes ('---').
 *  The result will be written to a log file and summarized as number of questions, number of correct answers and number of partially correct
 *  answers. 
 */
public class Testing1 {
	public static void main(String[] args) throws IOException {
	
	    Querying q = new Querying();
	    
			try {
				PrintWriter writer = new PrintWriter("testingLog.txt", "UTF-8");
				BufferedReader br = new BufferedReader(new FileReader("testfile2.txt"));
				
				String next_line = br.readLine();
				
				int row_count = 0;								//Counts the number of questions
				int correct_answer = 0;							//Counts the number of correct answers
				int part_correct = 0;							//Counts the number of partially correct answers
				int not_answered = 0;							//Counts the number of not answered questions
				while (next_line != null){
					if (!next_line.startsWith("---")){
						
						String[] list_of_values = next_line.split("%%");
						
						for (int i = 0; i< list_of_values.length; i++){
							if (list_of_values[i].equals("-")){
								list_of_values[i] = "";
							}
						}
						
						if (list_of_values[1].equals("Person")){
							Carrier carrier = q.querying(list_of_values[0], list_of_values[2].split(";"), "Person");
							
							String answer = ""+carrier.getID();
							
							if (!answer.equals("")){
								answer = carrier.rename_http(answer.replaceAll("_", " ").toLowerCase());
							}
							
							if (answer.equals(list_of_values[3].toLowerCase())){
								correct_answer ++;
								writer.println("CORRECT");
							}else if(answer.contains(list_of_values[3].toLowerCase())){
								part_correct ++;
								writer.println("PARTIALLY CORRECT");
							}else if(answer.equals("")){
								not_answered ++;
								writer.println("NOT ANSWERED");
							}else{
								writer.println("WRONG");
							}
							
							writer.println("Answer: " + answer);
							writer.println("Correct answer: " + list_of_values[3].toLowerCase());
							writer.println("Full line: " + next_line );
							writer.println("");
							
							
							
						} else if (list_of_values[1].equals("Location_country")){
							
							Carrier carrier = q.querying(list_of_values[0], list_of_values[2].split(";"), "Country");
							
							String answer = carrier.reformat_numbers(carrier.getValue("dbo:populationTotal"));
							
							if (answer.equals(list_of_values[3].toLowerCase())){
								correct_answer ++;
								writer.println("CORRECT");
							}else if(answer.contains(list_of_values[3].toLowerCase())){
								part_correct ++;
								writer.println("PARTIALLY CORRECT");
							}else if(answer.equals("")){
								not_answered ++;
								writer.println("NOT ANSWERED");
							}else{
								writer.println("WRONG");
							}
							
							
							writer.println("Answer: " + answer);
							writer.println("Correct answer: " + list_of_values[3].toLowerCase());
							writer.println("Full line: " + next_line);
							writer.println("");
							
							
						} else if (list_of_values[1].equals("Location_city")){
							
							Carrier carrier = q.querying(list_of_values[0], list_of_values[2].split(";"), "Location");
							
							String answer = carrier.rename_http(carrier.getValue("dbo:country").replaceAll("_", " ").toLowerCase());
							
							if (answer.equals(list_of_values[3].toLowerCase())){
								correct_answer ++;
								writer.println("CORRECT");
							}else if(answer.contains(list_of_values[3].toLowerCase())){
								part_correct ++;
								writer.println("PARTIALLY CORRECT");
							}else if(answer.equals("")){
								not_answered ++;
								writer.println("NOT ANSWERED");
							}else{
								writer.println("WRONG");
							}
							
							
							writer.println("Answer: " + answer);
							writer.println("Correct answer: " + list_of_values[3].toLowerCase());
							writer.println("Full line: " + next_line );
							writer.println("");
							
							
						} else if (list_of_values[1].equals("Organisation")){
							
							Carrier carrier = q.querying(list_of_values[0], list_of_values[2].split(";"), "Organisation");
							
							String answer = ""+carrier.getID();
							if (!answer.equals("")){
								answer = carrier.rename_http(answer.replaceAll("_", " ").toLowerCase());
							}
							
							if (answer.equals(list_of_values[3].toLowerCase())){
								correct_answer ++;
								writer.println("CORRECT");
							}else if(answer.contains(list_of_values[3].toLowerCase())){
								part_correct ++;
								writer.println("PARTIALLY CORRECT");
							}else if(answer.equals("")){
								not_answered ++;
								writer.println("NOT ANSWERED");
							}else{
								writer.println("WRONG");
							}
							
							
							writer.println("Answer: " + answer);
							writer.println("Correct answer: " + list_of_values[3].toLowerCase());
							writer.println("Full line: " + next_line);
							writer.println("");
							
							
						} else if (list_of_values[1].equals("Person_more")){
							
							Carrier carrier = q.querying(list_of_values[0], list_of_values[2].split(";"), "Person");
							
							String answer = carrier.reformat_numbers(carrier.getValue("dbo:birthDate"));
							
							if (answer.equals(list_of_values[3].toLowerCase())){
								correct_answer ++;
								writer.println("CORRECT");
							}else if(answer.contains(list_of_values[3].toLowerCase())){
								part_correct ++;
								writer.println("PARTIALLY CORRECT");
							}else if(answer.equals("")){
								not_answered ++;
								writer.println("NOT ANSWERED");
							}else{
								writer.println("WRONG");
							}
							
							
							writer.println("Answer: " + answer);
							writer.println("Correct answer: " + list_of_values[3].toLowerCase());
							writer.println("Full line: " + next_line );
							writer.println("");
							
						} 
						
						
						row_count ++;
					
					}
					System.out.println(next_line);
					next_line = br.readLine();
					
				}
				
				
				System.out.println("Number of questions: " + row_count);
				System.out.println("Number of correct answers: " + correct_answer);
				System.out.println("Number of partially correct answers: " + part_correct);
				System.out.println("Number of not answered questions: " + not_answered);
				System.out.println("Number of wrong answers: "+ (row_count-correct_answer-part_correct-not_answered));
				
				
				br.close();
				writer.close();
				
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
	}

}
