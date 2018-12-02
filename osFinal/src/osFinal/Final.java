package osFinal;

//import com.wanikani.api.config.Configuration;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.io.*;
import java.util.Scanner;
import java.util.Collections;
import java.util.LinkedList;


public class Final {

	public static void main(String[] args) throws MalformedURLException,IOException{
		// TODO Auto-generated method stub
		Scanner input = new Scanner(System.in);
		System.out.println("Type your wanikani api here: ");
		String key = input.nextLine();
		
		String url = "https://www.wanikani.com/api/user/" + key + "/kanji?callback=define";
		URL obj = new URL(url);
		
		//Here request and reading response codes
		//are taken from https://www.baeldung.com/java-http-request
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("GET");
		
		//con.setConnectTimeout(5000);
		//con.setReadTimeout(5000);
		
		int status = con.getResponseCode();
		BufferedReader in = new BufferedReader(
				new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8));
		String inputLine;
		StringBuffer content = new StringBuffer();
		while((inputLine = in.readLine()) != null)
		{
			content.append(inputLine);
		}
		in.close();
		con.disconnect();
		//end of referenced code. 
		System.out.println(content);
		String example = content.toString();
		
		FileWriter out = new FileWriter("output.txt");
		out.write(example);
		out.close();
		
	}
	
	public LinkedList<Word> trim(String str){
		LinkedList<Word> trimmed = new LinkedList<Word>();
		int loc = str.indexOf("requested_information");
		int level = 0;
		String character = null, meaning = null, on = null, kun = null;
		int hasAnother = str.indexOf("{", loc);;
		while(hasAnother != -1) {
			loc = indexAfter(str, "\"level\": ", loc);
			level = Integer.parseInt(str.substring(loc, str.indexOf(",", loc)));
			loc = indexAfter(str, "\"character\": \"", loc);
			character = str.substring(loc, str.indexOf("\"", loc));
			loc = indexAfter(str, "\"meaning\": \"", loc);
			meaning = str.substring(loc, str.indexOf("\"", loc));
			loc = indexAfter(str, "\"onyomi\": ", loc);	
			if(str.charAt(loc) == 'n') 
				on = "(None)";
			else on = str.substring(loc+1, str.indexOf("\"", loc+1));
			loc = indexAfter(str, "\"kunyomi\": ", loc);	
			if(str.charAt(loc) == 'n') 
				kun = "(None)";
			else kun = str.substring(loc+1, str.indexOf("\"", loc+1));
			trimmed.add(new Word(level, character, meaning, on, kun));
			hasAnother = str.indexOf("{", loc);
		}
			
		return trimmed;
	}
	
	private int indexAfter(String str, String target, int currentPos) {
		return(str.indexOf(target, currentPos) + target.length());
	}
	
	//idea for this implementation comes from https://stackoverflow.com/questions/2784514/sort-arraylist-of-custom-objects-by-property
	public void sort(LinkedList<Word> toSort, char sortBy) {
		switch (sortBy){
		case 'l':	//level
			Collections.sort(toSort, (o1, o2) -> o1.level.compareTo(o2.level));
			break;
		case 'c':	//character
			Collections.sort(toSort, (o1, o2) -> o1.character.compareTo(o2.character));
			break;
		case 'm':	//meaning
			Collections.sort(toSort, (o1, o2) -> o1.meaning.compareTo(o2.meaning));  
			break;
		case 'o':	//onyomi reading
			Collections.sort(toSort, (o1, o2) -> o1.onyomi.compareTo(o2.onyomi)); 
			break;
		case 'k':	//kunyomi reading
			Collections.sort(toSort, (o1, o2) -> o1.kunyomi.compareTo(o2.kunyomi));
		default: 
			System.out.printf("Error: sort type (%c) not found", sortBy);
			break;
		}
	}
	public void writeToFile(LinkedList<Word> toWrite) {
		//not yet implemented
	}
	
	

}
