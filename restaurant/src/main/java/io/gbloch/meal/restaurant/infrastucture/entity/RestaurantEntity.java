package io.gbloch.meal.restaurant.infrastucture.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "restaurants")
public class RestaurantEntity {

    @Id
    private UUID restaurantId;

    private boolean active;

    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(
        name = "restaurants_products",
        joinColumns = @JoinColumn(name = "restaurant_id"),
        inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private Set<ProductEntity> products = new HashSet<>();
}
