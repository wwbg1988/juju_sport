package com.juju.sport.user.mapper;

import com.juju.sport.user.pojo.UserAccount;
import com.juju.sport.user.pojo.UserAccountExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserAccountMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table juju_user_account
     *
     * @mbggenerated Mon May 25 18:00:09 CST 2015
     */
    int countByExample(UserAccountExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table juju_user_account
     *
     * @mbggenerated Mon May 25 18:00:09 CST 2015
     */
    int deleteByExample(UserAccountExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table juju_user_account
     *
     * @mbggenerated Mon May 25 18:00:09 CST 2015
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table juju_user_account
     *
     * @mbggenerated Mon May 25 18:00:09 CST 2015
     */
    int insert(UserAccount record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table juju_user_account
     *
     * @mbggenerated Mon May 25 18:00:09 CST 2015
     */
    int insertSelective(UserAccount record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table juju_user_account
     *
     * @mbggenerated Mon May 25 18:00:09 CST 2015
     */
    List<UserAccount> selectByExample(UserAccountExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table juju_user_account
     *
     * @mbggenerated Mon May 25 18:00:09 CST 2015
     */
    UserAccount selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table juju_user_account
     *
     * @mbggenerated Mon May 25 18:00:09 CST 2015
     */
    int updateByExampleSelective(@Param("record") UserAccount record, @Param("example") UserAccountExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table juju_user_account
     *
     * @mbggenerated Mon May 25 18:00:09 CST 2015
     */
    int updateByExample(@Param("record") UserAccount record, @Param("example") UserAccountExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table juju_user_account
     *
     * @mbggenerated Mon May 25 18:00:09 CST 2015
     */
    int updateByPrimaryKeySelective(UserAccount record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table juju_user_account
     *
     * @mbggenerated Mon May 25 18:00:09 CST 2015
     */
    int updateByPrimaryKey(UserAccount record);
}