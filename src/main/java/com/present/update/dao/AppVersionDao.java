package com.present.update.dao;

import com.present.update.bean.AppVersion;

/**
 * Created by Laiyin on 2017/9/2.
 */
public interface AppVersionDao {

    /**
     * ����versionName��ѯ���ݿ����Ƿ���ڴ˰汾�������򷵻ذ汾��Ϣ
     * @param versionName
     * @return
     */
    AppVersion seleteVersionNameByVersionName(String versionName);

}
