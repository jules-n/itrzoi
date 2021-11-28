package com.nure.br4.parsers;

import com.nure.br4.domain.models.Characteristic;
import com.nure.br4.domain.models.Drink;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.xml.bind.JAXBException;
import java.io.*;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

class XMLParserTest {

    @Autowired
    private JAXBParser xmlParser;

    private Drink drink;
    private String fileExampleName;
    private Logger logger;
    private UUID id;
    private Characteristic<Float> oldSpeckledHenABV;
    private Characteristic<Float> oldSpeckledHenIBU;

    @BeforeEach
    void setUp() {
        logger = LogManager.getLogger();
        fileExampleName = "drink";
        id = UUID.fromString("7c7ecf15-cc86-4455-b7d8-6ef96d8e6e4a");
        xmlParser = new JAXBParser();
        oldSpeckledHenABV = new Characteristic<>("ABV", 13f);
        oldSpeckledHenIBU = new Characteristic<>("IBU", 30f);
        drink = Drink.builder()
                .id(id.toString())
                .name("Old Speckled Hen")
                .volume(0.4f)
                .price(89f)
                .characteristics(
                        List.of(
                                oldSpeckledHenIBU,
                                oldSpeckledHenABV
                        )
                )
                .build();
    }

    @Test
    void marshal() throws IOException {
        String expected;
        String result;
        xmlParser.marshal(Drink.class, drink, fileExampleName);
        try (BufferedReader reader = new BufferedReader(new FileReader(fileExampleName + ".xml"));) {
            result = reader.lines().collect(Collectors.joining());
        }
        try (BufferedReader reader = new BufferedReader(new FileReader("drink-example.xml"));) {
            expected = reader.lines().collect(Collectors.joining());
        }
        assertThat(result).isEqualTo(expected);
    }

    @Test
    void unmarshall() {
        Drink result = xmlParser.unmarshal(Drink.class, fileExampleName);
        assertThat(result).isEqualTo(drink);
    }
}