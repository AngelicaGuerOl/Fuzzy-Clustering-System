
package objectguimas;

import jade.wrapper.AgentController;


public class ObjectguiMAS {

   
    public static void main(String[] args) {
        // TODO code application logic here
       String name1 = "gui";
       
        String name2 = "exp";
       
        String hostName = "localhost";
        String portName = "1099";

        Iniciar ag = new Iniciar(hostName, portName);
        AgentController gui = ag.iniciaAgente(name1, "objectguimas.GUIagent1");
        AgentController exp = ag.iniciaAgente(name2, "objectguimas.Expertagent1");
        
    }
    
}
