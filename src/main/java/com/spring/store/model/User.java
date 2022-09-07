package com.spring.store.model;

import com.spring.store.validator.ValidPhoneNumber;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Collection;
import java.util.Set;

@Entity
@Table(name = "usr")
@Data
@NoArgsConstructor
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Length(min = 5, message = "Имя пользователя должно быть не меньше 5-ти символов")
    private String username;
    @NotBlank(message = "Поле имени не может быть пустым")
    @Length(max = 60, message = "Значение имени слишком длинное")
    private String firstName;
    @NotBlank(message = "Поле фамилии не может быть пустым")
    @Length(max = 60, message = "Значение фамилии слишком длинное")
    private String lastName;
    @ValidPhoneNumber(message = "Пожалуйста, введите корректный номер телефона")
    private String phone;

    @NotBlank(message = "Поле email не может быть пустым")
    @Email(message = "Введите правильный email адрес")
    private String email;
    private String activationCode;
    private String country;
    private String address;

    @Length(min = 6, message = "Пароль должен быть не меньше 6-ти символов")
    private String password;
    private boolean active;

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;

    private String resetPasswordToken;
    private String confirmOrderToken;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }

    public boolean isAdmin() {
        return roles.contains(Role.ADMIN);
    }

    public boolean isManager() {
        return roles.contains(Role.MANAGER);
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isActive();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
