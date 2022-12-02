package com.ruoyi.postran.service.impl;

import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.uuid.IdUtils;
import com.ruoyi.postran.domain.PostranEntry;
import com.ruoyi.postran.domain.SysDict;
import com.ruoyi.postran.mapper.PostranEntryMapper;
import com.ruoyi.postran.mapper.SysDictMapper;
import com.ruoyi.postran.service.IPostranEntryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Validator;
import java.util.Date;
import java.util.List;

/**
 * 120核阳转运录入Service业务层处理
 * 
 * @author ruoyi
 * @date 2022-11-24
 */
@Service
public class PostranEntryServiceImpl implements IPostranEntryService
{
    private static final Logger log = LoggerFactory.getLogger(PostranEntryServiceImpl.class);

    @Autowired
    private PostranEntryMapper postranEntryMapper;

    @Autowired
    protected Validator validator;

    @Autowired
    private SysDictMapper sysDictMapper;

    /**
     * 查询120核阳转运录入
     * 
     * @param fdId 120核阳转运录入主键
     * @return 120核阳转运录入
     */
    @Override
    public PostranEntry selectPostranEntryByFdId(String fdId)
    {
        return postranEntryMapper.getPostranEntryByFdId(fdId);
    }

    /**
     * 查询120核阳转运录入列表
     * 
     * @param postranEntry 120核阳转运录入
     * @return 120核阳转运录入
     */
    @Override
    public List<PostranEntry> selectPostranEntryList(PostranEntry postranEntry)
    {
        return postranEntryMapper.selectPostranEntryList(postranEntry);
    }

    /**
     * 新增120核阳转运录入
     * 
     * @param postranEntry 120核阳转运录入
     * @return 结果
     */
    @Override
    public int insertPostranEntry(PostranEntry postranEntry)
    {
        // 生成id
        String fdId = IdUtils.fastSimpleUUID();
        // 获取当前时间
        Date nowDate = DateUtils.getNowDate();
        // 获取当前登录人id
        String username = SecurityUtils.getUsername();

        postranEntry.setFdId(fdId);
        postranEntry.setCreateBy(username);
        postranEntry.setUpdateBy(username);
        postranEntry.setCreateTime(nowDate);
        postranEntry.setUpdateTime(nowDate);
        return postranEntryMapper.insertPostranEntry(postranEntry);
    }

    /**
     * 修改120核阳转运录入
     *
     * @param postranEntry 120核阳转运录入
     * @return 结果
     */
    @Override
    public int updatePostranEntry(PostranEntry postranEntry)
    {
        // 获取当前时间
        Date nowDate = DateUtils.getNowDate();
        // 获取当前登录人id
        String username = SecurityUtils.getUsername();

        postranEntry.setUpdateBy(username);
        postranEntry.setUpdateTime(nowDate);
        return postranEntryMapper.updatePostranEntry(postranEntry);
    }

    /**
     * 批量删除120核阳转运录入
     * 
     * @param fdIds 需要删除的120核阳转运录入主键
     * @return 结果
     */
    @Override
    public int deletePostranEntryByFdIds(String[] fdIds)
    {
        return postranEntryMapper.deletePostranEntryByFdIds(fdIds);
    }

    /**
     * 删除120核阳转运录入信息
     * 
     * @param fdId 120核阳转运录入主键
     * @return 结果
     */
    @Override
    public int deletePostranEntryByFdId(String fdId)
    {
        return postranEntryMapper.deletePostranEntryByFdId(fdId);
    }

    /**
     * 导入120核阳转运录入数据
     *
     * @param postranEntryList 转运录入数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @return 结果
     */
    @Override
    public String importPostranEntry(List<PostranEntry> postranEntryList, Boolean isUpdateSupport)
    {
        if (StringUtils.isNull(postranEntryList) || postranEntryList.size() == 0)
        {
            throw new ServiceException("导入120核阳转运录入数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        for (PostranEntry postranEntry : postranEntryList)
        {
            try
            {
                // 验证是否存在这个用户
                PostranEntry p = postranEntryMapper.getRepeatPostranEntry(postranEntry.getCarCallTime(), postranEntry.getPatientIdNumber(), postranEntry.getPatientContractNumber());
                if (StringUtils.isNull(p))
                {
//                    BeanValidators.validateWithException(validator, postranEntry);
                    validImport(postranEntry);
                    postranEntry.setIsArriveHospital("0");
                    postranEntry.setDataSource("0");
                    postranEntry.setAssignStatus("0");

                    this.insertPostranEntry(postranEntry);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、呼车时间" + postranEntry.getCarCallTime() +
                            "，病人证件号码" + postranEntry.getPatientIdNumber() + "，病人联系电话" +
                            postranEntry.getPatientContractNumber() + " 导入成功");
                }
                else if (isUpdateSupport)
                {
//                    BeanValidators.validateWithException(validator, postranEntry);
                    validImport(postranEntry);

                    postranEntry.setFdId(p.getFdId());
                    this.updatePostranEntry(postranEntry);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、呼车时间" + postranEntry.getCarCallTime() +
                            "，病人证件号码" + postranEntry.getPatientIdNumber() + "，病人联系电话" +
                            postranEntry.getPatientContractNumber() + " 更新成功");
                }
                else
                {
                    failureNum++;
                    failureMsg.append("<br/>" + failureNum + "、呼车时间" + postranEntry.getCarCallTime() +
                            "，病人证件号码" + postranEntry.getPatientIdNumber() + "，病人联系电话" +
                            postranEntry.getPatientContractNumber() + " 已存在");
                }
            }
            catch (Exception e)
            {
                failureNum++;
                String msg = "<br/>" + failureNum + "、呼车时间" + postranEntry.getCarCallTime() +
                        "，病人证件号码" + postranEntry.getPatientIdNumber() + "，病人联系电话" +
                        postranEntry.getPatientContractNumber() + " 导入失败：";
                failureMsg.append(msg + e.getMessage());
                log.error(msg, e);
            }
        }
        if (failureNum > 0)
        {
            failureMsg.insert(0, "很抱歉，导入失败！共 " + failureNum + " 条数据格式不正确，错误如下：");
            throw new ServiceException(failureMsg.toString());
        }
        else
        {
            successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条，数据如下：");
        }
        return "";
    }

    /**
     * 校验导入的数据
     * @param postranEntry 导入的数据信息
     */
    public void validImport(PostranEntry postranEntry){
        if(StringUtils.isNotBlank(postranEntry.getCarCallUnit())){
            SysDict carCallUnitDict = sysDictMapper.selectByTypeAndValue("1", postranEntry.getCarCallUnit());
            if(carCallUnitDict == null){
                throw new ServiceException("呼车单位不符合字典数据要求");
            }
        }
    }
}
