document.addEventListener('DOMContentLoaded', function () {
    const readButtons = document.querySelectorAll(".notification-viewed");

    readButtons.forEach(function (item){
        item.addEventListener("click",function (e){
            let notificationId = item.getAttribute("notificationId");

            let formData = new FormData();
            formData.append("notificationId", notificationId);
            $.ajax({
                url: "/notifications/viewed",
                type: "POST",
                data: formData,
                processData: false,
                contentType: false,
                success: function (){
                    window.location.reload();
                },
                error: function (xhr){
                    try {
                        const errorData = JSON.parse(xhr.responseText);
                        if (errorData.hasOwnProperty("error")) {
                            alert(errorData.error);
                        } else {
                            alert('Что то пошло не так');
                        }
                    } catch (e) {
                        alert('Что то пошло не так');
                    }
                }
            })
        })
    })
});
