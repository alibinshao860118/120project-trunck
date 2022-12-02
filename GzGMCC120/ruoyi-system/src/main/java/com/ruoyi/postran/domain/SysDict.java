package com.ruoyi.postran.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 字典对象 sys_dict
 * 
 * @author huangmh
 * @date 2022-11-25
 */
public class SysDict extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 创建者 */
    @Excel(name = "创建者")
    private String creator;

    /** 创建者id */
    @Excel(name = "创建者id")
    private String creatorId;

    /** 修改者 */
    @Excel(name = "修改者")
    private String modifier;

    /** 修改者id */
    @Excel(name = "修改者id")
    private String modifierId;

    /** 字典类型，1 呼车单位，2 高危因素，3 派出医院，4 暴露风险，5 医院，6 任务中止原因 */
    @Excel(name = "字典类型，1 呼车单位，2 高危因素，3 派出医院，4 暴露风险，5 医院，6 任务中止原因")
    private String dictType;

    /** 逻辑删除，0 未删除，1 已删除 */
    @Excel(name = "逻辑删除，0 未删除，1 已删除")
    private String tagDelete;

    /** 字典值 */
    @Excel(name = "字典值")
    private String dictValue;

    /** 所属单位 */
    @Excel(name = "所属单位")
    private String subordinateUnit;

    /** 所属辖区 */
    @Excel(name = "所属辖区")
    private String jurisdictionArea;

    /** 排序 */
    @Excel(name = "排序")
    private Integer sort;

    /** 乐观锁版本号 */
    @Excel(name = "乐观锁版本号")
    private Integer version;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setCreator(String creator) 
    {
        this.creator = creator;
    }

    public String getCreator() 
    {
        return creator;
    }
    public void setCreatorId(String creatorId) 
    {
        this.creatorId = creatorId;
    }

    public String getCreatorId() 
    {
        return creatorId;
    }
    public void setModifier(String modifier) 
    {
        this.modifier = modifier;
    }

    public String getModifier() 
    {
        return modifier;
    }
    public void setModifierId(String modifierId) 
    {
        this.modifierId = modifierId;
    }

    public String getModifierId() 
    {
        return modifierId;
    }
    public void setDictType(String dictType) 
    {
        this.dictType = dictType;
    }

    public String getDictType() 
    {
        return dictType;
    }
    public void setTagDelete(String tagDelete) 
    {
        this.tagDelete = tagDelete;
    }

    public String getTagDelete() 
    {
        return tagDelete;
    }
    public void setDictValue(String dictValue) 
    {
        this.dictValue = dictValue;
    }

    public String getDictValue() 
    {
        return dictValue;
    }
    public void setSubordinateUnit(String subordinateUnit) 
    {
        this.subordinateUnit = subordinateUnit;
    }

    public String getSubordinateUnit() 
    {
        return subordinateUnit;
    }
    public void setJurisdictionArea(String jurisdictionArea) 
    {
        this.jurisdictionArea = jurisdictionArea;
    }

    public String getJurisdictionArea() 
    {
        return jurisdictionArea;
    }
    public void setSort(Integer sort) 
    {
        this.sort = sort;
    }

    public Integer getSort() 
    {
        return sort;
    }
    public void setVersion(Integer version) 
    {
        this.version = version;
    }

    public Integer getVersion() 
    {
        return version;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .append("creator", getCreator())
            .append("creatorId", getCreatorId())
            .append("modifier", getModifier())
            .append("modifierId", getModifierId())
            .append("dictType", getDictType())
            .append("tagDelete", getTagDelete())
            .append("dictValue", getDictValue())
            .append("subordinateUnit", getSubordinateUnit())
            .append("jurisdictionArea", getJurisdictionArea())
            .append("sort", getSort())
            .append("version", getVersion())
            .toString();
    }
}
