package com.ruoyi.web.controller.postran;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.postran.domain.*;
import com.ruoyi.postran.service.IPostranAssignService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
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
 * 120核阳转运调派Controller
 *
 * @author ruoyi
 * @date 2022-11-24
 */
@RestController
@RequestMapping("/postran/assign")
public class PostranAssignController extends BaseController
{
    @Autowired
    private IPostranAssignService postranAssignService;

    /**
     * 查询120核阳转运调派列表
     */
    //@PreAuthorize("@ss.hasPermi('postran:assign:list')")
    @GetMapping("/list")
    public TableDataInfo list(PostranEntryAssign postranEntryAssign)
    {
        startPage();
//        List<PostranAssign> list = postranAssignService.selectPostranAssignList(postranAssign);
        List<PostranEntryAssign> list = postranAssignService.selectPostranEntryAssignList(postranEntryAssign);
        return getDataTable(list);
    }

    /**
     * 导出120核阳转运调派列表
     */
    //@PreAuthorize("@ss.hasPermi('postran:assign:export')")
    @Log(title = "120核阳转运调派", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PostranAssign postranAssign)
    {
        List<PostranAssign> list = postranAssignService.selectPostranAssignList(postranAssign);
        ExcelUtil<PostranAssign> util = new ExcelUtil<PostranAssign>(PostranAssign.class);
        util.exportExcel(response, list, "120核阳转运调派数据");
    }

    /**
     * 获取120核阳转运调派详细信息
     */
    //@PreAuthorize("@ss.hasPermi('postran:assign:query')")
    @GetMapping(value = "/{fdId}")
    public AjaxResult getInfo(@PathVariable("fdId") String fdId)
    {
        return success(postranAssignService.selectPostranAssignByFdId(fdId));
    }

    /**
     * 新增120核阳转运调派
     */
    //@PreAuthorize("@ss.hasPermi('postran:assign:add')")
    @Log(title = "120核阳转运调派", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PostranAssign postranAssign)
    {
        return toAjax(postranAssignService.insertPostranAssign(postranAssign));
    }

    /**
     * 修改120核阳转运调派
     */
    //@PreAuthorize("@ss.hasPermi('postran:assign:edit')")
    @Log(title = "120核阳转运调派", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PostranAssign postranAssign)
    {
        return toAjax(postranAssignService.updatePostranAssign(postranAssign));
    }

    /**
     * 删除120核阳转运调派
     */
    //@PreAuthorize("@ss.hasPermi('postran:assign:remove')")
    @Log(title = "120核阳转运调派", businessType = BusinessType.DELETE)
	@DeleteMapping("/{fdIds}")
    public AjaxResult remove(@PathVariable String[] fdIds)
    {
        return toAjax(postranAssignService.deletePostranAssignByFdIds(fdIds));
    }


    /**
     * 编辑120核阳转运任务状态 1 延续，2 中止
     * @param postranAssignStatus
     * @param
     * @return
     */
    //@PreAuthorize("@ss.hasPermi('postran:assign:editAssignStatus')")
    @ApiOperation("编辑120核阳转运任务状态")
    @Log(title = "编辑120核阳转运任务状态", businessType = BusinessType.UPDATE)
    @PostMapping("/editStatus")
    public AjaxResult editAssignStatus(@RequestBody PostranAssignStatus postranAssignStatus) throws Exception {
        return toAjax(postranAssignService.editAssignStatus(postranAssignStatus.getFdIds(),
                postranAssignStatus.getTaskType(), postranAssignStatus.getReason()));
    }

    /**
     * 认领120核阳转运任务
     * @param postranAssignTask
     * @return
     */
    //@PreAuthorize("@ss.hasPermi('postran:assign:claimTask')")
    @ApiOperation("认领120核阳转运任务")
    @Log(title = "认领120核阳转运任务", businessType = BusinessType.UPDATE)
    @PostMapping("/claimTask")
    public AjaxResult claimTask(@RequestBody PostranAssignTask postranAssignTask) {
        return toAjax(postranAssignService.claimTask(postranAssignTask));
    }

    /**
     * 查看120核阳转运任务
     * @param fdId
     * @return
     */
    //@PreAuthorize("@ss.hasPermi('postran:assign:getAssignTask')")
    @ApiOperation("查看120核阳转运任务")
    @GetMapping("/getAssignTask/{fdId}")
    public AjaxResult getAssignTask(@PathVariable("fdId") String fdId) {
        return success(postranAssignService.getAssignTask(fdId));
    }
}
