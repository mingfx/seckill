package com.ming.seckill.service;

import com.ming.seckill.SeckillApplication;
import com.ming.seckill.model.Seckill;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


import java.util.Date;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
//要加入spring上下文的环境
@SpringBootTest(classes = SeckillApplication.class)
public class SeckillDAOServiceTest {
    @Autowired
    SeckillDAOService seckillDAOService;

    @Test
    public void getSeckills() {
        List<Seckill> seckills = seckillDAOService.getSeckills(0,10);
        for (Seckill seckill :
                seckills) {
            System.out.println(seckill);
        }
    }

    @Test
    public void selectSeckillById() {
        System.out.println(seckillDAOService.selectSeckillById(1001));
    }

    @Test
    public void reduceNumber(){
        Seckill seckill = seckillDAOService.selectSeckillById(1004);
        System.out.println(seckill.getName()+",库存:"+seckill.getNumber());
        seckillDAOService.reduceNumber(1004,new Date());
        System.out.println(seckillDAOService.selectSeckillById(1004).getName()+
                ",库存:"+ seckillDAOService.selectSeckillById(1004).getNumber());
    }
}