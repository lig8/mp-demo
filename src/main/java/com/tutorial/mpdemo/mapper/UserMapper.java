package com.tutorial.mpdemo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tutorial.mpdemo.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper<User> {
//    @Select("select * from user where age > 25 and age != 35 or name like 'lig'")
    List<User> selectRaw();
}
