package com.iams.core.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.iams.common.exception.ParameterException;
import com.iams.common.util.Utils;
import com.iams.core.mapper.RolePermissionMapper;
import com.iams.core.pojo.Permission;
import com.iams.core.pojo.RolePermission;
import com.iams.core.service.PermissionService;
import com.iams.core.service.RolePermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Wei yz
 * @since 2021-02-04
 */
@Service
public class RolePermissionServiceImpl implements RolePermissionService {

    @Autowired
    private RolePermissionMapper rolePermissionMapper;

    @Autowired
    private PermissionService permissionService;

    @Override
    public List<Integer> findPermissionIdsByRoleId(Integer roleId) {
        Utils.isEmpty(roleId,"查询权限id的角色id不能为空或者小于等于0！");
        LambdaQueryWrapper<RolePermission> wrapper = Wrappers.<RolePermission>lambdaQuery();
        wrapper.eq(RolePermission::getRoleId,roleId);
        List<RolePermission> rolePermissionList = rolePermissionMapper.selectList(wrapper);
        if(!CollectionUtils.isEmpty(rolePermissionList)){//不为空则返回id串
            return rolePermissionList.stream().map(RolePermission::getPermissionId).collect(Collectors.toList());
        }
        return null;
    }

    @Override
    public int insert(RolePermission rolePermission) {
        Integer result = 0;
        rolePermission.setId(null);
        //是权限则得到菜单的id
        Integer parentId = check(rolePermission.getPermissionId());
        System.out.println("父id： "+parentId);
        if(parentId!=null){//添加的是权限时先添加菜单
            //构件菜单对象
            RolePermission menu = new RolePermission()
                    .setPermissionId(parentId)
                    .setRoleId(rolePermission.getRoleId());
            result=add(menu);//添加菜单
        }
        result=result+add(rolePermission);
        return result;
    }

    @Override
    public int update(RolePermission rolePermission) {
        select(rolePermission.getId());
        if(!check(rolePermission)){
            return rolePermissionMapper.updateById(rolePermission);
        }
        return 0;
    }

    @Override
    public int delete(Integer id) {
        Integer result = 0;
        RolePermission rolePermission = select(id);//根据id查询出关系
        Integer parentId = check(rolePermission.getPermissionId());//得到父id
        if(parentId==null){//是菜单，则删除菜单下的全部权限
            //根据该关系的菜单id查询全部权限的id
            List<Permission> permissionList = permissionService.find(rolePermission.getPermissionId(), "permission");
            //提取权限id集合
            List<Integer> permissionIds = permissionList.stream().map(Permission::getId).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(permissionIds)) {//权限id不为空时
                for (Integer permissionId:permissionIds) {//批量删除该菜单下的子权限
                    RolePermission permission = new RolePermission()
                            .setPermissionId(permissionId)
                            .setRoleId(rolePermission.getRoleId());
                    result=result+delete(permission);
                }
            }
        }
        return result + rolePermissionMapper.deleteById(id);//删除权限或者菜单
    }

    private RolePermission select(Integer id) {
        Utils.isEmpty(id, "查询的权限id不能小于等于0或为空！");
        RolePermission rolePermission = rolePermissionMapper.selectById(id);
        if (rolePermission == null) {
            throw new ParameterException("查询失败，数据为空！");
        }
        return rolePermission;
    }

    /**
     * 检查添加或者修改的权限id和角色id是否冲突
     *
     * @param rolePermission 角色权限
     */
    private boolean check(RolePermission rolePermission) {
        LambdaQueryWrapper<RolePermission> wrapper = Wrappers.<RolePermission>lambdaQuery();
        if (Utils.isEmpty(rolePermission.getId())) {//id不为空且小于等于0则为修改
            wrapper.ne(RolePermission::getId, rolePermission.getId());//不等于当前id，排除出来
        }
        wrapper.gt(RolePermission::getId, "0");//用于拼接SQL语句
        wrapper.and(w -> w.eq(RolePermission::getRoleId, rolePermission.getRoleId())
                .eq(RolePermission::getPermissionId, rolePermission.getPermissionId()));
        List<RolePermission> list = rolePermissionMapper.selectList(wrapper);
        if (list != null && list.size() > 0) {//存在则为true
           return true;
        }
        return false;
    }


    /**
     * 查询是否是权限，是则返回父id
     * @param permissionId
     * @return
     */
    private Integer check(Integer permissionId) {
        Permission permission = permissionService.find(permissionId);//查询该权限信息
        if(permission.getType().equals("permission")){//是权限则返回父id
            return permission.getParentId();
        }
        return null;
    }

    private int add(RolePermission rolePermission){
        Integer result = 0;
        if(!check(rolePermission)){//不重复则添加
            result = rolePermissionMapper.insert(rolePermission);
        }
        return result;
    }

    /**
     * 根据角色id与权限id删除
     * @param rolePermission
     * @return
     */
    private int delete(RolePermission rolePermission){
        LambdaQueryWrapper<RolePermission> wrapper = Wrappers.<RolePermission>lambdaQuery();
        wrapper.eq(RolePermission::getRoleId,rolePermission.getRoleId())
               .eq(RolePermission::getPermissionId,rolePermission.getPermissionId());
        return rolePermissionMapper.delete(wrapper);
    }


}
