package pe.gob.osinergmin.sicoes.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pe.gob.osinergmin.sicoes.model.RequerimientoAprobacion;
import pe.gob.osinergmin.sicoes.repository.RequerimientoAprobacionDao;
import pe.gob.osinergmin.sicoes.service.RequerimientoAprobacionService;
import pe.gob.osinergmin.sicoes.util.Contexto;

@Service
public class RequerimientoAprobacionServiceImpl implements RequerimientoAprobacionService {

    @Autowired
    private RequerimientoAprobacionDao requerimientoAprobacionDao;

    @Override
    public Page<RequerimientoAprobacion> obtenerHistorial(String uuid, Contexto contexto, Pageable pageable) {
        return requerimientoAprobacionDao.obtenerAprobaciones(uuid, pageable);
    }

    @Override
    public RequerimientoAprobacion guardar(RequerimientoAprobacion model, Contexto contexto) {
        return null;
    }

    @Override
    public RequerimientoAprobacion obtener(Long aLong, Contexto contexto) {
        return null;
    }

    @Override
    public void eliminar(Long aLong, Contexto contexto) {

    }
}
