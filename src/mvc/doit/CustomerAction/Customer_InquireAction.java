package mvc.doit.CustomerAction;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mvc.doit.Customer.CustomerDao;
import mvc.doit.Customer.InquireDto;
import mvc.doit.Login.LoginDao;
import mvc.doit.Login.LoginDto;
import mvc.doit.SuperAction.SuperAction;

public class Customer_InquireAction implements SuperAction {
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
	 	request.setCharacterEncoding("utf-8");
	 	
	 	HttpSession session = request.getSession();
		int b_no = (int)session.getAttribute("memNo"); //ȸ�� ��ȣ 
		
	 	String inquire = request.getParameter("inquire");
	 	String reply = request.getParameter("reply");
	 	String mem = request.getParameter("mem");
	 	
	 	if(inquire.equals("Inquire")){
	        	
	       int c_ino=0,ref=1,re_step=0,re_level=0;  

	        
	        LoginDao LDao = LoginDao.getInstance();
			String m_id = LDao.getMemNo(b_no);
			
			LoginDto LDto = LDao.getMember(m_id);
			
			request.setAttribute("c_ino", new Integer(c_ino));
	        request.setAttribute("ref", new Integer(ref));
	        request.setAttribute("re_step", new Integer(re_step));
	        request.setAttribute("re_level", new Integer(re_level));  
	        
			request.setAttribute("LDto", LDto);

	 		
	 		
	 	}else if(inquire.equals("Inquire_reply")){
	 		
	 		String pageNum = request.getParameter("pageNum");//������ ��ȣ

	        if (pageNum == null) {
	            pageNum = "1";
	        }
	        int pageSize = 10;//�� �������� ���� ����
	        int currentPage = Integer.parseInt(pageNum);
	        int startRow = (currentPage - 1) * pageSize + 1;//�� �������� ���۱� ��ȣ
	        int endRow = currentPage * pageSize;//�� �������� ������ �۹�ȣ
	        int count = 0;
	        int number=0;

	        List articleList = null;
	        CustomerDao dbPro = CustomerDao.getInstance();//DB����
	        
	        if(reply.equals("wait")){
	        	int re = 0;
		        count = dbPro.UserInquireCount(b_no, re);//��ü ���� �� 
		        
		        if (count > 0) {
		            articleList = dbPro.UserInquireList(startRow, endRow,b_no,re);//���� �������� �ش��ϴ� �� ���
		        }

	        }else if(reply.equals("finish")){
	        	int re = 1;
	        	 count = dbPro.UserInquireCount(b_no ,re);//��ü ���� �� 
			        
			        if (count > 0) {
			            articleList = dbPro.UserInquireList(startRow, endRow,b_no,re);//���� �������� �ش��ϴ� �� ���
			        }
			        
			
	        	
	        }
	        number=count-(currentPage-1)*pageSize;//�۸�Ͽ� ǥ���� �۹�ȣ
	        
			
	        //�ش� �信�� ����� �Ӽ�
	        request.setAttribute("currentPage", new Integer(currentPage));
	        request.setAttribute("startRow", new Integer(startRow));
	        request.setAttribute("endRow", new Integer(endRow));
	        request.setAttribute("count", new Integer(count));
	        request.setAttribute("pageSize", new Integer(pageSize));
			request.setAttribute("number", new Integer(number));
	        request.setAttribute("articleList", articleList);
	        request.setAttribute("count", count);	 	
	        
	 		
	 	}
	 	
	 	request.setAttribute("b_no", b_no);
	 	request.setAttribute("inquire", inquire);
	 	request.setAttribute("reply", reply);

	
	
	return "/d_customer/customer_Inquire.jsp";
	}

}
