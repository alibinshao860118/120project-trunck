package com.ruoyi.web.controller.postran;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.postran.domain.SysDict;
import com.ruoyi.postran.service.ISysDictService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
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
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 字典表Controller
 * 
 * @author huangmh
 * @date 2022-11-23
 */
@RestController
@RequestMapping("/postran/dict")
public class SysDictController extends BaseController
{
    @Autowired
    private ISysDictService sysDictService;

    /**
     * 查询字典表列表
     */
    @ApiOperation("分页查询字典列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "页码", required = true, defaultValue = "1"),
            @ApiImplicitParam(name = "pageSize", value = "每页显示条数", required = true, defaultValue = "10"),
    })
    //@PreAuthorize("@ss.hasPermi('postran:dict:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysDict sysDict)
    {
        startPage();
        List<SysDict> list = sysDictService.selectSysDictList(sysDict);
        return getDataTable(list);
    }

    /**
     * 导出字典表列表
     */
    //@PreAuthorize("@ss.hasPermi('postran:dict:export')")
    @Log(title = "字典表", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysDict sysDict)
    {
        List<SysDict> list = sysDictService.selectSysDictList(sysDict);
        ExcelUtil<SysDict> util = new ExcelUtil<SysDict>(SysDict.class);
        util.exportExcel(response, list, "字典表数据");
    }

    /**
     * 获取字典表详细信息
     */
    //@PreAuthorize("@ss.hasPermi('postran:dict:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(sysDictService.selectSysDictById(id));
    }

    /**
     * 新增字典表
     */
    @ApiOperation("新增字典数据")
    //@PreAuthorize("@ss.hasPermi('postran:dict:add')")
    @Log(title = "字典表", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysDict sysDict)
    {
        return toAjax(sysDictService.insertSysDict(sysDict));
    }

    /**
     * 修改字典表
     */
    //@PreAuthorize("@ss.hasPermi('postran:dict:edit')")
    @ApiOperation("编辑字典数据")
    @Log(title = "字典表", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysDict sysDict)
    {
        return toAjax(sysDictService.updateSysDict(sysDict));
    }

    /**
     * 删除字典表
     */
    //@PreAuthorize("@ss.hasPermi('postran:dict:remove')")
    @ApiOperation("删除字典数据")
    @Log(title = "字典表", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(sysDictService.deleteSysDictByIds(ids));
    }

    //@PreAuthorize("@ss.hasPermi('postran:dict:removeData')")
    @ApiOperation("逻辑删除字典数据")
    @Log(title = "字典表", businessType = BusinessType.UPDATE)
    @PostMapping("/delete")
    public AjaxResult removeData(@RequestBody List<Long> ids) {
        return toAjax(sysDictService.batchDeleteSysDictById(ids));
    }

    //@PreAuthorize("@ss.hasPermi('postran:dict:getDictInfo')")
    @ApiOperation("获取指定字典类型数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "dictType", value = "字典类型", required = true),
    })
    @GetMapping("/find")
    public AjaxResult getDictInfo(String dictType) {
        List<Map<String, Object>> list = sysDictService.selectSysDictByType(dictType);
        if (list == null) {
            return AjaxResult.error();
        }
        return AjaxResult.success(list);
    }
}
