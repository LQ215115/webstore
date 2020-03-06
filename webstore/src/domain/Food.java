package domain;

public class Food {
private String fid;//主键
private String fname;
private double price;//定价
private double currPrice;//当前价
private double discount;//折扣 
private Category category;//所属食物分类
private String img;//图片路径
private String sid;//所属店铺ID
private String publishtime;//添加时间


public String getSid() {
	return sid;
}
public void setSid(String sid) {
	this.sid = sid;
}
public String getFid() {
	return fid;
}
public void setFid(String fid) {
	this.fid = fid;
}
public String getFname() {
	return fname;
}
public void setFname(String fname) {
	this.fname = fname;
}
public double getPrice() {
	return price;
}
public void setPrice(double price) {
	this.price = price;
}
public double getCurrPrice() {
	return currPrice;
}
public void setCurrPrice(double currPrice) {
	this.currPrice = currPrice;
}
public double getDiscount() {
	return discount;
}
public void setDiscount(double discount) {
	this.discount = discount;
}
public Category getCategory() {
	return category;
}
public void setCategory(Category category) {
	this.category = category;
}

public String getImg() {
	return img;
}
public void setImg(String img) {
	this.img = img;
}
public String getPublishtime() {
	return publishtime;
}
public void setPublishtime(String publishtime) {
	this.publishtime = publishtime;
}

}
