package mvc.doit.Admin;

import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mvc.doit.Delivery.DeliveryDao;
import mvc.doit.Online.OnDao;
import mvc.doit.SuperAction.SuperAction;

public class AdminOnSellListAction implements SuperAction {
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

	
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


	
	
	return "/d_admin/adminOnSellList.jsp";
}

}
