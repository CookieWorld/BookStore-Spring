package com.spring.store.controller;

import com.spring.store.exceptions.UserNotFoundExecption;
import com.spring.store.model.User;
import com.spring.store.service.UserService;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.util.Map;

@Controller
public class RegistrationController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public RegistrationController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(
            @RequestParam("password2") String passwordConfirm,
            @Valid User user,
            BindingResult bindingResult,
            Model model) throws MessagingException, UnsupportedEncodingException {
        boolean isConfirmEmpty = passwordConfirm.isBlank();

        if (isConfirmEmpty) {
            model.addAttribute("password2Error", "Подтверждение пароля не может быть пустым");
            return "registration";
        }

        if (user.getPassword() != null && !user.getPassword().equals(passwordConfirm)) {
            model.addAttribute("passwordError", "Пароли не равны");
        }

        if (isConfirmEmpty || bindingResult.hasErrors()) {
            Map<String, String> errors = ControllerUtils.getErrors(bindingResult);

            model.mergeAttributes(errors);

            return "registration";
        }
        if (!userService.addUser(user)) {
            model.addAttribute("messageType", "danger");
            model.addAttribute("message", "Пользователь c таким логином/почтой уже существует!");
            return "registration";
        }

        model.addAttribute("messageType", "success");
        model.addAttribute("message", "Подтвердите свою почту, перейдя по ссылке в письме");

        return "login";
    }

    @GetMapping("/activate/{code}")
    public String activate(Model model, @PathVariable String code) {
        boolean isActivated = userService.activateUser(code);

        if (isActivated) {
            model.addAttribute("messageType", "success");
            model.addAttribute("message", "Пользователь успешно активирован");
        } else {
            model.addAttribute("messageType", "danger");
            model.addAttribute("message", "Код активации не найден");
        }
        return "login";
    }

    @GetMapping("/forgot_password")
    public String showForgotPasswordForm() {
        return "forgot_password";
    }

    @PostMapping("/forgot_password")
    public String processForgotPassword(@RequestParam String email, Model model) throws MessagingException, UnsupportedEncodingException {
        String token = RandomString.make(30);

        try {
            userService.updateResetPasswordToken(token, email);
            userService.sendMessage(email, token);
            model.addAttribute("message", "Мы отправили вам ссылку на восстановление пароля на ваш почтовый адрес!");
        } catch (UserNotFoundExecption userNotFoundExecption) {
            model.addAttribute("error", userNotFoundExecption.getMessage());
        }

        return "forgot_password";
    }

    @GetMapping("/reset_password")
    public String showResetPasswordForm(@Param(value = "token") String token, Model model) {
        User user = userService.getByResetPasswordToken(token);
        model.addAttribute("token", token);

        if (user == null) {
            model.addAttribute("message", "Неправильный токен");
            return "reset_password";
        }

        return "reset_password";
    }

    @PostMapping("/reset_password")
    public String processResetPassword(
            @RequestParam("password") String newPassword,
            @RequestParam("password2") String passwordConfirm,
            @RequestParam String token,
            Model model) {
        User user = userService.getByResetPasswordToken(token);

        if (user != null) {
            Boolean isPasswordEquals = newPassword.equals(passwordConfirm);

            if (isPasswordEquals) {
                userService.updatePassword(user, newPassword);
                model.addAttribute("message", "Ваш пароль успешно обновлен");
            }
        } else {
            model.addAttribute("message", "Неправильный токен");
            return "reset_password";
        }

        return "reset_password";
    }
}
