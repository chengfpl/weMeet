package com.tencent.weili.dao;

import com.tencent.weili.entity.Participation;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ParticipationDAO {

    /*
     * 根据活动id去user_activity表查询所有参与情况
     */

    List<Participation> selectAllParticipationByActivityId(int activityId);

    /*
     * 创建者删除某个活动
     */
    int deleteByCreatorId(String creatorId);

    /*
     * 插入活动
     */
    int insertParticipation(Participation participation);

    /*
     * 根据参与人openId与活动id查询某个活动参与
     */
    Participation selectParticipationByOpenIdAndActivityId(@Param("userId") String userId,
                                                           @Param("activityId") Integer activityId);

    /*
     * 用户更新所选时间
     */
    int updateParticipationByOpenIdAndActivityId(@Param("userId") String userId,
                                                 @Param("activityId") Integer activityId,
                                                 @Param("time") String time);

}
