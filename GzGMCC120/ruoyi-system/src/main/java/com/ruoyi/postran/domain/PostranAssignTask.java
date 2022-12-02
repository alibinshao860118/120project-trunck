package com.ruoyi.postran.domain;

import lombok.Data;

import java.util.List;

/**
 * @Author: huangminghui
 * @Date: 2022/11/26 13:56
 * @Description: com.ruoyi.postran.domain
 * @Version: 1.0
 */
@Data
public class PostranAssignTask {
    private String fdId;
    private String doctor1;
    private String doctor2;
    private String nurse1;
    private String nurse2;
    private String driver;
    private String carNo;
    private String licensePlateNo;
    private String drawOutHospital;
    private String remark;
    private List<PostranAssign> postranAssignList;
    private List<PostranEntry> postranEntryList;
}
