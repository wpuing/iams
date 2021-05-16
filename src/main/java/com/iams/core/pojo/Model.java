package com.iams.core.pojo;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author Wei yz
 * @ClassName: Model
 * @Description:
 * @date 2021/2/5 12:27
 */
public class Model implements Serializable {

    /**
     * 密码
     */
    @NotNull(message = "密码不能为空")
    protected String password;

    /**
     * 随机码
     */
    protected String randomId;

    /**
     * 盐
     */
    protected String salt;

    @Override
    public String toString() {
        return "Model{" +
                "password='" + password + '\'' +
                ", randomId='" + randomId + '\'' +
                ", salt='" + salt + '\'' +
                '}';
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRandomId() {
        return randomId;
    }

    public void setRandomId(String randomId) {
        this.randomId = randomId;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }
}
