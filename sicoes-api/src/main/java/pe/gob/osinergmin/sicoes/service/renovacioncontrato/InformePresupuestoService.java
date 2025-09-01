package pe.gob.osinergmin.sicoes.service.renovacioncontrato;

import pe.gob.osinergmin.sicoes.model.dto.renovacioncontrato.DocumentoInformePresupuestoRequestDTO;
import pe.gob.osinergmin.sicoes.model.dto.renovacioncontrato.DocumentoInformePresupuestoResponseDTO;
import pe.gob.osinergmin.sicoes.util.Contexto;

public interface InformePresupuestoService {
    DocumentoInformePresupuestoResponseDTO agregarDocumento(DocumentoInformePresupuestoRequestDTO requestDTO, Contexto contexto) throws Exception;
    DocumentoInformePresupuestoResponseDTO anularDocumento(DocumentoInformePresupuestoRequestDTO requestDTO, Contexto contexto) throws Exception;
}
