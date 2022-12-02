package com.ruoyi.web.controller.postran;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.system.domain.ArriveImportOne;
import com.ruoyi.system.domain.ArriveImportTwo;
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
import com.ruoyi.system.domain.PostranArriveImport;
import com.ruoyi.system.service.IPostranArriveImportService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;

/**
 * 到达医院导入Controller
 *
 * @author ruoyi
 * @date 2022-11-25
 */
@RestController
@RequestMapping("/postran/import")
public class PostranArriveImportController extends BaseController
{
    @Autowired
    private IPostranArriveImportService postranArriveImportService;

    /**
     * 查询到达医院导入列表
     */
    //@PreAuthorize("@ss.hasPermi('postran:import:list')")
    @GetMapping("/list")
    public TableDataInfo list(PostranArriveImport postranArriveImport)
    {
        startPage();
        List<PostranArriveImport> list = postranArriveImportService.selectPostranArriveImportList(postranArriveImport);
        return getDataTable(list);
    }

    /**
     * 导出到达医院导入列表
     */
    //@PreAuthorize("@ss.hasPermi('postran:import:export')")
    @Log(title = "到达医院导入", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PostranArriveImport postranArriveImport)
    {
        List<PostranArriveImport> list = postranArriveImportService.selectPostranArriveImportList(postranArriveImport);
        ExcelUtil<PostranArriveImport> util = new ExcelUtil<PostranArriveImport>(PostranArriveImport.class);
        util.exportExcel(response, list, "到达医院导入数据");
    }

    /**
     * 获取到达医院导入详细信息
     */
    //@PreAuthorize("@ss.hasPermi('postran:import:query')")
    @GetMapping(value = "/{importId}")
    public AjaxResult getInfo(@PathVariable("importId") Long importId)
    {
        return success(postranArriveImportService.selectPostranArriveImportByImportId(importId));
    }

    /**
     * 新增到达医院导入
     */
    //@PreAuthorize("@ss.hasPermi('postran:import:add')")
    @Log(title = "到达医院导入", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PostranArriveImport postranArriveImport)
    {
        return toAjax(postranArriveImportService.insertPostranArriveImport(postranArriveImport));
    }

    /**
     * 修改到达医院导入
     */
    //@PreAuthorize("@ss.hasPermi('postran:import:edit')")
    @Log(title = "到达医院导入", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PostranArriveImport postranArriveImport)
    {
        return toAjax(postranArriveImportService.updatePostranArriveImport(postranArriveImport));
    }

    /**
     * 删除到达医院导入
     */
    //@PreAuthorize("@ss.hasPermi('postran:import:remove')")
    @Log(title = "到达医院导入", businessType = BusinessType.DELETE)
	@DeleteMapping("/{importIds}")
    public AjaxResult remove(@PathVariable Long[] importIds)
    {
        return toAjax(postranArriveImportService.deletePostranArriveImportByImportIds(importIds));
    }


    @PostMapping("/importTemplateOne")
    public void importTemplateOne(HttpServletResponse response)
    {
        ExcelUtil<ArriveImportOne> util = new ExcelUtil<ArriveImportOne>(ArriveImportOne.class);
        util.importTemplateExcel(response, "智能外呼导入");
    }


    @PostMapping("/importTemplateTwo")
    public void importTemplateTwo(HttpServletResponse response)
    {
        ExcelUtil<ArriveImportTwo> util = new ExcelUtil<ArriveImportTwo>(ArriveImportTwo.class);
        util.importTemplateExcel(response, "入院信息导入");
    }

    @Log(title = "导入管理", businessType = BusinessType.IMPORT)
    //@PreAuthorize("@ss.hasPermi('postran:import:import')")
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file, String type) throws Exception
    {
        ExcelUtil<PostranArriveImport> util = new ExcelUtil<PostranArriveImport>(PostranArriveImport.class);
        List<PostranArriveImport> userList = util.importExcel(file.getInputStream());
        String operName = getUsername();
        String message = postranArriveImportService.importData(userList, type, operName);
        return success(message);
    }

}
