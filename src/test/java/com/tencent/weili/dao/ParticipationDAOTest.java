package com.tencent.weili.dao;

import com.tencent.weili.entity.Participation;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ParticipationDAOTest {


    @Autowired
    private ParticipationDAO participationDAO;

    /*
     * OK
     */
    @Test
    public void selectAllParticipationByActivityId() throws Exception {
        List<Participation> list = participationDAO.selectAllParticipationByActivityId(4);
        for (Participation participation : list) {
            System.out.println(participation);
            System.out.println(participation.getActivity());
            System.out.println(participation.getUser());
        }
    }

    /*
     * OK
     */
    @Test
    public void deleteByOpenIdAndActivityId() throws Exception {
        int result = participationDAO.deleteByOpenIdAndActivityId("003", 9);
        System.out.println(result);
    }

    /*
     * OK
     */
    @Test
    public void deleteByActivityId() throws Exception {
        int result = participationDAO.deleteByActivityId(9);
    }

    /*
     * OK
     */
    @Test
    public void insertParticipation() throws Exception {
        Participation participation = new Participation();
        participation.setUserId("008");
        participation.setActivityId(3);
        participation.setFlag(true);
        participation.setType(1);
        participation.setCreatorId("009");
        participation.setTime("3-4;4-5;5-6");
        int result = participationDAO.insertParticipation(participation);
        assertEquals(1, result);
    }

    /*
     * OK
     */
    @Test
    public void selectParticipationByOpenIdAndActivityId() throws Exception {
        Participation participation = participationDAO.selectParticipationByOpenIdAndActivityId("001", 3);
        System.out.println(participation);
    }

    /*
     * OK
     */
    @Test
    public void updateParticipationByOpenIdAndActivityId() throws Exception {
        int result = participationDAO.updateParticipationByOpenIdAndActivityId("001", 3, "1-2");
        assertEquals(1, result);
    }

}