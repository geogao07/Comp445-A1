import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;



public class httpfs {
	public static void main(String args[]) throws IOException
	{
		
		int port=8080;//set default port if -p is not defined
		boolean verbose=false;
		boolean help=false;		
		String pathToDir=System.getProperty("user.dir");
		
		for(int i=0;i<args.length;i++)
		{
			if(args[i].equalsIgnoreCase("-v"))
			{
				verbose=true;
			}
			if(args[i].equalsIgnoreCase("-p"))
			{
				try 
				{
					port=Integer.parseInt(args[i+1]);
				}
				catch(Exception e)
				{
					System.out.print("invalid port input");
				}
			}
			if(args[i].equalsIgnoreCase("-d"))
			{
				
				pathToDir=args[i+1];
			}
			if(args[i].equalsIgnoreCase("help"))
			{
				help=true;
			}
		}
		
		if(help==true)
		{
			PrintHelpMessage();
		}
		if(port<1024||port>65535)
		{
			System.err.print("Invalid port number, must in the range of 1024 and 65535");
		}
		//need to implement 
		if(verbose==true)
		{
			//do something here
		}
		else
		{
			//do something else
		}
		
		
		//initiate server
		try {
		ServerSocket serverSocket =new ServerSocket(port,1,InetAddress.getLoopbackAddress());
		System.out.print("Server now listening on prot: "+port+"\r\nWorking Dir is: "+pathToDir+"\r\n");
		Socket client=serverSocket.accept();
		BufferedReader bReader=new BufferedReader(new InputStreamReader(client.getInputStream()));
		PrintWriter out=new PrintWriter(client.getOutputStream());
		
		int c;
		StringBuilder sBuilder=new StringBuilder();
		
		while((c=bReader.read())!=-1)
		{
			System.out.print((char)c);
			//sBuilder.append((char)c);
		}
		
		System.out.print(sBuilder.toString());
		/*out.close();
		bReader.close();
		client.close();
		serverSocket.close();*/
		
		
		}
		catch(Exception e)
		{
			System.err.print(e.getMessage());
		}
		/*PrintWriter out=new PrintWriter(client.getOutputStream());
		String body="Hello World";
		out.print("HTTP/1.0 200 OK\r\nContent-Type:text/html\r\nContent-Length:"+body.length()+"\r\n\r\n"+body);
		out.flush();*/
		
		
		
		
		
	}
	
	public static void PrintHelpMessage()
	{
		System.out.println("Usage\r\n" + 
				"httpfs is a simple file server.\r\n" + 
				"usage: httpfs [-v] [-p PORT] [-d PATH-TO-DIR]\r\n" + 
				"-v Prints debugging messages.\r\n" + 
				"-p Specifies the port number that the server will listen and serve at.\r\n" + 
				"Default is 8080.\r\n"+
				"-d Specifies the directory that the server will use to read/write requested files."+
				" Default is the current directory when launching the application."
				);
	}
}
