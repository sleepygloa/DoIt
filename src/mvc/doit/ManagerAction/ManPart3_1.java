package mvc.doit.ManagerAction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mvc.doit.Manager.ManDao;
import mvc.doit.Manager.ManDto;
import mvc.doit.SuperAction.SuperAction;

public class ManPart3_1 implements SuperAction{

	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		//�ѱ� ���ڵ� 
		request.setCharacterEncoding("UTF-8");
		
		//�Ķ���� ����
		String br_code = "";
		
		int gugu = Integer.parseInt(request.getParameter("gugu")); //��ü(1), �Ѱ� ���а�(0) 
		if(gugu != 1 || gugu == 0){ //�Ѱ��϶�
			br_code = request.getParameter("br_code"); //�����ڵ�
		}
		
		//manager ��üȭ
		ManDao mdao = ManDao.getInstance();
		
		//��ü ����
		ManDto mdto = new ManDto();
		mdto = mdao.getDashM();
		request.setAttribute("dashM", mdto);
		
		//���� ��üó��
		mdao.modiLib(br_code, gugu);
		
		//�ش� ȸ�� ��ü�� ó��
		mdao.modiPers();
		
		return "/d_manage/manPart3.do";
	}

}











