package mvc.doit.AdminAction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mvc.doit.Online.OnBookDto;
import mvc.doit.Online.OnBookIntroDto;
import mvc.doit.Online.OnDao;
import mvc.doit.Online.OnInspectionDto;
import mvc.doit.SuperAction.SuperAction;

public class OnBookAction implements SuperAction {
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		request.setCharacterEncoding("utf-8");
		
//---- ���� ���� -----	
		int d_bcode = Integer.parseInt(request.getParameter("d_bcode"));
		
		OnDao dao = OnDao.getInstance();
		OnBookDto dto = dao.Admin_Onbook(d_bcode);
//---- �˼� ����� ������ ������ִ� ���� ----S:80%, A:60%, B:40% ���� ( 100���� ���� )	
		int d_itotal = dto.getD_itotal();
		int d_bvalue = dto.getD_bvalue();
		int d_bpurchasevalue = dto. getD_bpurchasevalue(); //0
		int d_bsellvalue = dto.getD_bsellvalue(); //0
		String d_bgrade = "";
		
		if(d_itotal == 0){
			d_bgrade = "S"; // ������ 80�� ����
			d_bpurchasevalue = (int)((double)d_bvalue * 0.8)/100 * 100; //������ 80������ �ʿ����� ���� ����
			d_bsellvalue = d_bvalue;
		}else if(d_itotal <=4 ){
			d_bgrade = "A"; // ������ 60�� ����
			d_bpurchasevalue = (int)((double)d_bvalue * 0.6)/100 * 100; //������ 60%������ �ʿ����� ���� ����
			d_bsellvalue = ((int)((double)d_bpurchasevalue * 1.2)/100+1)*100; //���Ű��� 20% ���� ������ õ������ �ø�
		}else if(d_itotal <=8 ){
			d_bgrade = "B"; // ������ 40�� ����
			d_bpurchasevalue = (int)((double)d_bvalue * 0.4)/100 * 100; //������ 40%������ �ʿ����� ���� ����
			d_bsellvalue = ((int)((double)d_bpurchasevalue * 1.2)/100+1)*100; //���Ű��� 20% ���� ������ õ������ �ø�
		}else if(d_itotal <=12){
			d_bgrade = "���ԺҰ�"; // ���ԺҰ�, �ǸŰ� �Ұ����ϱ� ����
			d_bpurchasevalue = 0;
			d_bsellvalue = 0;
		}

		request.setAttribute("d_bgrade", d_bgrade);
		request.setAttribute("d_bpurchasevalue", d_bpurchasevalue);
		request.setAttribute("d_bsellvalue", d_bsellvalue);

//--------------------����, �Ұ��� �ҷ�����------------------------------
		OnBookIntroDto obiDto =	dao.Admin_OnBookIntro_load(d_bcode);
		
		request.setAttribute("d_bcode", d_bcode);
		request.setAttribute("dto", dto);
		request.setAttribute("obiDto",obiDto);

		

		
	
	
		return "/d_manage/man_part4_1.jsp";
	}

}
