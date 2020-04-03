function alertLoginNoUser() {
    window.alert("用户账号名/密码错误！")
}

function logout() {
    window.alert("账号注销成功，请重新登录")
}

function test() {
    console.log("anything but test");
    var map = new BMapGL.Map("map_canvas");
    var point = new BMapGL.Point(116.23, 39.54);
    map.centerAndZoom(point, 15);
    map.enableScrollWheelZoom(true);
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
        contentType: "application/json",
        data: JSON.stringify({"request-type": "addStaff", "sid": sid, "sname": sname}),
        success: function (result) {
            if(result["status"] == 0) {
                window.alert("添加成功！");
            } else {
                window.alert("添加失败！");
            }
        }
    })
}

function searchStaff() {
    var sid = $("#search_sid").val();

    $.ajax({
        type: "post",
        url: "staff_request.jsp",
        contentType: "application/json",
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
            } else {
                window.alert("无此客服！");
            }
        }
    })
}