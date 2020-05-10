const requestStatusCode = {"0": "初次申请", "1": "已派工", "-1": "用户已取消", "-2": "已拒绝" };

function alertLoginNoUser() {
    window.alert("用户账号名/密码错误！");
    window.location.href = "index.jsp";
}

function alertUserLoggedin() {
    alertWarning("用户已登录！如需登录其它账号，请注销。");
}

function logout() {
    confirmWarning("是否确认注销？", function () {
        $.ajax({
            type: "post",
            url: "admin_requests.jsp",
            contentType: "application/json; charset=utf-8",
            data: JSON.stringify({"request-type": "logout"}),
            success: function (result) {
                if(parseInt(result["status"].toString()) === 0) {
                    window.alert("账号注销成功，请重新登录");
                    window.location.href = "index.jsp";
                } else {
                    alertDanger("账号注销失败！");
                }
            }
        });
    })
}

function plusAuthority(inputAuth) {
    inputAuth.val(parseInt(inputAuth.val()) + 1);
}

function minusAuthority(inputAuth, adminAuth) {
    var authority = parseInt(inputAuth.val());
    if(authority > (adminAuth + 1)) {
        inputAuth.val(authority - 1);
    }
}

function modifiable(adminAuth, modifiedAuth) {
    var button = $("#info_admin button.btn-primary");

    if(adminAuth >= modifiedAuth) {
        button.removeAttr("data-dismiss");
        button.removeAttr("data-toggle");
        button.removeAttr("data-target");
        button.attr("title", "权限不够");
        button.attr("disabled", true);
    } else {
        button.removeAttr("title");
        button.removeAttr("disabled");
        button.attr("data-dismiss", "modal");
        button.attr("data-toggle", "modal");
        button.attr("data-target", "#modify_admin");
    }
}

function test() {
    var avatar = $("#modify_avatar")[0].files[0];
    var data = new FormData();

    if(!avatar) avatar = "";

    //将验证后的数据加入将要发送的FormData
    data.append("request-type", "test");
    data.append("img", avatar);

    $.ajax({
        type: "post",
        url: "admin_requests.jsp",
        contentType: false,
        processData: false,
        async: false,
        data: data,
        success: function (result) {
            if(parseInt(result["status"].toString()) === 0) {
                alertSuccess("修改信息成功！");
                flushInfo();
            } else {
                alertWarning("修改信息失败！");
            }
        },
        error: function (status, error) {

        }
    });
}

function setMarkerAnimation(marker) {
    marker.setAnimation(BMAP_ANIMATION_BOUNCE);
    marker.addEventListener("mouseover", function (e) {
        e.target.setAnimation(null);
    });
    marker.addEventListener("mouseout", function (e) {
        e.target.setAnimation(BMAP_ANIMATION_BOUNCE);
    });
}