package ice;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Xboard {
	
	private static BufferedReader reader =
		  new BufferedReader(new InputStreamReader(System.in));	
	private static FileWriter writer;
	
	private static ArrayList<String> AcceptedFeatures = new ArrayList<String>(); 
	private static ArrayList<String> RejectedFeatures = new ArrayList<String>();
	private static ArrayList<String> MovesReceived = new ArrayList<String>();
	private static ArrayList<String> MovesMade = new ArrayList<String>();

	private static boolean quit = false;
	
	private static Pattern movePat = Pattern.compile("[a-h][1-8][a-h][1-8]");
	
	// Entry point for an XBoard run....
	public static void run(){
		try{
			writer = new FileWriter("output.log", true);
		} catch(IOException e){
			System.exit(-1);
		}
		String input;
		while(!quit){
			input = getInput();
			parseInput(input);
		}
	}
	
	private static String getInput(){
		//while(!reader.()()){
		//}
		try{
			return reader.readLine();
		} catch(IOException e){
			log("WARNING","FAILED READING LINE");
			System.exit(1);
			return null;
		}
	}
	
	private static void parseInput(String input){
		String response = "";
		Matcher matchMovePat = movePat.matcher(input);
		
		if(input.equals(""))
		
		if(input.equals("xboard")){
			//Let the interface know that we're here and listening
			response = "\n";
		}
		if(input.equals("protover 2")){
			response = "feature usermove=1 done=1";
		}
		if(input.startsWith("accepted ")){
			AcceptedFeatures.add(input.substring(9));
		}
		if(input.startsWith("rejected ")){
			RejectedFeatures.add(input.substring(9));
		}
		if(input.startsWith("usermove ") || matchMovePat.matches()){
			String move;
			if(input.startsWith("usermove ")){
				move = input.substring(9);
			} else {
				move = input;
			}
			MovesReceived.add(move);
			//Opponent made move "move"
			
			int a = (int)(8*Math.random() + 1);
			char c = (char)(a+96);
			response = "move " + c + 7 + c + 5;
			while(contains(response, MovesMade)){
				a = (int)(8*Math.random() + 1);
				c = (char)(a+96);
				response = "move " + c + 7 + c + 5;
			}
			MovesMade.add(response);
		}
		log(input, response);
		System.out.println(response);
		System.out.flush();
	}
	
	private static boolean contains(String needle, ArrayList<String> haystack){
		for(String s : haystack){
			if(needle.equals(s)) return true;
		}
		return false;			
	}
	
	private static void log(String input, String response){
		try{
			writer.write(input + " : \"" + response + "\"");
			writer.write("\n");
			writer.flush();
		} catch(IOException e){
			System.exit(1);
		}
	}
	
	
}
