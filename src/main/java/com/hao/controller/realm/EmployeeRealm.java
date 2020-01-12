package com.hao.controller.realm;

import com.hao.domain.Employee;
import com.hao.domain.Permission;
import com.hao.domain.Role;
import com.hao.service.EmployeeService;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class EmployeeRealm extends AuthorizingRealm {
    @Autowired
    EmployeeService employeeService;
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("-----------------realm授权------------------");
        JdbcRealm jdbcRealm = new JdbcRealm();
        jdbcRealm.setPermissionsLookupEnabled(true);
        //获取用户
        Employee employee = (Employee) principalCollection.getPrimaryPrincipal();
        //创建用户的角色与权限
        List<String> roles = new ArrayList<>();
        List<String> permissions = new ArrayList<>();
        //判断是否为管理员
        if(employee.getAdmin()){
            permissions.add("*:*");
        }else{
            //查询其角色与权限
            roles = employeeService.getRolesByEid(employee.getId());
            permissions = employeeService.getPermissionsByEid(employee.getId());
        }
        //添加到授权信息
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        simpleAuthorizationInfo.addRoles(roles);
        simpleAuthorizationInfo.addStringPermissions(permissions);

        return simpleAuthorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("-----------------realm认证------------------");
        String username = (String)authenticationToken.getPrincipal();
        System.out.println(username);
        Employee employee = employeeService.getEmployeeByName(username);
        if(employee == null){
            return null;
        }
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(
                employee,
                employee.getPassword(),
                ByteSource.Util.bytes(employee.getUsername()),
                this.getName());
        return info;
    }
}
