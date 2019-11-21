package com.acongfly.mybatisplusdemo.controller;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.acongfly.mybatisplusdemo.dao.TeacherMapper;
import com.acongfly.mybatisplusdemo.model.Teacher;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

@RestController
@RequestMapping(value = "/teacher")
public class TeacherController {

    @Autowired
    private TeacherMapper teacherMapper;

    @GetMapping(value = "/selectAll")
    public List<Teacher> selectAll() {
        return teacherMapper.selectList(null);
    }

    @GetMapping(value = "/selectAllById")
    public Teacher selectByTeacherName(int id) {
        QueryWrapper<Teacher> teacherQueryWrapper = new QueryWrapper<>();
        teacherQueryWrapper.eq("id", id);
        return teacherMapper.selectOne(teacherQueryWrapper);
    }

    @GetMapping(value = "/selectAllByMap")
    public List<Teacher> selectAllByEntity(String name) {
        Map<String, Object> hashMap = new HashMap<>();
        hashMap.put("teacher_name", name);
        return teacherMapper.selectByMap(hashMap);
    }

    @GetMapping(value = "/selectCountByEntity")
    public int selectCount(String name) {
        Teacher teacher = new Teacher();
        teacher.setId(1);
        teacher.setTeacherName(name);
        QueryWrapper<Teacher> entityWrapper = new QueryWrapper<>(teacher);
        return teacherMapper.selectCount(entityWrapper);
    }

    @GetMapping(value = "/selectAllInPage")
    public List<Teacher> selectAllInPage(int pageNumber, int pageSize) {
        Page<Teacher> page = new Page<>(pageNumber, pageSize);
        QueryWrapper<Teacher> entityWrapper = new QueryWrapper<>();
        entityWrapper.ge("id", 1);
        return teacherMapper.selectPage(page, entityWrapper).getRecords();
    }

    @GetMapping(value = "/selectInIdArr")
    public List<Teacher> selectInIdArr() {
        List<Integer> idList = new ArrayList<>();
        idList.add(1);
        idList.add(10);
        idList.add(11);
        return teacherMapper.selectBatchIds(idList);
    }

    @GetMapping(value = "/insert")
    public void insert() {
        Teacher teacher = new Teacher();
        teacher.setTeacherName(createRandomStr(6));
        teacher.setTeacherPwd(createRandomStr(6));
        teacherMapper.insert(teacher);
    }

    @GetMapping(value = "/delete")
    public void delete() {
        Teacher teacher = new Teacher();
        teacher.setId(11);
        QueryWrapper entityWrapper = new QueryWrapper(teacher);
        teacherMapper.delete(entityWrapper);
    }

    @GetMapping(value = "/update")
    public void update() {
        // update的判断条件
        UpdateWrapper entityWrapper = new UpdateWrapper(new Teacher(1));
        // 更新之后的对象
        Teacher teacher = new Teacher();
        teacher.setTeacherPwd("new-pwd");
        teacherMapper.update(teacher, entityWrapper);
    }

    /**
     * 生成随机字符串
     *
     * @return
     */
    private static String createRandomStr(int length) {
        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(str.length());
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }

    @GetMapping(value = "/selectAllByWrapper1")
    public List<Teacher> selectAllByWrapper1() {
        Map<String, Object> map = new HashMap<>();
        map.put("teacher_name", "name");
        map.put("teacher_pwd", "pwd");
        QueryWrapper entity = new QueryWrapper();
        entity.allEq(map);
        return teacherMapper.selectList(entity);
    }

    @GetMapping(value = "/selectAllByWrapper2")
    public List<Teacher> selectAllByWrapper2() {
        QueryWrapper entity = new QueryWrapper();
        entity.eq("teacher_name", "name");
        return teacherMapper.selectList(entity);
    }

    @GetMapping(value = "/selectAllByWrapper3")
    public List<Teacher> selectAllByWrapper3() {
        UpdateWrapper entity = new UpdateWrapper();
        entity.ne("teacher_name", "name");
        return teacherMapper.selectList(entity);
    }

    @GetMapping(value = "/selectAllByWrapper4")
    public List<Teacher> selectAllByWrapper4() {
        QueryWrapper entity = new QueryWrapper();
        entity.gt("id", "0");
        entity.le("id", 11);
        entity.ne("teacher_name", "null_name");
        entity.like("teacher_name", "tt");
        entity.notLike("teacher_pwd", "sadas");
        entity.orderByAsc("id");
        return teacherMapper.selectList(entity);
    }

    // @GetMapping(value = "/selectAllByWrapper5")
    // public List<Teacher> selectAllByWrapper5() {
    // QueryWrapper entity = new QueryWrapper();
    //// entity.where("id>1").orNew("id=0").and("teacher_name='name'").isNull(true, "teacher_pwd");
    // return teacherMapper.selectList(entity);
    // }

    @GetMapping(value = "/selectAllByWrapper6")
    public List<Teacher> selectAllByWrapper6() {
        QueryWrapper entity = new QueryWrapper();
        entity.groupBy("teacher_name");
        entity.having("id>1");
        return teacherMapper.selectList(entity);
    }

    public static void main(String[] args) {
        System.out.println(createRandomStr(2));
    }
}
