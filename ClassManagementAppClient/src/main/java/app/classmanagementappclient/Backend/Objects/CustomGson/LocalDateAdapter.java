/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.classmanagementappclient.Backend.Objects.CustomGson;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author BEN
 */
public class LocalDateAdapter implements JsonSerializer<LocalDate>, JsonDeserializer<LocalDate> {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;

    @Override
    public JsonElement serialize(LocalDate localDate,
                                 Type type,
                                 JsonSerializationContext jsonSerializationContext) {

        return new JsonPrimitive(localDate.format(formatter)); // "yyyy-MM-dd"

    }

    @Override
    public LocalDate deserialize(JsonElement jsonElement,
                                 Type type,
                                 JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {

        return LocalDate.parse(jsonElement.getAsJsonPrimitive().getAsString(), formatter);
    }
}
