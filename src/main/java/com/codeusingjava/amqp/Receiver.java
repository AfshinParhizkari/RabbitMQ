package com.codeusingjava.amqp;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Receiver {

	@Autowired RabbitTemplate rabbitTemplate;

	@GetMapping(value = "/receive/{queuename}")
	@ResponseStatus(code = HttpStatus.OK)
	public String getMsg(@PathVariable("queuename") String qName) throws IOException, TimeoutException {
	    String result = (String) rabbitTemplate.receiveAndConvert(qName);
	    return "Queue: "+qName+" message: "+result;
	}
}