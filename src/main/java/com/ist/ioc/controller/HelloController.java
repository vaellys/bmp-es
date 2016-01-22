package com.ist.ioc.controller;

import java.io.File;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.POITextExtractor;
import org.apache.poi.extractor.ExtractorFactory;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.xmlbeans.XmlException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

/**
 * <p>User: Mu Yunlong
 * <p>Date: 2016年1月8日 下午2:31:06
 * <p>Version: 1.0
 */
@Controller
@RequestMapping("/")
public class HelloController {
    private static final String UPLOADDIR = "uploadDir/";

    @RequestMapping("/")
    public String index() {
        return "/index";
    }

    @RequestMapping("check")
    public String check(HttpServletRequest request, HttpServletResponse response, PlagiarismCheckForm form) throws IOException {
        String doc1 = form.getDocA();
        String doc2 = form.getDocB();
        double result = 0.0;
        if (doc1 != null && doc2 != null) {
            result = SimilarDegree(doc1, doc2);
        }

        // System.out.println(Checker.getCorrelation(doc1, doc2));
        request.setAttribute("result", similarityResult(result));
        request.setAttribute("docA", doc1);
        request.setAttribute("docB", doc2);
        return "/index";
    }

    @RequestMapping("file")
    public String upload() {
        return "/upload";
    }

    @RequestMapping("upload")
    public String uploadDo(HttpServletRequest request, HttpServletResponse response) throws IllegalStateException, IOException, InvalidFormatException, OpenXML4JException, XmlException {
        MultipartHttpServletRequest mRequest = (MultipartHttpServletRequest) request;
        Map<String, MultipartFile> fileMap = mRequest.getFileMap();
        String uploadDir = request.getSession().getServletContext().getRealPath("/") + UPLOADDIR;
        
        String fileName = null;  
        int i = 0;  
        double result = 0.0;
        String docA = "";
        String docB = "";
        for (Iterator<Map.Entry<String, MultipartFile>> it = fileMap.entrySet()  
                .iterator(); it.hasNext(); i++) {  
            Map.Entry<String, MultipartFile> entry = it.next();  
            MultipartFile mFile = entry.getValue();  
            fileName = mFile.getOriginalFilename();  
            if (StringUtils.isNotEmpty(fileName)) {
                /** 获取文件的后缀* */
                String suffix =fileName.substring(
                        fileName.lastIndexOf("."));
                /** 使用UUID生成文件名称* */
                String UUIDName = UUID.randomUUID().toString() + suffix;// 构建文件名称
                File file = new File(uploadDir+UUIDName);
                if (!file.exists()) {
                    file.mkdirs();
                }
                mFile.transferTo(file);
                POITextExtractor extractor = ExtractorFactory.createExtractor(file); 
                if (i==0) {
                    docA = extractor.getText();
                }else{
                    docB = extractor.getText();
                }
            }
         
        }
        if (StringUtils.isNotEmpty(docA) && StringUtils.isNotEmpty(docB) ) {
            result = SimilarDegree(docA, docB);
        }
        request.setAttribute("result", similarityResult(result));
        return "/upload";
    }

    /** 
     * 相似度转百分比 
     */

    public static String similarityResult(double resule) {
        return NumberFormat.getPercentInstance(new Locale("en ", "US ")).format(resule);
    }

    /** 
     * 相似度比较 
     * @param strA 
     * @param strB 
     * @return 
     */

    public static double SimilarDegree(String strA, String strB) {
        String newStrA = removeSign(strA);
        String newStrB = removeSign(strB);
        int temp = Math.max(newStrA.length(), newStrB.length());
        int temp2 = longestCommonSubstring(newStrA, newStrB).length();
        return temp2 * 1.0 / temp;
    }

    private static String removeSign(String str) {
        StringBuffer sb = new StringBuffer();
        for (char item : str.toCharArray())
            if (charReg(item)) {
                // System.out.println("--"+item);
                sb.append(item);
            }
        return sb.toString();

    }

    private static boolean charReg(char charValue) {
        return (charValue >= 0x4E00 && charValue <= 0X9FA5) || (charValue >= 'a' && charValue <= 'z') || (charValue >= 'A' && charValue <= 'Z')
                || (charValue >= '0' && charValue <= '9');
    }

    private static String longestCommonSubstring(String strA, String strB) {
        char[] chars_strA = strA.toCharArray();
        char[] chars_strB = strB.toCharArray();
        int m = chars_strA.length;
        int n = chars_strB.length;
        int[][] matrix = new int[m + 1][n + 1];
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (chars_strA[i - 1] == chars_strB[j - 1])
                    matrix[i][j] = matrix[i - 1][j - 1] + 1;
                else
                    matrix[i][j] = Math.max(matrix[i][j - 1], matrix[i - 1][j]);
            }
        }

        char[] result = new char[matrix[m][n]];
        int currentIndex = result.length - 1;
        while (matrix[m][n] != 0) {
            if (m >= n && matrix[n] == matrix[n - 1])
                n--;
            else if (matrix[m][n] == matrix[m - 1][n])
                m--;
            else {
                result[currentIndex] = chars_strA[m - 1];
                currentIndex--;
                n--;
                m--;
            }
        }
        return new String(result);

    }
    
    public static void main(String[] args) {
        System.out.println(similarityResult(1.1314777098891948E-4));
    }
}
