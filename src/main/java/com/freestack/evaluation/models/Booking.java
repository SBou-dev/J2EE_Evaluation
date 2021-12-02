package com.freestack.evaluation.models;

import javax.persistence.*;
import java.time.Instant;

@Entity
public class Booking {
    // Attributs
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "evaluation")
    private Integer score;

    @Column(name = "start_of_the_booking", columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private Instant startOfTheBooking;

    @Column(name = "end_of_the_booking", columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private Instant endOfTheBooking;

    @ManyToOne
    @JoinColumn(name = "driver_id")
    private UberDriver driver;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UberUser user;

    // Constructeur
    public Booking() {
    }

    // Getter Setter

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Instant getStartOfTheBooking() {
        return startOfTheBooking;
    }

    public void setStartOfTheBooking(Instant startOfTheBooking) {
        this.startOfTheBooking = startOfTheBooking;
    }

    public Instant getEndOfTheBooking() {
        return endOfTheBooking;
    }

    public void setEndOfTheBooking(Instant endOfTheBooking) {
        this.endOfTheBooking = endOfTheBooking;
    }

    public UberDriver getDriver() {
        return driver;
    }

    public void setDriver(UberDriver driver) {
        this.driver = driver;
    }

    public UberUser getUser() {
        return user;
    }

    public void setUser(UberUser user) {
        this.user = user;
    }

    // ToString
    @Override
    public String toString() {
        return "Booking{" +
                "id=" + id +
                ", evaluation=" + score +
                ", start_of_the_booking=" + startOfTheBooking +
                ", end_of_the_booking=" + endOfTheBooking +
                ", driver=" + driver +
                ", user=" + user +
                '}';
    }
}
