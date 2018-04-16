package com.present.update.bean;

import java.util.Date;

/**
 *
 * @ClassName: AppVersion
 * @Description: ���ݿ��app_version��Ӧ��entity
 */
public class AppVersion {

    /**
     * �汾id
     */
    private int id;

    /**
     * �汾ƽ̨��android
     */
    private String platform;

    /**
     * �汾����
     */
    private String versionName;

    /**
     * �汾��
     */
    private int versionCode;

    /**
     * �Ƿ����°汾 ��Ϊ1 û��Ϊ0
     */
    private int hasNewVersion;

    /**
     * �Ƿ���Ҫǿ������ ��ҪΪ1 ����ҪΪ0
     * ���ͻ��˵İ汾�޷��ڷ���������ļ����汾�в���ʱ����Ҫǿ�Ƹ���
     */
    private int must;

    /**
     * ������Ϣ
     */
    private String upGradeInfo;

    /**
     * app���°汾��ַ
     */
    private String updateUrl;

    /**
     * �汾��������
     */
    private String createDate;

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public String getUpGradeInfo() {
        return upGradeInfo;
    }

    public void setUpGradeInfo(String upGradeInfo) {
        this.upGradeInfo = upGradeInfo;
    }

    public String getUpdateUrl() {
        return updateUrl;
    }

    public void setUpdateUrl(String updateUrl) {
        this.updateUrl = updateUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public int getHasNewVersion() {
        return hasNewVersion;
    }

    public void setHasNewVersion(int hasNewVersion) {
        this.hasNewVersion = hasNewVersion;
    }

    public int getMust() {
        return must;
    }

    public void setMust(int must) {
        this.must = must;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }


}
