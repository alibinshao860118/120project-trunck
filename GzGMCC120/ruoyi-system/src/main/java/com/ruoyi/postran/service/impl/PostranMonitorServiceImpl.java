package com.ruoyi.postran.service.impl;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.postran.domain.PostranMonitor;
import com.ruoyi.postran.mapper.PostranMonitorMapper;
import com.ruoyi.postran.service.IPostranMonitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 120核阳转运监控Service业务层处理
 * 
 * @author ruoyi
 * @date 2022-11-24
 */
@Service
public class PostranMonitorServiceImpl implements IPostranMonitorService
{
    @Autowired
    private PostranMonitorMapper postranMonitorMapper;

    /**
     * 查询120核阳转运监控
     * 
     * @param fdId 120核阳转运监控主键
     * @return 120核阳转运监控
     */
    @Override
    public PostranMonitor selectPostranMonitorByFdId(String fdId)
    {
        return postranMonitorMapper.selectPostranMonitorByFdId(fdId);
    }

    /**
     * 查询120核阳转运监控列表
     * 
     * @param postranMonitor 120核阳转运监控
     * @return 120核阳转运监控
     */
    @Override
    public List<PostranMonitor> selectPostranMonitorList(PostranMonitor postranMonitor)
    {
        return postranMonitorMapper.selectPostranMonitorList(postranMonitor);
    }

    /**
     * 新增120核阳转运监控
     * 
     * @param postranMonitor 120核阳转运监控
     * @return 结果
     */
    @Override
    public int insertPostranMonitor(PostranMonitor postranMonitor)
    {
        postranMonitor.setCreateTime(DateUtils.getNowDate());
        return postranMonitorMapper.insertPostranMonitor(postranMonitor);
    }

    /**
     * 修改120核阳转运监控
     * 
     * @param postranMonitor 120核阳转运监控
     * @return 结果
     */
    @Override
    public int updatePostranMonitor(PostranMonitor postranMonitor)
    {
        postranMonitor.setUpdateTime(DateUtils.getNowDate());
        return postranMonitorMapper.updatePostranMonitor(postranMonitor);
    }

    /**
     * 批量删除120核阳转运监控
     * 
     * @param fdIds 需要删除的120核阳转运监控主键
     * @return 结果
     */
    @Override
    public int deletePostranMonitorByFdIds(String[] fdIds)
    {
        return postranMonitorMapper.deletePostranMonitorByFdIds(fdIds);
    }

    /**
     * 删除120核阳转运监控信息
     * 
     * @param fdId 120核阳转运监控主键
     * @return 结果
     */
    @Override
    public int deletePostranMonitorByFdId(String fdId)
    {
        return postranMonitorMapper.deletePostranMonitorByFdId(fdId);
    }
}
