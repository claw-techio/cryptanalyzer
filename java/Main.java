import java.util.Scanner;

    public class Main {
        public static void main(String[] args) {
            Scanner scanner = new Scanner(System.in);

            while (true) {
                System.out.println("\n=== Caesar Cipher Tool ===");
                System.out.println("1. Шифрование текста");
                System.out.println("2. Расшифровка с известным ключом");
                System.out.println("3. Расшифровка методом Brute Force");
                System.out.println("4. Расшифровка методом статистического анализа");
                System.out.println("0. Выход");
                System.out.print("Выберите режим: ");

                int choice;
                try {
                    choice = Integer.parseInt(scanner.nextLine().trim());
                } catch (NumberFormatException e) {
                    System.out.println("Ошибка: введите число.");
                    continue;
                }

                if (choice == 0) {
                    System.out.println("До свидания!");
                    break;
                }

                System.out.print("Введите путь к входному файлу: ");
                String inputPath = scanner.nextLine().trim();

                if (!FileHandler.fileExists(inputPath)) {
                    System.out.println("Ошибка: файл не найден — " + inputPath);
                    continue;
                }

                System.out.print("Введите путь к выходному файлу: ");
                String outputPath = scanner.nextLine().trim();

                String text = FileHandler.readFile(inputPath);
                if (text == null) {
                    System.out.println("Ошибка при чтении файла.");
                    continue;
                }

                switch (choice) {
                    case 1 -> {
                        int key = readKey(scanner);
                        if (key == -1) continue;
                        String encrypted = CaesarCipher.encrypt(text, key);
                        FileHandler.writeFile(outputPath, encrypted);
                        System.out.println("Файл зашифрован и сохранён: " + outputPath);
                    }
                    case 2 -> {
                        int key = readKey(scanner);
                        if (key == -1) continue;
                        String decrypted = CaesarCipher.decrypt(text, key);
                        FileHandler.writeFile(outputPath, decrypted);
                        System.out.println("Файл расшифрован и сохранён: " + outputPath);
                    }
                    case 3 -> {
                        BruteForce.decrypt(text, outputPath);
                        System.out.println("Результаты brute force сохранены: " + outputPath);
                    }
                    case 4 -> {
                        int key = StatisticalAnalyzer.findKey(text);
                        System.out.println("Найденный ключ: " + key);
                        String decrypted = CaesarCipher.decrypt(text, key);
                        FileHandler.writeFile(outputPath, decrypted);
                        System.out.println("Файл расшифрован и сохранён: " + outputPath);
                    }
                    default -> System.out.println("Неверный выбор.");
                }
            }

            scanner.close();
        }

        private static int readKey(Scanner scanner) {
            System.out.print("Введите ключ (сдвиг, 1-25): ");
            try {
                int key = Integer.parseInt(scanner.nextLine().trim());
                if (key < 1 || key > 25) {
                    System.out.println("Ошибка: ключ должен быть от 1 до 25.");
                    return -1;
                }
                return key;
            } catch (NumberFormatException e) {
                System.out.println("Ошибка: введите целое число.");
                return -1;
            }
        }
    }
