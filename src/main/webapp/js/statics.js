$(function () {
    $("#requests-table").bootstrapTable({
        pagination: true,
        height: 550,
        pageSize:10,
        sortName: "Rindex",
        locale: "zh-CN",
        undefinedText: "待更新",
        paginationHAlign: "left",
        search: true,
        showSearchButton: true,
        showSearchClearButton: true
    });
    $("#dispatches-table").bootstrapTable({
        pagination: true,
        pageSize:10,
        sortName: "Dindex",
        locale: "zh-CN",
        undefinedText: "待更新",
        height: 550,
        search: true,
        showSearchButton: true,
        showSearchClearButton: true,
        paginationHAlign: "left"
    });
    $("#staffs-table").bootstrapTable({
        pagination: true,
        pageSize:10,
        sortName: "Sindex",
        locale: "zh-CN",
        undefinedText: "未知",
        height: 550,
        search: true,
        showSearchButton: true,
        showSearchClearButton: true,
        paginationHAlign: "left"
    });
    flashRequests();
    flashDispatches();
    flashStaffs();
})

function flashRequests() {
    $.ajax({
        type: "post",
        url: "requestList.jsp",
        contentType: "application/json; charset=utf-8",
        data: JSON.stringify({"request-type": "flushMap", "trigger": 2}),
        success: function (result) {
            //清空列表里的过时数据
            $("#requests-table").bootstrapTable("removeAll");
            if (parseInt(result["status"].toString()) === 0) {
                for(let i = 0; i < result.request.length; i++) {
                    let row = {Rindex: i+1, Rname: result.request[i].name, Rtime: result.request[i].startTime,
                        Rlocation: result.request[i].location, Rstatus: requestStatusCode[result.request[i].status]};
                    let names = Object.keys(row);
                    for(let j in names) {
                        if(row[names[j]] === "") row[names[j]] = "待更新";
                    }
                    $("#requests-table").bootstrapTable('append', row);
                }
            } else if(parseInt(result["status"].toString()) === 1) {}
            else {
                alertWarning("刷新失败");
            }

        }
    })
}

function flashDispatches() {
    $.ajax({
        type: "post",
        url: "dispatch_request.jsp",
        contentType: "application/json; charset=utf-8",
        data: JSON.stringify({"request-type": "getDispatches"}),
        success: function (result) {
            //清空列表里的过时数据
            $("#dispatches-table").bootstrapTable("removeAll");
            if (parseInt(result["status"].toString()) === 0) {
                for(let i = 0; i < result.dispatches.length; i++) {
                    let row = {Dindex: i+1, Dcname: result.dispatches[i].cname, Dsname: result.dispatches[i].sname,
                        DstartTime: result.dispatches[i].startTime, DdispatchTime: result.dispatches[i].dispatchTime,
                        DendTime: result.dispatches[i].endTime, DwaitMinutes: result.dispatches[i].waitMinutes, Dstatus: result.dispatches[i].status,
                        Dstar: result.dispatches[i].star, Dcomment: result.dispatches[i].comment};
                    let names = Object.keys(row);
                    for(let j in names) {
                        if(row[names[j]] === "") row[names[j]] = "待更新";
                    }
                    $("#dispatches-table").bootstrapTable('append', row);
                }
            }
            else {
                alertWarning("刷新失败");
            }

        }
    })
}

function flashStaffs() {
    $.ajax({
        type: "post",
        url: "staff_request.jsp",
        contentType: "application/json; charset=utf-8",
        data: JSON.stringify({"request-type": "flushStaff"}),
        success: function (result) {
            //清空列表里的过时数据
            $("#staffs-table").bootstrapTable("removeAll");
            if (parseInt(result["status"].toString()) === 0) {
                for(let i = 0; i < result.staffs.length; i++) {
                    let row = {Sindex: i+1, Sid: result.staffs[i].sid, Sname: result.staffs[i].name,
                        Sbirthday: result.staffs[i].birth, Sgender: result.staffs[i].gender,
                        Stelephone: result.staffs[i].telephone, Semail: result.staffs[i].email,
                        SdutyTotalTimes: result.staffs[i].dutyTotalTimes, SdutyTotalHours: result.staffs[i].dutyTotalHours,
                        SgradeTotal: result.staffs[i].gradeTotal, SdutyMonthTimes: result.staffs[i].dutyMonthTimes,
                        SdutyMonthHours: result.staffs[i].dutyMonthHours, SgradeMonth: result.staffs[i].gradeMonth,
                        SabsenceTotal: result.staffs[i].absenceTotal, SabsenceMonth: result.staffs[i].absenceMonth,
                        Sstatus: result.staffs[i].status};
                    let names = Object.keys(row);
                    for(let j in names) {
                        if(row[names[j]] === "") row[names[j]] = "未知";
                    }
                    $("#staffs-table").bootstrapTable('append', row);
                }
            }
            else {
                alertWarning("刷新失败");
            }

        }
    })
}