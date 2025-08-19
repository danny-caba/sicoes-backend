package pe.gob.osinergmin.sicoes.util;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
 

public class ZipUtil {

	public static void main(String[] args) throws Exception {
		String path="C:\\data\\sicoes\\temp\\procesoItem\\1";
		String pathZip="C:\\data\\sicoes\\temp\\procesoItem\\1.zip";
		zipFolder(path,pathZip); 	
	}
	

	    public static  boolean zipearCarpeta(String srcFolder, String destZipFile) {
	        boolean result = false;
	        try {
	            zipFolder(srcFolder, destZipFile);
	            result = true;
	        
	        } catch (Exception e) {
	        	e.printStackTrace();
	        } finally {
	            return result;
	        }
	    }

	    private static void zipFolder(String srcFolder, String destZipFile) throws Exception {
	        ZipOutputStream zip = null;
	        FileOutputStream fileWriter = null;

	        fileWriter = new FileOutputStream(destZipFile);
	        zip = new ZipOutputStream(fileWriter);
	        addFolderToZip("", srcFolder, zip);
	        zip.flush();
	        zip.close();
	    }

	    private static void addFileToZip(String path, String srcFile, ZipOutputStream zip, boolean flag) throws Exception {
	        File folder = new File(srcFile);

	        if (flag == true) {
	            zip.putNextEntry(new ZipEntry(path + File.separator + folder.getName() +  File.separator));
	        } else { 
	            if (folder.isDirectory()) {
	                addFolderToZip(path, srcFile, zip);
	            } else {
	                byte[] buf = new byte[1024];
	                int len;
	                FileInputStream in = new FileInputStream(srcFile);
	                zip.putNextEntry(new ZipEntry(path +  File.separator + folder.getName()));
	                while ((len = in.read(buf)) > 0) {
	                    zip.write(buf, 0, len);
	                }
	            }
	        }
	    }

	    private static void addFolderToZip(String path, String srcFolder, ZipOutputStream zip) throws Exception {
	        File folder = new File(srcFolder);

	        if (folder.list().length == 0) {
	            System.out.println(folder.getName());
	            addFileToZip(path, srcFolder, zip, true);
	        } else {
	            for (String fileName : folder.list()) {
	                if (path.equals("")) {
	                    addFileToZip(folder.getName(), srcFolder + File.separator+ fileName, zip, false);
	                } else {
	                    addFileToZip(path +  File.separator + folder.getName(), srcFolder +  File.separator + fileName, zip, false);
	                }
	            }
	        }
	    }
	}

