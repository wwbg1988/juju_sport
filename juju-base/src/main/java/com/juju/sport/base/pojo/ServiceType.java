package com.juju.sport.base.pojo;

import java.util.Date;

public class ServiceType {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column juju_service_type.id
     *
     * @mbggenerated Wed Apr 08 11:43:04 CST 2015
     */
    private String id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column juju_service_type.service_name
     *
     * @mbggenerated Wed Apr 08 11:43:04 CST 2015
     */
    private String serviceName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column juju_service_type.create_time
     *
     * @mbggenerated Wed Apr 08 11:43:04 CST 2015
     */
    private Date createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column juju_service_type.last_update_time
     *
     * @mbggenerated Wed Apr 08 11:43:04 CST 2015
     */
    private Date lastUpdateTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column juju_service_type.stat
     *
     * @mbggenerated Wed Apr 08 11:43:04 CST 2015
     */
    private Integer stat;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column juju_service_type.id
     *
     * @return the value of juju_service_type.id
     *
     * @mbggenerated Wed Apr 08 11:43:04 CST 2015
     */
    public String getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column juju_service_type.id
     *
     * @param id the value for juju_service_type.id
     *
     * @mbggenerated Wed Apr 08 11:43:04 CST 2015
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column juju_service_type.service_name
     *
     * @return the value of juju_service_type.service_name
     *
     * @mbggenerated Wed Apr 08 11:43:04 CST 2015
     */
    public String getServiceName() {
        return serviceName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column juju_service_type.service_name
     *
     * @param serviceName the value for juju_service_type.service_name
     *
     * @mbggenerated Wed Apr 08 11:43:04 CST 2015
     */
    public void setServiceName(String serviceName) {
        this.serviceName = serviceName == null ? null : serviceName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column juju_service_type.create_time
     *
     * @return the value of juju_service_type.create_time
     *
     * @mbggenerated Wed Apr 08 11:43:04 CST 2015
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column juju_service_type.create_time
     *
     * @param createTime the value for juju_service_type.create_time
     *
     * @mbggenerated Wed Apr 08 11:43:04 CST 2015
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column juju_service_type.last_update_time
     *
     * @return the value of juju_service_type.last_update_time
     *
     * @mbggenerated Wed Apr 08 11:43:04 CST 2015
     */
    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column juju_service_type.last_update_time
     *
     * @param lastUpdateTime the value for juju_service_type.last_update_time
     *
     * @mbggenerated Wed Apr 08 11:43:04 CST 2015
     */
    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column juju_service_type.stat
     *
     * @return the value of juju_service_type.stat
     *
     * @mbggenerated Wed Apr 08 11:43:04 CST 2015
     */
    public Integer getStat() {
        return stat;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column juju_service_type.stat
     *
     * @param stat the value for juju_service_type.stat
     *
     * @mbggenerated Wed Apr 08 11:43:04 CST 2015
     */
    public void setStat(Integer stat) {
        this.stat = stat;
    }
}