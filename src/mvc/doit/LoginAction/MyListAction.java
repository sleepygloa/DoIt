package mvc.doit.LoginAction;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mvc.doit.Cart.CartDao;
import mvc.doit.Login.LoginDto;
import mvc.doit.Online.OnDao;
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
	        
	        
	        //��������
	        int pageSize2 = 10;//��ȭ�� �Խù�����
	        
	        
	        String pageNum2 = request.getParameter("pageNum2");
	        if(pageNum2==null){
	           pageNum2 = "1";
	        }
	        
	        
	        int currentPage2 = Integer.parseInt(pageNum2); //��������ȣ
	        int startRow2 = (currentPage2 -1) * pageSize2 +1; 
	        int endRow2 = currentPage2 * pageSize2 +1; //������������ �������� ���������
	        int count2 = 0;
	        int number2 = 0;
	        
	        List articleList2 = null;
	        BidbookDao article2 = BidbookDao.getInstance();
	        
	        
	        //�˻�
	        String search2 = request.getParameter("search2");
	        if(search2 == null){
	           //��ü����
	           count2 = article2.getMyBidCount(id);
	           if(count2 > 0){ //���� ������ BidListȣ��
	              
	              articleList2 = article2.getMyBidList(id, startRow2, endRow2);
	           }
	        }else{
	           count2 = article2.BidMySearchCount(search2,id);
	           if(count2 >0){
	              articleList2 = article2.BidMySearch(search2, id, startRow2, endRow2);
	           }
	        }
	        
	        number2= count2 -(currentPage2-1)*pageSize2;
	        
	        LoginDto dto2=article2.getGrade(id);//ȸ��  ��� Ȯ���� ����
	        
	        request.setAttribute("currentPage2", new Integer(currentPage2));
	        request.setAttribute("startRow2", new Integer(startRow2));
	        request.setAttribute("endRow2", new Integer(endRow2));
	        request.setAttribute("count2", new Integer(count2));
	        request.setAttribute("pageSize2", new Integer(pageSize2));
	        request.setAttribute("number2", new Integer(number2));
	        request.setAttribute("articleList2", articleList2);
	        request.setAttribute("bid_id2", id);
	        request.setAttribute("dto2", dto2);
	        
	        
			srt = "my_list3";
			request.setAttribute("my_li", srt);
			
		//�����Ǹ� ��� ��ȸ ����Ʈ
		}else if(col.equals("dr_pan")){
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
	        count = article.User_BuyBook_Count(id);//��ü ���� �� 

	        if(count > 0){
	            articleList = article.User_BuyBookList(startRow,endRow,id);//���� �������� �ش��ϴ� �� ���
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
	        request.setAttribute("noo", "my_list2_1"); //����ΰ� �ֹ��ΰ�
	        
	        srt = "my_list2";
			request.setAttribute("my_li", srt);
		
			
		//�����Ǹ� ��� ����Ʈ
		}else if(col.equals("dr_cencel")){
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
		    request.setAttribute("noo", "my_list2_2"); //����ΰ� �ֹ��ΰ�
		    
		    srt = "my_list2";
			request.setAttribute("my_li", srt);
		}

		
		return "/d_login/my_list.jsp";
	}
	
}







