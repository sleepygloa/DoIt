package mvc.doit.CartAction;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mvc.doit.Cart.CartDao;
import mvc.doit.SuperAction.SuperAction;

public class HeadCartList implements SuperAction{

	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		//�ѱ� ���ڵ�
		request.setCharacterEncoding("UTF-8");
		
		//�Ķ���� ����
		HttpSession session = request.getSession();
		int br_no = (int)session.getAttribute("memNo"); 
		String cols = request.getParameter("cols"); //��ٱ��� ���� column �̸�
//ȸ��ID�� ���� ���cols=d_sell��
		
		CartDao cdao = CartDao.getInstance();
		List CartList = cdao.getHeadCart(br_no, cols);
		
		request.setAttribute("CartList", CartList);
		request.setAttribute("cols", cols);
		
		
		
		return "/d_cart/rent_order.jsp";
	}
	
}
