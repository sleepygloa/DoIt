package mvc.doit.RentAction;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mvc.doit.Cart.CartDao;
import mvc.doit.Login.LoginDao;
import mvc.doit.Login.LoginDto;
import mvc.doit.Rent.RentDao;
import mvc.doit.Rent.RentDto;
import mvc.doit.SuperAction.SuperAction;

public class Rent_detailAction implements SuperAction {

	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//�ѱ� ���ڵ�
		request.setCharacterEncoding("UTF-8");

		/*  ���� ��ȣParameter  */
		String br_no = request.getParameter("br_no");

		/*  ��üȭ ����   */
		RentDao rdo = RentDao.getInstance(); //DAO ��ü ����
		LoginDao ldo = LoginDao.getInstance(); //ȸ�� ���� dao��ü
		CartDao cdao = CartDao.getInstance(); //��ٱ��� dao ��ü
		
		//�󼼺��� �⺻ ���� ���
		RentDto rto = rdo.getDetail(br_no);
		request.setAttribute("rto", rto);
		
		//����� ���� : ��� ��
		int personC = cdao.getPerson(rto.getBr_code());
		request.setAttribute("personC", personC);
		
		//�帣��ȣ�� ���� ���� ����Ʈ ���
		List jangList = rdo.getJang(rto.getD_bno(), br_no);
		request.setAttribute("jangList", jangList);
		
		
		//����� ���� ���
		String asd = ldo.getMemNo(rto.getBr_don());
		LoginDto login_dto = ldo.getMember(asd);
		if(login_dto == null){
			LoginDto login_dto1 = new LoginDto();
			login_dto1.setD_name("������");
			login_dto1.setD_mech_grade(10);
			login_dto1.setUser_phone1("1600");
			login_dto1.setUser_phone2("2222");
			login_dto1.setUser_phone3("[ ������ ]");
			request.setAttribute("login_dto1", login_dto1);
		}
		request.setAttribute("login_dto", login_dto);
		

		//����ڰ� ����� �ٸ� ���� ���
		//List gibuList = rdo.getGibu(login_dto.getD_no());
		List gibuList = null;
		if(login_dto != null){
			gibuList = rdo.getGibu(login_dto.getD_no());
		}else if(login_dto == null){
			gibuList = rdo.getGibu(0);
		}
		request.setAttribute("gibuList", gibuList);
		
		
		
		//=====//��� �ҷ����� 1========================================= 
		  
	      String pageNum = request.getParameter("pageNum");
	      
	      if(pageNum==null){
	         pageNum="1";
	      }
	      
	      int pageSize = 6; //��۰��� 6��
	      int currentPage = Integer.parseInt(pageNum);
	      int startRow = (currentPage-1)*pageSize+1;
	       int endRow = currentPage * pageSize;
	       int count = 0;
	       
	   //=====//��� �ҷ����� 2=========================================   
	      List re_List = null;
	      count = rdo.getReplyCount(br_no);//��ü ����� ��
	      
	      if (count > 0) {
	         re_List = rdo.getReply(br_no,startRow,endRow);//������ �� ���
	      }
	      
	      request.setAttribute("pageNum", new Integer(pageNum));
	      request.setAttribute("currentPage", new Integer(currentPage));
	      request.setAttribute("startRow", new Integer(startRow));
	      request.setAttribute("endRow", new Integer(endRow));
	      request.setAttribute("count", new Integer(count));
	      request.setAttribute("pageSize", new Integer(pageSize));
	      request.setAttribute("re_List", re_List);
	      
	      
		
		return "/d_rent/detail_cont.jsp";
	}//execute �޼��� ���� ��
}
