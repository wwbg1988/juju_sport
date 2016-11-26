package com.juju.sport.user.constants;
/** 
 * @author 作者 E-mail:jam_yin@ssic.cn 
 * @version 创建时间：2015年5月13日 上午10:14:48 
 * 类说明 
 */
public enum TeamTypeEnum {
    CAPTAIN(1,"队长"),
    NORMAL(0,"普通队员"),
    VICECAPTAIN(2,"副队长");

    private int index;

    private String typeName;

    TeamTypeEnum(int index, String typeName) {
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
