import java.net.*;
import java.io.*;

public class TextChatServer implements Runnable
{  private ThreadServer C[] = new ThreadServer[50];
   private ServerSocket S = null;
   private Thread T = null;
   private int Count = 0;

   public TextChatServer(int P)
   {  
	try
      {  
		System.out.println("Binding to port ");
         S = new ServerSocket(P);  
         start(); 
	  }
      catch(IOException ioe)
      {  
		System.out.println(ioe.getMessage()); 
	  }
   }
   public void run()
   { 
   while (T != null)
      
	  {  
	  try
         {  
			System.out.println("Client can be connected"); 
            addThread(S.accept()); 
		 }
         catch(IOException ioe)
         {  
		 System.out.println(ioe); 
		 stop(); 
		 }
      }
   }
   public void start()  
   { /* as before */ 
	   if (T == null)
	      {  
		     T = new Thread(this); 
	         T.start();
	      }
   }
   public void stop()   { 
	   /* as before */ 
	   if (T != null)
	      {  
		     T.interrupt(); 
	         T = null;
	      }
	   }
   private int findClient(int ID)
   {  for (int i = 0; i < Count; i++)
         if (C[i].getID() == ID)
            return i;
      return -1;
   }
   public synchronized void handle(int ID, String input)
   {  if (input.equals("quit"))
      {  C[findClient(ID)].send("quit");
         remove(ID); }
      else
         for (int i = 0; i < Count; i++)
            C[i].send(ID + ": " + input);   
   }
   public synchronized void remove(int ID)
   {  int pos = findClient(ID);
      if (pos >= 0)
      {  ThreadServer toTerminate = C[pos];
         System.out.println("Removing client thread " + ID + " at " + pos);
         if (pos < Count-1)
            for (int i = pos+1; i < Count; i++)
               C[i-1] = C[i];
         Count--;
         try
         {  
		 toTerminate.close(); 
		 }
         catch(IOException ioe)
         {  
		 System.out.println(ioe); 
		 }
         toTerminate.interrupt(); }
   }
   private void addThread(Socket Soc)
   {  if (Count < C.length)
      {  System.out.println("Client accepted: " + Soc);
         C[Count] = new ThreadServer(this, Soc);
         try
         {  C[Count].open(); 
            C[Count].start();  
            Count++; }
         catch(IOException ioe)
         {  System.out.println("Error opening thread: " + ioe); } }
      else
         System.out.println("Client refused: maximum " + C.length + " reached.");
   }
   public static void main(String args[]) { 
	   
		  TextChatServer serverobj = null;
	      if (args.length != 1)
	         System.out.println("Enter the port number");
	      else
	         serverobj = new TextChatServer(Integer.parseInt(args[0]));/* as before */ }
}