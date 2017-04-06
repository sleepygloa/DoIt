package mvc.doit.OnlineAction;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mvc.doit.Login.mySellingListDto;
import mvc.doit.Online.OnBookDto;
import mvc.doit.Online.OnDao;
import mvc.doit.Statistics.StatisticsDto;
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
		if(request.getParameter("select") != null){
			select = request.getParameter("select");
		}
		
        List articleList = null; //����Ʈ �ʱ�ȭ
        List avgSellvalue = null;
        OnDao dao = OnDao.getInstance();//DB����

        String selectBookFullNameReturn = null;
//--------------0. ��ü ����Ʈ  ��ȯ -----------------------------------------------------
        if(d_bonFillter == null && select == null){
            count = dao.getD_BSellCount();//��ü ���� �� 
            if(count > 0){
                articleList = dao.getD_BSellList(startRow, endRow);//���� �������� �ش��ϴ� �� ���
            }
//--------------1. å �帣 �������� ����Ʈ  ��ȯ----------------------------------------------    	        	
    	}else if(d_bonFillter != null){
            count = dao.getD_BSellCount(d_bonFillter ,d_bonFillterReturn);//��ü ���� �� 
            if(count > 0){
                articleList = dao.getD_BSellList(d_bonFillter, d_bonFillterReturn, startRow, endRow);//���� �������� �ش��ϴ� �� ���
            }
//-------------2. ���� �˻�(text)�� ����Ʈ ��ȯ----------------------------------------------
    	}else if(select != null){
            count = dao.getD_BSelectCount(select);//��ü ���� �� 
            if(count > 0){
                articleList = dao.getD_BSelectList(select, startRow, endRow);//���� �������� �ش��ϴ� �� ���
                //ū���� �̸��� '�˻��� + ����Ʈ'�� �����ϴ� ����, selectBookFullNameReturn �� ��ȯ�մϴ�. 
                selectBookFullNameReturn = dao.findSelectToBookFullName(select);
            }	
    	}        
        
        
        
//-------------3. ���� ���� å �� ---------------------------------------------------------
        String todayPurchaseCount = "";
        String[] todayPurchaseCountArray = null;
        int digitCheck = 0;
        String Thousand = "0";
        String hundred = "0";
        
        if(dao.getD_BTodayCount() == 0){}else{
        	
        	digitCheck = dao.getD_BTodayCount();
        	
        	if(digitCheck >= 0 && digitCheck < 10){
        		todayPurchaseCount += Thousand + hundred + digitCheck;
        	}else if(digitCheck >= 10 && digitCheck < 100){
        		todayPurchaseCount += Thousand + digitCheck;
        	}else{
        		todayPurchaseCount += digitCheck;
        	}
    		todayPurchaseCountArray = todayPurchaseCount.split("(?<=\\G.{" + 1 + "})");
        }
        



//-------------4. å  ��հ� ��ȯ ----------------------------------------------------------
        //���ʿ��ϰ� ������ List�� �ƴ� å ��ü DB�� å�� ��հ��� ��� List
        String getD_bname = null;
        OnBookDto dto = null;
        int d_bavgsellvalue = 0;
        int articleListSize = articleList.size();
     	for(int i = 0; i < articleListSize; i++){
     		dto = ((OnBookDto)articleList.get(i));
     				getD_bname = dto.getD_bname();
     				d_bavgsellvalue = dao.getBookNameToAvgSellValue(getD_bname);
     				dto.setD_bavgsellvalue(d_bavgsellvalue);
     	}
		number=count-(currentPage-1)*pageSize;//�۸�Ͽ� ǥ���� �۹�ȣ
//--------------5. ��ȯ --------------------------------------------------------


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
        request.setAttribute("selectBookFullNameReturn", selectBookFullNameReturn);

        
        
        
        return "/d_online/onSellBook.jsp";//�ش� ��
	}
}
