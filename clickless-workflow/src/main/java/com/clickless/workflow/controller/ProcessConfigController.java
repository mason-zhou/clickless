package com.clickless.workflow.controller;

import com.clickless.common.core.domain.AjaxResult;
import com.clickless.workflow.domain.ProcessConfig;
import com.clickless.workflow.service.IProcessConfigService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api("流程配置控制层")
@RestController
@RequestMapping("/processConfig")
public class ProcessConfigController {

    @Autowired
    IProcessConfigService processConfigService;

    @ApiOperation("根据流程定义key查询流程配置")
    @GetMapping("{processKey}")
    public AjaxResult view(@PathVariable String processKey) {
        ProcessConfig processConfig = processConfigService.getByProcessKey(processKey);
        return AjaxResult.success(processConfig);
    }

    @ApiOperation("新增或更新流程配置")
    @PutMapping
    public AjaxResult saveOrUpdate(@RequestBody ProcessConfig processConfig) {
        boolean b = processConfigService.saveOrUpdate(processConfig);
        if (b) {
            return AjaxResult.success();
        } else {
            return AjaxResult.error("操作失败");
        }
    }

}
