package com.juju.sport.family.pojo;

import java.util.Date;

public class Letter {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column juju_letter.id
     *
     * @mbggenerated Wed Apr 08 18:35:45 CST 2015
     */
    private String id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column juju_letter.letter
     *
     * @mbggenerated Wed Apr 08 18:35:45 CST 2015
     */
    private String letter;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column juju_letter.data
     *
     * @mbggenerated Wed Apr 08 18:35:45 CST 2015
     */
    private String data;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column juju_letter.create_time
     *
     * @mbggenerated Wed Apr 08 18:35:45 CST 2015
     */
    private Date createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column juju_letter.last_update_time
     *
     * @mbggenerated Wed Apr 08 18:35:45 CST 2015
     */
    private Date lastUpdateTime;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column juju_letter.id
     *
     * @return the value of juju_letter.id
     *
     * @mbggenerated Wed Apr 08 18:35:45 CST 2015
     */
    public String getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column juju_letter.id
     *
     * @param id the value for juju_letter.id
     *
     * @mbggenerated Wed Apr 08 18:35:45 CST 2015
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column juju_letter.letter
     *
     * @return the value of juju_letter.letter
     *
     * @mbggenerated Wed Apr 08 18:35:45 CST 2015
     */
    public String getLetter() {
        return letter;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column juju_letter.letter
     *
     * @param letter the value for juju_letter.letter
     *
     * @mbggenerated Wed Apr 08 18:35:45 CST 2015
     */
    public void setLetter(String letter) {
        this.letter = letter == null ? null : letter.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column juju_letter.data
     *
     * @return the value of juju_letter.data
     *
     * @mbggenerated Wed Apr 08 18:35:45 CST 2015
     */
    public String getData() {
        return data;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column juju_letter.data
     *
     * @param data the value for juju_letter.data
     *
     * @mbggenerated Wed Apr 08 18:35:45 CST 2015
     */
    public void setData(String data) {
        this.data = data == null ? null : data.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column juju_letter.create_time
     *
     * @return the value of juju_letter.create_time
     *
     * @mbggenerated Wed Apr 08 18:35:45 CST 2015
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column juju_letter.create_time
     *
     * @param createTime the value for juju_letter.create_time
     *
     * @mbggenerated Wed Apr 08 18:35:45 CST 2015
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column juju_letter.last_update_time
     *
     * @return the value of juju_letter.last_update_time
     *
     * @mbggenerated Wed Apr 08 18:35:45 CST 2015
     */
    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column juju_letter.last_update_time
     *
     * @param lastUpdateTime the value for juju_letter.last_update_time
     *
     * @mbggenerated Wed Apr 08 18:35:45 CST 2015
     */
    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }
}