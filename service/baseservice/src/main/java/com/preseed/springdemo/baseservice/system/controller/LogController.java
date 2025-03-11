package com.preseed.springdemo.baseservice.system.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.preseed.springdemo.common.model.Result;
import com.preseed.springdemo.common.result.PageResult;
import com.preseed.springdemo.baseservice.system.model.query.LogPageQuery;
import com.preseed.springdemo.baseservice.system.model.vo.LogPageVO;
import com.preseed.springdemo.baseservice.system.model.vo.VisitStatsVO;
import com.preseed.springdemo.baseservice.system.model.vo.VisitTrendVO;
import com.preseed.springdemo.baseservice.system.service.LogService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;


/**
 * 日志控制层
 *
 * @author Ray.Hao
 * @since 2.10.0
 */
// @Tag(name = "13.日志接口")
@RestController
@RequestMapping("/logs")
@RequiredArgsConstructor
public class LogController {

    private final LogService logService;

    // @Operation(summary = "日志分页列表")
    @GetMapping("/page")
    public PageResult<LogPageVO> getLogPage(
             LogPageQuery queryParams
    ) {
        Page<LogPageVO> result = logService.getLogPage(queryParams);
        return PageResult.success(result);
    }

    // @Operation(summary = "获取访问趋势")
    @GetMapping("/visit-trend")
    public Result<VisitTrendVO> getVisitTrend(
            // @Parameter(description = "开始时间", example = "yyyy-MM-dd")
            @RequestParam String startDate,
            // @Parameter(description = "结束时间", example = "yyyy-MM-dd")
            @RequestParam String endDate
    ) {
        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);
        VisitTrendVO data = logService.getVisitTrend(start, end);
        return Result.success(data);
    }

    // @Operation(summary = "获取访问统计")
    @GetMapping("/visit-stats")
    public Result<VisitStatsVO> getVisitStats() {
        VisitStatsVO result = logService.getVisitStats();
        return Result.success(result);
    }

}
