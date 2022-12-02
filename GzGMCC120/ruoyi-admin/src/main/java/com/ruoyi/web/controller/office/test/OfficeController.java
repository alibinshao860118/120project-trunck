package com.ruoyi.web.controller.office.test;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.constant.UserConstants;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysDept;
import com.ruoyi.common.enums.BusinessType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author liangbinbin
 * @title: OfficeController
 * @projectName 02turnk
 * @description: TODO
 * @date 2022/12/2
 */
@RestController
@RequestMapping("/office")
public class OfficeController extends BaseController {
    /**
     * 新增部门
     */
    @PreAuthorize("@ss.hasPermi('office:add')")
    @Log(title = "管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody String dept)
    {
//        if (UserConstants.NOT_UNIQUE.equals(deptService.checkDeptNameUnique(dept)))
//        {
//            return error("新增部门'" + dept.getDeptName() + "'失败，部门名称已存在");
//        }
//        dept.setCreateBy(getUsername());
        String result="sucess";
        return success(result);
    }
}
