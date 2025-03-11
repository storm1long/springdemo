package com.preseed.springdemo.baseservice.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.preseed.springdemo.baseservice.codegen.model.entity.GenConfig;
import com.preseed.springdemo.baseservice.system.model.entity.Menu;
import com.preseed.springdemo.baseservice.system.model.form.MenuForm;
import com.preseed.springdemo.baseservice.system.model.query.MenuQuery;
import com.preseed.springdemo.baseservice.system.model.vo.MenuVO;
import com.preseed.springdemo.baseservice.system.model.vo.RouteVO;
import com.preseed.springdemo.common.model.Option;

import java.util.List;
import java.util.Set;

/**
 * 菜单业务接口
 * 
 * @author haoxr
 * @since 2020/11/06
 */
public interface MenuService extends IService<Menu> {

    /**
     * 获取菜单表格列表
     */
    List<MenuVO> listMenus(MenuQuery queryParams);

    /**
     * 获取菜单下拉列表
     *
     * @param onlyParent 是否只查询父级菜单
     */
    List<Option<Long>> listMenuOptions(boolean onlyParent);

    /**
     * 新增菜单
     *
     * @param menuForm  菜单表单对象
     */
    boolean saveMenu(MenuForm menuForm);

    /**
     * 获取路由列表
     */
    List<RouteVO> getCurrentUserRoutes();

    /**
     * 修改菜单显示状态
     * 
     * @param menuId 菜单ID
     * @param visible 是否显示(1-显示 0-隐藏)
     */
    boolean updateMenuVisible(Long menuId, Integer visible);

    /**
     * 获取菜单表单数据
     *
     * @param id 菜单ID
     */
    MenuForm getMenuForm(Long id);

    /**
     * 删除菜单
     *
     * @param id 菜单ID
     */
    boolean deleteMenu(Long id);

    /**
     * 代码生成时添加菜单
     *
     * @param parentMenuId 父菜单ID
     * @param genConfig   实体名
     */
    void addMenuForCodegen(Long parentMenuId, GenConfig genConfig);
}
