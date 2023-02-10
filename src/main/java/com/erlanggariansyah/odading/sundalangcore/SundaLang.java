package com.erlanggariansyah.odading.sundalangcore;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * SundaLang v1
 * 10/02/2023
 */
public class SundaLang {
    private Map<String, String> dictionary;
    private List<String> syntax;

    public SundaLang() {
        generateData();
    }

    public Map<String, String> getDictionary() {
        return dictionary;
    }

    public void setDictionary(Map<String, String> dictionary) {
        this.dictionary = dictionary;
    }

    public List<String> getSyntax() {
        return syntax;
    }

    public void setSyntax(List<String> syntax) {
        this.syntax = syntax;
    }

    public String convert(String code) throws Exception {
        for (Map.Entry<String, String> entry : dictionary.entrySet()) {
            code = code.replaceAll("(?i)\\b" + entry.getKey() + "\\b", entry.getValue());
        }

        code = code.trim().replaceAll(" +", " ");
        code = code.replaceAll("\\R", " ");
        code = code.replace(";", ";\n");

        return code;
    }

    public void generateJava(String code) throws Exception {
        File file = new File("SundaLangOutput.java");

        PrintWriter printWriter = new PrintWriter(file);
        printWriter.println("public class SundaLangOutput { public static void main(String[] args) { " + code + " } }");
        printWriter.close();
    }

    private void generateData() {
        dictionary = new LinkedHashMap<>();
        syntax = new ArrayList<>();

        // data types
        dictionary.put("NomerPangleutikna", "byte");
        dictionary.put("NomerLeutik", "short");
        dictionary.put("Nomer", "int");
        dictionary.put("NomerBadag", "long");
        dictionary.put("NomerFraksi", "float");
        dictionary.put("NomerFraksiBadag", "double");
        dictionary.put("Surat", "char");
        dictionary.put("Kecap", "String");

        // logical operator
        dictionary.put("lamun", "if");
        dictionary.put("iraha", "while");
        dictionary.put("lamunhenteu", "else");
        dictionary.put("lamundeui", "else if");

        // function
        dictionary.put("cetakEuy", "System.out.println");

        // access modifiers
        dictionary.put("jeungsorangan", "private");
        dictionary.put("jeungurang", "protected");
        dictionary.put("jeungkabehan", "public");

        // loops
        dictionary.put("pikeun", "for");

        // others
        dictionary.put("nyoba", "try");
        dictionary.put("tangkep", "catch");
        dictionary.put("tungtungna", "finally");
        dictionary.put("angger", "final");
        dictionary.put("baledog", "throw");
        dictionary.put("baledogkeun", "throws");

        dictionary.forEach((key, value) -> {
            syntax.add(key);
        });
    }
}
