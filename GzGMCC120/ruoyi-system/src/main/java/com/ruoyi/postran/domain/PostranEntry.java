package com.ruoyi.postran.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * 120核阳转运录入对象 postran_entry
 * 
 * @author ruoyi
 * @date 2022-11-24
 */
public class PostranEntry extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 编号 */
    private String fdId;

    /** 受理编号 */
    @Excel(name = "受理编号")
    @Pattern(regexp = "^\\d{1,10}$", message = "受理编号不能为空且只能填10位数字以内")
    private String acceptNo;

    /** 呼车单位（字典类型1） */
    @Excel(name = "呼车单位")
    private String carCallUnit;

    /** 呼车日期（格式为yyyy-MM-dd） */
    @Excel(name = "呼车日期", dateFormat = "yyyy-MM-dd")
    @Pattern(regexp = "^\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|(1|2)[0-9]|30|31)$", message = "呼车日期格式不正确")
    private String carCallDate;

    /** 呼车时间（格式为hh24:mi） */
    @Excel(name = "呼车时间", dateFormat = "HH:mm")
    @Pattern(regexp = "([0-1]?[0-9]|2[0-3]):([0-5][0-9])", message = "呼车时间格式不正确")
    private String carCallTime;

    /** 呼车联系人 */
    @Excel(name = "呼车联系人")
    @Length(min = 1, max = 30, message = "呼车联系人不能为空且长度不能超过30")
    private String carCallContractPerson;

    /** 呼车联系电话 */
    @Excel(name = "呼车联系电话")
    @NotBlank(message = "呼车联系电话不能为空")
    @Pattern(regexp = "^\\d{11}$", message = "请输入11位的手机号码")
    private String carCallContractNumber;

    /** 病人姓名 */
    @Excel(name = "病人姓名")
    @Length(min = 1, max = 30, message = "病人姓名不能为空且长度不能超过30")
    private String patientName;

    /** 病人证件号码 */
    @Excel(name = "病人证件号码")
    @Pattern(regexp = "^[1-9]\\d{5}(18|19|([23]\\d))\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$", message = "病人证件号码错误，请重新输入")
    private String patientIdNumber;

    /** 病人联系电话 */
    @Excel(name = "病人联系电话")
    @NotBlank(message = "病人联系电话不能为空")
    @Pattern(regexp = "^\\d{11}$", message = "请输入11位的手机号码")
    private String patientContractNumber;

    /** 性别（0男 1女 2未知） */
    @Excel(name = "性别", readConverterExp = "0=男,1=女")
    private String sex;

    /** 年龄 */
    @Excel(name = "年龄")
    private String age;

    /** 病例来源（0本地 1入境） */
    @Excel(name = "病例来源", readConverterExp = "0=本地,1=入境")
    private String caseSource;

    /** 人员身份（0本人 1陪同人员） */
    @Excel(name = "人员身份", readConverterExp = "0=本人,1=陪同人员")
    private String personIdentity;

    /** 核酸检测结果（0阴性 1阳性） */
    @Excel(name = "核酸检测结果", readConverterExp = "0=阴性,1=阳性")
    private String nucleateResult;

    /** 核酸检测是否阳性（0否 1是） */
    @Excel(name = "核酸检测是否阳性", readConverterExp = "0=否,1=是")
    private String nucleateIsPositive;

    /** 防疫通高危因素 */
    @Excel(name = "高危因素")
    private String epgHighRiskFactor;

    /** 新增高危因素（字典类型2）,多个以英文分号分割 */
    @Excel(name = "新增高危因素")
    private String newHighRiskFactor;

    /** 派出医院 */
    @Excel(name = "派出医院")
    private String sentHospital;

    /** 车牌号 */
    @Excel(name = "车牌号")
    private String licensePlateNumber;

    /** 市八确认时间 */
    @Excel(name = "市八确认时间")
    private String cityEightConfirmTime;

    /** 调派日期（格式为yyyy-MM-dd） */
    @Excel(name = "调派日期", dateFormat = "yyyy-MM-dd")
    private String assignDate;

    /** 调派时间（格式为hh24:mi） */
    @Excel(name = "调派时间", dateFormat = "HH:mm")
    private String assignTime;

    /** 救护车类型（0负压救护车 1普通转运车） */
    @Excel(name = "救护车类型", readConverterExp = "0=负压救护车,1=普通转运车")
    private String ambulanceType;

    /** 通知时间 */
    @Excel(name = "通知时间")
    private String noticeTime;

    /** 出车时间 */
    @Excel(name = "出车时间")
    private String drawOutTime;

    /** 达到机场急救中心时间 */
    @Excel(name = "达到机场急救中心时间")
    private String arriveAirportEcTime;

    /** 接车地址 */
    @Excel(name = "接车地址")
    private String pickUpAddress;

    /** 接收医院 */
    @Excel(name = "接收医院")
    private String receiveHospital;

    /** 救护车返回时间 */
    @Excel(name = "救护车返回时间")
    private String ambulanceBackTime;

    /** 任务完成时间 */
    @Excel(name = "任务完成时间")
    private String completeJobTime;

    /** 患者人次 */
    @Excel(name = "患者人次")
    private String patientPersonTime;

    /** 呼车备注 */
    @Excel(name = "呼车备注")
    private String carCallRemark;

    /** 调派备注 */
    @Excel(name = "调派备注")
    private String assignRemark;

    /** 完成备注 */
    @Excel(name = "完成备注")
    private String finishRemark;

    /** 值班员（调派人） */
    @Excel(name = "值班员(调派人)")
    private String dutyMan;

    /** 特殊病情 */
    @Length(max = 300, message = "不能超过300个字")
    private String specialCondition;

    /** 呼车区域（10越秀区、20海珠区、30荔湾区、40天河区、50白云区、60黄埔区、70南沙区，80番禺区、90花都区、100增城区、110从化区、120入境） */
    private String carCallArea;

    /** 数据来源（0防疫通 1新增） */
    private String dataSource;

    /** 是否已到院（0否 1是） */
    private String isArriveHospital;

    /** 调派状态（0未调派 1已调派 2任务中止） */
    private String assignStatus;

    public void setFdId(String fdId) 
    {
        this.fdId = fdId;
    }

    public String getFdId() 
    {
        return fdId;
    }
    public void setAcceptNo(String acceptNo) 
    {
        this.acceptNo = acceptNo;
    }

    public String getAcceptNo() 
    {
        return acceptNo;
    }
    public void setCarCallUnit(String carCallUnit) 
    {
        this.carCallUnit = carCallUnit;
    }

    public String getCarCallUnit() 
    {
        return carCallUnit;
    }
    public void setCarCallDate(String carCallDate) 
    {
        this.carCallDate = carCallDate;
    }

    public String getCarCallDate() 
    {
        return carCallDate;
    }
    public void setCarCallTime(String carCallTime) 
    {
        this.carCallTime = carCallTime;
    }

    public String getCarCallTime() 
    {
        return carCallTime;
    }
    public void setCarCallContractPerson(String carCallContractPerson) 
    {
        this.carCallContractPerson = carCallContractPerson;
    }

    public String getCarCallContractPerson() 
    {
        return carCallContractPerson;
    }
    public void setCarCallContractNumber(String carCallContractNumber) 
    {
        this.carCallContractNumber = carCallContractNumber;
    }

    public String getCarCallContractNumber() 
    {
        return carCallContractNumber;
    }
    public void setPatientName(String patientName) 
    {
        this.patientName = patientName;
    }

    public String getPatientName() 
    {
        return patientName;
    }
    public void setPatientIdNumber(String patientIdNumber) 
    {
        this.patientIdNumber = patientIdNumber;
    }

    public String getPatientIdNumber() 
    {
        return patientIdNumber;
    }
    public void setPatientContractNumber(String patientContractNumber) 
    {
        this.patientContractNumber = patientContractNumber;
    }

    public String getPatientContractNumber() 
    {
        return patientContractNumber;
    }
    public void setSex(String sex) 
    {
        this.sex = sex;
    }

    public String getSex() 
    {
        return sex;
    }
    public void setAge(String age) 
    {
        this.age = age;
    }

    public String getAge() 
    {
        return age;
    }
    public void setCaseSource(String caseSource) 
    {
        this.caseSource = caseSource;
    }

    public String getCaseSource() 
    {
        return caseSource;
    }
    public void setPersonIdentity(String personIdentity) 
    {
        this.personIdentity = personIdentity;
    }

    public String getPersonIdentity() 
    {
        return personIdentity;
    }
    public void setNucleateResult(String nucleateResult) 
    {
        this.nucleateResult = nucleateResult;
    }

    public String getNucleateResult() 
    {
        return nucleateResult;
    }
    public void setNucleateIsPositive(String nucleateIsPositive) 
    {
        this.nucleateIsPositive = nucleateIsPositive;
    }

    public String getNucleateIsPositive() 
    {
        return nucleateIsPositive;
    }
    public void setEpgHighRiskFactor(String epgHighRiskFactor) 
    {
        this.epgHighRiskFactor = epgHighRiskFactor;
    }

    public String getEpgHighRiskFactor() 
    {
        return epgHighRiskFactor;
    }
    public void setNewHighRiskFactor(String newHighRiskFactor) 
    {
        this.newHighRiskFactor = newHighRiskFactor;
    }

    public String getNewHighRiskFactor() 
    {
        return newHighRiskFactor;
    }
    public void setSentHospital(String sentHospital) 
    {
        this.sentHospital = sentHospital;
    }

    public String getSentHospital() 
    {
        return sentHospital;
    }
    public void setLicensePlateNumber(String licensePlateNumber) 
    {
        this.licensePlateNumber = licensePlateNumber;
    }

    public String getLicensePlateNumber() 
    {
        return licensePlateNumber;
    }
    public void setCityEightConfirmTime(String cityEightConfirmTime)
    {
        this.cityEightConfirmTime = cityEightConfirmTime;
    }

    public String getCityEightConfirmTime()
    {
        return cityEightConfirmTime;
    }
    public void setAssignDate(String assignDate) 
    {
        this.assignDate = assignDate;
    }

    public String getAssignDate() 
    {
        return assignDate;
    }
    public void setAssignTime(String assignTime) 
    {
        this.assignTime = assignTime;
    }

    public String getAssignTime() 
    {
        return assignTime;
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
    public void setDrawOutTime(String drawOutTime)
    {
        this.drawOutTime = drawOutTime;
    }

    public String getDrawOutTime()
    {
        return drawOutTime;
    }
    public void setArriveAirportEcTime(String arriveAirportEcTime)
    {
        this.arriveAirportEcTime = arriveAirportEcTime;
    }

    public String getArriveAirportEcTime()
    {
        return arriveAirportEcTime;
    }
    public void setPickUpAddress(String pickUpAddress) 
    {
        this.pickUpAddress = pickUpAddress;
    }

    public String getPickUpAddress() 
    {
        return pickUpAddress;
    }
    public void setReceiveHospital(String receiveHospital) 
    {
        this.receiveHospital = receiveHospital;
    }

    public String getReceiveHospital() 
    {
        return receiveHospital;
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
    public void setPatientPersonTime(String patientPersonTime) 
    {
        this.patientPersonTime = patientPersonTime;
    }

    public String getPatientPersonTime() 
    {
        return patientPersonTime;
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
    public void setDutyMan(String dutyMan) 
    {
        this.dutyMan = dutyMan;
    }

    public String getDutyMan() 
    {
        return dutyMan;
    }
    public void setSpecialCondition(String specialCondition) 
    {
        this.specialCondition = specialCondition;
    }

    public String getSpecialCondition() 
    {
        return specialCondition;
    }
    public void setCarCallArea(String carCallArea) 
    {
        this.carCallArea = carCallArea;
    }

    public String getCarCallArea() 
    {
        return carCallArea;
    }
    public void setDataSource(String dataSource) 
    {
        this.dataSource = dataSource;
    }

    public String getDataSource() 
    {
        return dataSource;
    }
    public void setIsArriveHospital(String isArriveHospital) 
    {
        this.isArriveHospital = isArriveHospital;
    }

    public String getIsArriveHospital() 
    {
        return isArriveHospital;
    }
    public void setAssignStatus(String assignStatus) 
    {
        this.assignStatus = assignStatus;
    }

    public String getAssignStatus() 
    {
        return assignStatus;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("fdId", getFdId())
            .append("acceptNo", getAcceptNo())
            .append("carCallUnit", getCarCallUnit())
            .append("carCallDate", getCarCallDate())
            .append("carCallTime", getCarCallTime())
            .append("carCallContractPerson", getCarCallContractPerson())
            .append("carCallContractNumber", getCarCallContractNumber())
            .append("patientName", getPatientName())
            .append("patientIdNumber", getPatientIdNumber())
            .append("patientContractNumber", getPatientContractNumber())
            .append("sex", getSex())
            .append("age", getAge())
            .append("caseSource", getCaseSource())
            .append("personIdentity", getPersonIdentity())
            .append("nucleateResult", getNucleateResult())
            .append("nucleateIsPositive", getNucleateIsPositive())
            .append("epgHighRiskFactor", getEpgHighRiskFactor())
            .append("newHighRiskFactor", getNewHighRiskFactor())
            .append("sentHospital", getSentHospital())
            .append("licensePlateNumber", getLicensePlateNumber())
            .append("cityEightConfirmTime", getCityEightConfirmTime())
            .append("assignDate", getAssignDate())
            .append("assignTime", getAssignTime())
            .append("ambulanceType", getAmbulanceType())
            .append("noticeTime", getNoticeTime())
            .append("drawOutTime", getDrawOutTime())
            .append("arriveAirportEcTime", getArriveAirportEcTime())
            .append("pickUpAddress", getPickUpAddress())
            .append("receiveHospital", getReceiveHospital())
            .append("ambulanceBackTime", getAmbulanceBackTime())
            .append("completeJobTime", getCompleteJobTime())
            .append("patientPersonTime", getPatientPersonTime())
            .append("carCallRemark", getCarCallRemark())
            .append("assignRemark", getAssignRemark())
            .append("finishRemark", getFinishRemark())
            .append("dutyMan", getDutyMan())
            .append("specialCondition", getSpecialCondition())
            .append("carCallArea", getCarCallArea())
            .append("dataSource", getDataSource())
            .append("isArriveHospital", getIsArriveHospital())
            .append("assignStatus", getAssignStatus())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
