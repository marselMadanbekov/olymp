document.addEventListener('DOMContentLoaded', function () {
    const deletePupilButton = document.getElementById('delete-pupil');

    deletePupilButton.addEventListener("click", function (e){
        e.preventDefault();
        if(confirm("Вы уверены что хотите удалить этого участника?")){
            let formData = new FormData();
            let pupilId = this.getAttribute("participantId");
            formData.append("participantId", pupilId);

            $.ajax({
                url: "/admin/participants/delete-participant",
                type: "POST",
                data: formData,
                processData: false,
                contentType: false,
                success: function (response){
                    alert(response.message);
                    window.location.href = "/admin/participants";
                },
                error: function (xhr){
                    try{
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
            })
        }
    })
});
