package org.statistic.eggs.core.entity;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "feed_composition")
@Access(AccessType.PROPERTY)
public class FeedComposition {

    private UUID id;
    private String name;
    private String date;
    private List<FeedComponent> components;
    private List<Vitamin> vitamins;
    private List<Counter> counter;

    public FeedComposition() {
    }

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    @Column(nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(nullable = false)
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @OneToMany(mappedBy = "feedComposition", cascade = CascadeType.ALL, orphanRemoval = true)
    public List<FeedComponent> getComponents() {
        return components;
    }

    @OneToMany(mappedBy = "feedComposition", cascade = CascadeType.ALL, orphanRemoval = true)
    public List<Counter> getCounter() {
        return counter;
    }


    public void setCounter(List<Counter> counter) {
        this.counter = counter;
    }

    public void setComponents(List<FeedComponent> components) {
        this.components = components;
    }

    @OneToMany(mappedBy = "feedComposition", cascade = CascadeType.ALL, orphanRemoval = true)
    public List<Vitamin> getVitamins() {
        return vitamins;
    }

    public void setVitamins(List<Vitamin> vitamins) {
        this.vitamins = vitamins;
    }
}

