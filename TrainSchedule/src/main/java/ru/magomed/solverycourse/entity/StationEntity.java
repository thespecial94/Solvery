package ru.magomed.solverycourse.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "station")
public class StationEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "name")
    private String name;

    @ManyToMany
    @JoinTable(
            name = "schedule",
            joinColumns = @JoinColumn(name = "station_id"),
            inverseJoinColumns = @JoinColumn(name = "train_id"))
    private List<TrainEntity> trainEntities;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
