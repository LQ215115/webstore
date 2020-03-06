package dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import cn.itcast.commons.CommonUtils;
import cn.itcast.jdbc.TxQueryRunner;
import domain.Category;
import domain.Food;

public class FoodDao {
	private QueryRunner qr = new TxQueryRunner();
	
	public List<Food> findAll(String sid) throws SQLException {
		String sql = "select * from t_food where sid=?";
		List<Map<String,Object>> mapList = qr.query(sql, new MapListHandler(),sid);
		List<Food> list=toFoodList(mapList);
		return list;
	}
	
	public List<Food> findByCategory(String sid,String cid) throws SQLException {
		String sql = "select * from t_food where sid=? and cid=?";
		List<Map<String,Object>> mapList = qr.query(sql, new MapListHandler(),sid,cid);
		List<Food> beanList=toFoodList(mapList);
		return beanList;
	}
	/**
	 * 1.2级分类都映射在food中
	 * @param fid
	 * @return
	 * @throws SQLException
	 */
	public Food findByFid(String fid) throws SQLException{
		String sql = "select * from t_food f,t_category c where f.cid=c.cid and f.fid=?";
		// 一行记录中，包含了很多的属性，还有一个cid属性
				Map<String,Object> map = qr.query(sql, new MapHandler(), fid);
				// 把Map中除了cid以外的其他属性映射到Book对象中
				Food food= CommonUtils.toBean(map, Food.class);
				// 把Map中cid属性映射到Category中，即这个Category只有cid
				Category category = CommonUtils.toBean(map, Category.class);
				// 两者建立关系
				food.setCategory(category);
				
				// 把pid获取出来，创建一个Category parnet，把pid赋给它，然后再把parent赋给category
				if(map.get("pid") != null) {
					Category parent = new Category();
					parent.setCid((String)map.get("pid"));
					category.setParent(parent);
				}
				return food;
	}
	/**
	 * 查询指定分类下food的个数
	 * @param cid
	 * @return
	 * @throws SQLException
	 */
	public int findFoodCountByCategory(String cid) throws SQLException {
		String sql = "select count(*) from t_food where cid=?";
		Number cnt = (Number)qr.query(sql, new ScalarHandler(), cid);
		return cnt == null ? 0 : cnt.intValue();
	}
	/**
	 * 添加食品
	 * @param book
	 * @throws SQLException 
	 */
	public void add(Food food) throws SQLException {
		String sql = "insert into t_food(fid,fname,price,currPrice," +
				"discount,cid,img,sid,publishtime)" +
				" values(?,?,?,?,?,?,?,?,?)";
		Object[] params = {food.getFid(),food.getFname(),food.getPrice(),
				food.getCurrPrice(),food.getDiscount(),food.getCategory().getCid(),
				food.getImg(),food.getSid(),food.getPublishtime()};
		qr.update(sql, params);
	}
	/**
	 * 编辑食品
	 * @param book
	 * @throws SQLException 
	 */
	public void edit(Food food) throws SQLException {
		String sql = "update t_food set fname=?,price=?,currPrice=?," +
				"discount=?,cid=?,publishtime=? where fid=?";
		Object[] params = {food.getFname(),food.getPrice(),
				food.getCurrPrice(),food.getDiscount(),food.getCategory().getCid(),
				food.getPublishtime(),food.getFid()};
		qr.update(sql, params);
	}
	/**
	 * 删除食品
	 * @param book
	 * @throws SQLException 
	 */
	public void delete(Food food) throws SQLException {
		String sql = "delete from t_food where fid=?";
		qr.update(sql,food.getFid());
	}
	private Food toFood(Map<String,Object> map) {
		Food food = CommonUtils.toBean(map, Food.class);//cid无法映射，映射到category后设置给food
		Category category=CommonUtils.toBean(map, Category.class);
		food.setCategory(category);
		return food;
	}
	
	private List<Food> toFoodList(List<Map<String,Object>> mapList) {
		List<Food> foodList = new ArrayList<Food>();//创建一个空集合
		for(Map<String,Object> map : mapList) {//循环遍历每个Map
			Food c = toFood(map);//把一个Map转换成一个Store
			foodList.add(c);//添加到集合中
		}
		return foodList;//返回集合
	}
}
