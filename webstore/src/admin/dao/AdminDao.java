package admin.dao;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import admin.domain.Admin;
import cn.itcast.jdbc.TxQueryRunner;

public class AdminDao {
	private QueryRunner qr=new TxQueryRunner();

	public Admin find(String adminname,String adminpwd) throws SQLException{
		String sql="select * from t_admin where adminname=? and adminpwd=?";
		return qr.query(sql, new BeanHandler<Admin>(Admin.class),adminname,adminpwd);
	}
}
