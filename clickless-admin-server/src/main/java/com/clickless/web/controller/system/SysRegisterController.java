package com.clickless.web.controller.system;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.clickless.common.core.controller.BaseController;
import com.clickless.common.core.domain.AjaxResult;
import com.clickless.common.core.domain.model.RegisterBody;
import com.clickless.common.utils.StringUtils;
import com.clickless.framework.web.service.SysRegisterService;
import com.clickless.system.config.service.SysConfigService;

/**
 * 注册验证
 * 
 * @author clickless
 */
@RestController
public class SysRegisterController extends BaseController
{
    @Autowired
    private SysRegisterService registerService;

    @Autowired
    private SysConfigService configService;

    @PostMapping("/register")
    public AjaxResult register(@RequestBody RegisterBody user)
    {
        if (!("true".equals(configService.selectConfigByKey("sys.account.registerUser"))))
        {
            return error("当前系统没有开启注册功能！");
        }
        String msg = registerService.register(user);
        return StringUtils.isEmpty(msg) ? success() : error(msg);
    }
}
