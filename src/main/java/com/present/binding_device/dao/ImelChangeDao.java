package com.present.binding_device.dao;

import com.present.binding_device.bean.ImelChange;
import org.apache.ibatis.annotations.Param;

/**
 * Created by Laiyin on 2017/9/5.
 */
public interface ImelChangeDao {

    /**
     * ����imel��ѯ���ݿ�����Ϣ
     *
     * @param imel �豸��ʶ��
     * @return �豸��ʶ���Ķ�Ӧ��Ϣ
     */
    ImelChange seleteByImel(@Param("imel")String imel);

    /**
     * ����imel��Ϣ
     * @param imel
     * @param userId
     * @param changeTime
     */
    void addImel(@Param("imel")String imel, @Param("userId")String userId, @Param("changeTime")long changeTime);

    /**
     * ����imel��Ϣ
     * @param imel
     * @param userId
     * @param changeTime
     */
    void changeImel(@Param("imel")String imel, @Param("userId")String userId, @Param("changeTime")long changeTime);

}
