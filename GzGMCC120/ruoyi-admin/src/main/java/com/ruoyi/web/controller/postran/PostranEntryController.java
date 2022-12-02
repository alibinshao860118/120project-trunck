package com.ruoyi.web.controller.postran;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.postran.domain.PostranEntry;
import com.ruoyi.postran.service.IPostranEntryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

/**
 * 120核阳转运录入Controller
 * 
 * @author ruoyi
 * @date 2022-11-24
 */
@Api("120核阳转运录入")
@RestController
@RequestMapping("/postran/entry")
public class PostranEntryController extends BaseController
{
    @Autowired
    private IPostranEntryService postranEntryService;

    /**
     * 查询120核阳转运录入列表
     */
    @ApiOperation("查询120核阳转运录入列表")
//    @PreAuthorize("@ss.hasPermi('postran:entry:list')")
    @GetMapping("/list")
    public TableDataInfo list(PostranEntry postranEntry)
    {
        startPage();
        List<PostranEntry> list = postranEntryService.selectPostranEntryList(postranEntry);
        return getDataTable(list);
    }

    /**
     * 导出120核阳转运录入列表
     */
    //@PreAuthorize("@ss.hasPermi('postran:entry:export')")
    @Log(title = "120核阳转运录入", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PostranEntry postranEntry)
    {
        List<PostranEntry> list = postranEntryService.selectPostranEntryList(postranEntry);
        ExcelUtil<PostranEntry> util = new ExcelUtil<PostranEntry>(PostranEntry.class);
        util.exportExcel(response, list, "120核阳转运录入数据");
    }

    /**
     * 导入120核阳转运录入列表
     */
    @ApiOperation("导入120核阳转运录入列表")
    @Log(title = "120核阳转运录入", businessType = BusinessType.IMPORT)
//    @PreAuthorize("@ss.hasPermi('postran:entry:import')")
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception
    {
        ExcelUtil<PostranEntry> util = new ExcelUtil<PostranEntry>(PostranEntry.class);
        List<PostranEntry> postranEntryList = util.importExcel(file.getInputStream());
        String message = postranEntryService.importPostranEntry(postranEntryList, updateSupport);
        return success(message);
    }

    /**
     * 获取120核阳转运录入详细信息
     */
    @ApiOperation("获取120核阳转运信息")
    //@PreAuthorize("@ss.hasPermi('postran:entry:query')")
    @GetMapping(value = "/{fdId}")
    public AjaxResult getInfo(@PathVariable("fdId") String fdId)
    {
        return success(postranEntryService.selectPostranEntryByFdId(fdId));
    }

    /**
     * 新增120核阳转运录入
     */
    @ApiOperation("录入120核阳转运信息")
    //@PreAuthorize("@ss.hasPermi('postran:entry:add')")
    @Log(title = "120核阳转运录入", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Valid @RequestBody PostranEntry postranEntry)
    {
        // 数据来源：新增
        postranEntry.setDataSource("1");
        return toAjax(postranEntryService.insertPostranEntry(postranEntry));
    }

    /**
     * 修改120核阳转运录入
     */
    @ApiOperation("编辑120核阳转运信息")
    //@PreAuthorize("@ss.hasPermi('postran:entry:edit')")
    @Log(title = "120核阳转运录入", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PostranEntry postranEntry)
    {
        return toAjax(postranEntryService.updatePostranEntry(postranEntry));
    }

    /**
     * 删除120核阳转运录入
     */
    //@PreAuthorize("@ss.hasPermi('postran:entry:remove')")
    @Log(title = "120核阳转运录入", businessType = BusinessType.DELETE)
	@DeleteMapping("/{fdIds}")
    public AjaxResult remove(@PathVariable String[] fdIds)
    {
        return toAjax(postranEntryService.deletePostranEntryByFdIds(fdIds));
    }
}
