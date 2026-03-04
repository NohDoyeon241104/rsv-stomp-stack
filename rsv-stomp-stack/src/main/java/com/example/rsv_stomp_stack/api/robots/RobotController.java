package com.example.rsv_stomp_stack.api.robots;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
@RequiredArgsConstructor
public class RobotController {

    private final RobotService robotService;

    // 클라이언트 송신 경로: /api/robots/command
    @MessageMapping("/api/robots/command")
    public void handleRobotCommand(RobotCommandDTO command) {
        log.info("🤖 로봇 명령 수신: {}", command);
        robotService.processCommand(command);
    }
}