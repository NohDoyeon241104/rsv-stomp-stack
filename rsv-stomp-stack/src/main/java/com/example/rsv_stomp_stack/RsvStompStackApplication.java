package com.example.rsv_stomp_stack;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;

// @SpringBootApplication

// DB 설정 안된 경우에 임시 
@SpringBootApplication(exclude = {
    DataSourceAutoConfiguration.class,
    HibernateJpaAutoConfiguration.class
})
public class RsvStompStackApplication {

	public static void main(String[] args) {
		SpringApplication.run(RsvStompStackApplication.class, args);
	}

}
