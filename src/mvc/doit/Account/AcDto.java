package mvc.doit.Account;

import java.sql.Timestamp;

public class AcDto {
	//d_account dto
	private int d_acno;				//�α׹�ȣ
	private int d_no;				//ȸ����ȣ
	private String d_acnum;			//���¹�ȣ
	private Timestamp d_acregdate;	//���� ��� ��¥
	
	//d_log dto
	private int d_lno;				//�α׹�ȣ
	private int d_lsender;			//�����»��
	private int d_lreceiver;		//�޴»��
	private int d_bdelivery;		//����ڵ� �� �׿� �ŷ��ڵ�
	private int d_ldealmoney;		//�ŷ��ݾ�
	private int d_lsummoney;		//�ܾ�
	private int d_ldealtype;		//�ŷ�����(���)
	private int d_ldealresult;		//�ŷ����
	private Timestamp d_ldate;		//�ŷ� �ð�
	private String d_ldateS;		//�ŷ��ð� String ����
	private int listD_lsummoney;	//���� List ��� �ܾ�
	
	
	
	
	
	
	public int getListD_lsummoney() {
		return listD_lsummoney;
	}
	public void setListD_lsummoney(int listD_lsummoney) {
		this.listD_lsummoney = listD_lsummoney;
	}
	public String getD_ldateS() {
		return d_ldateS;
	}
	public void setD_ldateS(String d_ldateS) {
		this.d_ldateS = d_ldateS;
	}
	public int getD_acno() {
		return d_acno;
	}
	public void setD_acno(int d_acno) {
		this.d_acno = d_acno;
	}
	public int getD_no() {
		return d_no;
	}
	public void setD_no(int d_no) {
		this.d_no = d_no;
	}
	public String getD_acnum() {
		return d_acnum;
	}
	public void setD_acnum(String d_acnum) {
		this.d_acnum = d_acnum;
	}
	public Timestamp getD_acregdate() {
		return d_acregdate;
	}
	public void setD_acregdate(Timestamp d_acregdate) {
		this.d_acregdate = d_acregdate;
	}
	public int getD_lno() {
		return d_lno;
	}
	public void setD_lno(int d_lno) {
		this.d_lno = d_lno;
	}
	public int getD_lsender() {
		return d_lsender;
	}
	public void setD_lsender(int d_lsender) {
		this.d_lsender = d_lsender;
	}
	public int getD_lreceiver() {
		return d_lreceiver;
	}
	public void setD_lreceiver(int d_lreceiver) {
		this.d_lreceiver = d_lreceiver;
	}
	public int getD_bdelivery() {
		return d_bdelivery;
	}
	public void setD_bdelivery(int d_bdelivery) {
		this.d_bdelivery = d_bdelivery;
	}
	public int getD_ldealmoney() {
		return d_ldealmoney;
	}
	public void setD_ldealmoney(int d_ldealmoney) {
		this.d_ldealmoney = d_ldealmoney;
	}
	public int getD_lsummoney() {
		return d_lsummoney;
	}
	public void setD_lsummoney(int d_lsummoney) {
		this.d_lsummoney = d_lsummoney;
	}
	public int getD_ldealtype() {
		return d_ldealtype;
	}
	public void setD_ldealtype(int d_ldealtype) {
		this.d_ldealtype = d_ldealtype;
	}
	public int getD_ldealresult() {
		return d_ldealresult;
	}
	public void setD_ldealresult(int d_ldealresult) {
		this.d_ldealresult = d_ldealresult;
	}
	public Timestamp getD_ldate() {
		return d_ldate;
	}
	public void setD_ldate(Timestamp d_ldate) {
		this.d_ldate = d_ldate;
	}
	
	
	
			
}
