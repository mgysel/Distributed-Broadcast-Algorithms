package cs451;

enum MessageType {
    BROADCAST,
    ACK,
    FORWARD
}

public class Message implements Comparable<Message> {
    private MessageType type;
    private int sequenceNumber;
    private Host from;
    private Host to;
    private String content;
    private boolean receivedAck;
    private boolean isDelivered;

    public Message(MessageType type, int sequenceNumber, Host from, Host to, String content, boolean receivedAck, boolean isDelivered) {
        this.type = type;
        this.sequenceNumber = sequenceNumber;
        this.from = from;
        this.to = to;
        this.content = content;
        this.receivedAck = receivedAck;
        this.isDelivered = isDelivered;
    }

    // public Message(MessageType type, int sequenceNumber, Host from, String content, boolean receivedAck) {
    //     this.sequenceNumber = sequenceNumber;
    //     this.type = type;
    //     this.from = from;
    //     this.content = content;
    //     this.receivedAck = receivedAck;
    // }

    public Message(String message, Hosts hosts, Host me) {
        String[] messageComponents = message.split("/");
        if (messageComponents.length == 4) {
            // Sequence Number
            try {
                int sequenceNumber = Integer.parseInt(messageComponents[1]);
                this.sequenceNumber = sequenceNumber;
            } catch (NumberFormatException e) {
                System.out.printf("Cannot convert message because ID is not an integer: ", e);
            } catch (NullPointerException e) {
                System.out.printf("Cannot convert message because ID is a null pointer: ", e);
            }
            // Type
            if (messageComponents[0].equals("A")) {
                this.type = MessageType.ACK;
            } else if (messageComponents[0].equals("B")) {
                this.type = MessageType.BROADCAST;
            } else if (messageComponents[0].equals("F")) {
                this.type = MessageType.FORWARD;
            }
            // From
            try {
                Integer id = Integer.parseInt(messageComponents[2]);
                this.from = hosts.getHostById(id);
            } catch (NumberFormatException e) {
                System.out.printf("Cannot convert message because ID is not an integer: ", e);
            } catch (NullPointerException e) {
                System.out.printf("Cannot convert message because ID is a null pointer: ", e);
            }
            this.to = me;
            this.content = messageComponents[3];
            this.receivedAck = false;
            this.isDelivered = false;
        }
    }

    public Message getClone() {
        int sequenceNumber = this.getSequenceNumber();
        MessageType type = this.getType();
        Host from = this.getFrom();
        Host to = this.getTo();
        String content = new String(this.getContent());
        boolean receivedAck = this.getReceivedAck();
        boolean isDelivered = this.getIsDelivered();

        Message clone = new Message(type, sequenceNumber, from, to, content, receivedAck, isDelivered);
        return clone;

    }

    public static boolean isValidMessage(String message) {
        String[] messageComponents = message.split("/");
        if (messageComponents.length == 4) {
            return true;
        }
        return false;
    }

    // Compare Message objects
    @Override
    public boolean equals(Object o) {
        // If the object is compared with itself then return true 
        if (o == this) {
            return true;
        }

        // Check if instance of Message
        if (!(o instanceof Message)) {
            return false;
        }
        
        // typecast o to Message so that we can compare data members
        Message m = (Message) o;
        
        // Compare the data members and return accordingly
        if (m.getType() == this.getType() && m.getSequenceNumber() == this.getSequenceNumber() && m.getFrom().equals(this.getFrom()) && m.getContent().equals(this.getContent())) {
            return true;
        }

        return false;
    }

    @Override
    public String toString() {
        String output = "";
        if (this != null) {
            if (this.type == MessageType.BROADCAST) {
                output += "B";
            } else if (this.type == MessageType.ACK) {
                output += "A";
            } else if (this.type == MessageType.FORWARD) {
                output += "F";
            }
            output = String.format("%s/%d/%d/%s", output, this.getSequenceNumber(), this.from.getId(), this.content);
        }

        return output;
    }

    @Override
    public int compareTo(Message m) {
        Integer thisInt = this.getSequenceNumber();
        Integer mInt = m.getSequenceNumber();
        return thisInt.compareTo(mInt);
    }

    public int getSequenceNumber() {
        return this.sequenceNumber;
    }

    public void setSequenceNumber(int sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }

    public MessageType getType() {
        return this.type;
    }

    public Host getFrom() {
        return this.from;
    }

    public Host getTo() {
        return this.to;
    }

    public String getContent() {
        return this.content;
    }

    public boolean getReceivedAck() {
        return this.receivedAck;
    }

    public void setReceivedAck(boolean bool) {
        this.receivedAck = bool;
    }

    public boolean getIsDelivered() {
        return this.isDelivered;
    }

    public void setIsDelivered(boolean bool) {
        this.isDelivered = bool;
    }
}
