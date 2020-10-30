package com.lyl.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.lyl.dao.MemberDao;
import com.lyl.pojo.Member;
import com.lyl.service.MemberService;
import com.lyl.utils.DateUtils;
import com.lyl.utils.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service(interfaceClass = MemberService.class)
@Transactional
public class MemberServiceImpl implements MemberService {
    @Autowired
    private MemberDao memberDao;

    @Override
    public Member findByTelephone(String telephone) {
        return  memberDao.findByTelephone(telephone);
    }

    @Override
    public void add(Member member) {
        if (member.getPassword()!=null){
//  第一次注册只有手机号 和日期 密码为null 不会进来
            member.setPassword(MD5Utils.md5(member.getPassword()));
        }
        memberDao.add(member);
    }

    @Override
    public List<Integer> findMemberCountByMonth(List<String> list) {
       List<Integer>memeberCounts = new ArrayList<>();

        for (String months : list) {
//            获取一个月的最后一天的日期
            String regTime  = DateUtils.getLastDayOfMonth(months);
//            根据日期查询会员表十二个月的数据，然后存入集合中
            Integer memeberCount  = memberDao.findMemberCountBeforeDate(regTime);
            memeberCounts.add(memeberCount);
        }

        return memeberCounts;
    }
}
