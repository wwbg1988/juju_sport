package com.juju.sport.admin.manager.pojo;

import java.util.Date;

import lombok.ToString;

@ToString
public class Menu {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column admin_menu.id
     *
     * @mbggenerated Mon Sep 08 20:38:17 CST 2014
     */
    private String id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column admin_menu.menu_group_id
     *
     * @mbggenerated Mon Sep 08 20:38:17 CST 2014
     */
    private String menuGroupId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column admin_menu.menu_function_name
     *
     * @mbggenerated Mon Sep 08 20:38:17 CST 2014
     */
    private String menuFunctionName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column admin_menu.menu_function_action
     *
     * @mbggenerated Mon Sep 08 20:38:17 CST 2014
     */
    private String menuFunctionAction;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column admin_menu.menu_function_descript
     *
     * @mbggenerated Mon Sep 08 20:38:17 CST 2014
     */
    private String menuFunctionDescript;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column admin_menu.create_time
     *
     * @mbggenerated Mon Sep 08 20:38:17 CST 2014
     */
    private Date createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column admin_menu.create_person
     *
     * @mbggenerated Mon Sep 08 20:38:17 CST 2014
     */
    private String createPerson;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column admin_menu.last_update_time
     *
     * @mbggenerated Mon Sep 08 20:38:17 CST 2014
     */
    private Date lastUpdateTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column admin_menu.last_update_person
     *
     * @mbggenerated Mon Sep 08 20:38:17 CST 2014
     */
    private String lastUpdatePerson;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column admin_menu.stat
     *
     * @mbggenerated Mon Sep 08 20:38:17 CST 2014
     */
    private Integer stat;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column admin_menu.id
     *
     * @return the value of admin_menu.id
     *
     * @mbggenerated Mon Sep 08 20:38:17 CST 2014
     */
    public String getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column admin_menu.id
     *
     * @param id the value for admin_menu.id
     *
     * @mbggenerated Mon Sep 08 20:38:17 CST 2014
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column admin_menu.menu_group_id
     *
     * @return the value of admin_menu.menu_group_id
     *
     * @mbggenerated Mon Sep 08 20:38:17 CST 2014
     */
    public String getMenuGroupId() {
        return menuGroupId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column admin_menu.menu_group_id
     *
     * @param menuGroupId the value for admin_menu.menu_group_id
     *
     * @mbggenerated Mon Sep 08 20:38:17 CST 2014
     */
    public void setMenuGroupId(String menuGroupId) {
        this.menuGroupId = menuGroupId == null ? null : menuGroupId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column admin_menu.menu_function_name
     *
     * @return the value of admin_menu.menu_function_name
     *
     * @mbggenerated Mon Sep 08 20:38:17 CST 2014
     */
    public String getMenuFunctionName() {
        return menuFunctionName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column admin_menu.menu_function_name
     *
     * @param menuFunctionName the value for admin_menu.menu_function_name
     *
     * @mbggenerated Mon Sep 08 20:38:17 CST 2014
     */
    public void setMenuFunctionName(String menuFunctionName) {
        this.menuFunctionName = menuFunctionName == null ? null : menuFunctionName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column admin_menu.menu_function_action
     *
     * @return the value of admin_menu.menu_function_action
     *
     * @mbggenerated Mon Sep 08 20:38:17 CST 2014
     */
    public String getMenuFunctionAction() {
        return menuFunctionAction;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column admin_menu.menu_function_action
     *
     * @param menuFunctionAction the value for admin_menu.menu_function_action
     *
     * @mbggenerated Mon Sep 08 20:38:17 CST 2014
     */
    public void setMenuFunctionAction(String menuFunctionAction) {
        this.menuFunctionAction = menuFunctionAction == null ? null : menuFunctionAction.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column admin_menu.menu_function_descript
     *
     * @return the value of admin_menu.menu_function_descript
     *
     * @mbggenerated Mon Sep 08 20:38:17 CST 2014
     */
    public String getMenuFunctionDescript() {
        return menuFunctionDescript;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column admin_menu.menu_function_descript
     *
     * @param menuFunctionDescript the value for admin_menu.menu_function_descript
     *
     * @mbggenerated Mon Sep 08 20:38:17 CST 2014
     */
    public void setMenuFunctionDescript(String menuFunctionDescript) {
        this.menuFunctionDescript = menuFunctionDescript == null ? null : menuFunctionDescript.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column admin_menu.create_time
     *
     * @return the value of admin_menu.create_time
     *
     * @mbggenerated Mon Sep 08 20:38:17 CST 2014
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column admin_menu.create_time
     *
     * @param createTime the value for admin_menu.create_time
     *
     * @mbggenerated Mon Sep 08 20:38:17 CST 2014
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column admin_menu.create_person
     *
     * @return the value of admin_menu.create_person
     *
     * @mbggenerated Mon Sep 08 20:38:17 CST 2014
     */
    public String getCreatePerson() {
        return createPerson;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column admin_menu.create_person
     *
     * @param createPerson the value for admin_menu.create_person
     *
     * @mbggenerated Mon Sep 08 20:38:17 CST 2014
     */
    public void setCreatePerson(String createPerson) {
        this.createPerson = createPerson == null ? null : createPerson.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column admin_menu.last_update_time
     *
     * @return the value of admin_menu.last_update_time
     *
     * @mbggenerated Mon Sep 08 20:38:17 CST 2014
     */
    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column admin_menu.last_update_time
     *
     * @param lastUpdateTime the value for admin_menu.last_update_time
     *
     * @mbggenerated Mon Sep 08 20:38:17 CST 2014
     */
    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column admin_menu.last_update_person
     *
     * @return the value of admin_menu.last_update_person
     *
     * @mbggenerated Mon Sep 08 20:38:17 CST 2014
     */
    public String getLastUpdatePerson() {
        return lastUpdatePerson;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column admin_menu.last_update_person
     *
     * @param lastUpdatePerson the value for admin_menu.last_update_person
     *
     * @mbggenerated Mon Sep 08 20:38:17 CST 2014
     */
    public void setLastUpdatePerson(String lastUpdatePerson) {
        this.lastUpdatePerson = lastUpdatePerson == null ? null : lastUpdatePerson.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column admin_menu.stat
     *
     * @return the value of admin_menu.stat
     *
     * @mbggenerated Mon Sep 08 20:38:17 CST 2014
     */
    public Integer getStat() {
        return stat;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column admin_menu.stat
     *
     * @param stat the value for admin_menu.stat
     *
     * @mbggenerated Mon Sep 08 20:38:17 CST 2014
     */
    public void setStat(Integer stat) {
        this.stat = stat;
    }
}