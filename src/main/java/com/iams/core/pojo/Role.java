package com.iams.core.pojo;

/**
 * @author Wei yz
 * @ClassName: Role
 * @Description: 角色
 * @date 2021/2/6 12:14
 */
public class Role {

    /**
     * 角色id
     */
    private Integer id;

    /**
     * 角色名
     */
    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Role() {

    }

    public Role(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
