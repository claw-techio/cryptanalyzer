import java.util.HashMap;
import java.util.Map;

public class StatisticalAnalyzer {

    // Частоты букв в русском языке (%)
    private static final Map<Character, Double> RU_FREQ = new HashMap<>();
    // Частоты букв в английском языке (%)
    private static final Map<Character, Double> EN_FREQ = new HashMap<>();

    static {
        // Русский язык
        double[] ruFreqs = {8.01, 1.59, 4.54, 1.70, 2.98, 8.45, 0.04, 0.94, 1.65, 7.35,
                0.12, 3.49, 4.40, 3.21, 6.70, 10.97, 2.81, 4.73, 5.47, 6.26,
                2.62, 0.26, 0.97, 0.48, 1.44, 0.73, 0.04, 1.90, 1.74, 3.32, 0.64, 0.20};
        String ruAlpha = "абвгдеёжзийклмнопрстуфхцчшщъыьэюя";
        for (int i = 0; i < ruAlpha.length(); i++) {
            RU_FREQ.put(ruAlpha.charAt(i), ruFreqs[i]);
        }

        // Английский язык
        double[] enFreqs = {8.17, 1.49, 2.78, 4.25, 12.70, 2.23, 2.02, 6.09, 6.97, 0.15,
                0.77, 4.03, 2.41, 6.75, 7.51, 1.93, 0.10, 5.99, 6.33, 9.06,
                2.76, 0.98, 2.36, 0.15, 1.97, 0.07};
        for (int i = 0; i < 26; i++) {
            EN_FREQ.put((char) ('a' + i), enFreqs[i]);
        }
    }

    public static int findKey(String text) {
        String lower = text.toLowerCase();
        boolean isRussian = isRussianText(lower);

        if (isRussian) {
            return findKeyRussian(lower);
        } else {
            return findKeyEnglish(lower);
        }
    }

    private static boolean isRussianText(String text) {
        long ruCount = text.chars().filter(c -> c >= 'а' && c <= 'я').count();
        long enCount = text.chars().filter(c -> c >= 'a' && c <= 'z').count();
        return ruCount >= enCount;
    }

    private static int findKeyEnglish(String text) {
        int[] count = new int[26];
        int total = 0;

        for (char c : text.toCharArray()) {
            if (c >= 'a' && c <= 'z') {
                count[c - 'a']++;
                total++;
            }
        }

        if (total == 0) return 1;

        double bestScore = Double.MAX_VALUE;
        int bestKey = 1;

        for (int key = 1; key <= 25; key++) {
            double score = 0;
            for (int i = 0; i < 26; i++) {
                int shifted = (i - key + 26) % 26;
                double observed = (double) count[i] / total * 100;
                double expected = EN_FREQ.getOrDefault((char) ('a' + shifted), 0.0);
                score += Math.pow(observed - expected, 2);
            }
            if (score < bestScore) {
                bestScore = score;
                bestKey = key;
            }
        }

        return bestKey;
    }

    private static int findKeyRussian(String text) {
        String ruAlpha = "абвгдеёжзийклмнопрстуфхцчшщъыьэюя";
        int size = ruAlpha.length();
        int[] count = new int[size];
        int total = 0;

        for (char c : text.toCharArray()) {
            int idx = ruAlpha.indexOf(c);
            if (idx >= 0) {
                count[idx]++;
                total++;
            }
        }

        if (total == 0) return 1;

        double bestScore = Double.MAX_VALUE;
        int bestKey = 1;

        for (int key = 1; key < size; key++) {
            double score = 0;
            for (int i = 0; i < size; i++) {
                int shifted = (i - key + size) % size;
                double observed = (double) count[i] / total * 100;
                double expected = RU_FREQ.getOrDefault(ruAlpha.charAt(shifted), 0.0);
                score += Math.pow(observed - expected, 2);
            }
            if (score < bestScore) {
                bestScore = score;
                bestKey = key;
            }
        }

        return bestKey;
    }
}
