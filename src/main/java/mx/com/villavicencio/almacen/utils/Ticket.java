package mx.com.villavicencio.almacen.utils;

import java.math.BigDecimal;
import java.util.Random;
import javax.swing.JOptionPane;
import jpos.JposConst;
import jpos.JposException;
import jpos.POSPrinter;
import jpos.POSPrinterConst;
import jpos.events.ErrorEvent;
import jpos.events.ErrorListener;
import jpos.events.OutputCompleteEvent;
import jpos.events.OutputCompleteListener;
import jpos.events.StatusUpdateEvent;
import jpos.events.StatusUpdateListener;
import jpos.util.JposPropertiesConst;
import mx.com.villavicencio.almacen.commons.messages.ApplicationMessages;
import mx.com.villavicencio.almacen.properties.PropertiesBean;
import mx.com.villavicencio.almacen.properties.Property;
import mx.com.villavicencio.almacen.system.venta.nota.detalle.dto.DtoDetalleNotaVenta;
import mx.com.villavicencio.almacen.system.venta.nota.nota.dto.DtoNotaVenta;

/**
 *
 * @author Gabriel J
 */
public class Ticket implements OutputCompleteListener, StatusUpdateListener, ErrorListener {

    private final String ESC = ((char) 0x1b) + "";
    private final String LF = ((char) 0x0a) + "";
    private final String[] headers = {"PRODUCTO", "CANT", "KGS", "PRECIO "};
    private final String SPACES = "                                                                      ";
    private final String DIRECCION = "CALLE 4 205-A COL. ARENAL DEL. AZCAPOTZALCO       MÉXICO DF 02980";
    private final String RFC = "XXXXXXXXXXX";

    @Override
    public void outputCompleteOccurred(OutputCompleteEvent oce) {
    }

    @Override
    public void statusUpdateOccurred(StatusUpdateEvent sue) {
    }

    @Override
    public void errorOccurred(ErrorEvent event) {
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
        }
        event.setErrorResponse(JposConst.JPOS_ER_RETRY);
    }

    public Boolean imprimir(DtoNotaVenta object) {
        Boolean success = Boolean.TRUE;
        String spaceMayor = getSpaceMayor(object);
        headers[0] += space(headers[0], spaceHeader(headers[0], spaceMayor));
        headers[1] += "  ";
        headers[2] += "  ";

        BigDecimal total = BigDecimal.ZERO;
        System.setProperty(JposPropertiesConst.JPOS_POPULATOR_FILE_PROP_NAME, this.getClass().getResource("/jpos.xml").getFile());
        POSPrinter printer = new POSPrinter();
        try {
            printer.addOutputCompleteListener(this);
            printer.addStatusUpdateListener(this);
            printer.addErrorListener(this);
            printer.open("Star TSP100 Cutter (TSP143)_1");
            printer.claim(1);
            printer.setDeviceEnabled(Boolean.TRUE);
            printer.setAsyncMode(Boolean.TRUE);
            printer.setMapMode(POSPrinterConst.PTR_MM_METRIC);
            do {
                if (printer.getCoverOpen() == true) {
                    ShowDialog(PropertiesBean.getPosFile().getProperty(Property.ERROR_BANDEJA));
                    success = Boolean.FALSE;
                }
                if (printer.getRecEmpty() == true) {
                    ShowDialog(PropertiesBean.getPosFile().getProperty(Property.ERROR_PAPEL));
                    success = Boolean.FALSE;
                }
                printer.transactionPrint(POSPrinterConst.PTR_S_RECEIPT, POSPrinterConst.PTR_TP_TRANSACTION);
                if (printer.getCapRecBitmap() == Boolean.TRUE) {
                    try {
                        printer.printBitmap(POSPrinterConst.PTR_S_RECEIPT, this.getClass().getResource("/logo.gif").getFile(), POSPrinterConst.PTR_BM_ASIS, POSPrinterConst.PTR_BM_CENTER);
                    } catch (JposException e) {
                        if (e.getErrorCode() != JposConst.JPOS_E_NOEXIST) {
                            throw e;
                        }
                    }
                }
                printer.printNormal(POSPrinterConst.PTR_S_RECEIPT, ESC + "|cA" + ESC + "|bC" + DIRECCION + LF);
                printer.printNormal(POSPrinterConst.PTR_S_RECEIPT, ESC + "|cA" + ESC + "|bC" + RFC + LF);
                printer.printNormal(POSPrinterConst.PTR_S_RECEIPT, ESC + "|1C" + "FECHA " + DateUtils.dateToString(object.getFecha()) + ESC + "|1C" + " FOLIO " + object.getFolio() + LF);
                printer.printNormal(POSPrinterConst.PTR_S_RECEIPT, ESC + "|lA" + "CLIENTE " + object.getNombreCliente() + LF);
                printer.printNormal(POSPrinterConst.PTR_S_RECEIPT, ESC + "|uC" + headers[0] + " " + headers[1] + headers[2] + headers[3]
                        + SPACES.substring(0, printer.getRecLineChars() - "                                       ".length()) + LF);
                for (DtoDetalleNotaVenta detalles : object.getDetalles()) {
                    StringBuilder sb = new StringBuilder();
                    total = total.add(detalles.getSubTotal());
                    String nombre = detalles.getNombreProducto() + space(detalles.getNombreProducto(), spaceMayor);
                    String kilos = NumberUtils.formatMiles(detalles.getCantidadKilos());
                    if(kilos.length() == 1){
                        kilos += "       ";
                    } else if (kilos.length() == 2) {
                        kilos += "      ";
                    }else if (kilos.length() == 3) {
                        kilos += "     ";
                            }else if (kilos.length() == 4) {
                        kilos += "    ";
                    } else if (kilos.length() == 5) {
                        kilos += "   ";
                    } else if (kilos.length() == 6) {
                        kilos += "  ";
                    } else if (kilos.length() >= 7) {
                        kilos += " ";
                    }
                    sb.append(nombre)
                            .append(" ")
                            .append(detalles.getCantidadPiezas() < 9 ? "0" + detalles.getCantidadPiezas().toString() : detalles.getCantidadPiezas().toString())
                            .append("     ")
                            .append(kilos)
                            .append(NumberUtils.formatMoney(detalles.getSubTotal()));
                    printer.printNormal(POSPrinterConst.PTR_S_RECEIPT, sb.toString() + LF);
                }
                printer.printNormal(POSPrinterConst.PTR_S_RECEIPT, ESC + "|rA" + "Subtotal:  0.00" + LF);
                printer.printNormal(POSPrinterConst.PTR_S_RECEIPT, ESC + "|rA" + "  IVA    : 0.00" + LF);
                printer.printNormal(POSPrinterConst.PTR_S_RECEIPT, ESC + "|rA" + ESC + "|bC" + "Total: " + NumberUtils.formatMoney(total) + LF);
                printer.printNormal(POSPrinterConst.PTR_S_RECEIPT, LF);
                printer.printNormal(POSPrinterConst.PTR_S_RECEIPT, ESC + "|uC" + ESC + "|cA" + ESC + "|4C" + ESC + "|bC" + "FIRMA RECIBIDO"
                        + SPACES.substring(0, printer.getRecLineChars() - "               ".length()) + LF);
                if (printer.getCapRecBarCode() == true) {
                    printer.printBarCode(POSPrinterConst.PTR_S_RECEIPT, codigoBarra(), POSPrinterConst.PTR_BCS_Code39,
                            10 * 100, 60 * 100, POSPrinterConst.PTR_BC_CENTER, POSPrinterConst.PTR_BC_TEXT_BELOW);
                }
                printer.printNormal(POSPrinterConst.PTR_S_RECEIPT, ESC + "|100fP");
                printer.transactionPrint(POSPrinterConst.PTR_S_RECEIPT, POSPrinterConst.PTR_TP_NORMAL);
            } while (Boolean.FALSE);
        } catch (JposException ex) {
            ApplicationMessages.errorMessage(PropertiesBean.getPosFile().getProperty(Property.ERROR_PRINT), ex);
            success = Boolean.FALSE;
        } catch (Exception ex) {
            ApplicationMessages.errorMessage(PropertiesBean.getPosFile().getProperty(Property.ERROR_PRINT), ex);
            success = Boolean.FALSE;
        } finally {
            if (printer.getState() != JposConst.JPOS_S_CLOSED) {
                try {
                    while (printer.getState() != JposConst.JPOS_S_IDLE) {
                        Thread.sleep(0);
                    }
                    printer.close();
                } catch (InterruptedException | JposException e) {
                }
            }
        }
        return success;
    }

    private String codigoBarra() {
        StringBuilder codigo = new StringBuilder();
        StringBuilder numeroFaltante = new StringBuilder();
        Random numAleatorio = new Random();
        Integer n = numAleatorio.nextInt(2147483647 - 1 + 1) + 100;
        codigo.append(String.valueOf(n));
        for (Integer x = n.toString().length(); x < 12; x++) {
            int numero = (int) (Math.random() * 9 + 0);
            numeroFaltante.append(String.valueOf(numero));
        }
        codigo.append(numeroFaltante);
        return codigo.toString();
    }

    private void ShowDialog(String text) {
        JOptionPane.showMessageDialog(null, text, PropertiesBean.getErrorFile().getProperty(Property.ERROR_TITLE_PRINT), JOptionPane.ERROR_MESSAGE, null);
    }

    private String getSpaceMayor(DtoNotaVenta object) {
        Integer index = 0;
        Integer numMayor = 0;
        StringBuilder sb = null;
        for (DtoDetalleNotaVenta detalles : object.getDetalles()) {
            if (index > 0) {
                Integer num = detalles.getNombreProducto().length();
                if (num > numMayor) {
                    sb = new StringBuilder();
                    numMayor = detalles.getNombreProducto().length();
                    for (Integer j = 0; j < numMayor; j++) {
                        sb.append(" ");
                    }
                }
            } else {
                numMayor = detalles.getNombreProducto().length();
                index++;
            }
        }
        return sb.toString();
    }

    private String spaceHeader(String header, String sb) {
        String space = "";
        for (Integer x = header.length(); x < sb.length(); x++) {
            space += " ";
        }
        return (header + space);
    }

    private String space(String object, String sb) {
        String space = "";
        for (Integer x = object.length(); x < sb.length(); x++) {
            space += " ";
        }
        return space;
    }
}
