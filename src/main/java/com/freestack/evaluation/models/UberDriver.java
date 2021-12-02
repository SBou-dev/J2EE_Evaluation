package com.freestack.evaluation.models;

import javax.persistence.*;
import java.util.List;

@Entity
public class UberDriver {
    // Attributs
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Boolean available;
    private String firstname;
    private String lastname;

    @OneToMany(targetEntity = Booking.class, mappedBy = "driver")
    private List<Booking> courses;

    // Constructeur
    public UberDriver() {
    }

    public UberDriver(String firstname, String lastname) {
        this.available = true;
        this.firstname = firstname;
        this.lastname = lastname;
    }

    // Getter Setter

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable() {
        this.available= !getAvailable();
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    // ToString
    @Override
    public String toString() {
        return "UberDriver{" +
                "id=" + id +
                ", available=" + available +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                '}';
    }
}
