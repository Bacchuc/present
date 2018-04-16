package com.present.login.service;

import com.alibaba.fastjson.JSONObject;
import com.present.classes.bean.Classes;
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
 * Created by Laiyin on 2017/8/26.
 *
 * 设置密码服务
 */
@Service("settingPassword")
public class SettingPasswordService extends BaseService<String> {
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
        if(!registerVerificationService.isAlreadyRegister(params.getString("phone"))){
            MessageInfoDto messageInfoDto = MessageUtil.getMessageInfoByKey("forgetpwd.phone.notexist");
            throw new ExternalServiceException(messageInfoDto);
        }
        //通过电话查找老师Id
        String result = teacherDao.queryByPhoneForId(params.getString("phone"));
        ResponseDto<String> responseDto= new ResponseDto<String>();
        //如果在老师中查找不了此号码，则去学生中查找号码，同时获取用户类型，并去修改密码
        if(result==null){
            result = studentDao.queryByPhoneForId(params.getString("phone"));
            studentDao.updatePassword(result,params.getString("password"));
        }
        teacherDao.updatePassword(result,params.getString("password"));

        return new ResponseDto<String>();
    }

}
