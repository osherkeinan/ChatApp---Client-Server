package com.lifemichael;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.Socket;
import javax.swing.*;
import javax.swing.text.DefaultCaret;

public class ClientGUI implements StringConsumer, StringProducer {
	
	public static String serverIp = "10.12.1.9";   // Just set the server IP and chat up! :)

    private ConnectionProxy consumer;
    private JFrame frame;
    private JPanel buttomPanel;
    private JButton sendButton;
    private JTextField text;
    private JTextArea area;
    private JScrollPane scroll;
    private String nickname;
    
    public ClientGUI()
    {
    	frame = new JFrame("ChatUp");
    	buttomPanel = new JPanel();
    	sendButton = new JButton("SEND");
    	text = new JTextField();
    	area = new JTextArea();
    	scroll = new JScrollPane(area);
    	
    	
    }
    
    public void start(ClientGUI client)
    {
    	frame.setLayout(new BorderLayout());
    	scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    	area.setBackground(Color.gray);
    	area.setEnabled(false);
    	frame.getRootPane().setDefaultButton(sendButton);
    	frame.addWindowListener(new WindowAdapter() {
    		public void WindowClosing(WindowEvent e)
    		{
    			removeConsumer(null);
    			System.exit(0);
    		}
		});
    	sendButton.addActionListener(new ActionListener() 
		{
			
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				if(!(text.getText().equals("")))
				{
				client.send(text.getText().trim());
				}
			}
		});
    	buttomPanel.setLayout(new BorderLayout());
    	buttomPanel.add(text, BorderLayout.CENTER);
    	buttomPanel.add(sendButton, BorderLayout.EAST);
    	DefaultCaret caret = (DefaultCaret)area.getCaret();
    	caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
    	area.setLineWrap(true);
    	area.setWrapStyleWord(true);
    	frame.add(scroll,BorderLayout.CENTER);
    	frame.add(buttomPanel, BorderLayout.PAGE_END);
    	frame.setSize(350, 500);
    	frame.setVisible(true);
    }
    
    @Override
    public void addConsumer(StringConsumer sc) {
	this.consumer =(ConnectionProxy) sc;
	
    }

    @Override
    public void removeConsumer(StringConsumer sc) {
	this.consumer = null;
	
    }

    @Override
    public void consume(String str) {
    	if(str.equals("Nickname accepted"))
    	{
    		area.setText(str + "\n");
    		frame.setTitle("ChatUp - " + nickname);
    	}
    	else
    	{
    		area.append(str + "\n");
    	}
    	text.setText("");
    }
    
    public void send(String str)
    {
    	consumer.consume(str);
    	nickname = str;
    }
    
    public static void main(String args[])
    {
    	SwingUtilities.invokeLater(new Runnable(){
    			
    			@Override
    			public void run() {
    				ClientGUI client = new ClientGUI();
    		    	client.start(client);
    		    	Socket socket = null;
    		        	
    		    	try {
    					socket = new Socket(serverIp, 1300);
    			    	ConnectionProxy proxy = new ConnectionProxy(socket);
    			    	client.addConsumer(proxy);
    			    	proxy.start();
    			    	proxy.addConsumer(client);
    				} catch (IOException e) {
    					e.printStackTrace();
    				}
    		    	finally {
    		    		
    				}
				}}
    			);
    	
    	

    }

}
