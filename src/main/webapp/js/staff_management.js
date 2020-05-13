var map;
var staffs;
const idleIcon = new BMap.Icon("img/icon/marker_yellow.png", new BMap.Size(23, 25));
const busyIcon = new BMap.Icon("img/icon/marker_red.png", new BMap.Size(23, 25));

$(function () {
    map = new BMap.Map("map_canvas");
    //地图中心设置为重庆大学
    let point = new BMap.Point(106.475, 29.571);
    map.centerAndZoom(point, 10);
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

    $("#staffs-table").bootstrapTable({
        pagination: true,
        pageSize:10,
        sortName: "name",
        height: 650,
        paginationHAlign: "left",
        search: true,
        showSearchButton: true,
        showSearchClearButton: true,
        undefinedText: "未知",
        onClickCell: function (field, value) {
            if(field === "action")
                initStaffInfo($(value).attr("id").substring(3));
        }
    });

    $("#modify_staff").on("shown.bs.modal", function () {
        $("#modify_sid").val($("#staff_table>tbody>tr:nth-child(1)>th:nth-child(2)").text());
        $("#modify_sname").val($("#staff_table>tbody>tr:nth-child(2)>th:nth-child(2)").text());
        if($("#staff_table>tbody>tr:nth-child(3)>th:nth-child(2)").text() !== "未知")
            $("#modify_birth").val($("#staff_table>tbody>tr:nth-child(3)>th:nth-child(2)").text());
        else $("#modify_birth").val("");
        if($("#staff_table>tbody>tr:nth-child(5)>th:nth-child(2)").text() !== "未知")
            $("#modify_telephone").val($("#staff_table>tbody>tr:nth-child(5)>th:nth-child(2)").text());
        else $("#modify_telephone").val("");
        if($("#staff_table>tbody>tr:nth-child(6)>th:nth-child(2)").text() !== "未知")
            $("#modify_email").val($("#staff_table>tbody>tr:nth-child(6)>th:nth-child(2)").text());
        else $("#modify_email").val("");

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
    var sname = $("#search_sname").val();
    var totalAbscenseTimes = $("#search_totalAbscence").val();
    var data = {};

    if(sid.length === 0) {
        if(sname.length !== 0) {
            data.sname = sname;
        }
        else if(totalAbscenseTimes.length !== 0) {
            data.totalAbscenceTimes = totalAbscenseTimes;
        }
    } else {
        data.sid = sid;
    }

    data["request-type"] = "searchStaff";
    $.ajax({
        type: "post",
        url: "staff_request.jsp",
        contentType: "application/json; charset=utf-8",
        data: JSON.stringify(data),
        success: function (result) {
            let marker;
            $("#search_staff").modal("hide");
            if (parseInt(result["status"].toString()) === 0) {
                staffs = result.staff;
                $("#staffs-table").bootstrapTable("removeAll");
                map.clearOverlays();
                let location = getStaffCurrentLocation(sid);
                let point = new BMap.Point(location.longitude, location.latitude);
                marker = new BMap.Marker(point, {title: result["staff"][0].name});
                marker.addEventListener("click", showInfo.bind(this, result.staff[0]));
                map.addOverlay(marker);
                setMarkerAnimation(marker);
                map.centerAndZoom(point, 15);

                let row = genlistItem(result.staff[0]);
                $("#staffs-table").bootstrapTable("append", row);
            }
            else if(parseInt(result["status"].toString()) === 1) {
                staffs = result.staffs;
                $("#staffs-table").bootstrapTable("removeAll");
                map.clearOverlays();
                let new_point;
                for (var i = 0; i < result["staffNumber"]; i++) {
                    let location = simuLocation();
                    new_point = new BMap.Point(location["longitude"], location["latitude"]);
                    if(result["staffs"][i].status.toString() === "idle")
                        marker = new BMap.Marker(new_point, {icon: idleIcon, title: result["staffs"][i].name});
                    else
                        marker = new BMap.Marker(new_point, {icon: busyIcon, title: result["staffs"][i].name});
                    marker.addEventListener("click", showInfo.bind(this, result["staffs"][i]));
                    map.addOverlay(marker);
                    setMarkerAnimation(marker);
                    let row = genlistItem(result.staffs[i]);
                    $("#staffs-table").bootstrapTable("append", row);
                }
                map.panTo(new_point);
                map.centerAndZoom(new_point, 15);
            }
            else {
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
            if (parseInt(result["status"].toString()) === 0) {
                staffs = result.staffs;
                map.clearOverlays();
                $("#staffs-table").bootstrapTable("removeAll");
                let new_point;
                let marker;
                for (var i = 0; i < result["staffNumber"]; i++) {
                    let location = simuLocation();
                    new_point = new BMap.Point(location["longitude"], location["latitude"]);
                    if(result["staffs"][i].status.toString() === "idle")
                        marker = new BMap.Marker(new_point, {icon: idleIcon, title: result["staffs"][i].name});
                    else
                        marker = new BMap.Marker(new_point, {icon: busyIcon, title: result["staffs"][i].name});
                    marker.addEventListener("click", showInfo.bind(this, result["staffs"][i]));
                    map.addOverlay(marker);
                    setMarkerAnimation(marker);

                    let row = genlistItem(result.staffs[i]);
                    $("#staffs-table").bootstrapTable("append", row);
                }
                map.centerAndZoom(new_point, 10);
            } else {
                alertWarning("地图刷新失败");
            }
        },
        fail: function () {
            alertWarning("服务器连接失败");
        }
    })
}

function showInfo(staff) {
    initStaffInfo(staff.sid);
    $("#staff_info").modal("show");
}

function initStaffInfo(sid) {
    let i;
    for(i = 0; i < staffs.length; i++){
        if(sid === staffs[i].sid.toString()) break;
    }

    let staff = staffs[i];

    let names = Object.keys(staff);
    for(let j in names) {
        if(staff[names[j]] === "") staff[names[j]] = "未知";
    }

    let gender, avatarURL, status;
    if(staff["gender"].toString() === "male") gender = "男";
    else if(staff["gender"].toString() === "female") gender = "女";
    else gender = "未知";
    if(staff["avatarURL"].toString() === "") avatarURL = "";
    else avatarURL = "img/userAvatar/" + staff["avatarURL"].toString();
    if(staff["status"].toString() === "idle") status = "空闲";
    else if(staff["status"].toString() === "onduty") status = "出勤中";
    else status = "未知";

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
    $("#staff_table>tbody>tr:nth-child(15)>th:nth-child(2)").text(status);

    if($("#staff_avatar").length === 0) $("#staff_info .modal-body").prepend("<img class=\"avatar\" id=\"staff_avatar\" alt=\"\">");
    $("#staff_avatar").attr("src", avatarURL);
}

function genlistItem(staff) {
    let item;
    let button = "<button class='btn btn-group' data-toggle='modal' data-target='#staff_info' id='bt-" + staff.sid.toString() +
        "'>查看</button>";
    item = {name: staff.name === ""?"未知":staff.name, status: staff.status === ""?"未知":staff.status,
        gender: staff.gender === ""?"未知":staff.gender, telephone: staff.telephone === ""?"未知":staff.telephone, action: button};

    return item;
}