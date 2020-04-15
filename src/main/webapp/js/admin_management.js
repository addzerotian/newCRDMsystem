$(function () {
    getSystemAdminsInfo();
    $("#add_birth").datepicker({
        format: "yyyy-mm-dd",
        language: "zh-CN",
        autoclose: true,
        clearBtn: true
    });
    $("#modify_birth").datepicker({
        format: "yyyy-mm-dd",
        language: "zh-CN",
        autoclose: true,
        clearBtn: true
    });

    $("#add_admin").on("hidden.bs.modal", function () {
        $("#add_admin form input[type=text]").val("");
        $("#add_admin form input[type=file]").val("");
        $("#add_admin form input[type=radio]").removeAttr("checked");
    });

    $("#search_admin").on("hidden.bs.modal", function () {
        $("#search_admin form input[type=text]").val("");
    });

    $("#modify_admin").on("hidden.bs.modal", function () {
        $("#modify_admin form input[type=text]").val("");
        $("#modify_admin form input[type=file]").val("");
        $("#modify_admin form input[type=radio]").removeAttr("checked");
    });

    $("#modify_admin").on("shown.bs.modal", function () {
        $("#modify_aid").val($("#admin_table>tbody>tr:nth-child(1)>th:nth-child(2)").text());
        $("#modify_aname").val($("#admin_table>tbody>tr:nth-child(2)>th:nth-child(2)").text());
        $("#modify_birth").val($("#admin_table>tbody>tr:nth-child(3)>th:nth-child(2)").text());
        $("#modify_telephone").val($("#admin_table>tbody>tr:nth-child(5)>th:nth-child(2)").text());
        $("#modify_authority").val($("#admin_table>tbody>tr:nth-child(6)>th:nth-child(2)").text());
        $("#modify_email").val($("#admin_table>tbody>tr:nth-child(7)>th:nth-child(2)").text());

        const gender = $("#admin_table>tbody>tr:nth-child(4)>th:nth-child(2)").text();
        if(gender === "男") $("#modify_sex_male").prop("checked", true);
        else if(gender === "女") $("#modify_sex_female").prop("checked", true);
    });
});

function getSystemAdminsInfo() {
    $.ajax({
        type: "post",
        url: "admin_requests.jsp",
        contentType: "application/json; charset=utf-8",
        data: JSON.stringify({"request-type": "getInfo"}),
        success: function (result) {
            if(parseInt(result["status"].toString()) === 0) {
                $("#content tbody>tr:nth-child(1)>th:nth-child(2)").text(result["total"]);
                $("#content tbody>tr:nth-child(2)>th:nth-child(2)").text(result["active"]);
            } else {
                alertWarning("查询信息失败！");
            }
        }
    });
}

function searchAdmin(adminAuth) {
    var aid = $("#search_aid").val();
    if(aid.length === 0) {
        aid = $("#admin_table>tbody>tr:nth-child(1)>th:nth-child(2)").text();
    }

    $.ajax({
        type: "post",
        url: "admin_requests.jsp",
        contentType: "application/json; charset=utf-8",
        data: JSON.stringify({"request-type": "searchAdmin", "aid": aid, "requestAdminAuth": String(adminAuth)}),
        success: function (result) {
            if(parseInt(result["status"].toString()) === 0) {
                $("#search_admin").modal("hide");

                let gender, avatarURL;

                if(result["admin"][0]["gender"].toString() === "male") gender = "男";
                else if(result["admin"][0]["gender"].toString() === "female") gender = "女";
                else gender = "未知";
                if(result["admin"][0]["avatarURL"].toString() === "") avatarURL = "";
                else avatarURL = "img/userAvatar/" + result["admin"][0]["avatarURL"].toString();

                $("#admin_table>tbody>tr:nth-child(1)>th:nth-child(2)").text(result["admin"][0]["aid"].toString());
                $("#admin_table>tbody>tr:nth-child(2)>th:nth-child(2)").text(result["admin"][0]["name"].toString());
                $("#admin_table>tbody>tr:nth-child(3)>th:nth-child(2)").text(result["admin"][0]["birth"].toString());
                $("#admin_table>tbody>tr:nth-child(4)>th:nth-child(2)").text(gender);
                $("#admin_table>tbody>tr:nth-child(5)>th:nth-child(2)").text(result["admin"][0]["telephone"].toString());
                $("#admin_table>tbody>tr:nth-child(6)>th:nth-child(2)").text(result["admin"][0]["authority"].toString());
                $("#admin_table>tbody>tr:nth-child(7)>th:nth-child(2)").text(result["admin"][0]["email"].toString());
                $("#admin_avatar").attr("src", avatarURL);
                modifiable(parseInt(result["requestAdminAuth"]), parseInt(result["admin"][0]["authority"]));
                $("#info_admin").modal("show");
            } else {
                alertWarning("查询失败！");
            }
        }
    });
}

function addAdmin(requestAdminAuth) {
    //从表单获取数据
    var aid = $("#add_aid").val();
    var aname = $("#add_aname").val();
    var authority = $("#add_authority").val();
    var birth = $("#add_birth").val();
    var email = $("#add_email").val();
    var avatar = $("#add_avatar")[0].files[0];
    var gender = $("#add_sex input:checked").val();
    var telephone = $("#add_telephone").val();
    var data = new FormData();

    //验证表单数据
    if(!gender) gender = "";
    if(!aname) aname = "";
    if(!birth) birth = "";
    if(!email) email = "";
    if(!telephone) telephone = "";
    if(!avatar) avatar = "";

    //将验证后的数据加入将要发送的FormData
    data.append("request-type", "addAdmin");
    data.append("img", avatar);
    data.append("request-data", JSON.stringify({
        "aid": aid,
        "aname": aname,
        "authority": authority,
        "birth": birth,
        "email": email,
        "gender": gender,
        "requestAdminAuth": String(requestAdminAuth),
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
                alertSuccess("添加成功！");
                //隐藏模态框
                $("#add_admin").modal("hide");
            } else {
                alertWarning("添加失败！");
            }
        }
    });
}

function modifyAdmin(requestAdminAuth) {
    //从表单获取数据
    var aid = $("#modify_aid").val();
    var aname = $("#modify_aname").val();
    var sex = $("#modify_sex input:checked").val();
    var telephone = $("#modify_telephone").val();
    var authority = $("#modify_authority").val();
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
    data.append("request-type", "modifyAdmin");
    data.append("img", avatar);
    data.append("request-data", JSON.stringify({
        "aid": aid,
        "aname": aname,
        "authority": authority,
        "birth": birth,
        "email": email,
        "gender": sex,
        "requestAdminAuth": String(requestAdminAuth),
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
                $("#modify_admin").modal("hide");
                searchAdmin();
            } else {
                alertWarning("修改信息失败！");
            }
        }
    });
}

function submitByAdmin(action, adminAuth) {
    const functionName = action.prototype.constructor.name.toString();
    let aid_input;

    if(functionName === "addAdmin") aid_input = $("#add_aid");
    else if(functionName === "searchAdmin") aid_input = $("#search_aid");
    else if(functionName === "modifyAdmin") aid_input = $("#modify_aid");

    if(aid_input.val().toString() === "") {
        alertDanger("账号不能为空!")
    } else {
        action(adminAuth);
    }
}
