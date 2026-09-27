package ua.edu.crossplatform.practical1;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Locale;

public final class PlatformInfo {

    private PlatformInfo() {}

    public static void main(String[] args) {
        if (args.length == 0) {
            System.err.println("Помилка: Ви не вказали ім'я студента!");
            System.err.println("Запускайте програму так: java -jar platform-info.jar \"Ім'я Прізвище\"");
            System.exit(1);
        }

        String student = String.join(" ", args);
        String report = buildReport(student);

        System.out.print(report);

        try {
            Path reportDir = Path.of("reports");
            Files.createDirectories(reportDir);

            Path reportFile = reportDir.resolve("platform-info.txt");
            Files.writeString(reportFile, report, StandardCharsets.UTF_8);

            System.out.println("\nЗвіт збережено у файл:");
            System.out.println(reportFile.toAbsolutePath().normalize());
        } catch (IOException e) {
            System.err.println("Помилка під час запису файлу: " + e.getMessage());
        }
    }

    private static String buildReport(String student) {
        return """
               Студент: %s
               Версія Java: %s
               Постачальник JDK: %s
               Операційна система: %s
               Версія ОС: %s
               Архітектура: %s
               Файловий роздільник: %s
               Кодування за замовчуванням: %s
               Локаль за замовчуванням: %s
               Кількість процесорів: %s
               Робочий каталог: %s
               """.formatted(
                student,
                Runtime.version(),
                System.getProperty("java.vendor"),
                System.getProperty("os.name"),
                System.getProperty("os.version"),
                System.getProperty("os.arch"),
                System.getProperty("file.separator"),
                Charset.defaultCharset(),
                Locale.getDefault(),
                Runtime.getRuntime().availableProcessors(),
                Path.of("").toAbsolutePath().normalize()
        );
    }
}