document.addEventListener('DOMContentLoaded', function () {
    const registerForm = document.getElementById('register-form');

    registerForm.addEventListener('submit', function (e) {
        e.preventDefault();
        let formData = new FormData(this);
        document.getElementById("create-button").disabled = true;
        document.getElementById("spinner").hidden = false;
        $.ajax({
            url: "/auth/register",
            type: "POST",
            data: formData,
            processData: false,
            contentType: false,
            success: function (response) {
                document.getElementById("spinner").hidden = true;
                document.getElementById("sent-message").innerText = response.message;
                document.getElementById("sent-message").style.display = "block";
            },
            error: function (xhr) {
                document.getElementById("spinner").hidden = true;

                document.getElementById("sent-message").innerText = 'Ошибка регистрации! Проверьте правильность данных и повторите попытку';
                document.getElementById("sent-message").style.display = "block"
                try {
                    const errorData = JSON.parse(xhr.responseText);
                    if (errorData.hasOwnProperty("error")) {
                        console.log(errorData.error);
                    } else {
                        console.log('Что то пошло не так');
                    }
                } catch (e) {
                    console.log('Что то пошло не так');
                }
            }
        });
    })
});
