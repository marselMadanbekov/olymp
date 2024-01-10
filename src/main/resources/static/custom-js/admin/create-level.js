document.addEventListener('DOMContentLoaded', function () {
    const createGroupForm = document.getElementById('create-level-form');

    createGroupForm.addEventListener('submit', function (e) {
        e.preventDefault();
        if (confirm("Вы уверены что хотите создать уровень?")) {
            let formData = new FormData(this);

            document.getElementById("create-button").disabled=true;
            document.getElementById("spinner").hidden=false;
            $.ajax({
                url: "/admin/levels/create-level",
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
