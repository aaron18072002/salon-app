package com.coding.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "CATEGORIES")
public class Category extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ElementCollection
    @CollectionTable(
            name = "category_images",
            joinColumns = @JoinColumn(name = "category_id")
    )
    @Column(name = "image_url")
    private List<String> images;

    @Column(name = "salon_id", nullable = false)
    private UUID salonId;

}
