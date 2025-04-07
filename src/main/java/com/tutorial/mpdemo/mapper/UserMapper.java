package com.tutorial.mpdemo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tutorial.mpdemo.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
