package com.phaeris.rolex.controller;

import com.phaeris.rolex.obj.ResultBase;
import com.phaeris.rolex.obj.bo.JobBO;
import com.phaeris.rolex.obj.dto.GetJobDTO;
import com.phaeris.rolex.obj.vo.JobVO;
import com.phaeris.rolex.service.JobService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author wyh
 * @since 2024/7/19
 */
@Api(tags = "定时任务")
@RestController
@RequestMapping("/job")
@AllArgsConstructor
public class JobController {

    private final JobService jobService;

    @ApiOperation("任务列表(支持分页)")
    @PostMapping("/get")
    public ResultBase<List<JobVO>> get(@Valid @RequestBody GetJobDTO request) {
        return ResultBase.page(jobService.get(request));
    }

    @ApiOperation("任务详情")
    @GetMapping("/{id}")
    public ResultBase<JobBO> get(@PathVariable Long id) {
        return ResultBase.ok(jobService.getDetail(id));
    }

    @ApiOperation("新增/更新任务")
    @PostMapping
    public ResultBase<Long> save(@Valid @RequestBody JobBO request) {
        return ResultBase.ok(jobService.save(request));
    }

    @ApiOperation("删除任务")
    @DeleteMapping("/{id}")
    public ResultBase<Void> delete(@PathVariable Long id) {
        jobService.delete(id);
        return ResultBase.ok();
    }

    @ApiOperation("暂停任务")
    @PutMapping("/pause/{id}")
    public ResultBase<Void> pause(@PathVariable Long id) {
        jobService.pause(id);
        return ResultBase.ok();
    }

    @ApiOperation("恢复任务")
    @PutMapping("/resume/{id}")
    public ResultBase<Void> resume(@PathVariable Long id) {
        jobService.resume(id);
        return ResultBase.ok();
    }

    @ApiOperation("立即执行任务")
    @PutMapping("/run/{id}")
    public ResultBase<Void> run(@PathVariable Long id) {
        jobService.runNow(id);
        return ResultBase.ok();
    }
}
