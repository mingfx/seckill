package com.ming.seckill.service;

import com.ming.seckill.dao.SeckillDao;
import com.ming.seckill.model.Seckill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class SeckillDAOService {
    @Autowired
    SeckillDao seckillDao;

    public List<Seckill> getSeckills(int offset,int limit){
        return seckillDao.selectAll(offset,limit);
    }

    public Seckill selectSeckillById(long seckillId){
        return seckillDao.selectById(seckillId);
    }

    public boolean reduceNumber(long seckillId, Date killTime){
        return seckillDao.reduceNumber(seckillId,killTime) > 0;
    }
}
