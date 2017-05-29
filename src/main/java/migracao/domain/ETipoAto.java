package migracao.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public enum ETipoAto {
	ATO01("TXTATO01", 1), ATO02("TXTATO02", 2), ATO03("TXTATO03", 3), ATO04("TXTATO04", 4);

	@Getter
	private String tabelaTXT;

	@Getter
	private int idAto;

}
