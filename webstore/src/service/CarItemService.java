package service;

import java.sql.SQLException;
import java.util.List;

import cn.itcast.commons.CommonUtils;
import dao.CarItemDao;
import domain.CarItem;

public class CarItemService {
	CarItemDao carItemDao = new CarItemDao();

	public List<CarItem> myCarItem(String uid) {
		try {
			return carItemDao.findByUser(uid);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public CarItem updateQuantity(String carItemId, int quantity) {
		try {
			carItemDao.updateQuantity(carItemId, quantity);
			return carItemDao.findByCarItemId(carItemId);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public CarItem findByUidAndFid(String uid, String fid) {
		try {
			return carItemDao.findByUidAndFid(uid, fid);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public void addCartItem(CarItem carItem) {
		try {
				carItemDao.addCartItem(carItem);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
/**
 * 减少数量或删除条目
 * @param uid
 * @param fid
 */
	public void moveCartItem(String uid, String fid) {
		try {
				CarItem caritem = findByUidAndFid(uid, fid);
				if (!(caritem == null)) {
				if ((caritem.getQuantity() - 1) == 0) {//执行后数量为0则删除记录
					carItemDao.batchDelete(caritem.getCarItemId());
				} else {
					carItemDao.updateQuantity(caritem.getCarItemId(), caritem.getQuantity() - 1);
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	public void batchDelete(String carItemIds){
		try {
			carItemDao.batchDelete(carItemIds);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	

	/*
	 * 加载多个CartItem
	 */
	public List<CarItem> loadCarItems(String carItemIds) {
		try {
			return carItemDao.loadCarItems(carItemIds);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
