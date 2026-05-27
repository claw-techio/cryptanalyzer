public class CaesarCipher {

    private static final String RUSSIAN_UPPER = "АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ";
    private static final String RUSSIAN_LOWER = "абвгдеёжзийклмнопрстуфхцчшщъыьэюя";
    private static final int RU_SIZE = RUSSIAN_UPPER.length();

    public static String encrypt(String text, int key) {
        return processText(text, key);
    }

    public static String decrypt(String text, int key) {
        return processText(text, -key);
    }

    private static String processText(String text, int shift) {
        StringBuilder result = new StringBuilder(text.length());

        for (char c : text.toCharArray()) {
            if (c >= 'A' && c <= 'Z') {
                result.append((char) ('A' + ((c - 'A' + shift % 26 + 26) % 26)));
            } else if (c >= 'a' && c <= 'z') {
                result.append((char) ('a' + ((c - 'a' + shift % 26 + 26) % 26)));
            } else {
                int idxUpper = RUSSIAN_UPPER.indexOf(c);
                int idxLower = RUSSIAN_LOWER.indexOf(c);
                if (idxUpper >= 0) {
                    result.append(RUSSIAN_UPPER.charAt(((idxUpper + shift) % RU_SIZE + RU_SIZE) % RU_SIZE));
                } else if (idxLower >= 0) {
                    result.append(RUSSIAN_LOWER.charAt(((idxLower + shift) % RU_SIZE + RU_SIZE) % RU_SIZE));
                } else {
                    result.append(c);
                }
            }
        }

        return result.toString();
    }
}
