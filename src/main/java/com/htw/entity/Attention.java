package com.htw.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "WX_ATTENTION")
public class Attention {

	private String id;
	private String openId;
	private Double amount;
	private Date createTime;
	private Date updateTime;
	private String win; //1中奖，0未中奖
	
	public static String STATUS_WIN = "1" ;
	public static String STATUS_NOTWIN = "0" ;
	
	@Id
	@Column(name="id",nullable=false,length = 32)
	@GeneratedValue(generator="hibernate-uuid")
	@GenericGenerator(name = "hibernate-uuid", strategy = "uuid")
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	@Column(name = "open_id")
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	@Column(name = "amount")
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	@Column(name = "create_time")
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	@Column(name = "update_time")
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	@Column(name = "win")
	public String getWin() {
		return win;
	}
	public void setWin(String win) {
		this.win = win;
	}
}
