package dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import bean.PageBean;
import cn.itcast.commons.CommonUtils;
import cn.itcast.jdbc.TxQueryRunner;
import domain.Category;
import domain.Food;
import domain.Place;
import domain.Store;
import expression.Expression;

public class StoreDao {
	private QueryRunner qr = new TxQueryRunner();

	public Store findById(String id) throws SQLException {
		String sql = "select * from t_store where id=?";
		// 一行记录中，包含了很多的store的属性
		Map<String, Object> map = qr.query(sql, new MapHandler(), id);
		return toStore(map);
	}

	/**
	 * 按分类查询
	 * 
	 * @param cid
	 * @param pc
	 * @return
	 * @throws SQLException
	 */
	public PageBean<Store> findByCategory(String category, int pc, Place Place) throws SQLException {
		List<Expression> exprList = new ArrayList<Expression>();
		if (!(Place.getSheng() == null)) {
			exprList.add(new Expression("sheng", "like", "%" + Place.getSheng() + "%"));
			exprList.add(new Expression("shi", "like", "%" + Place.getShi() + "%"));
			exprList.add(new Expression("qu", "like", "%" + Place.getQu() + "%"));
		}
		exprList.add(new Expression("category", "=", category));
		return findByCriteria(exprList, pc);
	}

	/**
	 * 按名模糊查询
	 * 
	 * @param bname
	 * @param pc
	 * @return
	 * @throws SQLException
	 */
	public PageBean<Store> findByName(String name, int pc) throws SQLException {
		List<Expression> exprList = new ArrayList<Expression>();
		exprList.add(new Expression("name", "like", "%" + name + "%"));
		return findByCriteria(exprList, pc);
	}

	public PageBean<Store> findAll(int pc) throws SQLException {
		List<Expression> exprList = new ArrayList<Expression>();
		return findByCriteria(exprList, pc);
	}

	/**
	 * 按作者查
	 * 
	 * @param bname
	 * @param pc
	 * @return
	 * @throws SQLException
	 */
	public PageBean<Store> findByAuthor(String author, int pc) throws SQLException {
		List<Expression> exprList = new ArrayList<Expression>();
		exprList.add(new Expression("author", "like", "%" + author + "%"));
		return findByCriteria(exprList, pc);
	}

	/**
	 * 按地址组合查询
	 * 
	 * @param combination
	 * @param pc
	 * @return
	 * @throws SQLException
	 */
	public PageBean<Store> findByPlace(Place Place, int pc) throws SQLException {
		List<Expression> exprList = new ArrayList<Expression>();
		exprList.add(new Expression("sheng", "like", "%" + Place.getSheng() + "%"));
		exprList.add(new Expression("shi", "like", "%" + Place.getShi() + "%"));
		exprList.add(new Expression("qu", "like", "%" + Place.getQu() + "%"));
		return findByCriteria(exprList, pc);
	}

	/**
	 * 通用的查询方法
	 * 
	 * @param exprList
	 * @param pc
	 * @return
	 * @throws SQLException
	 */
	private PageBean<Store> findByCriteria(List<Expression> exprList, int pc) throws SQLException {
		/*
		 * 1. 得到ps 2. 得到tr 3. 得到beanList 4. 创建PageBean，返回
		 */
		/*
		 * 1. 得到ps
		 */
		int ps = 12;// 每页记录数
		/*
		 * 2. 通过exprList来生成where子句
		 */
		StringBuilder whereSql = new StringBuilder(" where 1=1");
		List<Object> params = new ArrayList<Object>();// SQL中有问号，它是对应问号的值
		for (Expression expr : exprList) {
			/*
			 * 添加一个条件上， 1) 以and开头 2) 条件的名称 3) 条件的运算符，可以是=、!=、>、< ... is null，is
			 * null没有值 4) 如果条件不是is null，再追加问号，然后再向params中添加一与问号对应的值
			 */
			whereSql.append(" and ").append(expr.getName()).append(" ").append(expr.getOperator()).append(" ");
			// where 1=1 and bid = ?
			if (!expr.getOperator().equals("is null")) {
				whereSql.append("?");
				params.add(expr.getValue());
			}
		}

		/*
		 * 3. 总记录数
		 */
		String sql = "select count(*) from t_store" + whereSql;
		Number number = (Number) qr.query(sql, new ScalarHandler(), params.toArray());
		int tr = number.intValue();// 得到了总记录数
		/*
		 * 4. 得到beanList，即当前页记录
		 */
		sql = "select * from t_store" + whereSql + " order by orderby limit ?,?";
		params.add((pc - 1) * ps);// 当前页首行记录的下标
		params.add(ps);// 一共查询几行，就是每页记录数

		List<Map<String, Object>> mapList = qr.query(sql, new MapListHandler(), params.toArray());
		List<Store> beanList = toStoreList(mapList);
		/*
		 * 5. 创建PageBean，设置参数
		 */
		PageBean<Store> pb = new PageBean<Store>();
		/*
		 * 其中PageBean没有url，这个任务由Servlet完成
		 */
		pb.setBeanList(beanList);
		pb.setPc(pc);
		pb.setPs(ps);
		pb.setTr(tr);

		return pb;
	}

	/**
	 * 高级查询
	 */
	public PageBean<Store> gjFind(String name, String category, String author, int pc) throws SQLException {
		List<Expression> exprList = new ArrayList<Expression>();
		if (!(name == null)) {
			exprList.add(new Expression("name", "like", "%" + name + "%"));
		}
		if (!(category == null)) {
			exprList.add(new Expression("category", "like", "%" + category + "%"));
		}
		if (!(author == null)) {
			exprList.add(new Expression("author", "like", "%" + author + "%"));
		}

		return findByCriteria(exprList, pc);
	}

	/**
	 * 将一个map映射为一个store
	 * 
	 * @param map
	 * @return
	 */
	private Store toStore(Map<String, Object> map) {
		Store store = CommonUtils.toBean(map, Store.class);// sheng,shi,qu无法映射到store,先映射到place再赋值给Store
		Place place = CommonUtils.toBean(map, Place.class);
		store.setPlace(place);
		return store;
	}

	/**
	 * 将多个map映射到多个Store
	 * 
	 * @param mapList
	 * @return
	 */
	private List<Store> toStoreList(List<Map<String, Object>> mapList) {
		List<Store> storeList = new ArrayList<Store>();// 创建一个空集合
		for (Map<String, Object> map : mapList) {// 循环遍历每个Map
			Store c = toStore(map);// 把一个Map转换成一个Store
			storeList.add(c);// 添加到集合中
		}
		return storeList;// 返回集合
	}

	/**
	 * 添加店铺
	 * 
	 * @param book
	 * @throws SQLException
	 */
	public void add(Store store) throws SQLException {
		String sql = "insert into t_store(id,name,category,author," + "time,sheng,shi,qu,img,orderby)"
				+ " values(?,?,?,?,?,?,?,?,?,?)";
		Object[] params = { store.getId(), store.getName(), store.getCategory(), store.getAuthor(), store.getTime(),
				store.getPlace().getSheng(), store.getPlace().getShi(), store.getPlace().getQu(), store.getImg(),
				store.getOrderby()};
		qr.update(sql, params);
	}

	/**
	 * 编辑店铺
	 * 
	 * @param book
	 * @throws SQLException
	 */
	public void edit(Store store) throws SQLException {
		String sql = "update t_store set name=?,category=?,author=?," + "time=?,sheng=?,shi=?,qu=?,orderby=? where id=?";
		Object[] params = {store.getName(), store.getCategory(), store.getAuthor(), store.getTime(),
				store.getPlace().getSheng(), store.getPlace().getShi(), store.getPlace().getQu(),
				store.getOrderby(),store.getId(),};
		qr.update(sql, params);
	}

	public void delete(Store store) throws SQLException {
			String sql = "delete from t_store where id=?";
			qr.update(sql,store.getId());
	}
}