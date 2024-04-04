package com.example.controller;

import com.example.common.Result;
import com.example.entity.Trash;
import com.example.service.TrashService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 垃圾箱前端操作接口
 **/
@RestController
@RequestMapping("/trash")
@Api(tags = "回收站接口")
public class TrashController {

    @Resource
    private TrashService trashService;

    /**
     * 新增
     */
    @ApiOperation(value = "新增")
    @PostMapping("/add")
    public Result add(@RequestBody Trash trash) {
        trashService.add(trash);
        return Result.success();
    }

    /**
     * 删除
     */
    @ApiOperation(value = "删除")
    @DeleteMapping("/delete/{id}")
    public Result deleteById(@PathVariable Integer id) {
        trashService.deleteById(id);
        return Result.success();
    }

    /**
     * 批量删除
     */
    @ApiOperation(value = "批量删除")
    @DeleteMapping("/delete/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        trashService.deleteBatch(ids);
        return Result.success();
    }

    /**
     * 修改
     */
    @ApiOperation(value = "修改")
    @PutMapping("/update")
    public Result updateById(@RequestBody Trash trash) {
        trashService.updateById(trash);
        return Result.success();
    }

    /**
     * 根据ID查询
     */
    @ApiOperation(value = "根据id查询")
    @GetMapping("/selectById/{id}")
    public Result selectById(@PathVariable Integer id) {
        Trash trash = trashService.selectById(id);
        return Result.success(trash);
    }

    /**
     * 查询所有
     */
    @ApiOperation(value = "查询所有")
    @GetMapping("/selectAll")
    public Result selectAll(Trash trash ) {
        List<Trash> list = trashService.selectAll(trash);
        return Result.success(list);
    }

    /**
     * 分页查询
     */
    @ApiOperation(value = "分页查询")
    @GetMapping("/selectPage")
    public Result selectPage(Trash trash,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<Trash> page = trashService.selectPage(trash, pageNum, pageSize);
        return Result.success(page);
    }

}