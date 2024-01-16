document.addEventListener('DOMContentLoaded', function () {
    const deleteExerciseButtons = document.querySelectorAll('.delete-exercise');

    deleteExerciseButtons.forEach(function (item){
        item.addEventListener("click",function (event){
            event.preventDefault();

            if(confirm("Вы уверены что хотите удалить задания?")){
                let formData = new FormData();
                let exerciseId = item.getAttribute("exerciseId");
                formData.append("exerciseId", exerciseId);

                $.ajax({
                    url: "/admin/exercises/delete-exercise",
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
