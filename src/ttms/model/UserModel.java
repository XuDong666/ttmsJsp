package ttms.model;

import java.io.Serializable;
/**
 * Created by xudong on 17-12-14.
 */
public class UserModel implements Serializable {
    private String emp_no;
    private String emp_pass;
    private int type;
    private String head_path;

    public String getEmp_no(){
        return this.emp_no;
    }

    public void setEmp_no(String emp_no){
        this.emp_no=emp_no;
    }

    public String getEmp_pass(){
        return this.emp_pass;
    }

    public void setEmp_pass(String emp_pass){
        this.emp_pass=emp_pass;
    }

    public int getType(){
        return this.type;
    }

    public void setType(int type){
        this.type=type;
    }

    public String getHead_path(){
        return this.head_path;
    }

    public void setHead_path(String head_path){
        this.head_path=head_path;
    }
}
