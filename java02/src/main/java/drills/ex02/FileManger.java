package drills.ex02;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;

public class FileManger  implements AutoCloseable {
   Path currentDir;
   Scanner sc;
   public FileManger() {
      sc = new Scanner(System.in);
   }

   @Override
   public void close() {
      sc.close();
   }

   public static void main(String[] args) {
      String path = null;
      for (String arg : args) {
         if (arg.startsWith("--cuurent-folder=")) {
            path = arg.substring("--cuurent-folder=".length());
         }
      }
      if (path == null) {
         path = System.getProperty("user.dir");
      }
      try (FileManger fm = new FileManger()) {
         fm.currentDir = Paths.get(path);
         fm.prompt();
      } catch (Exception e) {
         e.printStackTrace();
      }
   }

   void prompt() {
      while (true) {
         System.out.println(currentDir + " $");
         if (!sc.hasNextLine()) {
            break;
         }
         String line = sc.nextLine();
         if (line.equals("exit")) {
            break;
         }
         String[] args = line.split("\\s+");
         if (args.length == 0) {
            continue;
         }
         switch (args[0]) {
            case "ls":
               ls(args);
               break;
            case "cd":
               cd(args);
               break;
            case "mv":
               mv(args);
               break;
            default:
               System.out.println("Unknown command");
               break;
         }
      }
   }

   void ls(String[] args) {
      if (args.length != 1) {
         System.out.println("Usage: ls (no arguments)");
         return;
      }
      File dir = currentDir.toFile();
      File[] files = dir.listFiles();
      for (File file : files) {
         try {
            System.out.println(file.getName() + " " + getSizeInKb(file) + " KB");
         } catch (Exception e) {
         }
      }
   }

   private long getSizeInKb(File file) throws IOException {
      if (Files.isSymbolicLink(file.toPath())) {
         return 0;
      }
      if (file.isFile()) {
         return Files.size(file.toPath()) / 1024;
      }
      long sizeKb = 0;
      File[] files = file.listFiles();
      if (files == null) {
         return 0;
      }
      for (File f : files) {
         sizeKb += getSizeInKb(f);
      }
      return sizeKb;
   }

   void mv(String[] args) {
      if (args.length != 3) {
         System.out.println("Usage: mv <source> <destination>");
         return;
      }
      Path source = currentDir.resolve(args[1]);
      if (!Files.exists(source)) {
         System.out.println("Source file does not exist");
         return;
      }
      Path destination = null;
      if (args[2].startsWith("/")) {
         destination = Paths.get(args[2]);
      } else {
         destination = currentDir.resolve(args[2]);
      }
      if (Files.isDirectory(destination)) {
         destination = destination.resolve(source.getFileName());
      }
      try {
         Files.move(source, destination);
      } catch (Exception e) {
         e.printStackTrace();
      }
   }

   void cd(String[] args) {
      if (args.length != 2) {
         System.out.println("Usage: cd <directory>");
         return;
      }
      Path newDir = null;
      if (args[1].startsWith("/")) {
         newDir = Paths.get(args[1]);
      } else {
         newDir = currentDir.resolve(args[1]);
         newDir = newDir.normalize();
      }
      if (!Files.isDirectory(newDir)) {
         System.out.println("Not a directory");
         return;
      }
      currentDir = newDir;
   }
}
