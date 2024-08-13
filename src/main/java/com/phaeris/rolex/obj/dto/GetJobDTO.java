package com.phaeris.rolex.obj.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author wyh
 * @since 2024/7/22
 */
@Data
public class GetJobDTO {

    @NotNull(message = "任务类型不能为空")
    private Integer type;

    private String tag;

    private Integer pageNo;

    private Integer pageSize;
}
