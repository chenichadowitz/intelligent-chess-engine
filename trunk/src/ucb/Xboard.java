/**
 * <p>Titre : UCB</p>
 * <p>Description : Universal Chess Board Driver</p>
 * <p>Copyright : GPL 2002</p>
 * <p>Société : </p>
 * @author porchon@myrealbox.Com
 * @version 2.1
 *
 * Manage the communication pipe to send and receive the command from xboard.
 *
 * By Luc Porchon  <porchon@myrealbox.com>  October, 2002
 * v0.1
 * v0.2
 * v1.5  <porchon@myrealbox.com>  May, 1998
 *       - first Windows version
 * v2.1  <porchon@myrealbox.com>  October, 2002
 *      - First release of Linux version
 *
 */
package ucb;

import java.io.*;

public class Xboard{
  static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
  static String inString="";
  static boolean debug=false;


  static public void out(String outString) {
    System.out.print(outString);
    System.out.flush();
  }

  static public void debug(String outString) {
    if(debug){
      System.out.print(outString);
      System.out.flush();
    }
  }

  static public void setDebug() {
    debug=true;
  }

  static public void resetDebug() {
    debug=false;
  }

  static public boolean getDebug() {
    return debug;
  }

  static public String in(){
    try{
      inString = input.readLine();
      }
      catch (IOException e){
        Xboard.out("Error (Xboard.in-IOException): " + inString + "\n");
        }
    return inString;
    }

}