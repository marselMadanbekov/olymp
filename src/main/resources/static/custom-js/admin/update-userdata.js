document.addEventListener('DOMContentLoaded', function () {
    const updateUserdataForm = document.getElementById('update-user-data-form');

    updateUserdataForm.addEventListener('submit', function (e){
        e.preventDefault();
        document.getElementById("create-button").disabled=true;
        document.getElementById("spinner").hidden=false;
        if(confirm("Вы уверены что хотите изменить данные пользователя?")) {
            let formData = new FormData(this);
            $.ajax({
                url: "/admin/participants/update-user-data",
                type: "POST",
                data: formData,
                processData: false,
                contentType: false,
                success: function (response) {
                    document.getElementById("spinner").hidden = true;
                    alert(response.message);
                    window.location.reload();
                },
                error: function (xhr) {
                    document.getElementById("spinner").hidden = true;
                    document.getElementById("create-button").disabled=false;

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
});
