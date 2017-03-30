package mvc.doit.Login;

import java.sql.Timestamp;

public class LoginDto {
	
	private int d_no;
	private String d_id;
	private String d_pass;//�������
	private String d_pw;
	private String d_name;
	
	private String d_phone; //����ȣ ����
	private String user_phone1;
	private String user_phone2;
	private String user_phone3;
	
	private String d_addr;
	private String d_person;
	
	private String d_mail;	//��������
	private String user_mail1;
	private String user_mail2;
	
	private String d_birth; //������� ����
	private String user_birth1;
	private String user_birth2;
	private String user_birth3;
	
	private String d_gender;
	private String d_pic;
	private int d_nom_grade;
	private int d_mech_grade;
	private Timestamp d_date;
	
	
	
	//Getter , setter �޼��� 
	public int getD_no() {
		return d_no;
	}
	public void setD_no(int d_no) {
		this.d_no = d_no;
	}
	public String getD_id() {
		return d_id;
	}
	public void setD_id(String d_id) {
		this.d_id = d_id;
	}
	public String getD_pass() {
		return d_pass;
	}
	public void setD_pass(String d_pass) {
		this.d_pass = d_pass;
	}
	public String getD_pw() {
		return d_pw;
	}
	public void setD_pw(String d_pw) {
		this.d_pw = d_pw;
	}
	public String getD_name() {
		return d_name;
	}
	public void setD_name(String d_name) {
		this.d_name = d_name;
	}
	
//phone-------------------------------------------------	
	public String getD_phone() {
		return user_phone1+"-"+user_phone2+"-"+user_phone3;
	}
	public void setD_phone(String d_phone) {
		this.d_phone = d_phone;
	}
	public String getUser_phone1() {
		return user_phone1;
	}
	public void setUser_phone1(String user_phone1) {
		this.user_phone1 = user_phone1;
	}
	public String getUser_phone2() {
		return user_phone2;
	}
	public void setUser_phone2(String user_phone2) {
		this.user_phone2 = user_phone2;
	}
	public String getUser_phone3() {
		return user_phone3;
	}
	public void setUser_phone3(String user_phone3) {
		this.user_phone3 = user_phone3;
	}
	
	
	public String getD_addr() {
		return d_addr;
	}
	public void setD_addr(String d_addr) {
		this.d_addr = d_addr;
	}
	public String getD_person() {
		return d_person;
	}
	public void setD_person(String d_person) {
		this.d_person = d_person;
	}
	
//mail-------------------------------------------------	
	public String getD_mail() {
		return user_mail1+" @ "+user_mail2;
	}
	public void setD_mail(String d_mail) {
		this.d_mail = d_mail;
	}
	public String getUser_mail1() {
		return user_mail1;
	}
	public void setUser_mail1(String user_mail1) {
		this.user_mail1 = user_mail1;
	}
	public String getUser_mail2() {
		return user_mail2;
	}
	public void setUser_mail2(String user_mail2) {
		this.user_mail2 = user_mail2;
	}
	
	
	public String getD_birth() {
		return user_birth1+" �� "+user_birth2+" �� "+user_birth3+" �� ";
	} 
	public void setD_birth(String d_birth) {
		this.d_birth = d_birth;
	}
	public String getUser_birth1() {
		return user_birth1;
	}
	public void setUser_birth1(String user_birth1) {
		this.user_birth1 = user_birth1;
	}
	public String getUser_birth2() {
		return user_birth2;
	}
	public void setUser_birth2(String user_birth2) {
		this.user_birth2 = user_birth2;
	}
	public String getUser_birth3() {
		return user_birth3;
	}
	public void setUser_birth3(String user_birth3) {
		this.user_birth3 = user_birth3;
	}
	
	
	public String getD_gender() {
		return d_gender;
	}
	public void setD_gender(String d_gender) {
		this.d_gender = d_gender;
	}
	public String getD_pic() {
		return d_pic;
	}
	public void setD_pic(String d_pic) {
		this.d_pic = d_pic;
	}
	public int getD_nom_grade() {
		return d_nom_grade;
	}
	public void setD_nom_grade(int d_nom_grade) {
		this.d_nom_grade = d_nom_grade;
	}
	public int getD_mech_grade() {
		return d_mech_grade;
	}
	public void setD_mech_grade(int d_mech_grade) {
		this.d_mech_grade = d_mech_grade;
	}
	public Timestamp getD_date() {
		return d_date;
	}
	public void setD_date(Timestamp d_date) {
		this.d_date = d_date;
	}
	
	
	
	
	
	

}
