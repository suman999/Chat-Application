
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class TcpClientProgram 
{
	 public static void main(String argv[]) throws Exception  
		{
		 String sentence;
		   String modifiedSentence;
		   while(true)
		   {

			   BufferedReader br = new BufferedReader( new InputStreamReader(System.in));
			   Socket cs = new Socket(argv[0], Integer.parseInt(argv[1]));
			   DataOutputStream outToServer = new DataOutputStream(cs.getOutputStream());
			   BufferedReader inFromServer = new BufferedReader(new InputStreamReader(cs.getInputStream()));
			   sentence = br.readLine();
			   outToServer.writeBytes(sentence + '\n');
			   modifiedSentence = inFromServer.readLine();

			   if("bye".contains(sentence))
			   {	
				   System.out.println( modifiedSentence );
				   cs.close();
				  System.exit(0);
			   }
			   else
			   {
				   System.out.println("FROM SERVER: " + modifiedSentence);
				   continue;
			   }

		   }
		}
		
}
