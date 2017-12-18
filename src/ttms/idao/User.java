package ttms.idao;

import ttms.model.UserModel;

public interface User {
    public UserModel getUserByNameAndPwd(String userName,String password);
}
