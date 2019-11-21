package com.acongfly.mybatisplusdemo.dao;

import org.apache.ibatis.annotations.Mapper;

import com.acongfly.mybatisplusdemo.model.Teacher;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @author idea
 * @data 2019/5/24
 */
@Mapper
public interface TeacherMapper extends BaseMapper<Teacher> {}
