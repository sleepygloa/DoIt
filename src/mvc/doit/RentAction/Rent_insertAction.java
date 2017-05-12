package mvc.doit.RentAction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mvc.doit.Rent.RentDao;
import mvc.doit.SuperAction.SuperAction;

public class Rent_insertAction implements SuperAction{

	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		//�۾��� ����
		HttpSession session = request.getSession();
		int gugu = (int)session.getAttribute("memMG");
		
		//���̺� �̸� ����
		String tab_name = "b_rent";
		
		//�ڵ� �ڵ� ����
		// -> �ڵ� ���� ���뿡 "A,B,C" �� �����Ͽ� �Է�
		RentDao rdo = RentDao.getInstance();
		String code = rdo.code_generic(tab_name);
		
		request.setAttribute("br_code", "B"+code);
		request.setAttribute("gugu", gugu);
		
		
		
		return "/d_rent/b_write_gu.jsp";
	}
	
}
