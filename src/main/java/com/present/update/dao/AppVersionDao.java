package com.present.update.dao;

import com.present.update.bean.AppVersion;

/**
 * Created by Laiyin on 2017/9/2.
 */
public interface AppVersionDao {

    /**
     * 根据versionName查询数据库中是否存在此版本，存在则返回版本信息
     * @param versionName
     * @return
     */
    AppVersion seleteVersionNameByVersionName(String versionName);

}
