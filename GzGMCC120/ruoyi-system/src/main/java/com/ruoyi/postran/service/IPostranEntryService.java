package com.ruoyi.postran.service;

import com.ruoyi.postran.domain.PostranEntry;

import java.util.List;

/**
 * 120核阳转运录入Service接口
 * 
 * @author ruoyi
 * @date 2022-11-24
 */
public interface IPostranEntryService 
{
    /**
     * 查询120核阳转运录入
     * 
     * @param fdId 120核阳转运录入主键
     * @return 120核阳转运录入
     */
    public PostranEntry selectPostranEntryByFdId(String fdId);

    /**
     * 查询120核阳转运录入列表
     * 
     * @param postranEntry 120核阳转运录入
     * @return 120核阳转运录入集合
     */
    public List<PostranEntry> selectPostranEntryList(PostranEntry postranEntry);

    /**
     * 新增120核阳转运录入
     * 
     * @param postranEntry 120核阳转运录入
     * @return 结果
     */
    public int insertPostranEntry(PostranEntry postranEntry);

    /**
     * 修改120核阳转运录入
     * 
     * @param postranEntry 120核阳转运录入
     * @return 结果
     */
    public int updatePostranEntry(PostranEntry postranEntry);

    /**
     * 批量删除120核阳转运录入
     * 
     * @param fdIds 需要删除的120核阳转运录入主键集合
     * @return 结果
     */
    public int deletePostranEntryByFdIds(String[] fdIds);

    /**
     * 删除120核阳转运录入信息
     * 
     * @param fdId 120核阳转运录入主键
     * @return 结果
     */
    public int deletePostranEntryByFdId(String fdId);

    /**
     * 导入120核阳转运录入数据
     *
     * @param postranEntryList 转运录入数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @return 结果
     */
    public String importPostranEntry(List<PostranEntry> postranEntryList, Boolean isUpdateSupport);
}
