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

$('.login').on('click',function () {
    var name=$('.name').val();
    var password=$('.password').val();
    if(name != '' && password !=''){
        $.ajax({
            url: "/administrator/login",
            type : 'get',
            data: {
                'userName':name,
                'password':password
            },
            dataType: "JSON",
            success: function (data) {
                console.log(data);
                window.location.href='/admin/underwriting/select';
            }
        })
    }
})