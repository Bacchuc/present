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
 * �����������
 */
@Service("settingPassword")
public class SettingPasswordService extends BaseService<String> {
    //ѧ��dao��
    @Autowired
    StudentDao studentDao;
    //��ʦdao��
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
        //ͨ���绰������ʦId
        String result = teacherDao.queryByPhoneForId(params.getString("phone"));
        ResponseDto<String> responseDto= new ResponseDto<String>();
        //�������ʦ�в��Ҳ��˴˺��룬��ȥѧ���в��Һ��룬ͬʱ��ȡ�û����ͣ���ȥ�޸�����
        if(result==null){
            result = studentDao.queryByPhoneForId(params.getString("phone"));
            studentDao.updatePassword(result,params.getString("password"));
        }
        teacherDao.updatePassword(result,params.getString("password"));

        return new ResponseDto<String>();
    }

}
