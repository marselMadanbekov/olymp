document.addEventListener('DOMContentLoaded', function () {
    const applyForm = document.getElementById("apply-form");
    const rejectForm = document.getElementById("reject-form");

    applyForm.addEventListener("submit", function (e){
        e.preventDefault();

        if(confirm("Вы уверены что хотите принять этот запрос?")){
            document.getElementById("apply-button").disabled = true;
            document.getElementById("apply-spinner").hidden = false;
            $.ajax({
                url: "/admin/temp-users/apply",
                type: "POST",
                data: new FormData(this),
                processData: false,
                contentType: false,
                success: function (response) {
                    document.getElementById("apply-spinner").hidden = true;
                    alert(response.message);
                    window.location.href = "/admin/participants";
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

    rejectForm.addEventListener("submit", function (e){
        e.preventDefault();

        if(confirm("Вы уверены что хотите отклонить этот запрос?")){
            document.getElementById("reject-button").disabled = true;
            document.getElementById("reject-spinner").hidden = false;
            $.ajax({
                url: "/admin/temp-users/reject",
                type: "POST",
                data: new FormData(this),
                processData: false,
                contentType: false,
                success: function (response) {
                    document.getElementById("reject-spinner").hidden = true;
                    alert(response.message);
                    window.location.href = "/admin/participants";
                },
                error: function (xhr) {
                    document.getElementById("reject-button").disabled = false;
                    document.getElementById("reject-spinner").hidden = true;
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
