package mvc.doit.ManagerAction;

import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mvc.doit.Manager.ManDao;
import mvc.doit.Manager.ManDto;
import mvc.doit.Online.OnDao;
import mvc.doit.Rent.RentDao;
import mvc.doit.ResellBean.ResellintroDao;
import mvc.doit.ResellBean.ResellintroDto;
import mvc.doit.SuperAction.SuperAction;

public class ManPart1 implements SuperAction{

	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		//�ѱ� ���ڵ�
		request.setCharacterEncoding("UTF-8");
		
		//��ü ����
		ManDao mdao = ManDao.getInstance();
		ManDto mdto = new ManDto();
		mdto = mdao.getDashM();
		request.setAttribute("dashM", mdto);
		
		//����ó��
		HttpSession session = request.getSession();
		String id = (String)session.getAttribute("memId");
		
		//�ҷ��� ���� ��ϱ���
		String guga1 = request.getParameter("guga");
		int guga = 10;
		if(guga1 != null){
			guga = Integer.parseInt(guga1);
		}
		
		//���º���
		String mmod= request.getParameter("mmod");
		if(mmod != null){ //���º����Ұǰ�?
			if(mmod.equals("yes")){//���� �����ΰ�?
				String br_code = request.getParameter("br_code");
				mdao.modiDeliv(guga, br_code);
			}else{
				mdao.modiDeliv(guga, "");
			}	
		}
		
		//��� ����
		List DeliCont = mdao.getDeliCont(guga);
		request.setAttribute("DeliCont", DeliCont);
		request.setAttribute("guw", guga); //���� ���� ����-�ϰ�ó����
		
		
		
		/// �¶��� �Ǹ�
		String cenList = request.getParameter("cenList"); //����(sell) ���(cell)
		if(cenList == null){
			cenList = "sell";
		}
		
		if(cenList.equals("sell")){ //����
			request.setAttribute("man_part_li", "man_part1_1");
			
			//����� ���� - ���Ž�û, �Ϸ�
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
//		        count = article.Admin_SellCount();//��ü ���� �� 
		        
		        //���Ž�û(yyess) , ���ſϷ�(nooo)
		        String ynoo = request.getParameter("ynoo");
		        if(ynoo == null){
		        	ynoo = "yyess";
		        }
		        String sss = "";
		        
		        if(ynoo.equals("yyess")){//���� ��û(yyess)
		        	count = article.Admin_BuyBook_Count();//��ü ���� �� 
	 		        ///�ٲٱ�
	 		        if(count > 0){
	 		            articleList = article.Admin_BuyBookList(startRow,endRow);//���� �������� �ش��ϴ� �� ���
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
			        sss = "adminOnBuyBookList";//�ҷ��� ������
			        
		        }else{
		        	count = article.Admin_BuyBook_FinishList_Count();//��ü ���� �� 
		 		        ///�ٲٱ�
		 		        if(count > 0){
		 		            articleList = article.Admin_BuyBook_FinishList(startRow,endRow);//���� �������� �ش��ϴ� �� ���
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
		 		       sss = "adminOnBuyBookList_Finish"; //�ҷ��� ������
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
		        
		        request.setAttribute("temp_li2", sss); //��� �ҷ��� ������ �̸�
		        
			
		}else if(cenList.equals("cell")){ //���
			request.setAttribute("man_part_li", "man_part1_2");
			
			//����� ���� - ��ҽ�û, �Ϸ�
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
	        
	        //���뿡 ���� ����Ʈ ����
	        //��ҽ�û(yes) ��ҿϷ�(no)
			String result = request.getParameter("result");
			if(result == null){
				result = "yes";
			}
			String sss = "";//�ҷ��� ������ ����
	        ///////////��� ��û ����Ʈ//////////////
	        if(result.equals("yes")){ //��ҽ�û
		        count = article.Admin_BuyBook_CancelList_Count();//��ü ���� �� 
		        ///�ٲٱ�
		        if(count > 0){
		            articleList = article.Admin_BuyBook_CancelList(startRow,endRow);//���� �������� �ش��ϴ� �� ���
		        }
		        sss = "adminOnBuyList_Cancel";
		    }else{ //��ҿϷ�
	        //////��� �Ϸ� ����Ʈ
		        count = article.Admin_BuyBook_CancelFinish_Count();//��ü ���� �� 
			      ///�ٲٱ�
			      if(count > 0){
			          articleList = article.Admin_BuyBook_CancelFinishList(startRow,endRow);//���� �������� �ش��ϴ� �� ���
			      }
			      sss = "adminOnBuyList_CancelFinish";
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
	        
	        request.setAttribute("temp_li", sss); //��� �ҷ��� ������ �̸�
	        
		}
		
		
		
		
		
		
			
		
		return "/d_manage/man_part1.jsp";
	}

}
