//打包文件
$('.packaging').click(function () {
    const zipId = $(this).next().text();
    const urlPath = window.location.protocol+"//"+window.location.host;
    alert("正在打包中，期间不影响您进行其他操作");
    $.ajax({
        url: "/admin/zip/getZip",
        type : 'POST',
        data: {
            'zipId':zipId,
            'url':urlPath
        },
        dataType: "JSON",
        success: function (data) {
            alert("打包"+ data.msg);
            window.location.href='/admin/zip/selectMonth';
        }
    })
});
//重新打包文件
$('.packagingTwo').click(function () {
    const zipId = $(this).next().next().text();
    const urlPath = window.location.protocol+"//"+window.location.host;
    alert("正在打包中，期间不影响您进行其他操作");
    $.ajax({
        url: "/admin/zip/getZip",
        type : 'POST',
        data: {
            'zipId':zipId,
            'url':urlPath
        },
        dataType: "JSON",
        success: function (data) {
            alert("打包"+ data.msg);
            window.location.href='/admin/zip/selectMonth';
        }
    })
});
// 删除文件
$('.deleteMonth').click(function () {
    const zipId = $(this).next().text();
    if(confirm('此操作危险！你确定要删除吗？')) {
        $.ajax({
            url: "/admin/zip?zipId=" + zipId,
            type: 'DELETE',
            dataType: "JSON",
            contentType: "application/json",
            success: function (data) {
                window.location.href = '/admin/zip/selectMonth';
            }
        })
    }
});
$(".praise").attr("disabled",true).css("pointer-events","none");