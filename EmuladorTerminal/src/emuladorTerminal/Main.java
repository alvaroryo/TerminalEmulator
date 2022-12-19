package emuladorTerminal;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Main {

	public static void main(String[] args) {
		Scanner entrada = new Scanner(System.in);
		boolean terminado = false;
		String rutaDefault = "C:\\Users\\2DAM\\Desktop\\prueba";
		while (!terminado) {

			System.out.print("alvaro@alvaroPC >> ");

			String comando = entrada.nextLine();
			String[] partesComando = comando.split(" "); // C:\Users\2DAM\Desktop\prueba

			// -------------------LS----------------------------
			if (partesComando[0].equals("ls")) {

				if (existeIndice(partesComando, 1)) { // Si no esta solo el comando ls
					if (partesComando[1].equals("-l")) {
						if (existeIndice(partesComando, 2)) { // Es comando -l y hay ruta
							File ruta = new File(partesComando[2]);
							String[] resultado = listadoAvanzado(ruta);
							imprimeArray(resultado);
							
						} else { // Es comando -l y no tiene ruta
							File ruta = new File(rutaDefault);
							String[] resultado = listadoAvanzado(ruta);
							imprimeArray(resultado);
						}

					} else { // No es comando -l y tiene ruta
						File ruta = new File(partesComando[1]);
						String[] resultado = ruta.list();
						Arrays.sort(resultado);
						imprimeArray(resultado);

					}
				}

				else { // Si esta solo el comando ls
					File ruta = new File(rutaDefault);
					String[] resultado = ruta.list();
					Arrays.sort(resultado);
					imprimeArray(resultado);
				}
				
			}

			// -------------------MKDIR----------------------------
			else if (partesComando[0].equals("Mkdir")) {
				if (partesComando[1].contains("\\")) { // Si tiene ruta crea directorio ahi
					File ruta = new File(partesComando[1]);
					ruta.mkdir();
				} else {
					File ruta = new File(rutaDefault + "\\" + partesComando[1]);
					ruta.mkdir();
				}
			}

			// -------------------CAT----------------------------
			else if (partesComando[0].equals("cat")) {
				if (partesComando[1].contains("\\")) { // Si tiene ruta crea directorio ahi
					File ruta = new File(partesComando[1]);
					try {
						ruta.createNewFile();
					} catch (IOException e) {
						System.out.println("El archivo no ha podido ser creado.");
						e.printStackTrace();
					}
				} 
				else {	
					File ruta = new File(rutaDefault + "\\" + partesComando[1]);
					try {
						ruta.createNewFile();
					} catch (IOException e) {
						System.out.println("El archivo no ha podido ser creado.");
						e.printStackTrace();
					}
				}
			}

			// -------------------RM----------------------------
			else if (partesComando[0].equals("rm")) {
				if (partesComando[1].contains("\\")) { // Si tiene ruta crea directorio ahi
					File ruta = new File(partesComando[1]);
					ruta.delete();
				} else {
					File ruta = new File(rutaDefault + "\\" + partesComando[1]);
					ruta.delete();
				}
			}

			else {
				System.out.println("Comando no valido.");
			}

		}
	}

//-------------------EXISTE INDICE EN ARRAY-------------------------
	public static boolean existeIndice(String[] array, int indice) {
		return indice >= 0 && indice < array.length;
	}

//-------------------RESULTADO POR CONSOLA-------------------------
	public static void imprimeArray(String[] resultado) {
		for (int i = 0; i < resultado.length; i++) {
			System.out.println(resultado[i]);
		}
	}

//-------------------INFORMACION AVANZADA LS------------------------ 
	public static String[] listadoAvanzado(File fichero) {
		String[] componentes = fichero.list();
		String[] resultado = new String[componentes.length];

		// nombre,tipo(archivo o carpeta), fecha de ultimo acceso, permisos(RWX)
		// getName(), isFile() isDirectory(), lastAccessTime(), canWrite() canRead()
		// canExecute()

		for (int i = 0; i < componentes.length; i++) {
			File archivo = new File(fichero.getAbsolutePath() +"\\"+ componentes[i]);
			// NOMBRE
			resultado[i] =archivo.getName();
			// TIPO
			if (archivo.isFile()) {
				resultado[i] += " File";
			} else if (archivo.isDirectory()) {
				resultado[i] += " Directory";
			}

			// FECHA ULTIMO ACCESO
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			resultado[i] += " "+sdf.format(archivo.lastModified());

			// PERMISOS ARCHICO
			if (fichero.canWrite()) {
				resultado[i] += " write";
			}

			if (fichero.canRead()) {
				resultado[i] += " read";
			}

			if (fichero.canExecute()) {
				resultado[i] += " execute";
			}
		}

		return resultado;
	}
}
