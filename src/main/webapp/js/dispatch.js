$(function () {
    let map = new BMap.Map("map_canvas");
    //地图中心设置为重庆大学
    var point = new BMap.Point(106.475, 29.571);
    map.centerAndZoom(point, 15);
    map.enableScrollWheelZoom(true);
})

function getStaffsAroundCustomer(cusLng, cusLat) {
    console.log(cusLng + ", " + cusLat);
    var point = new BMap.Point(cusLng, cusLat);
    var marker = new BMap.Marker(point);

    let map = new BMap.Map("map_canvas");
    map.addOverlay(marker);
    map.centerAndZoom(point, 15);
    map.enableScrollWheelZoom(true);

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
                var map = new BMap.Map("map_canvas");
                var new_point = new BMap.Point(result.longitude, result.latitude);
                var myIcon = new BMap.Icon("img/icon/marker_yellow.png", new BMap.Size(23, 25));
                var marker = new BMap.Marker(new_point);
                map.addOverlay(marker);

                for(let i = 0; i < parseInt(result.staffNum); i++) {
                    new_point = new BMap.Point(result.staffs[i].longitude, result.staffs[i].latitude);
                    marker = new BMap.Marker(new_point, {icon: myIcon});
                    marker.addEventListener("click", showStaffInfo.bind(this, result.staffs[i]));
                    map.addOverlay(marker);
                }
                map.centerAndZoom(new_point, 15);
                map.enableScrollWheelZoom(true);
            } else {
                alertWarning("无客服！");
            }
        }
    });

}

function showStaffInfo(staff) {
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
        var button1 = "<button class=\"btn btn-primary\">派工</button>";
        $("#staff_info_footer").append(button1);
        $("#staff_info_footer button").attr("style", "margin-left: 70%");
    }
}