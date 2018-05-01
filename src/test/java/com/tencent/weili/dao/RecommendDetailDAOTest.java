package com.tencent.weili.dao;

import com.tencent.weili.entity.RecommendDetail;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RecommendDetailDAOTest {

    @Autowired
    private RecommendDetailDAO recommendDetailDAO;

    @Test
    public void insertRecommendDetail() throws Exception {
        RecommendDetail recommendDetail = new RecommendDetail();
        recommendDetail.setKind(1);
        recommendDetail.setDetail("hello");
        recommendDetail.setLocation("中关村");
        recommendDetail.setNumber("2-4");
        recommendDetail.setTitle("h");
        int result = recommendDetailDAO.insertRecommendDetail(recommendDetail);
        assertEquals(1, result);
    }

    @Test
    public void selectRecommendDetailById() throws Exception {
        RecommendDetail recommendDetail = recommendDetailDAO.selectRecommendDetailById(1);
        System.out.println(recommendDetail);
    }

}