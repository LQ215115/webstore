package service;

import java.sql.SQLException;
import java.util.List;

import javax.management.RuntimeErrorException;

import dao.CategoryDao;
import domain.Category;
import domain.Food;

/**
 * 分类模块业务层
 * 
 * @author qdmmy6
 *
 */
public class CategoryService {
	private CategoryDao categoryDao = new CategoryDao();

	/**
	 * 查询所有分类
	 * 
	 * @return
	 */
	public List<Category> findAll(String id) {
		try {
			return categoryDao.findAll(id);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 添加分类
	 * 
	 * @param category
	 */
	public void add(Category category) {
		try {
			categoryDao.add(category);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 获取所有父分类，不带子分类
	 * 
	 * @return
	 */
	public List<Category> findParents(String storeid) {
		try {
			return categoryDao.findParents(storeid);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 修改分类
	 * 
	 * @param category
	 */
	public void edit(Category category) {
		try {
			categoryDao.edit(category);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 加载分类
	 * 
	 * @param cid
	 * @return
	 */
	public Category load(String cid) {
		try {
			return categoryDao.load(cid);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 删除分类
	 * 
	 * @param cid
	 */
	public void delete(String cid) {
		try {
			categoryDao.delete(cid);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 查询指定父分类下子分类的个数
	 * 
	 * @param pid
	 * @return
	 */
	public int findChildrenCountByParent(String pid) {
		try {
			return categoryDao.findChildrenCountByParent(pid);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public List<Category> findChildren(String pid) {
		try {
			return categoryDao.findByParent(pid);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}