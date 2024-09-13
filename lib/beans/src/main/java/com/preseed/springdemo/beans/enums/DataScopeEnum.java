package com.preseed.springdemo.beans.enums;

import lombok.Getter;

@Getter
public enum DataScopeEnum {

  /* 全部的数据权限 */
  ALL("全部", "全部数据权限"),

  /* 自己部门的数据权限 */
  OWN_DEPT("所在部门", "所在部门的数据权限"),
    /* 自己部门的数据权限 */
  OWN_DEPT_AND_ALL_SUB("所在部门和所有下级部门", "所在部门和所有下级部门的数据权限"),

  /* 自定义的数据权限 */
  CUSTOMIZE("自定义", "自定义的数据权限");

  DataScopeEnum(String value, String description) {
    this.value = value;
    this.description = description;
  }

  private final String value;
  private final String description;

  public static DataScopeEnum find(String val) {
    for (DataScopeEnum dataScopeEnum : DataScopeEnum.values()) {
      if (dataScopeEnum.getValue().equals(val)) {
        return dataScopeEnum;
      }
    }
    return null;
  }

}
