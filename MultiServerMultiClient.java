import java.net.*;
import java.io.*;

public class MultiServerMultiClient implements Runnable
{  
   private ServerSocket     S = null;
   private Thread           T = null;
   private ChatServerThread CT = null;

   public MultiServerMultiClient(int port)
   {  
	try
      {  
		 System.out.println("Port Bind will happen");
		 System.out.println("Port Number is: "+ port);
         S = new ServerSocket(port);  
         System.out.println("Server started: " + S);
         startThread();
      }
      catch (IOException e) 
	  {
			// TODO Auto-generated catch block
			e.printStackTrace();
	  }
   }
   public void run()
   {  while (T!= null)
      {  try
         {  System.out.println("Waiting for a client ..."); 
            addThread(S.accept());
         }
         catch (IOException e) 
	     {
			// TODO Auto-generated catch block
			e.printStackTrace();
	     }
      }
   }
   public void addThread(Socket socket)
   {  
	  System.out.println("Client accepted: " + socket);
      CT = new ChatServerThread(this, socket);
      try
      {  
		 CT.openCon();
         CT.start();

         //client.run();
      }
      catch(IOException ioe)
      {  
		System.out.println("Error opening thread: " + ioe); 
	  }
   }
   
   public void stopThread()
   {  
	   if (T != null)
      {  
	  //thread.stop(); 
	    T.interrupt();
        T = null;
      }
   }
   public void startThread()
   {  
	   if (T == null)
      {  T = new Thread(this); 
         T.start();
         
      }
   }

   public static void main(String args[])
   { 
	   MultiServerMultiClient serverobj = null;
	      if (args.length != 1)
	         System.out.println("Enter Port number");
	      else
	         serverobj = new MultiServerMultiClient(Integer.parseInt(args[0]));
	      
   }
}