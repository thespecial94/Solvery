package ru.magomed.solverycourse.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "train")
public class TrainEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "count_seats")
    private int countSeats;

    @ManyToMany
    @JoinTable(
            name = "ticket",
            joinColumns = @JoinColumn(name = "train_id"),
            inverseJoinColumns = @JoinColumn(name = "passenger_id"))
    private List<PassengerEntity> passengerEntities;

    @ManyToMany(mappedBy = "trainEntities")
    private List<StationEntity> stationEntities;

    public int getCountSeats() {
        return countSeats;
    }

    public void setCountSeats(int countSeats) {
        this.countSeats = countSeats;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<PassengerEntity> getPassengerEntities() {
        return passengerEntities;
    }

    public void setPassengerEntities(List<PassengerEntity> passengerEntities) {
        this.passengerEntities = passengerEntities;
    }
}
