package objectguimas;

import java.awt.Graphics;
import static java.lang.Math.pow;
import static java.lang.Math.sqrt;
import java.text.DecimalFormat;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class clusteringOut {
    private DecimalFormat dec4 = new DecimalFormat("#0.0000");
    private clusteringIn gg;
    private double[][] distancias;
    private double[][] membresia;
    private double[][] nuevosCentroides;
    private double[] costos;
    private double costoTotal = 0.0;
     
    public void setclusteringout(clusteringIn g)
    {
        gg=g;
        if (nuevosCentroides == null || nuevosCentroides.length == 0) {
            nuevosCentroides = new double[gg.getNumCentroides()][2];
        }
    }
    
    public double FClustering() {
        Double y = 10.0;
        return y;
    }
    
    
    
    double calcularDistancia(double x1, double y1, double x2, double y2) {
        return sqrt(pow(x2 - x1, 2) + pow(y2 - y1, 2));
    }
    
    // Función para calcular la matriz de distancias e imprimirla en un JTextArea
   public void calcularMatrizDistancias() {
    distancias = new double[gg.getNumPuntos()][gg.getNumCentroides()];
    StringBuilder resultado = new StringBuilder(); // Usamos StringBuilder para construir el resultado

    // Calcular las distancias entre cada punto y cada centroide
    for (int i = 0; i < gg.getNumPuntos(); ++i) {
        for (int j = 0; j < gg.getNumCentroides(); ++j) {
            // Calculamos la distancia entre el punto y el centroide correspondiente
            distancias[i][j] = this.calcularDistancia(gg.getPuntos()[i][0], gg.getPuntos()[i][1], gg.getCentroides()[j][0], gg.getCentroides()[j][1]);
        }
    }

    // Construir el resultado para el JTextField
    resultado.append("Centroides\t");
    for (int i = 0; i < gg.getNumPuntos(); i++) {
        resultado.append("Dist ").append(i + 1).append("\t"); // Nombra las columnas de los puntos
    }
    resultado.append("\n");

    // Agregar las distancias por cada centroide
    for (int j = 0; j < gg.getNumCentroides(); j++) {
        resultado.append("Centroide ").append(j + 1).append("\t"); // Cambié el texto para reflejar que es un centroide
        // Las siguientes columnas son las distancias calculadas para cada punto
        for (int i = 0; i < gg.getNumPuntos(); i++) {
            resultado.append(String.format("%.4f", distancias[i][j])).append("\t"); // Formatear la distancia con 4 decimales
        }
        resultado.append("\n"); // Nueva línea al final de cada fila
    }
    gg.setFun(distancias);

    // Mostrar los centroides si es necesario
    StringBuilder centroidesResult = new StringBuilder();
    centroidesResult.append("Centroides:\n");
    

    
    }
    
    // Función para calcular la matriz de membrecia e imprimirla en un JTextArea
public void calcularMatrizMembresia() {
    membresia = new double[gg.getNumPuntos()][gg.getNumCentroides()];
    StringBuilder resultado = new StringBuilder(); // Usamos StringBuilder para construir el resultado

    // Calcular la matriz de membresía
    for (int j = 0; j < gg.getNumCentroides(); ++j) {
        for (int i = 0; i < gg.getNumPuntos(); ++i) {
            double suma = 0.0;
//            System.out.println("  Punto " + (i + 1) + ":");
            for (int k = 0; k < gg.getNumCentroides(); ++k) {
                // Validar distancias para evitar división por cero o problemas numéricos
                double ratio = 1.0; // Inicializamos el ratio en 1.0
                if (distancias[i][k] != 0) {
                    ratio = distancias[i][j] / distancias[i][k];
                }
                double termino = Math.pow(ratio, 2 / (gg.getPeso() - 1));
                suma += termino;

                }
            // Calcular la membresía de este punto respecto a este centroide
            membresia[i][j] = 1.0 / suma;
//            System.out.printf("  Membresía[%d][%d]=%.4f%n", i, j, membresia[i][j]);
        }
    }

    // Construir el resultado para el JTextField
    resultado.append("Centroides\t");
    for (int i = 0; i < gg.getNumPuntos(); i++) {
        resultado.append("M ").append(i + 1).append("\t"); // Nombra las columnas de los puntos
    }
    resultado.append("\n");

    // Agregar las distancias por cada centroide
    for (int j = 0; j < gg.getNumCentroides(); j++) {
        resultado.append("Centroide ").append(j + 1).append("\t");
        for (int i = 0; i < gg.getNumPuntos(); i++) {
            resultado.append(String.format("%.4f", membresia[i][j])).append("\t");
        }
        resultado.append("\n");
    }
    gg.setFun2(membresia); // Asigna la matriz de membresía calculada

    System.out.println("Matriz de membresia calculada:");
    System.out.println(resultado.toString()); // Muestra la matriz de membresía completa
}
    
    // Función para calcular la matriz de membrecia e imprimirla en un JTextArea
    public void calcularNuevosCentroides() {
        nuevosCentroides = new double[gg.getNumCentroides()][2]; // Para almacenar los nuevos centroides

        for (int j = 0; j < gg.getNumCentroides(); ++j) {
            double sumX = 0.0, sumY = 0.0, sumMembresia = 0.0;

            for (int i = 0; i < gg.getNumPuntos(); ++i) {
                // Elevar el valor de la membresía al exponente m
                double u_ij_m = Math.pow(membresia[i][j], gg.getPeso());

                // Acumular la suma ponderada de las coordenadas x e y
                sumX += u_ij_m * gg.getPuntos()[i][0];
                sumY += u_ij_m * gg.getPuntos()[i][1];

                // Acumular la suma ponderada de las membresías
                sumMembresia += u_ij_m;
            }

            // Calcular las nuevas coordenadas del centroide j
            nuevosCentroides[j][0] = sumX / sumMembresia;
            nuevosCentroides[j][1] = sumY / sumMembresia;
            
            gg.setFun3(nuevosCentroides);
        }

    }
    
    // Función para calcular las funciones de costo
    public void calcularCostos() {
        costos = new double[gg.getNumCentroides()];
        for (int j = 0; j < gg.getNumCentroides(); ++j) {
            double costoCentroide = 0.0;
            for (int i = 0; i < gg.getNumPuntos(); ++i) {
                // Costo para cada punto según el centroide j
                costoCentroide += Math.pow(membresia[i][j], gg.getPeso()) * Math.pow(distancias[i][j], 2);
            }
            // Guardar el costo del centroide j
            costos[j] = costoCentroide;
        }
                
        gg.setFun4(costos);
    }

    // Función para calcular el costo total
    public void calcularCostoTotal() {
        for (int j = 0; j < gg.getNumCentroides(); ++j) {
            costoTotal += costos[j];
        }
        gg.setFun5(costoTotal);
    }

    public clusteringIn getDistancia()
    {
        return gg;
    }
    
}