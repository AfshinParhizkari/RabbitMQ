package com.codeusingjava.amqp;

import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Count {

	@Autowired RabbitAdmin rabbitAdmin;

	@GetMapping(value = "/count/{qname}")
	public String send(@PathVariable("qname") String qName) {
		return rabbitAdmin.getQueueProperties(qName).get(RabbitAdmin.QUEUE_MESSAGE_COUNT).toString();
	}
}