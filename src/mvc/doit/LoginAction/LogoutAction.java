package mvc.doit.LoginAction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mvc.doit.SuperAction.SuperAction;


public class LogoutAction implements SuperAction {

	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception{

		int x = 1;
		
		request.setAttribute("x", x);
		
	return "/d_login/logout.jsp"; //View ���
	}
}


//������������� �α׾ƿ��� �α׾ƿ�Ȯ��â�� �����ϱ� ���ؼ� ������ ���� x ���� ����, x�� �ǹ̴� ����