document.addEventListener('DOMContentLoaded', function () {
    const addparticipant = document.getElementById('add-participant-by-level-form');

    addparticipant.addEventListener('submit', function (e) {
        e.preventDefault();
        if (confirm("Вы уверены что хотите зачислить всех пользователей с таким уровнем на тур?")) {
            let formData = new FormData(this);

            document.getElementById("add-button").disabled=true;
            document.getElementById("spinner").hidden=false;
            $.ajax({
                url: "/admin/tours/add-by-level",
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
