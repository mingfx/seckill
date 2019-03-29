package com.ming.seckill.service;

import com.ming.seckill.SeckillApplication;
import com.ming.seckill.dto.Exposer;
import com.ming.seckill.dto.SeckillExecution;
import com.ming.seckill.exception.SeckillException;
import com.ming.seckill.model.Seckill;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
//要加入spring上下文的环境
@SpringBootTest(classes = SeckillApplication.class)
public class SeckillServiceTest {
    @Autowired
    SeckillService seckillService;

    @Test
    public void getSeckillList() {
        List<Seckill> seckills = seckillService.getSeckillList();
        for (Seckill seckill :
                seckills) {
            System.out.println(seckill);
        }
    }

    @Test
    public void getById() {
        Seckill seckill = seckillService.getById(1003);
        System.out.println(seckill);
    }

    @Test
    public void exportSeckillUrl() {
        Exposer exposer = seckillService.exportSeckillUrl(1004);
        System.out.println(exposer);
    }

    @Test
    public void executeSeckill() {
        String md5 = "92094bbce5488a4af41b81c917b72972";
        try {
            SeckillExecution execution = seckillService.executeSeckill(1004,13515518474L,md5);
        } catch (SeckillException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void seckillLogic() {
        long seckillId = 1002;
        Exposer exposer = seckillService.exportSeckillUrl(seckillId);
        if (exposer.isExposed()){
            System.out.println("秒杀开始"+exposer);
            try {
                SeckillExecution execution = seckillService.executeSeckill(seckillId,13515518474L,exposer.getMd5());
            } catch (SeckillException e) {
                System.out.println(e.getMessage());
            }
        }else {
            System.out.println("秒杀未开启"+exposer);
        }
    }
}