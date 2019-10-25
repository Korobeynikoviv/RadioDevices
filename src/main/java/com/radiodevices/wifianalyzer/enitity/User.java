package com.radiodevices.wifianalyzer.enitity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "users")
public class User implements Serializable {
    @Id
    @GeneratedValue(generator = "user_generator")
    @SequenceGenerator(
            name = "user_generator",
            sequenceName = "user_sequence",
            initialValue = 1000
    )
    private Long id;

    @Column(columnDefinition = "email")
    private String email;

    @Column(columnDefinition = "name")
    private String name;

    @Column(columnDefinition = "pass_hash")
    private String hash;

    @Column(columnDefinition = "role")
    private String role;

    public Long getId() {
        return id;
    }

    /*
    * Получить email
    * */
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    /*
    * Получить наименование
    * */
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /*
    * Получить Hash
    * */
    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String toString() {
        return this.getId() + "; "
                + this.getEmail() + "; "
                + this.getName() + "; "
                + this.getHash();
    }
}
