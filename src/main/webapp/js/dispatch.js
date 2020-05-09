var map;
var staffs;

$(function () {
    map = new BMap.Map("map_canvas");
    //地图中心设置为重庆大学
    let point = new BMap.Point(106.475, 29.571);
    map.centerAndZoom(point, 15);
    map.enableScrollWheelZoom(true);
})

function getStaffsAroundCustomer(cusLng, cusLat) {
    let point = new BMap.Point(cusLng, cusLat);
    let marker = new BMap.Marker(point, {title: "客户"});

    map.clearOverlays();
    map.addOverlay(marker);
    map.centerAndZoom(point, 15);

    simuAroundStaffs(cusLng, cusLat);
}

function simuAroundStaffs(cusLng, cusLat) {
    $.ajax({
        type: "post",
        url: "staff_request.jsp",
        contentType: "application/json; charset=utf-8",
        data: JSON.stringify({"request-type": "searchAroundStaffs", "longitude": cusLng, "latitude": cusLat}),
        success: function (result) {
            if(parseInt(result.status) === 0) {
                staffs = result.staffs;
                let myIcon = new BMap.Icon("img/icon/marker_yellow.png", new BMap.Size(23, 25));

                for(let i = 0; i < parseInt(result.staffNum); i++) {
                    new_point = new BMap.Point(result.staffs[i].longitude, result.staffs[i].latitude);
                    marker = new BMap.Marker(new_point, {icon: myIcon, title: result.staffs[i].name});
                    marker.addEventListener("click", showStaffInfo.bind(this, result.staffs[i]));
                    map.addOverlay(marker);
                    setMarkerAnimation(marker);
                    let listItem = genListItem(result.staffs[i]);
                    $("#staff_info_body ul").append(listItem);
                }
                $("#staff_info_body li").click(function () {
                    initStaffTable($(this).attr("id"));
                })
            } else {
                alertWarning("无客服！");
            }
        }
    });

}

function showStaffInfo(staff) {
    initStaffTable(staff.sid.toString());
    $("#staff-modal").modal("show");
}

function dispatch() {
    confirmWarning("是否确认派工？", doDispatch);
}

function doDispatch() {
    confirmBoxHide();
    //获取客服id和请求id
    var urlList = window.document.location.href.toString().split("?");
    var sid = $("#staff_table>tbody>tr:nth-child(1)>th:nth-child(2)").text();
    var rid = urlList[1].split("=")[1];

    //向服务器发送派工数据，包括请求id和派工的客服id
    $.ajax({
        type: "post",
        url: "dispatch_request.jsp",
        contentType: "application/json; charset=utf-8",
        data: JSON.stringify({"request-type": "dispatch", "rid": rid, "sid": sid}),
        success: function (result) {
            if(parseInt(result.status) === 0) {
                confirmSuccess("派工成功,返回请求处理页面", function () {
                    window.location.href = "RequestManage";
                });
            } else {
                alertWarning("派工失败");
            }
        }
    })
}

function genListItem(staff) {
    let item;

    item = "<li class='list-group-item' id='" + staff.sid.toString() + "'> <a data-toggle='modal' href='#staff-modal'>客服: " + staff.name + "</a>";

    return item;
}

function initStaffTable(sid) {
    let i;
    for(i = 0; i < staffs.length; i++){
        if(sid === staffs[i].sid.toString()) break;
    }

    let staff = staffs[i];

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
    if($("#staff_avatar").length === 0) $("#staff-modal .modal-body").prepend("<img class=\"avatar\" id=\"staff_avatar\" alt=\"\">");
    $("#staff_avatar").attr("src", avatarURL);
}