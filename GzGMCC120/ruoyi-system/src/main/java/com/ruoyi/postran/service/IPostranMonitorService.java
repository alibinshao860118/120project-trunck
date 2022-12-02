package com.ruoyi.postran.service;

import com.ruoyi.postran.domain.PostranMonitor;

import java.util.List;

/**
 * 120核阳转运监控Service接口
 * 
 * @author ruoyi
 * @date 2022-11-24
 */
public interface IPostranMonitorService 
{
    /**
     * 查询120核阳转运监控
     * 
     * @param fdId 120核阳转运监控主键
     * @return 120核阳转运监控
     */
    public PostranMonitor selectPostranMonitorByFdId(String fdId);

    /**
     * 查询120核阳转运监控列表
     * 
     * @param postranMonitor 120核阳转运监控
     * @return 120核阳转运监控集合
     */
    public List<PostranMonitor> selectPostranMonitorList(PostranMonitor postranMonitor);

    /**
     * 新增120核阳转运监控
     * 
     * @param postranMonitor 120核阳转运监控
     * @return 结果
     */
    public int insertPostranMonitor(PostranMonitor postranMonitor);

    /**
     * 修改120核阳转运监控
     * 
     * @param postranMonitor 120核阳转运监控
     * @return 结果
     */
    public int updatePostranMonitor(PostranMonitor postranMonitor);

    /**
     * 批量删除120核阳转运监控
     * 
     * @param fdIds 需要删除的120核阳转运监控主键集合
     * @return 结果
     */
    public int deletePostranMonitorByFdIds(String[] fdIds);

    /**
     * 删除120核阳转运监控信息
     * 
     * @param fdId 120核阳转运监控主键
     * @return 结果
     */
    public int deletePostranMonitorByFdId(String fdId);
}
