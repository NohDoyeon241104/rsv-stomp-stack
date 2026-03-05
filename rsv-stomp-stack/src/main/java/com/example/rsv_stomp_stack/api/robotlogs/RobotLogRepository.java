package com.example.rsv_stomp_stack.api.robotlogs;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.rsv_stomp_stack.domain.RobotLog;

@Repository
public interface RobotLogRepository extends JpaRepository<RobotLog, Long> {
    // 추가적인 쿼리 메서드 정의 가능 (예: findByRobotId, findByLogType 등) 
    // 프론트엔드가 '이 로봇의 특정 타입 로그만 줘!' 할 때 쓸 메서드
    List<RobotLog> findByRobotIdAndLogTypeOrderByCreatedAtDesc(String robotId, String logType);
}
