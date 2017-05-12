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

public class MyAccountListAction implements SuperAction {
	public String execute(HttpServletRequest request, 
			HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession();
		String d_id = (String)session.getAttribute("memId"); //���� ����
		
		LoginDao manager = LoginDao.getInstance();
		LoginDto ldto = manager.getMember(d_id); //Dto ����
		
		
		int d_no = ldto.getD_no();
		AcDto adto = null;
		AcDao adao = AcDao.getInstance();
		
		//�Ա� ����� �ϱ� ���� �ݾ��� �޾ƿ�.
		int d_acMyMoney = 0;
		int d_acRequest = 0;
		if(request.getParameter("d_acMyMoney") != null){
			d_acMyMoney = Integer.parseInt(request.getParameter("d_acMyMoney"));
		}
		if(request.getParameter("d_acRequest") != null){
			d_acRequest = Integer.parseInt(request.getParameter("d_acRequest"));
		}
		
		
		//---- ���°� ���� ��� �޴� ��û(1)�� ���� �ű� ���¸� �����մϴ�.
		if(adao.getAccount(d_no) == null){
			
			//---- ���� ��� ��û�� ���� ��� �ű� ���¸� ������ݴϴ�.
			if(d_acRequest == 1){
				adao.insAccount(d_no);

			}
			adto = adao.getAccount(d_no);
		}else{
			//---- ���� �����ϱ� ��û(2)�� ���� ���¿� ���ϴ� ����ŭ ���� ���� ��ŵ�ϴ�. ���� ����ϱ� ��û(3)�� ���� ������ ���ϴ� ����ŭ ���� ���ҽ�ŵ�ϴ�.		
			if(d_acRequest == 2 || d_acRequest == 3){
					adao.MyMoneyToAccout(d_acMyMoney, d_no, d_acRequest);
			}else{}	
			//---- ��������  dto�� ��ȯ�մϴ�.
			adto = adao.getAccount(d_no);
		}



		//---- �ܾ׸���� ���� d_log table���� d_ldealmoney�� �ջ� �մϴ�.
		int d_lsummoney = 0;		
		d_lsummoney = adao.getAccountSumMoney(d_no);
        if(d_lsummoney <= 0){
       	 d_lsummoney = 0;
        }
		
		
		//---- �ŷ� ������ Ȯ���ϱ� ���� List ���� �մϴ� ----------------------
		List accountList = null;
	

		String pageNum = request.getParameter("pageNum");//������ ��ȣ
        if (pageNum == null) {
            pageNum = "1"; //1�������� 20���� å ������
        }

        int pageSize = 30;//�� �������� ���� ����
        int currentPage = Integer.parseInt(pageNum); //��������ȣ�� Int �� ������ ��
        int startRow = (currentPage - 1) * pageSize + 1;//�� �������� ���۱� ��ȣ, ���� �� ��ȣ 1
        int endRow = currentPage * pageSize;//�� �������� ������ �۹�ȣ, ������ ��ϱ۹�ȣ 20
        int count = 0;
        int number = 1;
        
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
        
        
		count = adao.dealSituationCount(d_no);
		if(count > 0){
			accountList = adao.dealSituation(d_no, startRow, endRow);
		}


	    //��Ʈ����Ʈ ����
	    request.setAttribute("ldto", ldto);
	    request.setAttribute("adto", adto);
		request.setAttribute("d_lsummoney", d_lsummoney);
		request.setAttribute("accountList", accountList);
		request.setAttribute("number", number);


		
	return "/d_login/myAccountList.jsp";
	}
}
