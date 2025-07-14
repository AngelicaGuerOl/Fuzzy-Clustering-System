/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package objectguimas;

/**
 *
 * @author Angelica Guerrero
 */
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 
 */
public class FuzzyCMeans implements Serializable{
    
    public int numClústeres;
    public int numPuntos;
    public int maxIteraciones;
    private double difusividad;
     double[][] centroides;
    public double[][] datos;
    public double[] x, y;       // Cambiado a double[] para almacenar coordenadas decimales
    public double[] x2, y2;
    public FuzzyCMeans(int numClústeres, int numPuntos, double difusividad, int maxIteraciones, double[][] centroides, double[][] datos) {
        this.numClústeres = numClústeres;
        this.numPuntos = numPuntos;
        this.difusividad = difusividad;
        this.maxIteraciones = maxIteraciones;
        this.centroides = centroides;
        this.datos = datos;
    }

    // Getters
    public int getNumClusters() { return numClústeres; }
    public int getNumPuntos() { return numPuntos; }
    public double getDifusividad() { return difusividad; }
    public int getMaxIteraciones() { return maxIteraciones; }
    public double[][] getCentroides() { return centroides; }
    public double[][] getDatos() { return datos; }
    // Función principal para ajustar los datos al algoritmo
    public void ajustar() {
        double[][] matrizMembresía = new double[numPuntos][numClústeres];
        inicializarMatrizMembresía(matrizMembresía);

        Scanner scanner = new Scanner(System.in);
        int iteración = 0;

        while (iteración < maxIteraciones) {
            System.out.println("Iteracion " + (iteración + 1) + ":");

            double[][] matrizDistancias = calcularMatrizDistancias();
            imprimirMatriz(matrizDistancias, "Matriz de Distancias");

            actualizarMatrizMembresía(matrizDistancias, matrizMembresía);
            imprimirMatriz(matrizMembresía, "Matriz de Membresia");

            centroides = actualizarCentroides(matrizMembresía);
            System.out.println("Nuevos Centroides:");
            for (double[] centroide : centroides) {
                System.out.println(Arrays.toString(centroide));
            }

//            double costo = calcularFuncionCosto(matrizDistancias, matrizMembresía);
//            System.out.println("Funcion de Costo: " + costo);
            System.out.println();

            // Opciones de control para el usuario
            System.out.println("Presiona Enter para continuar una iteracion mas, 'n' para avanzar multiples iteraciones, o 'q' para salir:");
            String input = scanner.nextLine();

            if (input.equals("q")) {
                System.out.println("Saliendo del programa...");
                break;
            } else if (input.equals("n")) {
                System.out.print("¿Cuantas iteraciones mas deseas realizar?: ");
                int iteracionesAdicionales = scanner.nextInt();
                scanner.nextLine(); // Limpiar el buffer
                for (int i = 0; i < iteracionesAdicionales && iteración < maxIteraciones - 1; i++) {
                    iteración++;
                    System.out.println("Iteracion " + (iteración + 1) + ":");

                    matrizDistancias = calcularMatrizDistancias();
                    imprimirMatriz(matrizDistancias, "Matriz de Distancias");
                    actualizarMatrizMembresía(matrizDistancias, matrizMembresía);
                    imprimirMatriz(matrizMembresía, "Matriz de Membresia");
                    centroides = actualizarCentroides(matrizMembresía);
                    System.out.println("Nuevos Centroides:");
                    for (double[] centroide : centroides) {
                        System.out.println(Arrays.toString(centroide));
                    }

//                    costo = calcularFuncionCosto(matrizDistancias, matrizMembresía);
//                    System.out.println("Funcion de Costo: " + costo);
                    System.out.println();
                }
            } else {
                iteración++; // Avanzar una iteración más al presionar "Enter"
            }
        }

        scanner.close();
    }

    public void inicializarMatrizMembresía(double[][] matrizMembresía) {
        for (int i = 0; i < matrizMembresía.length; i++) {
            double suma = 0;
            for (int j = 0; j < numClústeres; j++) {
                matrizMembresía[i][j] = Math.random();
                suma += matrizMembresía[i][j];
            }
            for (int j = 0; j < numClústeres; j++) {
                matrizMembresía[i][j] /= suma;
            }
        }
    }

    public double[][] calcularMatrizDistancias() {
        double[][] matrizDistancias = new double[numClústeres][numPuntos];
        for (int i = 0; i < numClústeres; i++) {
            for (int j = 0; j < numPuntos; j++) {
                matrizDistancias[i][j] = calcularDistancia(centroides[i], datos[j]);
            }
        }
        return matrizDistancias;
    }

    public void actualizarMatrizMembresía(double[][] matrizDistancias, double[][] matrizMembresía) {
        for (int i = 0; i < numPuntos; i++) {
            for (int j = 0; j < numClústeres; j++) {
                double suma = 0;
                for (int k = 0; k < numClústeres; k++) {
                    suma += Math.pow(matrizDistancias[j][i] / matrizDistancias[k][i], 2 / (difusividad - 1));
                }
                matrizMembresía[i][j] = 1 / suma;
            }
        }
    }

    double[][] actualizarCentroides(double[][] matrizMembresía) {
        double[][] nuevosCentroides = new double[numClústeres][datos[0].length];
        for (int j = 0; j < numClústeres; j++) {
            double[] numerador = new double[datos[0].length];
            double denominador = 0;
            for (int i = 0; i < numPuntos; i++) {
                double pesoMembresía = Math.pow(matrizMembresía[i][j], difusividad);
                for (int k = 0; k < datos[0].length; k++) {
                    numerador[k] += pesoMembresía * datos[i][k];
                }
                denominador += pesoMembresía;
            }
            for (int k = 0; k < datos[0].length; k++) {
                nuevosCentroides[j][k] = numerador[k] / denominador;
            }
        }
        return nuevosCentroides;
    }

    public List<Double> calcularFuncionCosto(double[][] matrizDistancias, double[][] matrizMembresía) {
    List<Double> costosParciales = new ArrayList<>();
    
    for (int i = 0; i < numClústeres; i++) {
        double costoParcial = 0;
        for (int j = 0; j < numPuntos; j++) {
            costoParcial += Math.pow(matrizMembresía[j][i], difusividad) * Math.pow(matrizDistancias[i][j], 2);
        }
        costosParciales.add(costoParcial); // Agrega el costo parcial a la lista
    }
    return costosParciales; // Retorna la lista de costos parciales
}

    public double calcularDistancia(double[] a, double[] b) {
        double suma = 0;
        for (int i = 0; i < a.length; i++) {
            suma += Math.pow(a[i] - b[i], 2);
        }
        return Math.sqrt(suma);
    }

    public void imprimirMatriz(double[][] matriz, String nombreMatriz) {
        System.out.println(nombreMatriz + ":");
        for (double[] fila : matriz) {
            System.out.println(Arrays.toString(fila));
        }
        System.out.println();
    }
}

