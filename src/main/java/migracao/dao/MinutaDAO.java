package migracao.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import migracao.domain.ETipoAto;
import migracao.util.MysqlConnect;

public class MinutaDAO {
	
	private MysqlConnect connection;
	
	public MinutaDAO() {
		this.connection = MysqlConnect.getDbCon();
	}
	
	
	public ResultSet getMinutas(ETipoAto pTipoAto, String nrProtocolo) {
		StringBuilder sqlText = new StringBuilder("SELECT * FROM ").append(pTipoAto.getTabelaTXT());
		
		sqlText.append(" WHERE 1=1 ");
		if(nrProtocolo != null && !nrProtocolo.isEmpty()) {
			sqlText.append(" and Prot = '").append(nrProtocolo).append("'");
		}
		
		try {
			return connection.query(sqlText.toString());
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
