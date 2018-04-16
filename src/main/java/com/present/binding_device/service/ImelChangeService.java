package com.present.binding_device.service;

import com.alibaba.fastjson.JSONObject;
import com.present.binding_device.bean.ImelChange;
import com.present.binding_device.dao.ImelChangeDao;
import com.present.common.dto.MessageInfoDto;
import com.present.common.dto.ResponseDto;
import com.present.common.exception.ExternalServiceException;
import com.present.common.service.BaseService;
import com.present.common.util.CheckUtil;
import com.present.common.util.MessageUtil;
import com.present.update.bean.AppVersion;
import com.present.update.dao.AppVersionDao;
import com.present.update.dto.IsNeedToUpdateDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Laiyin on 2017/9/5.
 * <p>
 * 通过imel查询用户id、时间
 * 然后检查id是否匹配，若匹配则放行，
 * 若不匹配则比较时间，若大于50分钟就正常登录和重新绑定账号和设备（即更改imel对应的用户id）
 * 小于则不放行并且提示距离上次登陆为满50分钟不可更改账号登陆
 * 匹配不成功直接走异常所以不需要dto
 */
@Service("checkImelBind")
public class ImelChangeService extends BaseService<String> {

    @Autowired
    ImelChangeDao imelChangeDao;

    /**
     * SimpleDateFormat非线程安全，所以高并发的时候容易出异常所以加个同步锁
     */
    private static final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    public static synchronized String formatDate(Date date) {
        return formatter.format(date);
    }

    @Override
    public ResponseDto<String> process(JSONObject params, HttpServletRequest request, HttpServletResponse response) {
        CheckUtil.checkEmpty(params, "userId", "imel");
        ImelChange imelChange = imelChangeDao.seleteByImel(params.getString("imel"));
        ResponseDto<String> responseDto = new ResponseDto<String>();
        //系统中有此设备的记录
        if (imelChange != null) {
            //userId不匹配
            if (!imelChange.getUserId().equals(params.getString("userId"))) {
                long m = (new Date().getTime() - imelChange.getChangeTime()) / 1000 / 60;
                if (m < 50) {
                    //提示距离上次登陆为满50分钟不可更改账号登陆
                    MessageInfoDto messageInfoDto = MessageUtil.getMessageInfoByKey("imel.binderror");
                    throw new ExternalServiceException(messageInfoDto);
                } else {
                    //若大于50分钟就正常登录和重新绑定账号和设备（即更改imel对应的用户id）
                    imelChangeDao.changeImel(params.getString("imel")
                            , params.getString("userId"), new Date().getTime());
                }
            }
        } else {
            imelChangeDao.addImel(params.getString("imel"), params.getString("userId"), new Date().getTime());
        }
        return responseDto;
    }

}
