package com.ruoyi.postran.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

/**
 * 120核阳转运调派对象 postran_assign
 * 
 * @author ruoyi
 * @date 2022-11-24
 */
public class PostranAssign extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 编号 */
    private String fdId;

    /** 医生1（字典类型3） */
    @Excel(name = "医生1", readConverterExp = "字=典类型3")
    private String doctor1;

    /** 医生2（字典类型3） */
    @Excel(name = "医生2", readConverterExp = "字=典类型3")
    private String doctor2;

    /** 护士1（字典类型3） */
    @Excel(name = "护士1", readConverterExp = "字=典类型3")
    private String nurse1;

    /** 护士2（字典类型3） */
    @Excel(name = "护士2", readConverterExp = "字=典类型3")
    private String nurse2;

    /** 司机（字典类型3） */
    @Excel(name = "司机", readConverterExp = "字=典类型3")
    private String driver;

    /** 出车医院（字典类型3） */
    @Excel(name = "出车医院", readConverterExp = "字=典类型3")
    private String drawOutHospital;

    /** 车号 */
    @Excel(name = "车号")
    private String carNo;

    /** 车牌号 */
    @Excel(name = "车牌号")
    private String licensePlateNo;

    /** 新接车地址 */
    @Excel(name = "新接车地址")
    private String newPickUpAddress;

    /** 特殊病情 */
    @Excel(name = "特殊病情")
    private String specialCondition;

    /** 新接收医院地址 */
    @Excel(name = "新接收医院地址")
    private String newReceiveHospitalAddress;

    /** 接收医院（字典类型5） */
    @Excel(name = "接收医院", readConverterExp = "字=典类型5")
    private String receiveHospital;

    /** 派车单号 */
    @Excel(name = "派车单号")
    private String dispatchNo;

    /** 任务终止原因 */
    @Excel(name = "任务终止原因")
    private String jobSuspendReason;

    /** 任务中止时间 */
    @Excel(name = "任务中止时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date jobSuspendTime;

    public void setFdId(String fdId) 
    {
        this.fdId = fdId;
    }

    public String getFdId() 
    {
        return fdId;
    }
    public void setDoctor1(String doctor1) 
    {
        this.doctor1 = doctor1;
    }

    public String getDoctor1() 
    {
        return doctor1;
    }
    public void setDoctor2(String doctor2) 
    {
        this.doctor2 = doctor2;
    }

    public String getDoctor2() 
    {
        return doctor2;
    }
    public void setNurse1(String nurse1) 
    {
        this.nurse1 = nurse1;
    }

    public String getNurse1() 
    {
        return nurse1;
    }
    public void setNurse2(String nurse2) 
    {
        this.nurse2 = nurse2;
    }

    public String getNurse2() 
    {
        return nurse2;
    }
    public void setDriver(String driver) 
    {
        this.driver = driver;
    }

    public String getDriver() 
    {
        return driver;
    }
    public void setDrawOutHospital(String drawOutHospital) 
    {
        this.drawOutHospital = drawOutHospital;
    }

    public String getDrawOutHospital() 
    {
        return drawOutHospital;
    }
    public void setCarNo(String carNo) 
    {
        this.carNo = carNo;
    }

    public String getCarNo() 
    {
        return carNo;
    }
    public void setLicensePlateNo(String licensePlateNo) 
    {
        this.licensePlateNo = licensePlateNo;
    }

    public String getLicensePlateNo() 
    {
        return licensePlateNo;
    }
    public void setNewPickUpAddress(String newPickUpAddress) 
    {
        this.newPickUpAddress = newPickUpAddress;
    }

    public String getNewPickUpAddress() 
    {
        return newPickUpAddress;
    }
    public void setSpecialCondition(String specialCondition) 
    {
        this.specialCondition = specialCondition;
    }

    public String getSpecialCondition() 
    {
        return specialCondition;
    }
    public void setNewReceiveHospitalAddress(String newReceiveHospitalAddress) 
    {
        this.newReceiveHospitalAddress = newReceiveHospitalAddress;
    }

    public String getNewReceiveHospitalAddress() 
    {
        return newReceiveHospitalAddress;
    }
    public void setReceiveHospital(String receiveHospital) 
    {
        this.receiveHospital = receiveHospital;
    }

    public String getReceiveHospital() 
    {
        return receiveHospital;
    }
    public void setDispatchNo(String dispatchNo) 
    {
        this.dispatchNo = dispatchNo;
    }

    public String getDispatchNo()
    {
        return dispatchNo;
    }

    public String getJobSuspendReason() {
        return jobSuspendReason;
    }

    public void setJobSuspendReason(String jobSuspendReason) {
        this.jobSuspendReason = jobSuspendReason;
    }

    public Date getJobSuspendTime() {
        return jobSuspendTime;
    }

    public void setJobSuspendTime(Date jobSuspendTime) {
        this.jobSuspendTime = jobSuspendTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("fdId", getFdId())
            .append("doctor1", getDoctor1())
            .append("doctor2", getDoctor2())
            .append("nurse1", getNurse1())
            .append("nurse2", getNurse2())
            .append("driver", getDriver())
            .append("drawOutHospital", getDrawOutHospital())
            .append("carNo", getCarNo())
            .append("licensePlateNo", getLicensePlateNo())
            .append("remark", getRemark())
            .append("newPickUpAddress", getNewPickUpAddress())
            .append("specialCondition", getSpecialCondition())
            .append("newReceiveHospitalAddress", getNewReceiveHospitalAddress())
            .append("receiveHospital", getReceiveHospital())
            .append("dispatchNo", getDispatchNo())
            .append("jobSuspendReason", getJobSuspendReason())
            .append("jobSuspendTime", getJobSuspendTime())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
