//    关闭添加时模态框
function closeModal(){
    $('.add_modal').removeClass('tran_scale');
    $('.shadow').hide();
}
//    打开添加的模态框
function showModal(){
    $('.add_modal').addClass('tran_scale');
    $('.shadow').show();
}
//加密函数
function encrypt(random,word){
    var key = CryptoJS.enc.Utf8.parse(random);
    var srcs = CryptoJS.enc.Utf8.parse(word);
    var encrypted = CryptoJS.AES.encrypt(srcs, key, {mode:CryptoJS.mode.ECB,padding: CryptoJS.pad.Pkcs7});
    return encrypted.toString();
}

function login(){
    var name=$('.name').val();
    var password=$('.password').val();
    if(name == ''){
        $('.name').css({
            'border-bottom':'1px solid red',
        })
        $('.caveat').text("请输入账户用户名！")
    }else if(password ==''){
        $('.password').css({
            'border-bottom':'1px solid red',
        })
        $('.caveat').text("请输入密码！")
    } else if(name != '' && password !=''){
        $.ajax({
            url: "/administrator/random",
            type : 'get',
            dataType: "JSON",
            success: function (data) {
                if(data.code==1){
                    var random = data.msg;
                    $.ajax({
                        url: "/administrator/login",
                        type : 'get',
                        data: {
                            'userName':name,
                            'password':encrypt(random,encrypt(random,password)+random)
                        },
                        dataType: "JSON",
                        success: function (data) {
                            if(data.code==1){
                                window.location.href='/admin/underwriting/select';
                            }else{
                                $('.caveat').text(data.msg);
                            }
                        }
                    })
                }else{
                    $('.caveat').text(data.msg);
                }
            }
        })

    }
}
$('.login').on('click',function () {
    login();
});
$(document).keyup(function(event){
    if(event.keyCode ==13){
        login();
    }
});

var url = "wss://"+ window.location.host +"/webSocket";
var websocket = null;
if('WebSocket' in window) {
    websocket = new WebSocket(url);
} else {
    console.log("该浏览器不支持消息通知，建议更换谷歌浏览器");
}
websocket.onopen = function (event) {
    // console.log('建立连接');
};

websocket.onclose = function (event) {
    // console.log('连接关闭');
};

websocket.onmessage = function (event) {
    alert('收到消息：' + event.data);
};

websocket.onerror = function () {
    console.log('websocket通信发生错误！');
};

window.onbeforeunload = function () {
    websocket.close();
};
