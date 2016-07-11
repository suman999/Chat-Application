import java.net.*;
import java.io.*;


public class MultiClientServer implements Runnable
{  
   private DataInputStream  DIS  =  null;
   private Socket S = null;
   private ServerSocket Ser = null;
   private Thread T = null;


   public MultiClientServer(int port)
   {  try
      {  
	     System.out.println("Port Bind will happen");
		 System.out.println("Port Number is:"+port);
         Ser = new ServerSocket(port);  
         System.out.println("Server started: " + Ser);
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
      {   
	   try
         {  System.out.println("Waiting for a client ..."); 
            S = Ser.accept();
            System.out.println("Client accepted: " + S);
            OpenCon();
            boolean bQuit = false;
            while (!bQuit)
            {  try
               {  String Input = DIS.readUTF();
                  System.out.println(Input);
                  bQuit = Input.equals("quit");
               }
               catch(IOException ioe)
               {  
            	   bQuit = true; 
            	   // TODO Auto-generated catch block
				   ioe.printStackTrace();
               }
            }
            CloseCon();
         }
         catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
      }
   }
   public void startThread()
   {  if (T == null)
      {  
		 T = new Thread(this); 
         T.start();
      }
   }
   public void stop()
   {  if (T != null)
      {  
		 T.interrupt();
         T = null;
      }
   }
   public void OpenCon() throws IOException
   {  DIS = new DataInputStream(new BufferedInputStream(S.getInputStream()));
   }
   public void CloseCon() throws IOException
   {  
		if (S != null)    
			S.close();
		if (DIS != null)  
			DIS.close();
      
	  System.out.println("Connection Closed at the server side");
   }
   public static void main(String args[])
   {  
	   MultiClientServer server = null;
      if (args.length != 1)
         System.out.println("Enter Port numbers");
      else
         server = new MultiClientServer(Integer.parseInt(args[0]));
   }
}