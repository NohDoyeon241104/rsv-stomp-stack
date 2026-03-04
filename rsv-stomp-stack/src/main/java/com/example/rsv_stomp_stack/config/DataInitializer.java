package com.example.rsv_stomp_stack.config;

import com.example.rsv_stomp_stack.domain.User;
import com.example.rsv_stomp_stack.api.users.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initAdminAccount(UserRepository userRepository) {
        return args -> {
            // "ubisam" ID가 없을 경우에만 초기 관리자 생성
            if (!userRepository.existsById("ubisam")) {
                User admin = User.builder()
                        .userId("ubisam")
                        .password("admin1234") // 실무에선 암호화 필요
                        .userName("노도연")
                        .companyName("UbiSam")
                        .email("nody1104@gmail.com")
                        .role("ROLE_ADMIN")
                        .build();
                userRepository.save(admin);
                System.out.println("✅ 관리자 계정(ubisam)이 생성되었습니다.");
            }
        };
    }
}