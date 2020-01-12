<%--
  Created by IntelliJ IDEA.
  User: hao
  Date: 20-1-6
  Time: 上午10:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <%@include file="/static/common/common.jsp"%>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/js/role.js"></script>
    <style>
        #dialog #myform .panel{
            padding: auto;
            margin: auto;
        }
        #dialog #myform .panel-header{
            height: 20px;
            margin-top: -15px;
        }

    </style>
</head>
<body>
<!--工具栏-->
<div id="toolbar">
    <a href="#" id="add" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true">添加</a>
    <a href="#" id="edit" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true">编辑</a>
    <a href="#" id="delete" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true">删除</a>
</div>
<!--数据表-->
<table id="role_dg"></table>
<!--对话框-->
<div id="dialog">
    <form id="myform">
        <table align="center" style="border-spacing: 0px 10px;font-size: small" >
            <input type="hidden" name="rid">
            <tr align="center">
                <td>角色编号: <input type="text" name="rnum" class="easyui-validatebox" ></td>
                <td>角色名称: <input type="text" name="rname" class="easyui-validatebox" ></td>
            </tr>
            <tr>
                <td><div id="role_data1"></div></td>
                <td><div id="role_data2"></div></td>
            </tr>
        </table>
    </form>
</div>
</body>
</html>
