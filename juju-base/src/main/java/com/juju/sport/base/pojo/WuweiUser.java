package com.juju.sport.base.pojo;

public class WuweiUser {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column wuwei_user.id
     *
     * @mbggenerated Fri Jun 05 10:41:54 CST 2015
     */
    private String id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column wuwei_user.name
     *
     * @mbggenerated Fri Jun 05 10:41:54 CST 2015
     */
    private String name;
    
    private String age;
    private String address;
    

    /**
     * @return the age
     */
    public String getAge()
    {
        return age;
    }

    /**   
     * @param age the age to set   
     */
    public void setAge(String age)
    {
        this.age = age;
    }

    /**
     * @return the address
     */
    public String getAddress()
    {
        return address;
    }

    /**   
     * @param address the address to set   
     */
    public void setAddress(String address)
    {
        this.address = address;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column wuwei_user.id
     *
     * @return the value of wuwei_user.id
     *
     * @mbggenerated Fri Jun 05 10:41:54 CST 2015
     */
    public String getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column wuwei_user.id
     *
     * @param id the value for wuwei_user.id
     *
     * @mbggenerated Fri Jun 05 10:41:54 CST 2015
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column wuwei_user.name
     *
     * @return the value of wuwei_user.name
     *
     * @mbggenerated Fri Jun 05 10:41:54 CST 2015
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column wuwei_user.name
     *
     * @param name the value for wuwei_user.name
     *
     * @mbggenerated Fri Jun 05 10:41:54 CST 2015
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }
}