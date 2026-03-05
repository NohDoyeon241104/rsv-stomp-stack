package com.example.rsv_stomp_stack.api.robots;

// 26/03/05 NDY : RosLog 와 RosLogRepository는 ROS2에서 수신한 데이터를 DB에 저장하기 위한 엔티티와 레포지트리이다.
import com.example.rsv_stomp_stack.domain.RobotLog;// 👈 domain 대신 api.robotlogs로 수정!
import com.example.rsv_stomp_stack.api.robotlogs.RobotLogRepository;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import jakarta.annotation.PostConstruct;

// 260305 ROS2 데이터 수신 및 중계 (Base code for ROS2 data reception and relay)
@Service
public class RosBridgeRelayService {
    
    private final SimpMessagingTemplate messagingTemplate;
    private final RobotLogRepository robotLogRepository; // 👈 1. DB 저장을 위한 도구 선언!

    //실제 ROS2 rosbridge 가 구동 중인 주소 (추후 환경변수로 분리 추천)
    private static final String ROSBRIDGE_URL = "ws://localhost:9090";

    // 👈 2. 생성자에 RobotLogRepository 주입 추가
    public RosBridgeRelayService(SimpMessagingTemplate messagingTemplate, RobotLogRepository robotLogRepository) {
        this.messagingTemplate = messagingTemplate;
        this.robotLogRepository = robotLogRepository; 
    }

    @PostConstruct
    public void connectToRosBridge() {
        StandardWebSocketClient client = new StandardWebSocketClient();
        client.execute(new TextWebSocketHandler() {
            
            @Override
            public void afterConnectionEstablished(WebSocketSession session) throws Exception {
                System.out.println("✅ [Spring Boot] ROS2 rosbridge 연결 성공!");
                
                // 1. ROS2 배터리 토픽 구독 요청 (rosbridge 규격 JSON)
                String subscribeBatteryMsg = "{\"op\":\"subscribe\",\"topic\":\"/battery_state\",\"type\":\"sensor_msgs/BatteryState\"}";
                session.sendMessage(new TextMessage(subscribeBatteryMsg));
            }

            @Override
            protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
                String payload = message.getPayload();
                
                // 3. 프론트엔드 Vue 대시보드 화면 렌더링을 위한 STOMP 중계
                // (프론트엔드 useStomp.js의 변환 규칙 적용 완료)
                messagingTemplate.convertAndSend("/topic/robot.battery_state", payload);
                
                // 4. 드디어 DB에 저장(save)하는 로직! (이 코드가 들어가야 import 에러가 사라집니다)
                RobotLog log = new RobotLog();
                log.setRobotId("ugv01");
                log.setLogType("event");
                log.setMessage(payload); // 수신한 JSON 배터리 데이터를 통째로 저장

                robotLogRepository.save(log); // DB에 인서트!
            }
        }, ROSBRIDGE_URL);
    }   
}