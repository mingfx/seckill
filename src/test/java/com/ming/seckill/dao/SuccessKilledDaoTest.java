package com.ming.seckill.dao;

import com.ming.seckill.SeckillApplication;
import com.ming.seckill.model.SuccessKilled;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;
@RunWith(SpringJUnit4ClassRunner.class)
//要加入spring上下文的环境
@SpringBootTest(classes = SeckillApplication.class)
public class SuccessKilledDaoTest {
    @Autowired
    SuccessKilledDao successKilledDao;

    @Test
    public void insertSuccessKilled() {
        System.out.println(successKilledDao.insertSuccessKilled(1004, 18211110001L));
    }

    @Test
    public void queryByIdWithSeckill() {
        SuccessKilled successKilled = successKilledDao.queryByIdWithSeckill(1003,18211110000L);
        //System.out.println(successKilled);
    }
}