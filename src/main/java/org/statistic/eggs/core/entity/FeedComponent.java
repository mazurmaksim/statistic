package org.statistic.eggs.core.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class FeedComponent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "feed_composition_id")
    private FeedComposition feedComposition;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String quantity;

    public FeedComponent() {}

    public FeedComponent(String name, String quantity, FeedComposition feedComposition) {
        this.name = name;
        this.quantity = quantity;
        this.feedComposition = feedComposition;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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
