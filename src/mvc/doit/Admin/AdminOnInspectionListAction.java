package mvc.doit.Admin;

import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mvc.doit.Delivery.DeliveryDao;
import mvc.doit.Online.OnDao;
import mvc.doit.SuperAction.SuperAction;

public class AdminOnInspectionListAction implements SuperAction {
	public String execute(HttpServletRequest request, HttpServletResponse reponse) throws Exception{
			
		//---- ���� ���� ----------------------------------------------
	    int pageSize = 5;
	
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
	    int currentPage = Integer.parseInt(pageNum);
	    int startRow = (currentPage - 1) * pageSize + 1; //
	    int endRow = currentPage * pageSize; //
	    int count = 0;
	    int number=0;
	    
	    

	    
	    //---- List ---- count:d_sfinish==1 (�ǸŰ����� å count), list: 3table(d_onBook, d_onSellList, d_bdelivery) �ǸŰ�����(d_sfinish==1) å List
	    List articleList = null;
        OnDao article = OnDao.getInstance();//DB����
        count = article.Admin_InspectionCount();//��ü ���� �� 

        if(count > 0){
        		articleList = article.Admin_InspectionList(startRow,endRow, d_bcode);//���� �������� �ش��ϴ� �� ���
        }
        
        //---- page size ----
		number=count-(currentPage-1)*pageSize;//�۸�Ͽ� ǥ���� �۹�ȣ
		
		//----
        request.setAttribute("currentPage", new Integer(currentPage));
        request.setAttribute("startRow", new Integer(startRow));
        request.setAttribute("endRow", new Integer(endRow));
        request.setAttribute("count", count);
        request.setAttribute("pageSize", new Integer(pageSize));
		request.setAttribute("number", new Integer(number));
        request.setAttribute("articleList", articleList);
		
		
		
		
		return "/d_admin/adminOnInspectionList.jsp";
	}

}
