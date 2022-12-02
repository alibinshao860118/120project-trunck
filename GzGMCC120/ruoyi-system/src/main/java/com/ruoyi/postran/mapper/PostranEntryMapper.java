package com.ruoyi.postran.mapper;

import com.ruoyi.postran.domain.PostranEntry;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 120核阳转运录入Mapper接口
 * 
 * @author ruoyi
 * @date 2022-11-24
 */
public interface PostranEntryMapper 
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
     * 删除120核阳转运录入
     * 
     * @param fdId 120核阳转运录入主键
     * @return 结果
     */
    public int deletePostranEntryByFdId(String fdId);

    /**
     * 批量删除120核阳转运录入
     * 
     * @param fdIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deletePostranEntryByFdIds(String[] fdIds);

    /**
     * 查询120核阳转运录入
     *
     * @param fdId 120核阳转运录入主键
     * @return 120核阳转运录入
     */
    PostranEntry getPostranEntryByFdId(String fdId);

    /**
     * 查询重复数据
     *
     * @param fdId 120核阳转运录入主键
     * @return 120核阳转运录入
     */

    /**
     * 查询重复数据
     * @param carCallTime 呼车时间
     * @param patientIdNumber 病人证件号码
     * @param patientContractNumber 病人联系电话
     * @return 120核阳转运录入
     */
    PostranEntry getRepeatPostranEntry(@Param(value = "carCallTime") String carCallTime, @Param(value = "patientIdNumber") String patientIdNumber,
                                       @Param(value = "patientContractNumber") String patientContractNumber);

    /**
     * 根据id批量查询
     * @param fdIds
     * @return
     */
    List<PostranEntry> getPostranEntryList(List<String> fdIds);
}
