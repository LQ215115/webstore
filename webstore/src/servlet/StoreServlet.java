package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.PageBean;
import cn.itcast.servlet.BaseServlet;
import domain.Place;
import domain.Store;
import service.StoreService;

public class StoreServlet extends BaseServlet {
	private StoreService service = new StoreService();

	public String findByCategory(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		int pc = getPc(req);
		Place place = new Place();
		// 先判断session中是否有地址，有就添加查询条件
		if (!(req.getSession().getAttribute("place") == null)) {
			place = (Place) req.getSession().getAttribute("place");
		}
		PageBean<Store> pb = service.findByCategory(req.getParameter("category"), pc, place);
		pb.setUrl(getUrl(req));
		req.setAttribute("pb", pb);
		return "f:/storelist.jsp";
	}

	/**
	 * 填写地址后调用，根据地址查询店铺
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String findByPlace(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int pc = getPc(req);
		Place place = new Place();
		place.setSheng(req.getParameter("sel1"));
		place.setShi(req.getParameter("sel2"));
		place.setQu(req.getParameter("sel3"));
		req.getSession().setAttribute("place", place);// 保存填写的地址，收货时自动填写该信息
		PageBean<Store> pb = service.findByPlace(place, pc);
		pb.setUrl(getUrl(req));
		req.setAttribute("pb", pb);
		return "f:/storelist.jsp";
	}

	public String findByAuthor(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int pc = getPc(req);
		PageBean<Store> pb = service.findByAuthor(req.getParameter("author"), pc);
		pb.setUrl(getUrl(req));
		req.setAttribute("pb", pb);
		return "f:/storelist.jsp";

	}

	public String findByName(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int pc = getPc(req);
		PageBean<Store> pb = service.findByName(req.getParameter("name"), pc);
		pb.setUrl(getUrl(req));
		req.setAttribute("pb", pb);
		return "f:/storelist.jsp";

	}

	/**
	 * 加载主页和刷新时调用，根据是否填写地址决定显示全部商店还是附近商店
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String findAll(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int pc = getPc(req);
		if (!(req.getSession().getAttribute("place") == null)) {
			Place place = (Place) req.getSession().getAttribute("place");
			PageBean<Store> pb = service.findByPlace(place, pc);
			pb.setUrl(getUrl(req));
			req.setAttribute("pb", pb);
			return "f:/storelist.jsp";
		} else {
			PageBean<Store> pb = service.findAll(pc);
			pb.setUrl(getUrl(req));
			req.setAttribute("pb", pb);
			return "f:/storelist.jsp";
		}
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

	public String gjFind(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int pc = getPc(req);
		PageBean<Store> pb = service.gjFind(req.getParameter("name"), req.getParameter("category"),
				req.getParameter("author"), pc);
		pb.setUrl(getUrl(req));
		req.setAttribute("pb", pb);
		String from = req.getParameter("from");
		if (from.equals("user")) {
			return "f:/storelist.jsp";
		} else if(from.equals("admin")) {
			return "f:/admin/jsp/storelist.jsp";
		}
		return "f:/login.jsp";
	}
}
