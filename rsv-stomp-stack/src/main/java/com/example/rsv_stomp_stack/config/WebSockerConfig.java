// 설정 파일 관련 

package com.example.rsv_stomp_stack.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker // STOMP 메시지 브로커 활성화
public class WebSockerConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // 클라이언트(Vue3)가 WebSocket 연결을 시도할 때 사용할 엔드포인트 등록
        registry.addEndpoint("/ws")
                .setAllowedOriginPatterns("*") // CORS 설정 (모든 도메인 허용)
                .withSockJS(); // SockJS 지원 추가
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // 메시지 브로커 설정
        // 서버가 클라이언트에게 메시지를 전달할 때 사용하는  Prefix (상황A)       
        registry.enableSimpleBroker("/topic", "/queue"); // "/topic"으로 시작하는 메시지는 브로커가 처리하도록 설정

        //// 클라이언트가 서버(컨트롤러)로 메시지를 보낼 때 사용하는 Prefix (상황B)
        registry.setApplicationDestinationPrefixes("/api"); // "/api"로 시작하는 메시지는 @MessageMapping이 처리하도록 설정
    }


}