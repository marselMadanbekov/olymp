document.addEventListener('DOMContentLoaded', function () {
    const deleteLevelButtons = document.querySelectorAll('.delete-level');

    deleteLevelButtons.forEach(function (item){
        item.addEventListener("click",function (event){
            event.preventDefault();

            if(confirm("Вы уверены что хотите удалить уровень?")){
                let formData = new FormData();
                let levelId = item.getAttribute("levelId");
                formData.append("levelId", levelId);

                $.ajax({
                    url: "/admin/levels/delete-level",
                    type: "POST",
                    data: formData,
                    processData: false,
                    contentType: false,
                    success: function () {
                        window.location.reload();
                    },
                    error: function (xhr) {
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
                });
            }
        })
    })
});
