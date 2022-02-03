/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Chat_App;



import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mohamed Elgarhy
 */
public class Server_side {
    ServerSocket server; 
     
    public static void main(String[] args){
    new Server_side();
                                          }
    
    public Server_side(){
    
        try
         {
    server =new ServerSocket(5005);
    while (true)
    {
    Socket s=server.accept();
    new ChatHandler(s);
    }
         } catch (IOException ex) {
            Logger.getLogger(Server_side.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    
    }    
}

class ChatHandler extends Thread
{
       DataInputStream dis;
         PrintStream ps;
         
static Vector<ChatHandler> clientsVector=new Vector<ChatHandler>();

public ChatHandler( Socket sc){
    try{
    dis= new DataInputStream(sc.getInputStream());
    ps=new PrintStream(sc.getOutputStream());
    clientsVector.add(this);
    start();
    }      catch (IOException ex) {
               Logger.getLogger(ChatHandler.class.getName()).log(Level.SEVERE, null, ex);
           }
      } 
    public void run()
{
     while(true)
      {
       String str;
         try {
             str = dis.readLine();
               sendMessageToAll(str);
         } catch (IOException ex) {
             Logger.getLogger(ChatHandler.class.getName()).log(Level.SEVERE, null, ex);
         }
        
 
      }
}
        void sendMessageToAll(String msg)
{
            for(ChatHandler ch: clientsVector)
             {
              ch.ps.println(msg);
                }
             }
}

