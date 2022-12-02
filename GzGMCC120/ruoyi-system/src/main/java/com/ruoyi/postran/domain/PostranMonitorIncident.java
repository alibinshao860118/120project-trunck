package com.ruoyi.postran.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 120核阳转运监控事件对象 postran_monitor_incident
 * 
 * @author ruoyi
 * @date 2022-11-24
 */
public class PostranMonitorIncident extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 事件 */
    @Excel(name = "事件")
    private String incident;

    /** 事件归类（字典类型4） */
    @Excel(name = "事件归类", readConverterExp = "字=典类型4")
    private String incidentType;

    /** 监控编号，关联监控表的主键 */
    @Excel(name = "监控编号，关联监控表的主键")
    private String monitorId;

    public void setIncident(String incident) 
    {
        this.incident = incident;
    }

    public String getIncident() 
    {
        return incident;
    }
    public void setIncidentType(String incidentType) 
    {
        this.incidentType = incidentType;
    }

    public String getIncidentType() 
    {
        return incidentType;
    }
    public void setMonitorId(String monitorId) 
    {
        this.monitorId = monitorId;
    }

    public String getMonitorId() 
    {
        return monitorId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("incident", getIncident())
            .append("incidentType", getIncidentType())
            .append("monitorId", getMonitorId())
            .toString();
    }
}
