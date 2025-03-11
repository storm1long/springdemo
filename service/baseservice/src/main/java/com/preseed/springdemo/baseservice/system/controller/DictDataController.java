package com.preseed.springdemo.baseservice.system.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.preseed.springdemo.beans.annotation.Log;
import com.preseed.springdemo.beans.annotation.RepeatSubmit;
import com.preseed.springdemo.beans.enums.LogModuleEnum;
import com.preseed.springdemo.common.model.Option;
import com.preseed.springdemo.common.model.Result;
import com.preseed.springdemo.common.result.PageResult;
import com.preseed.springdemo.baseservice.system.model.form.DictDataForm;
import com.preseed.springdemo.baseservice.system.model.query.DictDataPageQuery;
import com.preseed.springdemo.baseservice.system.model.vo.DictDataPageVO;
import com.preseed.springdemo.baseservice.system.service.DictDataService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 字典数据控制层
 *
 * @author Ray
 * @since 2.9.0
 */
// @Tag(name = "07.字典数据接口")
@RestController
@RequestMapping("/dict-data")
@RequiredArgsConstructor
public class DictDataController {

    private final DictDataService dictDataService;

    // @Operation(summary = "字典数据分页列表")
    @GetMapping("/page")
    // @Log( value = "字典数据分页列表",module = LogModuleEnum.DICT)
    public PageResult<DictDataPageVO> getDictDataPage(
            DictDataPageQuery queryParams
    ) {
        Page<DictDataPageVO> result = dictDataService.getDictDataPage(queryParams);
        return PageResult.success(result);
    }

    // @Operation(summary = "获取字典数据表单")
    @GetMapping("/{id}/form")
    public Result<DictDataForm> getDictDataForm(
            // @Parameter(description = "字典数据ID") 
            @PathVariable Long id
    ) {
        DictDataForm formData = dictDataService.getDictDataForm(id);
        return Result.success(formData);
    }

    // @Operation(summary = "新增字典数据")
    @PostMapping
    @PreAuthorize("@ss.hasPerm('sys:dict-data:add')")
    @RepeatSubmit
    public Result<Void> saveDictData(@Valid @RequestBody DictDataForm formData) {
        boolean result = dictDataService.saveDictData(formData);
        return Result.judge(result);
    }

    // @Operation(summary = "修改字典数据")
    @PutMapping("/{id}")
    @PreAuthorize("@ss.hasPerm('sys:dict-data:edit')")
    public Result<?> updateDictData(
            @PathVariable Long id,
            @RequestBody DictDataForm formData
    ) {
        boolean status = dictDataService.updateDictData(formData);
        return Result.judge(status);
    }

    // @Operation(summary = "删除字典数据")
    @DeleteMapping("/{ids}")
    @PreAuthorize("@ss.hasPerm('sys:dict-data:delete')")
    public Result<Void> deleteDictionaries(
            // @Parameter(description = "字典ID，多个以英文逗号(,)拼接") 
            @PathVariable String ids
    ) {
        dictDataService.deleteDictDataByIds(ids);
        return Result.success();
    }

    // @Operation(summary = "字典数据列表")
    @GetMapping("/{dictCode}/options")
    public Result<List<Option<String>>> getDictDataList(
            // @Parameter(description = "字典编码") 
            @PathVariable String dictCode
    ) {
        List<Option<String>> options = dictDataService.getDictDataList(dictCode);
        return Result.success(options);
    }

}
