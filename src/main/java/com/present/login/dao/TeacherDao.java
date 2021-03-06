package com.present.login.dao;

import com.present.login.bean.Teacher;
import com.present.student.bean.Student;
import org.apache.ibatis.annotations.Param;


/**
 * 老师登录dao层
 */
public interface TeacherDao {
    /**
     * 插入老师信息
     *
     * @param teacher 插入的数据信息
     */
    void insert(Teacher teacher);

    /**
     * 将密码插入数据库表中
     * @param id
     * @param password
     */
    void updatePassword(@Param("id")String id,@Param("password")String password);

    /**
     * 查找老师信息
     *
     * @param id 查询的数据信息条件
     * @return Teacher 符合条件的数据信息
     */
    Teacher queryByKey(String id);

    /**
     * 更新老师信息
     *
     * @param teacher 更新的数据信息
     * @return 更新数据的行数
     */
    int updateByKey(Teacher teacher);

    /**
     * 删除老师信息
     *
     * @param Id 删除的数据信息
     * @return 删除数据的行数
     */
    int deleteByKey(String Id);


    /**
     * 老师用户登录
     *
     * @param userName 用户名
     * @param password 用户密码
     * @return
     */
    Teacher login(@Param("phone") String userName,@Param("password") String password);


    /**
     * 通过手机号验证是否存在该用户
     *
     * @param phone
     * @return
     */
    int queryByPhone(@Param("phone")String phone);


    /**
     * 通过手机号查找id
     *
     * @param phone
     * @return
     */
    String queryByPhoneForId(@Param("phone")String phone);

}