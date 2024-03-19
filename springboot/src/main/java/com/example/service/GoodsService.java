package com.example.service;

import cn.hutool.core.date.DateUtil;
import com.example.common.enums.LikesModuleEnum;
import com.example.common.enums.RoleEnum;
import com.example.entity.Account;
import com.example.entity.Blog;
import com.example.entity.Goods;
import com.example.entity.User;
import com.example.mapper.GoodsMapper;
import com.example.utils.TokenUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/*
@author TanCheng
@create 2024 -03 -19    
*/
@Service
public class GoodsService {

    @Resource
    private GoodsMapper goodsMapper;

    @Resource
    UserService userService;


    public void add(Goods goods) {
        goods.setDate(DateUtil.today());
        Account currentUser = TokenUtils.getCurrentUser();
        if (RoleEnum.USER.name().equals(currentUser.getRole())) {
            goods.setUserId(currentUser.getId());
        }

        goodsMapper.insert(goods);
    }

    public void deleteById(Integer id) {
        goodsMapper.deleteById(id);
    }

    public void deleteBatch(List<Integer> ids) {
        for (Integer id : ids) {
            goodsMapper.deleteById(id);
        }
    }

    public Goods selectById(Integer id) {
        Goods goods = goodsMapper.selectById(id);
       if(goods.getUserId()!=null){
           User user = userService.selectById(goods.getUserId());
           goods.setUser(user);
           goods.setUserName(user.getName());
       }
        return goods;
    }

    public List<Goods> selectAll(Goods goods) {
        return goodsMapper.selectAll(goods);
    }

    public PageInfo<Goods> selectPage(Goods goods, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Goods> list = goodsMapper.selectAll(goods);
        return PageInfo.of(list);
    }

    public void updateById(Goods goods) {
        goodsMapper.updateById(goods);
    }

    public void changeSell(Integer id) {
        Goods goods = selectById(id);
        boolean sell = goods.getSell()!=true;
        goods.setSell(sell);
        updateById(goods);
    }
}
