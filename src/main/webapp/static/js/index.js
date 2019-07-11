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
            url: "/administrator/login",
            type : 'get',
            data: {
                'userName':name,
                'password':password
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
// $(function(){
//     document.onkeydown = function(e){
//         var ev = document.all ? window.event : e;
//         if(ev.keyCode==13) {
//             login();
//         }
//     }
// });