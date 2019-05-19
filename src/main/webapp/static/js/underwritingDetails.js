
var status;
//  是否通过信息
$('.result').on('click',function () {
    status=$(this).data('status');
    showModal();
})
//确认反馈信息
$('.sub_yes').click(function () {
    $.ajax({
        url: "/admin/template/send",
        type : 'GET',
        data: {
            'underwritingId':$('.underwritingId').val(),
            'auditResult':status,
            'note':$('.back-info').val(),
        },
        dataType: "JSON",
        success: function (data) {
            console.log(data);
            closeModal();
        }
    })
})
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