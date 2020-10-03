
public class Helper {
	//check if the user need help menu
		public static void checkHelp(String[] args)
		{
			if(args.length==0)
			{
				System.out.println("Enter httpc help for more infomation");
				System.exit(0);
			}
			else if(args.length==1&&args[0].equalsIgnoreCase("help"))
			{
				System.out.println("httpc is a curl-like application but supports HTTP protocol only.");
				System.out.println("Usage:");
				System.out.println("httpc command [arguments] The commands are:");
				System.out.println("get 	executes a HTTP GET request and prints the response.");
				System.out.println("post 	executes a HTTP POST request and prints the response.");
				System.out.println("help prints this screen.");
				System.out.println("Use \"httpc help [command]\" for more information about a command.");
				System.exit(0);
				
				
			}
			else if (args.length==2&&args[0].equalsIgnoreCase("help")&&args[1].equalsIgnoreCase("get"))
			{
				System.out.print("\r\nusage: httpc get [-v] [-h key:value] URL\r\n" + 
						"Get executes a HTTP GET request for a given URL.\r\n" + 
						"-v Prints the detail of the response such as protocol, status, and headers.\r\n" + 
						"-h key:value Associates headers to HTTP Request with the format 'key:value'.\r\n");
				System.exit(0);
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
				System.exit(0);
			}
			else if(args[0].equalsIgnoreCase("get"))
			{
				httpc.getMethod(args);
			}
			else if(args[0].equalsIgnoreCase("post"))
			{
				httpc.postMethod(args);
			}
			else {
				System.out.println("Command not recognized, Enter httpc help for more infomation");
				System.exit(0);
			}
			
		}
}
