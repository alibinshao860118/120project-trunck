package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 到达医院导入对象 postran_arrive_import
 *
 * @author ruoyi
 * @date 2022-11-25
 */
public class PostranArriveImport extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 导入ID */
    private Long importId;

    /** 患者ID */
    @Excel(name = "患者ID")
    private Long sickerId;

    /** 编号 */
    @Excel(name = "编号")
    private Long sickerNum;

    /** 患者手机号 */
    @Excel(name = "手机号")
    private String sickerMobile;

    /** 患者身份证 */
    @Excel(name = "身份证")
    private String identityCard;

    /** 呼车时间 */
    @Excel(name = "呼车时间")
    private String callTime;

    /** 数据来源 */
    @Excel(name = "备注")
    private String dataRemark;

    public void setImportId(Long importId)
    {
        this.importId = importId;
    }

    public Long getImportId()
    {
        return importId;
    }
    public void setSickerId(Long sickerId)
    {
        this.sickerId = sickerId;
    }

    public Long getSickerId()
    {
        return sickerId;
    }
    public void setSickerNum(Long sickerNum)
    {
        this.sickerNum = sickerNum;
    }

    public Long getSickerNum()
    {
        return sickerNum;
    }
    public void setSickerMobile(String sickerMobile)
    {
        this.sickerMobile = sickerMobile;
    }

    public String getSickerMobile()
    {
        return sickerMobile;
    }
    public void setIdentityCard(String identityCard)
    {
        this.identityCard = identityCard;
    }

    public String getIdentityCard()
    {
        return identityCard;
    }
    public void setCallTime(String callTime)
    {
        this.callTime = callTime;
    }

    public String getCallTime()
    {
        return callTime;
    }
    public void setDataRemark(String dataRemark)
    {
        this.dataRemark = dataRemark;
    }

    public String getDataRemark()
    {
        return dataRemark;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("importId", getImportId())
            .append("sickerId", getSickerId())
            .append("sickerNum", getSickerNum())
            .append("sickerMobile", getSickerMobile())
            .append("identityCard", getIdentityCard())
            .append("callTime", getCallTime())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("dataRemark", getDataRemark())
            .toString();
    }
}
