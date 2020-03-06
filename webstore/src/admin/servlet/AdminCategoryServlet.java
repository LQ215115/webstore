package admin.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import admin.domain.Admin;
import cn.itcast.commons.CommonUtils;
import cn.itcast.servlet.BaseServlet;
import domain.Category;
import service.CategoryService;
import service.FoodService;

public class AdminCategoryServlet extends BaseServlet {
	private CategoryService categoryservice = new CategoryService();
	private FoodService foodService = new FoodService();

	public String findAll(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<Category> parents = categoryservice.findAll(req.getParameter("id"));
		req.setAttribute("parents", parents);
		req.setAttribute("id", req.getParameter("id"));
		return "f:/admin/jsp/categorylist.jsp";
	}

	/**
	 * 添加一级分类
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String addParent(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		/*
		 * 1. 封装表单数据到Category中 2. 调用service的add()方法完成添加 3.
		 * 调用findAll()，返回list.jsp显示所有分类
		 */
		Category category = CommonUtils.toBean(req.getParameterMap(), Category.class);
		category.setCid(CommonUtils.uuid());// 设置cid
		// 手动映射pid
		String pid = req.getParameter("id");
		Category parent = new Category();
		parent.setCid(pid);
		category.setParent(parent);
		categoryservice.add(category);
		return findAll(req, resp);
	}

	/**
	 * 添加第二分类：第2步
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String addChild(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		/*
		 * 1. 封装表单数据到Category中 2. 需要手动的把表单中的pid映射到child对象中 2.
		 * 调用service的add()方法完成添加 3. 调用findAll()，返回list.jsp显示所有分类
		 */
		Category child = CommonUtils.toBean(req.getParameterMap(), Category.class);
		child.setCid(CommonUtils.uuid());// 设置cid

		// 手动映射pid
		String pid = req.getParameter("pid");
		Category parent = new Category();
		parent.setCid(pid);
		child.setParent(parent);
		categoryservice.add(child);
		return findAll(req, resp);
	}

	/**
	 * 添加第二分类：第一步
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String addChildPre(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String pid = req.getParameter("pid");// 当前点击的父分类id
		String storeid = req.getParameter("id");
		List<Category> parents = categoryservice.findParents(storeid);
		req.setAttribute("pid", pid);
		req.setAttribute("parents", parents);
		req.setAttribute("id", storeid);
		return "f:/admin/jsp/add2.jsp";
	}

	/**
	 * 修改一级分类：第一步
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String editParentPre(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		/*
		 * 1. 获取链接中的cid 2. 使用cid加载Category 3. 保存Category 4.
		 * 转发到edit.jsp页面显示Category
		 */
		String id = req.getParameter("id");// 商店ID
		String cid = req.getParameter("cid");
		Category parent = categoryservice.load(cid);
		req.setAttribute("parent", parent);
		req.setAttribute("id", id);
		return "f:/admin/jsp/edit.jsp";
	}

	/**
	 * 修改一级分类：第二步
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String editParent(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		/*
		 * 1. 封装表单数据到Category中 2. 调用service方法完成修改 3. 转发到list.jsp显示所有分类（return
		 * findAll();）
		 */
		Category category = CommonUtils.toBean(req.getParameterMap(), Category.class);
		Category parent = new Category();
		parent.setCid(req.getParameter("id"));
		category.setParent(parent);
		categoryservice.edit(category);
		return findAll(req, resp);
	}

	/**
	 * 修改二级分类：第一步
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String editChildPre(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		/*
		 * 1. 获取链接参数cid，通过cid加载Category，保存之 2. 查询出所有1级分类，保存之 3. 转发到edit2.jsp
		 */
		String id = req.getParameter("id");// 商店ID
		String cid = req.getParameter("cid");
		Category child = categoryservice.load(cid);
		req.setAttribute("child", child);
		req.setAttribute("id", id);
		req.setAttribute("parents", categoryservice.findParents(id));

		return "f:/admin/jsp/edit2.jsp";
	}

	/**
	 * 修改二级分类：第二步
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String editChild(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		/*
		 * 1. 封装表单参数到Category child 2. 把表单中的pid封装到child, ... 3.
		 * 调用service.edit()完成修改 4. 返回到list.jsp
		 */
		Category child = CommonUtils.toBean(req.getParameterMap(), Category.class);
		String pid = req.getParameter("pid");
		Category parent = new Category();
		parent.setCid(pid);
		child.setParent(parent);

		categoryservice.edit(child);
		return findAll(req, resp);
	}

	/**
	 * 删除一级分类
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String deleteParent(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		/*
		 * 1. 获取链接参数cid，它是一个1级分类的id 2. 通过cid，查看该父分类下子分类的个数 3.
		 * 如果大于零，说明还有子分类，不能删除。保存错误信息，转发到msg.jsp 4. 如果等于零，删除之，返回到list.jsp
		 */
		String cid = req.getParameter("cid");
		int cnt = categoryservice.findChildrenCountByParent(cid);
		if (cnt > 0) {
			req.setAttribute("msg", "该分类下还有子分类，不能删除！");
			return "f:/admin/msg.jsp";
		} else {
			categoryservice.delete(cid);
			return findAll(req, resp);
		}
	}

	/**
	 * 删除2级分类
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String deleteChild(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		/*
		 * 1. 获取cid，即2级分类id 2. 获取该分类下的图书个数 3. 如果大于零，保存错误信息，转发到msg.jsp 4.
		 * 如果等于零，删除之，返回到list.jsp
		 */
		String cid = req.getParameter("cid");
		int cnt = foodService.findFoodCountByCategory(cid);
		if (cnt > 0) {
			req.setAttribute("msg", "该分类下还存在食品实体，不能删除！");
			return "f:/admin/msg.jsp";
		} else {
			categoryservice.delete(cid);
			return findAll(req, resp);
		}
	}
}