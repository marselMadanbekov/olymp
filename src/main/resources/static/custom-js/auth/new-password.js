document.addEventListener('DOMContentLoaded', function () {
    const newPasswordForm = document.getElementById("new-password-form");

    newPasswordForm.addEventListener("submit", function (e) {
        e.preventDefault();

        document.getElementById("progress-spinner").hidden = false;
        $.ajax({
            url: "/auth/new-password",
            type: "POST",
            data: new FormData(this),
            processData: false,
            contentType: false,
            success: function (response) {
                document.getElementById("progress-spinner").hidden = true;
                window.location.href = '/login';
            },
            error: function (xhr) {
                document.getElementById("progress-spinner").hidden = true;
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
    })
});
