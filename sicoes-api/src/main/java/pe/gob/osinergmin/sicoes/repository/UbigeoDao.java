package pe.gob.osinergmin.sicoes.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import pe.gob.osinergmin.sicoes.model.Ubigeo;

@Repository
public interface UbigeoDao  extends PagingAndSortingRepository<Ubigeo, Long>{

	List<Ubigeo> findByCodigoProvinciaAndCodigoDistritoOrderByNombreDepartamento(String codigoProvincia,String codigoDistrito);
	List<Ubigeo> findByCodigoDepartamentoAndCodigoDistritoOrderByNombreProvincia(String codigoDepartamento,String codigoDistrito);
	List<Ubigeo> findByCodigoDepartamentoAndCodigoProvinciaOrderByNombreDistrito(String codigoDepartamento,String codigoProvincia);
}
