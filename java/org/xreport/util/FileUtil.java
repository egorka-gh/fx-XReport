package org.xreport.util;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import static java.nio.file.FileVisitResult.*;

public class FileUtil {

	public static void RemoveDir(String dirPath){
		Path dir = Paths.get(dirPath);
		try {
			  Files.walkFileTree(dir, new SimpleFileVisitor<Path>() {
			 
			      @Override
			      public FileVisitResult visitFile(Path file,
			              BasicFileAttributes attrs) throws IOException {
			 
			          //System.out.println("Deleting file: " + file);
			          Files.delete(file);
			          return CONTINUE;
			      }
			 
			      @Override
			      public FileVisitResult postVisitDirectory(Path dir,
			              IOException exc) throws IOException {
			 
			          //System.out.println("Deleting dir: " + dir);
			          if (exc == null) {
			              Files.delete(dir);
			              return CONTINUE;
			          } else {
			              throw exc;
			          }
			      }
			 
			  });
			} catch (IOException e) {
			  e.printStackTrace();
			}
	}
}
