package dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;

import cn.itcast.commons.CommonUtils;
import cn.itcast.jdbc.TxQueryRunner;
import domain.CarItem;
import domain.Food;
import domain.User;

public class CarItemDao {
	private QueryRunner qr1 = new TxQueryRunner();
	
	private QueryRunner qr = new TxQueryRunner();

	/**
	 * 查询单条记录
	 * @param uid
	 * @param fid
	 * @return
	 * @throws SQLException
	 */
	public CarItem findByUidAndFid(String uid, String fid) throws SQLException {
		String sql = "select * from t_caritem where uid=? and fid=?";
		Map<String,Object> map = qr1.query(sql, new MapHandler(), uid, fid);
		CarItem carItem = toCarItem(map);
		return carItem;
	}
	
	/**
	 * 修改指定条目的数量
	 * @param cartItemId
	 * @param quantity
	 * @throws SQLException 
	 */
	public void updateQuantity(String carItemId, int quantity) throws SQLException {
		String sql = "update t_caritem set quantity=? where carItemId=?";
		qr1.update(sql, quantity, carItemId);
	}
	
	/**
	 * 添加条目
	 * @param cartItem
	 * @throws SQLException 
	 */
	public void addCartItem(CarItem carItem) throws SQLException {
		String sql = "insert into t_caritem(carItemId, quantity, fid, uid)" +
				" values(?,?,?,?)";
		Object[] params = {carItem.getCarItemId(), carItem.getQuantity(),
				carItem.getFood().getFid(), carItem.getUser().getUid()};
		qr1.update(sql, params);
	}
	
	/**
	 * 批量删除
	 * @param cartItemIds
	 * @throws SQLException
	 */
	public void batchDelete(String carItemIds) throws SQLException {
		/*
		 * 需要先把cartItemIds转换成数组
		 * 1. 把cartItemIds转换成一个where子句
		 * 2. 与delete from 连接在一起，然后执行之
		 */
		Object[] cartItemIdArray = carItemIds.split(",");
		String whereSql = toWhereSql(cartItemIdArray.length);
		String sql = "delete from t_caritem where " + whereSql;
		qr.update(sql, cartItemIdArray);//其中cartItemIdArray必须是Object类型的数组！
	}
	
	/*
	 * 用来生成where子句
	 */
	private String toWhereSql(int len) {
		StringBuilder sb = new StringBuilder("carItemId in(");
		for(int i = 0; i < len; i++) {
			sb.append("?");
			if(i < len - 1) {
				sb.append(",");
			}
		}
		sb.append(")");
		return sb.toString();
	}
	/*
	 * 把一个Map映射成一个Caritem
	 */
	private CarItem toCarItem(Map<String,Object> map) {
		if(map == null || map.size() == 0) return null;
		CarItem carItem = CommonUtils.toBean(map, CarItem.class);
		Food food = CommonUtils.toBean(map, Food.class);
		User user = CommonUtils.toBean(map, User.class);
		carItem.setFood(food);
		carItem.setUser(user);
		return carItem;
	}
	
	/*
	 * 把多个Map(List<Map>)映射成多个CartItem(List<CartItem>)
	 */
	private List<CarItem> toCarItemList(List<Map<String,Object>> mapList) {
		List<CarItem> carItemList = new ArrayList<CarItem>();
		for(Map<String,Object> map : mapList) {
			CarItem carItem = toCarItem(map);
			carItemList.add(carItem);
		}
		return carItemList;
	}
	
	/**
	 * 通过用户查询购物车条目
	 * @param uid
	 * @return
	 * @throws SQLException 
	 */
	public List<CarItem> findByUser(String uid) throws SQLException {
		String sql = "select * from t_caritem c, t_food f where c.fid=f.fid and uid=? order by c.orderBy";
		List<Map<String,Object>> mapList = qr1.query(sql, new MapListHandler(), uid);
		return toCarItemList(mapList);
	}
	/**
	 * 按id查询
	 * @param cartItemId
	 * @return
	 * @throws SQLException 
	 */
	public CarItem findByCarItemId(String carItemId) throws SQLException {
		String sql = "select * from t_carItem c, t_food f where c.fid=f.fid and c.carItemId=?";
		Map<String,Object> map = qr.query(sql, new MapHandler(), carItemId);
		return toCarItem(map);
	}
	
	/**
	 * 加载多个CartItem
	 * @param cartItemIds
	 * @return
	 * @throws SQLException 
	 */
	public List<CarItem> loadCarItems(String carItemIds) throws SQLException {
		/*
		 * 1. 把cartItemIds转换成数组
		 */
		Object[] carItemIdArray = carItemIds.split(",");
		/*
		 * 2. 生成wehre子句
		 */
		String whereSql = toWhereSql(carItemIdArray.length);
		/*
		 * 3. 生成sql语句
		 */
		String sql = "select * from t_caritem c, t_food f where c.fid=f.fid and " + whereSql;
		/*
		 * 4. 执行sql，返回List<CartItem>
		 */
		return toCarItemList(qr.query(sql, new MapListHandler(), carItemIdArray));
	}
}
