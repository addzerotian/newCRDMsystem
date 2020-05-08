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

    $("#requests-list").on("show.bs.modal", getRequestsList);
    $("#requests-list").on("hidden.bs.modal", function () {
        $("#requests-list li").remove();
    })

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
                    setMarkerAnimation(marker);
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

function getRequestsList() {
    $.ajax({
        type: "post",
        url: "requestList.jsp",
        contentType: "application/json; charset=utf-8",
        data: JSON.stringify({"request-type": "getRequests"}),
        success: function (result) {
            if (parseInt(result["status"].toString()) === 0) {
                for(let i = 0; i < result.request.length; i++) {
                    let listItem = genListItem(result.request[i]);
                    $("#ordered-list-requests").append(listItem);
                }
                $("#ordered-list-requests li>a").click(function () {
                    $("#info_table>tbody>tr:nth-child(1)>th:nth-child(2)").text($(this).next().attr("id"));
                    $("#info_table>tbody>tr:nth-child(2)>th:nth-child(2)").text($(this).next().find("tr:nth-child(1)").find("th:nth-child(2)").text());
                    $("#info_table>tbody>tr:nth-child(5)>th:nth-child(2)").text($(this).next().find("tr:nth-child(2)").find("th:nth-child(2)").text());
                    $("#info_table>tbody>tr:nth-child(3)>th:nth-child(2)").text($(this).next().find("tr:nth-child(3)").find("th:nth-child(2)").text());
                    $("#info_table>tbody>tr:nth-child(4)>th:nth-child(2)").text($(this).next().find("tr:nth-child(4)").find("th:nth-child(2)").text());
                })
            }
            else {
                alertWarning("地图刷新失败！");
            }
        }
    });
}

function genListItem(request) {
    let item;
    let collapseID = request.rid;
    let collapseItem;

    item = "<li class='list-group-item'> <a data-toggle='collapse' href='#" + collapseID + "' aria-expanded='false'" +
        " aria-controls='" + collapseID + "'>时间: " + request.startTime
        + "<span style='margin-left: 50%'>状态: " + request.status + "</span></a>";
    collapseItem = "<div id='" + collapseID + "' class='collapse'>" +
        "<table class='table-bordered table-condensed'><tbody>" + "<tr><th>客户</th><th>" + request.name +"</th></tr>" +
        "<tr><th>位置</th><th>" + request.location +"</th></tr>" +
        "<tr><th>时间</th><th>" + request.startTime +"</th></tr>" +
        "<tr><th>状态</th><th>" + request.status +"</th></tr>" +
        "</tbody></table>" +
        "<button class='btn btn-primary' onclick='hideList()' data-toggle='modal' data-target='#dispatch'>处理</button></div>" ;

    item = item + collapseItem;

    return item;
}

function hideList() {
    $("#requests-list").modal("hide");
}