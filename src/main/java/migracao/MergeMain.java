package migracao;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import migracao.domain.ETipoAto;
import migracao.util.RTFUtil;

public class MergeMain {
	
	private static StringBuilder path = new StringBuilder("C:\\arquivos\\migracao\\").append(ETipoAto.ATO01.name());
	private static List<String> erros = new ArrayList<String>();

	public static void main(String[] args) {

		try {
			File[] files = new File(path.toString()).listFiles();
			merge(files);
			
			System.out.println("======================= E R R O S =======================");
			for(String prot : erros) {
				System.out.println(prot);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void merge(File[] files) {
	    for (File file : files) {
	        if (file.isDirectory()) {
	            System.out.println("Protocolo: " + file.getName());
	            
	            File traslado = new File(path.toString()+File.separator+file.getName()+File.separator+file.getName()+"_T.RTF");
	            
	            File assinatura = obterAssinatura(path.toString()+File.separator+file.getName()+File.separator+file.getName());
	            
	            if(traslado.exists() && assinatura.exists()) {
	    			RTFUtil.mergeRTF(traslado, assinatura,
					new File(path.toString()+File.separator+file.getName()+File.separator+file.getName()+".RTF"));
	            } else {
	            	erros.add(file.getName());
	            }
	        } 
	    }
	}
	
	public static File obterAssinatura(String qCaminho) {
		File assinatura  = new File(qCaminho+"_1.RTF");
		
		if(assinatura.exists()) {
			return assinatura;
		}
		
		return new File(qCaminho+"_2.RTF");
		
	}
	
}
