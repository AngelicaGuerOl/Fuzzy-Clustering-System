package objectguimas;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;
import java.io.IOException;
import javax.swing.JOptionPane;

public class Expertagent1 extends Agent {

    @Override
    protected void setup() {
        // Agrega tres comportamientos al agente: uno para enviar un mensaje "LISTO",
        // otro para recibir un mensaje de solicitud de clustering, y otro para recibir
        // un mensaje de finalización.
        addBehaviour(new SInformListoCA());
        addBehaviour(new RRequestSGauss());
        addBehaviour(new RInformFin());
    }
}

// Clase que envía el mensaje1 (INFORM "LISTO") al agente GUI
class SInformListoCA extends OneShotBehaviour {

    @Override
    public void action() {
        // Crea un mensaje de tipo INFORM
        ACLMessage msg1 = new ACLMessage(ACLMessage.INFORM);
        // Añade como receptor al agente "gui"
        msg1.addReceiver(new AID("gui", AID.ISLOCALNAME));
        // Establece el contenido del mensaje como "LISTO"
        msg1.setContent("LISTO");
        try {
            // Envía el mensaje
            ((Expertagent1) myAgent).send(msg1);
            // Registra el envío del mensaje
            Means.mensajesag("\n<Agente EXP> envía INFORM a <Agente GUI> con: " + msg1.getContent());
        } catch (Exception e) {
            // Maneja excepciones si el mensaje no puede ser enviado
            System.err.println("Catched exception " + e.getMessage());
        }
    }
}

// Clase que recibe el mensaje2 de solicitud de clustering (REQUEST), 
// realiza el cálculo de clustering y envía el mensaje3 con los resultados
class RRequestSGauss extends CyclicBehaviour {

    @Override
    public void action() {
        // Crea un filtro para recibir un mensaje de tipo REQUEST de "gui" con un ID de conversación "REQUEST CLUSTERING"
        MessageTemplate tem1 = MessageTemplate.and(MessageTemplate.MatchPerformative(ACLMessage.REQUEST),
                MessageTemplate.MatchSender(new AID("gui", AID.ISLOCALNAME)));
        MessageTemplate tem2 = MessageTemplate.and(tem1, MessageTemplate.MatchConversationId("REQUEST CLUSTERING"));

        // Crea un mensaje de tipo INFORM que se enviará a "gui" con los resultados del clustering
        ACLMessage msg3 = new ACLMessage(ACLMessage.INFORM);
        msg3.addReceiver(new AID("gui", AID.ISLOCALNAME));

        // Bloquea hasta recibir el mensaje2 que solicita el clustering
        ACLMessage msg2 = myAgent.blockingReceive(tem2);

        if (msg2 != null) {
            try {
                // Crea un objeto de salida para el clustering
                clusteringOut cdif = new clusteringOut();
                // Extrae el objeto de entrada de clustering enviado por "gui"
                clusteringIn g = (clusteringIn) msg2.getContentObject();
                // Establece el objeto de entrada en el objeto de salida
                cdif.setclusteringout(g);

                // Registra la recepción del mensaje de solicitud
                Means.mensajesag("\n<Agente EXP> recibe REQUEST de <Agente GUI> con: " + msg2.getConversationId());

                // Realiza los cálculos de clustering
                cdif.calcularMatrizDistancias();
                cdif.calcularMatrizMembresia();
                cdif.calcularNuevosCentroides();
                cdif.calcularCostos();
                cdif.calcularCostoTotal();

                try {
                    // Establece el ID de la conversación para los resultados
                    msg3.setConversationId("RESULTADO CLUSTERING");
                    // Establece el contenido del mensaje como los resultados del clustering (distancias)
                    msg3.setContentObject(cdif.getDistancia());
                    // Envía el mensaje con los resultados de clustering a "gui"
                    ((Expertagent1) myAgent).send(msg3);
                    // Registra el envío del mensaje con los resultados
                    Means.mensajesag("\n<Agente EXP> envía INFORM a <Agente GUI> con: " + msg3.getConversationId());
                } catch (IOException e) {
                    // Maneja excepciones si el mensaje no puede ser enviado
                    e.printStackTrace();
                }

            } catch (UnreadableException e3) {
                // Maneja excepciones si el contenido del mensaje no puede ser leído correctamente
                System.err.println(" catched exception " + e3.getMessage());
            }
        } 
        // Bloquea el comportamiento para esperar el próximo mensaje
        block();
    }
}

// Clase que recibe el mensaje6 de finalización ("FIN") de "gui"
class RInformFin extends OneShotBehaviour {

    @Override
    public void action() {
        // Crea un filtro para recibir el mensaje INFORM de "gui" con el ID de conversación "FIN"
        MessageTemplate tem1 = MessageTemplate.and(MessageTemplate.MatchPerformative(ACLMessage.INFORM),
                MessageTemplate.MatchSender(new AID("gui", AID.ISLOCALNAME)));
        MessageTemplate tem2 = MessageTemplate.and(tem1, MessageTemplate.MatchConversationId("FIN"));

        // Bloquea hasta recibir el mensaje6
        ACLMessage msg6 = ((Expertagent1) myAgent).blockingReceive(tem2);

        if (msg6 != null) {
            // Registra la recepción del mensaje de finalización
            Means.mensajesag("\n<Agente EXP> recibe INFORM de <Agente GUI> con: " + msg6.getContent());
        } else {
            // Si no se recibe el mensaje, bloquea el comportamiento
            block();
        }
    }
}
