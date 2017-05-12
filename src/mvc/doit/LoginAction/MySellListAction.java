package mvc.doit.LoginAction;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mvc.doit.Account.AcDao;
import mvc.doit.Account.AcDto;
import mvc.doit.Delivery.DeliveryDao;
import mvc.doit.Online.OnDao;
import mvc.doit.SuperAction.SuperAction;

public class MySellListAction implements SuperAction {
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception{
		
	//---- ���� ���� --------------------------------------------------
		HttpSession session = request.getSession();
		String d_id = (String)session.getAttribute("memId"); 
		String pageNum = request.getParameter("pageNum");//������ ��ȣ
        if (pageNum == null) {
            pageNum = "1"; //1�������� 5���� å ������
        }
        int pageSize = 50;//�� �������� ���� ���� //�ѹ��� �Ǹ����� å�� ������ ���� �ʴٰ� �����Ͽ� 5���� ����
        int currentPage = Integer.parseInt(pageNum); //��������ȣ�� Int �� ������ ��
        int startRow = (currentPage - 1) * pageSize + 1;//�� �������� ���۱� ��ȣ, ���� �� ��ȣ 1
        int endRow = currentPage * pageSize;//�� �������� ������ �۹�ȣ, ������ ��ϱ۹�ȣ 5
        int count = 0; //���� �ʱ�ȭ
        int number = 0; //���� �ʱ�ȭ
        
        //---- List ���� ���� ---------
        int d_sfinish = -1;
        if(request.getParameter("d_sfinish") != null){
        	d_sfinish = Integer.parseInt(request.getParameter("d_sfinish"));
        }
        int delivery = -1;
        if(request.getParameter("delivery") != null){
        	delivery = Integer.parseInt(request.getParameter("delivery"));
        }
	 	int d_bcode = -1;
	 	if(request.getParameter("d_bcode") != null){
	 		d_bcode = Integer.parseInt(request.getParameter("d_bcode"));
	 	}
        
	 	

	 	
        List articleList = null; //����Ʈ �ʱ�ȭ
        OnDao dao = OnDao.getInstance();//DB����
        
//---- ��� ��Ȳ ó�� Dao ----
        //---- ��۰��� 
        DeliveryDao ddao = DeliveryDao.getInstance();
        //---- delivery == 2 (��۽����� ��������), �����Ȳ d_bdelivery==12 (�����)
	 	if(delivery == 2){
	 		ddao.User_SellBook_delivertStart(d_bcode);
	 	}else{}  
        
//---- List ���� dao count--------------
        if(d_sfinish == -1){
        	count = dao.getD_bmySellingCount(d_id);//��ü ���� �� 
        }
//        else if(d_sfinish == 1){
//        	count = dao.getD_bmySellingFinishCount(d_id);//��ü ���� �� 
//        }
        
//---- List ���� dao List----------------
        if(count > 0){
        	if(d_sfinish == -1){
        		articleList = dao.getD_bMySellingList(d_id, startRow, endRow);//���� �������� �ش��ϴ� �� ���
        	}
//        	else if(d_sfinish == 1){
//        		 articleList = dao.getD_bMySellingFinishList(d_id, startRow, endRow);//���� �������� �ش��ϴ� �� ���
//        	}
 
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
		
		
		
        //�ش� �信�� ����� �Ӽ�
	    request.setAttribute("currentPage", new Integer(currentPage));
        request.setAttribute("startRow", new Integer(startRow));
        request.setAttribute("endRow", new Integer(endRow));
        request.setAttribute("count", new Integer(count));
        request.setAttribute("pageSize", new Integer(pageSize));
		request.setAttribute("number", new Integer(number));
        request.setAttribute("articleList", articleList);

		
	
		
		return "/d_login/mySellList.jsp";
	}
}
