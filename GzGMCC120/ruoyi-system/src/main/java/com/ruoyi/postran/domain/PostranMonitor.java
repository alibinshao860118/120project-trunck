package com.ruoyi.postran.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 120核阳转运监控对象 postran_monitor
 * 
 * @author ruoyi
 * @date 2022-11-24
 */
public class PostranMonitor extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 编号 */
    private String fdId;

    /** 出车时间 */
    @Excel(name = "出车时间")
    private String drawOutTime;

    /** 救护车类型（0负压救护车 1普通转运车） */
    @Excel(name = "救护车类型", readConverterExp = "0=负压救护车,1=普通转运车")
    private String ambulanceType;

    /** 通知时间 */
    @Excel(name = "通知时间")
    private String noticeTime;

    /** 救护车返回时间 */
    @Excel(name = "救护车返回时间")
    private String ambulanceBackTime;

    /** 任务完成时间 */
    @Excel(name = "任务完成时间")
    private String completeJobTime;

    /** 达到机场急救中心时间 */
    @Excel(name = "达到机场急救中心时间")
    private String arriveAirportEcTime;

    /** 整改期限（1:医务人员立即整改 2:督导员立即整改 3:1周内 4:1个月内 5：其他） */
    @Excel(name = "整改期限", readConverterExp = "1=:医务人员立即整改,2=:督导员立即整改,3=:1周内,4=:1个月内,5=：其他")
    private String rectificationLimit;

    /** 整改需求 */
    @Excel(name = "整改需求")
    private String rectificationDemand;

    /** 督导员 */
    @Excel(name = "督导员")
    private String supervisor;

    /** 呼车备注 */
    @Excel(name = "呼车备注")
    private String carCallRemark;

    /** 调派备注 */
    @Excel(name = "调派备注")
    private String assignRemark;

    /** 完成备注 */
    @Excel(name = "完成备注")
    private String finishRemark;

    public void setFdId(String fdId) 
    {
        this.fdId = fdId;
    }

    public String getFdId() 
    {
        return fdId;
    }
    public void setDrawOutTime(String drawOutTime)
    {
        this.drawOutTime = drawOutTime;
    }

    public String getDrawOutTime()
    {
        return drawOutTime;
    }
    public void setAmbulanceType(String ambulanceType) 
    {
        this.ambulanceType = ambulanceType;
    }

    public String getAmbulanceType() 
    {
        return ambulanceType;
    }
    public void setNoticeTime(String noticeTime)
    {
        this.noticeTime = noticeTime;
    }

    public String getNoticeTime()
    {
        return noticeTime;
    }
    public void setAmbulanceBackTime(String ambulanceBackTime)
    {
        this.ambulanceBackTime = ambulanceBackTime;
    }

    public String getAmbulanceBackTime()
    {
        return ambulanceBackTime;
    }
    public void setCompleteJobTime(String completeJobTime)
    {
        this.completeJobTime = completeJobTime;
    }

    public String getCompleteJobTime()
    {
        return completeJobTime;
    }
    public void setArriveAirportEcTime(String arriveAirportEcTime)
    {
        this.arriveAirportEcTime = arriveAirportEcTime;
    }

    public String getArriveAirportEcTime()
    {
        return arriveAirportEcTime;
    }
    public void setRectificationLimit(String rectificationLimit) 
    {
        this.rectificationLimit = rectificationLimit;
    }

    public String getRectificationLimit() 
    {
        return rectificationLimit;
    }
    public void setRectificationDemand(String rectificationDemand) 
    {
        this.rectificationDemand = rectificationDemand;
    }

    public String getRectificationDemand() 
    {
        return rectificationDemand;
    }
    public void setSupervisor(String supervisor) 
    {
        this.supervisor = supervisor;
    }

    public String getSupervisor() 
    {
        return supervisor;
    }
    public void setCarCallRemark(String carCallRemark) 
    {
        this.carCallRemark = carCallRemark;
    }

    public String getCarCallRemark() 
    {
        return carCallRemark;
    }
    public void setAssignRemark(String assignRemark) 
    {
        this.assignRemark = assignRemark;
    }

    public String getAssignRemark() 
    {
        return assignRemark;
    }
    public void setFinishRemark(String finishRemark) 
    {
        this.finishRemark = finishRemark;
    }

    public String getFinishRemark() 
    {
        return finishRemark;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("fdId", getFdId())
            .append("drawOutTime", getDrawOutTime())
            .append("ambulanceType", getAmbulanceType())
            .append("noticeTime", getNoticeTime())
            .append("ambulanceBackTime", getAmbulanceBackTime())
            .append("completeJobTime", getCompleteJobTime())
            .append("arriveAirportEcTime", getArriveAirportEcTime())
            .append("rectificationLimit", getRectificationLimit())
            .append("rectificationDemand", getRectificationDemand())
            .append("supervisor", getSupervisor())
            .append("carCallRemark", getCarCallRemark())
            .append("assignRemark", getAssignRemark())
            .append("finishRemark", getFinishRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
