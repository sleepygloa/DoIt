package mvc.doit.LoginAction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mvc.doit.Login.LoginDao;
import mvc.doit.Login.LoginDto;
import mvc.doit.SuperAction.SuperAction;

public class updateAction implements SuperAction {
	
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession();
		String id = (String)session.getAttribute("memId");
		
		LoginDao log = LoginDao.getInstance();
		LoginDto lto = log.getMember(id); //ȸ������ ����

		
		request.setAttribute("lto", lto); //ȸ������ �����Ͽ� ����

		
		return "/d_login/my_mody.jsp";
	}
	
	
}
