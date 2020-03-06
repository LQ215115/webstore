package admin.servlet;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.commons.CommonUtils;
import cn.itcast.servlet.BaseServlet;
import domain.Category;
import domain.Food;
import domain.Place;
import domain.Store;
import service.CategoryService;
import service.StoreService;

public class AdminStoreServlet extends BaseServlet {
	private StoreService storeService=new StoreService();
	private CategoryService categoryService=new CategoryService();
	/**
	 * 修改店铺信息时调用
	 * @param req
	 * @param resp
	 * @return
	 */
		public String findById(HttpServletRequest req, HttpServletResponse resp) {
			Store store= storeService.findById(req.getParameter("id"));
			req.setAttribute("store", store);
			return "f:/admin/jsp/storedesc.jsp";

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
			Map map = req.getParameterMap();
			Store store = CommonUtils.toBean(map, Store.class);
			Place place = CommonUtils.toBean(map, Place.class);
			store.setPlace(place);;
			
			storeService.edit(store);
			req.setAttribute("msg", "修改店铺信息成功！");
			return "f:/admin/msg.jsp";
		}
		/**
		 * 删除店铺
		 * @param req
		 * @param resp
		 * @return
		 * @throws ServletException
		 * @throws IOException
		 */
		public String delete(HttpServletRequest req, HttpServletResponse resp)
				throws ServletException, IOException {
			String id = req.getParameter("id");
			int cnt = categoryService.findChildrenCountByParent(id);
			if (cnt > 0) {
				req.setAttribute("msg", "该店铺下还有其他分类，不能删除！");
				return "f:/admin/msg.jsp";
			}else{
			
			
			/*
			 * 删除图片
			 */
			Store store = storeService.findById(id);
			String savepath = this.getServletContext().getRealPath("/");//获取真实的路径
			new File(savepath,store.getImg()).delete();//删除文件
			
			storeService.delete(store);//删除数据库的记录
			
			req.setAttribute("msg", "删除店铺成功！");
			return "f:/admin/msg.jsp";
			}
		}
}
