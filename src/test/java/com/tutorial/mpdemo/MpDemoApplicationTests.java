package com.tutorial.mpdemo;

import com.tutorial.mpdemo.entity.User;
import com.tutorial.mpdemo.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.Instant;

@SpringBootTest
@ActiveProfiles("test")
class MpDemoApplicationTests {

    @Autowired
    private UserMapper userMapper;

    @Test
    void testInsert() {
        Long now = Instant.now().toEpochMilli();
        User user = User.builder()
                .name("li3")
                .age(24)
                .email("li3@126.com")
                .createTime(now)
                .updateTime(now).build();
        userMapper.insert(user);
    }

}
