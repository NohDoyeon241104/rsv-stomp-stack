package com.example.rsv_stomp_stack.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @Column(nullable = false, unique = true)
    private String userId;        // 내부 관리 ID (예: "ubisam" 또는 소셜 식별값)

    private String password;      // 관리자용 (일반 소셜 유저는 nullable)

    @Column(nullable = false)
    private String userName;      // 실명 또는 닉네임

    private String companyName;   // 소속 회사 (예: "UbiSam")

    private String email;         // 알림 수신용 이메일

    private String phoneNumber;   // 카카오톡/SMS 알림용 연락처

    // 소셜 로그인 식별값
    private Long kakaoId;         // 카카오 API 고유 번호
    private String googleId;      // 구글 API 고유 번호

    // 알림 수신 설정 (기본값 true)
    @Builder.Default
    private Boolean isEmailNotify = true;
    @Builder.Default
    private Boolean isSmsNotify = false;
    @Builder.Default
    private Boolean isKakaoNotify = true;

    @Column(nullable = false)
    private String role;          // "ROLE_ADMIN" 또는 "ROLE_USER"
}