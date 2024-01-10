document.addEventListener('DOMContentLoaded', function () {
    let psvSelect = document.getElementById("PSV-select");
    let pbSelect = document.getElementById("PB-select");
    let pdSelect = document.getElementById("PD-select");
    let pupilSelect = document.getElementById("pupilSelect");
    let currentTopicLabel = document.getElementById("current-topic");

    const answerInput = document.getElementById("answerInput");
    const answerDiv = document.getElementById("answerDiv");

    const countOfPupilsSelect = document.getElementById("pupilsCount");

    const currentNumber = document.getElementById("currentNumber");

    const voiceOnButton = document.getElementById("voiceOn");
    const voiceOffButton = document.getElementById("voiceOff");

    const breakButton = document.getElementById("breakButton");
    const startButton = document.getElementById("startButton");
    const saveButton = document.getElementById("saveButton");

    let countOfDigits = document.getElementById("digitsCount").value;
    let speed = document.getElementById("speed").value;
    let countOfNumbers = document.getElementById("numbersCount").value;

    let intervalId;
    let speechSynthesis = window.speechSynthesis;

    let voiceOn = true;
    let showingArray = [];
    let correctAnswer = null;

    let totalCorrectAnswers = 0;
    let totalIncorrectAnswers = 0;

    let topic = 'PSV';

    countOfPupilsSelect.addEventListener("change",function (){
        window.location.href = "/trainer/trainer-" + this.value;
    });
    psvSelect.addEventListener("change", function (e) {
        const selectedOption = this.options[this.selectedIndex];
        currentTopicLabel.innerText = selectedOption.getAttribute('data-rus-value');
        topic = this.value;
        pbSelect.value = 0;
        pdSelect.value = 0;
    });

    pbSelect.addEventListener("change", function (e) {
        const selectedOption = this.options[this.selectedIndex];
        currentTopicLabel.innerText = selectedOption.getAttribute('data-rus-value');
        topic = this.value;
        psvSelect.value = 0;
        pdSelect.value = 0;
    });

    pdSelect.addEventListener("change", function (e) {
        const selectedOption = this.options[this.selectedIndex];
        currentTopicLabel.innerText = selectedOption.getAttribute('data-rus-value');
        topic = this.value;

        topic = pdSelect.value;
        psvSelect.value = 0;
        pbSelect.value = 0;
    });

    voiceOnButton.addEventListener("click", function () {
        voiceOn = false;
        voiceOnButton.hidden = true;
        voiceOffButton.hidden = false;
    })
    voiceOffButton.addEventListener("click", function () {
        voiceOn = true;
        voiceOnButton.hidden = false;
        voiceOffButton.hidden = true;
    })

    startButton.addEventListener("click", function () {
        breakButton.hidden = false;
        saveButton.hidden = true;
        startButton.hidden = true;
        countOfDigits = document.getElementById("digitsCount").value;
        countOfNumbers = document.getElementById("numbersCount").value;
        speed = document.getElementById("speed").value;

        let formData = new FormData();
        formData.append("digits", countOfDigits);
        formData.append("count", countOfNumbers);
        formData.append("topic", topic);
        $.ajax({
            url: "/trainer/get-trainer-task",
            type: "POST",
            data: formData,
            contentType: false,
            processData: false,
            success: function (response) {
                showingArray = response.task.taskEntry;
                correctAnswer = response.task.answer;
                console.log(correctAnswer);
                startTrainer();
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
                window.location.reload();
            }
        })
    });

    breakButton.addEventListener("click", function () {
        if (speechSynthesis) {
            speechSynthesis.cancel();
        }
        clearTimeout(intervalId);
        breakButton.hidden = true;
        startButton.hidden = false;
        currentNumber.innerText = "Приготовьтесь!"
    })
    saveButton.addEventListener("click", function () {
        let pupilsAnswer = answerInput.value;
        if (pupilsAnswer == null || pupilsAnswer === '') {
            alert("Введите ответ!");
        } else {
            if(+correctAnswer === +pupilsAnswer){
                document.getElementById("correct").innerText = totalCorrectAnswers + 1;
                totalCorrectAnswers++;
            }else{
                document.getElementById("incorrect").innerText = totalIncorrectAnswers + 1;
                totalIncorrectAnswers++;
            }


            let formData = new FormData();
            formData.append('isCorrect', (+correctAnswer === +pupilsAnswer) + '');
            formData.append('topic', topic);
            formData.append('pupilId', pupilSelect.value);
            $.ajax({
                url: "/trainer/submit-result-of-pupil",
                type: "POST",
                data: formData,
                contentType: false,
                processData: false,
                success: function (response) {
                    alert(response.message);
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
            })
            startButton.hidden = false;
            saveButton.hidden = true;
            answerDiv.hidden = true;
            currentNumber.style.fontSize = 30 + 'px';
            currentNumber.innerText = 'Приготовьтесь!';
            currentNumber.hidden = false;
        }
    })

    function startTrainer() {
        let index = 0;
        let speech = new SpeechSynthesisUtterance();
        speech.lang = 'ru';
        let isPrefixNeeded = false;


        if ('speechSynthesis' in window) {
            // Проверка на доступность speechSynthesis
            if (0.7 < speed && speed < 1.0) {
                speech.rate = 1.6;
            } else if (speed < 0.8) {
                voiceOnButton.click();
            } else {
                speech.rate = 1.2;
            }
            let fontSize = 100; // Базовый размер шрифта

            if (countOfDigits <= 3) {
                // Небольшое число, можно использовать больший размер шрифта
                fontSize = 100;
            } else {
                // Для более длинных чисел уменьшаем размер шрифта
                fontSize = 100 - (countOfDigits - 3) * 10; // Формула для уменьшения шрифта
            }
            function displayElement() {
                try {
                    if (index < showingArray.length) {
                        currentNumber.style.fontSize = fontSize + 'px';
                        if(index > 0 && showingArray[index - 1] === showingArray[index]){
                            if(index > 1 && showingArray[index] === showingArray[index-2] && !isPrefixNeeded){
                                currentNumber.innerText = showingArray[index];
                                isPrefixNeeded = true;
                            }else{
                                currentNumber.innerText = "  " + showingArray[index];
                                isPrefixNeeded = false;
                            }
                        }else{
                            currentNumber.innerText = showingArray[index];
                        }

                        if (voiceOn && speechSynthesis) {
                            speech.text = showingArray[index];
                            speechSynthesis.speak(speech);

                            // Задержка перед следующим вызовом
                            intervalId = setTimeout(function () {
                                index++;
                                displayElement();
                            }, speed * 1000); // Задержка в секундах
                        } else {
                            intervalId = setTimeout(function () {
                                index++;
                                displayElement();
                            }, speed * 1000); // Задержка в секундах
                        }

                    } else {
                        currentNumber.hidden = true;
                        answerDiv.hidden = false;
                        saveButton.hidden = false;
                        breakButton.hidden = true;
                        index = 0;
                    }
                } catch (error) {
                    alert('Произошла ошибка с speechSynthesis: ' + error);
                }
            }

            displayElement();
        } else {
            alert('speechSynthesis не поддерживается в этом браузере.');
        }
    }
});
