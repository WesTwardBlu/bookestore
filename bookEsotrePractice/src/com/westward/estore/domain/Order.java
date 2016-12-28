package com.westward.estore.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Order implements Serializable{
	private static final long serialVersionUID = 1L;

	private String id;//订单id
	private double money;//订单金额
	private String receiverinfo;//收货地址
	private int paystate;//支付状态
	private Date ordertime;//下单时间
	private int user_id;//用户id
	
	//用于订单查询时，可以将用户信息封装到Order类中
	private String username;//用户姓名
	private String nickname;//用户昵称
	
	//订单中包含多个订单项。通过订单查询订单内容(项)
	private List<OrderItem> orderItems;

	public Order() {
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public double getMoney() {
		return money;
	}

	public void setMoney(double money) {
		this.money = money;
	}

	public String getReceiverinfo() {
		return receiverinfo;
	}

	public void setReceiverinfo(String receiverinfo) {
		this.receiverinfo = receiverinfo;
	}

	public int getPaystate() {
		return paystate;
	}

	public void setPaystate(int paystate) {
		this.paystate = paystate;
	}

	public Date getOrdertime() {
		return ordertime;
	}

	public void setOrdertime(Date ordertime) {
		this.ordertime = ordertime;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public List<OrderItem> getOrderItems() {
		return orderItems;
	}

	public void setOrderItems(List<OrderItem> orderItems) {
		this.orderItems = orderItems;
	}
	
	
}
