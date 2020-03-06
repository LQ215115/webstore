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
import domain.Category;
import cn.itcast.jdbc.TxQueryRunner;

/**
 * 分类持久层
 * @author qdmmy6
 *
 */
public class CategoryDao {
	private QueryRunner qr = new TxQueryRunner();
	
	/*
	 * 把一个Map中的数据映射到Category中
	 */
	private Category toCategory(Map<String,Object> map) {
		/*
		 * map {cid:xx, cname:xx, pid:xx, desc:xx, orderBy:xx}
		 * Category{cid:xx, cname:xx, parent:(cid=pid), desc:xx}
		 */
		Category category = CommonUtils.toBean(map, Category.class);
		String pid = (String)map.get("pid");
		if(pid != null) {//如果父分类ID不为空，
			/*
			 * 使用一个父分类对象来拦截pid,否则映射时将丢失pid
			 * 再把父分类设置给category
			 */
			Category parent = new Category();
			parent.setCid(pid);
			category.setParent(parent);
		}
		return category;
	}
	
	/*
	 * 可以把多个Map(List<Map>)映射成多个Category(List<Category>)
	 */
	private List<Category> toCategoryList(List<Map<String,Object>> mapList) {
		List<Category> categoryList = new ArrayList<Category>();//创建一个空集合
		for(Map<String,Object> map : mapList) {//循环遍历每个Map
			Category c = toCategory(map);//把一个Map转换成一个Category
			categoryList.add(c);//添加到集合中
		}
		return categoryList;//返回集合
	}
	
	/**
	 * 返回所有分类
	 * @return
	 * @throws SQLException 
	 */
	public List<Category> findAll(String id) throws SQLException {
		/*
		 * 1. 查询出所有一级分类
		 */
		String sql = "select * from t_category where pid=? order by orderBy";
		List<Map<String,Object>> mapList = qr.query(sql, new MapListHandler(),id);
		
		List<Category> parents = toCategoryList(mapList);
		
		/*
		 * 2. 循环遍历所有的一级分类，为每个一级分类加载它的二级分类 
		 */
		for(Category parent : parents) {
			// 查询出当前父分类的所有子分类
			List<Category> children = findByParent(parent.getCid());
			// 设置给父分类
			parent.setChildren(children);
		}
		return parents;
	}
	
	/**
	 * 通过父分类查询子分类
	 * @param pid
	 * @return
	 * @throws SQLException 
	 */
	public List<Category> findByParent(String pid) throws SQLException {
		String sql = "select * from t_category where pid=? order by orderBy";
		List<Map<String,Object>> mapList = qr.query(sql, new MapListHandler(), pid);
		return toCategoryList(mapList);
	}

	/**
	 * 添加分类
	 * @param category
	 * @throws SQLException 
	 */
	public void add(Category category) throws SQLException {
		String sql = "insert into t_category(cid,cname,pid,`desc`) values(?,?,?,?)";
		String pid = category.getParent().getCid();
		Object[] params = {category.getCid(), category.getCname(), pid, category.getDesc()};
		qr.update(sql, params);
	}

	/**
	 * 获取所有父分类，但不带子分类的！
	 * @return
	 * @throws SQLException
	 */
	public List<Category> findParents(String storeid) throws SQLException {
		/*
		 * 1. 查询出所有一级分类
		 */
		String sql = "select * from t_category where  pid=? order by orderBy";
		List<Map<String,Object>> mapList = qr.query(sql, new MapListHandler(),storeid);
		
		return toCategoryList(mapList);
	}
	
	/**
	 * 加载分类
	 * 即可加载一级分类，也可加载二级分类
	 * @param cid
	 * @return
	 * @throws SQLException 
	 */
	public Category load(String cid) throws SQLException {
		String sql = "select * from t_category where cid=?";
		return toCategory(qr.query(sql, new MapHandler(), cid));
	}
	
	/**
	 * 修改分类
	 * 即可修改一级分类，也可修改二级分类
	 * @param category
	 * @throws SQLException 
	 */
	public void edit(Category category) throws SQLException {
		String sql = "update t_category set cname=?, pid=?, `desc`=? where cid=?";
		String pid = category.getParent().getCid();
		Object[] params = {category.getCname(), pid, category.getDesc(), category.getCid()};
		qr.update(sql, params);
	}
	/**
	 * 删除分类
	 * @param cid
	 * @throws SQLException 
	 */
	public void delete(String cid) throws SQLException {
		String sql = "delete from t_category where cid=?";
		qr.update(sql, cid);
	}

	/**
	 * 查询指定父分类下子分类的个数
	 * @param pid
	 * @return
	 * @throws SQLException 
	 */
	public int findChildrenCountByParent(String pid) throws SQLException {
		String sql = "select count(*) from t_category where pid=?";
		Number cnt = (Number)qr.query(sql, new ScalarHandler(), pid);
		return cnt == null ? 0 : cnt.intValue();//为真返回0，否则返回数量
	}
}
