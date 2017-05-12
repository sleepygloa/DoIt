package mvc.doit.RentAction;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import mvc.doit.Rent.RentDao;
import mvc.doit.Rent.RentDto;
import mvc.doit.SuperAction.SuperAction;

public class Rent_insertProAction implements SuperAction{

	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		//���� ���� ����
		
		//���� ���
		String path = request.getRealPath("/save");
		int size = 1024 * 1024 * 5; //5mb
		String enc = "UTF-8";
		DefaultFileRenamePolicy dp = new DefaultFileRenamePolicy();
		
		MultipartRequest multi = new MultipartRequest(request,path,size,enc,dp);
		
		RentDto dto = new RentDto();
		dto.setBr_code(multi.getParameter("br_code"));   //���� ���ο� �ڵ� ����
	    dto.setBr_name(multi.getParameter("br_name"));
	    dto.setBr_pub(multi.getParameter("br_pub"));
	    dto.setBr_writer(multi.getParameter("br_writer"));
	    dto.setBr_sname(multi.getParameter("br_sname"));
	    dto.setBr_cont(multi.getParameter("br_cont"));
	    dto.setD_bno(Integer.parseInt(multi.getParameter("d_bno")));
	    dto.setBr_don(Integer.parseInt(multi.getParameter("br_don")));
	    dto.setBr_admin(Integer.parseInt(multi.getParameter("br_admin")));
	    dto.setBr_wait(Integer.parseInt(multi.getParameter("br_wait")));      
	      
	    dto.setBr_thumpic(multi.getFilesystemName("save"));
		
	    RentDao dao = RentDao.getInstance();
	    dao.insertBook(dto);//���� ���� �Է�
	    dao.insOver(dto.getBr_code()); //�����ڵ忡 �´� ��ü ���ڵ� ����
	    
	    
	    request.setAttribute("view_type", "list_cont");
	    
	    

		return "/d_rent/rent_wrap.jsp";
	}
	
	
	
}
