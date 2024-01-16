document.addEventListener('DOMContentLoaded', function () {
    const createTourForm = document.getElementById('create-tour-form');

    createTourForm.addEventListener('submit', function (e) {
        e.preventDefault();
        if (confirm("Вы уверены что хотите создать тур?")) {
            let formData = new FormData(this);

            document.getElementById("create-button").disabled = true;
            document.getElementById("spinner").hidden = false;

            fetch("/admin/tours/create-tour", {
                method: "POST",
                body: formData
            })
                .then(response => response.json()) // Используем response.json() для получения объекта JSON из ответа
                .then(response => {
                    document.getElementById("spinner").hidden = true;
                    if (response.message) {
                        alert(response.message); // Показываем сообщение, если оно присутствует в ответе
                    } else if (response.error) {
                        alert(response.error); // Показываем сообщение об ошибке, если оно есть
                    }
                    window.location.reload();
                })
                .catch(error => {
                    document.getElementById("spinner").hidden = true;
                    document.getElementById("create-button").disabled = false;

                    alert("Ошибка:" + error); // Вывести ошибку, если что-то пошло не так
                });
        }
    })
});
