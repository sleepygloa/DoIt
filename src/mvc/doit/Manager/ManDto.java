package mvc.doit.Manager;

import java.sql.Timestamp;

public class ManDto {
	
	//���� ����
	
	//dashboard ����ǥ��
	private int d_seller; //�����Ǹ�
	private int d_lib; //������ ��ü��
	private int d_trade; //���ŷ� / ��� ������
	
	//������ ��ۻ��� ���
	private int br_no; //������ȣ
	private String br_code; //�����ڵ�
	private String br_thumpic; //��������
	private String br_name; //�����̸�
	private String br_pub;//���� ���ǻ�
	private String br_writer; //����
	private String br_waiter; //�����
	private int br_deli;//��ۻ���
	private Timestamp br_over_date;//�뿩����
	
	
	
	
	//setter,getter
	
	//dashboard ����ǥ��
	public int getD_seller() {
		return d_seller;
	}
	public void setD_seller(int d_seller) {
		this.d_seller = d_seller;
	}
	public int getD_lib() {
		return d_lib;
	}
	public void setD_lib(int d_lib) {
		this.d_lib = d_lib;
	}
	public int getD_trade() {
		return d_trade;
	}
	public void setD_trade(int d_trade) {
		this.d_trade = d_trade;
	}
	
	
	//������ ��ۻ��� ���
	public int getBr_no() {
		return br_no;
	}
	public void setBr_no(int br_no) {
		this.br_no = br_no;
	}
	public String getBr_code() {
		return br_code;
	}
	public void setBr_code(String br_code) {
		this.br_code = br_code;
	}
	public String getBr_thumpic() {
		return br_thumpic;
	}
	public void setBr_thumpic(String br_thumpic) {
		this.br_thumpic = br_thumpic;
	}
	public String getBr_name() {
		return br_name;
	}
	public void setBr_name(String br_name) {
		this.br_name = br_name;
	}
	public String getBr_pub() {
		return br_pub;
	}
	public void setBr_pub(String br_pub) {
		this.br_pub = br_pub;
	}
	public String getBr_writer() {
		return br_writer;
	}
	public void setBr_writer(String br_writer) {
		this.br_writer = br_writer;
	}
	public String getBr_waiter() {
		return br_waiter;
	}
	public void setBr_waiter(String br_waiter) {
		this.br_waiter = br_waiter;
	}
	public int getBr_deli() {
		return br_deli;
	}
	public void setBr_deli(int br_deli) {
		this.br_deli = br_deli;
	}
	public Timestamp getBr_over_date() {
		return br_over_date;
	}
	public void setBr_over_date(Timestamp br_over_date) {
		this.br_over_date = br_over_date;
	}
	
	
	
	
	
	
}
