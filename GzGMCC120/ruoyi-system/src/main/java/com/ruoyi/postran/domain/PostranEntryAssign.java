package com.ruoyi.postran.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.Date;

/**
 * 120核阳转运调派对象 PostranEntryAssign
 *
 * @author ruoyi
 * @date 2022-11-24
 */
@Data
public class PostranEntryAssign extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 编号 */
    private String fdId;

    /** 受理编号 */
    @Excel(name = "受理编号")
    @Pattern(regexp = "^\\d{1,10}$", message = "受理编号不能为空且只能填10位数字以内")
    private String acceptNo;

    /** 呼车单位（字典类型1） */
    @Excel(name = "呼车单位", readConverterExp = "字=典类型1")
    private String carCallUnit;

    /** 呼车日期（格式为yyyy-MM-dd） */
    @Excel(name = "呼车日期", readConverterExp = "格=式为yyyy-MM-dd")
    @Pattern(regexp = "^\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|(1|2)[0-9]|30|31)$", message = "呼车日期格式不正确")
    private String carCallDate;

    /** 呼车时间（格式为hh24:mi） */
    @Excel(name = "呼车时间", readConverterExp = "格=式为hh24:mi")
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
    @Excel(name = "性别", readConverterExp = "0=男,1=女,2=未知")
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
    @Excel(name = "防疫通高危因素")
    private String epgHighRiskFactor;

    /** 新增高危因素（字典类型2）,多个以英文分号分割 */
    @Excel(name = "新增高危因素", readConverterExp = "字=典类型2")
    private String newHighRiskFactor;

    /** 派出医院（字典类型3） */
    @Excel(name = "派出医院", readConverterExp = "字=典类型3")
    private String sentHospital;

    /** 车牌号 */
    @Excel(name = "车牌号")
    private String licensePlateNumber;

    /** 市八确认时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "市八确认时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date cityEightConfirmTime;

    /** 调派日期（格式为yyyy-MM-dd） */
    @Excel(name = "调派日期", readConverterExp = "格=式为yyyy-MM-dd")
    private String assignDate;

    /** 调派时间（格式为hh24:mi） */
    @Excel(name = "调派时间", readConverterExp = "格=式为hh24:mi")
    private String assignTime;

    /** 救护车类型（0负压救护车 1普通转运车） */
    @Excel(name = "救护车类型", readConverterExp = "0=负压救护车,1=普通转运车")
    private String ambulanceType;

    /** 通知时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "通知时间")
    private String noticeTime;

    /** 出车时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "出车时间", width = 30, dateFormat = "yyyy-MM-dd")
    private String drawOutTime;

    /** 达到机场急救中心时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "达到机场急救中心时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date arriveAirportEcTime;

    /** 接车地址 */
    @Excel(name = "接车地址")
    private String pickUpAddress;

    /** 接收医院 */
    @Excel(name = "接收医院")
    private String receiveHospital;

    /** 救护车返回时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "救护车返回时间", width = 30, dateFormat = "yyyy-MM-dd")
    private String ambulanceBackTime;

    /** 任务完成时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "任务完成时间", width = 30, dateFormat = "yyyy-MM-dd")
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
    @Excel(name = "值班员", readConverterExp = "调=派人")
    private String dutyMan;

    /** 特殊病情 */
    @Excel(name = "特殊病情")
    @Length(max = 300, message = "不能超过300个字")
    private String specialCondition;

    /** 呼车区域（10越秀区、20海珠区、30荔湾区、40天河区、50白云区、60黄埔区、70南沙区，80番禺区、90花都区、100增城区、110从化区、120入境） */
    @Excel(name = "呼车区域", readConverterExp = "1=0越秀区、20海珠区、30荔湾区、40天河区、50白云区、60黄埔区、70南沙区，80番禺区、90花都区、100增城区、110从化区、120入境")
    private String carCallArea;

    /** 数据来源（0防疫通 1新增） */
    @Excel(name = "数据来源", readConverterExp = "0=防疫通,1=新增")
    private String dataSource;

    /** 是否已到院（0否 1是） */
    @Excel(name = "是否已到院", readConverterExp = "0=否,1=是")
    private String isArriveHospital;

    /** 调派状态（0未调派 1已调派 2任务中止） */
    @Excel(name = "调派状态", readConverterExp = "0=未调派,1=已调派,2=任务中止")
    private String assignStatus;


    ////////////////////////////////////////////////

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

//    /** 特殊病情 */
//    @Excel(name = "特殊病情")
//    private String specialCondition;

    /** 新接收医院地址 */
    @Excel(name = "新接收医院地址")
    private String newReceiveHospitalAddress;

//    /** 接收医院（字典类型5） */
//    @Excel(name = "接收医院", readConverterExp = "字=典类型5")
//    private String receiveHospital;

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
