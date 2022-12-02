package com.ruoyi.web.controller.postran;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.postran.domain.PostranMonitor;
import com.ruoyi.postran.service.IPostranMonitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 120核阳转运监控Controller
 * 
 * @author ruoyi
 * @date 2022-11-24
 */
@RestController
@RequestMapping("/postran/monitor")
public class PostranMonitorController extends BaseController
{
    @Autowired
    private IPostranMonitorService postranMonitorService;

    /**
     * 查询120核阳转运监控列表
     */
    //@PreAuthorize("@ss.hasPermi('postran:monitor:list')")
    @GetMapping("/list")
    public TableDataInfo list(PostranMonitor postranMonitor)
    {
        startPage();
        List<PostranMonitor> list = postranMonitorService.selectPostranMonitorList(postranMonitor);
        return getDataTable(list);
    }

    /**
     * 导出120核阳转运监控列表
     */
    //@PreAuthorize("@ss.hasPermi('postran:monitor:export')")
    @Log(title = "120核阳转运监控", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PostranMonitor postranMonitor)
    {
        List<PostranMonitor> list = postranMonitorService.selectPostranMonitorList(postranMonitor);
        ExcelUtil<PostranMonitor> util = new ExcelUtil<PostranMonitor>(PostranMonitor.class);
        util.exportExcel(response, list, "120核阳转运监控数据");
    }

    /**
     * 获取120核阳转运监控详细信息
     */
    //@PreAuthorize("@ss.hasPermi('postran:monitor:query')")
    @GetMapping(value = "/{fdId}")
    public AjaxResult getInfo(@PathVariable("fdId") String fdId)
    {
        return success(postranMonitorService.selectPostranMonitorByFdId(fdId));
    }

    /**
     * 新增120核阳转运监控
     */
    //@PreAuthorize("@ss.hasPermi('postran:monitor:add')")
    @Log(title = "120核阳转运监控", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PostranMonitor postranMonitor)
    {
        return toAjax(postranMonitorService.insertPostranMonitor(postranMonitor));
    }

    /**
     * 修改120核阳转运监控
     */
    //@PreAuthorize("@ss.hasPermi('postran:monitor:edit')")
    @Log(title = "120核阳转运监控", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PostranMonitor postranMonitor)
    {
        return toAjax(postranMonitorService.updatePostranMonitor(postranMonitor));
    }

    /**
     * 删除120核阳转运监控
     */
    //@PreAuthorize("@ss.hasPermi('postran:monitor:remove')")
    @Log(title = "120核阳转运监控", businessType = BusinessType.DELETE)
	@DeleteMapping("/{fdIds}")
    public AjaxResult remove(@PathVariable String[] fdIds)
    {
        return toAjax(postranMonitorService.deletePostranMonitorByFdIds(fdIds));
    }
}
