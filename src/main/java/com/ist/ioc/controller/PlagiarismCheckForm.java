package com.ist.ioc.controller;

import java.io.File;

public class PlagiarismCheckForm  {
    
    private String docA;
    private String docB;
    private File fileA;
    private File fileB;
    
    
    public File getFileA() {
        return fileA;
    }
    public void setFileA(File fileA) {
        this.fileA = fileA;
    }
    public File getFileB() {
        return fileB;
    }
    public void setFileB(File fileB) {
        this.fileB = fileB;
    }
    public String getDocA() {
        return docA;
    }
    public void setDocA(String docA) {
        this.docA = docA;
    }
    public String getDocB() {
        return docB;
    }
    public void setDocB(String docB) {
        this.docB = docB;
    }
    
}