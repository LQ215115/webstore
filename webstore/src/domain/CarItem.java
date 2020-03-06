package domain;

import java.math.BigDecimal;

public class CarItem {
private String carItemId;//主键
private Food food;//食物对象
private User user;//用户
private int quantity;//数量

public double getSubtotal(){
	BigDecimal b1=new BigDecimal(food.getCurrPrice()+"");//加字符串让其变成字符串
	BigDecimal b2=new BigDecimal(quantity+"");
	BigDecimal b3=b1.multiply(b2);
	return b3.doubleValue();
}

public String getCarItemId() {
	return carItemId;
}
public void setCarItemId(String carItemId) {
	this.carItemId = carItemId;
}
public Food getFood() {
	return food;
}
public void setFood(Food food) {
	this.food = food;
}
public User getUser() {
	return user;
}
public void setUser(User user) {
	this.user = user;
}
public int getQuantity() {
	return quantity;
}
public void setQuantity(int quantity) {
	this.quantity = quantity;
}


}
