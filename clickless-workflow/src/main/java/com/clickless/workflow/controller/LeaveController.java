package com.clickless.workflow.controller;

import com.clickless.common.core.controller.BaseController;
import com.clickless.common.core.domain.AjaxResult;
import com.clickless.common.core.page.TableDataInfo;
import com.clickless.workflow.domain.Leave;
import com.clickless.workflow.exception.WorkflowException;
import com.clickless.workflow.req.LeaveREQ;
import com.clickless.workflow.service.ILeaveService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api("请假申请控制层")
@Slf4j
@RestController
@RequestMapping("/leave")
public class LeaveController extends BaseController {

    @Autowired
    ILeaveService leaveService;

    @ApiOperation("新增请假申请")
    @PostMapping
    public AjaxResult add(@RequestBody Leave leave) {
        leaveService.add(leave);
        return AjaxResult.success();
    }

    @ApiOperation("条件分页查询请假申请列表数据")
    @GetMapping("/list")
    public TableDataInfo listPage(LeaveREQ req) {
        startPage();
        List<Leave> list = leaveService.list(req);
        return getDataTable(list);
    }

    @ApiOperation("查询请假详情信息")
    @GetMapping("/{id}")
    public AjaxResult view(@PathVariable String id) {
        Leave leave = leaveService.getById(id);
        return AjaxResult.success(leave);
    }


    @ApiOperation("更新请假详情信息")
    @PutMapping
    public AjaxResult view(@RequestBody Leave leave) {
        try {
            leaveService.update(leave);
            return AjaxResult.success();
        } catch (WorkflowException e) {
            e.printStackTrace();
            return AjaxResult.error("更新失败", e.getMessage());
        }
    }
}