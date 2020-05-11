const gender = ["未知", "男", "女"];

$(function () {
    $("#customers-table").bootstrapTable({
        pagination: true,
        height: 550,
        pageSize:10,
        sortName: "Cindex",
        locale: "zh-CN",
        undefinedText: "未知",
        paginationHAlign: "left",
        search: true,
        showSearchButton: true,
        showSearchClearButton: true
    });

    flashCustomers();
});

function flashCustomers() {
    $.ajax({
        type: "post",
        url: "wx.jsp",
        contentType: "application/json; charset=utf-8",
        data: JSON.stringify({"request-type": "flushCustomers"}),
        success: function (result) {
            //清空列表里的过时数据
            $("#customers-table").bootstrapTable("removeAll");
            if (parseInt(result["status"].toString()) === 0) {
                for(let i = 0; i < result.customers.length; i++) {
                    let row = {Cindex: i+1, Cname: result.customers[i].name, Cid: result.customers[i].cid,
                        Cgender: gender[parseInt(result.customers[i].gender)], Cbirthday: result.customers[i].birth,
                        Ctelephone: result.customers[i].telephone, Cemail: result.customers[i].email,
                        CtotalRequestTimes: result.customers[i].totalRequestTimes,
                        CtotalDispatchTimes: result.customers[i].totalDispatchTimes};
                    let names = Object.keys(row);
                    for(let j in names) {
                        if(row[names[j]] === "") row[names[j]] = "未知";
                    }
                    $("#customers-table").bootstrapTable('append', row);
                }
            }
            else {
                alertWarning("刷新失败");
            }

        }
    })
}