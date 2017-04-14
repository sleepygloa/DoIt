package mvc.doit.ManagerAction;

import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mvc.doit.Delivery.DeliveryDao;
import mvc.doit.Manager.ManDao;
import mvc.doit.Manager.ManDto;
import mvc.doit.Online.OnBookDto;
import mvc.doit.Online.OnDao;
import mvc.doit.Rent.RentDao;
import mvc.doit.ResellBean.ResellintroDao;
import mvc.doit.ResellBean.ResellintroDto;
import mvc.doit.SuperAction.SuperAction;

public class ManPart4 implements SuperAction{

	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		//�ѱ� ���ڵ�
		request.setCharacterEncoding("UTF-8");
		
		//����ó��
		HttpSession session = request.getSession();
		String id = (String)session.getAttribute("memId");
		
		//��ü ����
		ManDao mdao = ManDao.getInstance();
		ManDto mdto = new ManDto();
		mdto = mdao.getDashM();
		request.setAttribute("dashM", mdto);
		
		//�¶��� ���
		
		
		
		////�ǸŽ�û����Ʈ //�¶��� ���
		String sdfe = request.getParameter("sdfe"); //�Ǹų� �Ϸ��
		if(sdfe == null){
			sdfe = "pana"; //�Ǹ�
		}
		String wew = "";
		
		if(sdfe.equals("pana")){
			//�ǸŽ�û ����Ʈ
				//�ǸŽ�û����Ʈ //�¶��� ���
				 String pageNum = request.getParameter("pageNum"); //
				    if (pageNum == null) { //Parameter�� null�϶� ����
				        pageNum = "1";
				    }
				    int delivery = -1;
				    if(request.getParameter("delivery") != null){
				    	delivery = Integer.parseInt(request.getParameter("delivery"));
				    }
				    int d_bcode = -1;
				    if(request.getParameter("d_bcode") != null){
				    	d_bcode = Integer.parseInt(request.getParameter("d_bcode"));
				    }
				    
				    // �˻��� ���� ����, ���� ��� ����
					// �� ȭ�鿡 �����ִ� �Խñ�
				    int pageSize = 20;
				    int currentPage = Integer.parseInt(pageNum);
				    int startRow = (currentPage - 1) * pageSize + 1; //
				    int endRow = currentPage * pageSize; //
				    int count = 0;
				    int number=0;
				    
				    //---- �ǸŽ�û Ȯ�� dao -----
				    List articleList = null;
			        OnDao article = OnDao.getInstance();//DB����
			        
			        //---- ����: �ǸŽ�û�Ϸ��ư�� ��������, Dao
			        String sell_confirm = null;
			        if(request.getParameter("sell_confirm") != null){
			        	article.Admin_Sell_Confirm(d_bcode);
			        }
			        
				    //---- ����: ����� -> ��ۿϷ� : �����Ȳ(d_bdelivery=12)�϶�,  delivery == 13 --> �����Ȳ(��ۿϷ� 13)���� table update -----
				    DeliveryDao ddao = DeliveryDao.getInstance();
				    if(delivery == 13){
				    	ddao.User_SellBook_delivertEnd(d_bcode);
				    }	
				    
				    //---- list ----
			        count = article.Admin_SellCount();//��ü ���� �� 
			        if(count > 0){
			            articleList = article.Admin_SellList(startRow,endRow);//���� �������� �ش��ϴ� �� ���
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
			        
			        wew = "adminOnSellList";
		}else{ 
			//�ϷḮ��Ʈ
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
		        count = article.Admin_OnBookCount();//��ü ���� ��

		        if(count > 0){
		            articleList = article.Admin_OnBookList(startRow,endRow);//���� �������� �ش��ϴ� �� ���
		        }

				number=count-(currentPage-1)*pageSize;//�۸�Ͽ� ǥ���� �۹�ȣ
				
		        request.setAttribute("currentPage", new Integer(currentPage));
		        request.setAttribute("startRow", new Integer(startRow));
		        request.setAttribute("endRow", new Integer(endRow));
		        request.setAttribute("count", count);
		        request.setAttribute("pageSize", new Integer(pageSize));
				request.setAttribute("number", new Integer(number));
		        request.setAttribute("articleList", articleList);
		        
		        wew = "adminOnBookList";
		}
		request.setAttribute("paanad", wew); //�ҷ� �� ������
		
	    
		
		////�˼�����Ʈ
		
		String gum_li = request.getParameter("gum_li");//����Ʈ�� �۾����̳�
		if(gum_li == null){
			gum_li = "list"; //�˼�����Ʈ
		}
		
		if(gum_li.equals("list")){ //�˼�����Ʈ�϶�
			request.setAttribute("gum_list", "adminOnInspectionList");//�ҷ��� ������ : ����Ʈ
			
			int pageSize2 = 5;
			
		    String pageNum2 = request.getParameter("pageNum2"); //
		    if (pageNum2 == null) { //Parameter�� null�϶� ����
		        pageNum2 = "1";
		    }
		    int delivery2 = -1;
		    if(request.getParameter("delivery2") != null){
		    	delivery2 = Integer.parseInt(request.getParameter("delivery2"));
		    }
		    int d_bcode2 = -1;
		    if(request.getParameter("d_bcode2") != null){
		    	d_bcode2 = Integer.parseInt(request.getParameter("d_bcode2"));
		    }
	
		    // �˻��� ���� ����, ���� ��� ����
		    int currentPage2 = Integer.parseInt(pageNum2);
		    int startRow2 = (currentPage2 - 1) * pageSize2 + 1; //
		    int endRow2 = currentPage2 * pageSize2; //
		    int count2 = 0;
		    int number2=0;
		    
		    
	
		    
		    //---- List ---- count:d_sfinish==1 (�ǸŰ����� å count), list: 3table(d_onBook, d_onSellList, d_bdelivery) �ǸŰ�����(d_sfinish==1) å List
		    List articleList2 = null;
	        OnDao article2 = OnDao.getInstance();//DB����
	        count2 = article2.Admin_InspectionCount();//��ü ���� �� 
	
	        if(count2 > 0){
	        		articleList2 = article2.Admin_InspectionList(startRow2,endRow2, d_bcode2);//���� �������� �ش��ϴ� �� ���
	        }
	        
	        //---- page size ----
			number2=count2-(currentPage2-1)*pageSize2;//�۸�Ͽ� ǥ���� �۹�ȣ
			
			//----
	        request.setAttribute("currentPage2", new Integer(currentPage2));
	        request.setAttribute("startRow2", new Integer(startRow2));
	        request.setAttribute("endRow2", new Integer(endRow2));
	        request.setAttribute("count2", count2);
	        request.setAttribute("pageSize2", new Integer(pageSize2));
			request.setAttribute("number2", new Integer(number2));
	        request.setAttribute("articleList2", articleList2);
        
		}else{//�˼�����Ʈ üũ ���
			
			int d_bcode2 = Integer.parseInt(request.getParameter("d_bcode2"));
			
			OnDao dao = OnDao.getInstance();
			
				
			OnBookDto dto = dao.Admin_Inspection(d_bcode2);
			
			request.setAttribute("d_bcode", d_bcode2);
			request.setAttribute("d_bname", dto.getD_bname());
			request.setAttribute("d_bvalue", dto.getD_bvalue());
			
			request.setAttribute("gum_list", "onInspection");//�ҷ��� ������ : ����Ʈ
			
		}
		
			
			
		
		return "/d_manage/man_part4.jsp";
	}

}
