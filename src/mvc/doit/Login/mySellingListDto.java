package mvc.doit.Login;

import java.sql.Timestamp;

public class mySellingListDto {
	//å ��ü DB 
	   private int d_bno;              //��Ϲ�ȣ
	   private int d_bcode;            //å�ڵ�
	   private String d_bname;            //å�̸�
	   private String d_bgrade;         //å���
	   private String d_bpublisher;      //���ǻ�
	   private String d_bauthor;         //����
	   private String d_bgenre;         //�帣
	   private String d_bgenre2;         //����
	   private String d_blocation;         //����(����/��)
	   private String d_bregistdate;      //å �Ⱓ ��¥
	   private String d_bpic;            //å ����
	   private int d_bcount;            //����
	   private int d_bvalue;            //���� 
	   private int d_bsellvalue;         //�ǸŰ�
	   private int d_bpurchasevalue;      //���԰�
	   private String d_binspeccode;      //�˼��ڵ�
	   private String d_id;            //�Ǹ����ڵ� //ȸ�������� ����
	   private int d_bdeliverycode;         //����ڵ�
	   private Timestamp d_bdate;            //DB ����Ͻ�(�˼� �Ϸ� ��)
	
//�ǸŽ�ûList
   private int d_sno;              //��Ϲ�ȣ,seq
   private int d_sfinish;			//0 ��û��, 1��û�Ϸ�, 2���
   private Timestamp d_sdate;            //DB �ǸŽ�û �Ͻ�()
   
public int getD_bno() {
	return d_bno;
}
public void setD_bno(int d_bno) {
	this.d_bno = d_bno;
}
public int getD_bcode() {
	return d_bcode;
}
public void setD_bcode(int d_bcode) {
	this.d_bcode = d_bcode;
}
public String getD_bname() {
	return d_bname;
}
public void setD_bname(String d_bname) {
	this.d_bname = d_bname;
}
public String getD_bgrade() {
	return d_bgrade;
}
public void setD_bgrade(String d_bgrade) {
	this.d_bgrade = d_bgrade;
}
public String getD_bpublisher() {
	return d_bpublisher;
}
public void setD_bpublisher(String d_bpublisher) {
	this.d_bpublisher = d_bpublisher;
}
public String getD_bauthor() {
	return d_bauthor;
}
public void setD_bauthor(String d_bauthor) {
	this.d_bauthor = d_bauthor;
}
public String getD_bgenre() {
	return d_bgenre;
}
public void setD_bgenre(String d_bgenre) {
	this.d_bgenre = d_bgenre;
}
public String getD_bgenre2() {
	return d_bgenre2;
}
public void setD_bgenre2(String d_bgenre2) {
	this.d_bgenre2 = d_bgenre2;
}
public String getD_blocation() {
	return d_blocation;
}
public void setD_blocation(String d_blocation) {
	this.d_blocation = d_blocation;
}
public String getD_bregistdate() {
	return d_bregistdate;
}
public void setD_bregistdate(String d_bregistdate) {
	this.d_bregistdate = d_bregistdate;
}
public String getD_bpic() {
	return d_bpic;
}
public void setD_bpic(String d_bpic) {
	this.d_bpic = d_bpic;
}
public int getD_bcount() {
	return d_bcount;
}
public void setD_bcount(int d_bcount) {
	this.d_bcount = d_bcount;
}
public int getD_bvalue() {
	return d_bvalue;
}
public void setD_bvalue(int d_bvalue) {
	this.d_bvalue = d_bvalue;
}
public int getD_bsellvalue() {
	return d_bsellvalue;
}
public void setD_bsellvalue(int d_bsellvalue) {
	this.d_bsellvalue = d_bsellvalue;
}
public int getD_bpurchasevalue() {
	return d_bpurchasevalue;
}
public void setD_bpurchasevalue(int d_bpurchasevalue) {
	this.d_bpurchasevalue = d_bpurchasevalue;
}
public String getD_binspeccode() {
	return d_binspeccode;
}
public void setD_binspeccode(String d_binspeccode) {
	this.d_binspeccode = d_binspeccode;
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
public Timestamp getD_bdate() {
	return d_bdate;
}
public void setD_bdate(Timestamp d_bdate) {
	this.d_bdate = d_bdate;
}
public int getD_sno() {
	return d_sno;
}
public void setD_sno(int d_sno) {
	this.d_sno = d_sno;
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
