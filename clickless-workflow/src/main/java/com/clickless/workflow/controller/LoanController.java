package com.clickless.workflow.controller;

import com.clickless.common.core.controller.BaseController;
import com.clickless.common.core.domain.AjaxResult;
import com.clickless.common.core.page.TableDataInfo;
import com.clickless.workflow.domain.Loan;
import com.clickless.workflow.exception.WorkflowException;
import com.clickless.workflow.req.LoanREQ;
import com.clickless.workflow.service.ILoanService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api("借款申请控制层")
@Slf4j
@RestController
@RequestMapping("/loan")
public class LoanController extends BaseController {

    @Autowired
    ILoanService loanService;

    @ApiOperation("新增借款申请")
    @PostMapping
    public AjaxResult add(@RequestBody Loan loan) {
        loanService.add(loan);
        return AjaxResult.success("新增成功");
    }

    @ApiOperation("条件分页查询借款申请列表数据")
    @GetMapping("/list")
    public TableDataInfo listPage(LoanREQ req) {
        startPage();
        List<Loan> list = loanService.list(req);
        return getDataTable(list);
    }

    @ApiOperation("查询借款详情信息")
    @GetMapping("/{id}")
    public AjaxResult view(@PathVariable String id) {
        Loan loan = loanService.getById(id);
        return AjaxResult.success(loan);
    }

    @ApiOperation("更新借款详情信息")
    @PutMapping
    public AjaxResult view(@RequestBody Loan loan) {
        try {
            loanService.update(loan);
            return AjaxResult.success();
        } catch (WorkflowException e) {
            e.printStackTrace();
            return AjaxResult.error("更新失败", e.getMessage());
        }
    }
}
