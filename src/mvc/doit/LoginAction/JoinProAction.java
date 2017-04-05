package mvc.doit.LoginAction;

import java.sql.Timestamp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import mvc.doit.SuperAction.SuperAction;
import mvc.doit.Login.LoginDao;
import mvc.doit.Login.LoginDto;



public class JoinProAction implements SuperAction {
	public String execute(HttpServletRequest request, 
									HttpServletResponse response) throws Exception {

		request.setCharacterEncoding("UTF-8");
		
		
		String path = request.getRealPath("save"); 			
		int size = 1024*1024*5;		
		String enc = "UTF-8";		
		DefaultFileRenamePolicy df = new DefaultFileRenamePolicy();				
		MultipartRequest multi = new MultipartRequest(request, path, size, enc, df);
		
		LoginDto dto = new LoginDto();
		dto.setD_id(multi.getParameter("d_id")); //���̵�
		dto.setD_pass( multi.getParameter("d_pass")); //��й�ȣ
		dto.setD_name(multi.getParameter("d_name")); //�̸�
		dto.setUser_phone1(multi.getParameter("user_phone1")); //010
		dto.setUser_phone2(multi.getParameter("user_phone2")); //�߰��ڸ�
		dto.setUser_phone3(multi.getParameter("user_phone3")); //���ڸ�
		dto.setD_addr(multi.getParameter("d_addr")); //ȸ���ּ�
		dto.setD_person(multi.getParameter("d_person")); //����/���
		dto.setUser_mail1(multi.getParameter("user_mail1")); //�̸��� ���ڸ�@
		dto.setUser_mail2(multi.getParameter("user_mail2")); //@�̸��� ���ڸ�
		dto.setUser_birth1(multi.getParameter("user_birth1")); //�������(���ڸ�)��
		dto.setUser_birth2(multi.getParameter("user_birth2")); //�������(�߰�)��
		dto.setUser_birth3(multi.getParameter("user_birth3")); //�������(��)��
		dto.setD_gender(multi.getParameter("d_gender")); //��/��
		dto.setD_pic(multi.getFilesystemName("save")); //ȸ�� ����
		dto.setD_date(new Timestamp(System.currentTimeMillis())); //ȸ�����Գ�¥
		//���⼭ ���� D_nom_grade�� �⺻������ 0 �Էµ˴ϴ�. D_nom_grade �¶��μ�����  ȸ�����
		
		request.setAttribute("on",multi.getOriginalFileName("save")); 
		request.setAttribute("ct",multi.getContentType("ct"));
		request.setAttribute("sn",multi.getFilesystemName("sb"));				
		request.setAttribute("path", path);
		
		LoginDao manager = LoginDao.getInstance();		
		manager.insertMember(dto);
		/*
		member.setUser_id(request.getParameter("user_id"));
		member.setD_pass(request.getParameter("D_pass"));
		member.setUser_name(request.getParameter("user_name"));
		member.setD_phone(request.getParameter("D_phone"));
		member.setAddr(request.getParameter("addr"));
		member.setJo_user_pers(request.getParameter("jo_user_pers"));
		member.setD_mail(request.getParameter("D_mail"));
		member.setD_birth(request.getParameter("D_birth"));
		member.setGender(request.getParameter("gender"));
		member.setD_pic(request.getParameter("d_pic"));
		member.setD_date(new Timestamp(System.currentTimeMillis()));
		
		manager.insertMember(member);
		*/
		
		/*
				
		
				request.setAttribute("d_id" , multi.getParameter("d_id"));
				request.setAttribute("d_pass" , multi.getParameter("d_pass"));
				request.setAttribute("d_name" , multi.getParameter("d_name"));
				request.setAttribute("d_phone" , multi.getParameter("d_phone"));
				request.setAttribute("d_addr" , multi.getParameter("d_addr"));
				request.setAttribute("d_person" , multi.getParameter("d_person"));
				request.setAttribute("d_mail" , multi.getParameter("d_mail"));
				request.setAttribute("d_birth" , multi.getParameter("d_birth"));
				request.setAttribute("d_gender" , multi.getParameter("d_gender"));
				request.setAttribute("d_pic" , multi.getParameter("d_pic"));
				request.setAttribute(arg0, arg1);
				member.setD_date(new Timestamp(System.currentTimeMillis()));
				
				request.setAttribute("on",multi.getOriginalFileName("save"));
				request.setAttribute("ct",multi.getContentType("ct"));
				request.setAttribute("sn",multi.getFilesystemName("sb"));				
				request.setAttribute("path", path);
				
				manager.insertMember(member);
	    
	    */
		return "/d_login/joinPro.jsp";
	}
}