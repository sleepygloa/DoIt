package mvc.doit.CustomerAction;

import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mvc.doit.Customer.CustomerDao;
import mvc.doit.Login.LoginDao;
import mvc.doit.Login.LoginDto;
import mvc.doit.SuperAction.SuperAction;

public class Customer_InquireListAction implements SuperAction {
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
	 	request.setCharacterEncoding("utf-8");
	 	
	 	String reply = request.getParameter("reply");
	 	
	 	HttpSession session = request.getSession();
		int br_no = 0;
		
		if(session.getAttribute("memNo") != null){
			br_no = (int)session.getAttribute("memNo");
		}; //ȸ�� ��ȣ 
		
		 int pageSize = 10;
		  String pageNum = request.getParameter("pageNum"); //
		    if (pageNum == null) { //Parameter�� null�϶� ����
		        pageNum = "1";
		    }

		    // �˻��� ���� ����, ���� ��� ����
		    int currentPage = Integer.parseInt(pageNum);
		    int startRow = (currentPage - 1) * pageSize + 1; //
		    int endRow = currentPage * pageSize; //
		    int count = 0;
		    int number=0;

        List articleList = null;
        CustomerDao dbPro = CustomerDao.getInstance();//DB����
      
        if(reply.equals("wait")){
        	int re = 0;
        	count = dbPro.adminInquireListCount(re);//��ü ���� �� 
	        if (count > 0) {
	            articleList = dbPro.adminInquireList(startRow, endRow,re);//���� �������� �ش��ϴ� �� ���
	        }
	       
        }else if(reply.equals("finish")){
        	int re = 1;
        	count = dbPro.adminInquireListCount(re);//��ü ���� �� 
	        if (count > 0) {
	            articleList = dbPro.adminInquireList(startRow, endRow,re);//���� �������� �ش��ϴ� �� ���
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
	
     	request.setAttribute("reply", reply);
	
	return "/d_customer/customer_InquireList.jsp";
	}

}
