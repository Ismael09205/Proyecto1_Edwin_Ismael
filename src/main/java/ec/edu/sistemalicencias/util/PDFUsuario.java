package ec.edu.sistemalicencias.util;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;
import ec.edu.sistemalicencias.model.entities.Usuarios;

import java.io.FileOutputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class PDFUsuario {
    private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final Font FONT_TITULO = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, BaseColor.DARK_GRAY);
    private static final Font FONT_SUBTITULO = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14, BaseColor.BLUE);
    private static final Font FONT_NORMAL = FontFactory.getFont(FontFactory.HELVETICA, 10, BaseColor.BLACK);
    private static final Font FONT_CABECERA = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10, BaseColor.WHITE);

    /**
     * Genera un reporte PDF con la lista de usuarios de la base de datos
     */
    public static void generarReporteUsuariosPDF(List<Usuarios> listaUsuarios, String rutaArchivo)
            throws DocumentException, IOException {


        Document documento = new Document(PageSize.A4.rotate());
        PdfWriter.getInstance(documento, new FileOutputStream(rutaArchivo));

        documento.open();
        agregarEncabezado(documento);
        agregarTablaUsuarios(documento, listaUsuarios);
        agregarPiePagina(documento);

        documento.close();
    }

    private static void agregarEncabezado(Document documento) throws DocumentException {
        Paragraph titulo = new Paragraph("REPÚBLICA DEL ECUADOR", FONT_TITULO);
        titulo.setAlignment(Element.ALIGN_CENTER);
        documento.add(titulo);

        Paragraph subtitulo = new Paragraph("SISTEMA DE GESTIÓN DE LICENCIAS - REPORTE DE USUARIOS", FONT_SUBTITULO);
        subtitulo.setAlignment(Element.ALIGN_CENTER);
        subtitulo.setSpacingAfter(15);
        documento.add(subtitulo);

        LineSeparator linea = new LineSeparator();
        linea.setLineColor(BaseColor.BLUE);
        documento.add(linea);
        documento.add(Chunk.NEWLINE);
    }

    private static void agregarTablaUsuarios(Document documento, List<Usuarios> listaUsuarios) throws DocumentException {

        PdfPTable tabla = new PdfPTable(7);
        tabla.setWidthPercentage(100);
        tabla.setWidths(new float[]{10f, 15f, 20f, 15f, 15f, 15f, 10f}); // Ajuste de anchos


        String[] headers = {"ID", "Cédula", "Nombre Completo", "Teléfono", "Dirección", "Rol", "Registro"};
        for (String header : headers) {
            PdfPCell cell = new PdfPCell(new Phrase(header, FONT_CABECERA));
            cell.setBackgroundColor(BaseColor.BLUE);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setPadding(5);
            tabla.addCell(cell);
        }


        for (Usuarios u : listaUsuarios) {
            tabla.addCell(new Phrase(String.valueOf(u.getId()), FONT_NORMAL));
            tabla.addCell(new Phrase(u.getCedula(), FONT_NORMAL));
            tabla.addCell(new Phrase(u.getNombre() + " " + u.getApellido(), FONT_NORMAL));
            tabla.addCell(new Phrase(u.getTelefono(), FONT_NORMAL));
            tabla.addCell(new Phrase(u.getDireccion(), FONT_NORMAL));
            tabla.addCell(new Phrase(u.getRol(), FONT_NORMAL));

            String fecha = (u.getFecha_registro() != null) ? u.getFecha_registro().format(FORMATO_FECHA) : "N/A";
            tabla.addCell(new Phrase(fecha, FONT_NORMAL));
        }

        documento.add(tabla);
    }

    private static void agregarPiePagina(Document documento) throws DocumentException {
        documento.add(Chunk.NEWLINE);
        Paragraph fechaGen = new Paragraph(
                "Reporte generado el: " + java.time.LocalDateTime.now().format(
                        DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")
                ),
                FontFactory.getFont(FontFactory.HELVETICA, 8, BaseColor.GRAY)
        );
        fechaGen.setAlignment(Element.ALIGN_RIGHT);
        documento.add(fechaGen);
    }
}