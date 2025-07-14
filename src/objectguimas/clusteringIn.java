
package objectguimas;

import java.io.Serializable;
import java.text.DecimalFormat;

public class clusteringIn implements Serializable {
    private DecimalFormat dec4 = new DecimalFormat("#0.0000");
    private int numPuntos,numCentroides,contador;
            private double peso;
    private double puntos[][];
    private double centroides[][];
    private double centroidesN[][];
    private double fun[][];     //valores de la función para usuario distancia
    private double fun2[][];    //valores de la función para usuario distancias
    private double fun3[][];    //valores de la función para usuario membresias
    private double fun4[];      //valores de la función para usuario nuevos centroides
    private double fun5;      //valores de la función para usuario nuevos centroides
    
    public void setDistancia(int numP, int numC, double Numpeso, double punt[][], double cent[][])
    {
        numPuntos=numP;
        numCentroides=numC;
        peso = Numpeso;
       puntos=punt;
        centroides=cent;
        
    }
    public void setFun(double ff[][])
    {
        fun=ff;
    }
    
    public void setFun2(double ff[][])
    {
        fun2=ff;
    }
    
    public void setFun3(double ff[][])
    {
        fun3=ff;
    }
    
    public void setFun4(double ff[])
    {
        fun4=ff;
    }
    
    public void setFun5(double ff)
    {
        fun5=ff;
    }
   
    public void despliega()
    {
        int fil;
        fil=fun.length;
        
//        System.out.println("\n Centro= "+getNumPuntos()+
//                "\nAmplitud= "+getNumCentroides()+
//                "\nInicio uod= " + getContador()+
//                "\nFin oud=" + getPeso()+
//                
//                "\nFilas= "+fil);
//        
        System.out.println("\nFunción\n X\t Y");
        for (int e = 0; e < fil; e++) {
            for (int i = 0; i < 2; i++) {
                System.out.print(dec4.format(fun[e][i]) + "\t");
            }
            System.out.print("\n");
        }
        
        
}

   
    public double[][] getFun() {
        return fun;
    }
    
    public double[][] getFun2() {
        return fun2;
    }
    
    public double[][] getFun3() {
        return fun3;
    }
    
    public double[] getFun4() {
        return fun4;
    }
    
    public double getFun5() {
        return fun5;
    }
    
    public int getNumPuntos() {
        return numPuntos;
    }

    
    public int getNumCentroides() {
        return numCentroides;
    }

    
    public int getContador() {
        return contador;
    }

    public double getPeso(){
        return peso;
    }
    
    
    public double[][] getPuntos() {
        return puntos;
    }

    
    public double[][] getCentroides() {
        return centroides;
    }
    public double[][] getNuevosCentroides() {
    return centroidesN; // Variable interna que contiene los nuevos centroides
}
}