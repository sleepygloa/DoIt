package mvc.doit.CartAction;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mvc.doit.Cart.CartDao;
import mvc.doit.SuperAction.SuperAction;

public class CartRemoveAction implements SuperAction{

	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		//�ѱ� ���ڵ�
		request.setCharacterEncoding("UTF-8");
		
		//���� ���� ����
		HttpSession session = request.getSession();
		
		//��ٱ��� Dao ��ü ����
		CartDao cdao = CartDao.getInstance();
		
		//�Ķ���� ����
		String br_code = request.getParameter("br_code"); //å �ڵ� ��ȣ
		String col = request.getParameter("cols"); // ������ �÷� ����
		int br_no = (int)session.getAttribute("memNo"); // ȸ����ȣ ���
		request.setAttribute("cols", col);
		
		//�ش� �ڵ� ����(��� ���� ���Ӱ� ������Ʈ��� �д´�.)
		cdao.deleteCart(br_no,br_code,col); // ȸ����ȣ, �ش� å�ڵ�, ���� �÷�
		
		//���Ӱ� ���� ���
		List CartList = cdao.getHeadCart(br_no, col);
		request.setAttribute("CartList", CartList);
		
		//���ǿ� ��ٱ��� ���Ӱ� ����
		if(col.equals("d_rent")){ //������ ���� ����
			session.removeAttribute("CartL");
			List CartL = cdao.getHeadCart(br_no, col);
			session.setAttribute("CartL", CartL);
		}else{ //�����Ǹ� ����
			session.removeAttribute("CartP");
			List CartP = cdao.getHeadCart(br_no, col);
			session.setAttribute("CartP", CartP);
		}
		
		
		return "/d_cart/rent_order.jsp";
	}
	
}











