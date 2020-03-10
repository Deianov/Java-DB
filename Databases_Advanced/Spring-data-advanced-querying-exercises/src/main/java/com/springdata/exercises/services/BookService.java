package com.springdata.exercises.services;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public interface BookService {
    String[] resultTask1(String input);
    String[] resultTask2(int maxCopies);
    String[] resultTask3(BigDecimal lowerThan, BigDecimal higherThan);
    String[] resultTask4(LocalDate before, LocalDate after);
    String[] resultTask5(LocalDate releaseDate);
    String[] resultTask7(String input);
    String[] resultTask8(String input);
    String resultTask9(int minLength);
    String resultTask11(String title);
    String resultTask12(LocalDate releaseDate, int copiesToAdd, DateTimeFormatter formatter);
    String resultTask13(int minCopies);
    String resultTask14(String firstName, String lastName);
}
