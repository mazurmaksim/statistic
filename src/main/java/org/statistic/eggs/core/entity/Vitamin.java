package org.statistic.eggs.core.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

@Entity
public class Vitamin {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "feed_composition_id")
    private FeedComposition feedComposition;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String quantity;

    public Vitamin() {}

    public Vitamin(String name, String quantity, FeedComposition feedComposition) {
        this.name = name;
        this.quantity = quantity;
        this.feedComposition = feedComposition;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public FeedComposition getFeedComposition() {
        return feedComposition;
    }

    public void setFeedComposition(FeedComposition feedComposition) {
        this.feedComposition = feedComposition;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}