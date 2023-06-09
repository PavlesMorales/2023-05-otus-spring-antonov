package ru.otus.spring.homework.repository.impl;

import ru.otus.spring.homework.exceptions.ResourceReaderException;
import ru.otus.spring.homework.repository.ResourceReadable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ResourceCsvReader implements ResourceReadable {
    private final String pathToResource;

    public ResourceCsvReader(String pathToResource) {
        this.pathToResource = pathToResource;
    }

    @Override
    public List<String> readResourceAsStrings() {
        List<String> result = new ArrayList<>();
        try (var in = getClass().getResourceAsStream(pathToResource)) {

            if (in == null) {
                throw new ResourceReaderException("Resource is null");
            }

            var reader = new BufferedReader(new InputStreamReader(in));

            String ignoreHeaders = reader.readLine();
            System.out.println(ignoreHeaders);

            while (reader.ready()) {
                result.add(reader.readLine());
            }

            return result;
        } catch (IOException e) {
            throw new ResourceReaderException("Error read csv file from resources");
        }
    }
}
