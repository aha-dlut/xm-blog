package com.example.controller;

import com.example.common.Result;
import com.example.entity.Blog;
import com.example.entity.Goods;
import com.example.service.GoodsService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.security.PublicKey;
import java.util.List;

/*
@author TanCheng
@create 2024 -03 -19    
*/
@RestController
@RequestMapping("/goods")
@Api(tags = "商品管理")
public class GoodsController {

    @Resource
    private GoodsService goodsService;

    @GetMapping("/")
    public Result test(){
        return Result.success();
    }

    @ApiOperation(value = "添加商品")
    @PostMapping("/add")
    public Result add(@RequestBody Goods goods){
        goodsService.add(goods);
        return Result.success();
    }

    @ApiOperation(value = "删除商品")
    @DeleteMapping("/delete/{id}")
    public Result deleteById(@PathVariable Integer id){
        goodsService.deleteById(id);
        return Result.success();
    }

    /**
     * 批量删除
     */
    @ApiOperation(value = "批量删除")
    @DeleteMapping("/delete/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        goodsService.deleteBatch(ids);
        return Result.success();
    }

    /**
     * 根据ID查询
     */
    @ApiOperation(value = "根据id查询")
    @GetMapping("/selectById/{id}")
    public Result selectById(@PathVariable Integer id) {
        Goods good = goodsService.selectById(id);
        return Result.success(good);
    }

    /**
     * 查询所有
     */
    @ApiOperation(value = "查询所有商品")
    @GetMapping("/selectAll")
    public Result selectAll(Goods goods) {
        List<Goods> list = goodsService.selectAll(goods);
        return Result.success(list);
    }

    /**
     * 分页查询
     */
    @ApiOperation(value = "分页查询")
    @GetMapping("/selectPage")
    public Result selectPage(Goods goods,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<Goods> page = goodsService.selectPage(goods, pageNum, pageSize);
        return Result.success(page);
    }

    /**
     * 修改
     */
    @ApiOperation(value = "修改商品")
    @PutMapping("/update")
    public Result updateById(@RequestBody Goods goods) {
        goodsService.updateById(goods);
        return Result.success();
    }

    @ApiOperation(value = "改变商品状态")
    @PostMapping("/changeSell/{id}")
    public Result changeSell(@PathVariable Integer id){
        goodsService.changeSell(id);
        return Result.success();
    }
}
