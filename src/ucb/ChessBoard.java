/**
 * <p>Titre : UCB</p>
 * <p>Description : Universal Chess Board Driver</p>
 * <p>Copyright : GPL 2002</p>
 * <p>Société : </p>
 * @author porchon@myrealbox.Com
 * @version 2.1
 *
 * Manage the Serial Port to send and receive the command from the Chess Board.
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
import javax.comm.*;
import java.util.*;
import ucb.*;

public class ChessBoard implements Runnable, SerialPortEventListener{

  static private InputStream          inputStream;
  static private String               streamName  ="/dev/ttyS0";
  static private Enumeration          portList;
  static private CommPortIdentifier   portId;
  static private SerialPort           serialPort;
  static private OutputStream         outputStream;

  static private int                  commSpeed       =9600;
  static private int                  commParity      =SerialPort.PARITY_NONE;
  static private int                  commData        =SerialPort.DATABITS_8;
  static private int                  commStopBit     =SerialPort.STOPBITS_1;

  static private Thread               readThread;
  static private ChessBoard           reader;
  static private String               readString      ="";
  static private boolean              quit            =false;


  public void streamName(String name){
    // Initialise the InputStreamName attribut
    streamName=name;
  }

  static public void close(){
    Xboard.debug("chessBoard.close()");
    try{
      inputStream.close();
    } catch (IOException e) {
      Xboard.out("Error (ChessBoard.close-inputStream.close): IOException\n");
    }
    try{
      outputStream.close();
    } catch (IOException e) {
      Xboard.out("Error (ChessBoard.close-outputStream.close): IOException\n");
    }
    serialPort.removeEventListener();
  }



  static public String getStreamName(){
    // Return the InputStreamName attribut
    return streamName;
  }

  static public void setStreamName(String name){
    // Set the InputStreamName attribut
    streamName=name;
  }

  static public void setCommSpeed(Integer value){
    // Initialise the commSpeed attribut
    commSpeed= value.intValue();
  }

  static public int getCommSpeed(){
    return commSpeed;
  }

  static public void setCommParity(int value){
    // Initialise the commparity attribut
    commParity=value;
  }

  static public void setCommData(int value){
    // Initialise the commData attribut
    commData=value;
  }

  static public void setCommStopBit(int value){
  // Initialise the commStopBit attribut
    commStopBit=value;
  }


  private static boolean initOutputStream(){
  // Initialise the input stream. Return true if inputStream open successfuly
        portList = CommPortIdentifier.getPortIdentifiers();

        while (portList.hasMoreElements()) {
            portId = (CommPortIdentifier) portList.nextElement();
            if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL) {
                if (portId.getName().equals(streamName)) {
                    try {
                        serialPort = (SerialPort) portId.open(ucb.applicationName, 2000);
                    } catch (PortInUseException e) {
                        Xboard.out("Error (ChessBoard.initOutputStream-portId.open): PortInUseException\n");
                        return false;
                    }
                    try {
                        outputStream = serialPort.getOutputStream();
                    } catch (IOException e) {
                      Xboard.out("Error (ChessBoard.initOutputStream-serialPort.getOutputStream): IOException\n");
                      return false;
                    }
                    try {
                        serialPort.setSerialPortParams(commSpeed, commData,
                                                          commStopBit, commParity);
                    } catch (UnsupportedCommOperationException e) {
                      Xboard.out("Error (ChessBoard.initOutputStream-serialPort.setSerialPortParams): UnsupportedCommOperationException\n");
                      return false;
                    }
                  return true;
                }
            }
        }
      return false;
  }

  public static boolean initialisation(){
  // chessBoard initialisation
    if (initOutputStream() && initInputStream()){
      return true;
    }
    return false;
  }

  public static void out(String messageString) {
      try {
           messageString=messageString+"\r\n";
           Xboard.debug("chessBoard.messageString="+messageString+"\n"); //LP
           outputStream.write(messageString.getBytes());
      } catch (IOException e) {
           Xboard.out("Error (ChessBoard.out-outputStream.write): IOException\n");
      }
    }


 private static boolean initInputStream() {
      reader = new ChessBoard();
      return true;
 }

 public ChessBoard() {
        try {
            inputStream = serialPort.getInputStream();
        } catch (IOException e) {
          Xboard.out("Error (ChessBoard.ChessBoard-serialPort.getInputStream): IOException\n");
        }
	try {
            serialPort.addEventListener(this);
	} catch (TooManyListenersException e) {
          Xboard.out("Error (ChessBoard.ChessBoard-serialPort.addEventListener): TooManyListenersException\n");
        }
        serialPort.notifyOnDataAvailable(true);
        readThread = new Thread(this);
        readThread.start();
 }

 public void run() {
        Xboard.debug("chessBoard.run()\n"); //LP
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
          Xboard.out("Error (ChessBoard.run-Thread.sleep): InterruptedException\n");
        }
        Xboard.debug("chessBoard.run().out\n"); //LP
 }

 public void serialEvent(SerialPortEvent event) {
        int numBytes=0;
        String bufferString;

        Xboard.debug("chessBoard.serialEvent()\n"); //LP
        switch(event.getEventType()) {
        case SerialPortEvent.BI:
        case SerialPortEvent.OE:
        case SerialPortEvent.FE:
        case SerialPortEvent.PE:
        case SerialPortEvent.CD:
        case SerialPortEvent.CTS:
        case SerialPortEvent.DSR:
        case SerialPortEvent.RI:
        case SerialPortEvent.OUTPUT_BUFFER_EMPTY:
            break;
        case SerialPortEvent.DATA_AVAILABLE:
            byte[] readBuffer = new byte[20];

            Xboard.debug("SerialPortEvent.DATA_AVAILABLE\n"); //LP
            try {
                while (inputStream.available() > 0) {
                    numBytes = inputStream.read(readBuffer);
                }
                bufferString= new String(readBuffer,0,numBytes);
                readString=readString.concat(bufferString);
                Xboard.debug("chessBoard.readString="+readString+"\n"); //LP
                if (bufferString.charAt(numBytes-1)=='\n'){
                  ucb.interpretChessBoard(readString);
                  readString="";
                }
            } catch (IOException e) {
              Xboard.out("Error (ChessBoard.serialEvent-inputStream.read): IOException\n");
            }
            break;
        }
 }

}