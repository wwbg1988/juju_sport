package com.juju.sport.order.pojo.search;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldIndex;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(indexName = "order_itmes",type = "order_itmes")
public class OrderItemsSearch {

	@Setter
	@Getter
	private String id;

	@Field(type=FieldType.String, index=FieldIndex.not_analyzed)
	@Setter
	@Getter
	private String orderId;

	@Field(type=FieldType.String, index=FieldIndex.not_analyzed)
	@Setter
	@Getter
	private String orderNo;

	@Setter
	@Getter
	private Date date;

	@Setter
	@Getter
	private String orderTime;
	
	@Setter
	@Getter
	private String endTime;

	@Setter
	@Getter
	private String spaceId;

	@Field(type=FieldType.String, index=FieldIndex.not_analyzed)
	@Setter
	@Getter
	private String spaceName;

	@Setter
	@Getter
	private String userAccountId;
	
	@Field(type=FieldType.String, index=FieldIndex.not_analyzed)
	@Setter
	@Getter
	private String userAccount;

	@Setter
	@Getter
	private String ownerAccountId;
	
	@Field(type=FieldType.String, index=FieldIndex.not_analyzed)
	@Setter
	@Getter
	private String ownerAccount;

	@Setter
	@Getter
	private String telephone;

	@Setter
	@Getter
	private Integer orderTotal;

	@Setter
	@Getter
	private Date createTime;

	@Setter
	@Getter
	private Date lastUpdateTime;

	@Setter
	@Getter
	private Integer stat;

}
