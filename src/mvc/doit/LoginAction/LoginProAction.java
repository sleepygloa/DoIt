package mvc.doit.LoginAction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import mvc.doit.SuperAction.SuperAction;
import mvc.doit.Login.LoginDao;
import mvc.doit.Login.LoginDto;


public class LoginProAction implements SuperAction {

	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		
		
//-----------------------------�α��� ����Ͻ� ���� ------------------------------------------
		
//���̵� ���� �ޱ����� UTF-8 �����Ѵ�.
//�޾ƿ� �Էµ� id, pass�� ������ �����Ѵ�.
//Dao���ִ� loginCheck(id,passwd) �� �Է�, db�� ���Ͽ� return boolean ���� �޴´�.
//true �� ��, ���ǵ��, �α��μ���
//false�϶�, ����X, �α��ν���
		request.setCharacterEncoding("UTF-8");

		String id = null;
		String passwd = null;
		boolean check = false;
		
	    id = request.getParameter("d_id");
		passwd  = request.getParameter("d_pass");
		
		LoginDao manager = LoginDao.getInstance();
	    check= manager.loginCheck(id,passwd);
	    
	    if(check==true){
	    	HttpSession session = request.getSession();
	    	session.setAttribute("memId", id);
	    	session.setAttribute("memPass", passwd);
	    	
	    	LoginDto ltt = manager.getMember(id);
	    	session.setAttribute("memNG", ltt.getD_nom_grade());
	    	session.setAttribute("memMG", ltt.getD_mech_grade());
	    	session.setAttribute("memNo", ltt.getD_no());
	    	session.setAttribute("memAddr", ltt.getD_addr());
	    }else{}

	    request.setAttribute("check", check);
	    request.setAttribute("d_id", id);
	    request.setAttribute("d_pass", passwd);

	    
	return "/d_login/loginPro.jsp"; //View ���
	}
}
