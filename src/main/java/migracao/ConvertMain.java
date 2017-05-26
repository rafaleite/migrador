package migracao;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.artofsolving.jodconverter.DocumentConverter;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.converter.OpenOfficeDocumentConverter;

import migracao.domain.ETipoAto;

public class ConvertMain {
	
	private static StringBuilder path = new StringBuilder("C:\\arquivos\\migracao\\").append(ETipoAto.ATO02.name());
	private static String pathODT = path.toString()+File.separator+"odt"+File.separator;
	private static List<String> erros = new ArrayList<String>();
	private static DocumentConverter converter;
	
	public static void main(String[] args) {
		OpenOfficeConnection connection = new SocketOpenOfficeConnection(8100);
		
		try {
			connection.connect();
			
			converter = new OpenOfficeDocumentConverter(connection);
			
			if (!new File(pathODT).exists()) {
				(new File(pathODT)).mkdirs();
			}
			
			File[] files = new File(path.toString()).listFiles();
			converterODT(files);
			
			connection.disconnect();
			
			System.out.println("======================= E R R O S =======================");
			for(String prot : erros) {
				System.out.println(prot);
			}
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void converterODT(File[] files) {
	    for (File file : files) {
	        if (file.isDirectory() && file.getName() != "odt") {
	            System.out.println("Protocolo: " + file.getName());
	            
	            File traslado = new File(path.toString()+File.separator+file.getName()+File.separator+file.getName()+".RTF");
	            
	            if(traslado.exists()) {
	            	converter.convert(traslado, new File(pathODT+file.getName()+".odt"));
	            } else {
	            	erros.add(file.getName());
	            }
	        } 
	    }
	}
	
}
