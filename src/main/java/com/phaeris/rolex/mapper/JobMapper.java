package com.phaeris.rolex.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.phaeris.rolex.obj.po.Job;
import com.phaeris.rolex.obj.vo.JobVO;
import org.apache.ibatis.annotations.Param;

/**
 * @author wyh
 * @since 2024/7/22
 */
public interface JobMapper extends CommonMapper<Job> {

    Page<JobVO> list(Page<JobVO> page, @Param("type") Integer type, @Param("tag") String tag);
}
