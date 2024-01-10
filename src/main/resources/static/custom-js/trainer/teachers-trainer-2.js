document.addEventListener('DOMContentLoaded', function () {
    const topicSelect1 = document.getElementById("topicSelect1");
    const topicSelect2 = document.getElementById("topicSelect2");

    const pupil1Select = document.getElementById("pupilSelect1");
    const pupil2Select = document.getElementById("pupilSelect2");

    const countOfPupilsSelect = document.getElementById("pupilsCount");

    const answerInput1 = document.getElementById("answerInput1");
    const answerDiv1 = document.getElementById("answerDiv1");
    const answerInput2 = document.getElementById("answerInput2");
    const answerDiv2 = document.getElementById("answerDiv2");

    const currentNumber1 = document.getElementById("currentNumber1");
    const currentNumber2 = document.getElementById("currentNumber2");


    const breakButton = document.getElementById("breakButton");
    const startButton = document.getElementById("startButton");
    const saveButton = document.getElementById("saveButton");

    let countOfDigits = document.getElementById("digitsCount").value;
    let speed = document.getElementById("speed").value;
    let countOfNumbers = document.getElementById("numbersCount").value;

    let intervalId;

    let showingArray1 = [];
    let showingArray2 = [];
    let correctAnswer1 = null;
    let correctAnswer2 = null;

    let totalCorrectAnswers1 = 0;
    let totalIncorrectAnswers1 = 0;

    let totalCorrectAnswers2 = 0;
    let totalIncorrectAnswers2 = 0;

    countOfPupilsSelect.addEventListener("change",function (){
       window.location.href = "/trainer/trainer-" + this.value;
    });
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
        formData.append("topic1", topicSelect1.value);
        formData.append("topic2", topicSelect2.value);
        $.ajax({
            url: "/trainer/get-multiple-trainer-tasks",
            type: "POST",
            data: formData,
            contentType: false,
            processData: false,
            success: function (response) {
                showingArray1 = response.task1.taskEntry;
                correctAnswer1 = response.task1.answer;
                showingArray2 = response.task2.taskEntry;
                correctAnswer2 = response.task2.answer;
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
        clearTimeout(intervalId);
        breakButton.hidden = true;
        startButton.hidden = false;
        currentNumber1.style.fontSize = 40 + 'px';
        currentNumber2.style.fontSize = 40 + 'px';
        currentNumber1.innerText = "Приготовьтесь!";
        currentNumber2.innerText = "Приготовьтесь!";
    })
    saveButton.addEventListener("click", function () {
        let pupilsAnswer1 = answerInput1.value;
        let pupilsAnswer2 = answerInput2.value;
        if (pupilsAnswer1 == null || pupilsAnswer1 === '' || pupilsAnswer2 == null || pupilsAnswer2 === '') {
            alert("Введите ответ каждого ученика!");
        } else {
            if (+correctAnswer1 === +pupilsAnswer1) {
                document.getElementById("correct1").innerText = totalCorrectAnswers1 + 1;
                totalCorrectAnswers1++;
            } else {
                document.getElementById("incorrect1").innerText = totalIncorrectAnswers1 + 1;
                totalIncorrectAnswers1++;
            }
            if (+correctAnswer2 === +pupilsAnswer2) {
                document.getElementById("correct2").innerText = totalCorrectAnswers2 + 1;
                totalCorrectAnswers2++;
            } else {
                document.getElementById("incorrect2").innerText = totalIncorrectAnswers2 + 1;
                totalIncorrectAnswers2++;
            }

            let results = {
                pupilId1 : pupil1Select.value,
                topic1: topicSelect1.value,
                isCorrect1: +correctAnswer1 === +pupilsAnswer1,
                pupilId2 : pupil2Select.value,
                topic2: topicSelect2.value,
                isCorrect2: +correctAnswer2 === +pupilsAnswer2,
            }

            $.ajax({
                url: "/trainer/submit-multiple-results",
                type: "POST",
                data: JSON.stringify(results), // Преобразуйте объект в строку JSON
                contentType: "application/json",
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
            answerDiv1.hidden = true;
            answerDiv2.hidden = true;
            currentNumber1.style.fontSize = 30 + 'px';
            currentNumber1.innerText = 'Приготовьтесь!';
            currentNumber1.hidden = false;
            currentNumber2.style.fontSize = 30 + 'px';
            currentNumber2.innerText = 'Приготовьтесь!';
            currentNumber2.hidden = false;
        }
    })

    function startTrainer() {
        let index = 0;
        let isPrefixNeeded1 = false;
        let isPrefixNeeded2 = false;


        let fontSize = 100; // Базовый размер шрифта

        if (countOfDigits <= 3) {
            // Небольшое число, можно использовать больший размер шрифта
            fontSize = 100;
        } else {
            // Для более длинных чисел уменьшаем размер шрифта
            fontSize = 100 - (countOfDigits - 3) * 10; // Формула для уменьшения шрифта
        }

        function displayElement() {
            if (index < showingArray1.length) {
                currentNumber1.style.fontSize = fontSize + 'px';
                currentNumber2.style.fontSize = fontSize + 'px';
                if (index > 0 && showingArray1[index - 1] === showingArray1[index]) {
                    if (index > 1 && showingArray1[index] === showingArray1[index - 2] && !isPrefixNeeded1) {
                        currentNumber1.innerText = showingArray1[index];
                        isPrefixNeeded1 = true;
                    } else {
                        currentNumber1.innerText = "  " + showingArray1[index];
                        isPrefixNeeded1 = false;
                    }
                } else {
                    currentNumber1.innerText = showingArray1[index];
                }
                if (index > 0 && showingArray2[index - 1] === showingArray2[index]) {
                    if (index > 1 && showingArray2[index] === showingArray2[index - 2] && !isPrefixNeeded2) {
                        currentNumber2.innerText = showingArray2[index];
                        isPrefixNeeded2 = true;
                    } else {
                        currentNumber2.innerText = "  " + showingArray2[index];
                        isPrefixNeeded2 = false;
                    }
                } else {
                    currentNumber2.innerText = showingArray2[index];
                }


                intervalId = setTimeout(function () {
                    index++;
                    displayElement();
                }, speed * 1000); // Задержка в секундах
            } else {
                currentNumber1.hidden = true;
                answerDiv1.hidden = false;
                currentNumber2.hidden = true;
                answerDiv2.hidden = false;
                saveButton.hidden = false;
                breakButton.hidden = true;
                index = 0;
            }
        }

        displayElement();
    }
});
