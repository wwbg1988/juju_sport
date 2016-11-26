package com.juju.sport.home.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

public class BuyDto implements Serializable{

	@Setter
	@Getter
	private int enable = 1;
	
	@Setter
	@Getter
	private String orderId;
	
	@Getter
	@Setter
	private Integer orderTotal;

	@Getter
	@Setter
	private int gotoPay; //0 不到支付页面 1 到支付页面   2 弹出个人还是团队 页面
	
	@Getter
	@Setter
	private Integer status;
	
	@Getter
	@Setter
	private String msg;
	
	@Getter
	@Setter	
	private String payInfo;
	
	//1:系统消息2:投诉场馆3:投诉用户4:投诉建议 5评价消息
	public enum PayType {
        pay(1, "支付"),
        no_pay(2, "不支付"),
        free(3, "免费个人或团队页面");
        
        private int nCode;

        private String description;

        // 构造函数，枚举类型只能为私有
        private PayType(Integer _nCode, String _description) {
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
