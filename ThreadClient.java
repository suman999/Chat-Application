import java.net.*;
import java.io.*;

public class ThreadClient extends Thread
{  private Socket  S   = null;
   private TextChatClient  C   = null;
   private DataInputStream DIS = null;

   public ThreadClient(TextChatClient C1, Socket S1)
   {  
	  C   = C1;
      S   = S1;
      open();  
      start();
   }
   public void open()
   {  
	  try
      {  
		DIS  = new DataInputStream(S.getInputStream());
      }
      catch(IOException ioe)
      {  
		 System.out.println(ioe);
         C.stop();
      }
   }
   public void close()
   {  try
      {  
	  if (DIS != null) 
		DIS.close();
      }
      catch(IOException ioe)
      {  
		System.out.println(ioe);
      }
   }
   public void run()
   {  
	while (true)
      {  try
         {  C.handle(DIS.readUTF());
         }
         catch(IOException ioe)
         {  
			System.out.println(ioe.getMessage());
            C.stop();
         }
      }
   }
}