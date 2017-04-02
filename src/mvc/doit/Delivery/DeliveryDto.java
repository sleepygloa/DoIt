package mvc.doit.Delivery;

import java.sql.Timestamp;

public class DeliveryDto {
	//��� ���̺��� ��۸�常 �ο��ؾ��ϴ� ��Ȳ�� �ǸŽ�û�� �ۼ��ϴ� ���������� �ֱ⶧����
	//����ڵ带 ������ ��� column ���� [notnull] �� �ƴϴ�.
	private int d_bdeliverycode; 		// ����ڵ� 
	private int d_bcode;				// å�ڵ�
	private int d_bdelibery;			// ��ۻ���
	private String d_bbuyer;			// ������ ���̵�
	private String d_brecipient;		// �޴»��
	private String d_brequested;		// ��ۿ�û����
	private Timestamp d_bdeldate;		// �ֹ���ϳ�¥
	
	
	public int getD_bdeliverycode() {
		return d_bdeliverycode;
	}
	public void setD_bdeliverycode(int d_bdeliverycode) {
		this.d_bdeliverycode = d_bdeliverycode;
	}
	public int getD_bcode() {
		return d_bcode;
	}
	public void setD_bcode(int d_bcode) {
		this.d_bcode = d_bcode;
	}
	public int getD_bdelibery() {
		return d_bdelibery;
	}
	public void setD_bdelibery(int d_bdelibery) {
		this.d_bdelibery = d_bdelibery;
	}
	public String getD_bbuyer() {
		return d_bbuyer;
	}
	public void setD_bbuyer(String d_bbuyer) {
		this.d_bbuyer = d_bbuyer;
	}
	public String getD_brecipient() {
		return d_brecipient;
	}
	public void setD_brecipient(String d_brecipient) {
		this.d_brecipient = d_brecipient;
	}
	public String getD_brequested() {
		return d_brequested;
	}
	public void setD_brequested(String d_brequested) {
		this.d_brequested = d_brequested;
	}
	public Timestamp getD_bdeldate() {
		return d_bdeldate;
	}
	public void setD_bdeldate(Timestamp d_bdeldate) {
		this.d_bdeldate = d_bdeldate;
	}
	
	
	
}
