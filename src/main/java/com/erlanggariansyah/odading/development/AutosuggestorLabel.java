//package com.erlanggariansyah.odading.development;
//
//import com.erlanggariansyah.odading.development.Autosuggestor;
//
//import javax.swing.*;
//import javax.swing.border.LineBorder;
//import javax.swing.text.JTextComponent;
//import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.KeyEvent;
//import java.awt.event.MouseAdapter;
//import java.awt.event.MouseEvent;
//
//public class AutosuggestorLabel extends JLabel {
//    private boolean focused = false;
//    private final JWindow autoSuggestionsPopUpWindow;
//    private final JTextComponent textComponent;
//    private final Autosuggestor autoSuggestor;
//    private final Color suggestionsTextColor;
//    private final Color suggestionBorderColor;
//
//    public AutosuggestorLabel(String string, final Color borderColor, Color suggestionsTextColor, Autosuggestor autoSuggestor) {
//        super(string);
//
//        this.suggestionsTextColor = suggestionsTextColor;
//        this.autoSuggestor = autoSuggestor;
//        this.textComponent = autoSuggestor.getTextField();
//        this.suggestionBorderColor = borderColor;
//        this.autoSuggestionsPopUpWindow = autoSuggestor.getAutoSuggestionPopUpWindow();
//
//        initComponent();
//    }
//
//    private void initComponent() {
//        setFocusable(true);
//        setForeground(suggestionsTextColor);
//
//        addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseClicked(MouseEvent me) {
//                super.mouseClicked(me);
//
//                replaceWithSuggestedText();
//
//                autoSuggestionsPopUpWindow.setVisible(false);
//            }
//        });
//
//        getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, true), "Enter released");
//        getActionMap().put("Enter released", new AbstractAction() {
//            @Override
//            public void actionPerformed(ActionEvent ae) {
//                replaceWithSuggestedText();
//                autoSuggestionsPopUpWindow.setVisible(false);
//            }
//        });
//    }
//
//    public void setFocused(boolean focused) {
//        if (focused) {
//            setBorder(new LineBorder(suggestionBorderColor));
//        } else {
//            setBorder(null);
//        }
//
//        repaint();
//        this.focused = focused;
//    }
//
//    public boolean isFocused() {
//        return focused;
//    }
//
//    private void replaceWithSuggestedText() {
//        String suggestedWord = getText();
//        String text = textComponent.getText();
//        String typedWord = autoSuggestor.getCurrentlyTypedWord();
//        String t = text.substring(0, text.lastIndexOf(typedWord));
//        String tmp = t + text.substring(text.lastIndexOf(typedWord)).replace(typedWord, suggestedWord);
//        textComponent.setText(tmp + " ");
//    }
//}
