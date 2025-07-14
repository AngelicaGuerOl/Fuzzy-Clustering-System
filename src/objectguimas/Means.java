/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package objectguimas;

import jade.gui.GuiEvent;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 *
 * @author Angelica Guerrero
 */
public class Means extends javax.swing.JFrame {

    DefaultTableModel modelo = new DefaultTableModel();
    DefaultTableModel modelo2 = new DefaultTableModel();
    DefaultTableModel modelo3 = new DefaultTableModel();
    private static DefaultTableModel modelo4 = new DefaultTableModel();
    private static DecimalFormat dec4 = new DecimalFormat("#0.0000");
    private static DecimalFormat dec10 = new DecimalFormat("#0.0000000000");
    private static clusteringIn gin = new clusteringIn();
    private GUIagent1 myAgent;
    GuiEvent ge;
    private static double[][] centroidesN;

    /**
     * Creates new form Means
     */

    public Means(GUIagent1 a) {

        initComponents();
        this.setSize(1366, 768); // Dimensiones mínimas cuando se oprime el botón maximizar
        this.setTitle("Agrupamiento");
        myAgent = a;
        txt_dis1.setEditable(false);
        txt_mem1.setEditable(false);
        txt_fcos1.setEditable(false);
        txt_j.setEditable(false);
        modelo.addColumn("X");
        modelo.addColumn("Y");
        modelo2.addColumn("X");
        modelo2.addColumn("Y");
        modelo3.addColumn("X");
        modelo3.addColumn("Y");
        modelo4.addColumn("X");
        modelo4.addColumn("Y");
        tabla = new JTable(modelo);
        scp.setViewportView(tabla);
        tabla1 = new JTable(modelo2);
        scp1.setViewportView(tabla1);

        tb_nCent = new JTable(modelo4);
        scp3.setViewportView(tb_nCent);
        this.setFocusable(true);
        txt_n.requestFocus();
        // Agregar listener para el campo de texto `txtN`
        txt_n.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Obtener el valor de `n`
                try {
                    int numFilas = Integer.parseInt(txt_n.getText());
                    actualizarFilas(numFilas);
                    txt_c.requestFocus();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Por favor, ingrese un número válido para n.");
                }
            }
        });
        

        txt_c.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Obtener el valor de `n`
                try {
                    int numFilas = Integer.parseInt(txt_c.getText());
                    actualizarFilas2(numFilas);
                    txt_m.requestFocus();

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Por favor, ingrese un número válido para n.");
                }
            }
        });
        tabla.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Obtener la fila y columna donde se hizo clic
                int row = tabla.rowAtPoint(e.getPoint());
                int column = tabla.columnAtPoint(e.getPoint());

                // Verificar que se hizo clic en una celda válida
                if (row >= 0 && column >= 0) {
                    // Iniciar la edición de la celda
                    tabla.editCellAt(row, column);
                    // Obtener el editor de la celda y solicitar el foco
                    Component editor = tabla.getEditorComponent();
                    if (editor != null) {
                        editor.requestFocusInWindow();
                    }
                }
            }
        });
        tabla1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Obtener la fila y columna donde se hizo clic
                int row = tabla1.rowAtPoint(e.getPoint());
                int column = tabla1.columnAtPoint(e.getPoint());

                // Verificar que se hizo clic en una celda válida
                if (row >= 0 && column >= 0) {
                    // Iniciar la edición de la celda
                    tabla1.editCellAt(row, column);
                    // Obtener el editor de la celda y solicitar el foco
                    Component editor = tabla1.getEditorComponent();
                    if (editor != null) {
                        editor.requestFocusInWindow();
                    }
                }
            }
        });

    }

    private void actualizarFilas(int numFilas) {
        // Obtener el modelo de la tabla
        DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();

        // Ajustar el número de filas en la tabla
        modelo.setRowCount(numFilas);
    }

    private void actualizarFilas2(int numFilas) {
        // Obtener el modelo de la tabla
        DefaultTableModel modelo2 = (DefaultTableModel) tabla1.getModel();

        // Ajustar el número de filas en la tabla
        modelo2.setRowCount(numFilas);
    }


    private void graficar(DefaultTableModel modeloCentroides) {
        int nPuntos = Integer.parseInt(txt_n.getText());
        int nCentroides = Integer.parseInt(txt_c.getText());

        // Inicializar las matrices de puntos y centroides
        double[][] puntos = new double[nPuntos][2];
        double[][] centroides = new double[nCentroides][2];

        // Obtener los puntos de la tabla
        for (int i = 0; i < nPuntos; i++) {
            Object valorX = modelo.getValueAt(i, 0); // Columna X
            Object valorY = modelo.getValueAt(i, 1); // Columna Y

            if (valorX != null && valorY != null) {
                puntos[i][0] = Double.parseDouble(valorX.toString());
                puntos[i][1] = Double.parseDouble(valorY.toString());
            }
        }

        // Obtener los centroides de la tabla pasada como parámetro
        for (int j = 0; j < nCentroides; j++) {
            Object valorX2 = modeloCentroides.getValueAt(j, 0); // Columna X
            Object valorY2 = modeloCentroides.getValueAt(j, 1); // Columna Y

            if (valorX2 != null && valorY2 != null) {
                centroides[j][0] = Double.parseDouble(valorX2.toString());
                centroides[j][1] = Double.parseDouble(valorY2.toString());
            }
        }

        // Crear una nueva ventana para mostrar el gráfico
        JFrame ventanaGrafico = new JFrame("Gráfico de Puntos y Centroides");
        ventanaGrafico.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        ventanaGrafico.setSize(500, 500);
        ventanaGrafico.setLayout(new BorderLayout());

        // Crear el panel gráfico y agregarlo a la ventana
        GraficoPanel graficoPanel = new GraficoPanel(puntos, centroides);
        ventanaGrafico.add(graficoPanel, BorderLayout.CENTER);

        // Hacer visible la ventana
        ventanaGrafico.setVisible(true);
    }

    private void fuzzyDatos() {
        int nPuntos = Integer.parseInt(txt_n.getText());
        int nCentroides = Integer.parseInt(txt_c.getText());
        double dif = Double.parseDouble(txt_m.getText());

        FuzzyCMeans fuzzyCMeans = new FuzzyCMeans(nCentroides, nPuntos, dif, 1, new double[nCentroides][2], new double[nPuntos][2]);

        // Inicializar datos y centroides
        for (int i = 0; i < nPuntos; i++) {
            Object valorX = modelo.getValueAt(i, 0);
            Object valorY = modelo.getValueAt(i, 1);
            if (valorX != null && valorY != null) {
                fuzzyCMeans.datos[i][0] = Double.parseDouble(valorX.toString());
                fuzzyCMeans.datos[i][1] = Double.parseDouble(valorY.toString());
            }
        }
        for (int i = 0; i < nCentroides; i++) {
            Object valorX = modelo2.getValueAt(i, 0);
            Object valorY = modelo2.getValueAt(i, 1);
            if (valorX != null && valorY != null) {
                fuzzyCMeans.centroides[i][0] = Double.parseDouble(valorX.toString());
                fuzzyCMeans.centroides[i][1] = Double.parseDouble(valorY.toString());
            }
        }

        double[][] matrizMembresia = new double[nPuntos][nCentroides];
        fuzzyCMeans.inicializarMatrizMembresía(matrizMembresia);

        StringBuilder outputDistancias = new StringBuilder();
        StringBuilder outputMembresia = new StringBuilder();
        StringBuilder outputCentroides = new StringBuilder();
        StringBuilder outputCosto = new StringBuilder();

        for (int iteracion = 0; iteracion < fuzzyCMeans.maxIteraciones; iteracion++) {
            double[][] matrizDistancias = fuzzyCMeans.calcularMatrizDistancias();
            outputDistancias.append(imprimirMatriz(matrizDistancias)).append("\n");

            fuzzyCMeans.actualizarMatrizMembresía(matrizDistancias, matrizMembresia);
            outputMembresia.append(imprimirMatrizInversa(matrizMembresia)).append("\n");

            fuzzyCMeans.centroides = fuzzyCMeans.actualizarCentroides(matrizMembresia);
            outputCentroides.append("Nuevos Centroides:\n");
            for (double[] centroide : fuzzyCMeans.centroides) {
                outputCentroides.append(Arrays.toString(centroide)).append("\n");
            }

            List<Double> costosParciales = fuzzyCMeans.calcularFuncionCosto(matrizDistancias, matrizMembresia);
            double costoTotal = costosParciales.stream().mapToDouble(Double::doubleValue).sum();

            // Agrega detalles de cada costo de grupo y el total
            for (int i = 0; i < costosParciales.size(); i++) {
                outputCosto.append("j").append(i + 1).append(" = ").append(String.format("%.4f", costosParciales.get(i))).append("\n");
            }
            outputCosto.append("J = ").append(String.format("%.4f", costoTotal)).append("\n\n");

            txt_dis1.setText(outputDistancias.toString());
            txt_mem1.setText(outputMembresia.toString());

            while (modelo4.getRowCount() < nCentroides) {
                modelo4.addRow(new Object[]{null, null});
            }

            for (int j = 0; j < nCentroides; j++) {
                modelo4.setValueAt(String.format("%.4f", fuzzyCMeans.centroides[j][0]), j, 0);
                modelo4.setValueAt(String.format("%.4f", fuzzyCMeans.centroides[j][1]), j, 1);
            }

            txt_fcos1.setText(outputCosto.toString());
        }
    }

    private void fuzzyDatos2() {
        int nPuntos = Integer.parseInt(txt_n.getText());
        int nCentroides = Integer.parseInt(txt_c.getText());
        double dif = Double.parseDouble(txt_m.getText());
        FuzzyCMeans fuzzyCMeans = new FuzzyCMeans(nCentroides, nPuntos, dif, 1, new double[nCentroides][2], new double[nPuntos][2]);

        // Inicializar datos y centroides
        for (int i = 0; i < nPuntos; i++) {
            Object valorX = modelo.getValueAt(i, 0);
            Object valorY = modelo.getValueAt(i, 1);
            if (valorX != null && valorY != null) {
                fuzzyCMeans.datos[i][0] = Double.parseDouble(valorX.toString());
                fuzzyCMeans.datos[i][1] = Double.parseDouble(valorY.toString());
            }
        }
        for (int i = 0; i < nCentroides; i++) {
            Object valorX = modelo4.getValueAt(i, 0);
            Object valorY = modelo4.getValueAt(i, 1);
            if (valorX != null && valorY != null) {
                fuzzyCMeans.centroides[i][0] = Double.parseDouble(valorX.toString());
                fuzzyCMeans.centroides[i][1] = Double.parseDouble(valorY.toString());
            }
        }

        double[][] matrizMembresia = new double[nPuntos][nCentroides];
        fuzzyCMeans.inicializarMatrizMembresía(matrizMembresia);

        StringBuilder outputDistancias = new StringBuilder();
        StringBuilder outputMembresia = new StringBuilder();
        StringBuilder outputCentroides = new StringBuilder();
        StringBuilder outputCosto = new StringBuilder();
        StringBuilder outputCostoTotal = new StringBuilder();

        for (int iteracion = 0; iteracion < fuzzyCMeans.maxIteraciones; iteracion++) {
            double[][] matrizDistancias = fuzzyCMeans.calcularMatrizDistancias();
            outputDistancias.append(imprimirMatriz(matrizDistancias)).append("\t");

            fuzzyCMeans.actualizarMatrizMembresía(matrizDistancias, matrizMembresia);
            outputMembresia.append(imprimirMatrizInversa(matrizMembresia)).append("\t");

            fuzzyCMeans.centroides = fuzzyCMeans.actualizarCentroides(matrizMembresia);
            outputCentroides.append("Nuevos Centroides:\n");
            for (double[] centroide : fuzzyCMeans.centroides) {
                outputCentroides.append(Arrays.toString(centroide)).append("\n");
            }

            List<Double> costosParciales = fuzzyCMeans.calcularFuncionCosto(matrizDistancias, matrizMembresia);
            double costoTotal = costosParciales.stream().mapToDouble(Double::doubleValue).sum();

            // Agrega detalles de cada costo de grupo y el total
            for (int i = 0; i < costosParciales.size(); i++) {
                outputCosto.append(String.format("%.4f", costosParciales.get(i))).append("\n");
            }
            outputCostoTotal.append(String.format("%.4f", costoTotal)).append("\n\n");

            txt_dis1.setText(outputDistancias.toString());
            txt_mem1.setText(outputMembresia.toString());

            while (modelo4.getRowCount() < nCentroides) {
                modelo4.addRow(new Object[]{null, null});
            }

            for (int j = 0; j < nCentroides; j++) {
                modelo4.setValueAt(String.format("%.4f", fuzzyCMeans.centroides[j][0]), j, 0);
                modelo4.setValueAt(String.format("%.4f", fuzzyCMeans.centroides[j][1]), j, 1);
            }

            txt_fcos1.setText(outputCosto.toString());
            txt_j.setText(outputCostoTotal.toString());
        }
    }

    public FuzzyCMeans getFuzzyCMeans() {
        int nPuntos = Integer.parseInt(txt_n.getText());
        int nCentroides = Integer.parseInt(txt_c.getText());
        double dif = Double.parseDouble(txt_m.getText());

        FuzzyCMeans fuzzyCMeans = new FuzzyCMeans(nCentroides, nPuntos, dif, 1, new double[nCentroides][2], new double[nPuntos][2]);

        // Inicializar datos y centroides
        for (int i = 0; i < nPuntos; i++) {
            Object valorX = modelo.getValueAt(i, 0);
            Object valorY = modelo.getValueAt(i, 1);
            if (valorX != null && valorY != null) {
                fuzzyCMeans.datos[i][0] = Double.parseDouble(valorX.toString());
                fuzzyCMeans.datos[i][1] = Double.parseDouble(valorY.toString());
            }
        }
        for (int i = 0; i < nCentroides; i++) {
            Object valorX = modelo2.getValueAt(i, 0);
            Object valorY = modelo2.getValueAt(i, 1);
            if (valorX != null && valorY != null) {
                fuzzyCMeans.centroides[i][0] = Double.parseDouble(valorX.toString());
                fuzzyCMeans.centroides[i][1] = Double.parseDouble(valorY.toString());
            }
        }

        return fuzzyCMeans;
    }

    private String imprimirMatriz(double[][] matriz) {
        StringBuilder sb = new StringBuilder();
        for (double[] fila : matriz) {
            for (double valor : fila) {
                // Formatear el valor a 4 decimales y añadir un espacio entre valores
                sb.append(String.format("%.4f", valor)).append("\t");
            }
            // Añadir una nueva línea al final de cada fila
            sb.append("\n");
        }
        return sb.toString().trim(); // Retorna la representación en cadena de la matriz sin espacios extra
    }

    public String imprimirMatrizInversa(double[][] matriz) {
        StringBuilder sb = new StringBuilder();

        // Recorre la matriz intercambiando filas y columnas
        for (int i = 0; i < matriz[0].length; i++) { // Recorre las columnas como si fueran filas
            for (int j = 0; j < matriz.length; j++) { // Recorre las filas como si fueran columnas
                // Formatear el valor a 4 decimales
                sb.append(String.format("%.4f", matriz[j][i])).append("\t");
            }
            sb.append("\n"); // Nueva línea al final de cada "fila" transpuesta
        }

        return sb.toString(); // Retorna la representación en cadena de la matriz transpuesta

    }



   public static String arre1Tostring(double[][] arre) {
        StringBuilder aux = new StringBuilder();  // Usar StringBuilder para mejorar el rendimiento
        int fil = arre.length;
        int col = arre[0].length;

        // Encabezado de la primera fila (vacía en la primera celda, luego D1, D2, ...)
        aux.append("\t");
        
        aux.append("\n");  // Nueva línea después del encabezado

        // Imprimir los resultados por columnas primero
        for (int j = 0; j < col; j++) {
            for (int i = 0; i < fil; i++) {
                aux.append(dec4.format(arre[i][j])).append("\t");  // Valor para cada centroide
            }
            aux.append("\n");  // Nueva línea después de cada columna
        }

        return aux.toString();  // Retorna la cadena generada
    }
    
    public static String arre2Tostring(double[][] arre) {
        StringBuilder aux = new StringBuilder();  // Usar StringBuilder para mejorar el rendimiento
        int fil = arre.length;
        int col = arre[0].length;

        // Encabezado de la primera fila (vacía en la primera celda, luego D1, D2, ...)
        aux.append("\t");
        
        aux.append("\n");  // Nueva línea después del encabezado

        // Imprimir los resultados por columnas primero
        for (int j = 0; j < col; j++) {
            for (int i = 0; i < fil; i++) {
                aux.append(dec4.format(arre[i][j])).append("\t");  // Valor para cada centroide
            }
            aux.append("\n");  // Nueva línea después de cada columna
        }

        return aux.toString();  // Retorna la cadena generada
    }

    public static String arre3Tostring(double[][] arre) {
        StringBuilder aux = new StringBuilder(); // Usar StringBuilder es más eficiente
        int fil = arre.length;
        int col = arre[0].length;

       
        // Filas para los centroides
        for (int i = 0; i < fil; i++) {
            for (int j = 0; j < col; j++) {
                aux.append(dec4.format(arre[i][j])).append("\t"); // Coordenadas X y Y
            }
            aux.append("\n"); // Salto de línea para la siguiente fila
        }

        return aux.toString();
    }

    
    public static String arre4Tostring(double[] arre) {
        StringBuilder aux = new StringBuilder(); // Usar StringBuilder es más eficiente
        int fil = arre.length;

        
        // Filas para los centroides
        for (int i = 0; i < fil; i++) {
            aux.append(dec4.format(arre[i])).append("\n"); // Valor del costo en la segunda columna
        }

        return aux.toString();
    }
    
    public static void mensajesag(String st) {
        String aux = txt_age.getText() + st;
        txt_age.setText(aux);

    }

    public static clusteringIn getclusteringIn() {
        return gin;
    }

    public static void showfun(clusteringIn g) {
        String aux = new String("X \t Y \n");
        double arre[][];
        arre = g.getFun();
        aux = arre1Tostring(arre);
        txt_dis1.setText(aux);
    }

    public static void showfun2(clusteringIn g) {
    // Crea una cadena auxiliar para almacenar los encabezados de las columnas
    String aux = new String("X \t Y \n");
    // Declara una matriz bidimensional de tipo double llamada "arre"
    double arre[][];
    // Llama al método getFun2() del objeto "g" (de tipo clusteringIn) y almacena el resultado en "arre"
    arre = g.getFun2();
    // Convierte la matriz "arre" a una cadena de texto utilizando el método arre2Tostring()
    aux = arre2Tostring(arre);
    // Establece el contenido de la caja de texto (txt_mem1) como el texto generado
    txt_mem1.setText(aux);
}
    public static void showfun3(clusteringIn g) {
        double[][] centroides = g.getFun3(); // Obtener los nuevos centroides
        actualizarTablaCentroides(modelo4, centroides); // Actualizar la tabla con el método
//      txt_nuevCent.setText(arre3Tostring(centroides)); // Actualizar el área de texto
        
    }
    public static void showfun4(clusteringIn g) {
        String aux = new String("X \t Y \n");
        double arre[];
        arre = g.getFun4();
        aux = arre4Tostring(arre);
        txt_fcos1.setText(aux);
    }
    public static void showfun5(clusteringIn g) {
        double arre;
        arre = g.getFun5();
        txt_j.setText(String.valueOf(dec4.format(arre)));
    }
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txt_n = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txt_c = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txt_m = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        scp = new javax.swing.JScrollPane();
        tabla = new javax.swing.JTable();
        jLabel6 = new javax.swing.JLabel();
        scp1 = new javax.swing.JScrollPane();
        tabla1 = new javax.swing.JTable();
        btn_iterar = new javax.swing.JButton();
        btn_iterarSiguiente = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        txt_dis1 = new javax.swing.JTextArea();
        jScrollPane5 = new javax.swing.JScrollPane();
        txt_mem1 = new javax.swing.JTextArea();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        txt_fcos1 = new javax.swing.JTextArea();
        graficar = new javax.swing.JButton();
        jLabel16 = new javax.swing.JLabel();
        graficar3 = new javax.swing.JButton();
        jScrollPane7 = new javax.swing.JScrollPane();
        txt_age = new javax.swing.JTextArea();
        jLabel17 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txt_j = new javax.swing.JTextField();
        scp3 = new javax.swing.JScrollPane();
        tb_nCent = new javax.swing.JTable();
        graficar4 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setText("Datos");

        jLabel2.setText("n=");

        jLabel3.setText("c=");

        txt_c.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_cActionPerformed(evt);
            }
        });

        jLabel4.setText("m=");

        txt_m.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_mActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel5.setText("Puntos");

        tabla.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Title 1", "Title 2"
            }
        ));
        tabla.setCellSelectionEnabled(true);
        tabla.setSelectionBackground(new java.awt.Color(204, 204, 204));
        tabla.setSelectionForeground(new java.awt.Color(0, 0, 0));
        tabla.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaMouseClicked(evt);
            }
        });
        scp.setViewportView(tabla);

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel6.setText("Centroides");

        tabla1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Title 1", "Title 2"
            }
        ));
        tabla1.setCellSelectionEnabled(true);
        tabla1.setSelectionBackground(new java.awt.Color(204, 204, 204));
        tabla1.setSelectionForeground(new java.awt.Color(0, 0, 0));
        tabla1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabla1MouseClicked(evt);
            }
        });
        scp1.setViewportView(tabla1);

        btn_iterar.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btn_iterar.setText("Iterar");
        btn_iterar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_iterarMouseClicked(evt);
            }
        });
        btn_iterar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_iterarActionPerformed(evt);
            }
        });

        btn_iterarSiguiente.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btn_iterarSiguiente.setText("Iteración siguiente");
        btn_iterarSiguiente.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_iterarSiguienteMouseClicked(evt);
            }
        });
        btn_iterarSiguiente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_iterarSiguienteActionPerformed(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel12.setText("Fuzzy CMeans");

        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel13.setText("Matriz de distancias");

        txt_dis1.setColumns(20);
        txt_dis1.setRows(5);
        jScrollPane3.setViewportView(txt_dis1);

        txt_mem1.setColumns(20);
        txt_mem1.setRows(5);
        txt_mem1.setText("\n");
        jScrollPane5.setViewportView(txt_mem1);

        jLabel14.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel14.setText("Nuevos centroides");

        jLabel15.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel15.setText("Funciones de costo");

        txt_fcos1.setColumns(20);
        txt_fcos1.setRows(5);
        jScrollPane6.setViewportView(txt_fcos1);

        graficar.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        graficar.setText("Graficar inicial");
        graficar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                graficarMouseClicked(evt);
            }
        });
        graficar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                graficarActionPerformed(evt);
            }
        });

        jLabel16.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel16.setText("Matriz de membresia");

        graficar3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        graficar3.setText("Graficar fuzzy");
        graficar3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                graficar3MouseClicked(evt);
            }
        });
        graficar3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                graficar3ActionPerformed(evt);
            }
        });

        txt_age.setColumns(20);
        txt_age.setRows(5);
        jScrollPane7.setViewportView(txt_age);

        jLabel17.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel17.setText("Protocolo agentes");

        jLabel7.setText("J:");

        tb_nCent.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Title 1", "Title 2"
            }
        ));
        scp3.setViewportView(tb_nCent);

        graficar4.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        graficar4.setText("Salir");
        graficar4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                graficar4MouseClicked(evt);
            }
        });
        graficar4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                graficar4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btn_iterar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btn_iterarSiguiente)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(graficar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(graficar3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(graficar4))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(scp1, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6)
                            .addComponent(scp, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5)
                            .addComponent(jLabel1)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txt_m, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel2)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txt_n, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel3)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txt_c, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(252, 252, 252)
                                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(389, 389, 389)
                                .addComponent(jLabel17))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(58, 58, 58)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(scp3, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addGroup(layout.createSequentialGroup()
                                                        .addGap(30, 30, 30)
                                                        .addComponent(jLabel14)))
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addGroup(layout.createSequentialGroup()
                                                        .addGap(61, 61, 61)
                                                        .addComponent(jLabel15))
                                                    .addGroup(layout.createSequentialGroup()
                                                        .addGap(46, 46, 46)
                                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addGroup(layout.createSequentialGroup()
                                                                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(txt_j, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                            .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 498, Short.MAX_VALUE)
                                                .addComponent(jScrollPane5, javax.swing.GroupLayout.Alignment.LEADING))))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(231, 231, 231)
                                        .addComponent(jLabel16)))
                                .addGap(60, 60, 60)
                                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 551, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(418, 418, 418)
                        .addComponent(jLabel12)))
                .addContainerGap(527, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(txt_n, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(15, 15, 15)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_c, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(txt_m, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(jLabel5)
                        .addGap(18, 18, 18)
                        .addComponent(scp, javax.swing.GroupLayout.DEFAULT_SIZE, 133, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(scp1, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel13)
                            .addComponent(jLabel17))
                        .addGap(15, 15, 15)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 425, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel16)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel14)
                                    .addComponent(jLabel15))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(4, 4, 4)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jLabel7)
                                            .addComponent(txt_j, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(14, 14, 14))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(scp3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                        .addGap(11, 11, 11)))))))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_iterar)
                    .addComponent(btn_iterarSiguiente)
                    .addComponent(graficar)
                    .addComponent(graficar3)
                    .addComponent(graficar4))
                .addGap(114, 114, 114))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txt_cActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_cActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_cActionPerformed

    private void txt_mActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_mActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_mActionPerformed

    private void tablaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaMouseClicked

    }//GEN-LAST:event_tablaMouseClicked

    private void tabla1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabla1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tabla1MouseClicked

    private void btn_iterarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_iterarActionPerformed
        // TODO add your handling code here:
        int n,c;
        double m;
        n = Integer.parseInt(txt_n.getText());
        c = Integer.parseInt(txt_c.getText());
        m = Double.parseDouble(txt_m.getText());
        // Obtener los datos de las tablas
        double[][] puntosO = obtenerDatosDeTabla(tabla);
        double[][] centroidesO = obtenerDatosDeTabla(tabla1);

        gin.setDistancia(n, c, m, puntosO, centroidesO);
        ge = new GuiEvent(btn_iterar, 1);
        myAgent.postGuiEvent(ge);
        // Actualiza la tabla de nuevos centroides
    double[][] nuevosCentroides = gin.getCentroides(); // Asegúrate de que `getCentroides` devuelva los nuevos datos
        actualizarTablaNuevosCentroides(nuevosCentroides);
    }//GEN-LAST:event_btn_iterarActionPerformed
private double[][] obtenerDatosDeTabla(JTable tabla) {
        TableModel modelo = tabla.getModel();
        int filas = modelo.getRowCount();
        int columnas = modelo.getColumnCount();
        double[][] datos = new double[filas][columnas];

        for (int row = 0; row < filas; row++) {
            for (int col = 0; col < columnas; col++) {
                Object value = modelo.getValueAt(row, col);
                // Manejo de valores nulos y conversión a double
                datos[row][col] = (value != null) ? Double.parseDouble(value.toString()) : 0.0;
            }
        }
        return datos;
    }
    private void btn_iterarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_iterarMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_iterarMouseClicked

    private void btn_iterarSiguienteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_iterarSiguienteMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_iterarSiguienteMouseClicked

    private void btn_iterarSiguienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_iterarSiguienteActionPerformed
        // TODO add your handling code here:
        fuzzyDatos2();
        ge = new GuiEvent(btn_iterar, 1);
        myAgent.postGuiEvent(ge);
    }//GEN-LAST:event_btn_iterarSiguienteActionPerformed
    
public static void actualizarTablaCentroides(DefaultTableModel modelo4, double[][] centroides) {
    int nCentroides = centroides.length;

    // Asegúrate de que el modelo tenga suficientes filas
    while (modelo4.getRowCount() < nCentroides) {
        modelo4.addRow(new Object[]{null, null}); // Agregar filas vacías
    }

    // Actualizar las coordenadas en la tabla
    for (int i = 0; i < nCentroides; i++) {
        modelo4.setValueAt(String.format("%.4f", centroides[i][0]), i, 0); // Columna X
        modelo4.setValueAt(String.format("%.4f", centroides[i][1]), i, 1); // Columna Y
    }
}
public void actualizarTablaNuevosCentroides(double[][] nuevosCentroides) {
    DefaultTableModel modelo = (DefaultTableModel) tb_nCent.getModel();

    // Asegúrate de que el modelo tenga suficientes filas
    while (modelo.getRowCount() < nuevosCentroides.length) {
        modelo.addRow(new Object[]{null, null}); // Agregar filas vacías si es necesario
    }

    // Actualizar los datos en la tabla
    for (int i = 0; i < nuevosCentroides.length; i++) {
        modelo.setValueAt(String.format("%.4f", nuevosCentroides[i][0]), i, 0); // Columna X
        modelo.setValueAt(String.format("%.4f", nuevosCentroides[i][1]), i, 1); // Columna Y
    }
}
    private void graficarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_graficarMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_graficarMouseClicked

    private void graficarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_graficarActionPerformed
        // TODO add your handling code here:
        graficar(modelo2);
    }//GEN-LAST:event_graficarActionPerformed

    private void graficar3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_graficar3MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_graficar3MouseClicked

    private void graficar3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_graficar3ActionPerformed
        // TODO add your handling code here:
        graficar(modelo4);
    }//GEN-LAST:event_graficar3ActionPerformed

    private void graficar4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_graficar4MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_graficar4MouseClicked

    private void graficar4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_graficar4ActionPerformed
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_graficar4ActionPerformed

    /**
     * @param args the command line arguments
     */
//    public static void main(String args[]) {
//        /* Set the Nimbus look and feel */
//        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
//        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
//         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
//         */
//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (ClassNotFoundException ex) {
//            java.util.logging.Logger.getLogger(Means.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(Means.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(Means.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(Means.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//
//        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                // new Means().setVisible(true);
//            }
//        });
//    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_iterar;
    private javax.swing.JButton btn_iterarSiguiente;
    private javax.swing.JButton graficar;
    private javax.swing.JButton graficar3;
    private javax.swing.JButton graficar4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane scp;
    private javax.swing.JScrollPane scp1;
    private javax.swing.JScrollPane scp3;
    private javax.swing.JTable tabla;
    private javax.swing.JTable tabla1;
    private static javax.swing.JTable tb_nCent;
    private static javax.swing.JTextArea txt_age;
    private javax.swing.JTextField txt_c;
    private static javax.swing.JTextArea txt_dis1;
    private static javax.swing.JTextArea txt_fcos1;
    private static javax.swing.JTextField txt_j;
    private javax.swing.JTextField txt_m;
    private static javax.swing.JTextArea txt_mem1;
    private javax.swing.JTextField txt_n;
    // End of variables declaration//GEN-END:variables
}
