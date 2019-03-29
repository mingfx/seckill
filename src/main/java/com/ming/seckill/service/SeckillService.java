package com.ming.seckill.service;

import com.ming.seckill.dto.Exposer;
import com.ming.seckill.dto.SeckillExecution;
import com.ming.seckill.exception.RepeatKillException;
import com.ming.seckill.exception.SeckillCloseException;
import com.ming.seckill.exception.SeckillException;
import com.ming.seckill.model.Seckill;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 业务接口:站在使用者角度设计接口
 * 三个方面：方法定义粒度，参数，返回类型（return类型、异常）
 */
@Service
public interface SeckillService {
    /**
     * 查询所有秒杀商品
     * @return
     */
    List<Seckill> getSeckillList();

    /**
     * 查询单个秒杀商品
     * @param seckillId
     * @return
     */
    Seckill getById(long seckillId);

    /**
     * 秒杀开启时，输出秒杀接口地址，否则输出系统时间和秒杀时间
     * @param seckillId
     */
    Exposer exportSeckillUrl(long seckillId);

    /**
     * 执行秒杀操作
     * @param seckillId
     * @param userPhone
     * @param md5
     */
    SeckillExecution executeSeckill(long seckillId, long userPhone, String md5)
    throws SeckillException, RepeatKillException, SeckillCloseException;
}
