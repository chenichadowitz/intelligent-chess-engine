package main;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Xboard {
	// DIAGRAM: http://www.ascotti.org/programming/chess/xboard.htm
	private static BufferedReader reader;
	private static FileWriter writer;
	
	private static ArrayList<String> AcceptedFeatures = new ArrayList<String>(); 
	private static ArrayList<String> RejectedFeatures = new ArrayList<String>();
	private static ArrayList<String> MovesReceived = new ArrayList<String>();
	private static ArrayList<String> MovesMade = new ArrayList<String>();

	private static boolean quit = false;
	private static int moveCounter = 0;
	private static boolean go = false;
	
	private static Pattern movePat = Pattern.compile("[a-h][1-8][a-h][1-8]");
	
	// Entry point for an XBoard run....
	public static void run(String[] args){
		try{
			reader = new BufferedReader(new InputStreamReader(System.in));
			log("args length: ", ""+args.length);
			if(args.length > 1){
				writer = new FileWriter("/home/cheni/"+args[1], true);
			} else {
				writer = new FileWriter("/home/cheni/xboardOutput.log", true);
			}
		} catch(Exception e){
			//System.exit(-1);
		}
		String input;
		while(true){
			input = getInput();
			parseInput(input);
		}
	}
	
	private static String getInput(){
		try{
			return reader.readLine();
		} catch(Exception e){
			log("WARNING","FAILED READING LINE");
			//System.exit(1);
		}
		return null;
	}
	
	private static void parseInput(String input){
		String response = "";
		Matcher matchMovePat = movePat.matcher(input);		
		
		/* QUIT */
		
		if(input.equals("quit")){
			quit = true;
			try{
				writer.close();
			} catch(Exception e){}
			System.exit(0);
		}
		
		/* XBOARD (interface started)*/
		
		if(input.equals("xboard")){
			//Let the interface know that we're here and listening
			response = "\n";
		}
		
		/* PROTOVER 2 (protocol com v2 */
		
		if(input.equals("protover 2")){
			response = "feature usermove=1 sigint=0 sigterm=0 nps=0 done=1";
		}
		
		/* ACCEPTED (feature accepted) */
		
		if(input.startsWith("accepted ")){
			AcceptedFeatures.add(input.substring(9));
		}
		
		/* REJECTED (feature rejected) */
		
		if(input.startsWith("rejected ")){
			RejectedFeatures.add(input.substring(9));
		}
		
		/* WHITE (play as white?) ****************************** */
		/**
		if(input.equals("white")){
			go = true;
			response = "move h2h4";
		}
		**/
		
		/* USERMOVE (opponent made move) */
		
		if(input.startsWith("usermove ") || matchMovePat.matches()){
			String move;
			if(input.startsWith("usermove ")){
				move = input.substring(9);
			} else {
				move = input;
			}
			MovesReceived.add(move);
			//Opponent made move "move"
			/**
			if(!go){
				if(moveCounter == 0){
					response = "move a7a5";
				} else if(moveCounter == 1){
					response = "move b7b5";
				} else {
					response = "move c7c5";
				}
			} else {
				if(moveCounter == 0){
					response = "move a2a4";
				} else if(moveCounter == 1){
					response = "move b2b4";
				} else {
					response = "move c2c4";
				}
			}
			moveCounter++;
			**/
			MovesMade.add(response);
		}
		
		/* LOG INPUT AND RESPONSE */
		log(input, response);
		
		/* SEND MESSAGE TO XBOARD OPPONENT IN POPUP */
		System.out.println("tellall "+response);
		
		/* Don't send response if it's empty */
		if(!response.equals("")){
			System.out.println(response);
		}
	}
	/*
	private static boolean contains(String needle, ArrayList<String> haystack){
		for(String s : haystack){
			if(needle.equals(s)) return true;
		}
		return false;			
	}
	*/
	private static void log(String input, String response){
		try{
			writer.write(input + " : \"" + response + "\"");
			writer.write("\n");
			writer.flush();
		} catch(Exception e){
			//System.exit(1);
		}
	}
	
	
}
