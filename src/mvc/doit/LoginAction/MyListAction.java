package mvc.doit.LoginAction;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mvc.doit.Cart.CartDao;
import mvc.doit.SuperAction.SuperAction;

public class MyListAction implements SuperAction{

	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		//�ѱ� ���ڵ�
		request.setCharacterEncoding("UTF-8");
		
		//���� ��¥ �ҷ�����
		Date date = new Date();
		SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy. MM. dd");
		String das = simpleDate.format(date);
		request.setAttribute("SimpleDate", das);
		
		//���� �� �ҷ�����
		String col = request.getParameter("cols");
		
		//���� �� ��������
		HttpSession session = request.getSession();
		int br_no = (int)session.getAttribute("memNo"); //ȸ�� ��ȣ 
		
		//��ü ����
		CartDao cdo = CartDao.getInstance(); //��ٱ��� ��ü ����
		
		//������ ����Ʈ ��ü
		List getE = new ArrayList();
		
		if(col.equals("dr_rent")){
			//----------------------- ������ ���� ��Ʈ ----------------------
			//å ���� �߰�
			getE = cdo.getHeadCart(br_no,"dr_rent");
		}else{
			//----------------------- �����Ǹ� �ֹ����� ----------------------
			
		}
		
		request.setAttribute("getE", getE);
		
		return "/d_login/my_list.jsp";
	}
	
}
