package dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import cn.itcast.jdbc.TxQueryRunner;
import domain.Place;
import domain.User;

public class PlaceDao {
	private QueryRunner qr = new TxQueryRunner();

	public List<Map<String, Object>> findSheng() throws SQLException{
		String sql = "select pname from t_place where paid=0";
		List<Map<String, Object>> place =qr.query(sql, new MapListHandler());
		return place;

	}

	public List<Map<String, Object>> findShi(String paid) throws SQLException {
		String sql = "select pname from t_place where paid=?";
		List<Map<String, Object>> place =qr.query(sql, new MapListHandler(),paid);
		return place;
	}
	
	public List<Map<String, Object>> findQu(String paid) throws SQLException {
		String sql = "select pname from t_place where paid=?";
		List<Map<String, Object>> place =qr.query(sql, new MapListHandler(),paid);
		return place;
	}
	public String findPaid(String pname)throws SQLException{
		String sql="select * from t_place where pname=?";
		return (String) qr.query(sql,new ScalarHandler(),pname);
	}
}
