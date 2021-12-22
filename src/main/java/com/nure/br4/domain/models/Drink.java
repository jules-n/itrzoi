package com.nure.br4.domain.models;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.*;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.List;
import java.util.Objects;

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
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Drink", propOrder = {
        "id",
        "name",
        "volume",
        "characteristics",
        "price",
        "components"
})
public class Drink {
    private String id;
    private String name;
    private float volume;
    private List<Characteristic> characteristics;
    private float price;
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
