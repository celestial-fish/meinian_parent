package com.lyl.job;

import com.lyl.utils.QiniuUtils;
import com.lyl.utils.RedisConstant;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.JedisPool;

import java.util.Iterator;
import java.util.Set;

public class ClearImgJob {
    @Autowired
    private JedisPool jedisPool;
    public void clearImg(){
        //Redis 的sdiff 命令，返回一个集合与其他集合之间的差异 注意集合大的，内容多的要放在前面
        Set<String>sdiff = jedisPool.getResource().sdiff(RedisConstant.SETMEAL_PIC_RESOURCES,RedisConstant.SETMEAL_PIC_DB_RESOURCES);
//        迭代集合 找到差异的图片
        Iterator<String> iterator = sdiff.iterator();
        while (iterator.hasNext()){
            String pic = iterator.next();
            System.out.println("差异图片==》" +pic );
//        找到差异图片，将其删除
            QiniuUtils.deleteFileFromQiniu(pic);
//            删除redis的图片
            jedisPool.getResource().srem(RedisConstant.SETMEAL_PIC_RESOURCES,pic);
        }

    }
}
