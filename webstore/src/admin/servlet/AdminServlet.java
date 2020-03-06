package admin.servlet;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import admin.domain.Admin;
import admin.service.AdminService;
import bean.PageBean;
import cn.itcast.commons.CommonUtils;
import cn.itcast.servlet.BaseServlet;
import domain.Store;
import service.StoreService;

public class AdminServlet extends BaseServlet {
	private AdminService adminservice=new AdminService();
	private StoreService storeservice=new StoreService();

	/**
	 * 登录功能
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String login(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		/*
		 * 1. 封装表单数据到Admin
		 */
		Admin form = CommonUtils.toBean(req.getParameterMap(), Admin.class);
		Admin admin = adminservice.login(form);
		if(admin == null) {
			req.setAttribute("msg", "用户名或密码错误！");
			return "/admin/login.jsp";
		}
		req.getSession().setAttribute("admin", admin);
		return "r:/admin/jsp/index.jsp";
	}
/**
 * 查询所有店铺
 * @param req
 * @param resp
 * @return
 * @throws ServletException
 * @throws IOException
 */
	public String findStore(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		int pc = getPc(req);
		PageBean<Store> pb = storeservice.findAll(pc);
		pb.setUrl(getUrl(req));
		req.setAttribute("pb", pb);
		return "f:/admin/jsp/storelist.jsp";
	}
	

	private int getPc(HttpServletRequest req) {
		int pc = 1;
		String param = req.getParameter("pc");
		if (param != null && !param.trim().isEmpty()) {
			try {
				pc = Integer.parseInt(param);
			} catch (RuntimeException e) {
			}
		}
		return pc;
	}

	private String getUrl(HttpServletRequest req) {
		String url = req.getRequestURI() + "?" + req.getQueryString();
		/*
		 * 如果url中存在pc参数，截取掉，如果不存在那就不用截取。
		 */
		int index = url.lastIndexOf("&pc=");
		if (index != -1) {
			url = url.substring(0, index);
		}
		return url;
	}
	public String findByAuthor(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int pc = getPc(req);
		PageBean<Store> pb = storeservice.findByAuthor(req.getParameter("author"), pc);
		pb.setUrl(getUrl(req));
		req.setAttribute("pb", pb);
		return "f:/admin/jsp/storelist.jsp";

	}
}
