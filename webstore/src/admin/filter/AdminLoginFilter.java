package admin.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

/**
 * Servlet Filter implementation class AdminLoginFilter
 */
@WebFilter(
		urlPatterns = { 
				"/AdminLoginFilter", 
				"/admin/jsp/*"
		}, 
		servletNames = { 
				"AdminAddFoodServlet", 
				"AdminFoodServlet", 
				"AdminOrderServlet", 
				"AdminCategoryServlet"
		})
public class AdminLoginFilter implements Filter {

    /**
     * Default constructor. 
     */
    public AdminLoginFilter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req=(HttpServletRequest) request;
		Object admin=req.getSession().getAttribute("admin");
		if(admin==null){
			request.setAttribute("msg", "请先登录");
			request.getRequestDispatcher("/admin/login.jsp").forward(request, response);
		}else{
		chain.doFilter(request, response);
		}
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
