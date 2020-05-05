var map;

$(function () {
    map = new BMap.Map("map_canvas");
    //地图中心设置为重庆大学
    let point = new BMap.Point(106.475, 29.571);
    map.centerAndZoom(point, 15);
    map.enableScrollWheelZoom(true);

    setInterval(flashMap, 1000);

    $("#dispatch").on("shown.bs.modal", function () {
       $("#request-table tbody tr:nth-child(1) th:nth-child(2)").text($("#info_table>tbody>tr:nth-child(1)>th:nth-child(2)").text());
       $("#request-table tbody tr:nth-child(2) th:nth-child(2)").text($("#info_table>tbody>tr:nth-child(2)>th:nth-child(2)").text());
       $("#request-table tbody tr:nth-child(3) th:nth-child(2)").text($("#info_table>tbody>tr:nth-child(5)>th:nth-child(2)").text());
       $("#request-table tbody tr:nth-child(4) th:nth-child(2)").text($("#info_table>tbody>tr:nth-child(3)>th:nth-child(2)").text());

    });

    flashMap(1);
})

function flashMap(trigger) {
    if(!trigger) trigger = 0;
    $.ajax({
        type: "post",
        url: "requestList.jsp",
        contentType: "application/json; charset=utf-8",
        data: JSON.stringify({"request-type": "flushMap", "trigger": trigger}),
        success: function (result) {
            if (parseInt(result["status"].toString()) === 0) {
                map.clearOverlays();
                let new_point;
                let marker;
                for (var i = 0; i < result["requestNumber"]; i++) {
                    new_point = new BMap.Point(result["request"][i]["longitude"], result["request"][i]["latitude"]);
                    marker = new BMap.Marker(new_point, {title: result.request[i].name});
                    marker.addEventListener("click", showInfo.bind(this, result["request"][i]));
                    map.addOverlay(marker);
                }
                map.centerAndZoom(new_point, 15);
            } else if (parseInt(result["status"].toString()) === 1) {
                map.clearOverlays();
                //地图中心设置为重庆大学
                let point = new BMap.Point(106.475, 29.571);
                map.centerAndZoom(point, 15);
            } else if (parseInt(result["status"].toString()) === -1) {}
            else {
                alertWarning("地图刷新失败！");
            }
        }
    })
}

function showInfo(request) {
    $("#info_table>tbody>tr:nth-child(1)>th:nth-child(2)").text(request["rid"]);
    $("#info_table>tbody>tr:nth-child(2)>th:nth-child(2)").text(request["name"]);
    $("#info_table>tbody>tr:nth-child(3)>th:nth-child(2)").text(request["startTime"]);
    $("#info_table>tbody>tr:nth-child(4)>th:nth-child(2)").text(request["status"]);
    $("#info_table>tbody>tr:nth-child(5)>th:nth-child(2)").text(request["location"]);
    if($("#request_info>button").length === 0) {
        var button1 = "<button class=\"btn btn-primary\" data-toggle='modal' data-target='#dispatch'>确认</button>";
        $("#request_info").append(button1);
    }
}

function goToDispatch() {
    var rid = $("#request-table tbody tr:nth-child(1) th:nth-child(2)").text();
    window.location.href = "Dispatch?rid=" + rid;
}

function rejectRequest() {
    var rid = $("#request-table tbody tr:nth-child(1) th:nth-child(2)").text();
    var rejectReason = $("#reject_reason").val();

    $.ajax({
        type: "post",
        url: "requestList.jsp",
        contentType: "application/json; charset=utf-8",
        data: JSON.stringify({"request-type": "reject", "rid": rid, "reason": rejectReason}),
        success: function (result) {
            $("#reject").modal("hide");
            if(parseInt(result["status"].toString()) === 0) {
                alertSuccess("请求成功");
                $("#info_table>tbody>tr:nth-child(1)>th:nth-child(2)").text("");
                $("#info_table>tbody>tr:nth-child(2)>th:nth-child(2)").text("");
                $("#info_table>tbody>tr:nth-child(3)>th:nth-child(2)").text("");
                $("#info_table>tbody>tr:nth-child(4)>th:nth-child(2)").text("");
                $("#info_table>tbody>tr:nth-child(5)>th:nth-child(2)").text("");
            } else {
                alertWarning("请求失败");
            }
        }
    });
}