/**
 * 
 */
import java.io.*;
import java.net.*;

/**
 * @author VAIO
 *
 */
public class ClientPro1 
{
 private Socket s = null;
 private BufferedReader BR = null;
 private DataOutputStream DOS = null;
 
 	public ClientPro1(String host, int port)
 	{
		// TODO Auto-generated constructor stub
 		System.out.println(" Connection is getting established ");
 		try 
 		{
			s = new Socket(host,port);
			System.out.println("Connection Established with the socket "+ s);
			start();
					
		} 
 		catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
 		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 		
 		String line ="";
 		while (!line.equals("quit"))
 		{
 		try {
			line = BR.readLine();
			DOS.writeChars(line);
			DOS.flush();
			
		} 
 		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 		}
 		
	stop();
	}
	public void start() throws IOException
		{
			BR = new BufferedReader(new InputStreamReader(System.in));
			DOS = new DataOutputStream(s.getOutputStream());
		}
	public void stop()
	{
		try 
		{
			if(BR != null)
				BR.close();
			if(DOS != null)
				DOS.close();
			if(s != null)
				s.close();
			System.out.println("Connection Closed");
		
		} 
		catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) 
	{
		// TODO Auto-generated method stub
		ClientPro1 CP = null;
		if(args.length != 2)
		{
			System.out.println("Required Port Number for connection establishment");
		}
		else
			CP = new ClientPro1(args[0],Integer.parseInt(args[1]));
		

	}

}
