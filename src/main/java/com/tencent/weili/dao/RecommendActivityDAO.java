package com.tencent.weili.dao;

import com.tencent.weili.entity.RecommendActivity;

public interface RecommendActivityDAO {
    /*
     * 插入主页活动
     */
    int insertRecommendActivity(RecommendActivity recommendActivity);

    /*
     * 查询主页活动
     */
    RecommendActivity selectRecommendActivityById(Integer id);
}
