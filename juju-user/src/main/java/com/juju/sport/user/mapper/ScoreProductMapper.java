package com.juju.sport.user.mapper;

import com.juju.sport.user.pojo.ScoreProduct;
import com.juju.sport.user.pojo.ScoreProductExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ScoreProductMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table juju_score_product
     *
     * @mbggenerated Tue Mar 24 18:24:43 CST 2015
     */
    int countByExample(ScoreProductExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table juju_score_product
     *
     * @mbggenerated Tue Mar 24 18:24:43 CST 2015
     */
    int deleteByExample(ScoreProductExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table juju_score_product
     *
     * @mbggenerated Tue Mar 24 18:24:43 CST 2015
     */
    int insert(ScoreProduct record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table juju_score_product
     *
     * @mbggenerated Tue Mar 24 18:24:43 CST 2015
     */
    int insertSelective(ScoreProduct record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table juju_score_product
     *
     * @mbggenerated Tue Mar 24 18:24:43 CST 2015
     */
    List<ScoreProduct> selectByExample(ScoreProductExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table juju_score_product
     *
     * @mbggenerated Tue Mar 24 18:24:43 CST 2015
     */
    int updateByExampleSelective(@Param("record") ScoreProduct record, @Param("example") ScoreProductExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table juju_score_product
     *
     * @mbggenerated Tue Mar 24 18:24:43 CST 2015
     */
    int updateByExample(@Param("record") ScoreProduct record, @Param("example") ScoreProductExample example);
}