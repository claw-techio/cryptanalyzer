public class BruteForce {

    public static void decrypt(String text, String outputPath) {
        StringBuilder allResults = new StringBuilder();

        for (int key = 1; key <= 25; key++) {
            String decrypted = CaesarCipher.decrypt(text, key);
            allResults.append("=== Ключ: ").append(key).append(" ===\n");
            allResults.append(decrypted).append("\n\n");
        }

        FileHandler.writeFile(outputPath, allResults.toString());
        System.out.println("Все варианты (ключи 1-25) записаны в файл.");
    }
}
