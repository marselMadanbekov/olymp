document.addEventListener('DOMContentLoaded', function () {
    const passwordResetUsernameForm = document.getElementById("password-reset-username-form");

    passwordResetUsernameForm.addEventListener("submit", function (e) {
        e.preventDefault();

        document.getElementById("progress-spinner").hidden = false;

        $.ajax({
            url: "/auth/password-reset",
            type: "POST",
            data: new FormData(passwordResetUsernameForm),
            processData: false,
            contentType: false,
            success: function (response) {
                window.location.href = `/auth/confirmation-code?username=${response.username}`;
                document.getElementById("progress-spinner").hidden = true;
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
                document.getElementById("user-not-found-label").hidden = false;
            }
        });
    })
});
