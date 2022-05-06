package com.example.itaem.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.itaem.VO.PreparerVo;
import com.example.itaem.entity.PreparerDB;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface PreparerMapper extends BaseMapper<PreparerDB> {



    @Select("SELECT t_preparer.name, t_preparer.mail, t_preparer.uid, t_preparer.phonecall, t_preparer.imgPath, " +
            "t_major.major, " +
            "t_direction.direction, " +
            "t_sex.gender, " +
            "t_state.state, " +
            "t_power.power" +
            " FROM" +
            " t_preparer, t_major, t_direction, t_sex, t_state, t_power" +
            " WHERE " +
            " t_preparer.majorId = t_major.id" +
            " AND " +
            " t_preparer.directionId = t_direction.id " +
            " AND " +
            " t_preparer.sexId = t_sex.id" +
            " AND " +
            " t_preparer.stateId = t_state.id " +
            " AND " +
            " t_preparer.powerId = t_power.id " +
            " AND" +
            "phonecall = ${phonecall}")
    PreparerVo getMsg(@Param("phonecall") String phonecall);




}
