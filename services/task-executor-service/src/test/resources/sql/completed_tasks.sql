INSERT INTO completed_task (source_code, was_successful, count_successful_tests, count_failed_tests, count_all_tests, id_task, id_test, id_user)
VALUES ('public class App() {public static void main(String... args) {}}', 0, 0, 1, 1, 1, 1, 1),
       ('public class App() {public static void main(String... args) {}}', 1, 1, 0, 1, 2, 2, 1),
       ('public class App() {public static void main(String... args) {}}', 0, 1, 1, 2, 3, 3, 1);
