package mvc.doit.ManagerAction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mvc.doit.SuperAction.SuperAction;

public class ManagerDash implements SuperAction {

	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		//�ѱ� ���ڵ�
		request.setCharacterEncoding("UTF-8");
		
		//�ҷ��� ������ �Ķ����
		String mana_page = request.getParameter("mana_page");
		
		
		
		
		//�Ķ���� ���� 
		request.setAttribute("mana_page", mana_page);
		
		return "/d_manage/manage_dash.jsp";
	}
	
}
