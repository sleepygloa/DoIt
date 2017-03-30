package mvc.doit.LoginAction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mvc.doit.Login.LoginDao;
import mvc.doit.Login.LoginDto;
import mvc.doit.SuperAction.SuperAction;

public class updateReAction implements SuperAction{
	
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		request.setCharacterEncoding("UTF-8");
		
		//���� ���̵� �ޱ�
		HttpSession session = request.getSession();
		String id = (String)session.getAttribute("memId");
		
		//�Է� ���� ��й�ȣ
		String pwCheck = request.getParameter("d_pass");
		
		LoginDao lao = LoginDao.getInstance();
		LoginDto Dto = lao.getMember(id);
		
		//DB ��й�ȣ �ޱ�
		String se_pass = Dto.getD_pass();
		
		if(se_pass.equals(pwCheck)){
			LoginDto dto = new LoginDto();
			dto.setD_pic(request.getParameter("thumb_pic"));
			dto.setD_pass(request.getParameter("user_pw"));
			dto.setUser_phone1(request.getParameter("user_phone"));
			dto.setUser_birth1(request.getParameter("user_birth"));
			dto.setD_addr(request.getParameter("addr"));
			dto.setUser_mail1(request.getParameter("user_mail"));
			
			lao.upDate(dto,id);
		}
		
		
		return "/d_login/myInfo.do";
	}
	
}
