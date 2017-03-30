package mvc.doit.Delivery;

import java.sql.Timestamp;

public class DeliveryDto {
	//��� ���̺��� ��۸�常 �ο��ؾ��ϴ� ��Ȳ�� �ǸŽ�û�� �ۼ��ϴ� ���������� �ֱ⶧����
	//����ڵ带 ������ ��� column ���� [notnull] �� �ƴϴ�.
	private int d_bdeliverycode; //����ڵ�
	private int d_bcode;
	private int d_bdelivery; //��ۻ���
	private Timestamp d_bwaitdate;
	private Timestamp d_bstartdate;
	private Timestamp d_benddate;
	private String d_bseller;
	private String d_bbuyer;
	
	
	
	
	public int getD_bcode() {
		return d_bcode;
	}
	public void setD_bcode(int d_bcode) {
		this.d_bcode = d_bcode;
	}
	public int getD_bdeliverycode() {
		return d_bdeliverycode;
	}
	public void setD_bdeliverycode(int d_bdeliverycode) {
		this.d_bdeliverycode = d_bdeliverycode;
	}
	public int getD_bdelivery() {
		return d_bdelivery;
	}
	public void setD_bdelivery(int d_bdelivery) {
		this.d_bdelivery = d_bdelivery;
	}
	public Timestamp getD_bwaitdate() {
		return d_bwaitdate;
	}
	public void setD_bwaitdate(Timestamp d_bwaitdate) {
		this.d_bwaitdate = d_bwaitdate;
	}
	public Timestamp getD_bstartdate() {
		return d_bstartdate;
	}
	public void setD_bstartdate(Timestamp d_bstartdate) {
		this.d_bstartdate = d_bstartdate;
	}
	public Timestamp getD_benddate() {
		return d_benddate;
	}
	public void setD_benddate(Timestamp d_benddate) {
		this.d_benddate = d_benddate;
	}
	public String getD_bseller() {
		return d_bseller;
	}
	public void setD_bseller(String d_bseller) {
		this.d_bseller = d_bseller;
	}
	public String getD_bbuyer() {
		return d_bbuyer;
	}
	public void setD_bbuyer(String d_bbuyer) {
		this.d_bbuyer = d_bbuyer;
	}
	
	
	
	
}
