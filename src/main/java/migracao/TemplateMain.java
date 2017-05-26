package migracao;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.odftoolkit.simple.TextDocument;
import org.odftoolkit.simple.style.StyleTypeDefinitions.HorizontalAlignmentType;
import org.odftoolkit.simple.text.Paragraph;

import migracao.domain.ETipoAto;

public class TemplateMain {

	private static StringBuilder path = new StringBuilder("C:\\arquivos\\migracao\\").append(ETipoAto.ATO04.name());
	private static String pathODT = path.toString() + File.separator + "odt" + File.separator;

	private static String pathODTemplate = path.toString() + File.separator + "odt_template" + File.separator;

	private static List<String> erros = new ArrayList<String>();

	public static void main(String[] args) {

		try {

			if (!new File(pathODTemplate).exists()) {
				(new File(pathODTemplate)).mkdirs();
			}

			File[] files = new File(pathODT).listFiles();
			converterODT(files);


			System.out.println("======================= E R R O S =======================");
			for (String prot : erros) {
				System.out.println(prot);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void converterODT(File[] files) {
		for (File file : files) {
			System.out.println("Protocolo: " + file.getName());

			try {
				TextDocument template = TextDocument.loadDocument("C:/arquivos/migracao/template.odt");
				TextDocument corpo = TextDocument.loadDocument(pathODT + file.getName());
				
				Iterator itrCorpo = corpo.getParagraphIterator();
				while (itrCorpo.hasNext()) {
					Paragraph element = (Paragraph) itrCorpo.next();
					element.setHorizontalAlignment(HorizontalAlignmentType.JUSTIFY);
				}
				
				Paragraph p1 = template.getParagraphByReverseIndex(0, false);

				template.insertContentFromDocumentAfter(corpo, p1, true);

				Iterator itr = template.getParagraphIterator();
				while (itr.hasNext()) {
					Paragraph element = (Paragraph) itr.next();
					template.removeParagraph(element);
					break;
				}

				template.save(new File(pathODTemplate+file.getName()));
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			

		}
	}

}
