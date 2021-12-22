package com.nure.br4.parsers;

import lombok.SneakyThrows;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.io.FileReader;

public class JAXBParser implements IParser {

    @SneakyThrows
    public void marshal(Class toParseClass, Object instance, String fileName) {
        JAXBContext context = JAXBContext.newInstance(toParseClass);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        marshaller.marshal(toParseClass.cast(instance), new File(fileName + ".xml"));
    }

    @SneakyThrows
    public <T> T unmarshal(Class toParseClass, String fileName) {
        JAXBContext context = JAXBContext.newInstance(toParseClass);
        return (T) context.createUnmarshaller()
                .unmarshal(new FileReader(fileName + ".xml"));
    }
}
