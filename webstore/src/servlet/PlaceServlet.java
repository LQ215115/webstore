package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.servlet.BaseServlet;
import domain.Place;
import service.PlaceService;

public class PlaceServlet extends BaseServlet {
	private PlaceService placeService = new PlaceService();

	public String findPlace(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<Map<String, Object>> result = placeService.findPlace();
		// 转json格式传回去
		String jsonData = result.toString().replace("{", "{\"").replace("}", "\"}").replace("=", "\":\"")
				.replace(", ", "\",\"").replace("}\",\"{", "},{");
		resp.getWriter().print(jsonData);
		return null;
	}

	public String findShi(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String pname = req.getParameter("pname");
		String paid = placeService.findPaid(pname);
		List<Map<String, Object>> result = placeService.findShi(paid);
		// 转json格式传回去
		String jsonData = result.toString().replace("{", "{\"").replace("}", "\"}").replace("=", "\":\"")
				.replace(", ", "\",\"").replace("}\",\"{", "},{");
		resp.getWriter().print(jsonData);
		return null;
	}

	public String findQu(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String pname = req.getParameter("pname");
		String paid = placeService.findPaid(pname);
		List<Map<String, Object>> result = placeService.findQu(paid);
		// 转json格式传回去
		String jsonData = result.toString().replace("{", "{\"").replace("}", "\"}").replace("=", "\":\"")
				.replace(", ", "\",\"").replace("}\",\"{", "},{");
		resp.getWriter().print(jsonData);
		return null;
	}

}
