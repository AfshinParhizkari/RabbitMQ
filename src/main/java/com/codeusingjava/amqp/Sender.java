package com.codeusingjava.amqp;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.codeusingjava.configuration.RabbitMqConfig;

@RestController
public class Sender {

	@Autowired RabbitTemplate rabbitTemplate;//send and receive message
	@Autowired TopicExchange exchange;
	@Autowired RabbitAdmin rabbitAdmin;// RabbitMQ-server's elements published by RabbitAdmin obj from Java code

	@GetMapping(value = "/send/{usr}/{msg}")
	@ResponseStatus(code = HttpStatus.OK)
	public String send(@PathVariable("usr") String routingKey,@PathVariable("msg") String message) {
		Queue queue=new Queue(routingKey,true,false,false,RabbitMqConfig.getArguments());
		//rabbitAdmin.declareExchange(exchange);
		rabbitAdmin.declareQueue(queue);//send Q to RabbitMQ-server
		Binding binding = BindingBuilder.bind(queue).to(exchange).with(routingKey);
		rabbitAdmin.declareBinding(binding);//send Binding to RabbitMQ-server
		rabbitTemplate.convertAndSend(binding.getExchange(), binding.getRoutingKey(), message);
		return "Publisher Acknowledge to Exchange "+binding.getExchange();
	}
}