package com.clickless.workflow.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.clickless.common.utils.DateUtils;
import com.clickless.workflow.exception.WorkflowException;
import com.clickless.workflow.req.ModelAddREQ;
import com.clickless.workflow.req.ModelREQ;
import com.clickless.workflow.service.IModelService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.Model;
import org.activiti.engine.repository.ModelQuery;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
public class ModelService extends ActivitiService implements IModelService {

    @Override
    public String add(ModelAddREQ req) throws Exception {

        int version = 0;

        // 1. 初始空的模型
        Model model = repositoryService.newModel();
        model.setName(req.getName());
        model.setKey(req.getKey());
        model.setVersion(version);

        // 封装模型json对象
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put(ModelDataJsonConstants.MODEL_NAME, req.getName());
        objectNode.put(ModelDataJsonConstants.MODEL_REVISION, version);
        objectNode.put(ModelDataJsonConstants.MODEL_DESCRIPTION, req.getDescription());
        model.setMetaInfo(objectNode.toString());
        // 保存初始化的模型基本信息数据
        repositoryService.saveModel(model);

        // 封装模型对象基础数据json串
        // {"id":"canvas","resourceId":"canvas","stencilset":{"namespace":"http://b3mn.org/stencilset/bpmn2.0#"},"properties":{"process_id":"未定义"}}
        ObjectNode editorNode = objectMapper.createObjectNode();
        ObjectNode stencilSetNode = objectMapper.createObjectNode();
        stencilSetNode.put("namespace", "http://b3mn.org/stencilset/bpmn2.0#");
        editorNode.replace("stencilset", stencilSetNode);
        // 标识key
        ObjectNode propertiesNode = objectMapper.createObjectNode();
        propertiesNode.put("process_id", req.getKey());
        editorNode.replace("properties", propertiesNode);

        repositoryService.addModelEditorSource(model.getId(), editorNode.toString().getBytes("utf-8"));

        return model.getId();
    }

    @Override
    public Map<String, Object> getModelList(ModelREQ req) {
        ModelQuery query = repositoryService.createModelQuery();
        if (StringUtils.isNotEmpty(req.getName())) {
            query.modelNameLike("%" + req.getName() + "%");
        }
        if (StringUtils.isNotEmpty(req.getKey())) {
            query.modelKey(req.getKey());
        }
        //创建时间降序排列
        query.orderByCreateTime().desc();

        // 分页查询
        List<Model> modelList = query.listPage(req.getFirstResult(), req.getSize());

        // 总记录数
        long total = query.count();

        List<Map<String, Object>> records = new ArrayList<>();
        for (Model model : modelList) {
            Map<String, Object> data = new HashMap<>();
            data.put("id", model.getId());
            data.put("name", model.getName());
            data.put("key", model.getKey());
            data.put("version", model.getVersion());
            String desc = JSONObject.parseObject(model.getMetaInfo())
                    .getString(ModelDataJsonConstants.MODEL_DESCRIPTION);
            data.put("description", desc);
            data.put("createDate", DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD_HH_MM_SS, model.getCreateTime()));
            data.put("updateDate", DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD_HH_MM_SS, model.getLastUpdateTime()));

            records.add(data);
        }

        Map<String, Object> result = new HashMap<>();
        result.put("records", records);
        result.put("total", total);
        return result;
    }

    @Override
    public void deploy(String modelId) throws Exception {
        // 1. 查询流程定义模型json字节码
        byte[] jsonBytes = repositoryService.getModelEditorSource(modelId);
        if (jsonBytes == null) {
            throw new WorkflowException("模型数据为空，请先设计流程定义模型，再进行部署");
        }
        // 将json字节码转为 xml 字节码，因为bpmn2.0规范中关于流程模型的描述是xml格式的，而activiti遵守了这个规范
        byte[] xmlBytes = bpmnJsonXmlBytes(jsonBytes);
        if (xmlBytes == null) {
            throw new WorkflowException("数据模型不符合要求，请至少设计一条主线流程");
        }
        // 2. 查询流程定义模型的图片
        byte[] pngBytes = repositoryService.getModelEditorSourceExtra(modelId);

        // 查询模型的基本信息
        Model model = repositoryService.getModel(modelId);

        // xml资源的名称 ，对应act_ge_bytearray表中的name_字段
        String processName = model.getName() + ".bpmn20.xml";
        // 图片资源名称，对应act_ge_bytearray表中的name_字段
        String pngName = model.getName() + "." + model.getKey() + ".png";

        // 3. 调用部署相关的api方法进行部署流程定义
        Deployment deployment = repositoryService.createDeployment()
                .name(model.getName()) // 部署名称
                .addString(processName, new String(xmlBytes, "UTF-8")) // bpmn20.xml资源
                .addBytes(pngName, pngBytes) // png资源
                .deploy();

        // 更新 部署id 到流程定义模型数据表中
        model.setDeploymentId(deployment.getId());
        repositoryService.saveModel(model);

    }


    private byte[] bpmnJsonXmlBytes(byte[] jsonBytes) throws IOException {
        if (jsonBytes == null) {
            return null;
        }

        // 1. json字节码转成 BpmnModel 对象
        JsonNode jsonNode = objectMapper.readTree(jsonBytes);
        BpmnModel bpmnModel = new BpmnJsonConverter().convertToBpmnModel(jsonNode);

        if (bpmnModel.getProcesses().size() == 0) {
            return null;
        }
        // 2. BpmnModel 对象转为xml字节码
        byte[] xmlBytes = new BpmnXMLConverter().convertToXML(bpmnModel);
        return xmlBytes;
    }


    @Override
    public void exportZip(String modelId, HttpServletResponse response) {
        ZipOutputStream zipos = null;
        try {
            // 实例化zip输出流
            zipos = new ZipOutputStream(response.getOutputStream());

            // 压缩包文件名
            String zipName = "模型不存在";

            // 1. 查询模型基本信息
            Model model = repositoryService.getModel(modelId);
            if (model != null) {
                // 2. 查询流程定义模型的json字节码
                byte[] bpmnJsonBytes = repositoryService.getModelEditorSource(modelId);
                // 2.1 将json字节码转换为xml字节码
                byte[] xmlBytes = bpmnJsonXmlBytes(bpmnJsonBytes);
                if (xmlBytes == null) {
                    zipName = "模型数据为空-请先设计流程定义模型，再导出";
                } else {
                    // 压缩包文件名
                    zipName = model.getName() + "." + model.getKey() + ".zip";

                    // 将xml添加到压缩包中(指定xml文件名：请假流程.bpmn20.xml ）
                    zipos.putNextEntry(new ZipEntry(model.getName() + ".bpmn20.xml"));
                    zipos.write(xmlBytes);

                    // 3. 查询流程定义模型的图片字节码
                    byte[] pngBytes = repositoryService.getModelEditorSourceExtra(modelId);
                    if (pngBytes != null) {
                        // 图片文件名（请假流程.leaveProcess.png)
                        zipos.putNextEntry(new ZipEntry(model.getName() + "." + model.getKey() + ".png"));
                        zipos.write(pngBytes);
                    }

                }
            }

            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition",
                    "attachment; filename=" + URLEncoder.encode(zipName, "UTF-8") + ".zip");
            // 刷出响应流
            response.flushBuffer();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (zipos != null) {
                try {
                    zipos.closeEntry();
                    zipos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
