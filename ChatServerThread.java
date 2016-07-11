import java.net.*;
import java.io.*;

public class ChatServerThread extends Thread
{  
   private Socket S = null;
   private MultiServerMultiClient Ser   = null;
   private DataInputStream DIS =  null;
   
   public int i      = -1;

   public ChatServerThread(MultiServerMultiClient Ser1, Socket S1)
   {  
		Ser = Ser1;  
		S = S1;  
		i = S.getPort();
   }
   public void run()
   {  
		String Input="";
		boolean bQuit = false;
		while (!bQuit)
		{  
		try
         {  
		 //System.out.println(DIS.readUTF());
    	  Input = DIS.readUTF();
    	  System.out.println(Input);
    	  
		  if(Input.equals("quit"))
    		  bQuit = true;
    	  
         }
         catch(IOException ioe) 
		 {  
        	 // TODO Auto-generated catch block
			 ioe.printStackTrace();
		 }
      }
   try 
   {
	closeCon();
   } 
catch (IOException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}
   }
   public void openCon() throws IOException
   {  
		DIS = new DataInputStream(new BufferedInputStream(S.getInputStream()));
   }
   public void closeCon() throws IOException
   {  
		if (S != null)    
			S.close();
		if (DIS != null)  
			DIS.close();
		System.out.println("Connection Closed at the server side too");
   }
}