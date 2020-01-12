<%--
  Created by IntelliJ IDEA.
  User: hao
  Date: 20-1-6
  Time: 上午10:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<html>
<head>
    <title>Title</title>
    <%@include file="/static/common/common.jsp"%>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/js/employee.js"></script>
</head>
<body>
<!--工具栏-->
<div id="toolbar">
    <shiro:hasPermission name="employee:add">
        <a href="#" id="add" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true">添加</a>
    </shiro:hasPermission>
    <shiro:hasPermission name="employee:edit">
        <a href="#" id="edit" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true">编辑</a>
    </shiro:hasPermission>
    <shiro:hasPermission name="employee:delete">
        <a href="#" id="delete" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true">离职</a>
    </shiro:hasPermission>
        <a href="#" id="getAll" class="easyui-linkbutton" data-options="iconCls:'icon-reload',plain:true">刷新</a>
    <input type="text" name="keyword" style="width: 200px; height: 30px;padding-left: 5px;">
    <a class="easyui-linkbutton" iconCls="icon-search" id="searchbtn">查询</a>
    <a class="easyui-linkbutton" iconCls="icon-edit" id="excelOut">Excel导出</a>
    <a class="easyui-linkbutton" iconCls="icon-edit" id="excelIn">Excel导入</a>

</div>
<!--数据表-->
<table id="dataGrid"></table>
<!--添加编辑对话框-->
<div id="box">
    <form id="employeeForm">
        <input type="hidden" name="id">
    <table align="center" style="border-spacing: 0px 10px">
        <tr>
            <td>用户名:</td>
            <td><input type="text" name="username" class="easyui-validatebox" data-options="required:true"></td>
        </tr>
        <tr  id="password">
            <td>密码:</td>
            <td><input type="text" name="password" class="easyui-validatebox"></td>
        </tr>
        <tr>
            <td>手机:</td>
            <td><input type="text" name="phone"></td>
        </tr>
        <tr>
            <td>邮箱:</td>
            <td><input type="text" name="email"></td>
        </tr>
        <tr>
            <td>入职日期:</td>
            <td><input type="text" name="entryTime" class="easyui-datebox"></td>
        </tr>
        <tr>
            <td>部门:</td>
            <td><input id="department" name="department.id" placeholder="选择部门"></td>
        </tr>
        <tr>
            <td>管理员:</td>
            <td><input id="state" name="admin" placeholder="是否为管理员"></td>
        </tr>
        <tr>
            <td>角色:</td>
            <td><input id="role" name="role.id" placeholder="选择担任角色"></td>
        </tr>
    </table>
    </form>
</div>
<div id="excelUpload">
    <form id="uploadForm" method="post" enctype="multipart/form-data">
        <tabel>
            <tr>
                <td><input type="file" name="excel" style="width: 180px; margin-top: 20px; margin-left: 5px;"></td>
                <td><a href="javascript:void(0);" id="downloadTml">下载模板</a></td>
            </tr>
        </tabel>
    </form>
</div>
<div id="pp" class="easyui-pagination" style="background:#efefef;border:1px solid #ccc;"
     data-options="total:2000,pageSize:10">
</div>
</body>
</html>
