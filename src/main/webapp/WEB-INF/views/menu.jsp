<%--
  Created by IntelliJ IDEA.
  User: hao
  Date: 20-1-6
  Time: 上午10:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <%@include file="/static/common/common.jsp"%>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/js/menu.js"></script>
</head>
<body>
<!-- 数据表格 -->
<table id="menu_datagrid"></table>
<!-- 数据表格CRUD按钮 -->
<div id="menu_toolbar">
    <div>
        <a class="easyui-linkbutton" iconCls="icon-add" plain="true" id="add">新增</a>
        <a class="easyui-linkbutton" iconCls="icon-edit" plain="true" id="edit">编辑</a>
        <a class="easyui-linkbutton" iconCls="icon-remove" plain="true" id="del">刪除</a>
        <a class="easyui-linkbutton" iconCls="icon-reload" plain="true" id="reload">刷新</a>
    </div>
</div>

<div id="menu_dialog">
    <form id="menu_form" method="post">
        <table align="center" style="margin-top: 15px;border-spacing: 10px 20px;font-size: small">
            <input type="hidden" name="id">
            <tr>
                <td>菜单名称</td>
                <td><input type="text" name="text"></td>
            </tr>
            <tr>
                <td>请求URL</td>
                <td><input type="text" name="url"></td>
            </tr>
            <tr>
                <td>父菜单</td>
                <td><input id="parentMenu" type="text" name="parentMenu.id" class="easyui-combobox" placeholder="请选择父菜单"/></td>
            </tr>
        </table>
    </form>
</div>

<!-- 对话框保存取消按钮 -->
<div id="menu_dialog_bt">
    <a class="easyui-linkbutton" plain="true" data-cmd="save" id="save">保存</a>
    <a class="easyui-linkbutton" plain="true" data-cmd="cancel" id="cancel">取消</a>
</div>
</body>
</html>
