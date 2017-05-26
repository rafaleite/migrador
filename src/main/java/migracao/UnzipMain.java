package migracao;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import migracao.dao.MinutaDAO;
import migracao.domain.ETipoAto;
import migracao.util.FileUtil;
import migracao.util.RTFUtil;

public class UnzipMain {

	public static void main(String[] args) {
		MinutaDAO minutaDAO = new MinutaDAO();
		ResultSet consulta = minutaDAO.getMinutas(ETipoAto.ATO04, null);

		String fullDir;

		StringBuilder path = new StringBuilder("C:\\arquivos\\migracao\\").append(ETipoAto.ATO04.name());

		try {
			while (consulta.next()) {
				
				// SALVAR O ZIP
				byte[] zipFile = consulta.getBytes("Texto");

				StringBuilder fileName = new StringBuilder(consulta.getString("Prot")).append("_")
						.append(consulta.getString("Tipo"));

				String fileNameZip = fileName.toString();
				
				System.out.println("ARQUIVO: "+ fileNameZip);
				
				fullDir = path.toString() + File.separator + consulta.getString("Prot");

				FileUtil.createFileFromByteArray(zipFile, fileNameZip, fullDir);

				// UNZIP
				String fileNameRTF = fileName.toString() + ".RTF";

				FileUtil.decompressGzipFile(fullDir + File.separator + fileNameZip,
						fullDir + File.separator + fileNameRTF);

				// CONVERTER PRA ODT

				// MERGE
			}

//			RTFUtil.mergeRTF(new File("C:\\arquivos\\migracao\\ATO01\\00002792\\00002792_T.RTF"),
//					new File("C:\\arquivos\\migracao\\ATO01\\00002792\\00002792_1.RTF"),
//					new File("C:\\arquivos\\migracao\\ATO01\\00002792\\00002792.RTF"));

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
