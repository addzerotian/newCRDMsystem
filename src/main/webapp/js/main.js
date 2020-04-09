function alertLoginNoUser() {
    window.alert("用户账号名/密码错误！");
    window.location.href = "index.jsp";
}

function alertUserLoggedin() {
    window.alert("用户已登录！如需登录其它账号，请注销。");
}

function logout() {
    $.ajax({
        type: "post",
        url: "admin_requests.jsp",
        contentType: "application/json; charset=utf-8",
        data: JSON.stringify({"request-type": "logout"}),
        success: function (result) {
            if(result["status"] == 0) {
                window.alert("账号注销成功，请重新登录");
                window.location.href = "index.jsp";
            } else {
                window.alert("账号注销失败！");
            }
        }
    });
}

function flashMap() {
    $.ajax({
        type: "post",
        url: "requestList.jsp",
        contentType: "application/json",
        data: JSON.stringify({"request-type": "flushMap"}),
        success: function (result) {
            if(result["status"] == 0) {
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
    if($("#request_info>button").length == 0) {
        var button1 = "<button class=\"btn btn-default\">确认</button>";
        var button2 = "<button class=\"btn btn-default\">取消</button>";
        $("#request_info").append(button1, button2);
    }
}

function addStaff() {
    var sid = $("#add_sid").val();
    var sname = $("#add_sname").val();
    $.ajax({
        type: "post",
        url: "staff_request.jsp",
        contentType: "application/json; charset=utf-8",
        data: JSON.stringify({"request-type": "addStaff", "sid": sid, "sname": sname}),
        success: function (result) {
            if(result["status"] == 0) {
                window.alert("添加成功！");
            } else {
                window.alert("添加失败！");
            }
        }
    });
}

function searchStaff() {
    var sid = $("#search_sid").val();
    if(sid.length == 0) {
        sid = $("#staff_table>tbody>tr:nth-child(1)>th:nth-child(2)").text();
    }

    $.ajax({
        type: "post",
        url: "staff_request.jsp",
        contentType: "application/json; charset=utf-8",
        data: JSON.stringify({"request-type": "searchStaff", "sid": sid}),
        success: function (result) {
            if(result["status"] == 0) {
                $("#search_staff").modal("hide");
                $("#staff_table>tbody>tr:nth-child(1)>th:nth-child(2)").text(result["staff"][0]["sid"]);
                $("#staff_table>tbody>tr:nth-child(2)>th:nth-child(2)").text(result["staff"][0]["name"]);
                $("#staff_table>tbody>tr:nth-child(3)>th:nth-child(2)").text(result["staff"][0]["birth"]);
                $("#staff_table>tbody>tr:nth-child(4)>th:nth-child(2)").text(result["staff"][0]["sex"]);
                $("#staff_table>tbody>tr:nth-child(5)>th:nth-child(2)").text(result["staff"][0]["telephone"]);
                $("#staff_table>tbody>tr:nth-child(6)>th:nth-child(2)").text(result["staff"][0]["dutyTotalTimes"]);
                $("#staff_table>tbody>tr:nth-child(7)>th:nth-child(2)").text(result["staff"][0]["dutyTotalHours"]);
                $("#staff_table>tbody>tr:nth-child(8)>th:nth-child(2)").text(result["staff"][0]["gradeTotal"]);
                $("#staff_table>tbody>tr:nth-child(9)>th:nth-child(2)").text(result["staff"][0]["absenceTotal"]);
                $("#staff_table>tbody>tr:nth-child(10)>th:nth-child(2)").text(result["staff"][0]["dutyMonthTimes"]);
                $("#staff_table>tbody>tr:nth-child(11)>th:nth-child(2)").text(result["staff"][0]["dutyMonthHours"]);
                $("#staff_table>tbody>tr:nth-child(12)>th:nth-child(2)").text(result["staff"][0]["gradeMonth"]);
                $("#staff_table>tbody>tr:nth-child(13)>th:nth-child(2)").text(result["staff"][0]["absenceMonth"]);
                if($("#staff_info_footer>button").length == 0) {
                    var button1 = "<button class=\"btn btn-primary\">修改信息</button>";
                    $("#staff_info_footer").append(button1);
                    $("#staff_info_footer button").attr("style", "margin-left: 70%");
                    $("#staff_info_footer button").attr("data-toggle", "modal");
                    $("#staff_info_footer button").attr("data-target", "#modify_staff");
                }
                var location = getStaffCurrentLocation(sid);
                var map = new BMapGL.Map("map_canvas");
                var point = new BMapGL.Point(location.longitude, location.latitude);
                var marker = new BMapGL.Marker(point, {title: location.sid});
                map.centerAndZoom(point, 15);
                map.enableScrollWheelZoom(true);
                map.addOverlay(marker);
            }else {
                window.alert("无此客服！");
            }
        }
    });
}

function simuLocation() {
    //随机生成一个在重庆市范围内的坐标
    var left = 105.7;
    var right = 107.0;
    var up = 30.1;
    var bottom = 29.0;
    var longitude = Math.random() * (right - left) + left;
    var latitude = Math.random() * (up - bottom) + bottom;

    return {"longitude": longitude, "latitude": latitude};
}

function getStaffCurrentLocation(sid) {
    var location = simuLocation();
    location.sid = sid;

    return location;
}
function modifyStaff() {
    var sid = $("#modify_sid").val();
    var sname = $("#modify_sname").val();
    var sex = $("#modify_sex input:checked").val();
    var telephone = $("#modify_telephone").val();
    if(!sex) sex = "";

    $("#modify_staff").modal("hide");
    $.ajax({
        type: "post",
        url: "staff_request.jsp",
        contentType: "application/json; charset=utf-8",
        data: JSON.stringify({"request-type": "modifyStaff", "sid": sid, "sname": sname, "sex": sex, "telephone": telephone}),
        success: function (result) {
            if(result["status"] == 0) {
                window.alert("修改信息成功！");
                searchStaff();
            } else {
                window.alert("修改信息失败！");
            }
        }
    });
}

function getSystemAdminsInfo() {
    $.ajax({
        type: "post",
        url: "admin_requests.jsp",
        contentType: "application/json; charset=utf-8",
        data: JSON.stringify({"request-type": "getInfo"}),
        success: function (result) {
            if(result["status"] == 0) {
                $("#content tbody>tr:nth-child(1)>th:nth-child(2)").text(result["total"]);
                $("#content tbody>tr:nth-child(2)>th:nth-child(2)").text(result["active"]);
            } else {
                window.alert("查询信息失败！");
            }
        }
    });
}

function searchAdmin() {
    var aid = $("#search_aid").val();
    if(aid.length == 0) {
        aid = $("#admin_table>tbody>tr:nth-child(1)>th:nth-child(2)").text();
    }

    $.ajax({
        type: "post",
        url: "admin_requests.jsp",
        contentType: "application/json; charset=utf-8",
        data: JSON.stringify({"request-type": "searchAdmin", "aid": aid}),
        success: function (result) {
            if(result["status"] == 0) {
                $("#search_admin").modal("hide");
                $("#admin_table>tbody>tr:nth-child(1)>th:nth-child(2)").text(result["admin"][0]["aid"]);
                $("#admin_table>tbody>tr:nth-child(2)>th:nth-child(2)").text(result["admin"][0]["name"]);
                $("#admin_table>tbody>tr:nth-child(3)>th:nth-child(2)").text(result["admin"][0]["birth"]);
                $("#admin_table>tbody>tr:nth-child(4)>th:nth-child(2)").text(result["admin"][0]["sex"]);
                $("#admin_table>tbody>tr:nth-child(5)>th:nth-child(2)").text(result["admin"][0]["telephone"]);
                $("#admin_table>tbody>tr:nth-child(6)>th:nth-child(2)").text(result["admin"][0]["authority"]);
                $("#info_admin").modal("show");
            } else {
                window.alert("查询失败！");
            }
        }
    });
}

function addAdmin() {
    var aid = $("#add_aid").val();
    var aname = $("#add_aname").val();
    var authority = $("#add_authority").val();

    $("#add_admin").modal("hide");
    $.ajax({
        type: "post",
        url: "admin_requests.jsp",
        contentType: "application/json; charset=utf-8",
        data: JSON.stringify({"request-type": "addAdmin", "aid": aid, "aname": aname, "authority": authority}),
        success: function (result) {
            if(result["status"] == 0) {
                window.alert("添加成功！");
            } else {
                window.alert("添加失败！");
            }
        }
    });
}

function modifyAdmin() {
    var aid = $("#modify_aid").val();
    var aname = $("#modify_aname").val();
    var sex = $("#modify_sex input:checked").val();
    var telephone = $("#modify_telephone").val();
    var authority = $("#modify_authority").val();
    if(!sex) sex = "";

    $("#modify_admin").modal("hide");
    $.ajax({
        type: "post",
        url: "admin_requests.jsp",
        contentType: "application/json; charset=utf-8",
        data: JSON.stringify({"request-type": "modifyAdmin", "aid": aid, "aname": aname, "sex": sex, "telephone": telephone, "authority": authority}),
        success: function (result) {
            if(result["status"] == 0) {
                window.alert("修改信息成功！");
                searchAdmin();
            } else {
                window.alert("修改信息失败！");
            }
        }
    });
}

function plusAuthority() {
    $("#add_authority").val(parseInt($("#add_authority").val()) + 1);
}

function minusAuthority(adminAuth) {
    var authority = parseInt($("#add_authority").val());
    if(authority > (adminAuth + 1)) {
        $("#add_authority").val(authority - 1);
    }
}