package com.clickless.workflow.controller;

import com.clickless.common.core.controller.BaseController;
import com.clickless.common.core.domain.AjaxResult;
import com.clickless.workflow.req.ModelAddREQ;
import com.clickless.workflow.req.ModelREQ;
import com.clickless.workflow.service.IModelService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.RepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@Api("流程定义模型管理")
@Slf4j
@RestController
@RequestMapping("/model")
public class ModelController extends BaseController {

    @Autowired
    IModelService modelService;

    @Autowired
    RepositoryService repositoryService;

    @ApiOperation("新增流程定义模型数据")
    @PostMapping // post 请求 /model
    public AjaxResult add(@RequestBody ModelAddREQ req) {
        try {
            return AjaxResult.success("创建成功", modelService.add(req));
        } catch (Exception e) {
            e.printStackTrace();
            log.error("创建模型失败：" + e.getMessage());
            return AjaxResult.error("创建模型失败");
        }
    }

    @ApiOperation("条件分页查询流程定义模型数据")
    @PostMapping("/list")
    public AjaxResult modelList(@RequestBody ModelREQ req) {
        try {
            return AjaxResult.success(modelService.getModelList(req));
        } catch (Exception e) {
            e.printStackTrace();
            log.error("条件分页查询流程定义模型数据:" + e.getMessage());
            return AjaxResult.error("查询列表数据失败");
        }

    }

    @ApiOperation("通过流程定义模型id部署流程定义")
    @PostMapping("/deploy/{modelId}")
    public AjaxResult deploy(@PathVariable("modelId") String modelId) {
        try {
            modelService.deploy(modelId);
            return AjaxResult.success();
        } catch (Exception e) {
            e.printStackTrace();
            log.error("部署流程定义失败：" + e.getMessage());
            return AjaxResult.error("部署流程定义失败，失败原因:" + e.getMessage());
        }
    }

    @ApiOperation("导出流程定义模型zip压缩包")
    @GetMapping("/export/zip/{modelId}")
    public void exportZip(@PathVariable("modelId") String modelId,
                          HttpServletResponse response) {
        modelService.exportZip(modelId, response);
    }

    @ApiOperation("删除流程定义模型")
    @DeleteMapping("{modelId}")
    public AjaxResult deleteModel(@PathVariable("modelId") String modelId) {
        repositoryService.deleteModel(modelId);
        return AjaxResult.success();
    }
}



