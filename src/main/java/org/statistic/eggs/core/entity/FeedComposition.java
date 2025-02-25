package org.statistic.eggs.core.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import org.hibernate.annotations.GenericGenerator;

import java.util.List;
import java.util.UUID;

@Entity
public class FeedComposition {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String date;

    @OneToMany(mappedBy = "feedComposition", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FeedComponent> components;

    @OneToMany(mappedBy = "feedComposition", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Vitamin> vitamins;

    public FeedComposition() {}

    public FeedComposition(String name, String date, List<FeedComponent> components, List<Vitamin> vitamins) {
        this.name = name;
        this.date = date;
        this.components = components;
        this.vitamins = vitamins;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<FeedComponent> getComponents() {
        return components;
    }

    public void setComponents(List<FeedComponent> components) {
        this.components = components;
    }

    public List<Vitamin> getVitamins() {
        return vitamins;
    }

    public void setVitamins(List<Vitamin> vitamins) {
        this.vitamins = vitamins;
    }
}

