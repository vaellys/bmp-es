package com.ist.common.es.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.lucene.util.IntsRef;

public class SimilarityUtils {
    private SimilarityUtils() {
    }

    public static int ld(String s, String t) {
        int d[][];
        int sLen = s.length();
        int tLen = t.length();
        int si;
        int ti;
        char ch1;
        char ch2;
        int cost;
        if (sLen == 0) {
            return tLen;
        }
        if (tLen == 0) {
            return sLen;
        }
        d = new int[sLen + 1][tLen + 1];
        for (si = 0; si <= sLen; si++) {
            d[si][0] = si;
        }
        for (ti = 0; ti <= tLen; ti++) {
            d[0][ti] = ti;
        }
        for (si = 1; si <= sLen; si++) {
            ch1 = s.charAt(si - 1);
            for (ti = 1; ti <= tLen; ti++) {
                ch2 = t.charAt(ti - 1);
                if (ch1 == ch2) {
                    cost = 0;
                } else {
                    cost = 1;
                }
                d[si][ti] = Math.min(Math.min(d[si - 1][ti] + 1, d[si][ti - 1] + 1), d[si - 1][ti - 1] + cost);
            }
        }
        return d[sLen][tLen];
    }

    public static double similarity(String src, String tar) {
        int ld = ld(src, tar, false);
        return 1 - (double) ld / Math.max(src.length(), tar.length());
    }

    public static boolean isChinese(String str) {
        boolean temp = false;
        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher m = p.matcher(str);
        if (m.find()) {
            temp = true;
        }
        return temp;
    }

    public static int ld(String target, String other, boolean allowTransposition) {
        IntsRef targetPoints;
        IntsRef otherPoints;
        int n;
        int d[][]; // cost array

        // NOTE: if we cared, we could 3*m space instead of m*n space, similar
        // to
        // what LevenshteinDistance does, except cycling thru a ring of three
        // horizontal cost arrays... but this comparator is never actually used
        // by
        // DirectSpellChecker, it's only used for merging results from multiple
        // shards
        // in "distributed spellcheck", and it's inefficient in other ways
        // too...

        // cheaper to do this up front once
        targetPoints = toIntsRef(target);
        otherPoints = toIntsRef(other);
        n = targetPoints.length;
        final int m = otherPoints.length;
        d = new int[n + 1][m + 1];

        if (n == 0 || m == 0) {
            if (n == m) {
                return 0;
            } else {
                return Math.max(n, m);
            }
        }

        // indexes into strings s and t
        int i; // iterates through s
        int j; // iterates through t
        int t_j; // jth character of t
        int cost; // cost
        for (i = 0; i <= n; i++) {
            d[i][0] = i;
        }
        for (j = 0; j <= m; j++) {
            d[0][j] = j;
        }
        for (j = 1; j <= m; j++) {
            t_j = otherPoints.ints[j - 1];

            for (i = 1; i <= n; i++) {
                cost = targetPoints.ints[i - 1] == t_j ? 0 : 1;
                // minimum of cell to the left+1, to the top+1, diagonally left
                // and up +cost
                d[i][j] = Math.min(Math.min(d[i - 1][j] + 1, d[i][j - 1] + 1), d[i - 1][j - 1] + cost);
                // transposition
                if (allowTransposition && i > 1 && j > 1 && targetPoints.ints[i - 1] == otherPoints.ints[j - 2]
                        && targetPoints.ints[i - 2] == otherPoints.ints[j - 1]) {
                    d[i][j] = Math.min(d[i][j], d[i - 2][j - 2] + cost);
                }
            }
        }
        return d[n][m];
    }

    private static IntsRef toIntsRef(String s) {
        IntsRef ref = new IntsRef(s.length()); // worst case
        int utf16Len = s.length();
        for (int i = 0, cp = 0; i < utf16Len; i += Character.charCount(cp)) {
            cp = ref.ints[ref.length++] = Character.codePointAt(s, i);
        }
        return ref;
    }

    public static void main(String[] args) {
        String src = "中国";
        String tar = "您好中国哈哈";
        // String src = "hello world!";
        // String tar = "hello";
        System.out.println("sim=" + SimilarityUtils.similarity(src, tar));
    }
}
