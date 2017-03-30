package mvc.doit.LoginAction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mvc.doit.SuperAction.SuperAction;

public class LogoutProAction implements SuperAction {
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		
		HttpSession session = request.getSession();
		session.invalidate();
		
		
		
	return "/d_login/logoutPro.jsp"; //View ���
	}
}
//�α׾ƿ� ����, ��� ���� ����. ��ٱ��� ���ǻ���, ���ǿ� ���� ������ �ʿ���