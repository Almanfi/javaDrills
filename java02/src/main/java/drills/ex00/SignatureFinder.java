package drills.ex00;

import java.io.FileInputStream;
import java.util.TreeMap;
import java.util.Scanner;
import java.util.SortedMap;

public class SignatureFinder implements AutoCloseable {
    private FileInputStream signatureFile;
    TreeMap<String, String> signatureMap = new TreeMap<>();
    int maxMagicNumberLength = 0;

    public static void main(String[] args) {
        try (SignatureFinder sf = new SignatureFinder()) {
            try (FileInputStream file = new FileInputStream(args[0])) {
                sf.searchExtention(file);
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public SignatureFinder() {
        try {
            signatureFile = new FileInputStream("drills/ex00/signatures.txt");
        } catch (Exception e) {
            System.out.println("Error: signature file - " + e.getMessage());
        }
        loadSignatureMap();
    }

    @Override
    public void close() {
        try {
            signatureFile.close();
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

    void searchExtention(FileInputStream file) {
        int byteRead = 0;
        try {
            byte[] buffer = new byte[maxMagicNumberLength];
            byteRead = file.read(buffer);
            if (byteRead == -1) {
                throw new ExtentionNotFoundException(null);
            }
            String Extention = searchSignatures(buffer, byteRead);
            if (Extention == null) {
                throw new ExtentionNotFoundException(null);
            }
            System.out.println("Extention: " + Extention);
        } catch (Exception e) {
            System.out.println("Error: searchExtention - " + e.getMessage());
        }
    }

    private String searchSignatures(byte[] buffer, int byteRead) {
        String bytes = "";
        for (int i = 0; i < byteRead; i++) {
            String byteStr = Integer.toHexString(buffer[i] & 0xFF);
            byteStr.toUpperCase();
            if (byteStr.length() == 1) {
                byteStr = "0" + byteStr;
            }
            bytes += byteStr;
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
        System.out.println("searching for : " + bytes);
        System.out.println("possibleExtentions: " + possibleExtentions.size());
        return possibleExtentions;
    }


    public class ExtentionNotFoundException extends Exception {
        public ExtentionNotFoundException(String message) {
            super(message);
        }
    }

}
