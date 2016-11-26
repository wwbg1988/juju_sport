package com.juju.sport.user.pojo;

import java.util.Date;

public class Team {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column juju_team.id
     *
     * @mbggenerated Sat May 02 11:01:39 CST 2015
     */
    private String id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column juju_team.team_name
     *
     * @mbggenerated Sat May 02 11:01:39 CST 2015
     */
    private String teamName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column juju_team.thumbnail
     *
     * @mbggenerated Sat May 02 11:01:39 CST 2015
     */
    private String thumbnail;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column juju_team.war_type
     *
     * @mbggenerated Sat May 02 11:01:39 CST 2015
     */
    private Integer warType;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column juju_team.sport_id
     *
     * @mbggenerated Sat May 02 11:01:39 CST 2015
     */
    private String sportId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column juju_team.contact
     *
     * @mbggenerated Sat May 02 11:01:39 CST 2015
     */
    private String contact;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column juju_team.war_desc
     *
     * @mbggenerated Sat May 02 11:01:39 CST 2015
     */
    private String warDesc;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column juju_team.order_id
     *
     * @mbggenerated Sat May 02 11:01:39 CST 2015
     */
    private String orderId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column juju_team.user_account_id
     *
     * @mbggenerated Sat May 02 11:01:39 CST 2015
     */
    private String userAccountId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column juju_team.max_num
     *
     * @mbggenerated Sat May 02 11:01:39 CST 2015
     */
    private Integer maxNum;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column juju_team.join_num
     *
     * @mbggenerated Sat May 02 11:01:39 CST 2015
     */
    private Integer joinNum;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column juju_team.name_and_time
     *
     * @mbggenerated Sat May 02 11:01:39 CST 2015
     */
    private String nameAndTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column juju_team.create_time
     *
     * @mbggenerated Sat May 02 11:01:39 CST 2015
     */
    private Date createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column juju_team.last_update_time
     *
     * @mbggenerated Sat May 02 11:01:39 CST 2015
     */
    private Date lastUpdateTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column juju_team.stat
     *
     * @mbggenerated Sat May 02 11:01:39 CST 2015
     */
    private Integer stat;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column juju_team.id
     *
     * @return the value of juju_team.id
     *
     * @mbggenerated Sat May 02 11:01:39 CST 2015
     */
    public String getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column juju_team.id
     *
     * @param id the value for juju_team.id
     *
     * @mbggenerated Sat May 02 11:01:39 CST 2015
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column juju_team.team_name
     *
     * @return the value of juju_team.team_name
     *
     * @mbggenerated Sat May 02 11:01:39 CST 2015
     */
    public String getTeamName() {
        return teamName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column juju_team.team_name
     *
     * @param teamName the value for juju_team.team_name
     *
     * @mbggenerated Sat May 02 11:01:39 CST 2015
     */
    public void setTeamName(String teamName) {
        this.teamName = teamName == null ? null : teamName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column juju_team.thumbnail
     *
     * @return the value of juju_team.thumbnail
     *
     * @mbggenerated Sat May 02 11:01:39 CST 2015
     */
    public String getThumbnail() {
        return thumbnail;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column juju_team.thumbnail
     *
     * @param thumbnail the value for juju_team.thumbnail
     *
     * @mbggenerated Sat May 02 11:01:39 CST 2015
     */
    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail == null ? null : thumbnail.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column juju_team.war_type
     *
     * @return the value of juju_team.war_type
     *
     * @mbggenerated Sat May 02 11:01:39 CST 2015
     */
    public Integer getWarType() {
        return warType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column juju_team.war_type
     *
     * @param warType the value for juju_team.war_type
     *
     * @mbggenerated Sat May 02 11:01:39 CST 2015
     */
    public void setWarType(Integer warType) {
        this.warType = warType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column juju_team.sport_id
     *
     * @return the value of juju_team.sport_id
     *
     * @mbggenerated Sat May 02 11:01:39 CST 2015
     */
    public String getSportId() {
        return sportId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column juju_team.sport_id
     *
     * @param sportId the value for juju_team.sport_id
     *
     * @mbggenerated Sat May 02 11:01:39 CST 2015
     */
    public void setSportId(String sportId) {
        this.sportId = sportId == null ? null : sportId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column juju_team.contact
     *
     * @return the value of juju_team.contact
     *
     * @mbggenerated Sat May 02 11:01:39 CST 2015
     */
    public String getContact() {
        return contact;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column juju_team.contact
     *
     * @param contact the value for juju_team.contact
     *
     * @mbggenerated Sat May 02 11:01:39 CST 2015
     */
    public void setContact(String contact) {
        this.contact = contact == null ? null : contact.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column juju_team.war_desc
     *
     * @return the value of juju_team.war_desc
     *
     * @mbggenerated Sat May 02 11:01:39 CST 2015
     */
    public String getWarDesc() {
        return warDesc;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column juju_team.war_desc
     *
     * @param warDesc the value for juju_team.war_desc
     *
     * @mbggenerated Sat May 02 11:01:39 CST 2015
     */
    public void setWarDesc(String warDesc) {
        this.warDesc = warDesc == null ? null : warDesc.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column juju_team.order_id
     *
     * @return the value of juju_team.order_id
     *
     * @mbggenerated Sat May 02 11:01:39 CST 2015
     */
    public String getOrderId() {
        return orderId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column juju_team.order_id
     *
     * @param orderId the value for juju_team.order_id
     *
     * @mbggenerated Sat May 02 11:01:39 CST 2015
     */
    public void setOrderId(String orderId) {
        this.orderId = orderId == null ? null : orderId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column juju_team.user_account_id
     *
     * @return the value of juju_team.user_account_id
     *
     * @mbggenerated Sat May 02 11:01:39 CST 2015
     */
    public String getUserAccountId() {
        return userAccountId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column juju_team.user_account_id
     *
     * @param userAccountId the value for juju_team.user_account_id
     *
     * @mbggenerated Sat May 02 11:01:39 CST 2015
     */
    public void setUserAccountId(String userAccountId) {
        this.userAccountId = userAccountId == null ? null : userAccountId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column juju_team.max_num
     *
     * @return the value of juju_team.max_num
     *
     * @mbggenerated Sat May 02 11:01:39 CST 2015
     */
    public Integer getMaxNum() {
        return maxNum;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column juju_team.max_num
     *
     * @param maxNum the value for juju_team.max_num
     *
     * @mbggenerated Sat May 02 11:01:39 CST 2015
     */
    public void setMaxNum(Integer maxNum) {
        this.maxNum = maxNum;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column juju_team.join_num
     *
     * @return the value of juju_team.join_num
     *
     * @mbggenerated Sat May 02 11:01:39 CST 2015
     */
    public Integer getJoinNum() {
        return joinNum;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column juju_team.join_num
     *
     * @param joinNum the value for juju_team.join_num
     *
     * @mbggenerated Sat May 02 11:01:39 CST 2015
     */
    public void setJoinNum(Integer joinNum) {
        this.joinNum = joinNum;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column juju_team.name_and_time
     *
     * @return the value of juju_team.name_and_time
     *
     * @mbggenerated Sat May 02 11:01:39 CST 2015
     */
    public String getNameAndTime() {
        return nameAndTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column juju_team.name_and_time
     *
     * @param nameAndTime the value for juju_team.name_and_time
     *
     * @mbggenerated Sat May 02 11:01:39 CST 2015
     */
    public void setNameAndTime(String nameAndTime) {
        this.nameAndTime = nameAndTime == null ? null : nameAndTime.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column juju_team.create_time
     *
     * @return the value of juju_team.create_time
     *
     * @mbggenerated Sat May 02 11:01:39 CST 2015
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column juju_team.create_time
     *
     * @param createTime the value for juju_team.create_time
     *
     * @mbggenerated Sat May 02 11:01:39 CST 2015
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column juju_team.last_update_time
     *
     * @return the value of juju_team.last_update_time
     *
     * @mbggenerated Sat May 02 11:01:39 CST 2015
     */
    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column juju_team.last_update_time
     *
     * @param lastUpdateTime the value for juju_team.last_update_time
     *
     * @mbggenerated Sat May 02 11:01:39 CST 2015
     */
    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column juju_team.stat
     *
     * @return the value of juju_team.stat
     *
     * @mbggenerated Sat May 02 11:01:39 CST 2015
     */
    public Integer getStat() {
        return stat;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column juju_team.stat
     *
     * @param stat the value for juju_team.stat
     *
     * @mbggenerated Sat May 02 11:01:39 CST 2015
     */
    public void setStat(Integer stat) {
        this.stat = stat;
    }
}