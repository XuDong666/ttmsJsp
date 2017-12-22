/**
 * Created by xudong on 17-12-19.
 */
$(document).ready(function () {
    $('#seat').click(function () {

        $('#studiomenu').slideToggle("slow");
    })
    $('#employeemanager').click(function () {
        $(".userlist").css('display','block')
    })
})