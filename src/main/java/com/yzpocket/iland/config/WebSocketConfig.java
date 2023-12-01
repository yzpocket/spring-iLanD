package com.yzpocket.iland.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration // 스프링 프레임 워크 설정 클래스로 등록
@EnableWebSocketMessageBroker // 웹소켓 서버 활성화하는데 사용한다.
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    // =>  웹소켓 설정을 사용자 정의한다.

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/chatMessage");
        //chatMessage 로 시작하는 목적지 메시지 관리
        config.setApplicationDestinationPrefixes("/app");
        //목적지가 /app 시작 메시지 라우팅
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/websocket");
        // URL을 웹소켓 엔드포인트로 등록한다. 클라이언트는 이 URL을 통해 웹소켓 연결을 초기화
    }

}