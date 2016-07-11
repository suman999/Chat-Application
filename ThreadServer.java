import java.net.*;
import java.io.*;

public class ThreadServer extends Thread
{  private TextChatServer Ser    = null;
   private Socket  S = null;
   private int ID  = -1;
   private DataInputStream  DIS  =  null;
   private DataOutputStream DOS = null;

   public ThreadServer(TextChatServer S1, Socket Soc1)
   {  super();
      Ser = S1;
      S = Soc1;
      ID     = S.getPort();
   }
   public void send(String msg)
   {   try
       {  
		  DOS.writeUTF(msg);
          DOS.flush();
       }
       catch(IOException ioe)
       {  System.out.println(ID + " ERROR sending: " + ioe.getMessage());
          Ser.remove(ID);
          stop();
       }
   }
   public int getID()
   {  return ID;
   }
   public void run()
   {  System.out.println("Server Thread " + ID + " running.");
      while (true)
      {  try
         {  Ser.handle(ID, DIS.readUTF());
         }
         catch(IOException ioe)
         {  System.out.println(ID + " ERROR reading: " + ioe.getMessage());
            Ser.remove(ID);
            stop();
         }
      }
   }
   public void open() throws IOException
   {  DIS = new DataInputStream(new 
                        BufferedInputStream(S.getInputStream()));
      DOS = new DataOutputStream(new
                        BufferedOutputStream(S.getOutputStream()));
   }
   public void close() throws IOException
   {  
      if (S != null)    
		S.close();
      if (DIS != null)  
		DIS.close();
      if (DOS != null) 
		DOS.close();
   }
}