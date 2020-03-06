package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.commons.CommonUtils;
import cn.itcast.servlet.BaseServlet;
import domain.CarItem;
import domain.Food;
import domain.Place;
import domain.User;
import service.CarItemService;
import service.FoodService;

public class CarItemServlet extends BaseServlet {
	private CarItemService carItemservie = new CarItemService();
	private FoodService foodService = new FoodService();

	public String myCar(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		User user = (User) req.getSession().getAttribute("sessionUser");
		if (!(user == null)) {
			List<CarItem> list = carItemservie.myCarItem(user.getUid());
			Place place = (Place) req.getSession().getAttribute("place");
			if (!(place == null)) {
				String address = place.getSheng() + "-" + place.getShi() + "-" + place.getQu();
				req.setAttribute("address", address);
			}
			req.setAttribute("carItemList", list);
			return "f:/carItemlist.jsp";
		} else {
			return "f:/down.jsp";
		}
	}

	// 添加条目,food.list页面调用
	public String addCarItem(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		User user = (User) req.getSession().getAttribute("sessionUser");
		if ((user == null)) {
			return "f:/down.jsp";
		} else {
			// 先判断是否存在，若存在则修改数量，不存在就创建
			CarItem _carItem = carItemservie.findByUidAndFid(user.getUid(), req.getParameter("fid"));
			if (_carItem == null) {
				Food food = foodService.findByFid(req.getParameter("fid"));
				CarItem carItem = new CarItem();
				carItem.setFood(food);
				carItem.setUser(user);
				carItem.setCarItemId(CommonUtils.uuid());
				carItem.setQuantity(1);
				carItemservie.addCartItem(carItem);
			} else {
				int quantity = _carItem.getQuantity() + 1;
				carItemservie.updateQuantity(_carItem.getCarItemId(), quantity);
			}

			// 下面计算总价传回页面
			double totalprice = 0;
			List<CarItem> listCarItem = carItemservie.myCarItem(user.getUid());
			for (CarItem caritem : listCarItem) {
				totalprice += caritem.getSubtotal();
			}
			req.setAttribute("totalprice", totalprice);
			return "f:/down.jsp";
		}

	}

	/**
	 * 购买数量加1,购物车页面调用
	 */
	public String updateQuantity(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String carItemId = req.getParameter("carItemId");
		int quantity = Integer.parseInt(req.getParameter("quantity"));
		CarItem carItem = carItemservie.updateQuantity(carItemId, quantity);

		// 给客户端返回一个json对象
		StringBuilder sb = new StringBuilder("{");
		sb.append("\"quantity\"").append(":").append(carItem.getQuantity());
		sb.append(",");
		sb.append("\"subtotal\"").append(":").append(carItem.getSubtotal());
		sb.append("}");

		resp.getWriter().print(sb);
		return null;
	}

	/**
	 * 删除条目，food.list页面调用
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String moveCarItem(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		User user = (User) req.getSession().getAttribute("sessionUser");
		if ((user == null)) {
			return "f:/down.jsp";
		} else {
			carItemservie.moveCartItem(user.getUid(), req.getParameter("fid"));
			// 下面计算总价传回页面
			double totalprice = 0;
			List<CarItem> listCarItem = carItemservie.myCarItem(user.getUid());
			for (CarItem caritem : listCarItem) {
				totalprice += caritem.getSubtotal();
			}
			req.setAttribute("totalprice", totalprice);
			return "f:/down.jsp";
		}

	}

	public String batchDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		carItemservie.batchDelete(req.getParameter("carItemIds"));
		return myCar(req, resp);
	}
}
