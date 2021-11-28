package com.nure.br4.domain.models;

import lombok.*;

import java.util.Objects;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class Characteristic<V> {
    private String name;
    private V value;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Characteristic)) return false;
        Characteristic<?> that = (Characteristic<?>) o;
        return Objects.equals(getName(), that.getName()) && Objects.equals(getValue(), that.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getValue());
    }
}
