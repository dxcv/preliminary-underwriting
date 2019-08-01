//删除代理人
$('.deleteAgent').on('click',function () {
    var thisId = $(this).data('id');
    if(confirm('此操作危险！你确定要删除吗？')) {
        $.ajax({
            url: "/admin/agent?agentId=" + thisId,
            type: 'DELETE',
            dataType: "JSON",
            contentType: "application/json",
            success: function (data) {
                alert(data.msg);
                window.location.href = '/admin/agent/select';
            }
        })
    }
});

//提交搜索
$('.seach').on('click',function () {
    let id=$(this).prev().val();
    console.log(id);

    if(id!=''&& id!=null){
        window.location.href='/admin/agent/select?nickname='+id;
    }
});
//获取地址栏中是否有keyword
function keyword() {
    if(getParamFromUrl("keyword") !=null){
        $('.seach').prev().val(getParamFromUrl("keyword"));
        $('.box_tit_sea').text("代理人管理--搜索结果");
    }
}
keyword();
//封装方法  截取？后的值       根据参数部分和参数名来获取参数值
function getParamString(paraPart,name) {
    if(paraPart == null) return null;
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    var r = paraPart.substr(1).match(reg);
    if (r != null) return decodeURI(r[2]); return null;
}
function getParamFromUrl(name){
    var r = getParamString(window.location.search,name)
    return r
}
$(".praise").attr("disabled",true).css("pointer-events","none");