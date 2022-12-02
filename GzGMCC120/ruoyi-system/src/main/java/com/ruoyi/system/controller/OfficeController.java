package com.ruoyi.system.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.system.domain.Office;
import com.ruoyi.system.service.IOfficeService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 合同文件Controller
 * 
 * @author ruoyi
 * @date 2022-12-02
 */
@RestController
@RequestMapping("/system/office")
public class OfficeController extends BaseController
{
    @Autowired
    private IOfficeService officeService;

    /**
     * 查询合同文件列表
     */
    @PreAuthorize("@ss.hasPermi('system:office:list')")
    @GetMapping("/list")
    public TableDataInfo list(Office office)
    {
        startPage();
        List<Office> list = officeService.selectOfficeList(office);
        return getDataTable(list);
    }

    /**
     * 导出合同文件列表
     */
    @PreAuthorize("@ss.hasPermi('system:office:export')")
    @Log(title = "合同文件", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Office office)
    {
        List<Office> list = officeService.selectOfficeList(office);
        ExcelUtil<Office> util = new ExcelUtil<Office>(Office.class);
        util.exportExcel(response, list, "合同文件数据");
    }

    /**
     * 获取合同文件详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:office:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(officeService.selectOfficeById(id));
    }

    /**
     * 新增合同文件
     */
    @PreAuthorize("@ss.hasPermi('system:office:add')")
    @Log(title = "合同文件", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Office office)
    {
        return toAjax(officeService.insertOffice(office));
    }

    /**
     * 修改合同文件
     */
    @PreAuthorize("@ss.hasPermi('system:office:edit')")
    @Log(title = "合同文件", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Office office)
    {
        return toAjax(officeService.updateOffice(office));
    }

    /**
     * 删除合同文件
     */
    @PreAuthorize("@ss.hasPermi('system:office:remove')")
    @Log(title = "合同文件", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(officeService.deleteOfficeByIds(ids));
    }
}
