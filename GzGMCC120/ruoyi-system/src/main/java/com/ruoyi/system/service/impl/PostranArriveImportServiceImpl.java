package com.ruoyi.system.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.bean.BeanValidators;
import com.ruoyi.postran.domain.PostranEntry;
import com.ruoyi.postran.mapper.PostranEntryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.PostranArriveImportMapper;
import com.ruoyi.system.domain.PostranArriveImport;
import com.ruoyi.system.service.IPostranArriveImportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 到达医院导入Service业务层处理
 *
 * @author ruoyi
 * @date 2022-11-25
 */
@Service
public class PostranArriveImportServiceImpl implements IPostranArriveImportService
{
    private static final Logger log = LoggerFactory.getLogger(SysUserServiceImpl.class);

    @Autowired
    private PostranArriveImportMapper postranArriveImportMapper;

    @Autowired
    private PostranEntryMapper postranEntryMapper;


    /**
     * 查询到达医院导入
     *
     * @param importId 到达医院导入主键
     * @return 到达医院导入
     */
    @Override
    public PostranArriveImport selectPostranArriveImportByImportId(Long importId)
    {
        return postranArriveImportMapper.selectPostranArriveImportByImportId(importId);
    }

    /**
     * 查询到达医院导入列表
     *
     * @param postranArriveImport 到达医院导入
     * @return 到达医院导入
     */
    @Override
    public List<PostranArriveImport> selectPostranArriveImportList(PostranArriveImport postranArriveImport)
    {
        return postranArriveImportMapper.selectPostranArriveImportList(postranArriveImport);
    }

    /**
     * 新增到达医院导入
     *
     * @param postranArriveImport 到达医院导入
     * @return 结果
     */
    @Override
    public int insertPostranArriveImport(PostranArriveImport postranArriveImport)
    {
        postranArriveImport.setCreateTime(DateUtils.getNowDate());
        return postranArriveImportMapper.insertPostranArriveImport(postranArriveImport);
    }

    /**
     * 修改到达医院导入
     *
     * @param postranArriveImport 到达医院导入
     * @return 结果
     */
    @Override
    public int updatePostranArriveImport(PostranArriveImport postranArriveImport)
    {
        postranArriveImport.setUpdateTime(DateUtils.getNowDate());
        return postranArriveImportMapper.updatePostranArriveImport(postranArriveImport);
    }

    /**
     * 批量删除到达医院导入
     *
     * @param importIds 需要删除的到达医院导入主键
     * @return 结果
     */
    @Override
    public int deletePostranArriveImportByImportIds(Long[] importIds)
    {
        return postranArriveImportMapper.deletePostranArriveImportByImportIds(importIds);
    }

    /**
     * 删除到达医院导入信息
     *
     * @param importId 到达医院导入主键
     * @return 结果
     */
    @Override
    public int deletePostranArriveImportByImportId(Long importId)
    {
        return postranArriveImportMapper.deletePostranArriveImportByImportId(importId);
    }

    /**
     * 导入用户数据
     *
     * @param importList 用户数据列表
     * @param type
     * @param operName 操作用户
     * @return 结果
     */
    @Override
    public String importData(List<PostranArriveImport> importList, String type, String operName)
    {
        if (StringUtils.isNull(importList) || importList.size() == 0)
        {
            throw new ServiceException("导入数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        List<PostranArriveImport> arriveImports = new ArrayList<>();
        for (PostranArriveImport postranArriveImport : importList)
        {
            try
            {
                if (postranArriveImport.getSickerNum() != null)
                {
                    postranArriveImport.setCreateBy(operName);
                    postranArriveImport.setCreateTime(DateUtils.getNowDate());
//                    arriveImports.add(postranArriveImport);
//                    if (arriveImports.size() >= 1000) {
//                        postranArriveImportMapper.batchInsertPostranArriveImport(arriveImports);
//                        arriveImports.clear();
//                    }
//                    智能外呼导入还要根据呼车时间、病人证件号码、病人联系电话去匹配录入表里面的数据，
//                    如果匹配上要把录入表里面的是否已到院更新为已到院
//                    `car_call_date` varchar(32) DEFAULT NULL COMMENT '呼车日期（格式为yyyy-MM-dd）',
//                        `car_call_time` varchar(32) DEFAULT NULL COMMENT '呼车时间（格式为hh24:mi）',
                    String callTime = postranArriveImport.getCallTime();
                    String identityCard = postranArriveImport.getIdentityCard();
                    String mobile = postranArriveImport.getSickerMobile();
                    PostranEntry postranEntry = new PostranEntry();
                    postranEntry.setCarCallTime(callTime);
                    postranEntry.setPatientIdNumber(identityCard);
                    postranEntry.setPatientContractNumber(mobile);
                    List<PostranEntry> postranEntryList = postranEntryMapper.selectPostranEntryList(postranEntry);

                    if (postranEntryList != null && postranEntryList.size() >0) {
                        // 更新状态已到院
                        PostranEntry obj = postranEntryList.get(0);
                        obj.setIsArriveHospital("1");
                        postranEntryMapper.updatePostranEntry(obj);

                    }

                    this.insertPostranArriveImport(postranArriveImport);

                    if (type.equals("1"))
                    successNum++;
              //      successMsg.append("<br/>" + successNum + "、编号 " + postranArriveImport.getSickerNum() + " 导入成功");

                } else {
                    failureNum++;
                    failureMsg.append("<br/>" + failureNum + "、编号 " + postranArriveImport.getSickerNum() + " 为空");
                }
            }
            catch (Exception e)
            {
                failureNum++;
                String msg = "<br/>" + failureNum + "、编号 " + postranArriveImport.getSickerNum()  + " 导入失败：";
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
            successMsg.insert(0, "数据已全部导入成功！共 " + successNum + " 条");
        }
        return successMsg.toString();
    }

//    public static void main(String[] args) {
//        StringBuilder str = new StringBuilder();
//        str.append("2222");
//        str.insert(0, "55555");
//        str.insert(1, "666");
//
//        System.out.println(str);
//    }
}
