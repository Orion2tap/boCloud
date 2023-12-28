/**
 * Copyright (C) 2018-2023
 * All rights reserved, Designed By www.flma.tech

 */
package tech.flma.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * @author: ZhangHouYing
 * @date: 2019-08-24 15:44
 */
@Configuration(proxyBeanMethods = false)
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

	@Bean
	public ServerEndpointExporter serverEndpointExporter() {
		return new ServerEndpointExporter();
	}

	@Override
	public void configureMessageBroker(MessageBrokerRegistry config) {
		// 客户端订阅消息的基路径
		config.enableSimpleBroker("/topic");
		// 服务器接收客户端消息的基路径
		config.setApplicationDestinationPrefixes("/app");
	}

	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		// 注册一个 STOMP 端点, 使用 SockJS 协议
		// 设置允许跨域的源 生产环境不能使用 "*"
		registry.addEndpoint("/ws").setAllowedOrigins("*").withSockJS();
	}

}
