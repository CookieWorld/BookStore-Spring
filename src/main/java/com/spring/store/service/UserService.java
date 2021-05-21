package com.spring.store.service;

import com.spring.store.controller.ControllerUtils;
import com.spring.store.model.Order;
import com.spring.store.model.OrderLine;
import com.spring.store.model.Role;
import com.spring.store.model.User;
import com.spring.store.repos.OrderRepo;
import com.spring.store.repos.UserRepo;
import freemarker.template.utility.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;

import javax.mail.MessagingException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private MailSender mailSender;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUsername(username);
        if (user == null) throw new IllegalArgumentException("Пользователь не найден");
        if (!user.isActive()) throw new IllegalArgumentException("Пользователь не активирован");
        return user;
    }

    public boolean addUser(User user) throws MessagingException {
        User userFromDB = userRepo.findByUsername(user.getUsername());
        if (userFromDB != null) {
            return false;
        }
        user.setActive(false);
        user.setRoles(Collections.singleton(Role.USER));
        user.setActivationCode(UUID.randomUUID().toString());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepo.save(user);

        sendMessage(user);
        return true;
    }

    private void sendMessage(User user) throws MessagingException {
        if (!StringUtils.isEmpty(user.getEmail())) {
            String message = "<h1>Здравствуйте, " + user.getUsername() + "!</h1>\n" +
                    "<p>Добро пожаловать на сайт <b>Book store</b> :). Пожалуйста, перейдите по ссылке, чтобы активировать аккаунт</p>" +
                    "<a href=\"http://localhost:8080/activate/" + user.getActivationCode() + "\"</a>http://localhost:8080/activate/" + user.getActivationCode() + "</a>";
            mailSender.send(user.getEmail(), "Activation code", message);
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

    public void saveUser(User user, String username, Map<String, String> form) {
        user.setUsername(username);
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

    public void updateProfile(User user, String email, String password, String phone) throws MessagingException {
        String userEmail = user.getEmail();

        boolean isEmailChanged = (email != null && !email.equals(userEmail)) || (userEmail != null && userEmail.equals(email));
        boolean isPasswordChanged = (password != null && !password.equals(user.getPassword()));
        boolean isPhoneChanged =  (phone != null && !phone.equals(user.getPhone()));

        if (isEmailChanged) {
            user.setEmail(email);
            user.setActive(false);
            if (!StringUtils.isEmpty(email)) {
                user.setActivationCode(UUID.randomUUID().toString());
            }
        }

        if (isPasswordChanged && !StringUtils.isEmpty(password)) {
            user.setPassword(password);
        }

        if (isPhoneChanged) {
            user.setPhone(phone);
        }
        userRepo.save(user);

        if (isEmailChanged) {
            sendMessage(user);
        }
    }

    public List<Order> getOrders(User user) {
        List<Order> allByUser = orderRepo.findAllByUser(user);
        return allByUser;
    }
}
