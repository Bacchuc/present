package com.present.binding_device.dao;

import com.present.binding_device.bean.ImelChange;
import org.apache.ibatis.annotations.Param;

/**
 * Created by Laiyin on 2017/9/5.
 */
public interface ImelChangeDao {

    /**
     * 根据imel查询数据库中信息
     *
     * @param imel 设备标识符
     * @return 设备标识符的对应信息
     */
    ImelChange seleteByImel(@Param("imel")String imel);

    /**
     * 更改imel信息
     * @param imel
     * @param userId
     * @param changeTime
     */
    void addImel(@Param("imel")String imel, @Param("userId")String userId, @Param("changeTime")long changeTime);

    /**
     * 更改imel信息
     * @param imel
     * @param userId
     * @param changeTime
     */
    void changeImel(@Param("imel")String imel, @Param("userId")String userId, @Param("changeTime")long changeTime);

}
