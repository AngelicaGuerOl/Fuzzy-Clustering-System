/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package objectguimas;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Vector;

/**
 *
 * @author Angelica Guerrero
 */
public class Cmeans {

    private int nPuntos;
    private int nCentroides;
    public double[] x, y;       // Cambiado a double[] para almacenar coordenadas decimales
    public double[] x2, y2;

    private double[][] distancias;
    private int[][] membresia;
    double[][] centroides;
    private Vector<Integer> posicionUnos = new Vector<>();
    double[][] datos;

    public Cmeans(int nPuntos, int nCentroides) {
        this.nPuntos = nPuntos;
        this.nCentroides = nCentroides;
        // Inicializar los arrays
        x = new double[nPuntos];
        y = new double[nPuntos];
        x2 = new double[nCentroides];
        y2 = new double[nCentroides];
        this.distancias = new double[nPuntos][nCentroides]; // Inicializar matriz de distancias
        
    }

    public void datos() {
        Scanner scanner = new Scanner(System.in);
        // Scanner para ingresar datos
        distancias = new double[nPuntos][nCentroides];

//        x = new int[nPuntos];
//        y = new int[nPuntos];
//        x2 = new int[nCentroides];
//        y2 = new int[nCentroides];
        for (int i = 0; i < nPuntos; i++) {
            System.out.println("Coordenada en X del punto " + (i + 1) + ":");
            x[i] = scanner.nextInt();
            System.out.println("Coordenada en Y del punto " + (i + 1) + ":");
            y[i] = scanner.nextInt();
            System.out.println("P" + (i + 1) + ": (" + x[i] + ", " + y[i] + ")");
            System.out.println("");
        }
        // Scanner para ingresar datos
        for (int j = 0; j < nCentroides; j++) {
            System.out.println("Coordenada en X del centroide " + (j + 1) + ":");
            x2[j] = scanner.nextInt();
            System.out.println("Coordenada en Y del centroide " + (j + 1) + ":");
            y2[j] = scanner.nextInt();
            System.out.println("C" + (j + 1) + ": (" + x2[j] + ", " + y2[j] + ")");
            System.out.println("");
        }
    }

    public String matrizDistancias() {
        StringBuilder resultado = new StringBuilder(); // Usar StringBuilder para construir el resultado

        for (int j = 0; j < nCentroides; j++) {
            for (int i = 0; i < nPuntos; i++) {
                distancias[i][j] = Math.sqrt(Math.pow((x[i] - x2[j]), 2) + Math.pow((y[i] - y2[j]), 2));
                resultado.append(String.format("    "+"%.4f ", distancias[i][j])); // Formatear y agregar al resultado
            }
            resultado.append("\n"); // Nueva línea después de cada fila
        }

        return resultado.toString(); // Retornar el resultado como String
    }
    public double[][] matrizDistancias2() {
    double[][] distancias = new double[nPuntos][nCentroides]; // Inicializar la matriz de distancias

    for (int j = 0; j < nCentroides; j++) {
        for (int i = 0; i < nPuntos; i++) {
            distancias[i][j] = Math.sqrt(Math.pow((x[i] - x2[j]), 2) + Math.pow((y[i] - y2[j]), 2));
        }
    }
    return distancias; // Retornar la matriz de distancias
}

    public String matrizMembrecia() {
        StringBuilder resultado2 = new StringBuilder(); // Usar StringBuilder para construir el resultado
        // Inicializar la matriz de membresía
        membresia = new int[nPuntos][nCentroides];
        // Buscar el menor valor en cada columna de la matriz de distancias
        for (int i = 0; i < nPuntos; i++) {
            double menor = Double.MAX_VALUE;  // Inicializar con el valor más grande posible
            int indiceMenor = -1;  // Para almacenar el índice del menor valor
            // Encontrar el menor valor en la columna j
            for (int j = 0; j < nCentroides; j++) {
                if (distancias[i][j] < menor) {
                    menor = distancias[i][j];
                    indiceMenor = j; // Almacenar el índice del punto más cercano
                }
            }
            // Asignar 1 al índice del punto más cercano y 0 a los demás
            for (int j = 0; j < nCentroides; j++) {
                if (j == indiceMenor) {
                    membresia[i][j] = 1;  // El centroide más cercano
                } else {
                    membresia[i][j] = 0;  // Los demás
                }
            }
        }
        for (int j = 0; j < nCentroides; j++) {
            for (int i = 0; i < nPuntos; i++) {
                resultado2.append(String.format(membresia[i][j] + " "));
            }
             resultado2.append("\n"); // Nueva línea después de cada fila
        }
        return resultado2.toString(); // Retornar el resultado como String
    }

    public String nuevosCentroides() {
        StringBuilder resultado2 = new StringBuilder(); // Usar StringBuilder para construir el resultado
        
        int[] cuentaPuntos = new int[nPuntos];
        double[] sumaX = new double[nCentroides];
        double[] sumaY = new double[nCentroides];
        double[] divisionX = new double[nCentroides];
        double[] divisionY = new double[nCentroides];
        centroides = new double[nCentroides][2];
        for (int j = 0; j < nCentroides; j++) {
            int cuenta = 0;  // Para almacenar el índice del menor valor
            Vector<Integer> posicionesUnosCentroide = new Vector<>(); // Vector para almacenar posiciones de unos para el centroide j
            for (int i = 0; i < nPuntos; i++) {
                if (membresia[i][j] == 1) {
                    cuenta++;
                    posicionesUnosCentroide.add(i + 1);
                    sumaX[j] += x[i]; // Sumar la coordenada x del punto
                    sumaY[j] += y[i]; // Sumar la coordenada x del punto

                }

            }
            cuentaPuntos[j] = cuenta;
            divisionX[j] = sumaX[j] / cuenta;
            divisionY[j] = sumaY[j] / cuenta;
            // Almacenar las nuevas coordenadas del centroide
        centroides[j][0] = divisionX[j]; // Coordenada X del centroide
        centroides[j][1] = divisionY[j]; // Coordenada Y del centroide
        resultado2.append(String.format("C" +(j+1)+"="+"%.1f"+","+" %.1f", centroides[j][0],centroides[j][1])); // Formatear y agregar las coordenadas del centroide    
        resultado2.append("\n"); // Nueva línea después de cada fila
      
            
//            System.out.println("Centroide " + (j + 1) + ": " + cuentaPuntos[j] + " puntos");
//            System.out.println("Posiciones de los unos: " + posicionesUnosCentroide);
//            System.out.println("Suma de coordenadas X: " + sumaX[j]); // Imprimir la suma de coordenadas x
//            System.out.println("Suma de coordenadas Y: " + sumaY[j]); // Imprimir la suma de coordenadas x
//            System.out.printf("division de coordenadas X: " + "%.1f ", divisionX[j]); // Imprimir la suma de coordenadas x
//            System.out.println("");
//            System.out.printf("division de coordenadas Y: " + "%.1f ", divisionY[j]); // Imprimir la suma de coordenadas x

        }
        return resultado2.toString();

    }

    public String funicionCosto() {
        StringBuilder resultado2 = new StringBuilder(); // Usar StringBuilder para construir el resultado
        
        int[] cuentaPuntos = new int[nPuntos];
        double sumaTotal = 0;
        for (int j = 0; j < nCentroides; j++) {
            double sumaF = 0;
            int cuenta = 0;  // Para almacenar el índice del menor valor
            
            Vector<Integer> posicionesUnosCentroide = new Vector<>(); // Vector para almacenar posiciones de unos para el centroide j
            // Encontrar el menor valor en la columna j
            for (int i = 0; i < nPuntos; i++) {
                if (membresia[i][j] == 1) {
                    cuenta++;
                    posicionesUnosCentroide.add(i + 1);
                    sumaF += distancias[i][j];  // El centroide más cercano
                }
            }
                cuentaPuntos[j] = cuenta;
                //System.out.println("Centroide " + (j + 1) + ": " + cuentaPuntos[j] + " puntos");
                //System.out.println("Posiciones de los unos FUNCION: " + posicionesUnosCentroide);
                resultado2.append(String.format( "j" +(j+1)+"="+"%.4f ", + sumaF));
                sumaTotal += sumaF; // Acumular la suma total    
                resultado2.append("\n"); // Nueva línea después de cada fila
            
        }
        resultado2.append(String.format( "J = " +"%.4f ",+ sumaTotal));
        
        return resultado2.toString();
        
    }
    
    
    
     

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Numero de puntos: ");
        int nPuntos = scanner.nextInt();
        System.out.print("Numero de Centroides: ");
        int nCentroides = scanner.nextInt();
        Cmeans means = new Cmeans(nPuntos, nCentroides);
        means.datos();
        means.matrizDistancias();
        means.matrizMembrecia();
        means.nuevosCentroides();
        means.funicionCosto();
    }

    double[][] getMatrizDistancias() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
