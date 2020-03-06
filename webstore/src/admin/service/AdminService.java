package admin.service;

import java.sql.SQLException;

import admin.dao.AdminDao;
import admin.domain.Admin;

public class AdminService {
	private AdminDao admindao=new AdminDao();
	
	public Admin login(Admin admin){
		try {
			return admindao.find(admin.getAdminname(), admin.getAdminpwd());
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
