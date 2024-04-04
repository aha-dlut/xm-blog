package com.example.controller;

import com.example.common.Result;
import com.example.entity.Share;
import com.example.service.ShareService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 文件分享前端操作接口
 **/
@RestController
@RequestMapping("/share")
@Api(tags = "网盘文件分享接口")
public class ShareController {

    @Resource
    private ShareService shareService;

    /**
     * 新增
     */
    @ApiOperation(value = "新增")
    @PostMapping("/add")
    public Result add(@RequestBody Share share) {
        shareService.add(share);
        return Result.success();
    }

    /**
     * 删除
     */
    @ApiOperation(value = "删除")
    @DeleteMapping("/delete/{id}")
    public Result deleteById(@PathVariable Integer id) {
        shareService.deleteById(id);
        return Result.success();
    }

    /**
     * 批量删除
     */
    @ApiOperation(value = "批量删除")
    @DeleteMapping("/delete/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        shareService.deleteBatch(ids);
        return Result.success();
    }

    /**
     * 修改
     */
    @ApiOperation(value = "修改")
    @PutMapping("/update")
    public Result updateById(@RequestBody Share share) {
        shareService.updateById(share);
        return Result.success();
    }

    /**
     * 根据ID查询
     */
    @ApiOperation(value = "根据id查询")
    @GetMapping("/selectById/{id}")
    public Result selectById(@PathVariable Integer id) {
        Share share = shareService.selectById(id);
        shareService.updateCount(id);  // 访问次数+1
        return Result.success(share);
    }

    /**
     * 查询所有
     */
    @ApiOperation(value = "查询所有")
    @GetMapping("/selectAll")
    public Result selectAll(Share share ) {
        List<Share> list = shareService.selectAll(share);
        return Result.success(list);
    }

    /**
     * 分页查询
     */
    @ApiOperation(value = "分页查询")
    @GetMapping("/selectPage")
    public Result selectPage(Share share,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<Share> page = shareService.selectPage(share, pageNum, pageSize);
        return Result.success(page);
    }

}