package mvc.doit.OnlineAction;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mvc.doit.Login.mySellingListDto;
import mvc.doit.Online.OnDao;
import mvc.doit.SuperAction.SuperAction;

public class OnSellBookAction implements SuperAction {
	public String execute(HttpServletRequest request, HttpServletResponse response)  throws Exception{
		
		request.setCharacterEncoding("UTF-8");

		
		
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
        
//------------1. fillter ���� -----------------------------------------------------
        int d_bonFillterReturn = 0; //������������ �ٽ� ��ȯ�Ͽ� ����� ��        
        String d_bonFillter = null; //Dao���� ����� ��
        
        if(request.getParameter("d_bonFillter") != null){
        	d_bonFillter = request.getParameter("d_bonFillter");
        	d_bonFillterReturn = Integer.parseInt(request.getParameter("d_bonFillter"));
        	switch(d_bonFillter){
        	//String ���尪�� DB ���尪�� ��ġ.
        	case "0" : d_bonFillter = ""; break;
        	case "01" : d_bonFillter = "�Ҽ� / �� / ������"; break;
        	case "02" : d_bonFillter = "���� / ��������"; break;
        	case "03" : d_bonFillter = "��� ����"; break;
        	case "04" : d_bonFillter = "�ι��� ����"; break;
        	case "05" : d_bonFillter = "���� ��������"; break;
        	case "06" : d_bonFillter = "��Ÿ ����"; break;
        	case "07" : d_bonFillter = "��Ʈ��"; break;
        	case "08" : d_bonFillter = "����ũ��"; break;
        	case "09" : d_bonFillter = "�������"; break;
        	case "10" : d_bonFillter = "������"; break;
        	case "11" : d_bonFillter = "����"; break;
        	case "12" : d_bonFillter = "����"; break;
        	}
        };       

//------------2. ���հ˻� ����-------------------------------------------------------------
		String select = null;
		if(request.getParameter("select") != ""){
			select = request.getParameter("select");
		}
		
        List articleList = null; //����Ʈ �ʱ�ȭ
        OnDao article = OnDao.getInstance();//DB����
        

//  //--------------0. ��ü ����Ʈ  ��ȯ test �ߺ��� ���� -----------------------------------------------------
//        if(d_bonFillter == null && select == null){
//            count = article.getD_BSellCount();//��ü ���� �� 
//            if(count>0){
//                articleList = article.getD_BSellList(startRow, endRow);//���� �������� �ش��ϴ� �� ���
//            }        
        
        
        
//--------------0. ��ü ����Ʈ  ��ȯ -----------------------------------------------------
        if(d_bonFillter == null && select == null){
            count = article.getD_BSellCount();//��ü ���� �� 
            if(count>0){
                articleList = article.getD_BSellList(startRow, endRow);//���� �������� �ش��ϴ� �� ���
            }
//--------------1. å �帣 �������� ����Ʈ  ��ȯ----------------------------------------------    	        	
    	}else if(d_bonFillter != null){
            count = article.getD_BSellCount(d_bonFillter ,d_bonFillterReturn);//��ü ���� �� 
            if(count>0){
                articleList = article.getD_BSellList(d_bonFillter, d_bonFillterReturn, startRow, endRow);//���� �������� �ش��ϴ� �� ���
            }
//-------------2. ���� �˻�(text)�� ����Ʈ ��ȯ----------------------------------------------
    	}else if(select != null){
            count = article.getD_BSelectCount(select);//��ü ���� �� 
            if(count>0){
                articleList = article.getD_BSelectList(select, startRow, endRow);//���� �������� �ش��ϴ� �� ���
            }	
    	}        
        
        
        
//-------------3. ���� ���� å �� ---------------------------------------------------------
        String todayPurchaseCount = "";
        String[] todayPurchaseCountArray = null;
        int digitCheck = 0;
        String Thousand = "0";
        String hundred = "0";
        
        if(article.getD_BTodayCount() == 0){}else{
        	
        	digitCheck += article.getD_BTodayCount();
        	
        	if(digitCheck > 0 || digitCheck < 10){
        		todayPurchaseCount += Thousand + hundred + digitCheck;
        	}else if(digitCheck > 11 || digitCheck < 100){
        		todayPurchaseCount += Thousand + digitCheck;
        	}else{
        		todayPurchaseCount += digitCheck;
        	}
    		todayPurchaseCountArray = todayPurchaseCount.split("(?<=\\G.{" + 1 + "})");
        }
        
        
        
        
        

//--------------1. ��� ���Ű� �˻� �� ��ȯ --------------------------------------------------------

		number=count-(currentPage-1)*pageSize;//�۸�Ͽ� ǥ���� �۹�ȣ
        //�ش� �信�� ����� �Ӽ�
	    request.setAttribute("currentPage", new Integer(currentPage));
        request.setAttribute("startRow", new Integer(startRow));
        request.setAttribute("endRow", new Integer(endRow));
        request.setAttribute("count", new Integer(count));
        request.setAttribute("pageSize", new Integer(pageSize));
		request.setAttribute("number", new Integer(number));
        request.setAttribute("articleList", articleList);
        request.setAttribute("d_bonFillterReturn", d_bonFillterReturn);
        request.setAttribute("digitCheck", digitCheck);
        request.setAttribute("todayPurchaseCount", todayPurchaseCount);
        request.setAttribute("todayPurchaseCountArray", todayPurchaseCountArray);
//        request.setAttribute("d_bonFillterGenre", d_bonFillterGenre); //Ȯ�ο�, �ּ�ó��
//        request.setAttribute("d_bonFillterLocation", d_bonFillterLocation); //Ȯ�ο�, �ּ�ó��
//        request.setAttribute("d_bonFillterGenre2", d_bonFillterGenre2);//Ȯ�ο�, �ּ�ó��
//        request.setAttribute("fillterVar", fillterVar); //Ȯ�ο�, �ּ�ó��
//        request.setAttribute("select", select); //Ȯ�ο�, �ּ�ó��
//        request.setAttribute("avgPurchaseValue", avgPurchaseValue);
        
        
        return "/d_online/onSellBook.jsp";//�ش� ��
	}
}
