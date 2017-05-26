package migracao.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import migracao.util.PostgresConnect;

public class AtoDAO {

	private PostgresConnect connection;

	public AtoDAO() {
		this.connection = PostgresConnect.getDbCon();
	}

	public ResultSet getAtos() {
		StringBuilder sqlText = new StringBuilder(
				"SELECT id_ato_protocolado, nr_livro, nr_folha_final, id_tipo_ato_legado, nr_protocolo_legado")
				.append(" FROM notas.nt_ato_protocolado")
				.append(" WHERE nr_protocolo_legado is not null");

		try {
			return connection.query(sqlText.toString());
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

}
