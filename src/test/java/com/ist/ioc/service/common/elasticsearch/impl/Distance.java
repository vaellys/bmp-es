package com.ist.ioc.service.common.elasticsearch.impl;

public class Distance {
    public static void main(String[]args){
        int a=new Distance().getDistance("牟云龙".toCharArray(),"牟云风我是".toCharArray());
        System.out.println(a);
    }
    public int getDistance(char[] str1,char[] str2){
        
         int n = str1.length;
         int m = str2.length;
         int[][] C = new int[n+1][m+1];
         int i, j, x, y, z;
         for (i = 0; i <= n; i++)
             C[i][0] = i;
         for (i = 1; i <= m; i++)
             C[0] [i] = i;
         for (i = 0; i < n; i++)
             for (j = 0; j < m; j++)
             {
                 x = C[i][j + 1] + 1;
                 y = C[i + 1][ j] + 1;
                 if (str1[i] == str2[j])
                     z = C[i][ j];
                 else
                     z = C[i][ j] + 1;
                 
                 C[i + 1][ j + 1] = Math.min(Math.min(x, y), z);
             }
         return C[n][ m];
     }
}
