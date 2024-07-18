document.addEventListener('DOMContentLoaded', function () {
    const removeParticipantButtons = document.querySelectorAll('.remove-participant');

    removeParticipantButtons.forEach(function (item){
        item.addEventListener("click",function (event){
            event.preventDefault();

            if(confirm("Вы уверены что хотите отчислить из этого тура участника?")){
                let formData = new FormData();
                let participantId = item.getAttribute("participantId");
                formData.append("participantId", participantId);

                $.ajax({
                    url: "/admin/tours/remove-participant",
                    type: "POST",
                    data: formData,
                    processData: false,
                    contentType: false,
                    success: function () {
                        window.location.reload();
                    },
                    error: function (xhr) {
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
    })
});
