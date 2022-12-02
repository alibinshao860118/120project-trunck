package com.ruoyi.postran.service;

import com.ruoyi.postran.domain.PostranAssign;
import com.ruoyi.postran.domain.PostranAssignTask;
import com.ruoyi.postran.domain.PostranEntryAssign;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 120核阳转运调派Service接口
 *
 * @author ruoyi
 * @date 2022-11-24
 */
public interface IPostranAssignService
{
    /**
     * 查询120核阳转运调派
     *
     * @param fdId 120核阳转运调派主键
     * @return 120核阳转运调派
     */
    public PostranAssign selectPostranAssignByFdId(String fdId);

    /**
     * 查询120核阳转运调派列表
     *
     * @param postranAssign 120核阳转运调派
     * @return 120核阳转运调派集合
     */
    public List<PostranAssign> selectPostranAssignList(PostranAssign postranAssign);

    /**
     * 新增120核阳转运调派
     *
     * @param postranAssign 120核阳转运调派
     * @return 结果
     */
    public int insertPostranAssign(PostranAssign postranAssign);

    /**
     * 修改120核阳转运调派
     *
     * @param postranAssign 120核阳转运调派
     * @return 结果
     */
    public int updatePostranAssign(PostranAssign postranAssign);

    /**
     * 批量删除120核阳转运调派
     *
     * @param fdIds 需要删除的120核阳转运调派主键集合
     * @return 结果
     */
    public int deletePostranAssignByFdIds(String[] fdIds);

    /**
     * 删除120核阳转运调派信息
     *
     * @param fdId 120核阳转运调派主键
     * @return 结果
     */
    public int deletePostranAssignByFdId(String fdId);

    public List<PostranEntryAssign> selectPostranEntryAssignList(PostranEntryAssign postranEntryAssign);

    /**
     * 编辑120核阳转运任务状态 1 延续，2 中止
     * @param fdIds
     * @param taskType
     * @return
     */
    int editAssignStatus(List<String> fdIds, String taskType, String reason) throws Exception;

    /**
     * 认领120核阳转运任务
     * @param postranAssignTask
     * @return
     */
    @Transactional
    int claimTask(PostranAssignTask postranAssignTask);

    /**
     * 查看120核阳转运任务
     * @param fdId
     * @return
     */
    PostranAssignTask getAssignTask(String fdId);
}
