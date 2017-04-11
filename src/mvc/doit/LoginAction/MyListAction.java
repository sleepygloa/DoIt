package mvc.doit.LoginAction;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mvc.doit.Cart.CartDao;
import mvc.doit.ResellBean.BidbookDao;
import mvc.doit.ResellBean.ResellbookDao;
import mvc.doit.SuperAction.SuperAction;

public class MyListAction implements SuperAction{

	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		//�ѱ� ���ڵ�
		request.setCharacterEncoding("UTF-8");
		
		//���� ��¥ �ҷ�����
		Date date = new Date();
		SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy. MM. dd");
		String das = simpleDate.format(date);
		request.setAttribute("SimpleDate", das);
		
		//���� �� �ҷ�����
		String col = request.getParameter("cols");
		String srt = ""; //���� ������ �̸�
		
		//���� �� ��������
		HttpSession session = request.getSession();
		int br_no = (int)session.getAttribute("memNo"); //ȸ�� ��ȣ 
		
		//��ü ����
		CartDao cdo = CartDao.getInstance(); //��ٱ��� ��ü ����

		//������ ����Ʈ ��ü
		List getE = new ArrayList();
		
		if(col.equals("dr_rent")){
			//----------------------- ������ ���� ��Ʈ ----------------------
			//å ���� �߰�
			getE = cdo.getHeadCart(br_no,"dr_rent");
			request.setAttribute("getE", getE);
			
			srt = "my_list1";
			request.setAttribute("my_li", srt);
		}else if(col.equals("dr_resell")){
			//----------------------- ���ŷ� �Խ��� ----------------------
			//���ɻ�ǰ
			int pageSize = 10;
			
			String pageNum = request.getParameter("pageNum"); //
		    if (pageNum == null) { //Parameter�� null�϶� ����
		        pageNum = "1";
		    }
			
		    int currentPage = Integer.parseInt(pageNum);
		    int startRow = (currentPage - 1) * pageSize + 1; //
		    int endRow = currentPage * pageSize; //
		    int count = 0;
		    int number=0;
		    
		    List articleList = null;
		    ResellbookDao dbPro = ResellbookDao.getInstance();
		    
		    String id =(String)session.getAttribute("memId");
		    count = dbPro.getMyScrapCount(id);
		    
		    if(count > 0){
		    	articleList = dbPro.getMyScrapList(id);
		    }
		    
		    number=count-(currentPage-1)*pageSize;

			request.setAttribute("id",id);
			request.setAttribute("currentPage", currentPage);
	        request.setAttribute("startRow", startRow);
	        request.setAttribute("endRow", endRow);
	        request.setAttribute("count", count);
	        request.setAttribute("pageSize", pageSize);
			request.setAttribute("number", number);
	        request.setAttribute("articleList", articleList);
	        

	        /////////////////��������
	        int pageSize1 = 10;
			
			String pageNum1 = request.getParameter("pageNum1"); //
			//int bid_no = Integer.parseInt(request.getParameter("pageNum"));
		    if (pageNum1 == null) { //Parameter�� null�϶� ����
		        pageNum1 = "1";
		    }
			
		    int currentPage1 = Integer.parseInt(pageNum1);
		    int startRow1 = (currentPage1 - 1) * pageSize1 + 1; //
		    int endRow1 = currentPage1 * pageSize1; //
		    int count1 = 0;
		    int number1=0;
		    
		    List articleList1 = null;
		    BidbookDao dbPro1 = BidbookDao.getInstance();
		    
		    String id1 =(String)session.getAttribute("memId");
		    count1 = dbPro1.getBidSellerCount(id1);
		    
		    if(count1 > 0){
		    	articleList1 = dbPro1.getBidSellerList(id1);
		    }
		    
		    number1=count1-(currentPage1-1)*pageSize1;
		    
			request.setAttribute("id1",id1);
			request.setAttribute("currentPage1", currentPage1);
	        request.setAttribute("startRow1", startRow1);
	        request.setAttribute("endRow1", endRow1);
	        request.setAttribute("count1", count1);
	        request.setAttribute("pageSize1", pageSize1);
			request.setAttribute("number1", number1);
	        request.setAttribute("articleList1", articleList1);
	        
			srt = "my_list3";
			request.setAttribute("my_li", srt);
		}

		
		return "/d_login/my_list.jsp";
	}
	
}







