package com.phaeris.rolex.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.phaeris.rolex.enums.JobTypeEnum;
import com.phaeris.rolex.exception.ExternalException;
import com.phaeris.rolex.mapper.JobMapper;
import com.phaeris.rolex.obj.bo.JobBO;
import com.phaeris.rolex.obj.dto.GetJobDTO;
import com.phaeris.rolex.obj.po.Job;
import com.phaeris.rolex.obj.vo.JobVO;
import com.phaeris.rolex.util.PageUtil;
import com.phaeris.rolex.service.JobService;
import com.phaeris.rolex.support.JobContext;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Objects;
import java.util.Optional;

/**
 * @author wyh
 * @since 2024/7/22
 */
@Service
@AllArgsConstructor
public class JobServiceImpl implements JobService {

    private final JobMapper jobMapper;

    @Override
    public Page<JobVO> get(GetJobDTO request) {
        return jobMapper.list(PageUtil.getPage(request.getPageNo(), request.getPageSize()), request.getType(), request.getTag());
    }

    @Override
    public JobBO getDetail(Long id) {
        return BeanUtil.copyProperties(get(id), JobBO.class);
    }

    @Override
    @Transactional
    public Long save(JobBO request) {
        JobTypeEnum jobType = JobTypeEnum.getByType(request.getType());
        String groupName = jobType.getGroupName();
        Long id = request.getId();
        String cron = request.getCron();
        String zoneId = request.getZoneId();
        Date startAt = request.getStartAt();
        Date endAt = request.getEndAt();
        Date now = new Date();
        String jobName;
        if (Objects.isNull(id)) {
            jobName = IdUtil.simpleUUID();
            request.setJobGroupName(groupName);
            request.setJobName(jobName);
            Optional.ofNullable(request.getParam()).ifPresent(p -> request.setParamJson(p.toJSONString()));
            request.setCtime(now);
            jobMapper.insert(request);
            id = request.getId();
            JobContext.addJob(jobType.getJobClass(), groupName, jobName, cron, zoneId, id, startAt, endAt);
        } else {
            Job job = get(id);
            jobName = job.getJobName();
            BeanUtil.copyProperties(request, job);
            Optional.ofNullable(request.getParam()).ifPresent(p -> job.setParamJson(p.toJSONString()));
            job.setMtime(now);
            jobMapper.updateById(job);
            JobContext.updateJob(groupName, jobName, cron, zoneId, startAt, endAt);
        }
        return id;
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Job job = get(id);
        JobTypeEnum jobType = JobTypeEnum.getByType(job.getType());
        jobMapper.deleteById(id);
        JobContext.deleteJob(jobType.getGroupName(), job.getJobName());
    }

    @Override
    public void pause(Long id) {
        Job job = get(id);
        JobTypeEnum jobType = JobTypeEnum.getByType(job.getType());
        JobContext.pauseJob(jobType.getGroupName(), job.getJobName());
    }

    @Override
    public void resume(Long id) {
        Job job = get(id);
        JobTypeEnum jobType = JobTypeEnum.getByType(job.getType());
        JobContext.resumeJob(jobType.getGroupName(), job.getJobName());
    }

    @Override
    public void runNow(Long id) {
        Job job = get(id);
        JobTypeEnum jobType = JobTypeEnum.getByType(job.getType());
        JobContext.runJobNow(jobType.getGroupName(), job.getJobName());
    }

    private Job get(Long id) {
        return Optional.ofNullable(jobMapper.selectById(id)).orElseThrow(() -> new ExternalException("任务不存在"));
    }
}
