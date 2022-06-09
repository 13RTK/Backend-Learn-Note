public class IndexMatch {
    public static void main(String[] args) {
        int res = index("goodgoogle", "google");
        System.out.println(res);
    }

    public static int index(String targetStr, String patternStr) {
        char[] targetChars = targetStr.toCharArray();
        char[] patternChars = patternStr.toCharArray();
        int patternIdx = 0;

        for (int targetIdx = 0; targetIdx < targetChars.length; targetIdx++) {
            for (; patternIdx < patternChars.length; patternIdx++) {
                if (targetChars[targetIdx] != patternChars[patternIdx]) {
                    break;
                }

            }

            if (patternIdx == patternChars.length) {
                return targetIdx;
            }
        }

        return -1;
    }
}