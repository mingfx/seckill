package com.ming.seckill.service.impl;

import com.ming.seckill.dao.SeckillDao;
import com.ming.seckill.dao.SuccessKilledDao;
import com.ming.seckill.dto.Exposer;
import com.ming.seckill.dto.SeckillExecution;
import com.ming.seckill.enums.SeckillStatEnum;
import com.ming.seckill.exception.RepeatKillException;
import com.ming.seckill.exception.SeckillCloseException;
import com.ming.seckill.exception.SeckillException;
import com.ming.seckill.model.Seckill;
import com.ming.seckill.model.SuccessKilled;
import com.ming.seckill.service.SeckillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.List;

@Service
public class SeckillServiceImpl implements SeckillService {
    private static final Logger logger = LoggerFactory.getLogger(SeckillServiceImpl.class);

    @Autowired
    SeckillDao seckillDao;

    @Autowired
    SuccessKilledDao successKilledDao;

    //salt,可以随机生成保存在数据库，每个不一样
    private final String salt = "jiagniaga";

    @Override
    public List<Seckill> getSeckillList() {
        return seckillDao.selectAll(0,10);
    }

    @Override
    public Seckill getById(long seckillId) {
        return seckillDao.selectById(seckillId);
    }

    @Override
    public Exposer exportSeckillUrl(long seckillId) {
        Seckill seckill = seckillDao.selectById(seckillId);
        if (seckill==null){
            return new Exposer(false,seckillId);
        }
        Date startTime = seckill.getStartTime();
        Date endTime = seckill.getEndTime();
        Date nowTime = new Date();
        if (nowTime.getTime()<startTime.getTime()
                || nowTime.getTime()>endTime.getTime()){
            return new Exposer(false,seckillId,nowTime.getTime(),
                    startTime.getTime(),endTime.getTime());
        }
        //不可逆
        String md5 = getMD5(seckillId);
        return new Exposer(true,md5,seckillId);
    }

    private String getMD5(long seckillId){
        String base = seckillId + "/" + salt;
        String md5 = DigestUtils.md5DigestAsHex(base.getBytes());
        return md5;
    }

    @Override
    @Transactional
    /**
     * 使用注解控制事务方法
     * 保证事务方法的执行时间尽可能短，方法内尽量只有数据库事务流程，不要穿插其他的网络操作，其他操作可以写到上层方法
     * 不是所有的方法都需要事务，比如只读，或只有一条修改操作
     */
    public SeckillExecution executeSeckill(long seckillId, long userPhone, String md5)
            throws SeckillException, RepeatKillException, SeckillCloseException {
        //?
        if (md5 == null || !md5.equals(getMD5(seckillId))){
            throw new SeckillException("seckill data rewrite");
        }
        try {
            //执行秒杀逻辑：减库存+记录购买行为
            int updateCount = seckillDao.reduceNumber(seckillId,new Date());
            if (updateCount <= 0){
                //没有更新到记录，减库存失败
                throw new SeckillCloseException("seckill is closed");
            }else {
                //减库存成功，记录购买行为
                int insertCount = successKilledDao.insertSuccessKilled(seckillId,userPhone);
                if (insertCount <= 0) {
                    //重复秒杀
                    throw new RepeatKillException("repeat kill");
                }else {
                    SuccessKilled successKilled = successKilledDao.queryByIdWithSeckill(seckillId,userPhone);
                    return new SeckillExecution(seckillId, SeckillStatEnum.SUCCESS,successKilled);
                }
            }
        } catch (SeckillCloseException e1) {
            throw e1;
        } catch (RepeatKillException e2) {
            throw e2;
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            //所有编译期异常转化为运行期异常
            throw new SeckillException("seckill inner error:"+e.getMessage());
        }
        //return null;
    }
}
