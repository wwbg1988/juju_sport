package com.juju.sport.admin.manager.mapper;

import com.juju.sport.admin.manager.pojo.AdminAccount;
import com.juju.sport.admin.manager.pojo.AdminAccountExample;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface AdminAccountMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table admin_account
     *
     * @mbggenerated Tue Nov 18 09:59:51 CST 2014
     */
    int countByExample(AdminAccountExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table admin_account
     *
     * @mbggenerated Tue Nov 18 09:59:51 CST 2014
     */
    int deleteByExample(AdminAccountExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table admin_account
     *
     * @mbggenerated Tue Nov 18 09:59:51 CST 2014
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table admin_account
     *
     * @mbggenerated Tue Nov 18 09:59:51 CST 2014
     */
    int insert(AdminAccount record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table admin_account
     *
     * @mbggenerated Tue Nov 18 09:59:51 CST 2014
     */
    int insertSelective(AdminAccount record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table admin_account
     *
     * @mbggenerated Tue Nov 18 09:59:51 CST 2014
     */
    List<AdminAccount> selectByExample(AdminAccountExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table admin_account
     *
     * @mbggenerated Tue Nov 18 09:59:51 CST 2014
     */
    AdminAccount selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table admin_account
     *
     * @mbggenerated Tue Nov 18 09:59:51 CST 2014
     */
    int updateByExampleSelective(@Param("record") AdminAccount record, @Param("example") AdminAccountExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table admin_account
     *
     * @mbggenerated Tue Nov 18 09:59:51 CST 2014
     */
    int updateByExample(@Param("record") AdminAccount record, @Param("example") AdminAccountExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table admin_account
     *
     * @mbggenerated Tue Nov 18 09:59:51 CST 2014
     */
    int updateByPrimaryKeySelective(AdminAccount record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table admin_account
     *
     * @mbggenerated Tue Nov 18 09:59:51 CST 2014
     */
    int updateByPrimaryKey(AdminAccount record);
}