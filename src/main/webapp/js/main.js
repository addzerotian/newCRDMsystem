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

