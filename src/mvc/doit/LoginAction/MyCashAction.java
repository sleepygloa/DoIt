package mvc.doit.LoginAction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mvc.doit.Online.OnDao;
import mvc.doit.SuperAction.SuperAction;

public class MyCashAction implements SuperAction{

	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		//�ѱ� ���ڵ�
		request.setCharacterEncoding("UTF-8");
		
		//ĳ�� ���� ����

		
		return "/d_login/my_cash.jsp";
	}
	
}
