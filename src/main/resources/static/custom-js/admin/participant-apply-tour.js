document.addEventListener('DOMContentLoaded', function () {
    const applyForm = document.getElementById("apply-participant-to-tour");

    applyForm.addEventListener("submit", function (e){
        e.preventDefault();

        if(confirm("Вы уверены что хотите принять этот запрос?")){
            document.getElementById("apply-button").disabled = true;
            document.getElementById("apply-spinner").hidden = false;
            $.ajax({
                url: "/admin/participants/apply-tour",
                type: "POST",
                data: new FormData(this),
                processData: false,
                contentType: false,
                success: function (response) {
                    document.getElementById("apply-spinner").hidden = true;
                    alert(response.message);
                    window.location.reload();
                },
                error: function (xhr) {
                    document.getElementById("apply-button").disabled = false;
                    document.getElementById("apply-spinner").hidden = true;
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
