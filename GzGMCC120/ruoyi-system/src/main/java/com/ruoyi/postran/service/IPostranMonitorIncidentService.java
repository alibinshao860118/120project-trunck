package com.ruoyi.postran.service;

import com.ruoyi.postran.domain.PostranMonitorIncident;

import java.util.List;

/**
 * 120核阳转运监控事件Service接口
 * 
 * @author ruoyi
 * @date 2022-11-24
 */
public interface IPostranMonitorIncidentService 
{
    /**
     * 查询120核阳转运监控事件
     * 
     * @param incident 120核阳转运监控事件主键
     * @return 120核阳转运监控事件
     */
    public PostranMonitorIncident selectPostranMonitorIncidentByIncident(String incident);

    /**
     * 查询120核阳转运监控事件列表
     * 
     * @param postranMonitorIncident 120核阳转运监控事件
     * @return 120核阳转运监控事件集合
     */
    public List<PostranMonitorIncident> selectPostranMonitorIncidentList(PostranMonitorIncident postranMonitorIncident);

    /**
     * 新增120核阳转运监控事件
     * 
     * @param postranMonitorIncident 120核阳转运监控事件
     * @return 结果
     */
    public int insertPostranMonitorIncident(PostranMonitorIncident postranMonitorIncident);

    /**
     * 修改120核阳转运监控事件
     * 
     * @param postranMonitorIncident 120核阳转运监控事件
     * @return 结果
     */
    public int updatePostranMonitorIncident(PostranMonitorIncident postranMonitorIncident);

    /**
     * 批量删除120核阳转运监控事件
     * 
     * @param incidents 需要删除的120核阳转运监控事件主键集合
     * @return 结果
     */
    public int deletePostranMonitorIncidentByIncidents(String[] incidents);

    /**
     * 删除120核阳转运监控事件信息
     * 
     * @param incident 120核阳转运监控事件主键
     * @return 结果
     */
    public int deletePostranMonitorIncidentByIncident(String incident);
}
