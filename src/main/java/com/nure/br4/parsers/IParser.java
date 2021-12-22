package com.nure.br4.parsers;

public interface IParser {
    void marshal(Class toParseClass, Object instance, String fileName);
    <T> T unmarshal(Class toParseClass, String fileName);
}
