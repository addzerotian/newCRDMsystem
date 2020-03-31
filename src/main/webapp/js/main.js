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