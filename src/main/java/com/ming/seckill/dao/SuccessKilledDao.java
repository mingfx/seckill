package com.ming.seckill.dao;

import com.ming.seckill.model.SuccessKilled;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SuccessKilledDao {
    /**
     * 插入购买明细，联合主键可过滤重复
     * @param seckillId
     * @param userPhone
     * @return 插入的行数，返回0表示插入失败
     */
    int insertSuccessKilled(@Param("seckillId") long seckillId,
                            @Param("userPhone") long userPhone);

    /**
     * 根据id查询SuccessKilled并携带秒杀产品对象实体
     * @param seckillId
     * @return
     */
    SuccessKilled queryByIdWithSeckill(@Param("seckillId") long seckillId,
                                       @Param("userPhone") long userPhone);
}
