package mvc.doit.ManagerAction;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mvc.doit.Manager.ManDao;
import mvc.doit.Manager.ManDto;
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
		
		
		
			
		
		return "/d_manage/man_part1.jsp";
	}

}
