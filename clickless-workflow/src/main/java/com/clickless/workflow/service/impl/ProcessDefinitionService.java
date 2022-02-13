package com.clickless.workflow.service.impl;

import com.clickless.common.utils.DateUtils;
import com.clickless.workflow.domain.ProcessConfig;
import com.clickless.workflow.req.ProcDefiREQ;
import com.clickless.workflow.service.IProcessDefinitionService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProcessDefinitionService extends ActivitiService implements IProcessDefinitionService {

    @Autowired
    ProcessConfigService processConfigService;

    @Override
    public Map<String, Object> getProcDefiList(ProcDefiREQ req) {
        ProcessDefinitionQuery query = repositoryService.createProcessDefinitionQuery();

        // 条件查询
        if (StringUtils.isNotEmpty(req.getName())) {
            query.processDefinitionNameLike("%" + req.getName() + "%");
        }

        if (StringUtils.isNotEmpty(req.getKey())) {
            query.processDefinitionKeyLike("%" + req.getKey() + "%");
        }

        // 查询相同key的最新版本
        List<ProcessDefinition> definitionList =
                query.latestVersion().listPage(req.getFirstResult(), req.getSize());

        // 总记录数
        long total = query.count();

        List<Map<String, Object>> records = new ArrayList<>();
        for (ProcessDefinition pd : definitionList) {
            Map<String, Object> data = new HashMap<>();
            data.put("id", pd.getId());
            data.put("name", pd.getName());
            data.put("key", pd.getKey());
            data.put("version", pd.getVersion());
            data.put("state", pd.isSuspended() ? "已暂停" : "已启动");
            data.put("xmlName", pd.getResourceName());
            data.put("pngName", pd.getDiagramResourceName());


            String deploymentId = pd.getDeploymentId();
            data.put("deploymentId", deploymentId);
            // 部署时间
            Deployment deployment = repositoryService.createDeploymentQuery()
                    .deploymentId(deploymentId).singleResult();
            data.put("deploymentTime", DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD_HH_MM_SS, deployment.getDeploymentTime()));

            // 查询流程定义和业务相关的配置信息
            ProcessConfig processConfig = processConfigService.getByProcessKey(pd.getKey());
            if (processConfig != null) {
                data.put("businessRoute", processConfig.getBusinessRoute());
                data.put("formName", processConfig.getFormName());
            }
            records.add(data);
        }

        Map<String, Object> result = new HashMap<>();
        result.put("total", total);
        result.put("records", records);
        return result;
    }

    @Override
    public void updateProcDefState(String ProcDefiId) {
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                .processDefinitionId(ProcDefiId)
                .singleResult();
        // 判断是否挂起，true则挂起，false则激活
        if (processDefinition.isSuspended()) {
            // 将当前为挂起状态更新为激活状态
            // 参数说明：参数1：流程定义id,参数2：是否激活（true是否级联对应流程实例，激活了则对应流程实例都可以审批），参数3：什么时候激活，如果为null则立即激活，如果为具体时间则到达此时间后激活
            repositoryService.activateProcessDefinitionById(ProcDefiId, true, null);
        } else {
            // 将当前为激活状态更新为挂起状态
            // 参数说明：参数1：流程定义id,参数2：是否挂起（true是否级联对应流程实例，挂起了则对应流程实例都不可以审批），参数3：什么时候挂起，如果为null则立即挂起，如果为具体时间则到达此时间后挂起
            repositoryService.suspendProcessDefinitionById(ProcDefiId, true, null);
        }
    }

    @Override
    public void deleteDeployment(String deploymentId, String key) {
        //1.删除部署的流程定义
        repositoryService.deleteDeployment(deploymentId);

        //2.查询当前key对应的流程定义数据是否还有，
        List<ProcessDefinition> list = repositoryService.createProcessDefinitionQuery()
                .processDefinitionKey(key).list();
        // 3. 没有此key的流程定义，则将流程配置数据删除
        if (CollectionUtils.isEmpty(list)) {
            processConfigService.deleteByProcessKey(key);
        }
    }

}
