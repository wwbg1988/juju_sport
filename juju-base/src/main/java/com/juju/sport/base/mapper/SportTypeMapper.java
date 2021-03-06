package com.juju.sport.base.mapper;

import com.juju.sport.base.pojo.SportType;
import com.juju.sport.base.pojo.SportTypeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SportTypeMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table juju_sport_type
     *
     * @mbggenerated Tue Mar 24 13:25:48 CST 2015
     */
    int countByExample(SportTypeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table juju_sport_type
     *
     * @mbggenerated Tue Mar 24 13:25:48 CST 2015
     */
    int deleteByExample(SportTypeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table juju_sport_type
     *
     * @mbggenerated Tue Mar 24 13:25:48 CST 2015
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table juju_sport_type
     *
     * @mbggenerated Tue Mar 24 13:25:48 CST 2015
     */
    int insert(SportType record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table juju_sport_type
     *
     * @mbggenerated Tue Mar 24 13:25:48 CST 2015
     */
    int insertSelective(SportType record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table juju_sport_type
     *
     * @mbggenerated Tue Mar 24 13:25:48 CST 2015
     */
    List<SportType> selectByExample(SportTypeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table juju_sport_type
     *
     * @mbggenerated Tue Mar 24 13:25:48 CST 2015
     */
    SportType selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table juju_sport_type
     *
     * @mbggenerated Tue Mar 24 13:25:48 CST 2015
     */
    int updateByExampleSelective(@Param("record") SportType record, @Param("example") SportTypeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table juju_sport_type
     *
     * @mbggenerated Tue Mar 24 13:25:48 CST 2015
     */
    int updateByExample(@Param("record") SportType record, @Param("example") SportTypeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table juju_sport_type
     *
     * @mbggenerated Tue Mar 24 13:25:48 CST 2015
     */
    int updateByPrimaryKeySelective(SportType record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table juju_sport_type
     *
     * @mbggenerated Tue Mar 24 13:25:48 CST 2015
     */
    int updateByPrimaryKey(SportType record);
}