package com.example.controller;

import com.example.common.Result;
import com.example.entity.Collect;
import com.example.service.CollectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/collect")
@Api(value = "收藏接口")
public class CollectController {

    @Resource
    CollectService collectService;

    @ApiOperation(value = "收藏和取消收藏")
    // 收藏和取消
    @PostMapping("/set")
    public Result set(@RequestBody Collect collect) {
        collectService.set(collect);
        return Result.success();
    }

}
