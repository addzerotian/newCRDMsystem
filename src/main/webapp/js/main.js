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

function flashMap() {
    $.ajax({
        type: "post",
        url: "requestList.jsp",
        contentType: "application/json",
        data: JSON.stringify({"request-type": "flushMap"}),
        success: function (result) {
            if(parseInt(result["status"].toString()) === 0) {
                map = new BMapGL.Map("map_canvas");
                var new_point;
                var marker;
                for (var i = 0; i < result["requestNumber"]; i++) {
                    new_point = new BMapGL.Point(result["request"][i]["longitude"], result["request"][i]["latitude"]);
                    marker = new BMapGL.Marker(new_point);
                    marker.addEventListener("click", showInfo.bind(this, result["request"][i]));
                    map.addOverlay(marker);
                }
                map.centerAndZoom(new_point, 15);
                map.enableScrollWheelZoom(true);
            }
        }
    })
}

function showInfo(request) {
    $("#info_table>tbody>tr:nth-child(1)>th:nth-child(2)").text(request["cid"]);
    $("#info_table>tbody>tr:nth-child(2)>th:nth-child(2)").text(request["startTime"]);
    $("#info_table>tbody>tr:nth-child(3)>th:nth-child(2)").text(request["status"]);
    $("#info_table>tbody>tr:nth-child(4)>th:nth-child(2)").text(request["location"]);
    if($("#request_info>button").length === 0) {
        var button1 = "<button class=\"btn btn-default\">确认</button>";
        var button2 = "<button class=\"btn btn-default\">取消</button>";
        $("#request_info").append(button1, button2);
    }
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