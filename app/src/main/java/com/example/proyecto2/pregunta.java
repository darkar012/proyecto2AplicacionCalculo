package com.example.proyecto2;

public class pregunta {

    private int A;
    private int B;
    private int C;
    private String operador;
    private String[] operandos = {"+", "-", "*", "/"};

    public pregunta() {
        this.A = (int) (Math.random() * 11);
        this.B = (int) (Math.random() * 11);
        int operadorRandom = (int) (Math.random() * 4);
        this.operador = operandos[operadorRandom];
    }

    public String getPregunta() {
        if (operador.equals("/")) {
            C = A * B;
            return C + " " + operador + " " + A;
        } else {
            return A + " " + operador + " " + B;
        }
    }

    public double getRespuesta() {
        double respuesta = 0;
        switch (operador) {
            case "+":
                respuesta = A + B;
                break;
            case "-":
                respuesta = A - B;
                break;
            case "*":
                respuesta = A * B;
                break;
            case "/":
                respuesta = C / A;
                break;
        }
        return respuesta;


    }

}
