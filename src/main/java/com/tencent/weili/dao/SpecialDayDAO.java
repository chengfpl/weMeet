package com.tencent.weili.dao;

import com.tencent.weili.entity.SpecialDay;

public interface SpecialDayDAO {

    /*
     * 插入日期与对应的节日名称
     */
    int insertSpecialDay(SpecialDay specialDay);

    /*
     * 根据日期返回对应的节日名称
     */
    String selectEventByDate(String date);

}
