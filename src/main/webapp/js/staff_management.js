$(function () {
    var map = new BMapGL.Map("map_canvas");
    //地图中心设置为重庆大学
    var point = new BMapGL.Point(106.475, 29.571);
    map.centerAndZoom(point, 15);
    map.enableScrollWheelZoom(true);

    flushStaffInfo();

    $("#add_birth").datepicker({
        format: "yyyy-mm-dd",
        language: "zh-CN",
        autoclose: true,
        clearBtn: true
    });

    $("#modify_birth").datepicker({
        format: "yyyy-mm-dd",
        language: "zh-CN",
        autoclose: true,
        clearBtn: true
    });

    $("#modify_staff").on("shown.bs.modal", function () {
        $("#modify_sid").val($("#staff_table>tbody>tr:nth-child(1)>th:nth-child(2)").text());
        $("#modify_sname").val($("#staff_table>tbody>tr:nth-child(2)>th:nth-child(2)").text());
        $("#modify_birth").val($("#staff_table>tbody>tr:nth-child(3)>th:nth-child(2)").text());
        $("#modify_telephone").val($("#staff_table>tbody>tr:nth-child(5)>th:nth-child(2)").text());
        $("#modify_email").val($("#staff_table>tbody>tr:nth-child(6)>th:nth-child(2)").text());

        const gender = $("#staff_table>tbody>tr:nth-child(4)>th:nth-child(2)").text();
        if(gender === "男") $("#modify_sex_male").prop("checked", true);
        else if(gender === "女") $("#modify_sex_female").prop("checked", true);
    });
    $("#add_staff").on("hidden.bs.modal", function () {
        $("#add_staff form input[type=text]").val("");
        $("#add_staff form input[type=file]").val("");
        $("#add_staff form input[type=radio]").removeAttr("checked");
    });
    $("#search_staff").on("hidden.bs.modal", function () {
        $("#search_staff form input[type=text]").val("");
    });
    $("#modify_staff").on("hidden.bs.modal", function () {
        $("#modify_staff form input[type=text]").val("");
        $("#modify_staff form input[type=file]").val("");
        $("#modify_staff form input[type=radio]").removeAttr("checked");
    });
});

function addStaff() {
    //从表单获取数据
    var sid = $("#add_sid").val();
    var sname = $("#add_sname").val();
    var birth = $("#add_birth").val();
    var email = $("#add_email").val();
    var avatar = $("#add_avatar")[0].files[0];
    var gender = $("#add_sex input:checked").val();
    var telephone = $("#add_telephone").val();
    var data = new FormData();

    //验证表单数据
    if(!gender) gender = "";
    if(!sname) sname = "";
    if(!birth) birth = "";
    if(!email) email = "";
    if(!telephone) telephone = "";
    if(!avatar) avatar = "";

    //将验证后的数据加入将要发送的FormData
    data.append("request-type", "addStaff");
    data.append("img", avatar);
    data.append("request-data", JSON.stringify({
        "sid": sid,
        "sname": sname,
        "birth": birth,
        "email": email,
        "gender": gender,
        "telephone": telephone,
    }));

    $.ajax({
        type: "post",
        url: "staff_request.jsp",
        contentType: false,
        processData: false,
        data: data,
        success: function (result) {
            if(parseInt(result["status"].toString()) === 0) {
                alertSuccess("添加成功！");
                //隐藏模态框
                $("#add_staff").modal("hide");
            } else {
                alertWarning("添加失败！");
            }
        }
    });
}

function searchStaff() {
    var sid = $("#search_sid").val();
    if(sid.length === 0) {
        sid = $("#staff_table>tbody>tr:nth-child(1)>th:nth-child(2)").text();
    }

    $.ajax({
        type: "post",
        url: "staff_request.jsp",
        contentType: "application/json; charset=utf-8",
        data: JSON.stringify({"request-type": "searchStaff", "sid": sid}),
        success: function (result) {
            if(parseInt(result["status"].toString()) === 0) {
                $("#search_staff").modal("hide");

                showInfo(result["staff"][0]);
                var location = getStaffCurrentLocation(sid);
                var map = new BMapGL.Map("map_canvas");
                var point = new BMapGL.Point(location.longitude, location.latitude);
                var marker = new BMapGL.Marker(point, {title: location.sid});
                map.centerAndZoom(point, 15);
                map.enableScrollWheelZoom(true);
                map.addOverlay(marker);
            }else {
                alertWarning("无此客服！");
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
    //从表单获取数据
    var sid = $("#modify_sid").val();
    var sname = $("#modify_sname").val();
    var sex = $("#modify_sex input:checked").val();
    var telephone = $("#modify_telephone").val();
    var birth = $("#modify_birth").val();
    var email = $("#modify_email").val();
    var avatar = $("#modify_avatar")[0].files[0];
    var data = new FormData();

    //验证表单数据
    if(!sex) sex = "";
    if(!sname) sname = "";
    if(!birth) birth = "";
    if(!email) email = "";
    if(!telephone) telephone = "";
    if(!avatar) avatar = "";

    //将验证后的数据加入将要发送的FormData
    data.append("request-type", "modifyStaff");
    data.append("img", avatar);
    data.append("request-data", JSON.stringify({
        "sid": sid,
        "sname": sname,
        "birth": birth,
        "email": email,
        "gender": sex,
        "telephone": telephone,
    }));

    $.ajax({
        type: "post",
        url: "staff_request.jsp",
        contentType: false,
        processData: false,
        data: data,
        success: function (result) {
            if(parseInt(result["status"].toString()) === 0) {
                alertSuccess("修改信息成功！");
                //隐藏模态框
                $("#modify_staff").modal("hide");
                searchStaff();
            } else {
                alertWarning("修改信息失败！");
            }
        }
    });
}

function flushStaffInfo() {
    $.ajax({
        type: "post",
        url: "staff_request.jsp",
        contentType: "application/json; charset=utf-8",
        data: JSON.stringify({"request-type": "flushStaff"}),
        success: function (result) {
            let map;
            if (parseInt(result["status"].toString()) === 0) {
                map = new BMapGL.Map("map_canvas");
                var idleIcon = new BMapGL.Icon("img/icon/marker_yellow.png", new BMapGL.Size(23, 25));
                var busyIcon = new BMapGL.Icon("img/icon/marker_red.png", new BMapGL.Size(23, 25));
                var new_point;
                var marker;
                for (var i = 0; i < result["staffNumber"]; i++) {
                    let location = simuLocation();
                    new_point = new BMapGL.Point(location["longitude"], location["latitude"]);
                    if(result["staffs"][i].status.toString() === "idle")
                        marker = new BMapGL.Marker(new_point, {icon: idleIcon});
                    else
                        marker = new BMapGL.Marker(new_point, {icon: busyIcon});
                    marker.addEventListener("click", showInfo.bind(this, result["staffs"][i]));
                    map.addOverlay(marker);
                }
                map.centerAndZoom(new_point, 10);
                map.enableScrollWheelZoom(true);
            }
        }
    })
}

function showInfo(staff) {
    let gender, avatarURL;
    if(staff["gender"].toString() === "male") gender = "男";
    else if(staff["gender"].toString() === "female") gender = "女";
    else gender = "未知";
    if(staff["avatarURL"].toString() === "") avatarURL = "";
    else avatarURL = "img/userAvatar/" + staff["avatarURL"].toString();

    $("#staff_table>tbody>tr:nth-child(1)>th:nth-child(2)").text(staff["sid"]);
    $("#staff_table>tbody>tr:nth-child(2)>th:nth-child(2)").text(staff["name"]);
    $("#staff_table>tbody>tr:nth-child(3)>th:nth-child(2)").text(staff["birth"]);
    $("#staff_table>tbody>tr:nth-child(4)>th:nth-child(2)").text(gender);
    $("#staff_table>tbody>tr:nth-child(5)>th:nth-child(2)").text(staff["telephone"]);
    $("#staff_table>tbody>tr:nth-child(6)>th:nth-child(2)").text(staff["email"]);
    $("#staff_table>tbody>tr:nth-child(7)>th:nth-child(2)").text(staff["dutyTotalTimes"]);
    $("#staff_table>tbody>tr:nth-child(8)>th:nth-child(2)").text(staff["dutyTotalHours"]);
    $("#staff_table>tbody>tr:nth-child(9)>th:nth-child(2)").text(staff["gradeTotal"]);
    $("#staff_table>tbody>tr:nth-child(10)>th:nth-child(2)").text(staff["absenceTotal"]);
    $("#staff_table>tbody>tr:nth-child(11)>th:nth-child(2)").text(staff["dutyMonthTimes"]);
    $("#staff_table>tbody>tr:nth-child(12)>th:nth-child(2)").text(staff["dutyMonthHours"]);
    $("#staff_table>tbody>tr:nth-child(13)>th:nth-child(2)").text(staff["gradeMonth"]);
    $("#staff_table>tbody>tr:nth-child(14)>th:nth-child(2)").text(staff["absenceMonth"]);

    if($("#staff_avatar").length === 0) $("#staff_info_body").append("<img class=\"avatar\" id=\"staff_avatar\" alt=\"\">");
    $("#staff_avatar").attr("src", avatarURL);
    if($("#staff_info_footer>button").length === 0) {
        var button1 = "<button class=\"btn btn-primary\">修改信息</button>";
        $("#staff_info_footer").append(button1);
        $("#staff_info_footer button").attr("style", "margin-left: 70%");
        $("#staff_info_footer button").attr("data-toggle", "modal");
        $("#staff_info_footer button").attr("data-target", "#modify_staff");
    }
}