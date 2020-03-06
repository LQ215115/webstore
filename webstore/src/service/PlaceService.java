package service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import dao.PlaceDao;

public class PlaceService {
	private PlaceDao placeDao = new PlaceDao();

	public List<Map<String, Object>> findPlace() {
		try {
			return placeDao.findSheng();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public List<Map<String, Object>> findShi(String paid) {
		try {
			return placeDao.findShi(paid);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public List<Map<String, Object>> findQu(String paid) {
		try {
			return placeDao.findShi(paid);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	public String findPaid(String pname){
		try {
			return placeDao.findPaid(pname);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
	}
}
