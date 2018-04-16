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
 * ͨ��imel��ѯ�û�id��ʱ��
 * Ȼ����id�Ƿ�ƥ�䣬��ƥ������У�
 * ����ƥ����Ƚ�ʱ�䣬������50���Ӿ�������¼�����°��˺ź��豸��������imel��Ӧ���û�id��
 * С���򲻷��в�����ʾ�����ϴε�½Ϊ��50���Ӳ��ɸ����˺ŵ�½
 * ƥ�䲻�ɹ�ֱ�����쳣���Բ���Ҫdto
 */
@Service("checkImelBind")
public class ImelChangeService extends BaseService<String> {

    @Autowired
    ImelChangeDao imelChangeDao;

    /**
     * SimpleDateFormat���̰߳�ȫ�����Ը߲�����ʱ�����׳��쳣���ԼӸ�ͬ����
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
        //ϵͳ���д��豸�ļ�¼
        if (imelChange != null) {
            //userId��ƥ��
            if (!imelChange.getUserId().equals(params.getString("userId"))) {
                long m = (new Date().getTime() - imelChange.getChangeTime()) / 1000 / 60;
                if (m < 50) {
                    //��ʾ�����ϴε�½Ϊ��50���Ӳ��ɸ����˺ŵ�½
                    MessageInfoDto messageInfoDto = MessageUtil.getMessageInfoByKey("imel.binderror");
                    throw new ExternalServiceException(messageInfoDto);
                } else {
                    //������50���Ӿ�������¼�����°��˺ź��豸��������imel��Ӧ���û�id��
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
