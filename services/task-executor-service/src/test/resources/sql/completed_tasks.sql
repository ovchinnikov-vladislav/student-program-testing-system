INSERT INTO completed_task (id_completed_task, source_code, was_successful, count_successful_tests, count_failed_tests, count_all_tests, id_task, id_test, id_user)
VALUES (random_uuid(), 'public class App() {public static void main(String... args) {}}', 0, 0, 1, 1, random_uuid(), random_uuid(), random_uuid()),
       (random_uuid(), 'public class App() {public static void main(String... args) {}}', 1, 1, 0, 1, random_uuid(), random_uuid(), random_uuid()),
       (random_uuid(), 'public class App() {public static void main(String... args) {}}', 0, 1, 1, 2, random_uuid(), random_uuid(), random_uuid());
