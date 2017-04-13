package ch19.action;

import java.sql.Timestamp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import ch12.board.BoardDataBean;
import ch12.board.BoardDBBean;

public class WriteProAction implements CommandAction {// �Էµ� �� ó��

    public String requestPro(HttpServletRequest request,
        HttpServletResponse response)  throws Throwable {

        request.setCharacterEncoding("euc-kr");//�ѱ� ���ڵ�
		String path=request.getRealPath("save");  // �������� ���ε� ���� ���
		int size=1024*1024*5; // 5MB
		String enc = "euc-kr";
		DefaultFileRenamePolicy df = new DefaultFileRenamePolicy(); // ������ ����
		MultipartRequest multi = new MultipartRequest(request,path,size,enc,df);
		
		String sn = multi.getFilesystemName("save"); // ���ε� �Ǵ� �̸�
		request.setAttribute("sn",multi.getFilesystemName("save"));
		
			
        BoardDataBean article = new BoardDataBean();//������ó�� ��
		article.setNum(Integer.parseInt(multi.getParameter("num")));
        article.setWriter(multi.getParameter("writer"));
        article.setEmail(multi.getParameter("email"));
        article.setSubject(multi.getParameter("subject"));
        article.setPasswd(multi.getParameter("passwd"));
        article.setReg_date(new Timestamp(System.currentTimeMillis()));
		article.setRef(Integer.parseInt(multi.getParameter("ref")));
		article.setRe_step(Integer.parseInt(multi.getParameter("re_step")));
		article.setRe_level(Integer.parseInt(multi.getParameter("re_level")));
		article.setContent(multi.getParameter("content"));
		article.setIp(request.getRemoteAddr());
		article.setSave(multi.getFilesystemName("save"));

        BoardDBBean dbPro = BoardDBBean.getInstance();//DBó��
        dbPro.insertArticle(article);

        return "/ch19/writePro.jsp";
    }
}
