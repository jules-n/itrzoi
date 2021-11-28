package com.nure.br4.domain.models;

import lombok.*;

import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Component {
    private Drink drink;
    private float amount;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Component)) return false;
        Component component = (Component) o;
        return Float.compare(component.getAmount(), getAmount()) == 0 && getDrink().equals(component.getDrink());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getDrink(), getAmount());
    }
}
