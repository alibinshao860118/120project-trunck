package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.Office;

/**
 * 合同文件Service接口
 * 
 * @author ruoyi
 * @date 2022-12-02
 */
public interface IOfficeService 
{
    /**
     * 查询合同文件
     * 
     * @param id 合同文件主键
     * @return 合同文件
     */
    public Office selectOfficeById(Long id);

    /**
     * 查询合同文件列表
     * 
     * @param office 合同文件
     * @return 合同文件集合
     */
    public List<Office> selectOfficeList(Office office);

    /**
     * 新增合同文件
     * 
     * @param office 合同文件
     * @return 结果
     */
    public int insertOffice(Office office);

    /**
     * 修改合同文件
     * 
     * @param office 合同文件
     * @return 结果
     */
    public int updateOffice(Office office);

    /**
     * 批量删除合同文件
     * 
     * @param ids 需要删除的合同文件主键集合
     * @return 结果
     */
    public int deleteOfficeByIds(Long[] ids);

    /**
     * 删除合同文件信息
     * 
     * @param id 合同文件主键
     * @return 结果
     */
    public int deleteOfficeById(Long id);
}
