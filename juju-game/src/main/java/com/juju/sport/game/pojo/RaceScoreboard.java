package com.juju.sport.game.pojo;

import java.util.Date;

public class RaceScoreboard {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column juju_race_scoreboard.id
     *
     * @mbggenerated Mon Apr 20 11:18:09 CST 2015
     */
    private String id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column juju_race_scoreboard.team_id
     *
     * @mbggenerated Mon Apr 20 11:18:09 CST 2015
     */
    private String teamId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column juju_race_scoreboard.team_name
     *
     * @mbggenerated Mon Apr 20 11:18:09 CST 2015
     */
    private String teamName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column juju_race_scoreboard.race_info_id
     *
     * @mbggenerated Mon Apr 20 11:18:09 CST 2015
     */
    private String raceInfoId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column juju_race_scoreboard.race_info_title
     *
     * @mbggenerated Mon Apr 20 11:18:09 CST 2015
     */
    private String raceInfoTitle;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column juju_race_scoreboard.team_group
     *
     * @mbggenerated Mon Apr 20 11:18:09 CST 2015
     */
    private String teamGroup;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column juju_race_scoreboard.won
     *
     * @mbggenerated Mon Apr 20 11:18:09 CST 2015
     */
    private Integer won;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column juju_race_scoreboard.drawn
     *
     * @mbggenerated Mon Apr 20 11:18:09 CST 2015
     */
    private Integer drawn;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column juju_race_scoreboard.lost
     *
     * @mbggenerated Mon Apr 20 11:18:09 CST 2015
     */
    private Integer lost;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column juju_race_scoreboard.goals_scored
     *
     * @mbggenerated Mon Apr 20 11:18:09 CST 2015
     */
    private Integer goalsScored;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column juju_race_scoreboard.goals_against
     *
     * @mbggenerated Mon Apr 20 11:18:09 CST 2015
     */
    private Integer goalsAgainst;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column juju_race_scoreboard.goals_difference
     *
     * @mbggenerated Mon Apr 20 11:18:09 CST 2015
     */
    private Integer goalsDifference;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column juju_race_scoreboard.points
     *
     * @mbggenerated Mon Apr 20 11:18:09 CST 2015
     */
    private Float points;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column juju_race_scoreboard.create_time
     *
     * @mbggenerated Mon Apr 20 11:18:09 CST 2015
     */
    private Date createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column juju_race_scoreboard.last_update_time
     *
     * @mbggenerated Mon Apr 20 11:18:09 CST 2015
     */
    private Date lastUpdateTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column juju_race_scoreboard.stat
     *
     * @mbggenerated Mon Apr 20 11:18:09 CST 2015
     */
    private Integer stat;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column juju_race_scoreboard.id
     *
     * @return the value of juju_race_scoreboard.id
     *
     * @mbggenerated Mon Apr 20 11:18:09 CST 2015
     */
    public String getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column juju_race_scoreboard.id
     *
     * @param id the value for juju_race_scoreboard.id
     *
     * @mbggenerated Mon Apr 20 11:18:09 CST 2015
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column juju_race_scoreboard.team_id
     *
     * @return the value of juju_race_scoreboard.team_id
     *
     * @mbggenerated Mon Apr 20 11:18:09 CST 2015
     */
    public String getTeamId() {
        return teamId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column juju_race_scoreboard.team_id
     *
     * @param teamId the value for juju_race_scoreboard.team_id
     *
     * @mbggenerated Mon Apr 20 11:18:09 CST 2015
     */
    public void setTeamId(String teamId) {
        this.teamId = teamId == null ? null : teamId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column juju_race_scoreboard.team_name
     *
     * @return the value of juju_race_scoreboard.team_name
     *
     * @mbggenerated Mon Apr 20 11:18:09 CST 2015
     */
    public String getTeamName() {
        return teamName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column juju_race_scoreboard.team_name
     *
     * @param teamName the value for juju_race_scoreboard.team_name
     *
     * @mbggenerated Mon Apr 20 11:18:09 CST 2015
     */
    public void setTeamName(String teamName) {
        this.teamName = teamName == null ? null : teamName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column juju_race_scoreboard.race_info_id
     *
     * @return the value of juju_race_scoreboard.race_info_id
     *
     * @mbggenerated Mon Apr 20 11:18:09 CST 2015
     */
    public String getRaceInfoId() {
        return raceInfoId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column juju_race_scoreboard.race_info_id
     *
     * @param raceInfoId the value for juju_race_scoreboard.race_info_id
     *
     * @mbggenerated Mon Apr 20 11:18:09 CST 2015
     */
    public void setRaceInfoId(String raceInfoId) {
        this.raceInfoId = raceInfoId == null ? null : raceInfoId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column juju_race_scoreboard.race_info_title
     *
     * @return the value of juju_race_scoreboard.race_info_title
     *
     * @mbggenerated Mon Apr 20 11:18:09 CST 2015
     */
    public String getRaceInfoTitle() {
        return raceInfoTitle;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column juju_race_scoreboard.race_info_title
     *
     * @param raceInfoTitle the value for juju_race_scoreboard.race_info_title
     *
     * @mbggenerated Mon Apr 20 11:18:09 CST 2015
     */
    public void setRaceInfoTitle(String raceInfoTitle) {
        this.raceInfoTitle = raceInfoTitle == null ? null : raceInfoTitle.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column juju_race_scoreboard.team_group
     *
     * @return the value of juju_race_scoreboard.team_group
     *
     * @mbggenerated Mon Apr 20 11:18:09 CST 2015
     */
    public String getTeamGroup() {
        return teamGroup;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column juju_race_scoreboard.team_group
     *
     * @param teamGroup the value for juju_race_scoreboard.team_group
     *
     * @mbggenerated Mon Apr 20 11:18:09 CST 2015
     */
    public void setTeamGroup(String teamGroup) {
        this.teamGroup = teamGroup == null ? null : teamGroup.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column juju_race_scoreboard.won
     *
     * @return the value of juju_race_scoreboard.won
     *
     * @mbggenerated Mon Apr 20 11:18:09 CST 2015
     */
    public Integer getWon() {
        return won;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column juju_race_scoreboard.won
     *
     * @param won the value for juju_race_scoreboard.won
     *
     * @mbggenerated Mon Apr 20 11:18:09 CST 2015
     */
    public void setWon(Integer won) {
        this.won = won;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column juju_race_scoreboard.drawn
     *
     * @return the value of juju_race_scoreboard.drawn
     *
     * @mbggenerated Mon Apr 20 11:18:09 CST 2015
     */
    public Integer getDrawn() {
        return drawn;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column juju_race_scoreboard.drawn
     *
     * @param drawn the value for juju_race_scoreboard.drawn
     *
     * @mbggenerated Mon Apr 20 11:18:09 CST 2015
     */
    public void setDrawn(Integer drawn) {
        this.drawn = drawn;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column juju_race_scoreboard.lost
     *
     * @return the value of juju_race_scoreboard.lost
     *
     * @mbggenerated Mon Apr 20 11:18:09 CST 2015
     */
    public Integer getLost() {
        return lost;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column juju_race_scoreboard.lost
     *
     * @param lost the value for juju_race_scoreboard.lost
     *
     * @mbggenerated Mon Apr 20 11:18:09 CST 2015
     */
    public void setLost(Integer lost) {
        this.lost = lost;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column juju_race_scoreboard.goals_scored
     *
     * @return the value of juju_race_scoreboard.goals_scored
     *
     * @mbggenerated Mon Apr 20 11:18:09 CST 2015
     */
    public Integer getGoalsScored() {
        return goalsScored;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column juju_race_scoreboard.goals_scored
     *
     * @param goalsScored the value for juju_race_scoreboard.goals_scored
     *
     * @mbggenerated Mon Apr 20 11:18:09 CST 2015
     */
    public void setGoalsScored(Integer goalsScored) {
        this.goalsScored = goalsScored;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column juju_race_scoreboard.goals_against
     *
     * @return the value of juju_race_scoreboard.goals_against
     *
     * @mbggenerated Mon Apr 20 11:18:09 CST 2015
     */
    public Integer getGoalsAgainst() {
        return goalsAgainst;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column juju_race_scoreboard.goals_against
     *
     * @param goalsAgainst the value for juju_race_scoreboard.goals_against
     *
     * @mbggenerated Mon Apr 20 11:18:09 CST 2015
     */
    public void setGoalsAgainst(Integer goalsAgainst) {
        this.goalsAgainst = goalsAgainst;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column juju_race_scoreboard.goals_difference
     *
     * @return the value of juju_race_scoreboard.goals_difference
     *
     * @mbggenerated Mon Apr 20 11:18:09 CST 2015
     */
    public Integer getGoalsDifference() {
        return goalsDifference;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column juju_race_scoreboard.goals_difference
     *
     * @param goalsDifference the value for juju_race_scoreboard.goals_difference
     *
     * @mbggenerated Mon Apr 20 11:18:09 CST 2015
     */
    public void setGoalsDifference(Integer goalsDifference) {
        this.goalsDifference = goalsDifference;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column juju_race_scoreboard.points
     *
     * @return the value of juju_race_scoreboard.points
     *
     * @mbggenerated Mon Apr 20 11:18:09 CST 2015
     */
    public Float getPoints() {
        return points;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column juju_race_scoreboard.points
     *
     * @param points the value for juju_race_scoreboard.points
     *
     * @mbggenerated Mon Apr 20 11:18:09 CST 2015
     */
    public void setPoints(Float points) {
        this.points = points;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column juju_race_scoreboard.create_time
     *
     * @return the value of juju_race_scoreboard.create_time
     *
     * @mbggenerated Mon Apr 20 11:18:09 CST 2015
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column juju_race_scoreboard.create_time
     *
     * @param createTime the value for juju_race_scoreboard.create_time
     *
     * @mbggenerated Mon Apr 20 11:18:09 CST 2015
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column juju_race_scoreboard.last_update_time
     *
     * @return the value of juju_race_scoreboard.last_update_time
     *
     * @mbggenerated Mon Apr 20 11:18:09 CST 2015
     */
    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column juju_race_scoreboard.last_update_time
     *
     * @param lastUpdateTime the value for juju_race_scoreboard.last_update_time
     *
     * @mbggenerated Mon Apr 20 11:18:09 CST 2015
     */
    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column juju_race_scoreboard.stat
     *
     * @return the value of juju_race_scoreboard.stat
     *
     * @mbggenerated Mon Apr 20 11:18:09 CST 2015
     */
    public Integer getStat() {
        return stat;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column juju_race_scoreboard.stat
     *
     * @param stat the value for juju_race_scoreboard.stat
     *
     * @mbggenerated Mon Apr 20 11:18:09 CST 2015
     */
    public void setStat(Integer stat) {
        this.stat = stat;
    }
}