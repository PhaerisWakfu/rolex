package com.phaeris.rolex.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;

import java.util.List;

/**
 * @author wyh
 * @since 2023/3/17
 */
public interface CommonMapper<T> extends BaseMapper<T> {

    /**
     * 批量插入{@link com.avatar.support.config.MybatisPlusConfig.MySqlInjector}
     *
     * @param list model list
     */
    @SuppressWarnings("all")
    void insertBatchSomeColumn(List<T> list);

    /**
     * 生成queryWrapper
     */
    default LambdaQueryWrapper<T> wrapper() {
        return Wrappers.lambdaQuery();
    }
}
