package mvc.doit.RentAction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mvc.doit.Rent.RentDao;
import mvc.doit.SuperAction.SuperAction;

public class Rent_replyDelAction implements SuperAction{

	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		//�ѱ� ���ڵ�
		request.setCharacterEncoding("UTF-8");
		
		//��üȭ
		RentDao rdo = RentDao.getInstance();
		
		/* ������ �ƴϳ� */
		String b_id = request.getParameter("b_id"); //���̵� ����
		String br_no = request.getParameter("br_no");//�󼼺��� �۹�ȣ
		String ba_no = request.getParameter("b_no");//��� ��ȣ ����
		int b_no = Integer.parseInt(ba_no);
		
	    HttpSession session = request.getSession();
	    String memId = (String)session.getAttribute("memId"); //���� ���̵�
	    int memMG = (int)session.getAttribute("memMG"); //������ ����
		
		//��� ����
		if(memId.equals(b_id) || memMG == 10){
			rdo.deleteReview(b_no);
		}
		
		
		request.setAttribute("br_no", br_no);
		
		return "/d_rent/detail.do";
	}
}
