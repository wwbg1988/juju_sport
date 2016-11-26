package com.juju.sport.base.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

public class MessagesDto implements Serializable{
	
	
	
	@Getter
	@Setter	
    private String id;
	
	@Getter
	@Setter	
    private Integer msgType;

	@Getter
	@Setter	
    private String msgFromId;
	@Getter
	@Setter	

    private String msgToId;

	@Getter
	@Setter	
    private Date msgTime;

	@Getter
	@Setter	
    private String attachment;

	@Getter
	@Setter	
    private Integer msgStatus;

	@Getter
	@Setter	
    private Date createTime;

	@Getter
	@Setter	
    private Date lastUpdateTime;

	@Getter
	@Setter	
    private Integer stat;

	@Getter
	@Setter	
    private String userAccount;

	@Getter
	@Setter	
    private String msgContent;
	
	@Getter
	@Setter
	private Integer msgScore;
	
	@Getter
	@Setter
	private String userImg;
	
	@Getter
	@Setter
	private String showMsgTime;
	
	@Getter
	@Setter
	private Integer msgResource;
	
	//1:系统消息2:投诉场馆3:投诉用户4:投诉建议 5评价消息
	public enum MsgType {
        SYSINFO(1, "系统消息"),
        VENUEINFO(2, "投诉场馆"),
        USERINFO(3, "投诉用户"),
        ADVISEINFO(4, "投诉建议"),
        COMMENTINFO(5, "评价消息");
        
        private int nCode;

        private String description;

        // 构造函数，枚举类型只能为私有
        private MsgType(Integer _nCode, String _description) {
            this.nCode = _nCode;
            this.description = _description;
        }
        
        public int getInfoType() {
            return this.nCode;
        }

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}    
            
    }
	
}