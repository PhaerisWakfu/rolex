package com.phaeris.rolex.job.system;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONAware;
import com.alibaba.fastjson.JSONObject;
import com.phaeris.rolex.job.AbstractJob;
import com.phaeris.rolex.support.RestContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * 通用透传任务
 * <p>固定格式入参：url+JSONObject
 * <p>固定请求方式：content-type = application/json
 *
 * @author wyh
 * @since 2024/7/25
 */
@Slf4j
@Component
public class PassThroughJob extends AbstractJob<PassThroughParam> {

    @Override
    public void exec(PassThroughParam param) {
        log.info("透传任务调用开始 ==>\n{}", JSON.toJSONString(param));
        JSONObject jsonObject = RestContext.get().postForObject(param.getUrl(), param.getParam(), JSONObject.class);
        log.info("透传任务调用完成 <==\n{}", Optional.ofNullable(jsonObject).map(JSONAware::toJSONString).orElse(null));
    }
}
