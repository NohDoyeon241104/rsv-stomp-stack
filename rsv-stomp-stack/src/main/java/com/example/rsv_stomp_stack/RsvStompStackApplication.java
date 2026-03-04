package com.example.rsv_stomp_stack;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
// import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
// import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;


// 260304 스케줄러 활성화 어노테이션 추가 
// @EnableScheduling // 👈 여기에 추가하면 @Scheduled가 작동하기 시작합니다!
// @EnableJpaRepositories// 리포지토리 경로 명시
// @SpringBootApplication
// DB 설정 안된 경우에 임시  //jpa 관련 자동설정 제외 (실무에선 DB 설정 꼭 해주세요!) 
// @SpringBootApplication(exclude = {
//     DataSourceAutoConfiguration.class,
//     HibernateJpaAutoConfiguration.class
// })



@EnableScheduling 
@EnableJpaRepositories(basePackages = "com.example.rsv_stomp_stack.api.users")
@SpringBootApplication // 👈 이제 자동 설정이 정상 작동하며 DB와 연결됩니다.
public class RsvStompStackApplication {

    public static void main(String[] args) {
        SpringApplication.run(RsvStompStackApplication.class, args);
    }
}