package mvc.doit.AdminAction;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mvc.doit.SuperAction.SuperAction;

public class OnRegistFormAction implements SuperAction {
	public String execute(HttpServletRequest request, HttpServletResponse reponse) throws Exception{
			
		//å�̸��� �������� DB�� ���Ͽ� ���� �̸��� �ִٸ�, å�� ������ �ҷ��� �ݴϴ�.
		String d_bname = request.getParameter("d_bname");
		
		//Db��Ͻ� �Ǹ����̸��� �����ϱ� ���� �Ǹ��� ������ �޾ƿɴ�
		String d_id = request.getParameter("d_id");
		
		//�˼��ڵ带 �޾ƿ� �˼��ڵ带 �޾ƿ� �˼��� ���� ������ �����մϴ�.
		int d_icode = Integer.parseInt(request.getParameter("d_icode"));
		
		
		
		
		return "/d_admin/onRegistForm.jsp";
	}

}
