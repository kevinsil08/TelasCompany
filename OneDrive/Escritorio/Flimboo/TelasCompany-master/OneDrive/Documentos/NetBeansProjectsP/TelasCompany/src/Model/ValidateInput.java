/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 *
 * @author natha
 */
public class ValidateInput {

    private String text;

    public ValidateInput(String text) {
        this.text = text;
    }

    public ValidateInput() {
        
    }

    public boolean validatePhoneNumber() {
        // Ecuador local number
        if (text.matches("09[1-9]{8}")) {
            return true;
        }
        if (text.matches("[1-9]{1}[0-9]{6}")) {
            return true;
        }
        if (text.matches("(0[2-7]\\d{7})")) {
            return true;
        }
        if (text.length() == 0) {
            setText(null);
            return true;
        }
        return false;
    }

    public boolean moneyNumber() {
        return text.matches("([+]?\\d+)|(([+]?\\d+)\\.(\\d{1,2}))");
    }

    public boolean names() {
        return text.matches("([A-ZZÁÉÍÓÚ][a-zñáéíóú]+(\\s)?){1,3}");
    }

    public boolean textArea() {
        return text.matches("([A-ZZÁÉÍÓÚ][a-zñáéíóú]+(\\s)?){1,}");
    }

    public boolean name_lastNames() {
        return text.matches("([A-ZZÁÉÍÓÚ][a-zñáéíóú]+)");
    }

    public boolean numbers() {
        return text.matches("\\d{1,}");
    }

    public boolean firstDateBeforeSecondDate(String firstDate, String secondDate) {
        SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date dateFirst = sdformat.parse(firstDate);
            Date dateSecond = sdformat.parse(secondDate);
            if (dateFirst.compareTo(dateSecond) < 0 || dateFirst.compareTo(dateSecond) == 0) {
                return true;
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return false;
    }
    
    private  LocalDate toDate (String dateString){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(dateString, formatter);
    return localDate;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}

