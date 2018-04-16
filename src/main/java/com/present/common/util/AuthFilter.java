package com.present.common.util;

import com.present.common.dto.MessageInfoDto;
import com.present.common.exception.ExternalServiceException;
import com.present.common.util.jwt.Jwt;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * toekn校验过滤器，所有的API接口请求都要经过该过滤器(除了某些接口)
 */
//@WebFilter(urlPatterns = "/externalservice/*")
//@WebFilter(urlPatterns = "/externalservice/checkToken/*")
public class AuthFilter implements Filter {

    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("token过滤器初始化了");
    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        //if(request.getRequestURI().endsWith("checkAppUpdateService")
        //		||request.getRequestURI().endsWith("studentLogin")
        //		||request.getRequestURI().endsWith("teacherLogin")
        //		||request.getRequestURI().endsWith("forgetPassword")
        //		||request.getRequestURI().endsWith("registerVerification")
        //		||request.getRequestURI().endsWith("getAllSchool")
        //		||request.getRequestURI().endsWith("getClassId")
        //		||request.getRequestURI().endsWith("submitStudentInfo")
        //		||request.getRequestURI().endsWith("settingPassword")){
        //	//logo接口不校验token，直接放行
        //	filterChain.doFilter(request, response);
        //	return;
        //}

        //其他API接口一律校验token
        System.out.println("开始校验token");
        //从请求头中获取token
        String token = request.getHeader("token");
        System.out.println("=========================================================="+token);
        Map<String, Object> resultMap = Jwt.validToken(token);
        TokenState state = TokenState.getTokenState((String) resultMap.get("state"));
        switch (state) {
            case VALID:
                //取出payload中数据,放入到request作用域中
                request.setAttribute("data", resultMap.get("data"));
                //request.getRequestDispatcher("checkToken/studentGetAllCourse");
                //放行
                filterChain.doFilter(request, response);
                break;
            case EXPIRED:
            case INVALID:
                System.out.println("无效token");
                //token过期或者无效，则输出错误信息返回
                //JSONObject outputMSg=new JSONObject();
                //outputMSg.put("code", 401);
                //outputMSg.put("info", "token过期或token格式有误");
                //outputMSg.put("data", null);
                //output(outputMSg.toJSONString(), response);
                MessageInfoDto messageInfoDto = MessageUtil.getMessageInfoByKey("token.isexpired");
                throw new ExternalServiceException(messageInfoDto);
            default:
                break;
        }
    }

    public void output(String jsonStr, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=UTF-8;");
        PrintWriter out = response.getWriter();
//		out.println();
        out.write(jsonStr);
        out.flush();
        out.close();
    }

    public void destroy() {

    }
}
