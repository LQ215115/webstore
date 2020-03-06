package service;

import java.sql.SQLException;
import java.util.List;

import dao.FoodDao;
import domain.Food;
import servlet.FoodServlet;

public class FoodService {
	private FoodDao foodDao = new FoodDao();

	public List<Food> findAll(String sid) {
		try {
			return foodDao.findAll(sid);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public List<Food> findByCategory(String sid,String cid){
		try {
			return foodDao.findByCategory(sid, cid);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	public Food findByFid(String fid){
		try {
			return foodDao.findByFid(fid);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	/**
	 * 返回当前分类下food个数
	 * @param cid
	 * @return
	 */
	public int findFoodCountByCategory(String cid) {
		try {
			return foodDao.findFoodCountByCategory(cid);
		} catch(SQLException e) {
			throw new RuntimeException(e);
		}
	}
	/**
	 * 添加食品
	 * @param food
	 */
	public void add(Food food){
		try {
			 foodDao.add(food);
		} catch(SQLException e) {
			throw new RuntimeException(e);
		}
	}
	public void edit(Food food){
		try {
			 foodDao.edit(food);
		} catch(SQLException e) {
			throw new RuntimeException(e);
		}
	}
	public void delete(Food food){
		try {
			 foodDao.delete(food);
		} catch(SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
