package com.example.rsv_stomp_stack.domain;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="robot_logs")
@Getter@Setter
public class RobotLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "robot_id", nullable = false)
    private String robotId; // 예: "ugv01"

    @Column(name = "log_type")
    private String logType; // "command", "navigation", "event"

    @Column(name = "message", columnDefinition = "TEXT")
    private String message; // 로그 내용 (JSON 그대로 넣어도 됨)

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}
