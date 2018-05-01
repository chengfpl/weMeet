package com.tencent.weili.dao;

import com.tencent.weili.entity.SpecialDay;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpecialDayDAOTest {

    @Autowired
    private SpecialDayDAO specialDayDAO;

    /*
     * OK
     */
    @Test
    public void insertSpecialDay() throws Exception {
        SpecialDay specialDay = new SpecialDay();
        specialDay.setDate("2015-02-01");
        specialDay.setEvent("hehe day!!!");
        int result = specialDayDAO.insertSpecialDay(specialDay);
        assertEquals(1, result);
    }

    /*
     * OK
     */
    @Test
    public void selectSpecialDayByDate() throws Exception {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String event = specialDayDAO.selectEventByDate(simpleDateFormat.format(new Date()));
        System.out.println(event);
    }

}