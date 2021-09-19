package com.geekbrains.ru.springmarket.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Collection;

@Data
@Entity
@NoArgsConstructor
@Table(name = "users")
@ToString(exclude = "roleEntities")
@EqualsAndHashCode(exclude = {"id", "roleEntities"})
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Имя пользователя обязательно!")
    private String username;

    @NotBlank(message = "Пароль обязателен!")
    private String password;

    @Email(message = "Требуется указать корректный email")
    private String email;

    private boolean enabled = true;

    @ManyToMany
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Collection<RoleEntity> roles;
}