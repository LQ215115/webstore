package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;

import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.config.AlipayConfig;

import bean.PageBean;
import cn.itcast.commons.CommonUtils;
import cn.itcast.servlet.BaseServlet;
import domain.CarItem;
import domain.Order;
import domain.OrderItem;
import domain.User;
import service.CarItemService;
import service.OrderService;

public class OrderServlet extends BaseServlet {
	private OrderService orderService = new OrderService();
	private CarItemService carItemService = new CarItemService();

	/**
	 * 获取当前页码
	 * 
	 * @param req
	 * @return
	 */
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

	/**
	 * 截取url，页面中的分页导航中需要使用它做为超链接的目标！
	 * 
	 * @param req
	 * @return
	 */
	/*
	 * http://localhost:8080/goods/BookServlet?methed=findByCategory&cid=xxx&pc=
	 * 3 /goods/BookServlet + methed=findByCategory&cid=xxx&pc=3
	 */
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

	/**
	 * 支付准备
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String paymentPre(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setAttribute("order", orderService.load(req.getParameter("oid")));
		return "f:/pay.jsp";
	}

	/**
	 * 支付方法
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String payment(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		//获得初始化的AlipayClient
		AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.app_id, AlipayConfig.merchant_private_key, "json", AlipayConfig.charset, AlipayConfig.alipay_public_key, AlipayConfig.sign_type);
		
		//设置请求参数
		AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
		alipayRequest.setReturnUrl(AlipayConfig.return_url);
		alipayRequest.setNotifyUrl(AlipayConfig.notify_url);
		//商户订单号，商户网站订单系统中唯一订单号，必填
		String out_trade_no = new String(req.getParameter("oid"));
		//付款金额，必填
		Double total=orderService.getTotal(req.getParameter("oid"));
		//订单名称，必填
		String subject = new String(("This is a test").getBytes("ISO-8859-1"),"UTF-8");
		//商品描述，可空
		String body = new String(("This is a test").getBytes("ISO-8859-1"),"UTF-8");
		System.out.println("test2");
		
		alipayRequest.setBizContent("{\"out_trade_no\":\""+ out_trade_no +"\"," 
				+ "\"total_amount\":\""+ total +"\"," 
				+ "\"subject\":\""+ subject +"\"," 
				+ "\"body\":\""+ body +"\"," 
				+ "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");
		
		//若想给BizContent增加其他可选请求参数，以增加自定义超时时间参数timeout_express来举例说明
		//alipayRequest.setBizContent("{\"out_trade_no\":\""+ out_trade_no +"\"," 
		//		+ "\"total_amount\":\""+ total_amount +"\"," 
		//		+ "\"subject\":\""+ subject +"\"," 
		//		+ "\"body\":\""+ body +"\"," 
		//		+ "\"timeout_express\":\"10m\"," 
		//		+ "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");
		//请求参数可查阅【电脑网站支付的API文档-alipay.trade.page.pay-请求参数】章节
		
		//请求
		try {
			String result = alipayClient.pageExecute(alipayRequest).getBody();
		    PrintWriter out = resp.getWriter();
		    out.flush();
		    out.println(result);
		} catch (AlipayApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 取消订单
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String cancel(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String oid = req.getParameter("oid");
		/*
		 * 校验订单状态
		 */
		int status = orderService.findStatus(oid);
		if (status != 1) {
			req.setAttribute("code", "error");
			req.setAttribute("msg", "状态不对，不能取消！");
			return "f:/result.jsp";
		}
		orderService.updateStatus(oid, 5);// 设置状态为取消！
		req.setAttribute("code", "success");
		req.setAttribute("msg", "您的订单已取消！");
		return "f:/result.jsp";
	}

	/**
	 * 确认收货
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String confirm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String oid = req.getParameter("oid");
		/*
		 * 校验订单状态
		 */
		int status = orderService.findStatus(oid);
		if (status != 3) {
			req.setAttribute("code", "error");
			req.setAttribute("msg", "状态不对，不能确认收货！");
			return "f:/result.jsp";
		}
		orderService.updateStatus(oid, 4);// 设置状态为交易成功！
		req.setAttribute("code", "success");
		req.setAttribute("msg", "恭喜，交易成功！");
		return "f:/result.jsp";
	}

	/**
	 * 加载订单
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String load(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String oid = req.getParameter("oid");
		Order order = orderService.load(oid);
		req.setAttribute("order", order);
		String btn = req.getParameter("btn");// btn说明了用户点击哪个超链接来访问本方法的
		req.setAttribute("btn", btn);
		return "/desc.jsp";
	}

	/**
	 * 生成订单
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String createOrder(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		/*
		 * 1. 获取所有购物车条目的id，查询之
		 */
		String carItemIds = req.getParameter("carItemIds");
		List<CarItem> carItemList = carItemService.loadCarItems(carItemIds);
		/*
		 * 2. 创建Order
		 */
		Order order = new Order();
		order.setOid(CommonUtils.uuid());// 设置主键
		order.setOrdertime(String.format("%tF %<tT", new Date()));// 下单时间
		order.setStatus(1);// 设置状态，1表示未付款
		String address = URLDecoder.decode(req.getParameter("address"), "UTF-8");
		order.setAddress(address);// 设置收货地址
		User owner = (User) req.getSession().getAttribute("sessionUser");
		order.setOwner(owner);// 设置订单所有者

		BigDecimal total = new BigDecimal("0");
		for (CarItem carItem : carItemList) {
			total = total.add(new BigDecimal(carItem.getSubtotal() + ""));
		}
		order.setTotal(total.doubleValue());// 设置总计

		/*
		 * 3. 创建List<OrderItem> 一个CartItem对应一个OrderItem
		 */
		List<OrderItem> orderItemList = new ArrayList<OrderItem>();
		for (CarItem cartItem : carItemList) {
			OrderItem orderItem = new OrderItem();
			orderItem.setOrderItemId(CommonUtils.uuid());// 设置主键
			orderItem.setQuantity(cartItem.getQuantity());
			orderItem.setSubtotal(cartItem.getSubtotal());
			orderItem.setFood(cartItem.getFood());
			orderItem.setOrder(order);
			orderItemList.add(orderItem);
		}
		order.setOrderItemList(orderItemList);

		/*
		 * 4. 调用service完成添加
		 */
		orderService.createOrder(order);

		// 删除购物车条目
		carItemService.batchDelete(carItemIds);
		/*
		 * 5. 保存订单，转发到ordersucc.jsp
		 */
		req.setAttribute("order", order);
		return "f:/ordersucc.jsp";
	}

	/**
	 * 我的订单
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String myOrders(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		/*
		 * 1. 得到pc：如果页面传递，使用页面的，如果没传，pc=1
		 */
		int pc = getPc(req);
		/*
		 * 2. 得到url：...
		 */
		String url = getUrl(req);
		/*
		 * 3. 从当前session中获取User
		 */
		User user = (User) req.getSession().getAttribute("sessionUser");

		/*
		 * 4. 使用pc和cid调用service#findByCategory得到PageBean
		 */
		PageBean<Order> pb = orderService.myOrders(user.getUid(), pc);
		/*
		 * 5. 给PageBean设置url，保存PageBean，转发到/jsps/book/list.jsp
		 */
		pb.setUrl(url);
		req.setAttribute("pb", pb);
		return "f:/orderlist.jsp";
	}
}
