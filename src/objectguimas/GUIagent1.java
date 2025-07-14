
package objectguimas;

import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.gui.GuiAgent;
import jade.gui.GuiEvent;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;
import java.io.IOException;
import java.util.Date;


public class GUIagent1 extends GuiAgent {

    // Declaración de un objeto de tipo Means
    Means JFgui;

    @Override
    protected void setup() {
        // Inicializa el objeto JFgui, pasándole la referencia al agente actual (this)
        JFgui = new Means(this);
        // Hace visible la interfaz gráfica asociada al objeto JFgui
        JFgui.setVisible(true);

        // Envia un mensaje al sistema de logging o monitoreo
        Means.mensajesag("INICIO");
        // Agrega un comportamiento al agente: RInformListoCA es el comportamiento a ejecutar cuando se recibe un mensaje de tipo INFORM
        addBehaviour(new RInformListoCA());
    }

    @Override
    protected void onGuiEvent(GuiEvent ev) {
        // Obtiene el tipo de evento (presumiblemente un entero)
        int tipo = ev.getType();

        // Dependiendo del tipo de evento, se ejecuta un bloque específico de código
        switch (tipo) {
            case 1:
                // Agrega los comportamientos SRequestGauss y RInformGauss
                addBehaviour(new SRequestGauss());
                addBehaviour(new RInformGauss());
                System.out.println("Caso 1");
                break;
            case 2:
                // En este caso solo se imprime un mensaje y se realiza la limpieza
                System.out.println("Caso 2");
                System.out.println("Limpiando el programa...");
                break;
            case 3:
                // Agrega el comportamiento SInformFin cuando se recibe el evento 3, para terminar el programa
                addBehaviour(new SInformFin());
                System.out.println("Caso 3");
                System.out.println("Terminando programa...");
                break;
        }
    }
}

// Clase que recibe el mensaje de tipo INFORM de un agente llamado "exp"
class RInformListoCA extends OneShotBehaviour {

    @Override
    public void action() {
        // Crea un filtro de mensaje que solo acepta mensajes INFORM del agente "exp"
        MessageTemplate tem1 = MessageTemplate.and(
                MessageTemplate.MatchPerformative(ACLMessage.INFORM),
                MessageTemplate.MatchSender(new AID("exp", AID.ISLOCALNAME)));

        // Recibe el mensaje con el filtro tem1
        ACLMessage msg1 = ((GUIagent1) myAgent).receive(tem1);

        if (msg1 != null) {
            // Si el contenido del mensaje es "LISTO", se registra el mensaje
            if (msg1.getContent().equals("LISTO")) {
                Means.mensajesag("\n<Agente GUI> recibe INFORM de <Agente EXP> con: " + msg1.getContent());
            } else {
                // Si no, bloquea el comportamiento
                block();
            }
        } else {
            // Si no se recibe un mensaje, bloquea el comportamiento
            block();
        }
    }
}

// Clase que envía un mensaje de tipo REQUEST al agente "exp" solicitando clustering
class SRequestGauss extends CyclicBehaviour {

    @Override
    public void action() {
        // Crea un mensaje de tipo REQUEST
        ACLMessage msg2 = new ACLMessage(ACLMessage.REQUEST);
        // Agrega como receptor al agente "exp"
        msg2.addReceiver(new AID("exp", AID.ISLOCALNAME));
        clusteringIn gi;
        // Obtiene el objeto de clustering a enviar
        gi = Means.getclusteringIn();

        try {
            // Establece el ID de la conversación
            msg2.setConversationId("REQUEST CLUSTERING");
            // Establece el contenido del mensaje como el objeto gi
            msg2.setContentObject(gi);
            // Envía el mensaje
            ((GUIagent1) myAgent).send(msg2);
            // Registra el envío del mensaje
            Means.mensajesag("\n<Agente GUI> envia REQUEST a <Agente EXP> con: " + msg2.getConversationId());
        } catch (IOException e) {
            // Maneja excepciones si el mensaje no puede ser enviado
            e.printStackTrace();
        }

        // Resetea el comportamiento y espera
        reset();
        ((GUIagent1) myAgent).doWait();
    }
}

// Clase que recibe el mensaje con el resultado del clustering
class RInformGauss extends CyclicBehaviour {

    @Override
    public void action() {
        // Crea un filtro para recibir el mensaje INFORM de "exp" con un ID de conversación específico
        MessageTemplate tem1 = MessageTemplate.and(MessageTemplate.MatchPerformative(ACLMessage.INFORM),
                MessageTemplate.MatchSender(new AID("exp", AID.ISLOCALNAME)));
        MessageTemplate tem2 = MessageTemplate.and(tem1, MessageTemplate.MatchConversationId("RESULTADO CLUSTERING"));

        // Recibe el mensaje con el filtro tem2
        ACLMessage msg3 = ((GUIagent1) myAgent).receive(tem2);

        if (msg3 != null) {
            // Registra el recibo del mensaje
            Means.mensajesag("\n<Agente GUI> recibe INFORM de <Agente EXP> con: " + msg3.getConversationId());

            try {
                // Obtiene el objeto clusteringIn del mensaje
                clusteringIn gi = (clusteringIn) msg3.getContentObject();
                // Muestra el resultado del clustering utilizando varios métodos
                Means.showfun(gi);
                Means.showfun2(gi);
                Means.showfun3(gi);
                Means.showfun4(gi);
                Means.showfun5(gi);
                // Resetea el comportamiento y espera
                reset();
                ((GUIagent1) myAgent).doWait();
            } catch (UnreadableException e3) {
                // Maneja excepciones si el contenido del mensaje no se puede leer
                System.err.println(" catched exception " + e3.getMessage());
            }
        } else {
            // Si no se recibe un mensaje, bloquea el comportamiento
            block();
        }
    }
}

// Clase que envía un mensaje de tipo INFORM para terminar el programa
class SInformFin extends OneShotBehaviour {

    @Override
    public void action() {
        // Crea un mensaje de tipo INFORM
        ACLMessage msg6 = new ACLMessage(ACLMessage.INFORM);
        // Agrega como receptor al agente "exp"
        msg6.addReceiver(new AID("exp", AID.ISLOCALNAME));
        // Establece el contenido del mensaje como "FIN"
        msg6.setContent("FIN");

        try {
            // Envía el mensaje
            ((GUIagent1) myAgent).send(msg6);
            // Registra el envío del mensaje
            Means.mensajesag("\n<Agente GUI> envía INFORM a <Agente EXP> con: " + msg6.getContent());
        } catch (Exception e) {
            // Maneja excepciones si el mensaje no puede ser enviado
            System.err.println("Catched exception " + e.getMessage());
        }

        // Elimina el agente una vez que ha terminado
        ((GUIagent1) myAgent).doDelete();
    }
}
