package mvc.doit.Customer;

import java.sql.Timestamp;

public class FaqDto {
	private int faq_no;				//�۹�ȣ
	private String faq_subject;		//������
	private String faq_content;		//�۳���
	private String faq_writer;		//�ۼ���
	private Timestamp faq_reg_date;	//�ۼ���¥
	private int faq_type;			//�з�Ÿ��
	
	
	public int getFaq_type() {
		return faq_type;
	}
	public void setFaq_type(int faq_type) {
		this.faq_type = faq_type;
	}
	public int getFaq_no() {
		return faq_no;
	}
	public void setFaq_no(int faq_no) {
		this.faq_no = faq_no;
	}
	public String getFaq_subject() {
		return faq_subject;
	}
	public void setFaq_subject(String faq_subject) {
		this.faq_subject = faq_subject;
	}
	public String getFaq_content() {
		return faq_content;
	}
	public void setFaq_content(String faq_content) {
		this.faq_content = faq_content;
	}
	public String getFaq_writer() {
		return faq_writer;
	}
	public void setFaq_writer(String faq_writer) {
		this.faq_writer = faq_writer;
	}
	public Timestamp getFaq_reg_date() {
		return faq_reg_date;
	}
	public void setFaq_reg_date(Timestamp faq_reg_date) {
		this.faq_reg_date = faq_reg_date;
	}
	
	
	

}
