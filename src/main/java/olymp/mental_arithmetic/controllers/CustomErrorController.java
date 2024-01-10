package olymp.mental_arithmetic.controllers;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/error")
public class CustomErrorController implements ErrorController {

    @GetMapping("/auth-error")
    public String authError(@RequestParam Integer error,
                            Model model) {
        String message;
        String submessage;
        switch (error) {
            case 0: {
                message = "Неправильные данные!";
                submessage = "Неправильные логин или пароль. Проверьте правильность данных и повторите попытку.";
                break;
            }
            case 1: {
                message = "Аккаунт не активен!";
                submessage = "Ваш аккаунт не активен в данное время. Возможно администратор вас заблокировал.";
                break;
            }
            case 2:{
                message = "Такого пользователя нет!";
                submessage = "Пользователя с таким логином не существует. Проверьте правильность логина и повторите попытку!";
                break;
            }
            default: {
                message = "Неизвестная ошибка!";
                submessage = "Произошла неизвестная ошибка. Проверьте правильность данных и попробуйте попытку.";
                break;
            }
        }
        model.addAttribute("message", message);
        model.addAttribute("submessage", submessage);
        model.addAttribute("status", error);
        return "error/error";
    }


    @RequestMapping("")
    public String handleError(HttpServletRequest request,
                              @RequestParam(required = false) String message1,
                              Model model) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        Object errorMessage = request.getAttribute("javax.servlet.error.message");
        int code = 0;
        String message = "";
        String submessage = "";

        if (status != null) {
            code = Integer.parseInt(status.toString());
            if (code == HttpStatus.NOT_FOUND.value()) {
                message = "Страница не найдена!";
                submessage = "Запрашиваемая вами страница не найдена, убедитесь в правильности адреса и попробуйте еще раз.";
            } else if (code == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                message = "Ошибка сервера!";
                submessage = "При обработке запроса возникли неполадки.";
            } else {
                message = "Неизвестная ошибка!";
            }
        }else {
            message = message1;
        }
        model.addAttribute("message", message);
        model.addAttribute("submessage", submessage);
        model.addAttribute("status", status);
        return "error/error";
    }
}
