package com.sparta.assignment.domain;

import lombok.*;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

//@RequiredArgsConstructor  ->UserRoleEnum 아닌가?
public enum UserRoleEnum {
  USER(Authority.USER);

  private final String authority;

  UserRoleEnum(String authority){
    this.authority = authority;
  }

  public String getAuthority() {
    return this.authority;
  }

  public static class Authority{
    public static final String USER = "ROLE_USER";
  }
}
