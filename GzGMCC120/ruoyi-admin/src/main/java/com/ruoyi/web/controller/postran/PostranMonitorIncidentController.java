package com.ruoyi.web.controller.postran;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.postran.domain.PostranMonitorIncident;
import com.ruoyi.postran.service.IPostranMonitorIncidentService;
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
 * 120核阳转运监控事件Controller
 * 
 * @author ruoyi
 * @date 2022-11-24
 */
@RestController
@RequestMapping("/postran/incident")
public class PostranMonitorIncidentController extends BaseController
{
    @Autowired
    private IPostranMonitorIncidentService postranMonitorIncidentService;

    /**
     * 查询120核阳转运监控事件列表
     */
    //@PreAuthorize("@ss.hasPermi('postran:incident:list')")
    @GetMapping("/list")
    public TableDataInfo list(PostranMonitorIncident postranMonitorIncident)
    {
        startPage();
        List<PostranMonitorIncident> list = postranMonitorIncidentService.selectPostranMonitorIncidentList(postranMonitorIncident);
        return getDataTable(list);
    }

    /**
     * 导出120核阳转运监控事件列表
     */
    //@PreAuthorize("@ss.hasPermi('postran:incident:export')")
    @Log(title = "120核阳转运监控事件", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PostranMonitorIncident postranMonitorIncident)
    {
        List<PostranMonitorIncident> list = postranMonitorIncidentService.selectPostranMonitorIncidentList(postranMonitorIncident);
        ExcelUtil<PostranMonitorIncident> util = new ExcelUtil<PostranMonitorIncident>(PostranMonitorIncident.class);
        util.exportExcel(response, list, "120核阳转运监控事件数据");
    }

    /**
     * 获取120核阳转运监控事件详细信息
     */
    //@PreAuthorize("@ss.hasPermi('postran:incident:query')")
    @GetMapping(value = "/{incident}")
    public AjaxResult getInfo(@PathVariable("incident") String incident)
    {
        return success(postranMonitorIncidentService.selectPostranMonitorIncidentByIncident(incident));
    }

    /**
     * 新增120核阳转运监控事件
     */
    //@PreAuthorize("@ss.hasPermi('postran:incident:add')")
    @Log(title = "120核阳转运监控事件", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PostranMonitorIncident postranMonitorIncident)
    {
        return toAjax(postranMonitorIncidentService.insertPostranMonitorIncident(postranMonitorIncident));
    }

    /**
     * 修改120核阳转运监控事件
     */
    //@PreAuthorize("@ss.hasPermi('postran:incident:edit')")
    @Log(title = "120核阳转运监控事件", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PostranMonitorIncident postranMonitorIncident)
    {
        return toAjax(postranMonitorIncidentService.updatePostranMonitorIncident(postranMonitorIncident));
    }

    /**
     * 删除120核阳转运监控事件
     */
    //@PreAuthorize("@ss.hasPermi('postran:incident:remove')")
    @Log(title = "120核阳转运监控事件", businessType = BusinessType.DELETE)
	@DeleteMapping("/{incidents}")
    public AjaxResult remove(@PathVariable String[] incidents)
    {
        return toAjax(postranMonitorIncidentService.deletePostranMonitorIncidentByIncidents(incidents));
    }
}
