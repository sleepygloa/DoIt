package mvc.doit.ManagerAction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mvc.doit.Manager.ManDao;
import mvc.doit.Manager.ManDto;
import mvc.doit.Online.OnDao;
import mvc.doit.Rent.RentDao;
import mvc.doit.ResellBean.BidbookDao;
import mvc.doit.ResellBean.ResellbookDao;
import mvc.doit.SuperAction.SuperAction;

public class ManagerDash implements SuperAction {

	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		//�ѱ� ���ڵ�
		request.setCharacterEncoding("UTF-8");
		
		//���� ����
		HttpSession session = request.getSession();
		
		//��ü ����
		ManDao mdao = ManDao.getInstance();
		ManDto mdto = new ManDto();
		mdto = mdao.getDashM();
		request.setAttribute("dashM", mdto);
		//session.setAttribute("dashM", mdto);
		
		//�����ǸŹ�
		OnDao odao = OnDao.getInstance();
		int onlinePa = odao.getD_BSellCount();
		request.setAttribute("onlinePa", onlinePa);
		
		//������ ����
		RentDao rdao = RentDao.getInstance();
		int r_book1 = rdao.getArticleCount(0); //�������
		int r_book2 = rdao.getArticleCount(1); //�����Ϸ�
		int r_book3 = rdao.getArticleCount(3); //��⵵��
		request.setAttribute("r_bookCount", r_book1+r_book2+r_book3);
		
		
		//���ŷ� / ��� �Խ���
		ResellbookDao rbdao = ResellbookDao.getInstance();
		BidbookDao biddao = BidbookDao.getInstance();
		int resell = rbdao.getResellCount();
		int resell2 = biddao.getBidCount();
		request.setAttribute("resellPa", resell + resell2);
		
		return "/d_manage/manage_dash.jsp";
	}
	
}
