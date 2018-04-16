package com.present.student.service;

import com.alibaba.fastjson.JSONObject;
import com.present.classes.dao.ClassesDao;
import com.present.common.dto.ResponseDto;
import com.present.common.service.BaseService;
import com.present.common.util.CheckUtil;
import com.present.login.dto.StudentLoginSuccessDto;
import com.present.login.service.StudentLoginService;
import com.present.school.dao.AllSchoolDao;
import com.present.student.bean.Student;
import com.present.student.dao.StudentDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Larry-sea on 2017/5/17.
 * <p>
 * 学生获取个人信息
 */
//@Service("checkToken/getStudentInfo")
@Service("getStudentInfo")
public class GetStudentInfoService extends BaseService<StudentLoginSuccessDto> {

    //学生的dao层
    @Autowired
    StudentDao studentDao;
    @Autowired
    ClassesDao classesDao;
    @Autowired
    AllSchoolDao allSchoolDao;

    @Override
    public ResponseDto<StudentLoginSuccessDto> process(JSONObject params, HttpServletRequest request, HttpServletResponse response) {
        CheckUtil.checkEmpty(params, "studentId");
        Student student = studentDao.queryByKey(params.getString("studentId"));
        ResponseDto<StudentLoginSuccessDto> resp = new ResponseDto<StudentLoginSuccessDto>();
        StudentLoginSuccessDto studentLoginSuccessDto = StudentLoginService.convertStudentToStudentLoginSuccessDto(student,null);
        studentLoginSuccessDto.setClassName(getClassNameByClassId(studentLoginSuccessDto.getClassId()));
        studentLoginSuccessDto.setSchoolName(getSchoolNameBySchoolId(studentLoginSuccessDto.getSchoolId()));
        resp.setData(studentLoginSuccessDto);
        return resp;
    }

    /**
     * 获取学校名称
     * @param schoolId
     * @return
     */
    public String getSchoolNameBySchoolId(String schoolId){
        return allSchoolDao.getSchoolNameByKey(schoolId);
    }

    /**
     * 获取班级名称
     * @param classId
     * @return
     */
    public String getClassNameByClassId(String classId){
        return classesDao.queryClassNameById(classId);
    }
}
