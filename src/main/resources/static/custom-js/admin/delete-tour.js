document.addEventListener('DOMContentLoaded', function () {
    const deleteTourButtons = document.querySelectorAll('.delete-tour');

    deleteTourButtons.forEach(function (item){
        item.addEventListener("click",function (event){
            event.preventDefault();

            if(confirm("Вы уверены что хотите удалить тур?")){
                let formData = new FormData();
                let tourId = item.getAttribute("tourId");
                formData.append("tourId", tourId);

                $.ajax({
                    url: "/admin/tours/delete-tour",
                    type: "POST",
                    data: formData,
                    processData: false,
                    contentType: false,
                    success: function () {
                        window.location.reload();
                    },
                    error: function (xhr) {
                        document.getElementById("spinner").hidden = true;
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
