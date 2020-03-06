package filter;

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
 * Servlet Filter implementation class LoginFilter
 */
@WebFilter(
		urlPatterns = { 
				"/LoginFilter", 
				"/desc.jsp",
				"/carItemlist.jsp",
				"/changepass.jsp",
				"/orderlist.jsp",
				"/ordersucc.jsp",
				"/pay.jsp"
		}, 
		servletNames = { 
				"OrderServlet"
		})
public class LoginFilter implements Filter {

    /**
     * Default constructor. 
     */
    public LoginFilter() {
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
		// TODO Auto-generated method stub
		// place your code here

		// pass the request along the filter chain
		HttpServletRequest req=(HttpServletRequest) request;
		Object user=req.getSession().getAttribute("sessionUser");
		if(user==null){
			req.setAttribute("code", "error");
			req.setAttribute("msg", "登录后才可以访问！");
			req.getRequestDispatcher("result.jsp").forward(req, response);
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
