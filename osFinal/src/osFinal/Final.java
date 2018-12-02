package osFinal;

import java.net.*;
import java.nio.charset.StandardCharsets;
import java.io.*;
import java.util.Scanner;
import java.util.LinkedList;

public class Final {
	
	static LinkedList<Word> list;
	static char[] allSortBy = {'l', 'c', 'm', 'o', 'k'};

	public static void main(String[] args) throws MalformedURLException,IOException{
		Scanner input = new Scanner(System.in);
		System.out.println("Type your wanikani api here: (Example api key: 536d16a72c34fa99d3c74b48829f92fd)");
		String key = input.nextLine();
		
		String url = "https://www.wanikani.com/api/user/" + key + "/kanji?callback=define";
		URL obj = new URL(url);
		
		//Here request and reading response codes
		//are inspired by https://www.baeldung.com/java-http-request
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("GET");
		
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
		list = trim(content.toString());
		System.out.println("How would you like to save the data?\nType l for by level, c for by character, m for by meaning, o for by onyomi reading, k for kunyomi reading, or a to make a seperate file for all of these");
		System.out.println("Please type some combination of these characters: l, c, m, o, k, a");
		char[] sortTypes = input.nextLine().replaceAll(" ", "").toCharArray();
		for(char c : sortTypes) {
			if(c == 'a') {
				sortTypes = allSortBy;
				break;
			}
		}
		LinkedList<ChildProcess> children = new LinkedList<ChildProcess>();
		for (char c : sortTypes){
			ChildProcess child = new ChildProcess(list, c);
			children.add(child);
			child.fork();
			}
		for(ChildProcess child : children) {
			child.join();
		}
		System.out.println();
		input.close();
		
	}
	
	public static LinkedList<Word> trim(String str){
		LinkedList<Word> trimmed = new LinkedList<Word>();
		int loc = str.indexOf("requested_information");
		int level = 0;
		String character = null, meaning = null, on = null, kun = null;
		int hasAnother = str.indexOf("{", loc);
		while(hasAnother != -1) {
			loc = indexAfter(str, "\"level\":", loc);
			level = Integer.parseInt(str.substring(loc, str.indexOf(",", loc)));
			loc = indexAfter(str, "\"character\":\"", loc);
			character = str.substring(loc, str.indexOf("\"", loc));
			loc = indexAfter(str, "\"meaning\":\"", loc);
			meaning = str.substring(loc, str.indexOf("\"", loc));
			loc = indexAfter(str, "\"onyomi\":", loc);	
			if(str.charAt(loc) == 'n') 
				on = "(None)";
			else on = str.substring(loc+1, str.indexOf("\"", loc+1));
			loc = indexAfter(str, "\"kunyomi\":", loc);	
			if(str.charAt(loc) == 'n') 
				kun = "(None)";
			else kun = str.substring(loc+1, str.indexOf("\"", loc+1));
			trimmed.add(new Word(level, character, meaning, on, kun));
			hasAnother = str.indexOf("{\"level", loc);
		}
			
		return trimmed;
	}
	
	private static int indexAfter(String str, String target, int currentPos) {
		return(str.indexOf(target, currentPos) + target.length());
	}
	
}
