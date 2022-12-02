package com.ruoyi.postran.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.postran.domain.SysDict;
import com.ruoyi.postran.mapper.SysDictMapper;
import com.ruoyi.postran.service.ISysDictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 字典Service业务层处理
 * 
 * @author huangmh
 * @date 2022-11-25
 */
@Service
public class SysDictServiceImpl implements ISysDictService 
{
    @Autowired
    private SysDictMapper sysDictMapper;

    /**
     * 查询字典
     * 
     * @param id 字典主键
     * @return 字典
     */
    @Override
    public SysDict selectSysDictById(Long id)
    {
        return sysDictMapper.selectSysDictById(id);
    }

    /**
     * 查询字典列表
     * 
     * @param sysDict 字典
     * @return 字典
     */
    @Override
    public List<SysDict> selectSysDictList(SysDict sysDict)
    {
        List<SysDict> list = new ArrayList<>();
        // 判断字典类型值
        String dictType = sysDict.getDictType();
        if (StringUtils.isBlank(dictType)) {
            return list;
        }
        // 查询未删除的
        sysDict.setTagDelete("0");
        list = sysDictMapper.selectSysDictList(sysDict);

        return list;
    }

    /**
     * 新增字典
     * 
     * @param sysDict 字典
     * @return 结果
     */
    @Override
    public int insertSysDict(SysDict sysDict)
    {
        // 获取当前时间
        Date nowDate = DateUtils.getNowDate();
        // 获取当前登陆人
        LoginUser loginUser = SecurityUtils.getLoginUser();

        sysDict.setCreator(loginUser.getUsername());
        sysDict.setCreatorId(loginUser.getUserId().toString());
        sysDict.setModifier(loginUser.getUsername());
        sysDict.setModifierId(loginUser.getUserId().toString());
        sysDict.setCreateTime(nowDate);
        sysDict.setUpdateTime(nowDate);
        return sysDictMapper.insertSysDict(sysDict);
    }

    /**
     * 修改字典
     * 
     * @param sysDict 字典
     * @return 结果
     */
    @Override
    public int updateSysDict(SysDict sysDict)
    {
        // 获取当前字典的信息
        SysDict oldSysDict = sysDictMapper.selectSysDictById(sysDict.getId());

        // 获取当前时间
        Date nowDate = DateUtils.getNowDate();
        // 获取当前登陆人
        LoginUser loginUser = SecurityUtils.getLoginUser();

        sysDict.setModifier(loginUser.getUsername());
        sysDict.setModifierId(loginUser.getUserId().toString());
        sysDict.setUpdateTime(nowDate);
        sysDict.setVersion(oldSysDict.getVersion() + 1);
        return sysDictMapper.updateSysDict(sysDict);
    }

    /**
     * 批量删除字典
     * 
     * @param ids 需要删除的字典主键
     * @return 结果
     */
    @Override
    public int deleteSysDictByIds(Long[] ids)
    {
        return sysDictMapper.deleteSysDictByIds(ids);
    }

    /**
     * 删除字典信息
     * 
     * @param id 字典主键
     * @return 结果
     */
    @Override
    public int deleteSysDictById(Long id)
    {
        return sysDictMapper.deleteSysDictById(id);
    }

    @Override
    public int batchDeleteSysDictById(List<Long> ids) {
        // 获取当前时间
        Date nowDate = DateUtils.getNowDate();
        // 获取当前登陆人
        LoginUser loginUser = SecurityUtils.getLoginUser();
        // 逻辑删除成功数
        int count = 0;

        List<SysDict> oldSysDictList = sysDictMapper.selectSysDictByIds(ids);
        for (SysDict oldSysDict : oldSysDictList) {
            SysDict sysDict = new SysDict();

            sysDict.setId(oldSysDict.getId());
            sysDict.setUpdateTime(nowDate);
            sysDict.setModifier(loginUser.getUsername());
            sysDict.setModifierId(loginUser.getUserId().toString());
            sysDict.setTagDelete("1");
            sysDict.setVersion(oldSysDict.getVersion() + 1);
            sysDictMapper.updateSysDict(sysDict);
            count++;
        }

        return count;
    }

    @Override
    public List<Map<String, Object>> selectSysDictByType(String dictType) {
        if (StringUtils.isBlank(dictType)) {
            return null;
        }
        List<Map<String, Object>> list = sysDictMapper.selectSysDictByType(dictType);
        return list;
    }
}
