package com.juju.sport.family.pojo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SportIntroductionExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table juju_sport_introduction
     *
     * @mbggenerated Wed Apr 08 18:35:45 CST 2015
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table juju_sport_introduction
     *
     * @mbggenerated Wed Apr 08 18:35:45 CST 2015
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table juju_sport_introduction
     *
     * @mbggenerated Wed Apr 08 18:35:45 CST 2015
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table juju_sport_introduction
     *
     * @mbggenerated Wed Apr 08 18:35:45 CST 2015
     */
    public SportIntroductionExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table juju_sport_introduction
     *
     * @mbggenerated Wed Apr 08 18:35:45 CST 2015
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table juju_sport_introduction
     *
     * @mbggenerated Wed Apr 08 18:35:45 CST 2015
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table juju_sport_introduction
     *
     * @mbggenerated Wed Apr 08 18:35:45 CST 2015
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table juju_sport_introduction
     *
     * @mbggenerated Wed Apr 08 18:35:45 CST 2015
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table juju_sport_introduction
     *
     * @mbggenerated Wed Apr 08 18:35:45 CST 2015
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table juju_sport_introduction
     *
     * @mbggenerated Wed Apr 08 18:35:45 CST 2015
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table juju_sport_introduction
     *
     * @mbggenerated Wed Apr 08 18:35:45 CST 2015
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table juju_sport_introduction
     *
     * @mbggenerated Wed Apr 08 18:35:45 CST 2015
     */
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table juju_sport_introduction
     *
     * @mbggenerated Wed Apr 08 18:35:45 CST 2015
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table juju_sport_introduction
     *
     * @mbggenerated Wed Apr 08 18:35:45 CST 2015
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table juju_sport_introduction
     *
     * @mbggenerated Wed Apr 08 18:35:45 CST 2015
     */
    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(String value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(String value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(String value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(String value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(String value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(String value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLike(String value) {
            addCriterion("id like", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotLike(String value) {
            addCriterion("id not like", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<String> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<String> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(String value1, String value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(String value1, String value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andImgUrlIsNull() {
            addCriterion("img_url is null");
            return (Criteria) this;
        }

        public Criteria andImgUrlIsNotNull() {
            addCriterion("img_url is not null");
            return (Criteria) this;
        }

        public Criteria andImgUrlEqualTo(String value) {
            addCriterion("img_url =", value, "imgUrl");
            return (Criteria) this;
        }

        public Criteria andImgUrlNotEqualTo(String value) {
            addCriterion("img_url <>", value, "imgUrl");
            return (Criteria) this;
        }

        public Criteria andImgUrlGreaterThan(String value) {
            addCriterion("img_url >", value, "imgUrl");
            return (Criteria) this;
        }

        public Criteria andImgUrlGreaterThanOrEqualTo(String value) {
            addCriterion("img_url >=", value, "imgUrl");
            return (Criteria) this;
        }

        public Criteria andImgUrlLessThan(String value) {
            addCriterion("img_url <", value, "imgUrl");
            return (Criteria) this;
        }

        public Criteria andImgUrlLessThanOrEqualTo(String value) {
            addCriterion("img_url <=", value, "imgUrl");
            return (Criteria) this;
        }

        public Criteria andImgUrlLike(String value) {
            addCriterion("img_url like", value, "imgUrl");
            return (Criteria) this;
        }

        public Criteria andImgUrlNotLike(String value) {
            addCriterion("img_url not like", value, "imgUrl");
            return (Criteria) this;
        }

        public Criteria andImgUrlIn(List<String> values) {
            addCriterion("img_url in", values, "imgUrl");
            return (Criteria) this;
        }

        public Criteria andImgUrlNotIn(List<String> values) {
            addCriterion("img_url not in", values, "imgUrl");
            return (Criteria) this;
        }

        public Criteria andImgUrlBetween(String value1, String value2) {
            addCriterion("img_url between", value1, value2, "imgUrl");
            return (Criteria) this;
        }

        public Criteria andImgUrlNotBetween(String value1, String value2) {
            addCriterion("img_url not between", value1, value2, "imgUrl");
            return (Criteria) this;
        }

        public Criteria andTypeIsNull() {
            addCriterion("type is null");
            return (Criteria) this;
        }

        public Criteria andTypeIsNotNull() {
            addCriterion("type is not null");
            return (Criteria) this;
        }

        public Criteria andTypeEqualTo(Integer value) {
            addCriterion("type =", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotEqualTo(Integer value) {
            addCriterion("type <>", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThan(Integer value) {
            addCriterion("type >", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("type >=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThan(Integer value) {
            addCriterion("type <", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThanOrEqualTo(Integer value) {
            addCriterion("type <=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeIn(List<Integer> values) {
            addCriterion("type in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotIn(List<Integer> values) {
            addCriterion("type not in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeBetween(Integer value1, Integer value2) {
            addCriterion("type between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("type not between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andSportIsNull() {
            addCriterion("sport is null");
            return (Criteria) this;
        }

        public Criteria andSportIsNotNull() {
            addCriterion("sport is not null");
            return (Criteria) this;
        }

        public Criteria andSportEqualTo(String value) {
            addCriterion("sport =", value, "sport");
            return (Criteria) this;
        }

        public Criteria andSportNotEqualTo(String value) {
            addCriterion("sport <>", value, "sport");
            return (Criteria) this;
        }

        public Criteria andSportGreaterThan(String value) {
            addCriterion("sport >", value, "sport");
            return (Criteria) this;
        }

        public Criteria andSportGreaterThanOrEqualTo(String value) {
            addCriterion("sport >=", value, "sport");
            return (Criteria) this;
        }

        public Criteria andSportLessThan(String value) {
            addCriterion("sport <", value, "sport");
            return (Criteria) this;
        }

        public Criteria andSportLessThanOrEqualTo(String value) {
            addCriterion("sport <=", value, "sport");
            return (Criteria) this;
        }

        public Criteria andSportLike(String value) {
            addCriterion("sport like", value, "sport");
            return (Criteria) this;
        }

        public Criteria andSportNotLike(String value) {
            addCriterion("sport not like", value, "sport");
            return (Criteria) this;
        }

        public Criteria andSportIn(List<String> values) {
            addCriterion("sport in", values, "sport");
            return (Criteria) this;
        }

        public Criteria andSportNotIn(List<String> values) {
            addCriterion("sport not in", values, "sport");
            return (Criteria) this;
        }

        public Criteria andSportBetween(String value1, String value2) {
            addCriterion("sport between", value1, value2, "sport");
            return (Criteria) this;
        }

        public Criteria andSportNotBetween(String value1, String value2) {
            addCriterion("sport not between", value1, value2, "sport");
            return (Criteria) this;
        }

        public Criteria andEnvironmentalIsNull() {
            addCriterion("environmental is null");
            return (Criteria) this;
        }

        public Criteria andEnvironmentalIsNotNull() {
            addCriterion("environmental is not null");
            return (Criteria) this;
        }

        public Criteria andEnvironmentalEqualTo(Integer value) {
            addCriterion("environmental =", value, "environmental");
            return (Criteria) this;
        }

        public Criteria andEnvironmentalNotEqualTo(Integer value) {
            addCriterion("environmental <>", value, "environmental");
            return (Criteria) this;
        }

        public Criteria andEnvironmentalGreaterThan(Integer value) {
            addCriterion("environmental >", value, "environmental");
            return (Criteria) this;
        }

        public Criteria andEnvironmentalGreaterThanOrEqualTo(Integer value) {
            addCriterion("environmental >=", value, "environmental");
            return (Criteria) this;
        }

        public Criteria andEnvironmentalLessThan(Integer value) {
            addCriterion("environmental <", value, "environmental");
            return (Criteria) this;
        }

        public Criteria andEnvironmentalLessThanOrEqualTo(Integer value) {
            addCriterion("environmental <=", value, "environmental");
            return (Criteria) this;
        }

        public Criteria andEnvironmentalIn(List<Integer> values) {
            addCriterion("environmental in", values, "environmental");
            return (Criteria) this;
        }

        public Criteria andEnvironmentalNotIn(List<Integer> values) {
            addCriterion("environmental not in", values, "environmental");
            return (Criteria) this;
        }

        public Criteria andEnvironmentalBetween(Integer value1, Integer value2) {
            addCriterion("environmental between", value1, value2, "environmental");
            return (Criteria) this;
        }

        public Criteria andEnvironmentalNotBetween(Integer value1, Integer value2) {
            addCriterion("environmental not between", value1, value2, "environmental");
            return (Criteria) this;
        }

        public Criteria andSexIsNull() {
            addCriterion("sex is null");
            return (Criteria) this;
        }

        public Criteria andSexIsNotNull() {
            addCriterion("sex is not null");
            return (Criteria) this;
        }

        public Criteria andSexEqualTo(Integer value) {
            addCriterion("sex =", value, "sex");
            return (Criteria) this;
        }

        public Criteria andSexNotEqualTo(Integer value) {
            addCriterion("sex <>", value, "sex");
            return (Criteria) this;
        }

        public Criteria andSexGreaterThan(Integer value) {
            addCriterion("sex >", value, "sex");
            return (Criteria) this;
        }

        public Criteria andSexGreaterThanOrEqualTo(Integer value) {
            addCriterion("sex >=", value, "sex");
            return (Criteria) this;
        }

        public Criteria andSexLessThan(Integer value) {
            addCriterion("sex <", value, "sex");
            return (Criteria) this;
        }

        public Criteria andSexLessThanOrEqualTo(Integer value) {
            addCriterion("sex <=", value, "sex");
            return (Criteria) this;
        }

        public Criteria andSexIn(List<Integer> values) {
            addCriterion("sex in", values, "sex");
            return (Criteria) this;
        }

        public Criteria andSexNotIn(List<Integer> values) {
            addCriterion("sex not in", values, "sex");
            return (Criteria) this;
        }

        public Criteria andSexBetween(Integer value1, Integer value2) {
            addCriterion("sex between", value1, value2, "sex");
            return (Criteria) this;
        }

        public Criteria andSexNotBetween(Integer value1, Integer value2) {
            addCriterion("sex not between", value1, value2, "sex");
            return (Criteria) this;
        }

        public Criteria andDescdeIsNull() {
            addCriterion("descde is null");
            return (Criteria) this;
        }

        public Criteria andDescdeIsNotNull() {
            addCriterion("descde is not null");
            return (Criteria) this;
        }

        public Criteria andDescdeEqualTo(String value) {
            addCriterion("descde =", value, "descde");
            return (Criteria) this;
        }

        public Criteria andDescdeNotEqualTo(String value) {
            addCriterion("descde <>", value, "descde");
            return (Criteria) this;
        }

        public Criteria andDescdeGreaterThan(String value) {
            addCriterion("descde >", value, "descde");
            return (Criteria) this;
        }

        public Criteria andDescdeGreaterThanOrEqualTo(String value) {
            addCriterion("descde >=", value, "descde");
            return (Criteria) this;
        }

        public Criteria andDescdeLessThan(String value) {
            addCriterion("descde <", value, "descde");
            return (Criteria) this;
        }

        public Criteria andDescdeLessThanOrEqualTo(String value) {
            addCriterion("descde <=", value, "descde");
            return (Criteria) this;
        }

        public Criteria andDescdeLike(String value) {
            addCriterion("descde like", value, "descde");
            return (Criteria) this;
        }

        public Criteria andDescdeNotLike(String value) {
            addCriterion("descde not like", value, "descde");
            return (Criteria) this;
        }

        public Criteria andDescdeIn(List<String> values) {
            addCriterion("descde in", values, "descde");
            return (Criteria) this;
        }

        public Criteria andDescdeNotIn(List<String> values) {
            addCriterion("descde not in", values, "descde");
            return (Criteria) this;
        }

        public Criteria andDescdeBetween(String value1, String value2) {
            addCriterion("descde between", value1, value2, "descde");
            return (Criteria) this;
        }

        public Criteria andDescdeNotBetween(String value1, String value2) {
            addCriterion("descde not between", value1, value2, "descde");
            return (Criteria) this;
        }

        public Criteria andVideoUrlIsNull() {
            addCriterion("video_url is null");
            return (Criteria) this;
        }

        public Criteria andVideoUrlIsNotNull() {
            addCriterion("video_url is not null");
            return (Criteria) this;
        }

        public Criteria andVideoUrlEqualTo(String value) {
            addCriterion("video_url =", value, "videoUrl");
            return (Criteria) this;
        }

        public Criteria andVideoUrlNotEqualTo(String value) {
            addCriterion("video_url <>", value, "videoUrl");
            return (Criteria) this;
        }

        public Criteria andVideoUrlGreaterThan(String value) {
            addCriterion("video_url >", value, "videoUrl");
            return (Criteria) this;
        }

        public Criteria andVideoUrlGreaterThanOrEqualTo(String value) {
            addCriterion("video_url >=", value, "videoUrl");
            return (Criteria) this;
        }

        public Criteria andVideoUrlLessThan(String value) {
            addCriterion("video_url <", value, "videoUrl");
            return (Criteria) this;
        }

        public Criteria andVideoUrlLessThanOrEqualTo(String value) {
            addCriterion("video_url <=", value, "videoUrl");
            return (Criteria) this;
        }

        public Criteria andVideoUrlLike(String value) {
            addCriterion("video_url like", value, "videoUrl");
            return (Criteria) this;
        }

        public Criteria andVideoUrlNotLike(String value) {
            addCriterion("video_url not like", value, "videoUrl");
            return (Criteria) this;
        }

        public Criteria andVideoUrlIn(List<String> values) {
            addCriterion("video_url in", values, "videoUrl");
            return (Criteria) this;
        }

        public Criteria andVideoUrlNotIn(List<String> values) {
            addCriterion("video_url not in", values, "videoUrl");
            return (Criteria) this;
        }

        public Criteria andVideoUrlBetween(String value1, String value2) {
            addCriterion("video_url between", value1, value2, "videoUrl");
            return (Criteria) this;
        }

        public Criteria andVideoUrlNotBetween(String value1, String value2) {
            addCriterion("video_url not between", value1, value2, "videoUrl");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNull() {
            addCriterion("create_time is null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNotNull() {
            addCriterion("create_time is not null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeEqualTo(Date value) {
            addCriterion("create_time =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(Date value) {
            addCriterion("create_time <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(Date value) {
            addCriterion("create_time >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("create_time >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(Date value) {
            addCriterion("create_time <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(Date value) {
            addCriterion("create_time <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<Date> values) {
            addCriterion("create_time in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<Date> values) {
            addCriterion("create_time not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(Date value1, Date value2) {
            addCriterion("create_time between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(Date value1, Date value2) {
            addCriterion("create_time not between", value1, value2, "createTime");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table juju_sport_introduction
     *
     * @mbggenerated do_not_delete_during_merge Wed Apr 08 18:35:45 CST 2015
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table juju_sport_introduction
     *
     * @mbggenerated Wed Apr 08 18:35:45 CST 2015
     */
    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}