package ru.otus.spring.homework.model;

import java.util.List;

public record StudentQuizResult(
        Student student,
        List<QuizResult> quizResult,
        int countStudentSuccessAnswers,
        int minimalSuccessAnswers) {
}
