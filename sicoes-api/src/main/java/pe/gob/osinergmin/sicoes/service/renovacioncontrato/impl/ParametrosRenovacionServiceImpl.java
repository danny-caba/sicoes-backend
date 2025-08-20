package pe.gob.osinergmin.sicoes.service.renovacioncontrato.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.gob.osinergmin.sicoes.model.ListadoDetalle;
import pe.gob.osinergmin.sicoes.repository.ListadoDetalleDao;
import pe.gob.osinergmin.sicoes.service.renovacioncontrato.ParametrosRenovacionService;

@Service
public class ParametrosRenovacionServiceImpl implements ParametrosRenovacionService {

    @Autowired
    private ListadoDetalleDao listadoDetalleDao;

    @Override
    public List<Map<String, Object>> listarTiposSolicitudes() {
        List<Map<String, Object>> tiposSolicitudes = new ArrayList<>();
        
        try {
            List<ListadoDetalle> tipos = listadoDetalleDao.listarListadoDetalle("TIPO_SOLICITUD");
            
            for (ListadoDetalle tipo : tipos) {
                Map<String, Object> tipoMap = new HashMap<>();
                tipoMap.put("id", tipo.getIdListadoDetalle());
                tipoMap.put("codigo", tipo.getCodigo());
                tipoMap.put("nombre", tipo.getDescripcion());
                tipoMap.put("valor", tipo.getValor());
                tipoMap.put("orden", tipo.getOrden());
                tipoMap.put("activo", true);
                tiposSolicitudes.add(tipoMap);
            }
        } catch (Exception e) {
            Map<String, Object> tipoDefault = new HashMap<>();
            tipoDefault.put("id", 1L);
            tipoDefault.put("codigo", "RENOVACION");
            tipoDefault.put("nombre", "Renovación de Contrato");
            tipoDefault.put("activo", true);
            tiposSolicitudes.add(tipoDefault);
        }
        
        return tiposSolicitudes;
    }

    @Override
    public List<Map<String, Object>> listarSubsectores() {
        List<Map<String, Object>> subsectores = new ArrayList<>();
        
        try {
            List<ListadoDetalle> sectores = listadoDetalleDao.listarListadoDetalle("SECTOR");
            
            for (ListadoDetalle sector : sectores) {
                List<ListadoDetalle> subsectoresSector = listadoDetalleDao.listarListadoDetalle("SUBSECTOR", sector.getIdListadoDetalle());
                
                for (ListadoDetalle subsector : subsectoresSector) {
                    Map<String, Object> subsectorMap = new HashMap<>();
                    subsectorMap.put("id", subsector.getIdListadoDetalle());
                    subsectorMap.put("codigo", subsector.getCodigo());
                    subsectorMap.put("nombre", subsector.getDescripcion());
                    subsectorMap.put("valor", subsector.getValor());
                    subsectorMap.put("orden", subsector.getOrden());
                    subsectorMap.put("idSector", sector.getIdListadoDetalle());
                    subsectorMap.put("nombreSector", sector.getDescripcion());
                    subsectorMap.put("activo", true);
                    subsectores.add(subsectorMap);
                }
            }
        } catch (Exception e) {
            Map<String, Object> subsectorDefault1 = new HashMap<>();
            subsectorDefault1.put("id", 1L);
            subsectorDefault1.put("codigo", "ELEC");
            subsectorDefault1.put("nombre", "Electricidad");
            subsectorDefault1.put("activo", true);
            subsectores.add(subsectorDefault1);
            
            Map<String, Object> subsectorDefault2 = new HashMap<>();
            subsectorDefault2.put("id", 2L);
            subsectorDefault2.put("codigo", "HIDRO");
            subsectorDefault2.put("nombre", "Hidrocarburos");
            subsectorDefault2.put("activo", true);
            subsectores.add(subsectorDefault2);
        }
        
        return subsectores;
    }
}