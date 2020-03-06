package admin.servlet;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.commons.CommonUtils;
import cn.itcast.servlet.BaseServlet;
import domain.Category;
import domain.Food;
import service.CategoryService;
import service.FoodService;

public class AdminFoodServlet extends BaseServlet {
	private FoodService foodService = new FoodService();
	private CategoryService categoryService = new CategoryService();

	/**
	 * 右边部分调用，加载食品列表
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	public String findAll(HttpServletRequest req, HttpServletResponse resp) {
		String sid = req.getParameter("id");
		req.getSession().setAttribute("sid", sid);
		req.setAttribute("foods", foodService.findAll(sid));
		req.setAttribute("id", sid);
		return "f:/admin/jsp/foodlist.jsp";
	}

	/**
	 * 左边调用，加载分类条
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	public String findLeft(HttpServletRequest req, HttpServletResponse resp) {
		String sid = req.getParameter("id");
		req.setAttribute("parents", categoryService.findAll(sid));
		req.setAttribute("id", sid);
		return "f:/admin/jsp/left.jsp";
	}

	public String findByCategory(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String sid = req.getParameter("id");
		String cid = req.getParameter("cid");
		req.setAttribute("foods", foodService.findByCategory(sid, cid));
		req.setAttribute("id", sid);
		return "f:/admin/jsp/foodlist.jsp";

	}
/**
 * 修改食品信息时调用
 * @param req
 * @param resp
 * @return
 */
	public String findByFid(HttpServletRequest req, HttpServletResponse resp) {
		Food food = foodService.findByFid(req.getParameter("fid"));
		req.setAttribute("food", food);
		String sid = req.getParameter("sid");
		req.setAttribute("parents", categoryService.findParents(sid));
		/*
		 * 3. 获取当前图书所属的一级分类下所有2级分类
		 */
		String pid = food.getCategory().getParent().getCid();
		req.setAttribute("children", categoryService.findChildren(pid));

		return "f:/admin/jsp/desc.jsp";

	}

	/**
	 * 添加food：第一步
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String addPre(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		/*
		 * 1. 获取所有一级分类，保存之 2. 转发到add.jsp，该页面会在下拉列表中显示所有一级分类
		 */
		List<Category> parents = categoryService.findAll(req.getParameter("id"));
		req.setAttribute("parents", parents);
		return "f:/admin/jsp/addfood.jsp";
	}

	public String ajaxFindChildren(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		/*
		 * 1. 获取pid 2. 通过pid查询出所有2级分类 3. 把List<Category>转换成json，输出给客户端
		 */
		String pid = req.getParameter("pid");
		List<Category> children = categoryService.findChildren(pid);
		String json = toJson(children);
		resp.getWriter().print(json);
		return null;
	}
	/**
	 * 修改食品
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String edit(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		/*
	 * 1. 把表单数据封装到food对象中
		 * 2. 封装cid到Category中
		 * 3. 把Category赋给food
		 * 4. 调用service完成工作
		 * 5. 保存成功信息，转发到msg.jsp
		 */
		Map map = req.getParameterMap();
		Food food = CommonUtils.toBean(map, Food.class);
		Category category = CommonUtils.toBean(map, Category.class);
		food.setCategory(category);
		
		foodService.edit(food);
		req.setAttribute("msg", "修改商品成功！");
		return "f:/admin/msg.jsp";
	}
	/**
	 * 删除商品
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String delete(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String fid = req.getParameter("fid");
		
		/*
		 * 删除图片
		 */
		Food food = foodService.findByFid(fid);
		String savepath = this.getServletContext().getRealPath("/");//获取真实的路径
		new File(savepath,food.getImg()).delete();//删除文件
		
		foodService.delete(food);//删除数据库的记录
		
		req.setAttribute("msg", "删除商品成功！");
		return "f:/admin/msg.jsp";
	}
	// {"cid":"fdsafdsa", "cname":"fdsafdas"}
	private String toJson(Category category) {
		StringBuilder sb = new StringBuilder("{");
		sb.append("\"cid\"").append(":").append("\"").append(category.getCid()).append("\"");
		sb.append(",");
		sb.append("\"cname\"").append(":").append("\"").append(category.getCname()).append("\"");
		sb.append("}");
		return sb.toString();
	}

	// [{"cid":"fdsafdsa", "cname":"fdsafdas"}, {"cid":"fdsafdsa",
	// "cname":"fdsafdas"}]
	private String toJson(List<Category> categoryList) {
		StringBuilder sb = new StringBuilder("[");
		for (int i = 0; i < categoryList.size(); i++) {
			sb.append(toJson(categoryList.get(i)));
			if (i < categoryList.size() - 1) {
				sb.append(",");
			}
		}
		sb.append("]");
		return sb.toString();
	}
}
