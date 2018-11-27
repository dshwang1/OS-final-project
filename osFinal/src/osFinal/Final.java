package osFinal;

//import com.wanikani.api.config.Configuration;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.io.*;
import java.util.Scanner;


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

}
