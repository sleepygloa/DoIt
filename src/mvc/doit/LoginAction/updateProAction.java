package mvc.doit.LoginAction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import mvc.doit.Login.LoginDao;
import mvc.doit.Login.LoginDto;
import mvc.doit.SuperAction.SuperAction;

public class updateProAction implements SuperAction{
	
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		request.setCharacterEncoding("UTF-8");
		
		String path = request.getRealPath("save"); //��� ����
		int size = 1024 * 1024 * 5; //������ 5mb���� ����
		String enc = "UTF-8"; //���ڵ� Ÿ��
		DefaultFileRenamePolicy df = new DefaultFileRenamePolicy(); //����� ����
		MultipartRequest multi = new MultipartRequest(request,path,size,enc,df); // ��Ƽ��Ʈ�� �ޱ�
		
		LoginDto lto = new LoginDto();
		lto.setD_pic(multi.getParameter("thum_pic"));
		lto.setD_pass(multi.getParameter("user_pw"));
		lto.setUser_phone1(multi.getParameter("user_phone1"));
		lto.setUser_phone2(multi.getParameter("user_phone2"));
		lto.setUser_phone3(multi.getParameter("user_phone3"));
		lto.setUser_birth1(multi.getParameter("user_birth1"));
		lto.setUser_birth2(multi.getParameter("user_birth2"));
		lto.setUser_birth3(multi.getParameter("user_birth3"));
		lto.setD_addr(multi.getParameter("addr"));
		lto.setUser_mail1(multi.getParameter("user_mail1"));
		lto.setUser_mail2(multi.getParameter("user_mail2"));
		
		request.setAttribute("lto", lto);
		request.setAttribute("sn", multi.getFilesystemName("thum_pic"));
		
		return "/d_login/up_result.jsp";
	}
}







