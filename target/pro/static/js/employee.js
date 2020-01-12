$(function () {
    //数据表
    $("#dataGrid").datagrid({
        url:"/employeeList",
        columns:[[
            {field:'username',title:'姓名',width:100,align:'center'},
            {field:'entryTime',title:'入职时间',width:100,align:'center'},
            {field:'phone',title:'电话',width:100,align:'center'},
            {field:'email',title:'电子邮件',width:100,align:'center'},
            {field:'department',title:'部门',width:100,align:'center',formatter:function (value,row,index) {
                    if(value){
                        return value.name;
                    }
                }},
            {field:'state',title:'状态',width:100,align:'center',formatter:function (value,row,index) {
                    if(row.state){
                        return "在职";
                    }else{
                        return "<front style='color: red'>离职</front>"
                    }
                }},
            {field:'admin',title:'管理员',width:100,align:'center',formatter:function (value,row,index) {
                    if(row.state){
                        return "是";
                    }else{
                        return "否";
                    }
                }},

        ]],
        fit:true,
        fitColumns:true,
        rownumbers:true,
        pagination:true,
        singleSelect:true,
        toolbar:"#toolbar",
        onClickRow:function (index,row) {
            if(!row.state){
                $("#delete").linkbutton("disable");
            }else {
                $("#delete").linkbutton("enable");
            }
        }

    });
    //对话框
    $("#box").dialog({
        width:400,
        height:450,
        closed:true,
        buttons:[{
            text:'保存',
            handler:function () {
                var url;
                var id = $("[name='id']").val();
                if(id){
                    url="updateEmployee";
                }else{
                    url="saveEmployee";
                }
                $("#employeeForm").form("submit",{
                    url:url,
                    onSubmit:function(param){
                        var values = $("#role").combobox("getValues");
                        for(var i=0;i<values.length;i++){
                            var rid = values[i];
                            param["roles["+i+"].rid"] = rid;
                        }
                    },
                    success:function (data) {
                        data = $.parseJSON(data)
                        if(data.success){
                            $.messager.alert("温馨提示",data.msg);
                            $("#box").dialog("close");
                            $("#dataGrid").datagrid("reload");
                        }else{
                            $.messager.alert("温馨提示",data.msg);
                        }

                    },

                });
            }
        },{
            text:'关闭',
            handler:function () {
                $("#box").dialog("close");
            }
        }],
    });
    //对话框中下拉input
    $("#state").combobox({
       width:150,
       panelHeight:'auto',
       textField:'text',
       valueField:'value',
       editable:false,
        data:[{
           text:'是',
            value:'true'
        },{
           text:'否',
            value:'false'
        }],
        onLoadSuccess:function () {
            $("#state").each(function(i){
                var span = $(this).siblings("span")[i];
                var targetInput = $(span).find("input:first");
                if(targetInput){
                    $(targetInput).attr("placeholder", $(this).attr("placeholder"));
                }
            });
        }
    });
    $("#department").combobox({
       width:150,
       panelHeight:'auto',
       url:'/departmentList',
        textField:'name',
        valueField:'id',
        editable:false,
        onLoadSuccess:function () {
            $("#department").each(function(i){
                var span = $(this).siblings("span")[i];
                var targetInput = $(span).find("input:first");
                if(targetInput){
                    $(targetInput).attr("placeholder", $(this).attr("placeholder"));
                }
            });
        }
    });
    $("#role").combobox({
        width:150,
        panelHeight:'auto',
        url:'/getAllRole',
        textField:'rname',
        valueField:'rid',
        editable:false,
        multiple:true,
        onLoadSuccess:function () {
            $("#role").each(function(i){
                var span = $(this).siblings("span")[i];
                var targetInput = $(span).find("input:first");
                if(targetInput){
                    $(targetInput).attr("placeholder", $(this).attr("placeholder"));
                }
            });
        }
    });
    //工具栏按钮
    $("#add").click(function () {
        $("#employeeForm").form("clear")
        $("#password").show();
        $("[name='password']").validatebox({required:true});
        $("#box").dialog("setTitle","添加员工");
        $("#box").dialog("open");
    });
    $("#edit").click(function () {
        var data = $("#dataGrid").datagrid("getSelected");
        if(!data){
            $.messager.alert("提示","请先选择一行数据")
            return;
        }
        console.log(data)
        $("[name='password']").validatebox({required:false});
        $("#box").dialog("setTitle","编辑员工");
        //数据回显
        data["department.id"] = data["department"].id;
        data["admin"] = data["admin"]+"";
        $.get("getRoleByEid?eid="+data.id,function (data) {
            $("#role").combobox("setValues",data);
        })

        $("#password").hide()
        $("#box").dialog("open");
        $("#employeeForm").form("load",data)
    });
    $("#delete").click(function () {
        var rowData = $("#dataGrid").datagrid("getSelected");
        if(!rowData){
            $.messager.alert("提示","请选择一个对象");
            return;
        }
        $.messager.confirm("提示","确认是否要进行离职操作",function (res) {
            if(res){
                $.get("/updateState?id="+rowData.id,function (data) {
                        $.messager.alert("温馨提示",data.msg);
                        $("#dataGrid").datagrid("reload");
                })
            }
        })

    });
    $("#getAll").click(function () {
        $("[name='keyword']").val('');
        $("#dataGrid").datagrid("load",{});
    })
    //高级查询
    $("#searchbtn").click(function () {
        var keyword = $("[name='keyword']").val();
        $("#dataGrid").datagrid("load",{keyword:keyword});
    })

    $("#excelOut").click(function () {
        window.open("downloadExcel");
    })

    $("#excelUpload").dialog({
        width:260,
        height:180,
        title:"导入Excel",
        buttons:[{
            text:'保存',
            handler:function(){
                console.log("dadadadadadadad");
                $("#uploadForm").form("submit",{
                    url:"uploadExcelFile",
                    success:function (data) {
                        data = $.parseJSON(data);
                        if (data.success){
                            $.messager.alert("温馨提示",data.msg);
                            /*关闭对话框 */
                            $("#excelUpload").dialog("close");
                            /*重新加载数据表格*/
                            $("#dataGrid").datagrid("reload");
                        } else {
                            $.messager.alert("温馨提示",data.msg);
                        }
                    }
                })
            }
        },{
            text:'关闭',
            handler:function(){
                $("#excelUpload").dialog("close");
            }
        }],
        closed:true
    })

    $("#excelIn").click(function () {
        $("#excelUpload").dialog("open");
    });

    $("#downloadTml").click(function () {
       window.open("downloadExcelTpl")
    });

});