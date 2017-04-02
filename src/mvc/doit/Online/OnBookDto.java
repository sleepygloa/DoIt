package mvc.doit.Online;

import java.sql.Timestamp;

public class OnBookDto {
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
	   private String d_id;            //�Ǹ����ڵ� //ȸ�������� ����
	   private int d_bdeliverycode;         //����ڵ�
	   private Timestamp d_bdate;            //DB ����Ͻ�(�˼� �Ϸ� ��)
	
		//�ǸŽ�ûList���� ����ϴ� List
		private int d_sno;              //��Ϲ�ȣ,seq
		private int d_sfinish;			//0 ��û��, 1��û�Ϸ�, 2���
		private Timestamp d_sdate;      //DB �ǸŽ�û �Ͻ�()
		
		//å ����Ҷ� ���
		private int d_icode;  	        //�˼� DB �˼��ڵ�
		private int d_itotal;  			//�˼� ����
		private int d_iold;			//�˼����(�����)
		private int d_icover;			//�˼����(ǥ��)
		private int d_ispine;			//�˼����(å��)
		private int d_ibind;			//�˼����(��������
		private Timestamp d_idate;		//�˼� ��� ��¥
		
		//å ���� , �Ұ���
		private String d_norder;		//����
		private String d_nintro;		//�Ұ���
		
		//���
		
		private int d_bdelibery;			// ��ۻ���
		private String d_bbuyer;			// ������ ���̵�
		private String d_brecipient;		// �޴»��
		private String d_brequested;		// ��ۿ�û����
		private Timestamp d_bdeldate;		// �ֹ���ϳ�¥
		   
		

		
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
public int getD_iold() {
			return d_iold;
		}
		public void setD_iold(int d_iold) {
			this.d_iold = d_iold;
		}
		public int getD_icover() {
			return d_icover;
		}
		public void setD_icover(int d_icover) {
			this.d_icover = d_icover;
		}
		public int getD_ispine() {
			return d_ispine;
		}
		public void setD_ispine(int d_ispine) {
			this.d_ispine = d_ispine;
		}
		public int getD_ibind() {
			return d_ibind;
		}
		public void setD_ibind(int d_ibind) {
			this.d_ibind = d_ibind;
		}
public String getD_norder() {
			return d_norder;
}
public void setD_norder(String d_norder) {
	this.d_norder = d_norder;
}
public String getD_nintro() {
	return d_nintro;
}
public void setD_nintro(String d_nintro) {
	this.d_nintro = d_nintro;
}
public int getD_icode() {
	return d_icode;
}
public void setD_icode(int d_icode) {
	this.d_icode = d_icode;
}
public Timestamp getD_idate() {
	return d_idate;
}
public void setD_idate(Timestamp d_idate) {
	this.d_idate = d_idate;
}
public int getD_itotal() {
	return d_iold+d_icover+d_ispine+d_ibind;
}
public void setD_itotal(int d_itotal) {
	this.d_itotal = d_itotal;
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

   
   
   
   
   
}