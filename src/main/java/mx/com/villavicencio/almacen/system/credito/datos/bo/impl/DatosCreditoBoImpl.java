package mx.com.villavicencio.almacen.system.credito.datos.bo.impl;

import mx.com.villavicencio.almacen.system.credito.datos.bo.DatosCreditoBo;
import mx.com.villavicencio.almacen.system.credito.datos.dao.DatosCreditoDao;

/**
 *
 * @author Gabriel J
 */
public class DatosCreditoBoImpl implements DatosCreditoBo {

    private DatosCreditoDao datosCreditoDao;

   
    public void setDatosCreditoDao(DatosCreditoDao datosCreditoDao) {
        this.datosCreditoDao = datosCreditoDao;
    }

}
