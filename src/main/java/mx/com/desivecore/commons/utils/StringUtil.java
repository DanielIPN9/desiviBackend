package mx.com.desivecore.commons.utils;

/**
 * Funciones para manipular string de la aplicación
 * 
 * @author Daniel Casas
 *
 */
public class StringUtil {
	/**
	 * Retira los espacios y da una longitud
	 * 
	 * @param text       el texto que se limpia
	 * @param stringSize la longitud máxima del texto
	 * @return el texto limpio
	 */
	public static String cleanString(String text, Integer stringSize) {

		String cleanedText = text.trim();
		cleanedText = cleanedText.replace(' ', ' ');
		cleanedText = cleanedText.trim();

		if (cleanedText.length() > stringSize) {
			cleanedText = cleanedText.substring(0, stringSize - 1);
		}
		return cleanedText;
	}

	/**
	 * Método que valida si existen comandos o caracteres especiales ocupados en SQL
	 * 
	 * @param field campo a validar
	 * 
	 * @return retorna el campo field sin los caracteres o comandos que contenga
	 */
	public String cleanStringToQuery(String field) {

		field = field.toUpperCase();

		field = field.contains("%") ? field.replace("%", " ") : field;

		field = field.contains("<") ? field.replace("<", " ") : field;

		field = field.contains(">") ? field.replace(">", " ") : field;

		field = field.contains("!") ? field.replace("!", " ") : field;

		field = field.contains("¡") ? field.replace("¡", " ") : field;

		field = field.contains("SELECT ") ? field.replace("SELECT ", "") : field;

		field = field.contains("FROM ") ? field.replace("FROM ", "") : field;

		field = field.contains("DELETE ") ? field.replace("DELETE ", "") : field;

		field = field.contains("INSERT ") ? field.replace("INSERT ", "") : field;

		return field;
	}

	/**
	 * Método para autocompletar longitudes de campos con espacios en 0 a la
	 * derecha o izquierda mediante el indicador direction respectivamente.
	 * 
	 * @param field     cadena de datos
	 * @param length    longitud máxima del campo a retornar
	 * @param direction valor booleano para indicar si los espacios serán agregados
	 *                  a la derecha (true) o a la izquierda (false)
	 * 
	 * @return cadena con agregación de espacios
	 */
	public String autocompleteSpace(String field, int length, boolean direction) {

		int counter = field.length() + 1;
		String spaces = "";

		while (counter <= length) {
			spaces = spaces + "0";
			counter++;
		}

		if (direction) {

			return field + spaces;

		} else {

			return spaces + field;
		}

	}

	public String cleanAccent(String value) {

		if (value.contains("á")) {
			value = value.replace("á", "a");
		}
		if (value.contains("Á")) {
			value = value.replace("Á", "A");
		}

		if (value.contains("é")) {
			value = value.replace("é", "e");
		}
		if (value.contains("É")) {
			value = value.replace("É", "E");
		}

		if (value.contains("í")) {
			value = value.replace("í", "i");
		}
		if (value.contains("Í")) {
			value = value.replace("Í", "I");
		}

		if (value.contains("ó")) {
			value = value.replace("ó", "o");
		}
		if (value.contains("Ó")) {
			value = value.replace("Ó", "O");
		}

		if (value.contains("ú")) {
			value = value.replace("ú", "u");
		}
		if (value.contains("Ú")) {
			value = value.replace("Ú", "U");
		}

		return value;
	}
}
