package com.juju.sport.admin.manager.pojo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MenuGroupExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table admin_menu_group
     *
     * @mbggenerated Thu Aug 28 14:42:12 CST 2014
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table admin_menu_group
     *
     * @mbggenerated Thu Aug 28 14:42:12 CST 2014
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table admin_menu_group
     *
     * @mbggenerated Thu Aug 28 14:42:12 CST 2014
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table admin_menu_group
     *
     * @mbggenerated Thu Aug 28 14:42:12 CST 2014
     */
    public MenuGroupExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table admin_menu_group
     *
     * @mbggenerated Thu Aug 28 14:42:12 CST 2014
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table admin_menu_group
     *
     * @mbggenerated Thu Aug 28 14:42:12 CST 2014
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table admin_menu_group
     *
     * @mbggenerated Thu Aug 28 14:42:12 CST 2014
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table admin_menu_group
     *
     * @mbggenerated Thu Aug 28 14:42:12 CST 2014
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table admin_menu_group
     *
     * @mbggenerated Thu Aug 28 14:42:12 CST 2014
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table admin_menu_group
     *
     * @mbggenerated Thu Aug 28 14:42:12 CST 2014
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table admin_menu_group
     *
     * @mbggenerated Thu Aug 28 14:42:12 CST 2014
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table admin_menu_group
     *
     * @mbggenerated Thu Aug 28 14:42:12 CST 2014
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
     * This method corresponds to the database table admin_menu_group
     *
     * @mbggenerated Thu Aug 28 14:42:12 CST 2014
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table admin_menu_group
     *
     * @mbggenerated Thu Aug 28 14:42:12 CST 2014
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table admin_menu_group
     *
     * @mbggenerated Thu Aug 28 14:42:12 CST 2014
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

        public Criteria andMenuGroupNameIsNull() {
            addCriterion("menu_group_name is null");
            return (Criteria) this;
        }

        public Criteria andMenuGroupNameIsNotNull() {
            addCriterion("menu_group_name is not null");
            return (Criteria) this;
        }

        public Criteria andMenuGroupNameEqualTo(String value) {
            addCriterion("menu_group_name =", value, "menuGroupName");
            return (Criteria) this;
        }

        public Criteria andMenuGroupNameNotEqualTo(String value) {
            addCriterion("menu_group_name <>", value, "menuGroupName");
            return (Criteria) this;
        }

        public Criteria andMenuGroupNameGreaterThan(String value) {
            addCriterion("menu_group_name >", value, "menuGroupName");
            return (Criteria) this;
        }

        public Criteria andMenuGroupNameGreaterThanOrEqualTo(String value) {
            addCriterion("menu_group_name >=", value, "menuGroupName");
            return (Criteria) this;
        }

        public Criteria andMenuGroupNameLessThan(String value) {
            addCriterion("menu_group_name <", value, "menuGroupName");
            return (Criteria) this;
        }

        public Criteria andMenuGroupNameLessThanOrEqualTo(String value) {
            addCriterion("menu_group_name <=", value, "menuGroupName");
            return (Criteria) this;
        }

        public Criteria andMenuGroupNameLike(String value) {
            addCriterion("menu_group_name like", value, "menuGroupName");
            return (Criteria) this;
        }

        public Criteria andMenuGroupNameNotLike(String value) {
            addCriterion("menu_group_name not like", value, "menuGroupName");
            return (Criteria) this;
        }

        public Criteria andMenuGroupNameIn(List<String> values) {
            addCriterion("menu_group_name in", values, "menuGroupName");
            return (Criteria) this;
        }

        public Criteria andMenuGroupNameNotIn(List<String> values) {
            addCriterion("menu_group_name not in", values, "menuGroupName");
            return (Criteria) this;
        }

        public Criteria andMenuGroupNameBetween(String value1, String value2) {
            addCriterion("menu_group_name between", value1, value2, "menuGroupName");
            return (Criteria) this;
        }

        public Criteria andMenuGroupNameNotBetween(String value1, String value2) {
            addCriterion("menu_group_name not between", value1, value2, "menuGroupName");
            return (Criteria) this;
        }

        public Criteria andMenuGroupOrderIsNull() {
            addCriterion("menu_group_order is null");
            return (Criteria) this;
        }

        public Criteria andMenuGroupOrderIsNotNull() {
            addCriterion("menu_group_order is not null");
            return (Criteria) this;
        }

        public Criteria andMenuGroupOrderEqualTo(Integer value) {
            addCriterion("menu_group_order =", value, "menuGroupOrder");
            return (Criteria) this;
        }

        public Criteria andMenuGroupOrderNotEqualTo(Integer value) {
            addCriterion("menu_group_order <>", value, "menuGroupOrder");
            return (Criteria) this;
        }

        public Criteria andMenuGroupOrderGreaterThan(Integer value) {
            addCriterion("menu_group_order >", value, "menuGroupOrder");
            return (Criteria) this;
        }

        public Criteria andMenuGroupOrderGreaterThanOrEqualTo(Integer value) {
            addCriterion("menu_group_order >=", value, "menuGroupOrder");
            return (Criteria) this;
        }

        public Criteria andMenuGroupOrderLessThan(Integer value) {
            addCriterion("menu_group_order <", value, "menuGroupOrder");
            return (Criteria) this;
        }

        public Criteria andMenuGroupOrderLessThanOrEqualTo(Integer value) {
            addCriterion("menu_group_order <=", value, "menuGroupOrder");
            return (Criteria) this;
        }

        public Criteria andMenuGroupOrderIn(List<Integer> values) {
            addCriterion("menu_group_order in", values, "menuGroupOrder");
            return (Criteria) this;
        }

        public Criteria andMenuGroupOrderNotIn(List<Integer> values) {
            addCriterion("menu_group_order not in", values, "menuGroupOrder");
            return (Criteria) this;
        }

        public Criteria andMenuGroupOrderBetween(Integer value1, Integer value2) {
            addCriterion("menu_group_order between", value1, value2, "menuGroupOrder");
            return (Criteria) this;
        }

        public Criteria andMenuGroupOrderNotBetween(Integer value1, Integer value2) {
            addCriterion("menu_group_order not between", value1, value2, "menuGroupOrder");
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

        public Criteria andCreatePersonIsNull() {
            addCriterion("create_person is null");
            return (Criteria) this;
        }

        public Criteria andCreatePersonIsNotNull() {
            addCriterion("create_person is not null");
            return (Criteria) this;
        }

        public Criteria andCreatePersonEqualTo(String value) {
            addCriterion("create_person =", value, "createPerson");
            return (Criteria) this;
        }

        public Criteria andCreatePersonNotEqualTo(String value) {
            addCriterion("create_person <>", value, "createPerson");
            return (Criteria) this;
        }

        public Criteria andCreatePersonGreaterThan(String value) {
            addCriterion("create_person >", value, "createPerson");
            return (Criteria) this;
        }

        public Criteria andCreatePersonGreaterThanOrEqualTo(String value) {
            addCriterion("create_person >=", value, "createPerson");
            return (Criteria) this;
        }

        public Criteria andCreatePersonLessThan(String value) {
            addCriterion("create_person <", value, "createPerson");
            return (Criteria) this;
        }

        public Criteria andCreatePersonLessThanOrEqualTo(String value) {
            addCriterion("create_person <=", value, "createPerson");
            return (Criteria) this;
        }

        public Criteria andCreatePersonLike(String value) {
            addCriterion("create_person like", value, "createPerson");
            return (Criteria) this;
        }

        public Criteria andCreatePersonNotLike(String value) {
            addCriterion("create_person not like", value, "createPerson");
            return (Criteria) this;
        }

        public Criteria andCreatePersonIn(List<String> values) {
            addCriterion("create_person in", values, "createPerson");
            return (Criteria) this;
        }

        public Criteria andCreatePersonNotIn(List<String> values) {
            addCriterion("create_person not in", values, "createPerson");
            return (Criteria) this;
        }

        public Criteria andCreatePersonBetween(String value1, String value2) {
            addCriterion("create_person between", value1, value2, "createPerson");
            return (Criteria) this;
        }

        public Criteria andCreatePersonNotBetween(String value1, String value2) {
            addCriterion("create_person not between", value1, value2, "createPerson");
            return (Criteria) this;
        }

        public Criteria andLastUpdateTimeIsNull() {
            addCriterion("last_update_time is null");
            return (Criteria) this;
        }

        public Criteria andLastUpdateTimeIsNotNull() {
            addCriterion("last_update_time is not null");
            return (Criteria) this;
        }

        public Criteria andLastUpdateTimeEqualTo(Date value) {
            addCriterion("last_update_time =", value, "lastUpdateTime");
            return (Criteria) this;
        }

        public Criteria andLastUpdateTimeNotEqualTo(Date value) {
            addCriterion("last_update_time <>", value, "lastUpdateTime");
            return (Criteria) this;
        }

        public Criteria andLastUpdateTimeGreaterThan(Date value) {
            addCriterion("last_update_time >", value, "lastUpdateTime");
            return (Criteria) this;
        }

        public Criteria andLastUpdateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("last_update_time >=", value, "lastUpdateTime");
            return (Criteria) this;
        }

        public Criteria andLastUpdateTimeLessThan(Date value) {
            addCriterion("last_update_time <", value, "lastUpdateTime");
            return (Criteria) this;
        }

        public Criteria andLastUpdateTimeLessThanOrEqualTo(Date value) {
            addCriterion("last_update_time <=", value, "lastUpdateTime");
            return (Criteria) this;
        }

        public Criteria andLastUpdateTimeIn(List<Date> values) {
            addCriterion("last_update_time in", values, "lastUpdateTime");
            return (Criteria) this;
        }

        public Criteria andLastUpdateTimeNotIn(List<Date> values) {
            addCriterion("last_update_time not in", values, "lastUpdateTime");
            return (Criteria) this;
        }

        public Criteria andLastUpdateTimeBetween(Date value1, Date value2) {
            addCriterion("last_update_time between", value1, value2, "lastUpdateTime");
            return (Criteria) this;
        }

        public Criteria andLastUpdateTimeNotBetween(Date value1, Date value2) {
            addCriterion("last_update_time not between", value1, value2, "lastUpdateTime");
            return (Criteria) this;
        }

        public Criteria andLastUpdatePersonIsNull() {
            addCriterion("last_update_person is null");
            return (Criteria) this;
        }

        public Criteria andLastUpdatePersonIsNotNull() {
            addCriterion("last_update_person is not null");
            return (Criteria) this;
        }

        public Criteria andLastUpdatePersonEqualTo(String value) {
            addCriterion("last_update_person =", value, "lastUpdatePerson");
            return (Criteria) this;
        }

        public Criteria andLastUpdatePersonNotEqualTo(String value) {
            addCriterion("last_update_person <>", value, "lastUpdatePerson");
            return (Criteria) this;
        }

        public Criteria andLastUpdatePersonGreaterThan(String value) {
            addCriterion("last_update_person >", value, "lastUpdatePerson");
            return (Criteria) this;
        }

        public Criteria andLastUpdatePersonGreaterThanOrEqualTo(String value) {
            addCriterion("last_update_person >=", value, "lastUpdatePerson");
            return (Criteria) this;
        }

        public Criteria andLastUpdatePersonLessThan(String value) {
            addCriterion("last_update_person <", value, "lastUpdatePerson");
            return (Criteria) this;
        }

        public Criteria andLastUpdatePersonLessThanOrEqualTo(String value) {
            addCriterion("last_update_person <=", value, "lastUpdatePerson");
            return (Criteria) this;
        }

        public Criteria andLastUpdatePersonLike(String value) {
            addCriterion("last_update_person like", value, "lastUpdatePerson");
            return (Criteria) this;
        }

        public Criteria andLastUpdatePersonNotLike(String value) {
            addCriterion("last_update_person not like", value, "lastUpdatePerson");
            return (Criteria) this;
        }

        public Criteria andLastUpdatePersonIn(List<String> values) {
            addCriterion("last_update_person in", values, "lastUpdatePerson");
            return (Criteria) this;
        }

        public Criteria andLastUpdatePersonNotIn(List<String> values) {
            addCriterion("last_update_person not in", values, "lastUpdatePerson");
            return (Criteria) this;
        }

        public Criteria andLastUpdatePersonBetween(String value1, String value2) {
            addCriterion("last_update_person between", value1, value2, "lastUpdatePerson");
            return (Criteria) this;
        }

        public Criteria andLastUpdatePersonNotBetween(String value1, String value2) {
            addCriterion("last_update_person not between", value1, value2, "lastUpdatePerson");
            return (Criteria) this;
        }

        public Criteria andStatIsNull() {
            addCriterion("stat is null");
            return (Criteria) this;
        }

        public Criteria andStatIsNotNull() {
            addCriterion("stat is not null");
            return (Criteria) this;
        }

        public Criteria andStatEqualTo(Integer value) {
            addCriterion("stat =", value, "stat");
            return (Criteria) this;
        }

        public Criteria andStatNotEqualTo(Integer value) {
            addCriterion("stat <>", value, "stat");
            return (Criteria) this;
        }

        public Criteria andStatGreaterThan(Integer value) {
            addCriterion("stat >", value, "stat");
            return (Criteria) this;
        }

        public Criteria andStatGreaterThanOrEqualTo(Integer value) {
            addCriterion("stat >=", value, "stat");
            return (Criteria) this;
        }

        public Criteria andStatLessThan(Integer value) {
            addCriterion("stat <", value, "stat");
            return (Criteria) this;
        }

        public Criteria andStatLessThanOrEqualTo(Integer value) {
            addCriterion("stat <=", value, "stat");
            return (Criteria) this;
        }

        public Criteria andStatIn(List<Integer> values) {
            addCriterion("stat in", values, "stat");
            return (Criteria) this;
        }

        public Criteria andStatNotIn(List<Integer> values) {
            addCriterion("stat not in", values, "stat");
            return (Criteria) this;
        }

        public Criteria andStatBetween(Integer value1, Integer value2) {
            addCriterion("stat between", value1, value2, "stat");
            return (Criteria) this;
        }

        public Criteria andStatNotBetween(Integer value1, Integer value2) {
            addCriterion("stat not between", value1, value2, "stat");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table admin_menu_group
     *
     * @mbggenerated do_not_delete_during_merge Thu Aug 28 14:42:12 CST 2014
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table admin_menu_group
     *
     * @mbggenerated Thu Aug 28 14:42:12 CST 2014
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