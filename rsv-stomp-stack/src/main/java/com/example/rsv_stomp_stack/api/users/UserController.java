package com.example.rsv_stomp_stack.api.users;

import com.example.rsv_stomp_stack.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;

    // 사용자 정보 조회 (GET /api/users/ubisam)
    @GetMapping("/{userId}")
    public User getUserInfo(@PathVariable String userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
    }

    // 사용자 정보 수정 (PUT /api/users/ubisam)
    @PutMapping("/{userId}")
    public User updateUserInfo(@PathVariable String userId, @RequestBody User updatedData) {
        return userRepository.findById(userId).map(user -> {
            user.setUserName(updatedData.getUserName());
            user.setCompanyName(updatedData.getCompanyName());
            user.setEmail(updatedData.getEmail());
            user.setPhoneNumber(updatedData.getPhoneNumber());
            user.setIsEmailNotify(updatedData.getIsEmailNotify());
            user.setIsSmsNotify(updatedData.getIsSmsNotify());
            user.setIsKakaoNotify(updatedData.getIsKakaoNotify());
            return userRepository.save(user);
        }).orElseThrow(() -> new RuntimeException("사용자 수정 실패"));
    }
}