package com.iams.core.pojo;

import com.iams.common.util.Utils;

import java.util.ArrayList;
import java.util.List;


/**
 *  @author: Wei yz
 *  @Date: 2021/2/6 12:15
 *  @Description:  绝色枚举类
 */
public enum RoleEnum {

	STUDENT(1, "学生"),
	TEACHER(2, "教师"),
	ADMIN(3, "管理员"),
	SUPERADMIN(4, "超级管理员");
	private int id;
	private String name;

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	private RoleEnum(int id, String name) {
		this.id = id;
		this.name = name;
	}

	/**
	 * 返回角色集合，通过getId()、getName();获得对应的值
	 * @return
	 */
	public static List<Role> list() {
		List<Role> list = new ArrayList<Role>();
		for (RoleEnum roleEnum : RoleEnum.values()) {
			list.add(new Role(roleEnum.getId(),roleEnum.getName()));
		}
		return list;
	}

	/**
	 * 传入id查询角色名
	 * @param id 角色id
	 * @return
	 */
	public static Role getRole(int id) {
		Utils.isEmpty(id,"角色id不能为空或者小于等于0");
		for (RoleEnum roleEnum : RoleEnum.values()) {
			if (roleEnum.id == id) {
				return new Role(roleEnum.getId(),roleEnum.getName());
			}
		}
		return new Role(999,"非法用户");
	}

	/**
	 * 传入id查询角色名
	 * @param name 角色名
	 * @return
	 */
	public static Integer getRoleId(String name) {
		Utils.isEmpty(name,"角色id不能为空或者小于等于0");
		if(name.equals("student")){
			return STUDENT.id;
		}
		if(name.equals("teacher")){
			return TEACHER.id;
		}
		if(name.equals("admin")){
			return ADMIN.id;
		}
		if(name.equals("superAdmin")){
			return SUPERADMIN.id;
		}
		return 0;
	}


}
