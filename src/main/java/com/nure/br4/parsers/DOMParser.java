package com.nure.br4.parsers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nure.br4.domain.models.Drink;
import htmlflow.StaticHtml;
import lombok.SneakyThrows;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DOMParser {


    @SneakyThrows
    public void marshal(Class toParseClass, Object instance, String fileName) {
        ObjectMapper objectMapper = new ObjectMapper();
        var objectNode = objectMapper.valueToTree(instance);
        var elements = objectNode.fields();

        var content = StaticHtml
                .view();
        content
                .html()
                .body();

        elements.forEachRemaining(
                element -> {
                    content.div()
                            .text(element.getKey())
                            .text(":")
                            .text(element.getValue().toPrettyString())
                            .__();
                }
        );
        try(PrintStream out = new PrintStream(new FileOutputStream(fileName+".html"))) {
            var renderContent = content.render()+"</body>"+"</html>";
            out.write(
                    renderContent.getBytes(StandardCharsets.UTF_8)
            );
        }
    }

    private String getSetterName(String fieldName) {
        return "set"+fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
    }

    private String removeDoubleQuotes(String value) {
        return value.replaceAll("^\"+|\"+$", "");
    }

    @SneakyThrows
    public <T> T unmarshall(Class toParseClass, String fileName) {
        ObjectMapper objectMapper = new ObjectMapper();
        Document doc = Jsoup.parse(new File(fileName+".html"), String.valueOf(StandardCharsets.UTF_8));
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        doc.body().select("div").eachText().forEach(
                element -> {
                    var result = element.split(" : ", 0);
                    if (result.length>2) result = element.split(" : ", 2);
                    map.put(result[0], result[1]);
                }
        );
        var instance = toParseClass.newInstance();
        Arrays.stream(toParseClass.getDeclaredFields()).forEach(
                field -> {
                   var fieldName = field.getName();
                   var result = map.get(fieldName);
                   var type = field.getType();
                   if (List.class.isAssignableFrom(type)) {
                       try {
                           var list = objectMapper.readValue(result.toString(), List.class);
                           ParameterizedType parameterizedType = (ParameterizedType) field.getGenericType();
                           Class<?> listClass = (Class<?>) parameterizedType.getActualTypeArguments()[0];
                           List parameterizedList = (List) list.stream().map(
                                   element -> {
                                       element = objectMapper.convertValue(element, listClass);
                                       return element;
                                   }
                           ).collect(Collectors.toList());
                           toParseClass.getMethod(getSetterName(fieldName), List.class).invoke(instance, parameterizedList);
                       } catch (JsonProcessingException e) {
                           e.printStackTrace();
                       }
                       catch (Exception e) {
                           e.printStackTrace();
                       }
                   }
                   else {
                      if (String.class.isAssignableFrom(type)) result = removeDoubleQuotes(result.toString());
                      var element = objectMapper.convertValue(result, type);
                       try {
                           instance.getClass().getMethod(getSetterName(fieldName), type).invoke(instance, element);
                       } catch (IllegalAccessException e) {
                           e.printStackTrace();
                       } catch (InvocationTargetException e) {
                           e.printStackTrace();
                       } catch (NoSuchMethodException e) {
                           e.printStackTrace();
                       }
                   }
                }
        );
       return (T) instance;
    }
}
