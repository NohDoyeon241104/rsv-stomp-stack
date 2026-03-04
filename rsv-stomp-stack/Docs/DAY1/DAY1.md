# 📡 STOMP 실시간 통신 학습 — DAY 1
> 2026-03-03 (화) | 프로젝트: `rsv-stomp-stack`

---

## 🗺️ 오늘의 학습 흐름

```
HTTP 한계 이해 → WebSocket/STOMP 개념 → 프로젝트 생성 → 서버 구동 성공
```

---

## 1. 왜 WebSocket인가?

### HTTP의 한계
| 특성 | HTTP/1.1 | WebSocket |
|------|----------|-----------|
| 통신 방향 | 단방향 (클라이언트 → 서버) | 양방향 |
| 서버 먼저 전송 | ❌ 불가 | ✅ 가능 |
| 실시간성 | Polling으로 흉내 (비효율) | 네이티브 지원 |
| 연결 방식 | 요청마다 새 연결 | 한 번 연결 후 유지 |

> 💡 **로봇 제어** 시나리오에서 1초마다 HTTP로 찌르는 **Polling 방식은 비효율적** → WebSocket 필요

### STOMP란?
- WebSocket 위에서 동작하는 **메시지 규약(Protocol)**
- 목적지(destination) 기반으로 메시지를 라우팅
- **발행(Publish) / 구독(Subscribe)** 패턴 지원

---

## 2. 핵심 개념 정리

| 용어 | 설명 |
|------|------|
| **Endpoint** (`/ws`) | 클라이언트가 WebSocket 연결을 맺는 진입점 |
| **Handshake** | HTTP로 시작 → WebSocket 프로토콜로 업그레이드되는 초기 과정 |
| **Broker** (`/topic`) | 메시지를 구독자들에게 뿌려주는 우체국 역할 |
| `/app` prefix | 클라이언트 → 서버 컨트롤러로 향하는 메시지 경로 |
| `/topic` prefix | 서버 → 구독 중인 모든 클라이언트에게 브로드캐스트 |

### 메시지 흐름 요약
```
클라이언트
    │
    ▼  전송: /app/robot/command
[WebSocketConfig] ← setApplicationDestinationPrefixes("/app")
    │
    ▼
[RobotMessageController] @MessageMapping("/robot/command")
    │
    ▼  반환값 브로드캐스트
[Broker] /topic/status
    │
    ▼
구독 중인 모든 클라이언트 (관제 UI 등)
```

---

## 3. 프로젝트 설정 코드

### WebSocket 설정 (`WebSocketConfig.java`)
```java
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws")          // 연결 진입점
                .setAllowedOriginPatterns("*")
                .withSockJS();               // 브라우저 호환성 확보
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/topic", "/queue"); // 브로드캐스트 경로
        registry.setApplicationDestinationPrefixes("/app"); // 컨트롤러 경로 prefix
    }
}
```

### DTO (`RobotCommandDTO.java`)
```java
// Java 17 record — 불변 객체, getter/생성자 자동 생성
public record RobotCommandDTO(
    String robotId,  // 제어 대상 로봇 ID
    String command,  // "MOVE", "STOP"
    double value     // 속도, 각도 등 수치값
) {}
```

### 컨트롤러 (`RobotMessageController.java`)
```java
@Slf4j
@Controller
public class RobotMessageController {

    @MessageMapping("/robot/command")   // 수신: /app/robot/command
    @SendTo("/topic/status")            // 송신: 구독자 전체에게 브로드캐스트
    public RobotCommandDTO handleRobotCommand(RobotCommandDTO command) {
        log.info("수신된 로봇 명령: {}", command);
        return command; // 현재는 Echo 테스트
    }
}
```

---

## 4. 트러블슈팅

```
./mvnw spring-boot:run 
```

### ❗ 오류 1 — DataSource 설정 없음
```
Failed to configure a DataSource: 'url' attribute is not specified
```
**원인:** `pom.xml`에 JPA 의존성이 있어 DB 설정을 요구함  
**해결:** DB 없이 우선 구동하도록 AutoConfiguration 제외
```java
@SpringBootApplication(exclude = {
    DataSourceAutoConfiguration.class,
    HibernateJpaAutoConfiguration.class
})
```

### ❗ 오류 2 — 404 Whitelabel Error Page
```
No static resource . (status=404)
```
**원인:** `src/main/resources/static/index.html` 없음  
**해결:** 정상 동작 — WebSocket 기능과 무관, 무시하고 진행 가능

---

## 5. 구동 확인

```
✅ http://localhost:8080        → 404 (정상, index.html 없음)
✅ ws://localhost:8080/ws       → WebSocket 연결 가능
✅ http://localhost:8080/ws     → SockJS 정상 응답
```

---

## ✅ DAY 1 체크리스트

- [x] HTTP vs WebSocket 차이 이해
- [x] STOMP 프로토콜 개념 파악
- [x] `/app` vs `/topic` prefix 역할 구분
- [x] WebSocketConfig 작성 및 이해
- [x] DTO (record) 작성
- [x] Controller (`@MessageMapping`, `@SendTo`) 작성
- [x] DB 없이 서버 구동 성공

---

> **DAY 2 예고:** 실제 클라이언트(HTML) 작성 → WebSocket 연결 → 메시지 송수신 테스트
심화 메모: 내일 실습 시 @SendTo 대신 SimpMessagingTemplate을 사용하여 ROS2 데이터 릴레이 로직을 구현할 예정.

> **다음 학습 방향 :**
Fleet 설계 메모: 다중 로봇 대응을 위해 RobotCommandDTO의 robotId를 기반으로 메시지를 라우팅하며, 
ROS2 측에서는 Namespace 설정을 통해 토픽 충돌을 방지함.