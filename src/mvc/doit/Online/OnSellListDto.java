package mvc.doit.Online;


import java.sql.Timestamp;
import java.util.List;

public class OnSellListDto {

	   private int d_sno;              //��Ϲ�ȣ,seq
	   private int d_bcode;            //å�ڵ�
	   private String d_id;            //�Ǹ��ھ��̵� //ȸ�������� ����
	   private int d_bdeliverycode;         //����ڵ�
	   private int d_sfinish;			//0 ��û��, 1��û�Ϸ�, 2���
	   private Timestamp d_sdate;            //DB �ǸŽ�û �Ͻ�()
	   
	   
	   
	public int getD_sno() {
		return d_sno;
	}
	public void setD_sno(int d_sno) {
		this.d_sno = d_sno;
	}
	public int getD_bcode() {
		return d_bcode;
	}
	public void setD_bcode(int d_bcode) {
		this.d_bcode = d_bcode;
	}
	public String getD_id() {
		return d_id;
	}
	public void setD_id(String d_id) {
		this.d_id = d_id;
	}
	public int getD_bdeliverycode() {
		return d_bdeliverycode;
	}
	public void setD_bdeliverycode(int d_bdeliverycode) {
		this.d_bdeliverycode = d_bdeliverycode;
	}
	public int getD_sfinish() {
		return d_sfinish;
	}
	public void setD_sfinish(int d_sfinish) {
		this.d_sfinish = d_sfinish;
	}
	public Timestamp getD_sdate() {
		return d_sdate;
	}
	public void setD_sdate(Timestamp d_sdate) {
		this.d_sdate = d_sdate;
	}
	   
	
	   
  

}
