//添加管理
$('.adduser').click(function () {
    const username = $('.username').val();
    const password = $('.password').val();
    const name = $('.name').val();
    const phone = $('.phone').val();
    if(username == ''){
        alert('请填写用户名！');
    }else if(password == ''){
        alert('请填写密码！');
    }else{
        $.ajax({
            url: "/admin/user",
            type : 'post',
            data: {
                'role':'100',
                'userName':username,
                'password':password,
                'name':name,
                'phone':phone
            },
            dataType: "JSON",
            success: function (data) {
                closeModal();
                alert('添加成功');
                window.location.href='/admin/user/select';
            }
        })
    }
});
// 点击关闭
$('.oper_close').click(function(){
    closeModal();
});
$('.sub_no').click(function(){
    closeModal();
});
$('.shadow').click(function(){
    closeModal();
});
//  添加
$('.box_add').click(function(){
    showModal();
});
//删除管理员
$('.op_refuse').on('click',function () {
    let id=$(this).data('id');
    if(confirm('此操作危险！你确定要删除吗？')){
        $.ajax({
            url: "/admin/user?userId="+id,
            type : 'DELETE',
            dataType: "JSON",
            success: function (data) {
                if(data.code==1){
                    // alert('删除成功');
                    window.location.href='/admin/user/select';
                }else{
                    alert("删除失败");
                }

            }
        })
    }
})

//修改管理员信息
$('.result-yes').click(function () {
    const userId = $('.userId').val();
    const role = $('.role').val();
    const username = $('.username').val();
    const password = $('.password').val();
    const name = $('.name').val();
    const phone = $('.phone').val();
    if(username == ''){
        $('.errormsg').text("请输入完整用户名");
    }else if(password == ''){
        $('.errormsg').text("请输入完整密码");
    } else{
        $.ajax({
            url: "/admin/user",
            type : 'PUT',
            data: {
                'userId':userId,
                'role':role,
                'userName':username,
                'password':password,
                'name':name,
                'phone':phone
            },
            dataType: "JSON",
            success: function (data) {
                if(data.code==1){
                    alert(data.msg);
                    window.location.href='/admin/user/select';
                }

            }
        })
    }

});
$('.result-no').click(function () {
    window.location.href='/admin/user/select';
});
//是否启用
$('.switch').on('click',function () {
    let id=$(this).data("id");
    $.ajax({
        url: "/admin/user/update",
        type : 'POST',
        data:{
            'userId':id,
        },
        dataType: "JSON",
        success: function (data) {
            if(data.code==1){
                location.reload();
            }else{
                alert("修改失败")
            }
        }
    })
})