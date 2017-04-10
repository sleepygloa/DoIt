package mvc.doit.Admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mvc.doit.Account.AcDao;
import mvc.doit.Online.OnDao;
import mvc.doit.SuperAction.SuperAction;

public class AdminOnPurchasePaymentListAction implements SuperAction {
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
//------------0. ����κ� -------------------------------------------------------------		
	String pageNum = request.getParameter("pageNum");//������ ��ȣ
    if (pageNum == null) {
        pageNum = "1"; //1�������� 20���� å ������
    }
    int pageSize = 20;//�� �������� ���� ����
    int currentPage = Integer.parseInt(pageNum); //��������ȣ�� Int �� ������ ��
    int startRow = (currentPage - 1) * pageSize + 1;//�� �������� ���۱� ��ȣ, ���� �� ��ȣ 1
    int endRow = currentPage * pageSize;//�� �������� ������ �۹�ȣ, ������ ��ϱ۹�ȣ 20
    int count = 0; //���� �ʱ�ȭ
    int number = 0; //���� �ʱ�ȭ
	
    
    //------------1. DB ���� ------------------------------------------------------
    List paymentList = null; //����Ʈ �ʱ�ȭ
    AcDao adao = AcDao.getInstance();//DB����
    
    //------------2. dao�� �ʿ��� d_no ã��--------------------------------------------
    if(request.getParameter("pay_send") == null){
    }else{
    	int d_lno = 0;
    	if(request.getParameter("d_lno") != null){
    		d_lno = Integer.parseInt(request.getParameter("d_lno"));
    	}
    	
    	//���� ���� ��ư�� ��������, d_log�� ���ڵ带 update�մϴ�.
    	adao.updateAccountLog(d_lno);
    }
    		
  //--------------2. ��ü ����Ʈ  ��ȯ -----------------------------------------------------
    int aoppl = 0;
    if(request.getParameter("aoppl") != null){
    	aoppl = Integer.parseInt(request.getParameter("aoppl"));  
    }

    if(aoppl == 0){
        count = adao.getD_sPayListCount();//��ü ���� �� 
        if(count > 0){
        	paymentList = adao.getD_sPayList(startRow, endRow);//���� �������� �ش��ϴ� �� ���

    	} 
    }else if(aoppl == 1){
        count = adao.getD_sNoPayListCount();//��ü ���� �� 
        if(count > 0){
        	paymentList = adao.getD_sNoPayList(startRow, endRow);//���� �������� �ش��ϴ� �� ���

    	}  
    }else if(aoppl == 2){
        count = adao.getD_sPayEndListCount();//��ü ���� �� 
        if(count > 0){
        	paymentList = adao.getD_sPayEndList(startRow, endRow);//���� �������� �ش��ϴ� �� ���

    	}  
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
        
    	request.setAttribute("aoppl", aoppl);
        
        request.setAttribute("count", count);
        request.setAttribute("paymentList", paymentList);

        request.setAttribute("currentPage", new Integer(currentPage));
        request.setAttribute("startRow", new Integer(startRow));
        request.setAttribute("endRow", new Integer(endRow));
        request.setAttribute("pageSize", new Integer(pageSize));
		request.setAttribute("number", new Integer(number));     
        
        
        
	return "/d_admin/adminOnPurchasePaymentList.jsp";

	}
}
