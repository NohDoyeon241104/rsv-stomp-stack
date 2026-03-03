package com.example.rsv_stomp_stack.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.example.rsv_stomp_stack.dto.RobotCommandDTO;

import lombok.extern.slf4j.Slf4j;

// 모든 IMPORT 한 번에 정리 단축키 : Ctrl + Shift + O (vscode 기준)
@Slf4j
@Controller
public class RobotMessageController {

    // 클라이언트가 /app/robot/command로 보낸 메시지를 이 메서드가 처리합니다.
    @MessageMapping("/robot/command")
    // 로직 처리 후 결과를 /topic/status를 구독 중인 모든 클라이언트에게 뿌립니다.
    @SendTo("/topic/status")
    public RobotCommandDTO handleRobotCommand(RobotCommandDTO command) {
        log.info("수신된 로봇 명령: {}", command);
        
        // 실제 비즈니스 로직(DB 저장 등)이 여기서 일어납니다.
        // 지금은 받은 명령을 그대로 응답으로 돌려주어 통신을 확인합니다.
        return command; 
    }
}