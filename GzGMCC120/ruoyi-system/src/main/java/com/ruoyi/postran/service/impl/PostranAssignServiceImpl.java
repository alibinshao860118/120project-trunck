package com.ruoyi.postran.service.impl;

import com.ruoyi.common.constant.HttpStatus;
import com.ruoyi.common.constant.PostranConstants;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.postran.domain.*;
import com.ruoyi.postran.mapper.PostranAssignMapper;
import com.ruoyi.postran.mapper.PostranEntryMapper;
import com.ruoyi.postran.mapper.SequenceMapper;
import com.ruoyi.postran.service.IPostranAssignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 120核阳转运调派Service业务层处理
 *
 * @author ruoyi
 * @date 2022-11-24
 */
@Service
public class PostranAssignServiceImpl implements IPostranAssignService
{
    @Autowired
    private PostranAssignMapper postranAssignMapper;
    @Autowired
    private PostranEntryMapper postranEntryMapper;
    @Autowired
    private SequenceMapper sequenceMapper;

    /**
     * 查询120核阳转运调派
     *
     * @param fdId 120核阳转运调派主键
     * @return 120核阳转运调派
     */
    @Override
    public PostranAssign selectPostranAssignByFdId(String fdId)
    {
        return postranAssignMapper.selectPostranAssignByFdId(fdId);
    }

    /**
     * 查询120核阳转运调派列表
     *
     * @param postranAssign 120核阳转运调派
     * @return 120核阳转运调派
     */
    @Override
    public List<PostranAssign> selectPostranAssignList(PostranAssign postranAssign)
    {
        return postranAssignMapper.selectPostranAssignList(postranAssign);
    }

    /**
     * 新增120核阳转运调派
     *
     * @param postranAssign 120核阳转运调派
     * @return 结果
     */
    @Override
    public int insertPostranAssign(PostranAssign postranAssign)
    {
        postranAssign.setCreateTime(DateUtils.getNowDate());
        return postranAssignMapper.insertPostranAssign(postranAssign);
    }

    /**
     * 修改120核阳转运调派
     *
     * @param postranAssign 120核阳转运调派
     * @return 结果
     */
    @Override
    public int updatePostranAssign(PostranAssign postranAssign)
    {
        postranAssign.setUpdateTime(DateUtils.getNowDate());
        return postranAssignMapper.updatePostranAssign(postranAssign);
    }

    /**
     * 批量删除120核阳转运调派
     *
     * @param fdIds 需要删除的120核阳转运调派主键
     * @return 结果
     */
    @Override
    public int deletePostranAssignByFdIds(String[] fdIds)
    {
        return postranAssignMapper.deletePostranAssignByFdIds(fdIds);
    }

    /**
     * 删除120核阳转运调派信息
     *
     * @param fdId 120核阳转运调派主键
     * @return 结果
     */
    @Override
    public int deletePostranAssignByFdId(String fdId)
    {
        return postranAssignMapper.deletePostranAssignByFdId(fdId);
    }

    @Override
    public List<PostranEntryAssign> selectPostranEntryAssignList(PostranEntryAssign postranEntryAssign) {
        return postranAssignMapper.selectPostranEntryAssignList(postranEntryAssign);

    }

    /**
     * 编辑120核阳转运任务状态 1 延续，2 中止
     * @param fdIds
     * @param taskType
     * @return
     */
    @Override
    public int editAssignStatus(List<String> fdIds, String taskType, String reason) throws Exception {
        if (!PostranConstants.TASK_ASSIGN.equals(taskType) && !PostranConstants.TASK_STOP.equals(taskType)) {
            throw new ServiceException("非法调派状态改变，请重新选择", HttpStatus.ERROR);
        }

        // 获取患者信息和调派信息
        List<Map<String, Object>> mapList = postranAssignMapper.getPostranAssignList(fdIds);

        // 获取当前时间
        Date nowDate = DateUtils.getNowDate();
        // 获取当前登录人id
        String username = SecurityUtils.getUsername();

        for (Map<String, Object> map : mapList) {
            String fdId = (String) map.get("fd_id");
            String oldAssignStatus = (String) map.get("assign_status");
            Object oldJobSuspendReason = map.get("job_suspend_reason");
            if (oldAssignStatus.equals(taskType)) {
                throw new ServiceException("已勾选的调派任务不允许重复执行该操作", HttpStatus.ERROR);
            }

            // 修改调派信息
            PostranAssign postranAssign = new PostranAssign();
            postranAssign.setFdId(fdId);
            if (oldJobSuspendReason == null) {
                postranAssign.setJobSuspendReason(reason);
            } else {
                byte[] jobSuspendReason = (byte[]) oldJobSuspendReason;
                String str = new String(jobSuspendReason, "GBK");
                postranAssign.setJobSuspendReason(str + ";" + reason);
            }

            postranAssign.setUpdateBy(username);
            if ("2".equals(taskType)) {
                postranAssign.setJobSuspendTime(nowDate);
            }
            postranAssignMapper.updatePostranAssign(postranAssign);

            // 修改录入信息
            PostranEntry postranEntry = new PostranEntry();
            postranEntry.setFdId(fdId);
            postranEntry.setAssignStatus(taskType);
            postranEntry.setUpdateBy(username);
            postranEntry.setUpdateTime(nowDate);
            postranEntryMapper.updatePostranEntry(postranEntry);
        }

        return mapList.size();
    }

    @Override
    public int claimTask(PostranAssignTask postranAssignTask) {
        // 获取调派信息和录入信息
        List<PostranAssign> postranAssignList = postranAssignTask.getPostranAssignList();

        // 校验勾选患者条数
        if (postranAssignList.size() > PostranConstants.TASK_NUM) {
            throw new ServiceException("勾选任务超过限制，请重新勾选", HttpStatus.ERROR);
        }

        // 获取当前时间
        Date nowDate = DateUtils.getNowDate();
        // 获取当前登录人id
        String username = SecurityUtils.getUsername();
        int sequense = sequenceMapper.getSequence("seq_dispatch_no", "admin");
        DecimalFormat decimalFormat = new DecimalFormat("0000");
        String dispatchNo = decimalFormat.format(sequense);

        if (StringUtils.isBlank(postranAssignTask.getFdId())) {
            // 校验调派状态
            List<String> fdIdList = postranAssignList.stream().map(PostranAssign::getFdId).collect(Collectors.toList());
            List<PostranEntry> entryList = postranEntryMapper.getPostranEntryList(fdIdList);
            List<String> assignStatusList = entryList.stream().map(PostranEntry::getAssignStatus).collect(Collectors.toList());
            if (!assignStatusList.contains(PostranConstants.TASK_NOT_ASSIGN)) {
                throw new ServiceException("勾选调派状态不是未调派的任务，请重新勾选", HttpStatus.ERROR);
            }

            // 认领任务新增调派信息
            for (PostranAssign postranAssign : postranAssignList) {
                PostranEntry postranEntry = new PostranEntry();
                postranEntry.setFdId(postranAssign.getFdId());
                postranEntry.setAssignStatus(PostranConstants.TASK_ASSIGN);
                postranEntry.setUpdateBy(username);
                postranEntry.setUpdateTime(nowDate);
                postranEntryMapper.updatePostranEntry(postranEntry);

                postranAssign.setDoctor1(postranAssignTask.getDoctor1());
                postranAssign.setDoctor2(postranAssignTask.getDoctor2());
                postranAssign.setNurse1(postranAssignTask.getNurse1());
                postranAssign.setNurse2(postranAssignTask.getNurse2());
                postranAssign.setDriver(postranAssignTask.getDriver());
                postranAssign.setDrawOutHospital(postranAssignTask.getDrawOutHospital());
                postranAssign.setCarNo(postranAssignTask.getCarNo());
                postranAssign.setLicensePlateNo(postranAssignTask.getLicensePlateNo());
                postranAssign.setRemark(postranAssignTask.getRemark());
                postranAssign.setDispatchNo(dispatchNo);
                postranAssign.setCreateBy(username);
                postranAssign.setCreateTime(nowDate);
                postranAssign.setUpdateBy(username);
                postranAssign.setUpdateTime(nowDate);
                postranAssignMapper.insertPostranAssign(postranAssign);
            }
        } else {
            for (PostranAssign postranAssign : postranAssignList) {
                if (postranAssignTask.getFdId().equals(postranAssign.getFdId())) {
                    postranAssign.setDoctor1(postranAssignTask.getDoctor1());
                    postranAssign.setDoctor2(postranAssignTask.getDoctor2());
                    postranAssign.setNurse1(postranAssignTask.getNurse1());
                    postranAssign.setNurse2(postranAssignTask.getNurse2());
                    postranAssign.setDriver(postranAssignTask.getDriver());
                    postranAssign.setDrawOutHospital(postranAssignTask.getDrawOutHospital());
                    postranAssign.setCarNo(postranAssignTask.getCarNo());
                    postranAssign.setLicensePlateNo(postranAssignTask.getLicensePlateNo());
                    postranAssign.setRemark(postranAssignTask.getRemark());
                    postranAssign.setUpdateBy(username);
                    postranAssign.setUpdateTime(nowDate);

                    PostranAssign oldPostranAssign = postranAssignMapper.selectPostranAssignByFdId(postranAssignTask.getFdId());
                    if (oldPostranAssign == null) {
                        postranAssign.setCreateBy(username);
                        postranAssign.setCreateTime(nowDate);
                        postranAssignMapper.insertPostranAssign(postranAssign);
                    } else {
                        // 更新调派信息
                        postranAssignMapper.updatePostranAssign(postranAssign);
                    }
                }
            }
        }

        return postranAssignList.size();
    }

    @Override
    public PostranAssignTask getAssignTask(String fdId) {
        PostranAssignTask postranAssignTask = new PostranAssignTask();
        List<PostranEntry> postranEntryList = new ArrayList<>();
        List<PostranAssign> postranAssignList = new ArrayList<>();

        // 封装调派任务详情信息
        PostranEntry postranEntry = postranEntryMapper.selectPostranEntryByFdId(fdId);
        PostranAssign postranAssign = postranAssignMapper.selectPostranAssignByFdId(fdId);
        postranAssignTask.setFdId(postranAssign.getFdId());
        postranAssignTask.setDoctor1(postranAssign.getDoctor1());
        postranAssignTask.setDoctor2(postranAssign.getDoctor2());
        postranAssignTask.setNurse1(postranAssign.getNurse1());
        postranAssignTask.setNurse2(postranAssign.getNurse2());
        postranAssignTask.setDriver(postranAssign.getDriver());
        postranAssignTask.setDrawOutHospital(postranAssign.getDrawOutHospital());
        postranAssignTask.setCarNo(postranAssign.getCarNo());
        postranAssignTask.setLicensePlateNo(postranAssign.getLicensePlateNo());
        postranAssignTask.setRemark(postranAssign.getRemark());
        postranAssignList.add(postranAssign);
        postranEntryList.add(postranEntry);
        postranAssignTask.setPostranAssignList(postranAssignList);
        postranAssignTask.setPostranEntryList(postranEntryList);

        return postranAssignTask;
    }
}
