$(function () {
    $("#jqGrid").jqGrid({
        url: '/admin/users/adminlist',
        datatype: "json",
        colModel: [
            {label: 'id', name: 'userId', index: 'userId', width: 50, key: true, hidden: true},
            {label: '昵称', name: 'nick_name', index: 'nick_name', width: 180},
            {label: '登录名', name: 'userEmail', index: 'userEmail', width: 120},
            {label: '身份状态', name: 'locked', index: 'locked', width: 60, formatter: lockedFormatter},
           // {label: '是否注销', name: 'isDeleted', index: 'isDeleted', width: 60, formatter: deletedFormatter},
           // {label: '注册时间', name: 'createTime', index: 'createTime', width: 120}
        ],
        height: 560,
        rowNum: 10,
        rowList: [10, 20, 50],
        styleUI: 'Bootstrap',
        loadtext: '信息读取中...',
        rownumbers: false,
        rownumWidth: 20,
        autowidth: true,
        multiselect: true,
        pager: "#jqGridPager",
        jsonReader: {
            root: "data.list",
            page: "data.currPage",
            total: "data.totalPage",
            records: "data.totalCount"
        },
        prmNames: {
            page: "page",
            rows: "limit",
            order: "order",
        },
        gridComplete: function () {
            //隐藏grid底部滚动条
            $("#jqGrid").closest(".ui-jqgrid-bdiv").css({"overflow-x": "hidden"});
        }
    });

    $(window).resize(function () {
        $("#jqGrid").setGridWidth($(".card-body").width());
    });

    function lockedFormatter(cellvalue) {
        if (cellvalue == 1) {
            return "<button type=\"button\" class=\"btn btn-block btn-secondary btn-sm\" style=\"width: 50%;\">锁定</button>";
        } else if (cellvalue == 0) {
            return "<button type=\"button\" class=\"btn btn-block btn-success btn-sm\" style=\"width: 50%;\">正常</button>";
        }
    }

    function deletedFormatter(cellvalue) {
        if (cellvalue == 1) {
            return "<button type=\"button\" class=\"btn btn-block btn-secondary btn-sm\" style=\"width: 50%;\">注销</button>";
        } else if (cellvalue == 0) {
            return "<button type=\"button\" class=\"btn btn-block btn-success btn-sm\" style=\"width: 50%;\">正常</button>";
        }
    }
});


function adminAdd() {
    reset();
    $('.modal-title').html('店员添加');
    $('#categoryModal').modal('show');
}
function reset() {
    $("#loginName").val('');
    $("#password1").val('');
    $("#password2").val('');
    $("#nickName").val('');
    $('#edit-error-msg').css("display", "none");
}



//绑定modal上的保存按钮
$('#saveButton').click(function () {
   var loginName  = $("#loginName").val();
    var password1=$("#password1").val();
    var password2= $("#password2").val();
    var nickName=$("#nickName").val();

    if(!/^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/.test(loginName)){
        swal('请输入正确的邮箱账号', {
            icon: "error",
        });
        return;
    }
    if (password1.trim().length < 3) {
        swal("请输入正确的密码", {
            icon: "error",
        });
        return;
    }
    if (password2.trim().length < 3) {
        swal("请输入正确的密码", {
            icon: "error",
        });
        return;
    }
    if (password1.trim()!=password2.trim()) {
        swal("请输入相同的密码", {
            icon: "error",
        });
        return;
    }
    if (nickName.trim().length < 2) {
        swal("请输入正确的昵称", {
            icon: "error",
        });
        return;
    }
        var data = {
            "loginName": loginName,
            "password": password1,
            "nickName": nickName
        };
        var url = '/admin/users/addAdmin';
        $.ajax({
            type: 'POST',//方法类型
            url: url,
            contentType: 'application/json',
            data: JSON.stringify(data),
            success: function (result) {
                if (result.resultCode == 200) {
                    $('#categoryModal').modal('hide');
                    swal("保存成功", {
                        icon: "success",
                    });
                    reload();
                } else {
                    $('#categoryModal').modal('hide');
                    swal(result.message, {
                        icon: "error",
                    });
                }
                ;
            },
            error: function () {
                swal("操作失败", {
                    icon: "error",
                });
            }
        });

});


/**
 * jqGrid重新加载
 */
function reload() {
    var page = $("#jqGrid").jqGrid('getGridParam', 'page');
    $("#jqGrid").jqGrid('setGridParam', {
        page: page
    }).trigger("reloadGrid");
}


function lockUser(lockStatus) {
    var ids = getSelectedRows();
    if (ids == null) {
        return;
    }
    if (lockStatus != 0 && lockStatus != 1) {
        swal('非法操作', {
            icon: "error",
        });
    }
    swal({
        title: "确认弹框",
        text: "确认要修改账号状态吗?",
        icon: "warning",
        buttons: true,
        dangerMode: true,
    }).then((flag) => {
            if (flag) {
                $.ajax({
                    type: "POST",
                    url: "/admin/users/adminlock/" + lockStatus,
                    contentType: "application/json",
                    data: JSON.stringify(ids),
                    success: function (r) {
                        if (r.resultCode == 200) {
                            swal("操作成功", {
                                icon: "success",
                            });
                            $("#jqGrid").trigger("reloadGrid");
                        } else {
                            swal(r.message, {
                                icon: "error",
                            });
                        }
                    }
                });
            }
        }
    )
    ;
}