package com.clickless.workflow.service;

import com.clickless.workflow.req.ModelAddREQ;
import com.clickless.workflow.req.ModelREQ;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public interface IModelService {

    /**
     * 新增模型基本信息（创建空的模型）
     *
     * @param req
     * @return modelId
     * @throws Exception
     */
    String add(ModelAddREQ req) throws Exception;

    /**
     * 查询流程定义模型
     *
     * @param req
     * @return
     */
    Map<String, Object> getModelList(ModelREQ req);

    /**
     * 通过模型数据部署流程定义
     *
     * @param modelId
     * @return
     * @throws Exception
     */
    void deploy(String modelId) throws Exception;

    /**
     * 将模型以zip的方式导出
     *
     * @param modelId
     * @param response
     */
    void exportZip(String modelId, HttpServletResponse response);

}
