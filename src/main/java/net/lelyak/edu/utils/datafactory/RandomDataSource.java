package net.lelyak.edu.utils.datafactory;

import net.lelyak.edu.entity.enums.Rating;
import org.apache.commons.lang.math.RandomUtils;
import org.fluttercode.datafactory.impl.DataFactory;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.*;

public class RandomDataSource {

    private final DataFactory dataFactory = new DataFactory();
    private final Map<String, String> storedData = new HashMap<>();
    private final Random random = new Random();
    private final String characters = "qwertyuiopasdfghjklzxcvbnm";

    public void fillEntity(Object entity) {
        if (entity != null) {

            // add all class fields
            Class<?> aClass = entity.getClass();
            List<Field> fields = Arrays.asList(aClass.getDeclaredFields());
            List<Field> allFields = new ArrayList<>(fields);

            // add all super class fields
            if (aClass.getSuperclass() != null) {
                List<Field> superFields = Arrays.asList(aClass.getSuperclass().getDeclaredFields());
                allFields.addAll(superFields);
            }

            for (Field field : allFields) {
                if (field.isAnnotationPresent(InjectRandomData.class)) {

                    InjectRandomData data = field.getAnnotation(InjectRandomData.class);
                    field.setAccessible(true);

                    switch (data.type()) {
                        case NUMERIC:
                            ReflectionUtils
                                    .setField(field, entity, getNumeric(data.min(), data.max()));
                            break;

                        case STRING:
                            if (data.join() != null && !data.join().isEmpty()) {

                                ReflectionUtils.setField(field, entity,
                                                join(data.join(), getString(data.min(), data.max())));
                                break;
                            }
                            ReflectionUtils.setField(field, entity, getString(data.min(), data.max()));
                            break;

                        case ADDRESS:
                            ReflectionUtils.setField(field, entity, getAddress());
                            break;
                        case NAME:
                            ReflectionUtils.setField(field, entity, getName());
                            break;
                        case FIRST_NAME:
                            ReflectionUtils.setField(field, entity, getFirstName());
                            break;
                        case LAST_NAME:
                            ReflectionUtils.setField(field, entity, getLastName());
                            break;
                        case BIRTH_DATE:
                            ReflectionUtils.setField(field, entity, getBirthDate());
                            break;
                        case BUSINESS_NAME:
                            ReflectionUtils.setField(field, entity, getBusinessName());
                            break;
                        case EMAIL:
                            ReflectionUtils.setField(field, entity, getEmail());
                            break;
                        case CITY:
                            ReflectionUtils.setField(field, entity, getCity());
                            break;
                        case STREET:
                            ReflectionUtils.setField(field, entity, getStreet());
                            break;
                        case TEXT:
                            ReflectionUtils.setField(field, entity, getText(data.min(), data.max()));
                            break;
                        case WORD:
                            ReflectionUtils.setField(field, entity, getWord(data.min(), data.max()));
                            break;
                        case CHARS:
                            ReflectionUtils.setField(field, entity, getChars(data.min(), data.max()));
                            break;
                        case BOOLEAN:
                            ReflectionUtils.setField(field, entity, getBoolean());
                            break;
                        case GENDER:
                            ReflectionUtils.setField(field, entity, getGender());
                            break;
                        case PRICE:
                            ReflectionUtils.setField(field, entity, getDouble());
                            break;
                        case ROLE:
                            ReflectionUtils.setField(field, entity, getRole());
                            break;
                        case FUTURE_DATE:
                            ReflectionUtils.setField(field, entity, getDate(data.min(), data.max()));
                            break;
                        case NUMBER:
                            ReflectionUtils.setField(field, entity, getNumber(data.min(), data.max()));
                            break;
                        case EVENT_RATING:
                            ReflectionUtils.setField(field, entity, getEventRating());
                            break;
                    }
                }
            }
        }
    }

    private Rating getEventRating() {
        Rating[] ratings = Rating.values();
        return ratings[random.nextInt(ratings.length)];
    }

    private int getNumber(int from, int to) {
        return from + random.nextInt(to - from);
    }

    private Date getDate(int minDaysJump, int maxDaysJump) {
        Date now = new Date();
        return dataFactory.getDate(now, minDaysJump, maxDaysJump);
    }

    private double getDouble() {
        double randDouble = RandomUtils.nextDouble() * 100;
        return  (double) Math.round(randDouble);
    }

    private String getRole() {
        return random.nextBoolean() ? "BOOKING_MANAGER" : "REGISTERED_USER";
    }

    private String join(String id, String value) {
        if (storedData.containsKey(id)) {
            return storedData.get(id);
        }
        storedData.put(id, value);
        return value;
    }

    private char rand() {
        return characters.charAt(random.nextInt(characters.length()));
    }

    private String rand(int length) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            builder.append(characters.charAt(random.nextInt(characters.length())));
        }
        return builder.toString();
    }

    public String getNumeric(int min, int max) {
        return String.valueOf(dataFactory.getNumberBetween(min, max));
    }

    public String getString(int min, int max) {
        return dataFactory.getRandomChars(min, max);
    }

    public String getAddress() {
        return dataFactory.getAddress();
    }

    public String getName() {
        return dataFactory.getName();
    }

    public String getFirstName() {
        return dataFactory.getFirstName() + rand(3);
    }

    public String getLastName() {
        return dataFactory.getLastName() + rand(3);
    }

    public Date getBirthDate() {
        return dataFactory.getBirthDate();
    }

    public String getBusinessName() {
        return dataFactory.getBusinessName();
    }

    public String getEmail() {
        return dataFactory.getEmailAddress();
    }

    public String getCity() {
        return dataFactory.getCity();
    }

    public String getStreet() {
        return dataFactory.getStreetName();
    }

    public String getText(int min, int max) {
        return dataFactory.getRandomText(min, max);
    }

    public String getWord(int min, int max) {
        return dataFactory.getRandomWord(min, max);
    }

    public String getChars(int min, int max) {
        return dataFactory.getRandomChars(min, max);
    }

    public String getBoolean() {
        return String.valueOf(random.nextBoolean());
    }

    public String getGender() {
        return random.nextBoolean() ? "Male" : "Female";
    }
}
