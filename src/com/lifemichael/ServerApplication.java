package com.lifemichael;

import java.awt.List;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;

public class ServerApplication
{
@SuppressWarnings("resource")
public static void main(String args[])
 {
  ServerSocket server = null;
  MessageBoard mb = new MessageBoard();
  try
  {
      server = new ServerSocket(1300,5);
  }
  catch(IOException e)
  {
      System.out.println(e.getMessage());
  }
  Socket socket = null;
  ClientDescriptor client = null;
  ConnectionProxy connection = null;
  InputStream is = null;
  DataInputStream dis = null;
  LinkedList<String> nicknames = new LinkedList<String>();
  while(true)
  {
   try
   {
    socket = server.accept();
    connection = new ConnectionProxy(socket);
    boolean haveNick = false;
    client = new ClientDescriptor("Anonimous");
    connection.addConsumer(client);
    client.addConsumer(mb);    
    while(!(haveNick)){
    connection.consume("Enter nickname");
    is = socket.getInputStream();
    dis = new DataInputStream(is);
	String nickname = dis.readUTF(); 
	if(!(nicknames.contains(nickname)))
	{
	    	client.setNickname(nickname);
	    	connection.consume("Nickname accepted");
	    	haveNick = true;
	    	nicknames.add(nickname);
	    	mb.consume(nickname + " Joined");
	    	mb.addConsumer(connection);
	}
	else
	{
	    connection.consume("Nickname in use. Please choose different.");
	}
    }
        connection.start();
   }
   catch(IOException e)
   {
	   if(is!=null)
		  {
		  try{is.close();}catch(IOException e1){e1.printStackTrace();}
		  }
		  if(dis!=null)
		  {
		  try{dis.close();}catch(IOException e1){e1.printStackTrace();}
		  }
		  if(socket!=null)
		  {
		  try{socket.close();}catch(IOException e1){e1.printStackTrace();}
		  }
   }
  }
  }
}