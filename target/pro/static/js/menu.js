$(function(){

    $("#menu_datagrid").datagrid({
        url:"menuList",
        columns:[[
            {field:'text',title:"菜单名称",width:100,align:'center'},
            {field:'url',title:"请求URL",width:100,align:'center'},
            {field:'parentMenu',title:"父菜单",width:100,align:'center',formatter:function(value,row,index){
                    return value?value.text:'';
                }
            }
        ]],
        fit:true,
        fitColumns:true,
        rownumbers:true,
        singleSelect:true,
        pagination:true,
        toolbar:'#menu_toolbar'
    });
    /*
         * 初始化新增/编辑对话框
         */
    $("#menu_dialog").dialog({
        width:300,
        height:300,
        closed:true,
        buttons:'#menu_dialog_bt'
    });
    $("#parentMenu").combobox({
        width:150,
        panelHeight:'auto',
        url:'/getAllMenu',
        textField:'text',
        valueField:'id',
        editable:false,
        onLoadSuccess:function () {
            $("#parentMenu").each(function(i){
                var span = $(this).siblings("span")[i];
                var targetInput = $(span).find("input:first");
                if(targetInput){
                    $(targetInput).attr("placeholder", $(this).attr("placeholder"));
                }
            });
        }
    });

    $("#add").click(function () {
        $("#menu_form").form("clear");
        $("#menu_dialog").dialog("setTitle","添加菜单");
        $("#menu_dialog").dialog("open");
    });
    $("#edit").click(function () {
        $("#menu_form").form("clear");
        var data = $("#menu_datagrid").datagrid("getSelected");
        if(!data){
            $.messager.alert("提示","请先选择一行数据")
            return;
        }
        if(data.parentMenu){
            data["parentMenu.id"] = data.parentMenu.id;
        }else{
            $("#parentMenu").each(function(i){
                var span = $(this).siblings("span")[i];
                var targetInput = $(span).find("input:first");
                if(targetInput){
                    $(targetInput).attr("placeholder", $(this).attr("placeholder"));
                }
            });
        }
        $("#menu_dialog").dialog("setTitle","编辑菜单");
        $("#menu_dialog").dialog("open");
        $("#menu_form").form("load",data)

    });
    $("#del").click(function () {
        var rowData = $("#menu_datagrid").datagrid("getSelected");
        if(!rowData){
            $.messager.alert("温馨提示","请先选择一行数据")
            return;
        }
        $.messager.confirm("温馨提示","是否删除此菜单项",function (msg) {
            if(msg){
                $.get("deleteMenu?id="+rowData.id,function (data) {
                    $.messager.alert("温馨提示",data.msg);
                    $("#menu_datagrid").datagrid("reload");
                })
            }

        })
    })
    $("#reload").click(function () {
        $("#menu_datagrid").datagrid("load",{})
    })



    $("#save").click(function () {
        var url;
        var id = $("[name='id']").val();
        var parent_id = $("[name='parentMenu.id']").val();
        if(id){
            if (id==parent_id){
                $.messager.confirm("温馨提示","父菜单不能选择自己")
                return;
            }
            url="updateMenu";
        }else{
            url="saveMenu";
        }


        $("#menu_form").form("submit",{
            url:url,
            success:function (data) {
                data = $.parseJSON(data)
                if(data.success){
                    $.messager.alert("温馨提示",data.msg);
                    $("#menu_dialog").dialog("close");
                    $("#parentMenu").combobox("reload");
                    $("#menu_datagrid").datagrid("reload");
                }else{
                    $.messager.alert("温馨提示",data.msg);
                }

            },

        });


    })

});