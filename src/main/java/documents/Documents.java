package documents;

import lombok.extern.log4j.Log4j2;

import javax.swing.text.MaskFormatter;
import java.util.ArrayList;
import java.util.InputMismatchException;

import static sun.util.calendar.CalendarUtils.mod;

/**
 * @author Rubens Lobo
 * @since 17/07/2018
 */
@Log4j2
public class Documents {

    private ArrayList<Integer> listaAleatoria = new ArrayList<Integer>();
    private ArrayList<Integer> listaNumMultiplicados = null;


    private int geraNumAleatorio(){
        int numero = (int) (Math.random() * 10);
        return numero;
    }

    private ArrayList<Integer> geraPrimeiroDigito(){
        listaNumMultiplicados = new ArrayList<Integer>();
        int primeiroDigito;
        int totalSomatoria = 0;
        int restoDivisao;
        int peso = 10;

        for(int item : listaAleatoria){
            listaNumMultiplicados.add(item * peso);

            peso--;
        }

        for(int item : listaNumMultiplicados){
            totalSomatoria += item;
        }

        restoDivisao = (totalSomatoria % 11);

        if(restoDivisao < 2){
            primeiroDigito = 0;
        } else{
            primeiroDigito = 11 - restoDivisao;
        }

        listaAleatoria.add(primeiroDigito);

        return listaAleatoria;
    }

    private ArrayList<Integer> geraCPFParcial(){
        for(int i = 0; i < 9; i++){
            listaAleatoria.add(geraNumAleatorio());
        }

        return listaAleatoria;
    }

    private ArrayList<Integer> geraSegundoDigito(){
        listaNumMultiplicados = new ArrayList<Integer>();
        int segundoDigito;
        int totalSomatoria = 0;
        int restoDivisao;
        int peso = 11;

        for(int item : listaAleatoria){
            listaNumMultiplicados.add(item * peso);

            peso--;
        }

        for(int item : listaNumMultiplicados){
            totalSomatoria += item;
        }

        restoDivisao = (totalSomatoria % 11);

        if(restoDivisao < 2){
            segundoDigito = 0;
        } else{
            segundoDigito = 11 - restoDivisao;
        }

        listaAleatoria.add(segundoDigito);

        return listaAleatoria;
    }

    /**
     * Get Random CPF documents
     *
     * @param mask - pass true if you want to get cpf with mask
     * @return a valid CPF document
     */
    public String getCpf(boolean mask) {
        geraCPFParcial();
        geraPrimeiroDigito();
        geraSegundoDigito();

        String cpf = "";
        String texto = "";


        for(int item : listaAleatoria){
            texto += item;
        }
        if(mask){
            try{
                MaskFormatter mf = new MaskFormatter("###.###.###-##");
                mf.setValueContainsLiteralCharacters(false);
                cpf = mf.valueToString(texto);
            }catch(Exception ex){
                log.error("Erro ao tentar gerar mascara para o cpf " + ex);
            }
        }else {
            cpf = texto;
        }
        return cpf;
    }


    /**
     * Get Random CNPJ documents
     *
     * @param mask - pass true if you want to get cnpj with mask
     * @return a valid CNPJ document
     */
    public static String getCnpj(boolean mask) {

        int n1 = createOneDigitRandomNumber();
        int n2 = createOneDigitRandomNumber();
        int n3 = createOneDigitRandomNumber();
        int n4 = createOneDigitRandomNumber();
        int n5 = createOneDigitRandomNumber();
        int n6 = createOneDigitRandomNumber();
        int n7 = createOneDigitRandomNumber();
        int n8 = createOneDigitRandomNumber();
        int n9 = 0;
        int n10 = 0;
        int n11 = 0;
        int n12 = 1;
        int d1 = n12 * 2 + n11 * 3 + n10 * 4 + n9 * 5 + n8 * 6 + n7 * 7 + n6 * 8 + n5 * 9 + n4 * 2 + n3 * 3 + n2 * 4 + n1 * 5;

        d1 = 11 - (mod(d1, 11));

        if (d1 >= 10)
            d1 = 0;

        int d2 = d1 * 2 + n12 * 3 + n11 * 4 + n10 * 5 + n9 * 6 + n8 * 7 + n7 * 8 + n6 * 9 + n5 * 2 + n4 * 3 + n3 * 4 + n2 * 5 + n1 * 6;

        d2 = 11 - (mod(d2, 11));

        if (d2 >= 10)
            d2 = 0;

        String response;

        if (mask)
            response = "" + n1 + n2 + "." + n3 + n4 + n5 + "." + n6 + n7 + n8 + "/" + n9 + n10 + n11 + n12 + "-" + d1 + d2;
        else
            response = "" + n1 + n2 + n3 + n4 + n5 + n6 + n7 + n8 + n9 + n10 + n11 + n12 + d1 + d2;

        return response;
    }

    /**
     * Verify if cpf provided is valid
     *
     * @param cpf - Pass cpf as a string
     * @return true or false
     */
    public static boolean isCpf(String cpf) {

        cpf = removeSpecialCharacter(cpf);

        if (cpf.equals("00000000000") || cpf.equals("11111111111") || cpf.equals("22222222222") ||
                cpf.equals("33333333333") || cpf.equals("44444444444") || cpf.equals("55555555555") ||
                cpf.equals("66666666666") || cpf.equals("77777777777") || cpf.equals("88888888888") ||
                cpf.equals("99999999999") || (cpf.length() != 11))
            return (false);

        char dig10, dig11;
        int sm, i, r, num, weight;

        try {
            sm = 0;
            weight = 10;
            for (i = 0; i < 9; i++) {
                num = cpf.charAt(i) - 48;
                sm = sm + (num * weight);
                weight = weight - 1;
            }

            r = 11 - (sm % 11);
            if ((r == 10) || (r == 11))
                dig10 = '0';
            else
                dig10 = (char) (r + 48);

            sm = 0;
            weight = 11;
            for (i = 0; i < 10; i++) {
                num = cpf.charAt(i) - 48;
                sm = sm + (num * weight);
                weight = weight - 1;
            }

            r = 11 - (sm % 11);
            if ((r == 10) || (r == 11))
                dig11 = '0';
            else
                dig11 = (char) (r + 48);

            return (dig10 == cpf.charAt(9)) && (dig11 == cpf.charAt(10));
        } catch (InputMismatchException e) {
            return (false);
        }
    }

    /**
     * Verify if cnpj provided is valid
     *
     * @param cnpj - Pass cnpj as a document
     * @return true or false
     */
    public static boolean isCnpj(String cnpj) {

        cnpj = removeSpecialCharacter(cnpj);

        if (cnpj.equals("00000000000000") || cnpj.equals("11111111111111") || cnpj.equals("22222222222222") ||
                cnpj.equals("33333333333333") || cnpj.equals("44444444444444") || cnpj.equals("55555555555555") ||
                cnpj.equals("66666666666666") || cnpj.equals("77777777777777") || cnpj.equals("88888888888888") ||
                cnpj.equals("99999999999999") || (cnpj.length() != 14))
            return (false);

        char dig13, dig14;
        int sm, i, r, num, weight;

        try {
            sm = 0;
            weight = 2;
            for (i = 11; i >= 0; i--) {
                num = cnpj.charAt(i) - 48;
                sm = sm + (num * weight);
                weight = weight + 1;
                if (weight == 10)
                    weight = 2;
            }

            r = sm % 11;
            if ((r == 0) || (r == 1))
                dig13 = '0';
            else
                dig13 = (char) ((11 - r) + 48);

            sm = 0;
            weight = 2;
            for (i = 12; i >= 0; i--) {
                num = cnpj.charAt(i) - 48;
                sm = sm + (num * weight);
                weight = weight + 1;
                if (weight == 10)
                    weight = 2;
            }

            r = sm % 11;
            if ((r == 0) || (r == 1))
                dig14 = '0';
            else
                dig14 = (char) ((11 - r) + 48);

            return (dig13 == cnpj.charAt(12)) && (dig14 == cnpj.charAt(13));
        } catch (InputMismatchException e) {
            return (false);
        }
    }

    /**
     * Create one digit random number
     */
    private static int createOneDigitRandomNumber() {

        return (int) (Math.random() * 10);
    }

    /**
     * Remove special character of the documents
     *
     * @param document - pass cpf or cnpj document to remove especial characters
     * @return String containing only numbers
     */
    private static String removeSpecialCharacter(String document) {
        if (document.contains(".")) {
            document = document.replace(".", "");
        }
        if (document.contains("-")) {
            document = document.replace("-", "");
        }
        if (document.contains("/")) {
            document = document.replace("/", "");
        }
        return document;
    }
}
