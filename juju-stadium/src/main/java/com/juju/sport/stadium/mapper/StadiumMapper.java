package com.juju.sport.stadium.mapper;

import com.juju.sport.stadium.pojo.Stadium;
import com.juju.sport.stadium.pojo.StadiumExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface StadiumMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table juju_venues_info
     *
     * @mbggenerated Fri Mar 27 17:25:34 CST 2015
     */
    int countByExample(StadiumExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table juju_venues_info
     *
     * @mbggenerated Fri Mar 27 17:25:34 CST 2015
     */
    int deleteByExample(StadiumExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table juju_venues_info
     *
     * @mbggenerated Fri Mar 27 17:25:34 CST 2015
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table juju_venues_info
     *
     * @mbggenerated Fri Mar 27 17:25:34 CST 2015
     */
    int insert(Stadium record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table juju_venues_info
     *
     * @mbggenerated Fri Mar 27 17:25:34 CST 2015
     */
    int insertSelective(Stadium record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table juju_venues_info
     *
     * @mbggenerated Fri Mar 27 17:25:34 CST 2015
     */
    List<Stadium> selectByExample(StadiumExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table juju_venues_info
     *
     * @mbggenerated Fri Mar 27 17:25:34 CST 2015
     */
    Stadium selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table juju_venues_info
     *
     * @mbggenerated Fri Mar 27 17:25:34 CST 2015
     */
    int updateByExampleSelective(@Param("record") Stadium record, @Param("example") StadiumExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table juju_venues_info
     *
     * @mbggenerated Fri Mar 27 17:25:34 CST 2015
     */
    int updateByExample(@Param("record") Stadium record, @Param("example") StadiumExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table juju_venues_info
     *
     * @mbggenerated Fri Mar 27 17:25:34 CST 2015
     */
    int updateByPrimaryKeySelective(Stadium record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table juju_venues_info
     *
     * @mbggenerated Fri Mar 27 17:25:34 CST 2015
     */
    int updateByPrimaryKey(Stadium record);
}