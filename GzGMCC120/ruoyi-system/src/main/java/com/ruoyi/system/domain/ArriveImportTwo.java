package com.ruoyi.system.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 到达医院导入对象 postran_arrive_import
 *
 * @author ruoyi
 * @date 2022-11-25
 */
public class ArriveImportTwo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 编号 */
    @Excel(name = "编号")
    private Long sickerNum;

//    /** 呼车时间 */
//    @Excel(name = "呼车时间")
//    private String callTime;

    /** 手机号 */
    @Excel(name = "手机号")
    private String sickerMobile;

    /** 患者身份证 */
    @Excel(name = "身份证")
    private String identityCard;

    /** 数据来源 */
    @Excel(name = "备注")
    private String dataRemark;

}
