var map;
var requests;

$(function () {
    map = new BMap.Map("map_canvas");
    //地图中心设置为重庆大学
    let point = new BMap.Point(106.475, 29.571);
    map.centerAndZoom(point, 15);
    map.enableScrollWheelZoom(true);

    setInterval(flashMap, 1000);

    $("#reject").on("hidden.bs.modal", function () {
        $("#reject_reason").val("");
    });

    $("#ordered-list-requests").bootstrapTable({
        pagination: true,
        pageSize:10,
        sortName: "time",
        height: 650,
        paginationHAlign: "left",
        search: true,
        showSearchButton: true,
        showSearchClearButton: true,
        onClickCell: function (field, value) {
            if(field === "action")
                initRequestInfo($(value).attr("id").substring(3));
        }
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
                requests = result.request;
                //清空地图和列表里的过时数据
                map.clearOverlays();
                $("#ordered-list-requests").bootstrapTable("removeAll");

                let new_point;
                let marker;
                for (let i = 0; i < result["requestNumber"]; i++) {
                    new_point = new BMap.Point(result["request"][i]["longitude"], result["request"][i]["latitude"]);
                    marker = new BMap.Marker(new_point, {title: result.request[i].name});
                    marker.addEventListener("click", showInfo.bind(this, result["request"][i]));
                    map.addOverlay(marker);
                    setMarkerAnimation(marker);
                    let listItem = genListItem(result.request[i]);
                    $("#ordered-list-requests").bootstrapTable('insertRow', {index: i+1, row: listItem});
                }
                map.centerAndZoom(new_point, 15);
            } else if (parseInt(result["status"].toString()) === 1) {
                requests = [];
                map.clearOverlays();
                $("#ordered-list-requests tbody").bootstrapTable("removeAll");
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
    initRequestInfo(request.rid.toString());
    $("#dispatch").modal("show");
}

function goToDispatch() {
    let rid = $("#request_info table").attr("id").substring(3);
    window.location.href = "Dispatch?rid=" + rid;
}

function rejectRequest() {
    let rid = $("#request_info table").attr("id").substring(3);
    let rejectReason = $("#reject_reason").val();

    $.ajax({
        type: "post",
        url: "requestList.jsp",
        contentType: "application/json; charset=utf-8",
        data: JSON.stringify({"request-type": "reject", "rid": rid, "reason": rejectReason}),
        success: function (result) {
            $("#reject").modal("hide");
            if(parseInt(result["status"].toString()) === 0) {
                alertSuccess("请求成功");
            } else {
                alertWarning("请求失败");
            }
        }
    });
}

function genListItem(request) {
    let item;
    let button = "<button class='btn btn-group' data-toggle='modal' data-target='#dispatch' id='bt-" + request.rid.toString() +
        "'>查看</button>";
    item = {name: request.name, time: request.startTime, status: requestStatusCode[request.status.toString()], action: button};

    return item;
}

function initRequestInfo(rid) {
    let i;
    for(i = 0; i < requests.length; i++){
        if(rid === requests[i].rid.toString()) break;
    }

    let request = requests[i];

    $("#request_info table").attr("id", "tb-" + rid);
    $("#request_info tbody>tr:nth-child(1)>th:nth-child(2)").text(request["name"]);
    $("#request_info tbody>tr:nth-child(2)>th:nth-child(2)").text(request["startTime"]);
    $("#request_info tbody>tr:nth-child(3)>th:nth-child(2)").text(requestStatusCode[request["status"].toString()]);
    $("#request_info tbody>tr:nth-child(4)>th:nth-child(2)").text(request["location"]);
}