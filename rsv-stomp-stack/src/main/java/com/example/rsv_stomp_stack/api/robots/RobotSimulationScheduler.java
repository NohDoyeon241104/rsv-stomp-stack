package com.example.rsv_stomp_stack.api.robots;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RobotSimulationScheduler {

    private final SimpMessagingTemplate messagingTemplate;

    // 2초마다 모든 구독자에게 로봇 상태 전송
    @Scheduled(fixedRate = 2000)
    public void sendRobotLocation() {
        // 실제 ROS2 데이터를 모사한 가상 데이터
        RobotCommandDTO status = new RobotCommandDTO(
            "ROBOT-01", 
            "MOVING", 
            Math.round(Math.random() * 100) / 10.0 // 소수점 한자리 랜덤값
        );

        // 중요: 발행(Broker) 경로는 Prefix(/api)를 붙이지 않고 바로 /topic으로 쏩니다.
        messagingTemplate.convertAndSend("/topic/status", status);
    }
}