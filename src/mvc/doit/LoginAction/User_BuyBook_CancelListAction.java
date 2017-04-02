package mvc.doit.LoginAction;

import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mvc.doit.Online.OnDao;
import mvc.doit.SuperAction.SuperAction;

public class User_BuyBook_CancelListAction implements SuperAction {
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
	 	
	 				request.setCharacterEncoding("utf-8");
	 					
	 				 	HttpSession session = request.getSession();
	 					String id = (String)session.getAttribute("memId");
	 					
	 				 	
	 				 // �� ȭ�鿡 �����ִ� �Խñ�
	 				    int pageSize = 10;
	 				    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm"); // ��¥ ���� �ٲ㼭 ������
	 				
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
	 			      OnDao article = OnDao.getInstance();//DB����
	 			      count = article.User_BuyBook_CancelFinish_Count(id);//��ü ���� �� 
	 			      ///�ٲٱ�
	 			      if(count > 0){
	 			          articleList = article.User_BuyBook_CancelList(startRow,endRow,id);//���� �������� �ش��ϴ� �� ���
	 			      }
	 			
	 			      //�Խñ��� pagesize ���� Ŭ��, �� 1���������� ������ ������ ������ ��ȣ ���
	 			      if (count > 0) {
	 			          int pageCount = count / pageSize + ( count % pageSize == 0 ? 0 : 1);
	 			  		 
	 			          int startPage = (int)(currentPage/10)*10+1;
	 			  		int pageBlock=10;
	 			          int endPage = startPage + pageBlock-1;
	 			          if (endPage > pageCount) endPage = pageCount;
	 			          
	 			          request.setAttribute("pageCount", pageCount);
	 			          request.setAttribute("startPage", startPage);
	 			          request.setAttribute("endPage", endPage);
	 			          
	 			      }        
	 			      
	 					number=count-(currentPage-1)*pageSize;//�۸�Ͽ� ǥ���� �۹�ȣ
	 					
	 					
	 					
	 			    request.setAttribute("currentPage", new Integer(currentPage));
	 			    request.setAttribute("startRow", new Integer(startRow));
	 			    request.setAttribute("endRow", new Integer(endRow));
	 			    request.setAttribute("count", count);
	 			    request.setAttribute("pageSize", new Integer(pageSize));
	 				request.setAttribute("number", new Integer(number));
	 			    request.setAttribute("articleList", articleList);
	 			    request.setAttribute("id", id);
	
	return "/d_login/user_BuyBook_CancelList.jsp";
}

}
