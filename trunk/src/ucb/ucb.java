/**
 * <p>Titre : UCB</p>
 * <p>Description : Universal Chess Board Driver</p>
 * <p>Copyright : GPL 2002</p>
 * <p>Société : </p>
 * @author porchon@myrealbox.Com
 * @version 2.1
 *
 * Interpret both system side commands to insure Chess Board and xboard
 * communication.
 *
 * By Luc Porchon  <porchon@myrealbox.com>  October, 2002
 * v0.1
 * v0.2
 * v1.5  <porchon@myrealbox.com>  May, 1998
 *       - first Windows version
 * v2.1  <porchon@myrealbox.com>  October, 2002
 *      - First release of Linux version
 * v2.2  <porchon@myrealbox.com>  November, 2002
 *      - Fix output printing error
 *      - Add "\n" for each "move" command
 *      - Add "help" command.
 *
 */

package ucb;

import java.io.*;
import ucb.*;

public class ucb {

  static public  String               applicationName   ="UniversalChessBoard_2.2";
  static boolean quit=false;
  static boolean xboard=false;

  static public boolean setParameter(String command){
    if(command.equals("-help")||command.equals("-?")||command.equals("?")){
      Xboard.out("This is an Universal Chess Board driver written in Java.\n");
      Xboard.out("It can be used like any chess program compatible with\n");
      Xboard.out("xboard protocol. Therefore, refer to Tim Mann's page\n");
      Xboard.out("to get all info to configure either Winboard (MSWindows)\n");
      Xboard.out("or xboard (linux+unix).\n\n");
      Xboard.out("Syntax:\n");
      Xboard.out("java -jar ucb.jar -commSpeed=9600\n");
      Xboard.out("      -streamName=/dev/ttyS0 (or COM1)\n");
      Xboard.out("      -debug\n");
      Xboard.out("      -xboard\n");
      Xboard.out("\nHow to use it \n\nSimply !\n "+
        "You must have javax.comm (comm.jar) already installed\n"+
        "(you can get it at sun's web site).\n"+
        "To manage the RS232 port you need to get the RXTX driver.\n"+
        "Connect your Universal Chessboard (UCB) to your computer.\n"+
        "Edit the file /usr/local/share/ucb/bin/ucb\n"+
        "to set the port variable to the value that correspond to the serial port\n"+
        "number where the board is connected on.\n"+
        "Ex: /dev/ttyS0 or /dev/ttyS1 (COM 1 or COM 2) ...\n"+
        "java -jar ucb.jar -streamName=/dev/ttyS0\n"+
        "Make a connection test:\n"+
        "execute ucb alone; Once the UCB Version shows switch on the chessboard\n"+
        "(PC position).\n"+
        "After a few seconds, UCB send some commands to your PC that displays\n"+
        "there meanings. You can then make a valid move on the board and see it\n"+
        "on your monitor.\n"+
        "On the other way try to type in e7e5 on your keyboard and the corresponding LEDs\n"+
        "must show on the board. Otherwise check your connection port if it corresponds\n"+
        "to the one you declared in ucb file.\n"+
        "To exit from UCB just type \"quit\"\n");;
//      Xboard.out("      -commParity=0\n");
//      Xboard.out("      -commData=8\n");
//      Xboard.out("      -commStopBit=1\n");
      return true;
    }
    if(command.equals("-debug")){
        Xboard.setDebug();
        Xboard.out("ucb.main.Xboard.setDebug()\n");
    }
    if(command.equals("-xboard")){
        xboard=true;
        Xboard.debug("ucb.main.xboard=true\n");
    }
    if(command.startsWith("-commSpeed=")){
      ChessBoard.setCommSpeed( new Integer(command.substring(command.indexOf("=")+1)));
      Xboard.debug("ucb.main.setParameter(commSpeed)="+ChessBoard.getCommSpeed()+"\n");
    }
    if(command.startsWith("-streamName=")){
      ChessBoard.setStreamName(command.substring(command.indexOf("=")+1));
      Xboard.debug("ucb.main.setParameter(streamName)="+ChessBoard.getStreamName()+"\n");
    }
  return false;
  }

  static public void main (String args[]) {

    byte buff[]=new byte[1];
    String s;
    int i;

    Xboard.out(applicationName+"\n");
    if(args.length>0)
      for (i=0;i<args.length;i++){
        if(setParameter(args[i])) return;
      }

    if (!ChessBoard.initialisation()){
      Xboard.out("Error (ucb.main): Chess Board Initialisation failed\n");
      return;
    }

    while(!quit){
     if(!xboard) Xboard.out("ucb> ");
      interpretXboard(Xboard.in());
    }
    ChessBoard.close();
  }

  static public void interpretChessBoard(String readString){
    String promotedPiece="";
    String outString;

    readString=readString.toLowerCase().replace('\r',' ').replace('\n',' ').trim();

    switch (readString.charAt(0)){

      case 'm': {  //MOVE
        if(readString.length()>5){
          //Promotion detected
          promotedPiece=readString.substring(6);
        }
        outString=readString.substring(1,5).concat(promotedPiece);
        if(xboard){
          Xboard.out("move "+outString+"\n");
        }else{
          Xboard.out("\n");
          Xboard.out("move "+outString+"\n");
          Xboard.out("ucb> ");
        }
        break;
      }

      case 't': { //TACK BACK
        break;
      }

      case 'n': { //NEW GAME
        break;
      }

      case 'i': { //Report ID Command
        ChessBoard.out(applicationName);
        Xboard.out(applicationName);
        Xboard.out(" connected on "+ChessBoard.getStreamName()+"\n");
        break;
      }

      case 'e': { //Easy Mode
        Xboard.out("Easy Mode Off\n");
        break;
      }

      case 'v': { //Video Mode
        ChessBoard.out("Video Mode");
        Xboard.out("Select Video Mode\n");
        break;
      }

      case 'a': { //Autoclock off
        ChessBoard.out("Autoclock off");
        Xboard.out("Select Autoclock off\n");
        break;
      }

      case 'x': { //Transmit Moves As Played
        ChessBoard.out("Xmit on");
        Xboard.out("Select Transmit Moves As Played\n");
        break;
      }

      case 'p': { //Board Command
        ChessBoard.out(
          ".rnbqkbnr\r\n.pppppppp\r\n.        \r\n.        \r\n.    P   \r\n.        \r\n.PPPP PPP\r\n.RNBQKBNR-\r\n");
        Xboard.out("Print Board Command\n");
        break;
      }
    }

  }

  static public boolean isMove(String move){
    char[]  cmd = move.toCharArray();
    if(move.length()>=4){
      if ( (cmd[1]>='1') && (cmd[1]<='8') && (cmd[3]>='1') && (cmd[3]<='8')){
        return true;
      }
    }
    return false;
  }

 static void interpretXboard(String command){
    String  move="M"; // Format: Mxy-xy/P

    command=command.toLowerCase().replace('\r',' ').replace('\n',' ').trim();
    if(isMove(command)){
      move=move.concat(command.substring(0,2));
      move=move+"-";
      move=move.concat(command.substring(2,4));
      if(command.length()==5){
        move=move+"/";
        move=move.concat(command.substring(4).toUpperCase());
      }
    ChessBoard.out(move);
    return;
    }

    if(command.equals("quit")){
      Xboard.out("Bye!\n");
      quit=true;
      return;
    }

    if(command.equals("xboard")){
      xboard=true;
      return;
    }

    if(command.equals("help")){
      setParameter("?");
      return;
    }

    if(command.equals("debug")){
      if(Xboard.getDebug()){
        Xboard.out("Reset debug mode\n");
        Xboard.resetDebug();
      } else {
        Xboard.setDebug();
        Xboard.out("Set debug mode\n");
      }
      return;
    }
  }
}