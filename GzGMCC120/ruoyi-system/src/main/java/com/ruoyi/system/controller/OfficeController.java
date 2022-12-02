package com.ruoyi.system.controller;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.deepoove.poi.config.Configure;
import com.deepoove.poi.plugin.table.LoopRowTableRenderPolicy;
import com.ruoyi.common.annotation.Anonymous;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
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
import com.deepoove.poi.XWPFTemplate;
/**
 * 合同文件Controller
 * 
 * @author ruoyi
 * @date 2022-12-02
 */
@Anonymous
@RestController
@RequestMapping("/system/office")
public class OfficeController extends BaseController
{
    @Autowired
    private IOfficeService officeService;
    final String docUrl="D:\\A-kingpoint\\改造20221201\\";
    /**
     * 查询合同文件列表
     */
//    @PreAuthorize("@ss.hasPermi('system:office:list')")
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
     * 转换合同文件
     */
    @PostMapping(value = "/changeinfo")
    public AjaxResult changeinfo(@RequestBody String args)
    {
        String result="sucess";
        Map<String,Object> map = new HashMap<>();
        JSONArray req = JSONArray.parseArray(args);
        //获取类别
//        Office office = new Office();

        for (int i = 0; i < req.size(); i++) {
            JSONObject obj = JSONObject.parseObject(req.get(i).toString());
            if (obj.containsKey("lb")){
//                office.setLb((String)obj.get("lb"));
                map.put((String)obj.get("lb"),(String)obj.get("content"));
            }
        }

//        List<Office> list = officeService.selectOfficeList(office);

        String documentname ="template22.docx";

        XWPFTemplate template = XWPFTemplate.compile(docUrl+documentname).render(
                map);
//        template.write(new FileOutputStream("D:\\kingpoint\\output.docx"));
//        template.close();
        try {
            template.writeAndClose(new FileOutputStream(docUrl+"output\\output1.docx"));
        } catch (IOException e) {
            e.printStackTrace();
            return error();
        }

        return success(result);
    }
    /**
     * 转换合同文件excel
     */
    @PostMapping(value = "/changeinfoExcel")
    public AjaxResult changeinfoExcel(@RequestBody String args)
    {
        String result="sucess";

        JSONArray req = JSONArray.parseArray(args);

        List<Map> offices = req.toJavaList(Map.class);
        //获取类别


        LoopRowTableRenderPolicy policy = new LoopRowTableRenderPolicy();

        Configure config = Configure.builder()
                .bind("offices", policy)
                  .build();     // bind("labors", policy)
        String documentname ="template33.docx";
        XWPFTemplate template = XWPFTemplate.compile(docUrl+documentname, config).render(
                new HashMap<String, Object>() {{
                    put("offices", offices);
//                    put("labors", labors);
                }}
        );


        try {
            template.writeAndClose(new FileOutputStream(docUrl+"output\\output2.docx"));
        } catch (IOException e) {
            e.printStackTrace();
            return error();
        }

        return success(result);
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

    private Map map =new HashMap<String, Object>(){{
        put("title", "Hi, poi-tl Word模板引擎");
        put("name", "李思");
    }};
    /**
     * 修改合同文件
     */
    @PreAuthorize("@ss.hasPermi('system:office:edit')")
    @Log(title = "合同文件", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Office office)
    {
        String result="sucess";
        String documentname ="template22.docx";

        XWPFTemplate template = XWPFTemplate.compile("D:\\A-kingpoint\\改造20221201\\"+documentname).render(
                map);
//        template.write(new FileOutputStream("D:\\kingpoint\\output.docx"));
//        template.close();
        try {
            template.writeAndClose(new FileOutputStream("D:\\A-kingpoint\\output.docx"));
        } catch (IOException e) {
            e.printStackTrace();
        }
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
