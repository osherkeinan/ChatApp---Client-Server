package com.lifemichael;

import java.util.LinkedList;
import java.util.List;

public class MessageBoard implements StringConsumer, StringProducer{
    
    private List<StringConsumer> consumers = new LinkedList<StringConsumer>();
    
    @Override
    public void addConsumer(StringConsumer sc) {
	consumers.add(sc);
	
    }

    @Override
    public void removeConsumer(StringConsumer sc) {
	consumers.remove(sc);
	
    }

    @Override
    public void consume(String str) {
	for(StringConsumer consumer : consumers)
	{
	    consumer.consume(str);
	}
	
    }

}
