$(function () {
    $("#role_dg").datagrid({
        url:"roleList",
        columns:[[
            {field:'rnum',title:'角色编号',width:100,align:'center'},
            {field:'rname',title:'角色名称',width:100,align:'center'},

        ]],
        fit:true,
        fitColumns:true,
        rownumbers:true,
        pagination:true,
        singleSelect:true,
        toolbar:"#toolbar",
    });
    $("#dialog").dialog({
        width:500,
        hight:500,
        closed:true,
        buttons:[{
            text:'保存',
            handler:function(){
                var url;
                var rid = $("[name='rid']").val();
                if(rid){
                    url='updateRole';
                }else{
                    url='saveRole';
                }

                /*提交表单*/
                $("#myform").form("submit",{
                    url:url,
                    onSubmit:function(param){  /*传递额外参数  已选择的权限*/

                        /*获取已经选择的权限*/
                        var allRows =  $("#role_data2").datagrid("getRows");
                        /*遍历出每一个权限*/
                        for(var i = 0; i< allRows.length; i++){
                            /*取出每一个权限 */
                            var row =  allRows[i];
                            /*给它封装到集合中*/
                            param["permissions["+i+"].pid"] = row.pid;
                        }

                    },
                    success:function (data) {
                        data = $.parseJSON(data);
                        if (data.success){
                            $.messager.alert("温馨提示",data.msg);
                            /*关闭对话框 */
                            $("#dialog").dialog("close");
                            /*重新加载数据表格*/
                            $("#role_dg").datagrid("reload");
                        } else {
                            $.messager.alert("温馨提示",data.msg);
                        }
                    }
                });

            }
        },{
            text:'关闭',
            handler:function(){
                $("#dialog").dialog("close");
            }
        }],
    });
    $("#role_data1").datagrid({
        url:'permissionList',
        title:'所有权限',
        width:200,
        height:350,
        fitColumns:true,
        scrollbarSize:1,
        singleSelect:true,
        columns:[[
            {field:'pname',title:'权限名称',width:100,align:'center'},
        ]],
        onClickRow:function (index,rowData) {
            /*判断是否已经存在该权限*/
            /*取出所有的已选权限*/
            var allRows = $("#role_data2").datagrid("getRows");
            /*取出每一个进行判断*/
            for(var i = 0; i<allRows.length; i++){
                /*取出每一行*/
                var row = allRows[i];
                if(rowData.pid == row.pid){/*已经存在该权限*/
                    /*让已经存在权限成为选中的状态*/
                    /*获取已经成为选中状态当前角标*/
                    var index = $("#role_data2").datagrid("getRowIndex",row);
                    /*让该行成为选中状态*/
                    $("#role_data2").datagrid("selectRow",index);
                    return;
                }
            }
            /*把当前选中的,添加到已选权限*/
            $("#role_data2").datagrid("appendRow",rowData);
        }
    });
    $("#role_data2").datagrid({
        title:'已选权限',
        width:200,
        height:350,
        fitColumns:true,
        scrollbarSize:1,
        singleSelect:true,
        columns:[[
            {field:'pname',title:'权限名称',width:100,align:'center'},
        ]],
        onClickRow:function (index,rowData) {
            $("#role_data2").datagrid("deleteRow",index);
        }

    });
    $("#add").click(function () {
        $("#myform").form("clear");
        $("#role_data2").datagrid("loadData",{rows:[]});
        $("#dialog").dialog("setTitle","添加角色");
        $("#dialog").dialog("open")
    })
    $("#edit").click(function () {
        $("#dialog").dialog("setTitle","编辑角色");
        var data = $("#role_dg").datagrid("getSelected");
        if(!data){
            $.messager.alert("温馨提示","请先选择一行数据")
            return;
        }
        $("#myform").form("load",data);
        var options =  $("#role_data2").datagrid("options");
        options.url='getPermissionsByRole?rid='+data.rid;
        $("#role_data2").datagrid("load");
        $("#dialog").dialog("open");
    })
    $("#delete").click(function () {
        var rowData = $("#role_dg").datagrid("getSelected");
        if(!rowData){
            $.messager.alert("温馨提示","请选择一行数据");
            return;
        }
        $.messager.confirm("温馨提示","请确认是否删除该角色",function (res) {
            if(res){
                $.get("deleteRole?rid="+rowData.rid,function (data) {
                    $.messager.alert("温馨提示",data.msg);
                    $("#role_dg").datagrid("reload");
                })
            }
        })
    })
})









