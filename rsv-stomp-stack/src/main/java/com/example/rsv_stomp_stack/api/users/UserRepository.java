package com.example.rsv_stomp_stack.api.users;

import com.example.rsv_stomp_stack.domain.User;
import org.springframework.data.jpa.repository.JpaRepository; // Spring Data JPA의 JpaRepository 인터페이스를 상속하여 CRUD 메서드 자동 생성  //existsById, findById 같은 CRUD 메서드를 생성
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    // <User, String>은 <엔티티 클래스명, @Id 필드의 타입>을 의미합니다.
}