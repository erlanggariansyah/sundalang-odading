//package com.erlanggariansyah.odading.development;
//
//import javax.swing.*;
//import javax.swing.event.DocumentEvent;
//import javax.swing.event.DocumentListener;
//import javax.swing.text.JTextComponent;
//import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.KeyEvent;
//import java.util.ArrayList;
//import java.util.List;
//
//public class Autosuggestor {
//    private final JTextComponent textComp;
//    private final Window container;
//    private final JPanel suggestionsPanel;
//    private final JWindow autoSuggestionPopUpWindow;
//    private String typedWord;
//    private final List<String> dictionary = new ArrayList<>();
//    private int currentIndexOfSpace, tW, tH;
//    private final Color suggestionsTextColor;
//    private final Color suggestionFocusedColor;
//
//    public Autosuggestor(JTextComponent textComp, Window mainWindow, List<String> words, Color popUpBackground, Color textColor, Color suggestionFocusedColor, float opacity) {
//        this.textComp = textComp;
//        this.suggestionsTextColor = textColor;
//        this.container = mainWindow;
//        this.suggestionFocusedColor = suggestionFocusedColor;
//
//        DocumentListener documentListener = new DocumentListener() {
//            @Override
//            public void insertUpdate(DocumentEvent de) {
//                checkForAndShowSuggestions();
//            }
//
//            @Override
//            public void removeUpdate(DocumentEvent de) {
//                checkForAndShowSuggestions();
//            }
//
//            @Override
//            public void changedUpdate(DocumentEvent de) {
//                checkForAndShowSuggestions();
//            }
//        };
//
//        this.textComp.getDocument().addDocumentListener(documentListener);
//
//        setDictionary(words);
//
//        typedWord = "";
//        currentIndexOfSpace = 0;
//        tW = 0;
//        tH = 0;
//
//        autoSuggestionPopUpWindow = new JWindow(mainWindow);
//        autoSuggestionPopUpWindow.setOpacity(opacity);
//
//        suggestionsPanel = new JPanel();
//        suggestionsPanel.setLayout(new GridLayout(0, 1));
//        suggestionsPanel.setBackground(popUpBackground);
//
//        addKeyBindingToRequestFocusInPopUpWindow();
//    }
//
//    private void addKeyBindingToRequestFocusInPopUpWindow() {
//        textComp.getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0, true), "Down released");
//        textComp.getActionMap().put("Down released", new AbstractAction() {
//            @Override
//            public void actionPerformed(ActionEvent ae) {
//                // Fokus ke label pertama pada suggestion
//                for (int i = 0; i < suggestionsPanel.getComponentCount(); i++) {
//                    if (suggestionsPanel.getComponent(i) instanceof AutosuggestorLabel) {
//                        ((AutosuggestorLabel) suggestionsPanel.getComponent(i)).setFocused(true);
//                        autoSuggestionPopUpWindow.toFront();
//                        autoSuggestionPopUpWindow.requestFocusInWindow();
//                        suggestionsPanel.requestFocusInWindow();
//                        suggestionsPanel.getComponent(i).requestFocusInWindow();
//                        break;
//                    }
//                }
//            }
//        });
//
//        suggestionsPanel.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0, true), "Down released");
//        suggestionsPanel.getActionMap().put("Down released", new AbstractAction() {
//            int lastFocusableIndex = 0;
//
//            @Override
//            public void actionPerformed(ActionEvent ae) {
//                // Scroll pada label suggestion
//                ArrayList<AutosuggestorLabel> sls = getAddedSuggestionLabels();
//                int max = sls.size();
//
//                // lebih dari 1 suggestion
//                if (max > 1) {
//                    for (int i = 0; i < max; i++) {
//                        AutosuggestorLabel sl = sls.get(i);
//                        if (sl.isFocused()) {
//                            if (lastFocusableIndex == max - 1) {
//                                lastFocusableIndex = 0;
//                                sl.setFocused(false);
//                                autoSuggestionPopUpWindow.setVisible(false);
//                                setFocusToTextField();
//
//                                checkForAndShowSuggestions();
//                            } else {
//                                sl.setFocused(false);
//                                lastFocusableIndex = i;
//                            }
//                        } else if (lastFocusableIndex <= i) {
//                            sl.setFocused(true);
//                            autoSuggestionPopUpWindow.toFront();
//                            autoSuggestionPopUpWindow.requestFocusInWindow();
//                            suggestionsPanel.requestFocusInWindow();
//                            suggestionsPanel.getComponent(i).requestFocusInWindow();
//                            lastFocusableIndex = i;
//
//                            break;
//                        }
//                    }
//                } else {
//                    // hanya ada satu suggestion
//                    autoSuggestionPopUpWindow.setVisible(false);
//                    setFocusToTextField();
//
//                    checkForAndShowSuggestions();
//                }
//            }
//        });
//    }
//
//    private void setFocusToTextField() {
//        container.toFront();
//        container.requestFocusInWindow();
//        textComp.requestFocusInWindow();
//    }
//
//    public ArrayList<AutosuggestorLabel> getAddedSuggestionLabels() {
//        ArrayList<AutosuggestorLabel> sls = new ArrayList<>();
//        for (int i = 0; i < suggestionsPanel.getComponentCount(); i++) {
//            if (suggestionsPanel.getComponent(i) instanceof AutosuggestorLabel sl) {
//                sls.add(sl);
//            }
//        }
//
//        return sls;
//    }
//
//    private void checkForAndShowSuggestions() {
//        typedWord = getCurrentlyTypedWord();
//
//        // menghapus kata/jpanel yang sudah ada
//        suggestionsPanel.removeAll();
//
//        // premises kalkuasi terhadap size dari jwindow
//        tW = 0;
//        tH = 0;
//
//        boolean added = wordTyped(typedWord);
//
//        if (!added) {
//            if (autoSuggestionPopUpWindow.isVisible()) {
//                autoSuggestionPopUpWindow.setVisible(false);
//            }
//        } else {
//            showPopUpWindow();
//            setFocusToTextField();
//        }
//    }
//
//    protected void addWordToSuggestions(String word) {
//        AutosuggestorLabel suggestionLabel = new AutosuggestorLabel(word, suggestionFocusedColor, suggestionsTextColor, this);
//        calculatePopUpWindowSize(suggestionLabel);
//        suggestionsPanel.add(suggestionLabel);
//    }
//
//    public String getCurrentlyTypedWord() {
//        //get newest word after last white spaceif any or the first word if no white spaces
//        String text = textComp.getText();
//        String wordBeingTyped = "";
//        text = text.replaceAll("(\\r|\\n)", " ");
//
//        if (text.contains(" ")) {
//            int tmp = text.lastIndexOf(" ");
//            if (tmp >= currentIndexOfSpace) {
//                currentIndexOfSpace = tmp;
//                wordBeingTyped = text.substring(text.lastIndexOf(" "));
//            }
//        } else {
//            wordBeingTyped = text;
//        }
//
//        return wordBeingTyped.trim();
//    }
//
//    private void calculatePopUpWindowSize(JLabel label) {
//        //so we can size the JWindow correctly
//        if (tW < label.getPreferredSize().width) {
//            tW = label.getPreferredSize().width;
//        }
//
//        tH += label.getPreferredSize().height;
//    }
//
//    private void showPopUpWindow() {
//        autoSuggestionPopUpWindow.getContentPane().add(suggestionsPanel);
//        autoSuggestionPopUpWindow.setMinimumSize(new Dimension(textComp.getWidth(), 30));
////        autoSuggestionPopUpWindow.setSize(textComp.getWidth(), tH);
//        autoSuggestionPopUpWindow.setVisible(true);
//
//        int windowX = 0;
//        int windowY = 0;
//
//        windowX = container.getX() + textComp.getX() + 5;
//        if (suggestionsPanel.getHeight() > autoSuggestionPopUpWindow.getMinimumSize().height) {
//            windowY = container.getY() + textComp.getY() + textComp.getHeight() + autoSuggestionPopUpWindow.getMinimumSize().height;
//        } else {
//            windowY = container.getY() + textComp.getY() + textComp.getHeight() + autoSuggestionPopUpWindow.getHeight();
//        }
//
//        // Menampilkan pop up suggestion
//        autoSuggestionPopUpWindow.setLocation(windowX, windowY);
//        autoSuggestionPopUpWindow.setMinimumSize(new Dimension(textComp.getWidth(), 30));
//        autoSuggestionPopUpWindow.revalidate();
//        autoSuggestionPopUpWindow.repaint();
//    }
//
//    public void setDictionary(List<String> words) {
//        dictionary.clear();
//
//        // Membuat nullable argumen dictionary pada constructor tanpa harus melakukan catch exception
//        if (words == null) {
//            return;
//        }
//
//        dictionary.addAll(words);
//    }
//
//    public JWindow getAutoSuggestionPopUpWindow() {
//        return autoSuggestionPopUpWindow;
//    }
//
//    public Window getContainer() {
//        return container;
//    }
//
//    public JTextComponent getTextField() {
//        return textComp;
//    }
//
//    public void addToDictionary(String word) {
//        dictionary.add(word);
//    }
//
//    boolean wordTyped(String typedWord) {
//        if (typedWord.isEmpty()) {
//            return false;
//        }
//
//        boolean suggestionAdded = false;
//
//        // Mengambil data dictionary
//        for (String word : dictionary) {
//            boolean fullymatches = true;
//
//            // Untuk setiap karakter dalam word
//            for (int i = 0; i < typedWord.length(); i++) {
//                if (!typedWord.toLowerCase().startsWith(String.valueOf(word.toLowerCase().charAt(i)), i)) {//check for match
//                    fullymatches = false;
//
//                    break;
//                }
//            }
//
//            if (fullymatches) {
//                addWordToSuggestions(word);
//                suggestionAdded = true;
//            }
//        }
//
//        return suggestionAdded;
//    }
//}
