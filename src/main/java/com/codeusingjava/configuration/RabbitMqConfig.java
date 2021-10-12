package com.codeusingjava.configuration;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

@Configuration
public class RabbitMqConfig {
	
	@Value("${spring.rabbitmq.host}") private String host;
	@Value("${spring.rabbitmq.port}") private Integer port;
	@Value("${spring.rabbitmq.username}") private String username;
	@Value("${spring.rabbitmq.password}") private String password;
	@Value("${rabbitmq.exchange}") private String exchange;

	/*			Sender/Receiver Configuration			*/	
	@Bean
	public TopicExchange topicExchange() {
		return new TopicExchange(exchange,Boolean.TRUE,Boolean.FALSE);
	}
	@Bean 
	public RabbitAdmin getRabbitAdmin(org.springframework.amqp.rabbit.connection.ConnectionFactory connectionFactory) {
		return new RabbitAdmin(connectionFactory); 
	}
	public static Map<String, Object> getArguments() {
		Map<String, Object> arguments=new HashMap<String, Object>();
		arguments.put("x-message-ttl",60480000);//100*60*60*24*7=1Week
		arguments.put("x-expires", 1800000);//idle Queue : 100*60*60*5=5Hours
		arguments.put("x-max-length", 100);//message
		arguments.put("x-max-length-bytes", 3145728);//1024*1024*3=3MByte
		arguments.put("x-queue-mode", "lazy");//Saved message on HDD
		return arguments; 
	}

	/*			Receiver-Pull Configuration			*/	
	@Bean
	public Connection getChannel() throws IOException, TimeoutException {
		ConnectionFactory factory=new ConnectionFactory();
		factory.setHost(host);
		factory.setPort(port);
		factory.setUsername(username);
		factory.setPassword(password);
		return factory.newConnection();
	}
}