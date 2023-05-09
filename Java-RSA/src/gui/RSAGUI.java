package gui;

import encryption.RSACore;
import utils.KeyManager;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.math.BigInteger;
import java.util.*;
import javax.swing.*;
import javax.swing.border.Border;

public class RSAGUI extends JFrame implements ActionListener {

    private JButton
            keyGenBut, keySaveBut, keyLoadPrivBut, keyApplyBut,
            msgEncryptBut, msgDecryptBut, msgLoadThirdPartyKey,
            sigSignBut, sigVerifyBut;

    private JLabel
            keyPreviewNameLabel;
    private JTextArea
            messageInputTextArea, messageOutputTextArea, signatureTextArea;

    private GridLayout twoButtonPackedLayout, fourButtonPackedLayout, fiveButtonPackedLayout;

    private JFileChooser fileChooser;

    private static String lastUsedPath = "./";
    private final Map<String, String> activatedKeyFn = new HashMap<>();

    private final RSACore core;
    private final KeyManager keyCore;

    public RSAGUI() {

        core = new RSACore();
        keyCore = new KeyManager();

        String emptyKeyPlaceholder = "None";
        activatedKeyFn.put("None", emptyKeyPlaceholder);
        activatedKeyFn.put("SenderPublicKey", emptyKeyPlaceholder);
        activatedKeyFn.put("SenderPrivateKey", emptyKeyPlaceholder);
        activatedKeyFn.put("ReceiverPublicKey", emptyKeyPlaceholder);

        setTitle("RSA Encryption/Decryption Demo");
        setSize(720, 480);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Border b = BorderFactory.createEmptyBorder(10, 10, 10, 10);

        twoButtonPackedLayout = new GridLayout(1, 2);
        twoButtonPackedLayout.setVgap(10);
        twoButtonPackedLayout.setHgap(10);

        fourButtonPackedLayout = new GridLayout(1, 4);
        fourButtonPackedLayout.setVgap(10);

        fiveButtonPackedLayout = new GridLayout(1, 5);
        fiveButtonPackedLayout.setVgap(10);
        fiveButtonPackedLayout.setHgap(10);

        JPanel mainPanel = new JPanel();
//        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBorder(b);

        // Key Management Panel
        JPanel keyMngPanel = new JPanel();
        keyMngPanel.setLayout(new BorderLayout());

        JLabel keyMngLabel = new JLabel("Key Management");
        keyMngPanel.add(keyMngLabel, BorderLayout.NORTH);

        JPanel keyMngButPanel = new JPanel();
        keyMngButPanel.setLayout(fiveButtonPackedLayout);
        keyMngButPanel.setBorder(b);

        keyGenBut = new JButton("Generate Keys");
        keyGenBut.addActionListener(this);
        keySaveBut = new JButton("Save Keys");
        keySaveBut.addActionListener(this);
        msgLoadThirdPartyKey = new JButton("Load Receiver Key");
        msgLoadThirdPartyKey.addActionListener(this);
        keyLoadPrivBut = new JButton("Load Self Key");
        keyLoadPrivBut.addActionListener(this);
        keyApplyBut = new JButton("Apply Keys");
        keyApplyBut.addActionListener(this);
        keyMngButPanel.add(keyGenBut);
        keyMngButPanel.add(keySaveBut);
        keyMngButPanel.add(msgLoadThirdPartyKey);
        keyMngButPanel.add(keyLoadPrivBut);
        keyMngButPanel.add(keyApplyBut);
        keyMngPanel.add(keyMngButPanel, BorderLayout.SOUTH);

        JPanel keyPreviewPanel = new JPanel();
        keyPreviewPanel.setLayout(new BorderLayout());

        keyPreviewNameLabel = new JLabel(getLoadedKeysListStr());
        keyPreviewPanel.add(keyPreviewNameLabel, BorderLayout.NORTH);

        keyMngPanel.add(keyPreviewPanel, BorderLayout.CENTER);

        mainPanel.add(keyMngPanel, BorderLayout.NORTH);

        JPanel subpanel = new JPanel();
        subpanel.setLayout(new GridLayout(2, 1));

        // Message Panel
        JPanel messagePanel = new JPanel();
        messagePanel.setLayout(new BorderLayout());

        JLabel messageAreaLabel = new JLabel("Message Area");
        messagePanel.add(messageAreaLabel, BorderLayout.NORTH);

        JPanel messageAreaPanel = new JPanel();
        messageAreaPanel.setLayout(new GridLayout(1, 2));

        // Input area
        JPanel messageInputArea = new JPanel();
        messageInputArea.setLayout(new BorderLayout());
        JLabel messageInputLabel = new JLabel("Input");
        messageInputArea.add(messageInputLabel, BorderLayout.NORTH);
        messageInputTextArea = new JTextArea();
        messageInputTextArea.setLineWrap(true);
        messageInputTextArea.setWrapStyleWord(true);
        JScrollPane msgInputScrollPane = new JScrollPane(messageInputTextArea);

        messageInputArea.add(msgInputScrollPane, BorderLayout.CENTER);

        // Output Area
        JPanel messageOutputArea = new JPanel();
        messageOutputArea.setLayout(new BorderLayout());
        JLabel messageOutputLabel = new JLabel("Output");
        messageOutputArea.add(messageOutputLabel, BorderLayout.NORTH);
        messageOutputTextArea = new JTextArea();
        messageOutputTextArea.setLineWrap(true);
        messageOutputTextArea.setWrapStyleWord(true);
        JScrollPane msgOutputScrollPane = new JScrollPane(messageOutputTextArea);

        messageOutputArea.add(msgOutputScrollPane, BorderLayout.CENTER);

        JPanel msgButPanel = new JPanel();
        msgButPanel.setLayout(twoButtonPackedLayout);
        msgButPanel.setBorder(b);
        msgEncryptBut = new JButton("Encrypt");
        msgEncryptBut.addActionListener(this);
        msgDecryptBut = new JButton("Decrypt");
        msgDecryptBut.addActionListener(this);
        msgButPanel.add(msgEncryptBut);
        msgButPanel.add(msgDecryptBut);
        messagePanel.add(msgButPanel, BorderLayout.SOUTH);

        messageAreaPanel.add(messageInputArea);
        messageAreaPanel.add(messageOutputArea);

        messagePanel.add(messageAreaPanel);

        subpanel.add(messagePanel);

        // Signature Panel
        JPanel signaturePanel = new JPanel();
        signaturePanel.setLayout(new BorderLayout());

        JLabel signatureAreaLabel = new JLabel("Signature (SHA-256)");
        signaturePanel.add(signatureAreaLabel, BorderLayout.NORTH);

        signatureTextArea = new JTextArea();
        signatureTextArea.setLineWrap(true);
        signatureTextArea.setWrapStyleWord(true);
        JScrollPane sigScrollPane = new JScrollPane(signatureTextArea);

        signaturePanel.add(sigScrollPane, BorderLayout.CENTER);

        JPanel sigButPanel = new JPanel();
        sigButPanel.setLayout(twoButtonPackedLayout);
        sigButPanel.setBorder(b);
        sigSignBut = new JButton("Sign");
        sigSignBut.addActionListener(this);
        sigVerifyBut = new JButton("Verify");
        sigVerifyBut.addActionListener(this);
        sigButPanel.add(sigSignBut);
        sigButPanel.add(sigVerifyBut);
        signaturePanel.add(sigButPanel, BorderLayout.SOUTH);

        subpanel.add(signaturePanel);

        mainPanel.add(subpanel, BorderLayout.CENTER);

        fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(lastUsedPath));
        fileChooser.setAcceptAllFileFilterUsed(true);

        setContentPane(mainPanel);
        repaint();
    }

    private String getLoadedKeysListStr() {
        String senderPriv = activatedKeyFn.get("SenderPrivateKey");
        String receiverPub = activatedKeyFn.get("ReceiverPublicKey");
        return "Activated Keys: " + senderPriv + " " + receiverPub;
    }

    private void refreshKeyList() {
        this.keyPreviewNameLabel.setText(getLoadedKeysListStr());
    }

    private void applyKeysToCore() {
        core.setSelfKeys(keyCore.getPrivateKey(), keyCore.getPublicKey());
        core.setReceiverPublicKey(keyCore.getThirdPartyKey());
    }

    public void actionPerformed(ActionEvent e) {
        SwingUtilities.updateComponentTreeUI(this);
        SwingUtilities.updateComponentTreeUI(this.getContentPane());

        if (e.getSource() == keyGenBut) {
            this.keyCore.generateKeys();

        } else if (e.getSource() == keySaveBut) {

            if (!this.keyCore.writeKeys()) {
                JOptionPane.showMessageDialog(
                        this,
                        "No valid keys to write.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }

            String privFn = keyCore.getKeyFileName(true);
            String pubFn = keyCore.getKeyFileName(false);

            JOptionPane.showMessageDialog(
                    this,
                    "Keys saved as " + privFn + " and " + pubFn + ".",
                    "Success", JOptionPane.INFORMATION_MESSAGE);

        } else if (e.getSource() == keyLoadPrivBut) {

            int result = this.fileChooser.showOpenDialog(this);

            if (result == JFileChooser.APPROVE_OPTION) {
                String fp = fileChooser.getSelectedFile().getAbsolutePath();

                String[] seg = fp.split("\\\\");
                String filename = seg[seg.length - 1];

                int ret = keyCore.readKeys(filename, false);

                if (ret >= 0) {
                    JOptionPane.showMessageDialog(
                            this,
                            "Invalid private key " + filename + ".",
                            "Error", JOptionPane.ERROR_MESSAGE);

                } else {
                    JOptionPane.showMessageDialog(
                            this,
                            "Loaded private key file " + filename + ".",
                            "Success", JOptionPane.INFORMATION_MESSAGE);
                    this.activatedKeyFn.put("SenderPrivateKey", filename);
                }
                refreshKeyList();
                applyKeysToCore();
            }
        } else if (e.getSource() == msgLoadThirdPartyKey) {
            int result = this.fileChooser.showOpenDialog(this);

            if (result == JFileChooser.APPROVE_OPTION) {
                String fp = fileChooser.getSelectedFile().getAbsolutePath();

                String[] seg = fp.split("\\\\");
                String filename = seg[seg.length - 1];

                int ret = keyCore.readKeys(filename, true);

                if (ret <= 0) {
                    JOptionPane.showMessageDialog(
                            this,
                            "Invalid public key " + filename + ".",
                            "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(
                            this,
                            "Loaded public key file " + filename + ".",
                            "Success", JOptionPane.INFORMATION_MESSAGE);
                    this.activatedKeyFn.put("ReceiverPublicKey", filename);
                }

                refreshKeyList();
                applyKeysToCore();

            }
        }  else if (e.getSource() == keyApplyBut) {
            JOptionPane.showMessageDialog(
                    this,
                     "Key(s) applied.",
                    "Success", JOptionPane.INFORMATION_MESSAGE);
            applyKeysToCore();

        } else if (e.getSource() == msgEncryptBut) {
            String encoded = Base64.getEncoder().encodeToString(messageInputTextArea.getText().getBytes());
            core.setMessage(encoded);

            BigInteger result = core.encryptMessage();

            if (result == null) {
                JOptionPane.showMessageDialog(
                        this,
                        "Invalid receiver's public key.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                messageOutputTextArea.setText(Base64.getEncoder().encodeToString(result.toString().getBytes()));
            }
        } else if (e.getSource() == msgDecryptBut) {
            String cipherText = new String(Base64.getDecoder().decode(messageOutputTextArea.getText()));
            core.setMessage(new BigInteger(cipherText));

            String result = core.decryptMessage();
            if (result == null) {
                JOptionPane.showMessageDialog(
                        this,
                        "Invalid self private key.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                try {
                    messageInputTextArea.setText(new String(Base64.getDecoder().decode(result)));
                } catch (IllegalArgumentException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(
                            this,
                            "Compromised base64 string.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }

        }  else if (e.getSource() == sigSignBut) {
            String cipherText = new String(Base64.getDecoder().decode(messageOutputTextArea.getText()));

            BigInteger signature = core.generateSignature(new BigInteger(cipherText));

            signatureTextArea.setText(Base64.getEncoder().encodeToString(signature.toString().getBytes()));

        } else if (e.getSource() == sigVerifyBut) {
            try {
            String signature = new String(Base64.getDecoder().decode(signatureTextArea.getText()));
            String cipherText = new String(Base64.getDecoder().decode(messageOutputTextArea.getText()));

                if (core.verifySignature(new BigInteger(cipherText), new BigInteger(signature))) {
                    JOptionPane.showMessageDialog(
                            this,
                            "Message is verified.",
                            "Verfied", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(
                            this,
                            "Message is not verified.",
                            "Not verfied.", JOptionPane.ERROR_MESSAGE);
                }

            } catch (IllegalArgumentException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(
                        this,
                        "Compromised base64 string.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

    }
}



