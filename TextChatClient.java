import java.net.*;
import java.io.*;

public class TextChatClient implements Runnable
{  private Socket S  = null;
   private Thread T  = null;
   private BufferedReader  BR   = null;
   private DataOutputStream DOS = null;
   private ThreadClient TC    = null;

   public TextChatClient(String serverName, int serverPort)
   {  
      try
      {  S = new Socket(serverName, serverPort);
         System.out.println("Connected: " + S);
         start();
      }
      catch(UnknownHostException uhe)
      {  System.out.println(uhe.getMessage()); }
      catch(IOException ioe)
      {  System.out.println(ioe.getMessage()); }
   }
   public void run()
   {  while (T != null)
      {  try
         {  DOS.writeUTF(BR.readLine());
            DOS.flush();
         }
         catch(IOException ioe)
         {  
		 System.out.println(ioe.getMessage());
            stop();
         }
      }
   }
   public void handle(String str)
   {  
   if (str.equalsIgnoreCase("quit"))
      {  
		System.out.println("Good bye. Press RETURN to exit ...");
         stop();
      }
      else
         System.out.println(str);
   }
   public void start() throws IOException
   {  
		BR = new BufferedReader(new InputStreamReader(System.in));
      DOS = new DataOutputStream(S.getOutputStream());
      if (T == null)
      {  
    	 TC = new ThreadClient(this, S);
         T = new Thread(this);                   
         T.start();
      }
   }
   public void stop()
   {  
	if (T != null)
      {  
		 T.interrupt();  
         T = null;
      }
      try
      {  if (BR != null)  
			BR.close();
         if (DOS != null)  
			DOS.close();
         if (S!= null)  
			S.close();
      }
      catch(IOException ioe)
      {  
	  System.out.println("Connection cant be closed"); 
	  }
      TC.close();  
      TC.interrupt();
   }
   public static void main(String args[])
   {  
	  TextChatClient clientObj = null;
      if (args.length != 2)
         System.out.println("Enter host name and port");
      else
         clientObj = new TextChatClient(args[0], Integer.parseInt(args[1]));
   }
}