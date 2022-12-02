package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 合同文件对象 office
 * 
 * @author ruoyi
 * @date 2022-12-02
 */
public class Office extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 类别 */
    @Excel(name = "类别")
    private String lb;

    /** lbmc */
    @Excel(name = "lbmc")
    private String lbmc;

    /** 说明 */
    @Excel(name = "说明")
    private String content;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setLb(String lb) 
    {
        this.lb = lb;
    }

    public String getLb() 
    {
        return lb;
    }
    public void setLbmc(String lbmc) 
    {
        this.lbmc = lbmc;
    }

    public String getLbmc() 
    {
        return lbmc;
    }
    public void setContent(String content) 
    {
        this.content = content;
    }

    public String getContent() 
    {
        return content;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("lb", getLb())
            .append("lbmc", getLbmc())
            .append("createTime", getCreateTime())
            .append("content", getContent())
            .toString();
    }
}
