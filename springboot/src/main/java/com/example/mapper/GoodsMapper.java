package com.example.mapper;

import com.example.entity.Goods;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/*
@author TanCheng
@create 2024 -03 -19    
*/

public interface GoodsMapper {
    void insert(Goods goods);

    void deleteById(Integer id);

    Goods selectById(Integer id);

    List<Goods> selectAll(Goods goods);

    int updateById(Goods goods);
}
