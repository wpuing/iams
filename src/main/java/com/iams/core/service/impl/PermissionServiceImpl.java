package com.iams.core.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.iams.common.constant.IamsConstants;
import com.iams.common.exception.ParameterException;
import com.iams.common.util.IamsUtils;
import com.iams.common.util.Utils;
import com.iams.core.dto.PermissionBo;
import com.iams.core.dto.PermissionDto;
import com.iams.core.mapper.PermissionMapper;
import com.iams.core.pojo.Permission;
import com.iams.core.pojo.RoleEnum;
import com.iams.core.pojo.RolePermission;
import com.iams.core.service.PermissionService;
import com.iams.core.service.RolePermissionService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Wei yz
 * @since 2021-02-04
 */
@Service
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private PermissionMapper permissionMapper;

    @Autowired
    private RolePermissionService rolePermissionService;

    @Override
    public Permission find(Integer id) {
        return select(id);
    }

    @Override
    public List<Permission> find(Integer parentId,String type) {
        LambdaQueryWrapper<Permission> wrapper = Wrappers.<Permission>lambdaQuery();
        wrapper.eq(Permission::getType,type);
        if (Utils.isEmpty(parentId)){//查询权限
            wrapper.eq(Permission::getParentId,parentId);
        }
        return permissionMapper.selectList(wrapper);
    }

    @Override
    public List<Permission> findRoleAllMenu(Integer roleId) {
        List<Integer> ids = rolePermissionService.findPermissionIdsByRoleId(roleId);//查询出该角色的所欲权限关系
        LambdaQueryWrapper<Permission> wrapper = Wrappers.<Permission>lambdaQuery();
        wrapper.eq(Permission::getType,"menu");
        wrapper.in(Permission::getId,ids);
        return permissionMapper.selectList(wrapper);
    }

    @Override
    public List<PermissionDto> findAll() {
        List<PermissionDto> permissionDtoList = new ArrayList<>();
        List<PermissionBo> itemCatsAfterList = new ArrayList<>();
        List<PermissionBo> itemCatsBefterList = new ArrayList<>();
        List<Permission> permissions1 = permissionMapper.selectList(Wrappers.<Permission>lambdaQuery().orderByAsc(Permission::getSort));
        if(!CollectionUtils.isEmpty(permissions1)){
            permissions1.forEach(permission -> {
                PermissionBo permissionBo = new PermissionBo();
                BeanUtils.copyProperties(permission, permissionBo);//拷贝
                itemCatsBefterList.add(permissionBo);
            });
        }
        permissionDtoList.add( new PermissionDto()
                .setType("ALL")
                .setPermissionList(IamsUtils.sort(0,itemCatsBefterList,itemCatsAfterList)));
        for (int i=0;i<RoleEnum.list().size();i++){
            PermissionDto permissionDto = new PermissionDto();
            if(RoleEnum.list().get(i).getName().equals("超级管理员")){
                permissionDto.setType("superAdmin");
                permissionDto.setPermissionList(new ArrayList<>());
            }else {
                permissionDto.setType(type(RoleEnum.list().get(i).getName()));//设置类型
                permissionDto.setRoleId(RoleEnum.list().get(i).getId());//设置类型
                itemCatsAfterList = new ArrayList<>();
                List<PermissionBo> permissions = IamsUtils.sort(0,selPermissionBo(RoleEnum.list().get(i).getId()),itemCatsAfterList);
                //将排序过后的权限树返回
                permissionDto.setPermissionList(permissions);
            }
            permissionDtoList.add(permissionDto);
        }
        return permissionDtoList;
    }

    private String type(String name){
        if(name.equals("学生")){
            return "student";
        }
        if(name.equals("教师")){
            return "teacher";
        }
        if(name.equals("管理员")){
            return "admin";
        }
        return "superAdmin";
    }

    @Override
    public List<Permission> findRolePermission(Integer roleId) {
        Utils.isEmpty(roleId,"查询失败，角色id为空或者小于等于0！");
        List<Integer> ids = rolePermissionService.findPermissionIdsByRoleId(roleId);
        return permissionMapper.selectBatchIds(ids);
    }

    @Override
    public int insert(Permission permission) {
        if(permission.getType().equals("menu")){
            permission.setParentId(0);
        }
        permission.setId(null);
        permission.setDeleted(IamsConstants.DELETED);
        check(permission);
        return permissionMapper.insert(permission);
    }

    @Override
    public int update(Permission permission) {
        select(permission.getId());
        check(permission);
        return permissionMapper.updateById(permission);
    }

    @Override
    public int delete(Integer id) {
        select(id);
        return permissionMapper.deleteById(id);
    }

    @Override
    public void updatePermission() {
        List<Permission> list = permissionMapper.selectList(null);
        if(!CollectionUtils.isEmpty(list)){
            List<Integer> ids = list.stream().map(Permission::getId).collect(Collectors.toList());
            for( Integer id:ids){
                rolePermissionService.insert(new RolePermission().setPermissionId(id).setRoleId(RoleEnum.getRoleId("superAdmin")));
            }
        }
    }

    private Permission select(Integer id){
        Utils.isEmpty(id,"查询的权限id不能小于等于0或为空！");
        Permission permission = permissionMapper.selectById(id);
        if(permission==null){
            throw new ParameterException("查询失败，数据为空！");
        }
        return permission;
    }

    private List<PermissionBo> selPermissionBo(Integer roleId){
        QueryWrapper<Permission> wrapper = Wrappers.<Permission>query();
        wrapper.eq("rp.role_id",roleId);
        wrapper.eq("p.deleted",IamsConstants.DELETED);
        wrapper.orderByAsc("p.sort");
        return permissionMapper.findRolePermissionInfo(wrapper);
    }

    /**
     * 检查添加或者修改的权限名称或者类型是否冲突
     * @param permission 权限信息
     */
    private void check(Permission permission){
        LambdaQueryWrapper<Permission> wrapper = Wrappers.<Permission>lambdaQuery();
        if(Utils.isEmpty(permission.getId())){//id不为空且小于等于0则为修改
            wrapper.ne(Permission::getId,permission.getId());//不等于当前id，排除出来
        }
        wrapper.gt(Permission::getId,"0");//用于拼接SQL语句
        if(permission.getType().equals("menu")){//菜单
            wrapper.and(w->w.eq(Permission::getName,permission.getName())
                    .eq(Permission::getType,permission.getType()));
        }
        if(permission.getType().equals("permission")){//权限,查询名称、类型、父id、权限码一致的
            wrapper.and(w->w.eq(Permission::getName,permission.getName())
                    .eq(Permission::getPerCode,permission.getPerCode())
                    .eq(Permission::getParentId,permission.getParentId())
                    .eq(Permission::getType,permission.getType()));
        }
        List<Permission> list = permissionMapper.selectList(wrapper);
        if(list!=null && list.size()>0){//存在则取第一条记录的id
            throw new ParameterException("更新失败，更新的信息中已存在该数据，id："+list.get(0).getId());
        }
    }
}
