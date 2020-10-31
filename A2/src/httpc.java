//Comp 445 Assignment1
//Author:Jia GAO


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

import java.util.Scanner;


public class httpc {
	
	public static String headerHolder="";
	public static String url;
	public static String[] urlArray=new String[2];
	public static String domainName="";
	public static String param="";
	public static String postReaderLine="";
	public static String fileOut="";
	public static boolean hasV=false;
	public static boolean hasD=false;
	public static boolean hasF=false;
	public static boolean hasO=false;

	
	public static void main(String[] args) {
			
		Helper.checkHelp(args);
		//getMethod(args);
	    //  postMethod(args);
		
	}
	
	
	
	
	public static void processUrl(String url)
	{
		if (url.contains("//"))
		{
				urlArray=url.split("//");//now the urlArray should be something like https:   www.abc.com/status/418
				if(urlArray[1].contains("/"))
				{
					urlArray=urlArray[1].split("/",2);
					domainName=urlArray[0];
					param=urlArray[1];
					//System.out.println(url);
					//System.out.println(domainName);
					//System.out.println(param);
					
				}
				else
				{
					domainName=urlArray[1];
				}
		}
		else if(url.contains("/"))
		{
			urlArray=url.split("/",2);
			domainName=urlArray[0];
			param=urlArray[1];
			//System.out.println(url);
			//System.out.println(domainName);
			//System.out.println(param);
			
		}
		else {
			domainName=url;
		}
	}
	
	
	
	public static void postMethod(String[] args)
	{
		String bodyMessage="";//stores -d body string
		
		if(args.length==1&&args[0].equalsIgnoreCase("post"))
		{
			System.out.print("\r\nusage: httpc post [-v] [-h key:value] [-d inline-data] [-f file] URL\r\n" + 
					"Post executes a HTTP POST request for a given URL with inline data or from file.\r\n" + 
					"-v Prints the detail of the response such as protocol, status, and headers.\r\n" + 
					"-h key:valueAssociates headers to HTTP Request with the format 'key:value'.\r\n" + 
					"-d stringAssociates an inline data to the body HTTP POST request.\r\n" + 
					"-f fileAssociates the content of a file to the body HTTP POST request.\r\n" + 
					"Either [-d] or [-f] can be used but not both.\r\n");
			System.exit(0);
		}
		for(int i=0;i<args.length;i++)
		{	
			if (args[i].equalsIgnoreCase("-v"))
			{
				hasV=true;
			}
			else if(args[i].equalsIgnoreCase("-h"))
			{
				if(args[i+1].startsWith("-"))
				{
					System.out.print("input format error, please check the helper java httpc help");
					System.exit(1);
					
				}
				else
					headerHolder=headerHolder.concat(args[i+1]+"\r\n");

			}
			else if(args[i].contains("http")||args[i].contains("www."))
			{
				url=args[i];
				processUrl(url);
			}
				
			else if(args[i].equalsIgnoreCase("-d"))
			{
				hasD=true;
				//-d logic goes here
				bodyMessage=bodyMessage.concat(args[i+1]);
				
			}
			else if(args[i].equalsIgnoreCase("-o"))
			{
				hasO=true;
				fileOut=args[i+1];
			}
			else if(args[i].equalsIgnoreCase("-f"))
					{
				hasF=true;
				//-f logic goes here
				//reading files
				
		        try{
		            File file = new File(args[i+1]);
		            Scanner reader=new Scanner(file);
		            while(reader.hasNextLine()) {
		               postReaderLine =postReaderLine.concat(reader.nextLine());
		            }
		            reader.close();
		        }
		        catch(Exception e){
		            System.err.println(e.getMessage());
		        }
		        bodyMessage=postReaderLine;
		        
					}
			
			
		}
		
		if(hasD&&hasF)
		{
			System.out.println("-d and -f cannot be both used in post method, please check the post help");
			System.out.print("\r\nusage: httpc post [-v] [-h key:value] [-d inline-data] [-f file] URL\r\n" + 
					"Post executes a HTTP POST request for a given URL with inline data or from file.\r\n" + 
					"-v Prints the detail of the response such as protocol, status, and headers.\r\n" + 
					"-h key:valueAssociates headers to HTTP Request with the format 'key:value'.\r\n" + 
					"-d stringAssociates an inline data to the body HTTP POST request.\r\n" + 
					"-f fileAssociates the content of a file to the body HTTP POST request.\r\n" + 
					"Either [-d] or [-f] can be used but not both.\r\n");
			System.exit(0);
		}
		
		String result="";
		int spaceCount=0;
		
		try 
		{
		Socket socket=new Socket(domainName, 80);
		PrintWriter pWriter=new PrintWriter(socket.getOutputStream());
		Scanner in = new Scanner(socket.getInputStream());
		//headHolder has /r/n at end already
		pWriter.write("POST /"+param+" HTTP/1.0\r\nContent-Length:"+bodyMessage.length()+"\r\n"+headerHolder+"\r\n"+bodyMessage);
		
		pWriter.flush();
		//if verbose is true give all info
		if(hasV)
		{
			
			if(hasO)
			{
				 try {
					 
					   File myFile = new File(fileOut);
					 
					   if (myFile.createNewFile()){
					    System.out.println("File is created!");
					   }
					   FileWriter myWriter = new FileWriter(fileOut);
					   while(in.hasNextLine())
						{
						   myWriter.write(in.nextLine()+"\r\n");
						}
					   myWriter.close();
					  } 
				 catch (IOException e) {
					   e.printStackTrace();
					  }
					 
					 
			}
			else {
			while(in.hasNextLine())
			{
				System.out.println(in.nextLine());
			}
			}
		}
		
		//verbose is false
		else			
		{
		
			if(hasO)
			{
				 try {
					 
					   File myFile = new File(fileOut);
					 
					   if (myFile.createNewFile()){
					    System.out.println("New file is created!");
					   }
					   FileWriter myWriter = new FileWriter(fileOut);
					   while(in.hasNextLine())
						{
							result=in.nextLine();
						if(result!=null&&result.isEmpty())
						{
							spaceCount++;
							
						}
						else if(result!=null&&spaceCount>=1)
							myWriter.write(result+"\r\n");
						}
							myWriter.close(); 
					  } 
				 catch (IOException e) {
					   e.printStackTrace();
					  }
			}
			else {
			while(in.hasNextLine())
			{
				result=in.nextLine();
			if(result!=null&&result.isEmpty())
			{
				spaceCount++;
				
			}
			else if(result!=null&&spaceCount>=1)
				System.out.println(result);
			}
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
	
	public static void getMethod(String[] args)
	{
		if(args.length==1)//invalid get method
		{
			System.out.print("\r\nusage: httpc get [-v] [-h key:value] URL\r\n" + 
					"Get executes a HTTP GET request for a given URL.\r\n" + 
					"-v Prints the detail of the response such as protocol, status, and headers.\r\n" + 
					"-h key:value Associates headers to HTTP Request with the format 'key:value'.\r\n");
			System.exit(0);
		}
		
		for(int i=0;i<args.length;i++)
		{	
			if (args[i].equalsIgnoreCase("-v"))
			{
				hasV=true;
			}
			else if(args[i].equalsIgnoreCase("-h"))
			{
				if(args[i+1].startsWith("-"))
				{
					System.out.print("input format error, please check the helper java httpc help");
					System.exit(1);
					
				}
				
				headerHolder=headerHolder.concat(args[i+1]+"\r\n");

			}
			else if(args[i].contains("http")||args[i].contains("www."))
			{
				url=args[i];
				processUrl(url);
			}
				
			else if(args[i].equalsIgnoreCase("-d")||args[i].equalsIgnoreCase("-f"))
			{
				System.out.println("Get method should not contain '-d' or '-f', please try again ");
				System.exit(0);
			}
			//output file supported in get
			else if(args[i].equalsIgnoreCase("-o"))
			{
				hasO=true;
				fileOut=args[i+1];
			}
			
		}
		
		
		String result="";
		int spaceCount=0;
		try 
		{
		Socket socket=new Socket("127.0.0.1", 8080);
		//Socket socket=new Socket("https://global.jd.com", 80);
		PrintWriter pWriter=new PrintWriter(socket.getOutputStream());
		Scanner in = new Scanner(socket.getInputStream());
		pWriter.write("GET /"+param+" HTTP/1.0\r\n"+headerHolder+"\r\n");
		//pWriter.write("GET / HTTP/1.0\r\n\r\n");
		pWriter.flush();
		//if verbose is true give all info
		if(hasV)
		{
			
			if(hasO)
			{
				 try {
					 
					   File myFile = new File(fileOut);
					 
					   if (myFile.createNewFile()){
					    System.out.println("File is created!");
					   }
					   FileWriter myWriter = new FileWriter(fileOut);
					   while(in.hasNextLine())
						{
						   myWriter.write(in.nextLine()+"\r\n");
						}
					   myWriter.close();
					  } 
				 catch (IOException e) {
					   e.printStackTrace();
					  }
					 
					 
			}
			else {
			while(in.hasNextLine())
			{
				System.out.println(in.nextLine());
			}
			}
		}
		
		//verbose is false
		else			
		{
			if(hasO)
			{
				 try {
					 
					   File myFile = new File(fileOut);
					 
					   if (myFile.createNewFile()){
					    System.out.println("New file is created!");
					   }
					   FileWriter myWriter = new FileWriter(fileOut);
					   while(in.hasNextLine())
						{
							result=in.nextLine();
						if(result!=null&&result.isEmpty())
						{
							spaceCount++;
							
						}
						else if(result!=null&&spaceCount>=1)
							myWriter.write(result+"\r\n");
						}
							myWriter.close(); 
					  } 
				 catch (IOException e) {
					   e.printStackTrace();
					  }
			}
			else {
			while(in.hasNextLine())
			{
				result=in.nextLine();
			if(result!=null&&result.isEmpty())
			{
				spaceCount++;
				
			}
			else if(result!=null&&spaceCount>=1)
				System.out.println(result);
			}
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
