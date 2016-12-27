package com.lifemichael;

public class ClientDescriptor implements StringConsumer, StringProducer
 {
    
    private String nickname;
    private StringConsumer consumer = null;
    public ClientDescriptor(String nickname) {
		this.nickname = nickname;
	}
    @Override
    public void addConsumer(StringConsumer sc) {
	this.consumer = sc;
	
    }

    @Override
    public void removeConsumer(StringConsumer sc) {
	this.consumer = null;
	
    }
    

    public String getNickname() {
        return nickname;
    }
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
    @Override
    public void consume(String str) {
	consumer.consume(nickname + ":" + str);
	
    }

}
