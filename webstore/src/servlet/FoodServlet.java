package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.servlet.BaseServlet;
import domain.Category;
import domain.Food;
import service.FoodService;

public class FoodServlet extends BaseServlet {
	private FoodService foodService = new FoodService();

	public String findAll(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String sid=req.getParameter("id");
		req.setAttribute("foods", foodService.findAll(sid));
		return "f:/foodlist.jsp";

	}
	public String findByCategory(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String sid=req.getParameter("id");
		String cid=req.getParameter("cid");
		req.setAttribute("foods", foodService.findByCategory(sid, cid));
		return "f:/foodlist.jsp";

	}

}
