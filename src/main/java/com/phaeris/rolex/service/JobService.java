package com.phaeris.rolex.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.phaeris.rolex.obj.bo.JobBO;
import com.phaeris.rolex.obj.dto.GetJobDTO;
import com.phaeris.rolex.obj.vo.JobVO;

/**
 * @author wyh
 * @since 2024/7/22
 */
public interface JobService {

    /**
     * 获取所有订阅任务
     *
     * @param request 入参
     * @return 任务
     */
    Page<JobVO> get(GetJobDTO request);

    /**
     * 获取订阅任务
     *
     * @param id 任务id
     * @return 任务
     */
    JobBO getDetail(Long id);

    /**
     * 新增/更新任务
     *
     * @param request 入参
     * @return 任务id
     */
    Long save(JobBO request);

    /**
     * 删除任务
     *
     * @param id id
     */
    void delete(Long id);

    /**
     * 暂停
     *
     * @param id id
     */
    void pause(Long id);

    /**
     * 恢复
     *
     * @param id id
     */
    void resume(Long id);

    /**
     * 立即运行
     *
     * @param id id
     */
    void runNow(Long id);
}
