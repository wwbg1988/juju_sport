package com.juju.sport.family.pojo;

import java.util.Date;

public class Health {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column juju_health.id
     *
     * @mbggenerated Wed Apr 08 18:35:45 CST 2015
     */
    private String id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column juju_health.health_knowledge
     *
     * @mbggenerated Wed Apr 08 18:35:45 CST 2015
     */
    private String healthKnowledge;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column juju_health.create_time
     *
     * @mbggenerated Wed Apr 08 18:35:45 CST 2015
     */
    private Date createTime;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column juju_health.id
     *
     * @return the value of juju_health.id
     *
     * @mbggenerated Wed Apr 08 18:35:45 CST 2015
     */
    public String getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column juju_health.id
     *
     * @param id the value for juju_health.id
     *
     * @mbggenerated Wed Apr 08 18:35:45 CST 2015
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column juju_health.health_knowledge
     *
     * @return the value of juju_health.health_knowledge
     *
     * @mbggenerated Wed Apr 08 18:35:45 CST 2015
     */
    public String getHealthKnowledge() {
        return healthKnowledge;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column juju_health.health_knowledge
     *
     * @param healthKnowledge the value for juju_health.health_knowledge
     *
     * @mbggenerated Wed Apr 08 18:35:45 CST 2015
     */
    public void setHealthKnowledge(String healthKnowledge) {
        this.healthKnowledge = healthKnowledge == null ? null : healthKnowledge.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column juju_health.create_time
     *
     * @return the value of juju_health.create_time
     *
     * @mbggenerated Wed Apr 08 18:35:45 CST 2015
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column juju_health.create_time
     *
     * @param createTime the value for juju_health.create_time
     *
     * @mbggenerated Wed Apr 08 18:35:45 CST 2015
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}