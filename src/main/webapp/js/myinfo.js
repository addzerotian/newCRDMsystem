$(function () {
    flushInfo();

    $("#modify_birth").datepicker({
        format: "yyyy-mm-dd",
        language: "zh-CN",
        autoclose: true,
        clearBtn: true
    });

    $("#modify_info").on("hidden.bs.modal", function () {
        $("#modify_info form input[type=text]").val("");
        $("#modify_info form input[type=file]").val("");
        $("#modify_info form input[type=radio]").removeAttr("checked");
    });

    $("#modify_info").on("shown.bs.modal", function () {
        $("#modify_aid").val($("#content tbody>tr:nth-child(1)>th:nth-child(2)").text());
        $("#modify_aname").val($("#content tbody>tr:nth-child(2)>th:nth-child(2)").text());
        $("#modify_birth").val($("#content tbody>tr:nth-child(3)>th:nth-child(2)").text());
        $("#modify_telephone").val($("#content tbody>tr:nth-child(5)>th:nth-child(2)").text());
        $("#modify_email").val($("#content tbody>tr:nth-child(7)>th:nth-child(2)").text());

        const gender = $("#content tbody>tr:nth-child(4)>th:nth-child(2)").text();
        if(gender === "男") $("#modify_sex_male").prop("checked", true);
        else if(gender === "女") $("#modify_sex_female").prop("checked", true);
    });
})

function deleteAdmin() {
    confirmDanger("账号删除后无法恢复，是否确认删除账号？", function () {
        $.ajax({
            type: "post",
            url: "admin_requests.jsp",
            contentType: "application/json; charset=utf-8",
            data: JSON.stringify({"request-type": "deleteAdmin"}),
            success: function (result) {
                if(parseInt(result["status"].toString()) === 0) {
                    window.alert("账号删除成功!");
                    window.location.href = "index.jsp";
                } else {
                    alertWarning("刷新失败!");
                }
            }
        })
    })
}

function flushInfo() {
    $.ajax({
        type: "post",
        url: "admin_requests.jsp",
        contentType: "application/json; charset=utf-8",
        data: JSON.stringify({"request-type": "flushInfo"}),
        success: function (result) {
            if(parseInt(result["status"].toString()) === 0) {
                let gender, avatarURL;

                if(result["admin"][0]["gender"].toString() === "male") gender = "男";
                else if(result["admin"][0]["gender"].toString() === "female") gender = "女";
                else gender = "未知";
                if(result["admin"][0]["avatarURL"].toString() === "") avatarURL = "";
                else avatarURL = "img/userAvatar/" + result["admin"][0]["avatarURL"].toString();

                $("#content tbody>tr:nth-child(1)>th:nth-child(2)").text(result["admin"][0]["aid"].toString());
                $("#content tbody>tr:nth-child(2)>th:nth-child(2)").text(result["admin"][0]["name"].toString());
                $("#content tbody>tr:nth-child(3)>th:nth-child(2)").text(result["admin"][0]["birth"].toString());
                $("#content tbody>tr:nth-child(4)>th:nth-child(2)").text(gender);
                $("#content tbody>tr:nth-child(5)>th:nth-child(2)").text(result["admin"][0]["telephone"].toString());
                $("#content tbody>tr:nth-child(6)>th:nth-child(2)").text(result["admin"][0]["authority"].toString());
                $("#content tbody>tr:nth-child(7)>th:nth-child(2)").text(result["admin"][0]["email"].toString());
                $("#my_avatar").attr("src", avatarURL);

            } else {
                alertWarning("刷新失败!");
            }
        }
    })
}

function updateInfo() {
    //从表单获取数据
    var aid = $("#modify_aid").val();
    var aname = $("#modify_aname").val();
    var sex = $("#modify_sex input:checked").val();
    var telephone = $("#modify_telephone").val();
    var birth = $("#modify_birth").val();
    var email = $("#modify_email").val();
    var avatar = $("#modify_avatar")[0].files[0];
    var data = new FormData();

    //验证表单数据
    if(!sex) sex = "";
    if(!aname) aname = "";
    if(!birth) birth = "";
    if(!email) email = "";
    if(!telephone) telephone = "";
    if(!avatar) avatar = "";

    //将验证后的数据加入将要发送的FormData
    data.append("request-type", "updateInfo");
    data.append("img", avatar);
    data.append("request-data", JSON.stringify({
        "aid": aid,
        "aname": aname,
        "birth": birth,
        "email": email,
        "gender": sex,
        "telephone": telephone,
    }));

    $.ajax({
        type: "post",
        url: "admin_requests.jsp",
        contentType: false,
        processData: false,
        data: data,
        success: function (result) {
            if(parseInt(result["status"].toString()) === 0) {
                alertSuccess("修改信息成功！");
                //隐藏模态框
                $("#modify_info").modal("hide");
                flushInfo();
            } else {
                alertWarning("修改信息失败！");
            }
        }
    });
}