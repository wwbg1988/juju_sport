package com.juju.sport.base.mapper;

import com.juju.sport.base.pojo.WuweiUser;
import com.juju.sport.base.pojo.WuweiUserExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface WuweiUserMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wuwei_user
     *
     * @mbggenerated Fri Jun 05 10:41:54 CST 2015
     */
    int countByExample(WuweiUserExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wuwei_user
     *
     * @mbggenerated Fri Jun 05 10:41:54 CST 2015
     */
    int deleteByExample(WuweiUserExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wuwei_user
     *
     * @mbggenerated Fri Jun 05 10:41:54 CST 2015
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wuwei_user
     *
     * @mbggenerated Fri Jun 05 10:41:54 CST 2015
     */
    int insert(WuweiUser record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wuwei_user
     *
     * @mbggenerated Fri Jun 05 10:41:54 CST 2015
     */
    int insertSelective(WuweiUser record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wuwei_user
     *
     * @mbggenerated Fri Jun 05 10:41:54 CST 2015
     */
    List<WuweiUser> selectByExample(WuweiUserExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wuwei_user
     *
     * @mbggenerated Fri Jun 05 10:41:54 CST 2015
     */
    WuweiUser selectByPrimaryKey(String id);
    
    List<WuweiUser> findUserDetail(WuweiUserExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wuwei_user
     *
     * @mbggenerated Fri Jun 05 10:41:54 CST 2015
     */
    int updateByExampleSelective(@Param("record") WuweiUser record, @Param("example") WuweiUserExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wuwei_user
     *
     * @mbggenerated Fri Jun 05 10:41:54 CST 2015
     */
    int updateByExample(@Param("record") WuweiUser record, @Param("example") WuweiUserExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wuwei_user
     *
     * @mbggenerated Fri Jun 05 10:41:54 CST 2015
     */
    int updateByPrimaryKeySelective(WuweiUser record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wuwei_user
     *
     * @mbggenerated Fri Jun 05 10:41:54 CST 2015
     */
    int updateByPrimaryKey(WuweiUser record);
}