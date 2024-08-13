package com.phaeris.rolex.obj.vo;

import com.phaeris.rolex.obj.po.Job;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * @author wyh
 * @since 2024/7/22
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class JobVO extends Job {

    private String state;
}
