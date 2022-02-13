package com.clickless.workflow.controller;

import com.clickless.common.core.domain.AjaxResult;
import com.clickless.workflow.req.ProcDefiREQ;
import com.clickless.workflow.service.IProcessDefinitionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.repository.ProcessDefinition;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.zip.ZipInputStream;

@Api("流程定义管理控制器")
@Slf4j
@RestController
@RequestMapping("/process")
public class ProcessDefinitionController {

    @Autowired
    IProcessDefinitionService processDefinitionService;

    @Autowired
    RepositoryService repositoryService;

    @ApiOperation("条件分页查询相同key的最新版本的流程定义列表数据")
    @PostMapping("/list")
    public AjaxResult getProcDefiList(@RequestBody ProcDefiREQ req) {
        return AjaxResult.success(processDefinitionService.getProcDefiList(req));
    }

    @ApiOperation("更新流程状态：激活（启动）或者挂起（暂停）")
    @PutMapping("/state/{procDefiId}")
    public AjaxResult updateProcDefState(@PathVariable String procDefiId) {
        processDefinitionService.updateProcDefState(procDefiId);
        return AjaxResult.success();
    }

    @ApiOperation("删除流程定义")
    @DeleteMapping("/{deploymentId}") // delete方式请求 /process/{deploymentId}?key=xxx
    public AjaxResult deleteDeployment(@PathVariable String deploymentId,
                                       @RequestParam String key) {
        processDefinitionService.deleteDeployment(deploymentId, key);
        return AjaxResult.success();
    }

    @ApiOperation("导出流程定义文件（xml,png)")
    @GetMapping("/export/{type}/{definitionId}")
    public void exportFile(@PathVariable String type,
                           @PathVariable String definitionId,
                           HttpServletResponse response) {
        try {
            ProcessDefinition processDefinition = repositoryService.getProcessDefinition(definitionId);

            String resourceName = "文件不存在";

            if ("xml".equals(type)) {
                // 获取的是 xml 资源名
                resourceName = processDefinition.getResourceName();
            } else if ("png".equals(type)) {
                // 获取 png 图片资源名
                resourceName = processDefinition.getDiagramResourceName();
            }

            // 查询到相关的资源输入流 （deploymentId, resourceName）
            InputStream input =
                    repositoryService.getResourceAsStream(processDefinition.getDeploymentId(), resourceName);

            // 创建输出流
            response.setHeader("Content-Disposition",
                    "attachment; filename=" + URLEncoder.encode(resourceName, "UTF-8"));

            // 流的拷贝放到设置请求头下面，不然文件大于10k可能无法导出
            IOUtils.copy(input, response.getOutputStream());

            response.flushBuffer();
        } catch (Exception e) {
            e.printStackTrace();
            log.error("导出文件失败：{}", e.getMessage());
        }
    }

    @ApiOperation("上传zip、bpmn、xml后缀的文件来进行部署流程定义")
    @PostMapping("/file/deploy")
    public AjaxResult deployByFile(@RequestParam("file") MultipartFile file) {
        try {
            // 文件名+后缀名
            String filename = file.getOriginalFilename();
            // 文件后缀名
            String suffix = filename.substring(filename.lastIndexOf(".") + 1).toUpperCase();

            InputStream input = file.getInputStream();

            DeploymentBuilder deployment = repositoryService.createDeployment();
            if ("ZIP".equals(suffix)) {
                // zip
                deployment.addZipInputStream(new ZipInputStream(input));
            } else {
                // xml 或 bpmn
                deployment.addInputStream(filename, input);
            }

            // 部署名称
            deployment.name(filename.substring(0, filename.lastIndexOf(".")));

            // 开始部署
            deployment.deploy();

            return AjaxResult.success();
        } catch (IOException e) {
            e.printStackTrace();
            log.error("部署失败：" + e.getMessage());
            return AjaxResult.error("部署失败");
        }
    }
}
