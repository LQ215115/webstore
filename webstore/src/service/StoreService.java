package service;

import java.sql.SQLException;

import bean.PageBean;
import dao.StoreDao;
import domain.Place;
import domain.Store;

public class StoreService {
	private StoreDao dao = new StoreDao();

	public Store findById(String id) {
		try {
			return dao.findById(id);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public PageBean<Store> findByCategory(String category, int pc, Place place) {
		try {
			return dao.findByCategory(category, pc, place);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public PageBean<Store> findByName(String name, int pc) {
		try {
			return dao.findByName(name, pc);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public PageBean<Store> findAll(int pc) {
		try {
			return dao.findAll(pc);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public PageBean<Store> findByAuthor(String author, int pc) {
		try {
			return dao.findByAuthor(author, pc);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public PageBean<Store> findByPlace(Place Place, int pc) {
		try {
			return dao.findByPlace(Place, pc);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public PageBean<Store> gjFind(String name, String category, String author, int pc) {
		try {
			return dao.gjFind(name, category, author, pc);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public void add(Store store) {
		try {
			dao.add(store);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public void edit(Store store) {
		try {
			dao.edit(store);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public void delete(Store store) {
		try {
			dao.delete(store);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}