package com.ming.seckill.dao;

import com.ming.seckill.model.Seckill;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

@Mapper
public interface SeckillDao {
    /**
     *
     * @param offset
     * @param limit
     * @return
     */
    List<Seckill> selectAll(@Param("offset") int offset,
                            @Param("limit") int limit);

    /**
     *
     * @param seckillId
     * @return
     */
    Seckill selectById(@Param("seckillId") long seckillId);

    /**
     * 减库存
     * @param seckillId
     * @param killTime
     * @return
     */
    int reduceNumber(@Param("seckillId") long seckillId,
                     @Param("killTime") Date killTime);
}
