package com.present.update.service;

import com.alibaba.fastjson.JSONObject;
import com.present.common.dto.MessageInfoDto;
import com.present.common.dto.ResponseDto;
import com.present.common.exception.ExternalServiceException;
import com.present.common.service.BaseService;
import com.present.common.util.CheckUtil;
import com.present.common.util.DateUtil;
import com.present.common.util.MessageUtil;
import com.present.update.bean.AppVersion;
import com.present.update.dao.AppVersionDao;
import com.present.update.dto.IsNeedToUpdateDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Laiyin on 2017/9/2.
 *
 * 检查应用更新服务
 */
@Service("checkAppUpdateService")
public class CheckAppUpdateService extends BaseService<IsNeedToUpdateDto> {

    @Autowired
    AppVersionDao appVersionDao;

    @Override
    public ResponseDto<IsNeedToUpdateDto> process(JSONObject params, HttpServletRequest request, HttpServletResponse response) {
        CheckUtil.checkEmpty(params,"versionNumber");
        AppVersion appVersion = appVersionDao.seleteVersionNameByVersionName(params.getString("versionNumber"));
        ResponseDto<IsNeedToUpdateDto> responseDto= new ResponseDto<IsNeedToUpdateDto>();
        if(appVersion!=null){
            responseDto.setData(initIsNeedToUpdateDto(appVersion));
        }else {
            //版本不存在
            MessageInfoDto messageInfoDto = MessageUtil.getMessageInfoByKey("app.update.hasnotnewversion");
            throw new ExternalServiceException(messageInfoDto);
        }
        return responseDto;
    }

    /**
     * 初始化检查更新返回的数据dto
     *
     * @return
     */
    public static IsNeedToUpdateDto initIsNeedToUpdateDto(final AppVersion appVersion) {
        if (appVersion != null) {
            IsNeedToUpdateDto isNeedToUpdateDto=new IsNeedToUpdateDto();
            isNeedToUpdateDto.setVersionCode(appVersion.getVersionCode());
            isNeedToUpdateDto.setCreateDate(appVersion.getCreateDate());
            isNeedToUpdateDto.setHasNewVersion(appVersion.getHasNewVersion());
            isNeedToUpdateDto.setId(appVersion.getId());
            isNeedToUpdateDto.setMust(appVersion.getMust());
            isNeedToUpdateDto.setPlatform(appVersion.getPlatform());
            isNeedToUpdateDto.setUpdateUrl(appVersion.getUpdateUrl());
            isNeedToUpdateDto.setUpGradeInfo(appVersion.getUpGradeInfo());
            isNeedToUpdateDto.setVersionName(appVersion.getVersionName());
            return isNeedToUpdateDto;
        } else {
            throw new IllegalArgumentException("param cant empty");
        }
    }
}
