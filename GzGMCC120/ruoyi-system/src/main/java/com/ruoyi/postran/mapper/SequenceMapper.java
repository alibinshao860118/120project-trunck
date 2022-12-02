package com.ruoyi.postran.mapper;

import org.apache.ibatis.annotations.Param;

/**
 * 序列号Mapper接口
 * 
 * @author ruoyi
 * @date 2022-11-24
 */
public interface SequenceMapper
{
    /**
     * 根据序列号类型+更新账号查询序列号
     * @param seqType 序列号类型
     * @param updateBy 更新账号
     * @return
     */
    int getSequence(@Param(value = "seqType") String seqType, @Param(value = "updateBy") String updateBy);
}
