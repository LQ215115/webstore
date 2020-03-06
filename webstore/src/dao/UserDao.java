package dao;

import java.sql.SQLException;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import cn.itcast.jdbc.TxQueryRunner;
import domain.User;

public class UserDao {
	private QueryRunner qr = new TxQueryRunner();
//检查用户是否被注册
	public boolean ajaxValidateLoginname(String loginname) throws SQLException {
		String sql = "select count(1) from t_user where loginname=?";
		Number number = (Number) qr.query(sql, new ScalarHandler(), loginname);
		return number.intValue() == 0;
	}
//检查邮箱是否被注册
	public boolean ajaxValidateEmail(String email) throws SQLException{
		String sql="select count(1) from t_user where email=?";
		Number number=(Number)qr.query(sql,new ScalarHandler(),email);
		return number.intValue()==0;
	}
	//插入注册用户
	public void add(User user) throws SQLException{
		String sql="insert into t_user values(?,?,?,?,?,?)";
		Object[] params={user.getUid(),user.getLoginname(),user.getLoginpass(),user.getEmail(),user.isStatus(),user.getActivationCode()};
		qr.update(sql, params);
	}
	//激活码找用户
	public User findByCode(String activationCode)throws SQLException{
		String sql="select * from t_user where activationCode=?";
		return qr.query(sql,new BeanHandler<User>(User.class),activationCode);
	}
	//修改状态
	public void upDateStatus(String uid,boolean status) throws SQLException{
		String sql="update t_user set status=?  where uid=?";
		qr.update(sql,status,uid);
	}
	//名字和密码找用户（登录校验）
	public User findByloginnameandloginpass(String loginname,String loginpass) throws SQLException{
		String sql="select * from t_user where loginname=? and loginpass=?";
		return qr.query(sql,new  BeanHandler<User>(User.class),loginname,loginpass);
	}
	//uid loginnpass找用户（改密码）
	public boolean findByuidandloginpass(String uid,String loginpass) throws SQLException{
		String sql="select count(1) from t_user where uid=? and loginpass=?";
		Number number=(Number) qr.query(sql,new ScalarHandler(),uid,loginpass);
		return number.intValue()>0;
	}
	public void updatePass(String uid,String newpass) throws SQLException{
		String sql="update t_user set loginpass=? where uid=?";
		qr.update(sql,newpass,uid);
	}
}
