package com.phaeris.rolex.job.system;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

import java.io.Serializable;

/**
 * @author wyh
 * @since 2024/7/25
 */
@Data
public class PassThroughParam implements Serializable {

    private static final long serialVersionUID = 0;

    private String url;

    private JSONObject param;
}
