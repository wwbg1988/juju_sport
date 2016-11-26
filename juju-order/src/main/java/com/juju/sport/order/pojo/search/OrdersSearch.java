package com.juju.sport.order.pojo.search;

import java.util.Date;

import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldIndex;
import org.springframework.data.elasticsearch.annotations.FieldType;

import lombok.Getter;
import lombok.Setter;

@Document(indexName = "orders",type = "orders")
public class OrdersSearch {

	@Getter
	@Setter
	private String id;
	
	@Field(type=FieldType.String, index=FieldIndex.not_analyzed)
	@Getter
	@Setter
	private String orderNo;
	
	@Field(type=FieldType.String, index=FieldIndex.not_analyzed)
	@Getter
	@Setter
	private String telephone;
	
	@Getter
	@Setter
	private Integer orderTotal;
	
	@Getter
	@Setter
	private int orderStatus;
	
	@Field(type=FieldType.String, index=FieldIndex.not_analyzed)
	@Getter
	@Setter
	private String orderStatusName;
	
	@Getter
	@Setter
	private int paymentStatus;
	
	@Field(type=FieldType.String, index=FieldIndex.not_analyzed)
	@Getter
	@Setter
	private String paymentStatusName;
	
	@Getter
	@Setter
	private String userAccountId;
	
	@Field(type=FieldType.String, index=FieldIndex.not_analyzed)
	@Getter
	@Setter
	private String userAccount;
	
	@Getter
	@Setter
	private String ownerAccountId;
	
	@Field(type=FieldType.String, index=FieldIndex.not_analyzed)
	@Getter
	@Setter
	private String ownerAccount;
	
	@Getter
	@Setter
	private Date orderTime;
	
	@Getter
	@Setter
	private Date createTime;
	
	@Getter
	@Setter
	private Date lastUpdateTime;
	
	@Getter
	@Setter
	private Integer stat;
   
}
