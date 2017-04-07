package mvc.doit.LoginAction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mvc.doit.Account.AcDao;
import mvc.doit.Account.AcDto;
import mvc.doit.Login.LoginDao;
import mvc.doit.Login.LoginDto;
import mvc.doit.SuperAction.SuperAction;


public class MyPageInfoAction implements SuperAction{
	
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		HttpSession session = request.getSession();
		String d_id = (String)session.getAttribute("memId"); //���� ����
		
		LoginDao manager = LoginDao.getInstance();
		LoginDto lto = manager.getMember(d_id); //Dto ����
	    
	//---- ���µ�� ��û��û�� ���� ��� --------

		if(d_id == null){
			return "/d_login/login.jsp";
		}
		int d_no = lto.getD_no();
		AcDto adto = null;
		AcDao adao = AcDao.getInstance();
		
		//---- ������ ���¸� �ҷ��ɴϴ�.
		adto = adao.getAccount(d_no);

		//---- �ܾ׸���� ���� d_log table���� d_ldealmoney�� �ջ� �մϴ�.
		int d_lsummoney = 0;		
		d_lsummoney = adao.getAccountSumMoney(d_no);

	    //------------------------------------ ���� �ڵ� �ӽ� ���� �ڵ� �� -----------------------------------------------------//
		
	    //��Ʈ����Ʈ ����
	    request.setAttribute("lto", lto);
	    request.setAttribute("adto", adto);
	    request.setAttribute("d_no", d_no);
	    request.setAttribute("d_lsummoney", d_lsummoney);
		
		return "/d_login/my_info.jsp";
	}
	
}
