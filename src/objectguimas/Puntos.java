/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package objectguimas;

import java.util.Scanner;

/**
 *
 * @author Angelica Guerrero
 */
public class Puntos {
   
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Numero de puntos de datos: ");
        int numPuntos = scanner.nextInt();
        System.out.print("Numero de caracteristicas (por ejemplo, 2 para x y y): ");
        int numCaracterísticas = scanner.nextInt();
        double[][] datos = new double[numPuntos][numCaracterísticas];
        
        System.out.println("Ingresa los valores de cada punto de datos:");
        for (int i = 0; i < numPuntos; i++) {
            System.out.println("X" + (i + 1) + ":");
            for (int j = 0; j < numCaracterísticas; j++) {
                System.out.print("Valor " + (j + 1) + ": ");
                datos[i][j] = scanner.nextDouble();
            }
        }

        System.out.print("Numero de centroides: ");
        int numClústeres = scanner.nextInt();
        double[][] centroidesIniciales = new double[numClústeres][numCaracterísticas];
        System.out.println("Ingresa los valores de cada centroide:");
        for (int i = 0; i < numClústeres; i++) {
            System.out.println("C" + (i + 1) + ":");
            for (int j = 0; j < numCaracterísticas; j++) {
                System.out.print("Valor " + (j + 1) + ": ");
                centroidesIniciales[i][j] = scanner.nextDouble();
            }
        }

        System.out.print("Valor de m (difusividad): ");
        double difusividad = scanner.nextDouble();

        FuzzyCMeans fuzzyCMeans = new FuzzyCMeans(numClústeres, numPuntos, difusividad, 100, centroidesIniciales, datos);
 fuzzyCMeans.ajustar();
        
        scanner.close();
    }
}
