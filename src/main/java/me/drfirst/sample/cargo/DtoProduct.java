package me.drfirst.sample.cargo;

import lombok.NonNull;
import lombok.Value;
import lombok.experimental.Wither;
import me.drfirst.sample.entities.JpaProduct;

/**
 * Product (JSON) sent over the wire.
 *
 */
@Value(staticConstructor = "of")
@Wither
public class DtoProduct {
    public static DtoProduct of(@NonNull final JpaProduct product) {
        return of(
                product.getId(),
                product.getName(),
                product.getPrice());
    }

    @NonNull private final String id;
    @NonNull private final String name;
    @NonNull private final Integer price;
}
