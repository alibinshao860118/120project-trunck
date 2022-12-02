package com.ruoyi.postran.mapper;

import java.util.List;
import java.util.Map;

import com.ruoyi.postran.domain.SysDict;
import org.apache.ibatis.annotations.Param;

/**
 * 字典Mapper接口
 * 
 * @author huangmh
 * @date 2022-11-25
 */
public interface SysDictMapper 
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
     * 删除字典
     * 
     * @param id 字典主键
     * @return 结果
     */
    public int deleteSysDictById(Long id);

    /**
     * 批量删除字典
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSysDictByIds(Long[] ids);

    /**
     * 根据id批量查询字典
     * @param ids
     * @return
     */
    List<SysDict> selectSysDictByIds(List<Long> ids);

    /**
     * 根据字典类型值查询字典， 1 呼车单位，2 高危因素，3 派出医院，4 暴露风险，5 医院，6 任务中止原因
     * @param sysDict
     * @return
     */
    List<Map<String, Object>> selectSysDictByType(String dictType);

    /**
     * 根据字典类型+value值查询
     * @param dictType 字典类型（1 呼车单位，2 高危因素，3 派出医院，4 暴露风险，5 医院，6 任务中止原因）
     * @param dictValue 字典值
     * @return
     */
    SysDict selectByTypeAndValue(@Param(value = "dictType") String dictType, @Param(value = "dictValue") String dictValue);
}
