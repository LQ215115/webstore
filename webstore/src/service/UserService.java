package service;

import java.io.IOException;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.management.RuntimeErrorException;

import com.sun.mail.handlers.message_rfc822;

import cn.itcast.commons.CommonUtils;
import cn.itcast.mail.Mail;
import cn.itcast.mail.MailUtils;
import dao.UserDao;
import domain.User;
import exception.UserException;

public class UserService {
	private UserDao userDao = new UserDao();

	// 修改密码
	public void updatePass(String uid, String oldpass, String newpass) throws UserException {
		try {
			boolean bool = userDao.findByuidandloginpass(uid, oldpass);
			if (!bool) {
				throw new UserException("原密码错误!");
			}
			userDao.updatePass(uid, newpass);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	// 登录功能
	public User login(User user) {
		try {
			return userDao.findByloginnameandloginpass(user.getLoginname(), user.getLoginpass());
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public void activation(String activationCode) throws UserException {
		try {
			User user = userDao.findByCode(activationCode);
			if (user == null)
				throw new UserException("无效激活码");
			if (user.isStatus())
				throw new UserException("该用户已激活");
			userDao.upDateStatus(user.getUid(), true);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public boolean ajaxValidateLoginname(String loginname) {
		try {
			return userDao.ajaxValidateLoginname(loginname);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public boolean ajaxValidateEmail(String email) {
		try {
			return userDao.ajaxValidateEmail(email);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public void regist(User user) {
		user.setUid(CommonUtils.uuid());
		user.setStatus(false);
		user.setActivationCode(CommonUtils.uuid() + CommonUtils.uuid());
		try {
			userDao.add(user);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		// 加载配置文件
		Properties prop = new Properties();
		try {
			prop.load(this.getClass().getClassLoader().getResourceAsStream("email_template.properties"));
		} catch (IOException e1) {
			throw new RuntimeException(e1);
		}
		String host = prop.getProperty("host");
		String name = prop.getProperty("username");
		String pass = prop.getProperty("password");
		Session session = MailUtils.createSession(host, name, pass);
		String from = prop.getProperty("from");
		String to = user.getEmail();
		String subject = prop.getProperty("subject");
		// 替换文本中{0}，{1}，{2},替换的个数为参数个数，第一个参数为模板
		String content = MessageFormat.format(prop.getProperty("content"), user.getActivationCode());
		Mail mail = new Mail(from, to, subject, content);
		try {
			MailUtils.send(session, mail);
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
