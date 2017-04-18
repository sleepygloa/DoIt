package mvc.doit.LoginAction;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mvc.doit.Account.AcDao;
import mvc.doit.Account.AcDto;
import mvc.doit.Login.LoginDao;
import mvc.doit.Login.LoginDto;
import mvc.doit.SuperAction.SuperAction;

public class MyCashAction implements SuperAction{

	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		//�ѱ� ���ڵ�
		request.setCharacterEncoding("UTF-8");
		
		//ĳ�� ���� ����
		HttpSession session = request.getSession();
		String d_id = (String)session.getAttribute("memId"); //���� ����
		
		LoginDao manager = LoginDao.getInstance();
		LoginDto ldto = manager.getMember(d_id); //Dto ����
		request.setAttribute("ldto", ldto); //ȸ������ ����

		int d_no = ldto.getD_no();
		AcDao adao = AcDao.getInstance();

		
///------------------------------------------------------------------------- D_cash �Է� , ��� -------------------------------------------------------------------------------------------//		
		
		//�Ա� ����� �ϱ� ���� �ݾ��� �޾ƿ�.
		int d_acMyMoney = 0; //�Աݾ�, ��ݾ�
		if(request.getParameter("d_acMyMoney") != null){
			d_acMyMoney = Integer.parseInt(request.getParameter("d_acMyMoney"));
		}
		
		int d_acRequest = 0;  //�Ա�,��� ����
		if(request.getParameter("d_acRequest") != null){
			d_acRequest = Integer.parseInt(request.getParameter("d_acRequest"));
		}
		
		
		//---- ���°� ���� ��� �޴� ��û(1)�� ���� �ű� ���¸� �����մϴ�.
		if(adao.getAccount(d_no) != null){
		/*
			//---- ���� ��� ��û�� ���� ��� �ű� ���¸� ������ݴϴ�.
			if(d_acRequest == 1){
				adao.insAccount(d_no);//�����߰�, ���̵������ ������ִ� �ڵ�������, ������ ���� �־��.
			}
			adto = adao.getAccount(d_no); 
		}else{
		*/
			//---- ���� �����ϱ� ��û(2)�� ���� ���¿� ���ϴ� ����ŭ ���� ���� ��ŵ�ϴ�. ���� ����ϱ� ��û(3)�� ���� ������ ���ϴ� ����ŭ ���� ���ҽ�ŵ�ϴ�.		
			if(d_acRequest == 2 || d_acRequest == 3){
					adao.MyMoneyToAccout(d_acMyMoney, d_no, d_acRequest); //�����
			}
			
			
		}	
		
///------------------------------------------------------------------------- D_cash �Է� , ��� ��-------------------------------------------------------------------------------------------//		
		
		
//-------------------------------------------------- ��������  dto�� ��ȯ�մϴ�. : �ڽ��� ���� ��� -------------------------------------------------------//
		AcDto adto = null;
		adto = adao.getAccount(d_no);
		request.setAttribute("adto", adto);
		
//-------------------------------------------------- ��������  dto�� ��ȯ�մϴ�. : �ڽ��� ���� ��� -------------------------------------------------------//
		
		
//------------------------------------------------- �ŷ� ������ Ȯ���ϱ� ���� List ���� �մϴ� -------------------------------------------------------------------------------------------//
		String pageNum = request.getParameter("pageNum");//������ ��ȣ
        if (pageNum == null) {
            pageNum = "1"; //1�������� 20���� å ������
        }
        
        int pageSize = 10;//�� �������� ���� ����
        int currentPage = Integer.parseInt(pageNum); //��������ȣ�� Int �� ������ ��
        int startRow = (currentPage - 1) * pageSize + 1;//�� �������� ���۱� ��ȣ, ���� �� ��ȣ 1
        int endRow = currentPage * pageSize;//�� �������� ������ �۹�ȣ, ������ ��ϱ۹�ȣ 20
        int count = 0;
        int number = 1;
        
        count = adao.dealSituationCount(d_no); //�α�ī��Ʈ 
		
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
        
		number=count-(currentPage-1)*pageSize;//�۸�Ͽ� ǥ���� �۹�ȣ
		request.setAttribute("number", number); //�۹�ȣ ����
        
		
		//----------------�α� ����Ʈ ����
		List accountList = null;
		if(count > 0){ 
			String colm = request.getParameter("colm"); //�÷��̸�
			if(colm == null){
				colm = "d_lsender";
			}
			
			String gua = request.getParameter("gua");//�ҷ��� ���а�
			int guaa = 3;
			if(gua != null){
				guaa = Integer.parseInt(gua);
			}
			
			String inout = request.getParameter("inout"); //����� ����
			int inoutt = 1; //�Ա�
			if(inout != null){
				inoutt = Integer.parseInt(inout);
			}
			
			accountList = adao.getLog(d_no, colm, guaa, inoutt ,startRow, endRow);
			
			//���� ������Ʈ
			if(d_acRequest == 2){ //�Ա�
				adao.upTMon(d_acMyMoney, d_no);
			}else{
				adao.upTMon(-d_acMyMoney, d_no);
			}
			
		}
		
		
		request.setAttribute("accountList", accountList); //�α� ����Ʈ


		
		return "/d_login/my_cash.jsp";
	}
	
}
