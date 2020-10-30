package com.lyl.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lyl.pojo.Setmeal;
import com.lyl.constant.MessageConstant;
import com.lyl.service.SetmealService;
import com.lyl.utils.QiniuUtils;
import com.lyl.utils.RedisConstant;
import com.lyl.entity.PageResult;
import com.lyl.entity.QueryPageBean;
import com.lyl.entity.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.JedisPool;
import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/setmeal")
public class SetmealController {
    @Reference
    private SetmealService setmealService;
    @Autowired
    private JedisPool jedisPool;



    @RequestMapping("/upload")
    public Result upload(MultipartFile imgFile){
        try{
            //获取源文件名
            String originalFilename= imgFile.getOriginalFilename();
            // 找到.最后出现的位置
            int lastIndexOf = originalFilename.lastIndexOf(".");
            //获取文件后缀
            String suffix = originalFilename.substring(lastIndexOf);
            //使用UUID随机产生文件名称，防止同名文件覆盖
            String fileName = UUID.randomUUID().toString() + suffix;
            QiniuUtils.upload2Qiniu(imgFile.getBytes(),fileName);
            //图片上传成功
            Result result = new Result(true, MessageConstant.PIC_UPLOAD_SUCCESS, fileName);
            jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_RESOURCES,fileName);
            return result;

        }catch (IOException e){
            e.printStackTrace();
        }
        return new Result(false,MessageConstant.PIC_UPLOAD_FAIL);
    }

    @RequestMapping("/add")
    public Result add(Integer[]travelgroupIds,@RequestBody Setmeal setmeal){
        setmealService.add(travelgroupIds,setmeal);
        return new Result(true,MessageConstant.ADD_SETMEAL_SUCCESS);

    }

    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean){
        return setmealService.findPage(queryPageBean);
    }

}
