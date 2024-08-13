package com.phaeris.rolex.util;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

/**
 * @author wyh
 * @since 2023/4/19
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PageUtil {

    /**
     * 分页查询
     *
     * @param mapper       查询mapper
     * @param queryWrapper 查询wrapper
     * @param pageNo       第几页
     * @param pageSize     每页多少条
     * @param clazz        需要转换的对象class
     * @param <T>          实体对象
     * @param <R>          返回对象
     * @return 分页结果
     */
    @SuppressWarnings("unused")
    public static <T, R> Page<R> selectPage(BaseMapper<T> mapper, Wrapper<T> queryWrapper, @Nullable Integer pageNo, @Nullable Integer pageSize, Class<R> clazz) {
        return convertPage(selectPage(mapper, queryWrapper, pageNo, pageSize), clazz);
    }

    /**
     * 分页查询
     *
     * @param mapper       查询mapper
     * @param queryWrapper 查询wrapper
     * @param pageNo       第几页
     * @param pageSize     每页多少条
     * @param <T>          实体对象
     * @return 分页结果
     */
    public static <T> Page<T> selectPage(BaseMapper<T> mapper, Wrapper<T> queryWrapper, @Nullable Integer pageNo, @Nullable Integer pageSize) {
        return mapper.selectPage(getPage(pageNo, pageSize), queryWrapper);
    }

    /**
     * 分页对象类型转换
     *
     * @param source 原分页对象
     * @param clazz  要转换成的对象class
     * @param <T>    要转换成的对象类型
     * @return 转换后的对象
     */
    public static <T> Page<T> convertPage(Page<?> source, Class<T> clazz) {
        Page<T> target = new Page<>();
        BeanUtil.copyProperties(source, target);
        target.setRecords(BeanUtil.copyToList(source.getRecords(), clazz));
        return target;
    }

    /**
     * 获取mp分页对象
     *
     * @param pageNo   第几页, 不传不分页
     * @param pageSize 每页多少条, 不传不分页
     * @param <T>      实体类型
     * @return 分页对象
     */
    public static <T> Page<T> getPage(@Nullable Integer pageNo, @Nullable Integer pageSize) {
        int finalPageNo = -1;
        int finalPageSize = -1;
        if (pageNo != null && pageSize != null) {
            finalPageNo = pageNo;
            finalPageSize = pageSize;
        }
        return new Page<>(finalPageNo, finalPageSize);
    }
}
