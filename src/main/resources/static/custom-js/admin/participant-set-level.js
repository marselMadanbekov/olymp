document.addEventListener('DOMContentLoaded', function () {
    const applyForm = document.getElementById("set-level-form");

    applyForm.addEventListener("submit", function (e){
        e.preventDefault();

        if(confirm("Вы уверены что хотите назначить уровень?")){
            document.getElementById("set-level-button").disabled = true;
            document.getElementById("set-level-spinner").hidden = false;
            $.ajax({
                url: "/admin/participants/set-level",
                type: "POST",
                data: new FormData(this),
                processData: false,
                contentType: false,
                success: function (response) {
                    document.getElementById("set-level-spinner").hidden = true;
                    alert(response.message);
                    window.location.reload();
                },
                error: function (xhr) {
                    document.getElementById("set-level-button").disabled = false;
                    document.getElementById("set-level-spinner").hidden = true;
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
    });
});
