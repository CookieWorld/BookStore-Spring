package com.spring.store.service.impl;

import com.spring.store.entity.User;
import com.spring.store.model.Role;
import com.spring.store.repos.UserRepo;
import com.spring.store.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepo userRepo;
    private final MailSender mailSender;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepo userRepo, MailSender mailSender, BCryptPasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.mailSender = mailSender;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User register(User user) {
        user.setRoles(Collections.singleton(Role.USER));
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userRepo.save(user);
    }

    @Override
    public User update(User user) {
        if (isUserExist(user.getUsername())) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            return userRepo.save(user);
        }
        throw new UsernameNotFoundException("User with username: " + user.getUsername() + " not found");
    }

    @Override
    public User findByUsername(String username) {
        return userRepo.findByUsername(username);
    }

    @Override
    public List<User> getAll() {
        return userRepo.findAll();
    }

    @Override
    public void deleteUser(Long id) {
        userRepo.deleteById(id);
    }

    private boolean isUserExist(String username) {
        return userRepo.findByUsername(username) != null;
    }

    /*public boolean addUser(User user) throws MessagingException, UnsupportedEncodingException {
        User userFromDB = userRepo.findByUsername(user.getUsername());
        if (userFromDB != null) {
            return false;
        }
        userFromDB = userRepo.findByEmail(user.getEmail());
        if (userFromDB != null) {
            return false;
        }
        user.setActive(false);
        user.setRoles(Collections.singleton(Role.USER));
        user.setActivationCode(UUID.randomUUID().toString());
        //user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepo.save(user);

        sendMessage(user);
        return true;
    }

    private void sendMessage(User user) throws MessagingException, UnsupportedEncodingException {
        if (!StringUtils.isEmpty(user.getEmail())) {
            String message = "<h1>Здравствуйте, " + user.getUsername() + "!</h1>\n" +
                    "<p>Добро пожаловать на сайт <b>Book store</b> :). Пожалуйста, перейдите по ссылке, чтобы активировать аккаунт</p>" +
                    "<a href=\"http://localhost:8080/activate/" + user.getActivationCode() + "\">http://localhost:8080/activate/" + user.getActivationCode() + "</a>";
            mailSender.send(user.getEmail(), "Activation code", message);
        }
    }

    public void sendMessage(String email, String token) throws MessagingException, UnsupportedEncodingException {
        if (!StringUtils.isEmpty(email)) {
            String message = String.format(
                    "<h3>Здравствуйте!</h3> " +
                            "<p>Вы запрашивали смену пароля</p>" +
                            "<p><a href=\"http://localhost:8080/reset_password?token=" + token + "\"<b>Поменять пароль</b></a></p>" +
                            "<p>Игнорируйте это письмо, если вы не запрашивали смену пароля.</p>"
            );
            mailSender.send(email, "Смена пароля", message);
        }
    }

    public boolean activateUser(String code) {
        User user = userRepo.findByActivationCode(code);

        if (user == null) {
            return false;
        }

        user.setActivationCode(null);
        user.setActive(true);
        userRepo.save(user);
        return true;
    }

    public List<User> findAll() {
        return userRepo.findAll();
    }

    public List<User> findAllExceptMySelf(Long id) {
        return userRepo.findByIdNot(id);
    }

    public void saveUser(User user, String username, Boolean active, Map<String, String> form) {
        user.setUsername(username);
        user.setActive(active);
        if (active) {
            user.setActivationCode(null);
        } else {
            user.setActivationCode(UUID.randomUUID().toString());
        }
        Set<String> roles = Arrays.stream(Role.values())
                .map(Role::name)
                .collect(Collectors.toSet());
        user.getRoles().clear();
        for (String key : form.keySet()) {
            if (roles.contains(key)) {
                user.getRoles().add(Role.valueOf(key));
            }
        }
        userRepo.save(user);
    }

    public void updateProfile(User user, String email, String password, String phone) throws MessagingException, UnsupportedEncodingException {
        String userEmail = user.getEmail();

        boolean isEmailChanged = (email != null && !email.equals(userEmail)) || (userEmail != null && !userEmail.equals(email));
        boolean isPasswordChanged = (!password.isEmpty() && password != null && !password.equals(user.getPassword()));
        boolean isPhoneChanged = (phone != null && !phone.equals(user.getPhone()));

        if (isEmailChanged) {
            user.setEmail(email);
            user.setActive(false);
            if (!StringUtils.isEmpty(email)) {
                user.setActivationCode(UUID.randomUUID().toString());
            }
        }

        *//*if (isPasswordChanged && !StringUtils.isEmpty(password)) {
            user.setPassword(passwordEncoder.encode(password));
        }*//*

        if (isPhoneChanged) {
            user.setPhone(phone);
        }
        userRepo.save(user);

        if (isEmailChanged) {
            sendMessage(user);
        }
    }

    public void updateResetPasswordToken(String token, String email) throws UserNotFoundExecption {
        User user = userRepo.findByEmail(email);
        if (user != null) {
            user.setResetPasswordToken(token);
            userRepo.save(user);
        } else {
            throw new UserNotFoundExecption("Пользователя с такой почтой " + email + " не найден");
        }
    }

    public User getByResetPasswordToken(String token) {
        return userRepo.findByResetPasswordToken(token);
    }

    public void updatePassword(User user, String newPassword) {
        *//*BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(newPassword);
        user.setPassword(encodedPassword);*//*

        user.setResetPasswordToken(null);
        userRepo.save(user);
    }

    public void updateConfirmOrderToken(String token, String email) throws UserNotFoundExecption {
        User user = userRepo.findByEmail(email);
        if (user != null) {
            user.setConfirmOrderToken(token);
            userRepo.save(user);
        } else {
            throw new UserNotFoundExecption("Пользователя с такой почтой " + email + " не найден");
        }
    }

    public User getByConfirmOrderToken(String token) {
        return userRepo.findByConfirmOrderToken(token);
    }

    public void deleteUser(Long id) {
        userRepo.deleteById(id);
    }*/
}
