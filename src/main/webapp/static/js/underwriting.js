// $('.op_agree').on('click',function () {
//     let id=$(this).data('id');
//     console.log(id);
//     $.ajax({
//         url: "/admin/underwriting/selectById",
//         type : 'GET',
//         data: {
//             'underwritingId':id
//         },
//         dataType: "JSON",
//         success: function (data) {
//             console.log(data);
//             window.location.href='/underwriting/underwritingDetails';
//         }
//     })
// })