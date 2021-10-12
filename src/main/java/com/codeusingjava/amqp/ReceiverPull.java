package com.codeusingjava.amqp;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.GetResponse;

@RestController
public class ReceiverPull {

	private static final Logger LOGGER = LoggerFactory.getLogger(ReceiverPull.class);
	@Autowired Connection connection;

	@GetMapping(value = "/pull/{queuename}")
	@ResponseStatus(code = HttpStatus.OK)
	public String getMsg(@PathVariable("queuename") String qName) throws IOException, TimeoutException {
	    String result = "";
   	    Channel channel = connection.createChannel();
	    GetResponse chResponse = channel.basicGet(qName, true);
	    channel.close();
	    if(chResponse != null) {
	        byte[] body = chResponse.getBody();
	        result = new String(body);
	    }
	    LOGGER.info("messages in Queue "+qName+" is: "+result);
	    return "messages in Queue "+qName+" is: "+result;
	}
}