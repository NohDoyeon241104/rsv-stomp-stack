package com.example.rsv_stomp_stack.api.visions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.io.File;

@RestController
@RequestMapping("/api/visions") // API 엔드포인트도 복수형 일치
public class VisionController {

    private Process visionProcess;

    @PostMapping("/start")
    public ResponseEntity<String> startVisionServer() {
        if (visionProcess != null && visionProcess.isAlive()) {
            return ResponseEntity.ok("이미 비전 서버가 실행 중입니다.");
        }

        try {
            // Python 서버 독립 실행 로직
            ProcessBuilder pb = new ProcessBuilder("python", "vision_server.py");
            pb.directory(new File("python_vision")); 
            visionProcess = pb.start();
            
            return ResponseEntity.ok("비전 서버 구동을 시작했습니다.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("비전 서버 구동 실패: " + e.getMessage());
        }
    }
    
    // 나중에 비전 서버를 강제로 끄는 API도 이 컨트롤러에 추가하기 좋습니다.
    @PostMapping("/stop")
    public ResponseEntity<String> stopVisionServer() {
        if (visionProcess != null) {
            visionProcess.destroy();
            return ResponseEntity.ok("비전 서버를 종료했습니다.");
        }
        return ResponseEntity.ok("실행 중인 비전 서버가 없습니다.");
    }
}