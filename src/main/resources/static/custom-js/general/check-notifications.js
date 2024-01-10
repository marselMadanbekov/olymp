document.addEventListener('DOMContentLoaded', function () {
    const notificationHavingBadge = document.getElementById('notificationHaving');


    function checkNotifications(){
        $.ajax({
            url: "/notifications/having-non-read",
            type: "GET",
            success: function (response){
                console.log(response.isHave);
                if(response.isHave === true || response.isHave === 1){
                    notificationHavingBadge.hidden = false;
                }
            },
            error: function (){
            }
        })
    }
    checkNotifications();
});
