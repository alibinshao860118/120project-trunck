package com.ruoyi.postran.service;

import java.util.List;
import java.util.Map;

import com.ruoyi.postran.domain.SysDict;

/**
 * 字典Service接口
 * 
 * @author huangmh
 * @date 2022-11-25
 */
public interface ISysDictService 
{
    /**
     * 查询字典
     * 
     * @param id 字典主键
     * @return 字典
     */
    public SysDict selectSysDictById(Long id);

    /**
     * 查询字典列表
     * 
     * @param sysDict 字典
     * @return 字典集合
     */
    public List<SysDict> selectSysDictList(SysDict sysDict);

    /**
     * 新增字典
     * 
     * @param sysDict 字典
     * @return 结果
     */
    public int insertSysDict(SysDict sysDict);

    /**
     * 修改字典
     * 
     * @param sysDict 字典
     * @return 结果
     */
    public int updateSysDict(SysDict sysDict);

    /**
     * 批量删除字典
     * 
     * @param ids 需要删除的字典主键集合
     * @return 结果
     */
    public int deleteSysDictByIds(Long[] ids);

    /**
     * 删除字典信息
     * 
     * @param id 字典主键
     * @return 结果
     */
    public int deleteSysDictById(Long id);

    /**
     * 根据id批量删除字典
     * @param ids
     * @return
     */
    int batchDeleteSysDictById(List<Long> ids);

    /**
     * 根据字典类型值获取字典数据
     * @param dictType
     * @return
     */
    List<Map<String, Object>> selectSysDictByType(String dictType);
}
