package me.drfirst.sample.entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

/**
 * Product JPA Entity.
 *
 */
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
@Data
@Entity
@Table(name = "product")
public final class JpaProduct implements Serializable {
    private static final long serialVersionUID = 1L;
    @NonNull @Column(name = "id") @Id private String id;
    @NonNull @Column(name = "name") private String name;
    @NonNull @Column(name = "price") private Integer price;
}
