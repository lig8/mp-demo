package com.tutorial.mpdemo;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tutorial.mpdemo.entity.User;
import com.tutorial.mpdemo.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
class MpDemoApplicationTests {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void testInsert() {
        User user = User.builder()
                .name("li6")
                .age(24)
                .email("li6@126.com")
                .createTime(Instant.now().toEpochMilli())
                .updateTime(Instant.now().toEpochMilli()).build();
        userMapper.insert(user);
    }

    @Test
    public void testFindById() {
        User user = userMapper.selectById(26L);
        System.out.println(user);
    }

    @Test
    public void testFindAll() {
        List<User> users = userMapper.selectList(null);
        if (users != null && !users.isEmpty()) {
            users.forEach(System.out::println);
        } else {
            System.out.println("No users found.");
        }
    }

    @Test
    public void testUpdateById() {
        User user = User.builder()
                .id(28L)
                .email("li33@126.com").build();
        userMapper.updateById(user);

        user = userMapper.selectById(27L);
        user.setEmail("li222@126.com");
        user.setVersion(null);
        user.setUpdateTime(null);
        user.setCreateTime(null);
        userMapper.updateById(user);
    }

    @Test
    public void testDeleteById() {
        userMapper.deleteById(26L);
    }

    @Test
    public void testQueryWrapper1() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("age", 24);
        queryWrapper.likeRight("name", "li");
        queryWrapper.between("age",1,100);
        queryWrapper.select("id","email");
        List<User> users = userMapper.selectList(queryWrapper);
        if (users != null && !users.isEmpty()) {
            users.forEach(System.out::println);
        } else {
            System.out.println("No users found.");
        }
    }

    @Test
    public void testQueryWrapper2() {
        String name = "li";
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.like(StringUtils.hasText(name), "name", name);
        List<User> users = userMapper.selectList(wrapper);
        if (users != null && !users.isEmpty()) {
            users.forEach(System.out::println);
        } else {
            System.out.println("No users found.");
        }
    }

    @Test
    public void testQueryWrapper3() {
        User user = new User();
        user.setName("ligang");
        user.setAge(24);
        QueryWrapper<User> wrapper = new QueryWrapper<>(user);
        List<User> users = userMapper.selectList(wrapper);
        users.forEach(System.out::println);
    }

    @Test
    public void testQueryWrapper4() {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        Map<String, Object> param = new HashMap<>();
        param.put("age", 24);
        param.put("name", "ligang");
        wrapper.allEq(param);
        List<User> users = userMapper.selectList(wrapper);
        users.forEach(System.out::println);
    }

    @Test
    public void testUpdateWrapper() {
        UpdateWrapper updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("age", 24);
        updateWrapper.likeRight("name", "li");
        updateWrapper.between("age",1,100);
        updateWrapper.set("name", "ligang");
        userMapper.update( updateWrapper);
    }

    @Test
    public void testLambdaQuery() {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(User::getName, "ligang").lt(User::getAge, 30);
        List<User> users = userMapper.selectList(wrapper);
        users.forEach(System.out::println);
    }

    @Test
    public void testLambdaUpdate1() {
        LambdaUpdateWrapper<User> wrapper = new LambdaUpdateWrapper<>();
        wrapper.like(User::getName, "lihaha").lt(User::getAge, 80);
        wrapper.set(User::getName, "lihaha2");
        userMapper.update(wrapper);
    }

    @Test
    public void testLambdaUpdate2() {
        User user = new User();
        user.setName("lig888");
        user.setUpdateTime(Instant.now().toEpochMilli());
        LambdaUpdateWrapper<User> wrapper = new LambdaUpdateWrapper<>();
        wrapper.between(User::getAge, 15,31).likeRight(User::getName,"li");
        userMapper.update(user, wrapper);
    }

    @Test
    public void testPage() {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.ge(User::getAge, 20);
        // set page info,find page 3 and 2 records in each page
        Page<User> page = new Page<>(2, 2);
        Page<User> userPage = userMapper.selectPage(page, wrapper);
        System.out.println("total = " + userPage.getTotal());
        System.out.println("pages = " + userPage.getPages());
        System.out.println("current page = " + userPage.getCurrent());
        List<User> records = userPage.getRecords();
        records.forEach(System.out::println);
    }

    @Test
    public void testCustomSQL(){
        List<User> users = userMapper.selectRaw();
        users.forEach(System.out::println);
    }

    @Transactional
    public void concurrentUpdate() {
        User user1 = userMapper.selectById(27L);
        User user2 = userMapper.selectById(27L);

        user1.setVersion(null);
        user1.setEmail("optest1@126.com");
        userMapper.updateById(user1);
        user2.setVersion(null);
        user2.setEmail("optest2@126.com");

        int count = userMapper.updateById(user2);
        if (count == 0) {
            System.out.println("update failed");
        }else {
            System.out.println("update success");
        }
    }

    @Test
    public void testOptimisticLock() {
        concurrentUpdate();
    }
}
