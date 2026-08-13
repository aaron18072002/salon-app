package com.coding.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "SERVICE_OFFERINGS")
public class ServiceOffering extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(length = 500)
    private String description;

    @Column(nullable = false, precision = 12, scale = 0)
    private BigDecimal price;

    @Column(name = "duration_minutes", nullable = false)
    private int durationMinutes;

    @Column(name = "salon_id", nullable = false)
    private UUID salonId;

    @Column(name = "category_id", nullable = false)
    private Long categoryId;

    @ElementCollection
    @CollectionTable(
            name = "service_offering_images",
            joinColumns = @JoinColumn(name = "service_offering_id")
    )
    @Column(name = "image_url")
    private List<String> images;

}
