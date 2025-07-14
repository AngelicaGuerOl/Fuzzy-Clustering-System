package objectguimas;

import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import jade.core.*;
import jade.core.Runtime;


public class Iniciar extends Agent {

    Runtime rt = Runtime.instance();
    AgentController ac;
    ContainerController cc;

   
    public Iniciar(String host, String port) {
        Profile p = new ProfileImpl();
        p.setParameter(Profile.MAIN_HOST, host);
        p.setParameter(Profile.MAIN_PORT, port);
        cc = rt.createMainContainer(p);
    }

    public AgentController iniciaAgente(String name, String nameClass) {
        if (cc != null) {
            try {
                ac = cc.createNewAgent(name, nameClass, null);
                ac.start();
                return ac;
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
        return null;
    }

    public AgentController iniciaAgente(String host, String port, String name, String nameClass) {
        if (cc != null) {
            try {
                ac = cc.createNewAgent(name, nameClass, null);
                ac.start();
                return ac;
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        } else {
            System.out.println("-----> Contenedor vacio");
        }
        return null;
    }
}