package migracao;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;

import migracao.dao.AtoDAO;
import migracao.domain.ETipoAto;

public class RenameFileMain {
	
	public static void main(String[] args) {
		ETipoAto tipo = ETipoAto.ATO04;
		StringBuilder path = new StringBuilder("C:/arquivos/migracao/").append(tipo.name());
		
		String pathOrigem = path.toString() + File.separator + "odt_template" + File.separator;

		String pathDestino = path.toString() + File.separator + "odt_final" + File.separator;
		
		List<String> erros = new ArrayList<String>();
		
		if (!new File(pathDestino).exists()) {
			(new File(pathDestino)).mkdirs();
		}
		
		AtoDAO atoDAO = new AtoDAO();
		ResultSet consulta = atoDAO.getAtos(tipo);

		try {
			while (consulta.next()) {
				String nmArquivoOrigem = String.format("%08d", consulta.getLong("nr_protocolo_legado"));
				
				System.out.println("==>" + nmArquivoOrigem);
				
				File origem = new File(pathOrigem+nmArquivoOrigem+".odt");
				if(origem.exists()) {
					String nmArquivoDestino = String.format("%07d", consulta.getLong("id_ato_protocolado"));
					
					FileUtils.copyFile(origem, new File(pathDestino+nmArquivoDestino+".odt"));
				} else {
					erros.add(consulta.getString("nr_protocolo_legado"));
				}
			}
			
			System.out.println("======================= E R R O S =======================");
			for(String prot : erros) {
				System.out.println(prot);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
