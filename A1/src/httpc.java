//Comp 445 Assignment1
//Author:Jia GAO

import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class httpc {
	public static String webAddress="www.httpbin.org";
	public static boolean hasV;
	public static void main(String[] args) {
		
	//checkHelp(args);
		getMethod();
		
	}
	
	//check if the user need help menu
	public static void checkHelp(String[] args)
	{
		if(args.length==0)
			System.out.println("Enter httpc help for more infomation");
		else if(args.length==1&&args[0].equalsIgnoreCase("help"))
		{
			System.out.println("httpc is a curl-like application but supports HTTP protocol only.");
			System.out.println("Usage:");
			System.out.println("httpc command [arguments] The commands are:");
			System.out.println("get 	executes a HTTP GET request and prints the response.");
			System.out.println("post 	executes a HTTP POST request and prints the response.");
			System.out.println("help prints this screen.");
			System.out.println("Use \"httpc help [command]\" for more information about a command.");
			
			
		}
		else if (args.length==2&&args[0].equalsIgnoreCase("help")&&args[1].equalsIgnoreCase("get"))
		{
			System.out.print("\r\nusage: httpc get [-v] [-h key:value] URL\r\n" + 
					"Get executes a HTTP GET request for a given URL.\r\n" + 
					"-v Prints the detail of the response such as protocol, status, and headers.\r\n" + 
					"-h key:value Associates headers to HTTP Request with the format 'key:value'.\r\n");
		}
		else if (args.length==2&&args[0].equalsIgnoreCase("help")&&args[1].equalsIgnoreCase("post"))
		{
			System.out.print("\r\nusage: httpc post [-v] [-h key:value] [-d inline-data] [-f file] URL\r\n" + 
					"Post executes a HTTP POST request for a given URL with inline data or from file.\r\n" + 
					"-vPrints the detail of the response such as protocol, status, and headers.\r\n" + 
					"-h key:valueAssociates headers to HTTP Request with the format 'key:value'.\r\n" + 
					"-d stringAssociates an inline data to the body HTTP POST request.\r\n" + 
					"-f fileAssociates the content of a file to the body HTTP POST request.\r\n" + 
					"Either [-d] or [-f] can be used but not both.\r\n");
		}
		else {
			System.out.println("Command not recognized, Enter httpc help for more infomation");
		}
		
	}
	
	
	public static void getMethod()
	{
		
		hasV=false;
		String result="";
		int spaceCount=0;
		try 
		{
		Socket socket=new Socket(webAddress, 80);
		PrintWriter pWriter=new PrintWriter(socket.getOutputStream());
		Scanner in = new Scanner(socket.getInputStream());
		pWriter.write("GET /status/418 HTTP/1.0\r\nUser-Agent: HELLOWORLD\r\n\r\n");
		pWriter.flush();
		//if verbose is true give all info
		if(hasV)
		{
			while(in.hasNextLine())
			{
				System.out.println(in.nextLine());
			}
		}
		
		//verbose is false
		else			
		{
		
			while(in.hasNextLine())
			{
				result=in.nextLine();
			if(result!=null&&result.isEmpty())
			{
				spaceCount++;
				
			}
			if(result!=null&&spaceCount>1)
				System.out.println(result);
			}

		}
		pWriter.close();
		in.close();
		socket.close();
		}
		catch(Exception ex)
		{
			System.err.println(ex);
			
		}
		
	
	}

}
