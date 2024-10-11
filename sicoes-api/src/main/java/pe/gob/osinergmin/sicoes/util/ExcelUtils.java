package pe.gob.osinergmin.sicoes.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFDataFormat;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtils {
	
	private static Logger logger = LogManager.getLogger(DateUtil.class);


	public static final int widthColum = 25;
	public static final String FONT_DEFAULT = "Arial";
	public static final int maxRegistroExcel = 50 * 1000;

	private static Font createFont(XSSFWorkbook libro, String fontName, Integer size, byte underLine, boolean bold) {
		return createFont(libro, fontName, size, underLine, bold, null);
	}

	private static Font createFont(XSSFWorkbook libro, String fontName, Integer size, byte underLine, boolean bold,
			Integer color) {
		XSSFFont font = libro.createFont();
		font.setFontName(fontName);
		Short tamanio = Short.valueOf(size + "");
		font.setFontHeightInPoints(tamanio);
		font.setUnderline(underLine);
		font.setBold(bold);
		if (color != null) {
			font.setColor(color.shortValue());
		}
		return font;
	}

	// public static Font
	private static XSSFCellStyle createCellStyle(XSSFWorkbook libro, Font font, HorizontalAlignment align) {
		XSSFCellStyle estiloCelda = libro.createCellStyle();
		estiloCelda.setFont(font);
		estiloCelda.setAlignment(align);
		return estiloCelda;
	}

	private static XSSFCellStyle getArial8Negrita(XSSFWorkbook excel, HorizontalAlignment align) {
		Font arial8Negrita = createFont(excel, FONT_DEFAULT, 8, XSSFFont.U_NONE, true);
		return createCellStyle(excel, arial8Negrita, align);
	}

	private static XSSFCellStyle getArial8Normal(XSSFWorkbook excel, HorizontalAlignment align) {
		Font arial8Negrita = createFont(excel, FONT_DEFAULT, 8, XSSFFont.U_NONE, false);
		return createCellStyle(excel, arial8Negrita, align);
	}

	private static XSSFCellStyle getArial12Negrita(XSSFWorkbook excel, HorizontalAlignment align) {
		Font arial12Negrita = createFont(excel, FONT_DEFAULT, 12, XSSFFont.U_NONE, true);
		return createCellStyle(excel, arial12Negrita, align);
	}

	private static XSSFCellStyle getFormatoCabecera(XSSFWorkbook excel, HorizontalAlignment align) {
		Font arial12Negrita = createFont(excel, FONT_DEFAULT, 8, XSSFFont.U_NONE, true,
				new Integer(IndexedColors.WHITE.index));

		XSSFCellStyle cell = createCellStyle(excel, arial12Negrita, align);

		// cell.setFillPattern(XSSFCellStyle.FINE_DOTS);
		cell.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		// cell.setFillBackgroundColor(IndexedColors.PALE_BLUE.getIndex());

		cell.setFillForegroundColor(IndexedColors.BLUE.index);
		// cell.setFillForegroundColor(IndexedColors.RED.getIndex());

		return cell;
	}

	private static int escribirPieArchivo(int fila, int numeroRegistros, DatosExportacion datos, XSSFWorkbook excel,XSSFSheet hoja)
			throws Exception {
		XSSFCellStyle estiloEtiquetaFiltro = getArial8Negrita(excel, HorizontalAlignment.LEFT);
		XSSFCellStyle estiloEtiquetaValor = getArial8Normal(excel, HorizontalAlignment.LEFT);		 
		fila += 2;
		XSSFRow row;
		XSSFCell celda;

		row = hoja.createRow(fila++);
		celda = row.createCell(0, XSSFCell.CELL_TYPE_STRING);
		celda.setCellStyle(estiloEtiquetaFiltro);
		celda.setCellValue("NÚMERO DE REGISTROS");

		celda = row.createCell(1, XSSFCell.CELL_TYPE_STRING);
		celda.setCellStyle(estiloEtiquetaValor);
		celda.setCellValue(": " + (numeroRegistros - datos.getDescuentoRegistros()));

		row = hoja.createRow(fila++);
		celda = row.createCell(0, XSSFCell.CELL_TYPE_STRING);
		celda.setCellStyle(estiloEtiquetaFiltro);
		celda.setCellValue("FECHA DE GENERACIÓN");

		celda = row.createCell(1, XSSFCell.CELL_TYPE_STRING);
		celda.setCellStyle(estiloEtiquetaValor);
		celda.setCellValue(": " + convertirFechaHoraFormato(datos.getFecha()));

		row = hoja.createRow(fila++);
		celda = row.createCell(0, XSSFCell.CELL_TYPE_STRING);
		celda.setCellStyle(estiloEtiquetaFiltro);
		celda.setCellValue("USUARIO");

		celda = row.createCell(1, XSSFCell.CELL_TYPE_STRING);
		celda.setCellStyle(estiloEtiquetaValor);
		celda.setCellValue(": " + datos.getUsuario().toUpperCase());

		return fila;
	}

	private static int escribirEncabezadoArchivo(int fila, DatosExportacion datos, XSSFWorkbook excel,XSSFSheet hoja)
			throws Exception {
		XSSFCellStyle estiloTitulo = getArial12Negrita(excel, HorizontalAlignment.LEFT);
		XSSFCellStyle estiloEtiquetaFiltro = getArial8Negrita(excel, HorizontalAlignment.LEFT);
		XSSFCellStyle estiloEtiquetaValor = getArial8Normal(excel, HorizontalAlignment.LEFT);
		XSSFRow row;
		XSSFCell celda;		
		row = hoja.createRow(fila++);
		celda = row.createCell(0, XSSFCell.CELL_TYPE_STRING);
		celda.setCellStyle(estiloTitulo);
		celda.setCellValue(datos.getTitulo().toUpperCase());
		hoja.addMergedRegion(new CellRangeAddress(0, (short) 0, 0, (short) datos.getNombreTitulos().length - 1));

		String[][] filtros = datos.getFiltros();

		if (filtros != null) {
			for (int i = 0; i < filtros.length; i++) {
				row = hoja.createRow(fila++);
				for (int j = 0; j < filtros[i].length; j++) {
					celda = row.createCell(j, XSSFCell.CELL_TYPE_STRING);
					if (filtros[i][j] != null && filtros[i][j].equals("SELECCIONE")) {
						celda.setCellValue("TODOS");
					} else {
						String value = filtros[i][j];
						if (value == null) {
							celda.setCellValue("");
						} else {
							celda.setCellValue(value.toUpperCase());
						}

					}
					if (j % 2 == 0) {
						celda.setCellStyle(estiloEtiquetaFiltro);
					} else {
						celda.setCellStyle(estiloEtiquetaValor);
					}
				}

			}
		}

		return ++fila;
	}

	public static InputStream generarArchivo(DatosExportacion datos) {
		List<DatosExportacion> listDatos = new ArrayList<DatosExportacion>();
		listDatos.add(datos);
		return generarArchivo(listDatos);
	}
	
	public static InputStream generarArchivo(List<DatosExportacion> listDatos) {
		InputStream elFichero = null;
		try {
			XSSFWorkbook excel = new XSSFWorkbook();
			for (int indice=1;indice<=listDatos.size();indice++) {
				DatosExportacion datos=listDatos.get(indice-1);
				XSSFSheet hoja = excel.createSheet(datos.getNombreHoja()==null?"Hoja "+indice:datos.getNombreHoja());

				XSSFRow row;
				XSSFCell celda;
				int fila = 0;
				int columna = 0;
				int numeroRegistros = 0;

				fila = escribirEncabezadoArchivo(fila, datos, excel, hoja);

				if (datos.getListado() != null) {
					int numeroColumnas = datos.getNombreTitulos().length;

					XSSFCellStyle formatoCadena = getArial8Normal(excel, HorizontalAlignment.LEFT);
					XSSFCellStyle formatoNumero = getArial8Normal(excel, HorizontalAlignment.RIGHT);
					XSSFCellStyle formatoNumeroDecimal = getArial8Normal(excel, HorizontalAlignment.RIGHT);
					XSSFDataFormat df = excel.createDataFormat();
					formatoNumeroDecimal.setDataFormat(df.getFormat("#,##0.00"));
					// Cabecera
					row = hoja.createRow(fila++);
					for (columna = 1; columna <= numeroColumnas; columna++) {
						String nombreColumna = datos.getNombreTitulos()[columna - 1];
						XSSFCellStyle formatoCabecera = obtenerFormatoElemento(columna, numeroColumnas,
								getFormatoCabecera(excel, HorizontalAlignment.CENTER), datos.getFormatoTitulos());
						celda = row.createCell(columna - 1);
						celda.setCellStyle(formatoCabecera);
						celda.setCellValue(nombreColumna.toUpperCase());
					}
					// Registros
					for (Object[] mRow : datos.getListado()) {
						numeroRegistros++;
						row = hoja.createRow(fila++);
						for (columna = 1; columna <= numeroColumnas; columna++) {
							Object oCel = mRow[columna - 1];
							Class tipoColumna = null;
							if (oCel == null) {
								tipoColumna = String.class;
							} else {
								tipoColumna = oCel.getClass();
							}
							celda = row.createCell(columna - 1);
							if (tipoColumna == Integer.class) {
								celda.setCellStyle(formatoNumero);
								Integer a = Integer.parseInt(mRow[columna - 1] + "");
								celda.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
								celda.setCellValue(a);
							} else if (tipoColumna == Double.class) {
								Double a = Double.parseDouble(mRow[columna - 1] + "");
								celda.setCellStyle(formatoNumeroDecimal);
								celda.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
								celda.setCellValue(a);
							} else if (tipoColumna == BigDecimal.class) {
								BigDecimal a = new BigDecimal(mRow[columna - 1] + "");
								celda.setCellStyle(formatoNumeroDecimal);
								celda.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
								celda.setCellValue(a.toString());
							} else {
								celda.setCellStyle(formatoCadena);
								celda.setCellType(XSSFCell.CELL_TYPE_STRING);
								if (mRow[columna - 1] == null) {
									celda.setCellValue("");
								} else {
									String value = mRow[columna - 1] + "";
									celda.setCellValue(value == null ? "" : value.toUpperCase());
								}
							}
						}
					}
				}
				fila = escribirPieArchivo(fila, numeroRegistros, datos, excel,hoja);

				for (int i = 0; i < datos.getNombreTitulos().length; i++) {
					hoja.autoSizeColumn(i);
				}

			}

			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			excel.write(outputStream);
			elFichero = new ByteArrayInputStream(outputStream.toByteArray());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return elFichero;
	}

	private static XSSFCellStyle obtenerFormatoElemento(int columna, int numeroColumnas, XSSFCellStyle formato,
			FormatoElemento[] formatos) throws Exception {
		if (formatos != null) {
			for (int i = 0; i < formatos.length; i++) {
				int ini = formatos[i].getIndiceIni();
				int fin = formatos[i].getIndiceFin();

				if (ini <= 0) {
					ini = numeroColumnas + ini;
					fin = numeroColumnas + fin;
				} else if (fin <= 0) {
					fin = numeroColumnas + fin;
				}

				if (columna >= ini && columna <= fin) {
					FormatoElemento formatoElemento = formatos[i];
//					if(formatoElemento.getOrientacion() != null) {
//						formato.setOrientation((Orientation) formatoElemento.getOrientacion());
//					}
//					if(formatoElemento.getTextoAjustado() != null) {
//						//formato.setWrapText((Boolean)formatoElemento.getTextoAjustado());						
//					}

				}
			}
		}

		return formato;
	}

	private static String convertirFechaHoraFormato(java.util.Date fecha) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		return sdf.format(fecha);
	}

}
