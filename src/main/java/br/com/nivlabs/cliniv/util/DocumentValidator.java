package br.com.nivlabs.cliniv.util;

public class DocumentValidator {
    private static final int[] pesoCPF = {11, 10, 9, 8, 7, 6, 5, 4, 3, 2};
    private static final int[] pesoCNPJ = {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};

    private DocumentValidator() {
    }

    private static int calcularDigito(String str, int[] peso) {
        int soma = 0;
        for (int indice = str.length() - 1, digito; indice >= 0; indice--) {
            digito = Integer.parseInt(str.substring(indice, indice + 1));
            soma += digito * peso[peso.length - str.length() + indice];
        }
        soma = 11 - soma % 11;
        return soma > 9 ? 0 : soma;
    }

    private static boolean isInvalidSequence(String string) {
        boolean invalidSequence = true;
        if (string != null && !string.isEmpty()) {
            char[] chars = string.toCharArray();
            char firstChar = chars[0];
            for (int i = 1; i < chars.length; i++) {
                if (firstChar != chars[i]) {
                    invalidSequence = false;
                    break;
                }
            }
        }
        return invalidSequence;
    }

    public static boolean isValidCPF(String cpf) {
        if (cpf == null || cpf.length() != 11 || isInvalidSequence(cpf)) {
            return false;
        }

        int digito1 = calcularDigito(cpf.substring(0, 9), pesoCPF);
        int digito2 = calcularDigito(cpf.substring(0, 9) + digito1, pesoCPF);
        return cpf.equals(cpf.substring(0, 9) + digito1 + digito2);
    }

    public static boolean isValidCNPJ(String cnpj) {
        if (cnpj == null || cnpj.length() != 14 || isInvalidSequence(cnpj)) {
            return false;
        }

        int digito1 = calcularDigito(cnpj.substring(0, 12), pesoCNPJ);
        int digito2 = calcularDigito(cnpj.substring(0, 12) + digito1, pesoCNPJ);
        return cnpj.equals(cnpj.substring(0, 12) + digito1 + digito2);
    }

}