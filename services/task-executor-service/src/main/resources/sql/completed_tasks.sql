INSERT INTO completed_task (source_code, info_completed_task, count_successful_tests, count_failed_tests, count_all_tests, id_task, id_test, id_user)
VALUES ('public class App() {public static void main(String... args) {}}', 'Тестовый 1', 0, 1, 1, 1, 1, 1),
       ('public class App() {public static void main(String... args) {}}', 'Тестовый 2', 1, 0, 1, 2, 2, 1),
       ('public class App() {public static void main(String... args) {}}', 'Тестовый 3', 1, 1, 2, 3, 3, 1);
