package com.nure.br4.persistent;

import com.nure.br4.domain.models.Characteristic;
import com.nure.br4.domain.models.Component;
import com.nure.br4.domain.models.Drink;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class DrinksService {

    private List<Drink> drinks = List.of();

    public DrinksService() {
        drinks = new ArrayList<>();
        drinks.add(
                Drink.builder()
                        .id(UUID.randomUUID().toString())
                        .name("Coca-Cola")
                        .volume(0.5f)
                        .price(12f)
                        .build()
        );
        drinks.add(
                Drink.builder()
                        .id(UUID.randomUUID().toString())
                        .name("Tonic Everves")
                        .volume(0.5f)
                        .price(18f)
                        .build()
        );
        drinks.add(
                Drink.builder()
                        .id(UUID.randomUUID().toString())
                        .name("London dry gin")
                        .volume(0.1f)
                        .price(85f)
                        .build()
        );
        Characteristic<Float> ginTonicABV = new Characteristic<>("ABV", 15f);
        drinks.add(
                Drink.builder()
                        .id(UUID.randomUUID().toString())
                        .name("Gin tonic")
                        .characteristics(List.of(
                                ginTonicABV
                        ))
                        .volume(0.25f)
                        .price(100f)
                        .components(
                                List.of(
                                        Component.builder()
                                                .drink(drinks.get(1))
                                                .amount(0.150f)
                                                .build(),
                                        Component.builder()
                                                .drink(drinks.get(2))
                                                .amount(0.05f)
                                                .build()
                                )
                        )
                        .build()
        );
        Characteristic<Float> beerHarpABV = new Characteristic<>("ABV", 11.5f);
        Characteristic<Float> beerHarpIBU = new Characteristic<>("IBU", 18f);
        drinks.add(
                Drink.builder()
                        .id(UUID.randomUUID().toString())
                        .name("Harp")
                        .volume(0.4f)
                        .price(87f)
                        .characteristics(
                                List.of(
                                        beerHarpABV,
                                        beerHarpIBU
                                )
                        )
                        .build()
        );
        drinks.add(
                Drink.builder()
                        .id(UUID.randomUUID().toString())
                        .name("Harp")
                        .volume(0.25f)
                        .price(56f)
                        .characteristics(
                                List.of(
                                        beerHarpABV,
                                        beerHarpIBU
                                )
                        )
                        .build()
        );
        Characteristic<Float> oldSpeckledHenABV = new Characteristic<>("ABV", 13f);
        Characteristic<Float> oldSpeckledHenIBU = new Characteristic<>("IBU", 30f);
        drinks.add(
                Drink.builder()
                        .id(UUID.randomUUID().toString())
                        .name("Old Speckled Hen")
                        .volume(0.4f)
                        .price(89f)
                        .characteristics(
                                List.of(
                                        oldSpeckledHenIBU,
                                        oldSpeckledHenABV
                                )
                        )
                        .build()
        );
    }

    public List<Drink> getAll() {
        return drinks;
    }

    public Optional<Drink> findById(UUID id) {
        drinks.stream().map(
                drink -> {
                    if (drink.getId().equals(id)) {
                        return Optional.of(drink);
                    }
                    return Optional.empty();
                }
        );
        return Optional.empty();
    }

    public Optional<Drink> findByName(String name) {
        drinks.stream().map(
                drink -> {
                    if (drink.getName().equals(name)) {
                        return Optional.of(drink);
                    }
                    return Optional.empty();
                }
        );
        return Optional.empty();
    }

    public void save(Drink drink) {
        drinks.add(drink);
    }

    public void delete(UUID id) {
        var drink = findById(id);
        drinks.remove(drink.get());
    }

    public void update(UUID id, Drink drink) {
        delete(id);
        save(drink);
    }
}
