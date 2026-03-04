🏗️ 실무 관례(CoC)를 적용한 프로젝트 구조
Plaintext

src/main/java/com/example/stomp/
│
├── 📂 domain/                 # 핵심 데이터 모델 (State & Entity)
│      ├── Robot.java          # 로봇의 상태 및 속성 정의
│      └── User.java           # 사용자 정보 정의
│
└── 📂 app/                    # 서비스 진입점 및 기능 단위 (Behavior)
       ├── 📂 robots/          # 로봇 도메인에 특화된 기능들
       │      ├── RobotController.java  # STOMP 엔드포인트 핸들링
       │      ├── RobotHandler.java     # (필요시) 저수준 메시지 처리 로직
       │      ├── RobotService.java     # 비즈니스 로직 및 SimpMessagingTemplate 활용
       │      └── RobotCommandDTO.java  # 데이터 전달 객체 (Java 17 record)
       │
       └── 📂 users/           # 사용자 도메인에 특화된 기능들
              ├── UserController.java
              └── UserService.java


🛠️ 주요 파일 코드 가이드
1. RobotService.java (app/robots/)
컨트롤러에서 직접 로직을 처리하지 않고, 서비스 계층에서 **SimpMessagingTemplate**을 사용해 메시지를 발송하는 구조로 바꿉니다.

Java

package com.example.stomp.app.robots;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RobotService {

    private final SimpMessagingTemplate messagingTemplate;

    public void processAndBroadcast(RobotCommandDTO command) {
        // 여기서 로직 처리 (예: DB 저장, 로봇 상태 체크 등)
        // 완료 후 구독자들에게 브로드캐스트
        messagingTemplate.convertAndSend("/topic/status", command);
    }
}
2. RobotController.java (app/robots/)
컨트롤러는 요청을 받고 서비스로 넘겨주는 역할에 집중합니다.

Java

package com.example.stomp.app.robots;

import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import lombok.RequiredArgsConstructor;

@Slf4j
@Controller
@RequiredArgsConstructor
public class RobotController {

    private final RobotService robotService;

    // 경로 관례: /app/robots/command
    @MessageMapping("/robots/command") 
    public void handleCommand(RobotCommandDTO command) {
        log.info("수신된 로봇 명령: {}", command);
        robotService.processAndBroadcast(command);
    }
}
📝 노사원님을 위한 실무 필기 (CoC 기반)
도메인 분리: domain 폴더의 Robot.java는 순수한 데이터 객체(Entity)로 두고, app 폴더 내의 파일들은 그 데이터를 활용하는 **동작(Behavior)**에 집중합니다.

복수형 사용 (robots, users): 단수형보다 복수형 폴더명을 사용함으로써, 해당 도메인의 모든 리소스를 관리하는 '집합체'임을 명확히 합니다.

패키지 선언 주의: 파일 위치를 옮기면 파일 상단의 package com.example.stomp.app.robots;와 같이 바뀐 경로를 정확히 기입해야 합니다.

.

🏗️ 변경된 CoC 기반 폴더 구조 분석
현재 구조는 회사 세미나에서 강조된 **"관례"**를 잘 따르고 있습니다.

domain/: 로봇(Robot.java)이나 사용자(User.java) 같은 핵심 비즈니스 객체들이 위치합니다. 서비스의 '상태'를 정의하는 곳입니다.

app/: 도메인을 활용한 실제 '기능(Behavior)'들이 모여 있습니다.

robots/: 로봇 제어, 관제 데이터 릴레이 등 로봇과 관련된 모든 로직(Controller, Service, DTO, Handler)을 이 안에 모아 응집도를 높입니다.

users/: 사용자 관리, 권한 등 사용자 도메인 기능을 독립적으로 관리합니다.

config/: WebSocket 설정처럼 인프라적인 공통 설정을 분리하여 도메인 로직에 혼선이 없도록 했습니다.

🛠️ 파일 이동 시 필수 체크리스트
파일을 드래그하여 옮기셨다면, IDE(IntelliJ 등)에서 빨간 줄이 뜰 수 있습니다. 아래 3가지만 확인해 주세요.

1. Package 경로 수정
각 파일 최상단의 package 선언이 물리적 위치와 일치해야 합니다.

RobotController.java: package com.example.rsv_stomp_stack.app.robots;

Robot.java: package com.example.rsv_stomp_stack.domain;

2. Import 문 재정렬
폴더가 달라졌으므로 다른 폴더의 파일을 참조할 때 경로가 바뀝니다.

RobotController에서 domain에 있는 Robot을 쓸 때: import com.example.rsv_stomp_stack.domain.Robot;

3. @MessageMapping 경로 관례 적용
RobotController 내의 매핑 경로도 폴더 구조인 app/robots를 따라가는 것이 CoC 관례상 자연스럽습니다.

Java

@MessageMapping("/robots/command") // 관례를 따라 복수형 사용
public void handleCommand(RobotCommandDTO command) { ... }