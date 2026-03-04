package com.example.rsv_stomp_stack.api.robots;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RobotService {

    private final SimpMessagingTemplate messagingTemplate;

    public void processCommand(RobotCommandDTO command) {
        // 비즈니스 로직 처리 (예: 데이터베이스 저장 또는 로봇 상태 검증)
        
        // 처리 완료 후 구독 중인 클라이언트들에게 상태 브로드캐스트
        messagingTemplate.convertAndSend("/topic/status", command);
    }
}