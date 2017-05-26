package migracao.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.zip.GZIPInputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.imageio.stream.FileImageOutputStream;

public class FileUtil {
	
	private static final int BUFFER_SIZE = 4096;
	
	/**
	 * @param pFile
	 * @return
	 * @throws IOException
	 */
	public static byte[] convertFileToByte(File pFile) throws IOException {
		byte[] bFile = Files.readAllBytes(pFile.toPath());
		return bFile;
	}
	
	
	/**
	 * @param btArquivo
	 * @param nmArquivo
	 * @param diretorio
	 * @return
	 */
	public static File createFileFromByteArray(byte[] btArquivo, String nmArquivo, String diretorio) {
		
		if (!new File(diretorio).exists()) {
			(new File(diretorio)).mkdirs();
		}
		
		FileImageOutputStream imageOutput;
		
		try {
			File arquivo = new File(diretorio+File.separator+nmArquivo);
			imageOutput = new FileImageOutputStream(arquivo);
			imageOutput.write(btArquivo, 0, btArquivo.length);
			imageOutput.close();
			
			return arquivo;
		} catch (IOException e) {
			return null;
		}
		
	}
	
	
	 /**
     * Extracts a zip file specified by the zipFilePath to a directory specified by
     * destDirectory (will be created if does not exists)
     * @param zipFilePath
     * @param destDirectory
     * @throws IOException
     */
    public static void unzip(String zipFilePath, String destDirectory) throws IOException {
        File destDir = new File(destDirectory);
        if (!destDir.exists()) {
            destDir.mkdir();
        }
        ZipInputStream zipIn = new ZipInputStream(new FileInputStream(zipFilePath));
        ZipEntry entry = zipIn.getNextEntry();
        // iterates over entries in the zip file
        while (entry != null) {
            String filePath = destDirectory + File.separator + entry.getName();
            if (!entry.isDirectory()) {
                // if the entry is a file, extracts it
                extractFile(zipIn, filePath);
            } else {
                // if the entry is a directory, make the directory
                File dir = new File(filePath);
                dir.mkdir();
            }
            zipIn.closeEntry();
            entry = zipIn.getNextEntry();
        }
        zipIn.close();
    }
    
    
    public static void decompressGzipFile(String gzipFile, String newFile) {
        try {
            FileInputStream fis = new FileInputStream(gzipFile);
            GZIPInputStream gis = new GZIPInputStream(fis);
            FileOutputStream fos = new FileOutputStream(newFile);
            byte[] buffer = new byte[1024];
            int len;
            while((len = gis.read(buffer)) != -1){
                fos.write(buffer, 0, len);
            }
            //close resources
            fos.close();
            gis.close();
            
            File zipFile = new File(gzipFile);
            zipFile.delete();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }
    
    /**
     * Extracts a zip entry (file entry)
     * @param zipIn
     * @param filePath
     * @throws IOException
     */
    private static void extractFile(ZipInputStream zipIn, String filePath) throws IOException {
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(filePath));
        byte[] bytesIn = new byte[BUFFER_SIZE];
        int read = 0;
        while ((read = zipIn.read(bytesIn)) != -1) {
            bos.write(bytesIn, 0, read);
        }
        bos.close();
    }
    

}
