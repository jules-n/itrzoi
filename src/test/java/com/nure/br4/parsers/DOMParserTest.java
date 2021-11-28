package com.nure.br4.parsers;

import com.nure.br4.domain.models.Characteristic;
import com.nure.br4.domain.models.Component;
import com.nure.br4.domain.models.Drink;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class DOMParserTest {

    private DOMParser parser;
    private String idCola;
    private String idTonic;
    private String idGin;
    private String idGinTonic;
    private List<Drink> drinks;

    @BeforeEach
    void setUp() {
        parser = new DOMParser();
        idCola = UUID.randomUUID().toString();
        idTonic = "5a80d724-dde0-4dc5-b8c3-0cf62724a009";
        idGin = "cda196fb-3c8b-4a73-b771-a8b59bcb37d0";
        idGinTonic = "5fbb33c2-0e62-4360-b80b-f6abbb25b2ba";
        drinks = new ArrayList<>();
        drinks.add(
                Drink.builder()
                        .id(idCola)
                        .name("Coca-Cola")
                        .volume(0.5f)
                        .price(12f)
                        .build()
        );
        drinks.add(
                Drink.builder()
                        .id(idTonic)
                        .name("Tonic Everves")
                        .volume(0.5f)
                        .price(18f)
                        .build()
        );
        drinks.add(
                Drink.builder()
                        .id(idGin)
                        .name("London dry gin")
                        .volume(0.1f)
                        .price(85f)
                        .build()
        );
        Characteristic<Float> ginTonicABV = new Characteristic<>("ABV", 15f);
        drinks.add(
                Drink.builder()
                        .id(idGinTonic)
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
    }

    @Test
    void marshal() {
        parser.marshal(Drink.class, drinks.get(3), "drink");
        assertNotNull(new File("drink.html"));
    }

    @Test
    void unmarshal() {
        var result = (Drink) parser.unmarshall(Drink.class, "drink");
        assertThat(result.toString()).isEqualTo(result.toString());
    }
}