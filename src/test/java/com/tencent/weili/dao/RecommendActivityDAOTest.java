package com.tencent.weili.dao;

import com.tencent.weili.entity.RecommendActivity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RecommendActivityDAOTest {

    @Autowired
    private RecommendActivityDAO recommendActivityDAO;

    @Test
    public void insertRecommendActivity() throws Exception {
        RecommendActivity recommendActivity = new RecommendActivity();
        recommendActivity.setKind(2);
        recommendActivity.setText("wangqiu");
        recommendActivity.setDetailText("fengpei_wangqiu");
        int result = recommendActivityDAO.insertRecommendActivity(recommendActivity);
        assertEquals(1, result);
    }

    @Test
    public void selectRecommendActivityById() throws Exception {
        RecommendActivity recommendActivity = recommendActivityDAO.selectRecommendActivityById(2);
        System.out.println(recommendActivity);
    }

}