package com.template.api.apps.member;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
public class Member {
    @Id
    @GeneratedValue
    Long id;
    String name;
    String address;

    public void update(String name, String address) {
        this.name = name;
        this.address = address;
    }
}
