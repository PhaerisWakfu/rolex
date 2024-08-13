package com.phaeris.rolex.obj.bo;

import cn.hutool.core.text.CharSequenceUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.phaeris.rolex.obj.po.Job;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Optional;

/**
 * @author wyh
 * @since 2024/7/24
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class JobBO extends Job {

    private JSONObject param;

    public JSONObject getParam() {
        return Optional.ofNullable(param)
                .orElse(CharSequenceUtil.isNotBlank(getParamJson())
                        ? JSON.parseObject(getParamJson())
                        : null);
    }
}
