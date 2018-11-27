package osFinal;

//import com.wanikani.api.config.Configuration;
import java.net.*;
import java.io.*;
import java.util.Scanner;

public class Final {

	public static void main(String[] args) throws MalformedURLException{
		// TODO Auto-generated method stub
		Scanner input = new Scanner(System.in);
		System.out.println("Type your wanikani api here: ");
		String key = input.nextLine();
		
		URL url = new URL("https://www.wanikani.com/api/user/" + key + "/kanji?callback=define");

	}

}
