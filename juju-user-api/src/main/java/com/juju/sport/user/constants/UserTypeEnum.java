package com.juju.sport.user.constants;

public enum UserTypeEnum {
    NORMALUSER(1,"普通用户"),
    SITEUSER(2,"场地"),
    COACHUSER(3,"教练");

    private int index;

    private String typeName;

    UserTypeEnum(int index, String typeName) {
        this.index = index;
        this.typeName = typeName;
    }

    public int getIndex(){
        return index;
    }
    public String getTypeName(){
        return typeName;
    }
}
