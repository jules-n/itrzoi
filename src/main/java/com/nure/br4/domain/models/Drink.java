package com.nure.br4.domain.models;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.*;
import org.springframework.lang.Nullable;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@XmlRootElement
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Characteristic.class, name = "characteristics"),
        @JsonSubTypes.Type(value = Component.class, name = "components")
})
public class Drink {
    private String id;
    private String name;
    private float volume;
    @Nullable
    private List<Characteristic> characteristics;
    private float price;
    @Nullable
    private List<Component> components;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Drink)) return false;
        Drink drink = (Drink) o;
        return Float.compare(drink.getVolume(), getVolume()) == 0 && Float.compare(drink.getPrice(), getPrice()) == 0 && Objects.equals(getId(), drink.getId()) && Objects.equals(getName(), drink.getName()) && Objects.equals(getCharacteristics(), drink.getCharacteristics()) && Objects.equals(getComponents(), drink.getComponents());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getVolume(), getCharacteristics(), getPrice(), getComponents());
    }
}
