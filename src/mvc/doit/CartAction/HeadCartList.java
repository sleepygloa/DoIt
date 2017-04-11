package mvc.doit.CartAction;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mvc.doit.Cart.CartDao;
import mvc.doit.Login.LoginDao;
import mvc.doit.Login.LoginDto;
import mvc.doit.Online.OnBookDto;
import mvc.doit.Online.OnDao;
import mvc.doit.SuperAction.SuperAction;

public class HeadCartList implements SuperAction{

	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		//�ѱ� ���ڵ�
		request.setCharacterEncoding("UTF-8");
		
		//�Ķ���� ����
		HttpSession session = request.getSession();
		int br_no = (int)session.getAttribute("memNo");
		String id = (String)session.getAttribute("memId");
		
		String cols = request.getParameter("cols"); //��ٱ��� ���� column �̸�
		String buy = request.getParameter("buy");

		int d_bgradevalue = 0;
		
		CartDao cdao = CartDao.getInstance();
		List CartList = cdao.getHeadCart(br_no, cols);
		
		
		request.setAttribute("CartList", CartList);
		request.setAttribute("cols", cols);
		request.setAttribute("buy", buy);
		
		
		if(cols.equals("d_sell")){
			if(buy.equals("buy")){
				//�޾ƿ� ���� d_bno, d_bcode �� ���� �ϳ��� ���� �� ��, ������ �����Ѵ�.
				int d_bno = 0;
				int d_bcode = 0;
				if(request.getParameter("d_bno") == null && request.getParameter("d_bcode") == null){
							
				}else if( request.getParameter("d_bcode") == null && request.getParameter("d_bno") != null){
					d_bno = Integer.parseInt(request.getParameter("d_bno"));
				}else if(request.getParameter("d_bcode") != null){
					d_bcode = Integer.parseInt(request.getParameter("d_bcode"));
				}
			
				 	
				OnDao dao = OnDao.getInstance();
				OnBookDto dto = dao.User_onBuyBook(d_bno, d_bcode);
				
				LoginDao LogDao = LoginDao.getInstance();
				LoginDto LogDto = LogDao.getMember(id);
				
				String user_check = null;
				if(request.getParameter("user_check") != null){
					user_check = request.getParameter("user_check");
				}
				int d_total = cdao.getBookBuyTotal(br_no, cols);
				if(d_total == 0 ){
					d_total = dto.getD_bsellvalue();
				}
				//ȸ����޿� ���� ���ΰ��� total�ݾ��� ���ϴ� ����
				int dng = LogDto.getD_nom_grade();
				int d_totalDis = 0;
				if(dng == 1 ){
					d_totalDis = (int)((double)d_total * 0.05);
				}else if(dng == 2){
					d_totalDis = (int)((double)d_total * 0.10);
				}else{}

				d_bgradevalue = d_total - d_totalDis;
				if(request.getParameter("d_bgradevalue") != null){
					d_bgradevalue = Integer.parseInt(request.getParameter("d_bgradevalue"));
				}		

				
				request.setAttribute("d_totalDis", d_totalDis);
				request.setAttribute("d_total", d_total);
				request.setAttribute("d_bgradevalue", d_bgradevalue);
				
				request.setAttribute("dto", dto);
				request.setAttribute("LogDto", LogDto);
				request.setAttribute("user_check", user_check);
				request.setAttribute("id", id);
				
						
			}else if(buy.equals("cart")){
				List CartP = (List) session.getAttribute("CartP");
				
				LoginDao LogDao = LoginDao.getInstance();
				LoginDto LogDto = LogDao.getMember(id);
				
				String user_check = null;
				if(request.getParameter("user_check") != null){
					user_check = request.getParameter("user_check");
				}
				
				int d_total = cdao.getBookBuyTotal(br_no, cols);				

				//ȸ����޿� ���� ���ΰ��� total�ݾ��� ���ϴ� ����
				int dng = LogDto.getD_nom_grade();
				int d_totalDis = 0;
				if(dng == 1 ){
					d_totalDis = (int)((double)d_total * 0.05);
				}else if(dng == 2){
					d_totalDis = (int)((double)d_total * 0.10);
				}else{}

				d_bgradevalue = d_total - d_totalDis;
				if(request.getParameter("d_bgradevalue") != null){
					d_bgradevalue = Integer.parseInt(request.getParameter("d_bgradevalue"));
				}				
				
				
				
				
				

				request.setAttribute("d_total", d_total);
				request.setAttribute("d_bgradevalue", d_bgradevalue);
				request.setAttribute("LogDto", LogDto);
				request.setAttribute("user_check", user_check);
				request.setAttribute("id", id);
			}
		}
		
		
		
		return "/d_cart/rent_order.jsp";
	}
	
}
