package ttms.model;

/**
 * Created by xudong on 17-12-19.
 */
public class EmployeeModel {
    private int emp_id;
    private String emp_no;
    private String emp_name;
    private String emp_tel_num;
    private String emp_addr;
    private String emp_email;


    public int getEmp_id(){
        return this.emp_id;
    }
    public void setEmp_id(int emp_id){
        this.emp_id=emp_id;
    }


    public String getEmp_no(){
        return this.emp_no;
    }
    public void setEmp_no(String emp_no){
        this.emp_no=emp_no;
    }


    public String getEmp_name(){
        return this.emp_name;
    }
    public void setEmp_name(String emp_name) {
        this.emp_name = emp_name;
    }


    public String getEmp_tel_num(){
        return this.emp_tel_num;
    }
    public void setEmp_tel_num(String emp_tel_num){
        this.emp_tel_num=emp_tel_num;
    }


    public String getEmp_addr(){
        return this.emp_addr;
    }
    public void setEmp_addr(String emp_addr){
        this.emp_addr=emp_addr;
    }


    public String getEmp_email(){
        return this.emp_email;
    }
    public void setEmp_email(String emp_email){
        this.emp_email=emp_email;
    }
}
