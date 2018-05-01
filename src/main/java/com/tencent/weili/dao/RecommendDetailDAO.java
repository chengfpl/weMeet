package com.tencent.weili.dao;

import com.tencent.weili.entity.RecommendDetail;

public interface RecommendDetailDAO {

    /*
     * 插入具体活动
     */
    int insertRecommendDetail(RecommendDetail recommendDetail);

    /*
     * 查询具体活动
     */
    RecommendDetail selectRecommendDetailById(Integer id);
}
