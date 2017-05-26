package migracao.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public enum ETipoAto {
	ATO01("TXTATO01"),
	ATO02("TXTATO02"),
	ATO03("TXTATO03"),
	ATO04("TXTATO04");
	
	@Getter
	private String tabelaTXT;
	
}
