package drills.ex00;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.TreeMap;
import java.util.Scanner;
import java.util.SortedMap;

public class SignatureFinder implements AutoCloseable {
    private String logPath = "result.txt";
    private FileOutputStream logFile;
    private InputStream signatureFile;
    TreeMap<String, String> signatureMap = new TreeMap<>();
    int maxMagicNumberLength = 0;

    public static void main(String[] args) {
        try (SignatureFinder sf = new SignatureFinder()) {
            Scanner sc = new Scanner(System.in);
            while (sc.hasNextLine()) {
                String path = sc.nextLine().trim();
                if (path.equals("42")) {
                    break;
                }
                sf.treatFile(path);
            }
            sc.close();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    void treatFile(String path) {
        String extention = null;
        try (FileInputStream file = new FileInputStream
            (path)) {
            extention = searchExtention(file);
        } catch (Exception e) {
            System.out.println("Error: treatFile - " + e.getMessage());
        }
        if (extention == null) {
            System.out.println("UNDEFINED");
            return;
        }
        System.out.println("PROCESSED");
        logExtentionToFile(extention);
    }

    private void logExtentionToFile(String extention) {
        try {
            if (logFile == null) {
                logFile = new FileOutputStream(logPath, true);
            }
            logFile.write((extention + "\n").getBytes());
        } catch (Exception e) {
            System.out.println("Error: logExtentionToFile - " + e.getMessage());
        }
    }

    public SignatureFinder() {
        try {
            signatureFile = SignatureFinder.class.getResourceAsStream("signatures.txt");
            if (signatureFile == null) {
                System.out.println("Resource file not found.");
                System.exit(1);
            }
            // signatureFile = new FileInputStream("drills/ex00/signatures.txt");
        } catch (Exception e) {
            System.out.println("Error: signature file - " + e.getMessage());
            System.exit(1);
        }
        try {
            logFile = new FileOutputStream(logPath, true);
        } catch (Exception e) {
            System.out.println("Error: log file - " + e.getMessage());
            System.exit(2);
        }
        loadSignatureMap();
    }

    public Path getLogPath() {
        try {
            return java.nio.file.Paths.get(logPath);
        } catch (Exception e) {
            System.out.println("Error: getLogPath - " + e.getMessage());
        }
        return null;
    }

    void deleteLog() {
        try {
            logFile.close();
            java.nio.file.Files.delete(java.nio.file.Paths.get(logPath));
        } catch (Exception e) {
            System.out.println("Error: deleteLog - " + e.getMessage());
        }
    }

    @Override
    public void close() {
        try {
            signatureFile.close();
            logFile.close();
        } catch (Exception e) {}
    }

    private void loadSignatureMap() {
        Scanner sc = new Scanner(signatureFile);
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            if (line.isEmpty()) {
                continue;
            }
            String[] parts = line.split(",");
            if (parts.length != 2) {
                System.out.println("Invalid line: " + line);
                continue;
            }
            String extention = parts[0].trim();
            String signature = parts[1].trim();
            String[] bytes = signature.split(" ");
            signature = "";
            boolean valid = true;
            for (String byteN : bytes) {
                if (byteN.length() != 2) {
                    valid = false;
                    continue;
                }
                signature += byteN;
            }
            if (!valid) {
                System.out.println("Invalid signature: " + signature);
                continue;
            }
            if (signature.length() > maxMagicNumberLength) {
                maxMagicNumberLength = signature.length();
            }
            signatureMap.put(signature, extention);
        }
        sc.close();
    }

    String searchExtention(FileInputStream file) {
        int byteRead = 0;
        String extention = null;
        try {
            byte[] buffer = new byte[maxMagicNumberLength];
            byteRead = file.read(buffer);
            if (byteRead == -1) {
                throw new ExtentionNotFoundException(null);
            }
            extention = searchSignatures(buffer, byteRead);
        } catch (Exception e) {
            System.out.println("Error: searchExtention - " + e.getMessage());
        }
        return extention;
    }

    String searchSignatures(byte[] buffer, int byteRead) {
        String bytes = "";
        for (int i = 0; i < byteRead; i++) {
            String byteStr = Integer.toHexString(buffer[i] & 0xFF);
            if (byteStr.length() == 1) {
                byteStr = "0" + byteStr;
            }
            bytes += byteStr.toUpperCase();;
            SortedMap<String, String> possibleExtentions = possibleExtentions(bytes);
            int size = possibleExtentions.size();
            if (size == 0) {
                return null;
            }
            if (size == 1) {
                String signature = possibleExtentions.firstKey();
                String extention = possibleExtentions.get(signature);
                String byteString = createByteString(buffer, signature.length() / 2);
                if (byteString.equals(signature)) {
                    return extention;
                }
                else 
                    return null;
            }
        }
        return null;
    }

    private String createByteString(byte[] buffer, int byteRead) {
        String bytes = "";
        for (int i = 0; i < byteRead; i++) {
            String byteStr = Integer.toHexString(buffer[i] & 0xFF);
            if (byteStr.length() == 1) {
                byteStr = "0" + byteStr;
            }
            bytes += byteStr;
        }
        return bytes.toUpperCase();
    }

    public SortedMap<String, String> possibleExtentions(String bytes) { // could pass map to continue search in section
        String EndKey = bytes + Character.MAX_VALUE;
        SortedMap<String, String> possibleExtentions = signatureMap.subMap(bytes, EndKey);
        return possibleExtentions;
    }


    public class ExtentionNotFoundException extends Exception {
        public ExtentionNotFoundException(String message) {
            super(message);
        }
    }

}
