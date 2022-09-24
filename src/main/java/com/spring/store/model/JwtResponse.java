package com.spring.store.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class JwtResponse implements Serializable {
    private final String jwttoken;
    private final String name;
    private final boolean isAdmin;
    private final boolean isManager;
}
