package com.preseed.springdemo.baseservice.controller;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.preseed.springdemo.baseservice.model.form.MenuForm;
import com.preseed.springdemo.baseservice.model.query.MenuQuery;
import com.preseed.springdemo.baseservice.model.vo.MenuVO;
import com.preseed.springdemo.baseservice.model.vo.RouteVO;
import com.preseed.springdemo.baseservice.service.MenuService;
import com.preseed.springdemo.common.model.Option;
import com.preseed.springdemo.common.model.Result;

import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;

/**
 * 菜单控制层
 *
 */
@RestController
@RequestMapping("/menus")
@Slf4j
public class MenuController {

    @Resource
    private MenuService menuService;

    // @Operation(summary = "菜单列表")
    @GetMapping
    // @Log( value = "菜单列表",module = LogModuleEnum.MENU)
    public Result<List<MenuVO>> listMenus(MenuQuery queryParams) {
        List<MenuVO> menuList = menuService.listMenus(queryParams);
        return Result.success(menuList);
    }

    // @Operation(summary = "菜单下拉列表")
    @GetMapping("/options")
    public Result<List<Option<Long>>> listMenuOptions(
          // @Parameter(description = "是否只查询父级菜单")
          @RequestParam(required = false, defaultValue = "false") boolean onlyParent
    ) {
        List<Option<Long>> menus = menuService.listMenuOptions(onlyParent);
        return Result.success(menus);
    }

    // @Operation(summary = "菜单路由列表")
    @GetMapping("/routes")
    public Result<List<RouteVO>> getCurrentUserRoutes() {
        List<RouteVO> routeList = menuService.getCurrentUserRoutes();
        return Result.success(routeList);
    }

    // @Operation(summary = "菜单表单数据")
    @GetMapping("/{id}/form")
    public Result<MenuForm> getMenuForm(
            // @Parameter(description = "菜单ID") 
            @PathVariable Long id
    ) {
        MenuForm menu = menuService.getMenuForm(id);
        return Result.success(menu);
    }

    // @Operation(summary = "新增菜单")
    @PostMapping
    @PreAuthorize("@ss.hasPerm('sys:menu:add')")
    // @RepeatSubmit
    public Result<?> addMenu(@RequestBody MenuForm menuForm) {
        boolean result = menuService.saveMenu(menuForm);
        return Result.judge(result);
    }

    // @Operation(summary = "修改菜单")
    @PutMapping(value = "/{id}")
    @PreAuthorize("@ss.hasPerm('sys:menu:edit')")
    public Result<?> updateMenu(
            @RequestBody MenuForm menuForm
    ) {
        boolean result = menuService.saveMenu(menuForm);
        return Result.judge(result);
    }

    // @Operation(summary = "删除菜单")
    @DeleteMapping("/{id}")
    @PreAuthorize("@ss.hasPerm('sys:menu:delete')")
    public Result<?> deleteMenu(
            // @Parameter(description = "菜单ID，多个以英文(,)分割") 
            @PathVariable("id") Long id
    ) {
        boolean result = menuService.deleteMenu(id);
        return Result.judge(result);
    }

    // @Operation(summary = "修改菜单显示状态")
    @PatchMapping("/{menuId}")
    public Result<?> updateMenuVisible(
            // @Parameter(description = "菜单ID") 
            @PathVariable Long menuId,
            // @Parameter(description = "显示状态(1:显示;0:隐藏)") 
            Integer visible

    ) {
        boolean result = menuService.updateMenuVisible(menuId, visible);
        return Result.judge(result);
    }

}
