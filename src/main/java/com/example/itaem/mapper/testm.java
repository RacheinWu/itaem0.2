package com.example.itaem.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

/**
 * @author 计算机系 ITAEM 吴远健
 * @date 2022/2/27 15:53
 */
public interface testm extends BaseMapper<Userx> {

    @Select("insert into t_user(username) values ('中文')")
    void xxx();

}
