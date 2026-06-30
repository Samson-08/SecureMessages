package za.ac.tut.encryption;

import za.ac.tut.message.Message;

public class MessageEncryptor {
    public MessageEncryptor() {}

    public Message encrypt(Message plainMessage) {
        String plainMsg = plainMessage.getPlainMsg().toLowerCase();
        StringBuilder encryptedMsg = new StringBuilder();

        for (int i = 0; i < plainMsg.length(); i++) {
            char ch = plainMsg.charAt(i);
            if (Character.isLetter(ch)) {
                char shifted = (char)(((ch - 'a' + 3) % 26) + 'a');
                encryptedMsg.append(shifted);
            } else {
                encryptedMsg.append(ch); // preserve punctuation, spaces, etc.
            }
        }

        return new Message(encryptedMsg.toString());
    }
}
