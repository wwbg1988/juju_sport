package com.juju.sport.stadium.constants;

public enum ComplaintEnum {
	SYSCONTENT(1,"系统消息"),
    COMPLAINTSITE(2,"场馆投诉"),
    COMPLAINTUSER(3,"投诉用户"),
    SUGGESTIONS(4,"投诉建议 "),
    INFORMATIONS(5,"评价消息");
    
    private int index;

    private String typeName;

    ComplaintEnum(int index, String typeName) {
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
