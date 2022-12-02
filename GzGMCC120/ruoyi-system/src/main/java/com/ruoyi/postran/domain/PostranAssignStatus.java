package com.ruoyi.postran.domain;

import lombok.Data;

import java.util.List;

@Data
public class PostranAssignStatus {
    private List<String> fdIds;
    private String taskType;
    private String reason;
}
