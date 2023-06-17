package ru.otus.spring.homework.model;

public record Answer(boolean isRight, String answer) {

    @Override
    public String toString() {

        return """
                answer: %s
                isRight: %s
                """.formatted(answer, isRight);
    }
}
