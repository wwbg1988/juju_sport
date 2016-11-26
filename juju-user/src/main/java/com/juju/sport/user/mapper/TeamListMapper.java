package com.juju.sport.user.mapper;

import com.juju.sport.user.pojo.TeamList;
import com.juju.sport.user.pojo.TeamListExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TeamListMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table juju_team_list
     *
     * @mbggenerated Wed May 13 10:11:34 CST 2015
     */
    int countByExample(TeamListExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table juju_team_list
     *
     * @mbggenerated Wed May 13 10:11:34 CST 2015
     */
    int deleteByExample(TeamListExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table juju_team_list
     *
     * @mbggenerated Wed May 13 10:11:34 CST 2015
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table juju_team_list
     *
     * @mbggenerated Wed May 13 10:11:34 CST 2015
     */
    int insert(TeamList record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table juju_team_list
     *
     * @mbggenerated Wed May 13 10:11:34 CST 2015
     */
    int insertSelective(TeamList record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table juju_team_list
     *
     * @mbggenerated Wed May 13 10:11:34 CST 2015
     */
    List<TeamList> selectByExample(TeamListExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table juju_team_list
     *
     * @mbggenerated Wed May 13 10:11:34 CST 2015
     */
    TeamList selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table juju_team_list
     *
     * @mbggenerated Wed May 13 10:11:34 CST 2015
     */
    int updateByExampleSelective(@Param("record") TeamList record, @Param("example") TeamListExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table juju_team_list
     *
     * @mbggenerated Wed May 13 10:11:34 CST 2015
     */
    int updateByExample(@Param("record") TeamList record, @Param("example") TeamListExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table juju_team_list
     *
     * @mbggenerated Wed May 13 10:11:34 CST 2015
     */
    int updateByPrimaryKeySelective(TeamList record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table juju_team_list
     *
     * @mbggenerated Wed May 13 10:11:34 CST 2015
     */
    int updateByPrimaryKey(TeamList record);
}