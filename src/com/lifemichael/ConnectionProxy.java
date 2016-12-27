package com.lifemichael;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

public class ConnectionProxy extends Thread implements StringConsumer, StringProducer{
    
    private Socket socket;
    private List<StringConsumer> consumers = new LinkedList<StringConsumer>();
    
    public ConnectionProxy(Socket socket) {
	this.socket = socket;
    }
    
    @Override
    public void addConsumer(StringConsumer sc) {
    	consumers.add(sc);
    }

    @Override
    public void removeConsumer(StringConsumer sc) {
	consumers.remove(sc);
	
    }
    
    public void run()
    {
	InputStream is = null;
	DataInputStream dis = null;
	  try {
		while(true)
		{
        	is = socket.getInputStream();
        	dis = new DataInputStream(is);
        	String incomingMsg = dis.readUTF(); 
        	for(StringConsumer consumer: consumers)
        	{
        	consumer.consume(incomingMsg);
        	}
        	
	}
	  } catch (IOException e) {
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
	  finally
	  {
	  if(is!=null)
	  {
	  try{is.close();}catch(IOException e){e.printStackTrace();}
	  }
	  if(dis!=null)
	  {
	  try{dis.close();}catch(IOException e){e.printStackTrace();}
	  }
	  if(socket!=null)
	  {
	  try{socket.close();}catch(IOException e){e.printStackTrace();}
	  }
	  }

	 
    }

    @Override
    public void consume(String str) {
	 OutputStream os = null;
	 DataOutputStream dos = null;
	 try
	{
	 os = socket.getOutputStream();
	 dos = new DataOutputStream(os);
	 dos.writeUTF(str);
	 }
	 catch(IOException e)
	 {
		 if(os!=null)
		  {
		  try{os.close();}catch(IOException e1){e1.printStackTrace();}
		  }
		  if(dos!=null)
		  {
		  try{dos.close();}catch(IOException e1){e1.printStackTrace();}
		  }
		  if(socket!=null)
		  {
		  try{socket.close();}catch(IOException e1){e1.printStackTrace();}
		  }
	 }
	 } 
	} 	

