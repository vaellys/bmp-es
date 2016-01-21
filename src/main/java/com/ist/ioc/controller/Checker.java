package com.ist.ioc.controller;



import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * @author luodi
 */
public class Checker {
    
    public static double getCorrelation(String a, String b){
        if (a == null || b == null || a.length() == 0 || b.length() == 0) {
            return 0;
        }
        int[][] m = getTrackingMatrix(a, b);
        return (double) m[a.length()][b.length()] / 
        (a.length() < b.length() ? a.length() : b.length());
    }

    public static String getLcs(String a, String b){
        int[][] m = getTrackingMatrix(a, b);
        return getLcs(m, a, b).toString();
    }
    
    private static StringBuilder getLcs(int[][] m, String a, String b){
        return backtrack(m, a, b, a.length(), b.length());
    }
    
    public static List<String> getAllLcs(String a, String b){
        int[][] m = getTrackingMatrix(a, b);
        List<String> list = new ArrayList<String>();
        list.addAll(getAllLcs(m, a, b));
        return list;
    }
    
    private static Set<String> getAllLcs(int[][] m, String a, String b) {
        Set<String> set = new HashSet<String>();
        List<StringBuilder> l = backtrackAll(m, a, b, a.length(), b.length());
        for (StringBuilder sb : l) {
            set.add(sb.toString());
        }
        return set;
    }
    
    private static int[][] getTrackingMatrix(String a, String b) {
        int[][] m = new int[a.length() + 1][b.length() + 1];
        for (int i = 1; i < m.length; i++) {
            for (int j = 1; j < m[i].length; j++) {
                if (a.charAt(i - 1) == b.charAt(j - 1)) {
                    m[i][j] = m[i - 1][j - 1] + 1;
                } else if (m[i - 1][j] >= m[i][j - 1]) {
                    m[i][j] = m[i - 1][j];
                } else {
                    m[i][j] = m[i][j - 1];
                }
            }
        }
        return m;
    }
    
    private static List<StringBuilder> backtrackAll(int[][] m, String a, String b, int i, int j) {
        if (i == 0 || j == 0) {
            List<StringBuilder> r = new LinkedList<StringBuilder>();
            r.add(new StringBuilder(""));
            return r;
        }
        if (a.charAt(i - 1) == b.charAt(j - 1)) {
            List<StringBuilder> r = backtrackAll(m, a, b, i - 1, j - 1);
            for (StringBuilder s : r) {
                s.append(a.charAt(i - 1));
            }
            return r;
        } else {
            List<StringBuilder> r = new LinkedList<StringBuilder>();
            if (m[i - 1][j] >= m[i][j - 1]) {
                r = backtrackAll(m, a, b, i - 1, j);
            }
            if (m[i][j - 1] >= m[i - 1][j]) {
                r.addAll(backtrackAll(m, a, b, i, j - 1));
            }
            return r;
        } 
    }
    
    private static StringBuilder backtrack(int[][] m, String a, String b, int i, int j) {
        if (i == 0 || j == 0) {
            return new StringBuilder("");
        }
        if (a.charAt(i - 1) == b.charAt(j - 1)) {
            return backtrack(m, a, b, i - 1, j - 1).append(a.charAt(i - 1));
        } else if (m[i - 1][j] >= m[i][j - 1]) {
            return backtrack(m, a, b, i - 1, j);
        } else {
            return backtrack(m, a, b, i, j - 1);
        } 
    }    
}
