package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections.map.HashedMap;

import cn.itcast.commons.CommonUtils;
import cn.itcast.servlet.BaseServlet;
import domain.User;
import exception.UserException;
import service.UserService;

public class UserServlet extends BaseServlet {
	private UserService userService = new UserService();

	// 退出功能
	public String quit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getSession().invalidate();
		return "r:/login.jsp";
	}

	public String login(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		User formUser = CommonUtils.toBean(req.getParameterMap(), User.class);
		Map<String, String> errors = validateLogin(formUser, req.getSession());
		if (errors.size() > 0) {
			req.setAttribute("user", formUser);
			req.setAttribute("errors", errors);
			return "f:/login.jsp";
		}
		User user = userService.login(formUser);
		if (user == null) {
			req.setAttribute("msg", "用户名或密码错误！");
			req.setAttribute("user", formUser);
			return "f:/login.jsp";
		} else {
			if (!user.isStatus()) {
				req.setAttribute("msg", "该用户还未激活");
				req.setAttribute("user", formUser);
				return "f:/login.jsp";
			} else {
				req.getSession().setAttribute("sessionUser", user);// 将登录用户保存至session中
				String loginname = user.getLoginname();
				loginname = URLEncoder.encode(loginname, "utf-8");// 保存中文要先编码
				Cookie cookie = new Cookie("loginname", loginname);
				cookie.setMaxAge(60 * 60 * 24 * 10);// 保存时间为10天
				resp.addCookie(cookie);
				return "r:/index.jsp";
			}
		}
	}

	// 修改密码
	public String updatePassword(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		User formUser = CommonUtils.toBean(req.getParameterMap(), User.class);
		User user = (User) req.getSession().getAttribute("sessionUser");
		if (user == null) {
			req.setAttribute("msg", "请先登录！");
			return "f:/login.jsp";
		} else if (formUser.getLoginpass().equals(formUser.getNewpass())) {
			req.setAttribute("msg", "新旧密码一致，不能更改");
			req.setAttribute("user", formUser);
			return "f:/changepass.jsp";
		}
		try {
			userService.updatePass(user.getUid(), formUser.getLoginpass(), formUser.getNewpass());
			req.setAttribute("msg", "修改密码成功!");
			req.setAttribute("code", "success");
			return "f:/result.jsp";
		} catch (UserException e) {
			req.setAttribute("msg", e.getMessage());
			req.setAttribute("user", formUser);
			return "f:/changepass.jsp";
		}
	}

	public String regist(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 封装表单数据
		User formUser = CommonUtils.toBean(req.getParameterMap(), User.class);
		Map<String, String> errors = validateRegist(formUser, req.getSession());
		if (errors.size() > 0) {
			req.setAttribute("form", formUser);
			req.setAttribute("errors", errors);
			return "f:/regist.jsp";
		}
		userService.regist(formUser);
		req.setAttribute("code", "success");
		req.setAttribute("msg", "注册成功,请马上到邮箱激活！");
		return "f:/result.jsp";
	}

	public String activation(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String activationCode = req.getParameter("activationCode");
		try {
			userService.activation(activationCode);
			req.setAttribute("msg", "恭喜，激活成功！");
			req.setAttribute("code", "success");
		} catch (UserException e) {
			req.setAttribute("msg", e.getMessage());
			req.setAttribute("code", "error");
		}

		return "f:/result.jsp";
	}

	public String ajaxValidateLoginname(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String loginname = req.getParameter("loginname");
		boolean b = userService.ajaxValidateLoginname(loginname);
		resp.getWriter().print(b);
		return null;
	}

	public String ajaxValidateEmail(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String email = req.getParameter("email");
		boolean b = userService.ajaxValidateEmail(email);
		resp.getWriter().print(b);
		return null;
	}

	public String ajaxValidateVerifyCode(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String verifyCode = req.getParameter("verifyCode");
		String vCode = (String) req.getSession().getAttribute("vCode");
		boolean b = verifyCode.equalsIgnoreCase(vCode);// 忽略大小写
		resp.getWriter().print(b);
		return null;
	}

	// 内部校验方法
	private Map<String, String> validateRegist(User formUser, HttpSession session) {
		Map<String, String> errors = new HashedMap();
		// 名字校验
		String loginname = formUser.getLoginname();
		if (loginname == null || loginname.trim().isEmpty()) {
			errors.put("loginname", "用户名不能为空！");
		} else if (loginname.length() < 2 || loginname.length() > 20) {
			errors.put("loginname", "用户名长度为2~20");
		} else if (!userService.ajaxValidateLoginname(loginname)) {
			errors.put("loginname", "用户名已被注册！");
		}
		// 密码校验
		String loginpass = formUser.getLoginpass();
		if (loginpass == null || loginpass.trim().isEmpty()) {
			errors.put("loginpass", "密码不能为空！");
		} else if (loginname.length() < 2 || loginname.length() > 20) {
			errors.put("loginpass", "密码长度为2~20");
		}
		// 确认密码校验
		String reloginpass = formUser.getReloginpass();
		if (reloginpass == null || reloginpass.trim().isEmpty()) {
			errors.put("reloginpass", "密码不能为空！");
		} else if (!reloginpass.equals(loginpass)) {
			errors.put("reloginpass", "两次密码不一致");
		}
		// email
		String email = formUser.getEmail();
		if (email == null || email.trim().isEmpty()) {
			errors.put("email", "email不能为空！");
		} else if (!email.matches("^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\\.[a-zA-Z0-9_-]{2,3}){1,2})$")) {
			errors.put("email", "错误的email格式");
		} else if (!userService.ajaxValidateEmail(email)) {
			errors.put("email", "email已被注册！");
		}
		// 验证码
		String verifyCode = formUser.getVerifyCode();
		String vCode = (String) session.getAttribute("vCode");
		if (verifyCode == null || verifyCode.trim().isEmpty()) {
			errors.put("verifyCode", "验证码不能为空！");
		} else if (!vCode.equalsIgnoreCase(verifyCode)) {
			errors.put("verifyCode", "验证码错误");
		}
		return errors;

	}

	private Map<String, String> validateLogin(User formUser, HttpSession session) {
		Map<String, String> errors = new HashedMap();
		// 名字校验
		String loginname = formUser.getLoginname();
		if (loginname == null || loginname.trim().isEmpty()) {
			errors.put("loginname", "用户名不能为空！");
		} else if (loginname.length() < 2 || loginname.length() > 20) {
			errors.put("loginname", "用户名长度为2~20");
		}
		// 密码校验
		String loginpass = formUser.getLoginpass();
		if (loginpass == null || loginpass.trim().isEmpty()) {
			errors.put("loginpass", "密码不能为空！");
		} else if (loginname.length() < 2 || loginname.length() > 20) {
			errors.put("loginpass", "密码长度为2~20");
		}
		String verifyCode = formUser.getVerifyCode();
		String vCode = (String) session.getAttribute("vCode");
		if (verifyCode == null || verifyCode.trim().isEmpty()) {
			errors.put("verifyCode", "验证码不能为空！");
		} else if (!vCode.equalsIgnoreCase(verifyCode)) {
			errors.put("verifyCode", "验证码错误");
		}
		return errors;

	}

	// 添加数量时查看是否登录
	public void ajaxValidateLogin(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		User user = (User) req.getSession().getAttribute("sessionUser");
		if ((user == null)) {
			resp.getWriter().print("0");
		} else {
			resp.getWriter().print("1");
		}
	}
}
