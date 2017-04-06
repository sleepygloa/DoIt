package mvc.doit.Admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mvc.doit.Delivery.DeliveryDao;
import mvc.doit.Online.OnBookDto;
import mvc.doit.Online.OnDao;
import mvc.doit.SuperAction.SuperAction;

public class AdminOnBuyFinishAction implements SuperAction {
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
	 	request.setCharacterEncoding("utf-8");
	 	
	 	int d_bcode = Integer.parseInt(request.getParameter("d_bcode"));

	 	int delivery = Integer.parseInt(request.getParameter("delivery"));
	 	
	 	DeliveryDao dao = DeliveryDao.getInstance();
	 	
//---- ��۰��� 
	 	int d_bdelivery =-1;
	 	if(delivery == 0){
	 		dao.Admin_OnBuyBook_finish(d_bcode);
	 		d_bdelivery = 0;
	 	}else if(delivery == 1){
	 		dao.Admin_OnBuyBook_delivertStart(d_bcode);
	 		d_bdelivery = 1;
	 	}else if(delivery == 2){
	 		dao.Admin_OnBuyBook_delivertEnd(d_bcode);
	 		d_bdelivery = 2;
	 	}else if(delivery == 4){
	 		dao.Admin_OnBuyBook_CancelCheck(d_bcode);
	 		d_bdelivery = 4;
	 	}
	 	
	 	OnDao odao = OnDao.getInstance();
		//---- ȸ���� ȸ���� ����� �ľ��ϰ�, ���� �Ǵ� ��� ����� ��ŵ�ϴ�.
	 	String Check = "d_bcode";
	 	String id = null;
	 	String userGradeCheck = odao.getUserSellPurchaseCountToGrade(d_bcode, id, Check); //d_bcode�� ��� ����� å�� ������ �ҷ���
	//å�ڵ� d_bcode�� ȸ���� ���̵� �ҷ��ɴϴ�.
	 	OnBookDto dto = new OnBookDto();
	 	Check = "";  //������ ���� ������������ '�ǸſϷ��ư'�� ȸ�� ���̵� �޾ƿð�
	 	dto = odao.getOnBookArticle(d_bcode, Check);
	 	String d_id = dto.getD_id();
	 	
	 	
	 	
	 	
	 	request.setAttribute("d_bdelivery", d_bdelivery);
	 	request.setAttribute("userGradeCheck", userGradeCheck);
	 	request.setAttribute("d_id", d_id);
	
	 	
	return "/d_admin/adminOnBuyFinish.jsp";
}

}
