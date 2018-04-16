package com.present.login.service;

import com.alibaba.fastjson.JSONObject;
import com.present.common.dto.MessageInfoDto;
import com.present.common.dto.ResponseDto;
import com.present.common.exception.ExternalServiceException;
import com.present.common.service.BaseService;
import com.present.common.util.CheckUtil;
import com.present.common.util.MessageUtil;
import com.present.login.bean.Teacher;
import com.present.login.dao.TeacherDao;
import com.present.student.bean.Student;
import com.present.student.dao.StudentDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Laiyin on 2017/6/21.
 *
 * 忘记密码重置密码服务
 */
@Service("forgetPassword")
public class ForgetPasswordService extends BaseService<String> {

    //学生dao层
    @Autowired
    StudentDao studentDao;
    //老师dao层
    @Autowired
    TeacherDao teacherDao;

    @Autowired
    RegisterVerificationService registerVerificationService;

    @Override
    public ResponseDto<String> process(JSONObject params, HttpServletRequest request, HttpServletResponse response) {
        CheckUtil.checkEmpty(params, "phone", "password");
        //手机号不存在则抛异常
        //String phone=params.getString("phone");
        //boolean b=registerVerificationService.isAlreadyRegister(phone);
        if(!registerVerificationService.isAlreadyRegister(params.getString("phone"))){
            MessageInfoDto messageInfoDto = MessageUtil.getMessageInfoByKey("forgetpwd.phone.notexist");
            throw new ExternalServiceException(messageInfoDto);
        }
        //通过电话查找老师Id
        String result = teacherDao.queryByPhoneForId(params.getString("phone"));

        //如果在老师中查找不了此号码，则去学生中查找号码，同时获取用户类型，并去修改密码
        if(result==null){
            result = studentDao.queryByPhoneForId(params.getString("phone"));
            Student student = new Student();
            student.setId(params.getString(result));
            student.setPassword(params.getString("password"));
            studentDao.updateByKey(student);
        }
        Teacher teacher = new Teacher();
        teacher.setId(result);
        teacher.setPassword(params.getString("password"));
        teacherDao.updateByKey(teacher);
        return new ResponseDto<String>();
    }
}
