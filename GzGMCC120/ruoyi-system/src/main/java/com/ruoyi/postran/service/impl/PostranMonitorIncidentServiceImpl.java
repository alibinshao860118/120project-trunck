package com.ruoyi.postran.service.impl;

import com.ruoyi.postran.domain.PostranMonitorIncident;
import com.ruoyi.postran.mapper.PostranMonitorIncidentMapper;
import com.ruoyi.postran.service.IPostranMonitorIncidentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 120核阳转运监控事件Service业务层处理
 * 
 * @author ruoyi
 * @date 2022-11-24
 */
@Service
public class PostranMonitorIncidentServiceImpl implements IPostranMonitorIncidentService
{
    @Autowired
    private PostranMonitorIncidentMapper postranMonitorIncidentMapper;

    /**
     * 查询120核阳转运监控事件
     * 
     * @param incident 120核阳转运监控事件主键
     * @return 120核阳转运监控事件
     */
    @Override
    public PostranMonitorIncident selectPostranMonitorIncidentByIncident(String incident)
    {
        return postranMonitorIncidentMapper.selectPostranMonitorIncidentByIncident(incident);
    }

    /**
     * 查询120核阳转运监控事件列表
     * 
     * @param postranMonitorIncident 120核阳转运监控事件
     * @return 120核阳转运监控事件
     */
    @Override
    public List<PostranMonitorIncident> selectPostranMonitorIncidentList(PostranMonitorIncident postranMonitorIncident)
    {
        return postranMonitorIncidentMapper.selectPostranMonitorIncidentList(postranMonitorIncident);
    }

    /**
     * 新增120核阳转运监控事件
     * 
     * @param postranMonitorIncident 120核阳转运监控事件
     * @return 结果
     */
    @Override
    public int insertPostranMonitorIncident(PostranMonitorIncident postranMonitorIncident)
    {
        return postranMonitorIncidentMapper.insertPostranMonitorIncident(postranMonitorIncident);
    }

    /**
     * 修改120核阳转运监控事件
     * 
     * @param postranMonitorIncident 120核阳转运监控事件
     * @return 结果
     */
    @Override
    public int updatePostranMonitorIncident(PostranMonitorIncident postranMonitorIncident)
    {
        return postranMonitorIncidentMapper.updatePostranMonitorIncident(postranMonitorIncident);
    }

    /**
     * 批量删除120核阳转运监控事件
     * 
     * @param incidents 需要删除的120核阳转运监控事件主键
     * @return 结果
     */
    @Override
    public int deletePostranMonitorIncidentByIncidents(String[] incidents)
    {
        return postranMonitorIncidentMapper.deletePostranMonitorIncidentByIncidents(incidents);
    }

    /**
     * 删除120核阳转运监控事件信息
     * 
     * @param incident 120核阳转运监控事件主键
     * @return 结果
     */
    @Override
    public int deletePostranMonitorIncidentByIncident(String incident)
    {
        return postranMonitorIncidentMapper.deletePostranMonitorIncidentByIncident(incident);
    }
}
