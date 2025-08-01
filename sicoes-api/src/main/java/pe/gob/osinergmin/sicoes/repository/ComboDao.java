package pe.gob.osinergmin.sicoes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pe.gob.osinergmin.sicoes.model.Combo;

import java.util.List;

@Repository
public interface ComboDao extends JpaRepository<Combo, Long> {


	 @Query( value = " SELECT \n" +
			 "    ID_SUPERVISORA AS ID,\n" +
			 "    CASE \n" +
			 "        WHEN NO_RAZON_SOCIAL IS NOT NULL THEN NO_RAZON_SOCIAL\n" +
			 "        ELSE \n" +
			 "            TRIM(\n" +
			 "                NVL(AP_PATERNO, '') || ' ' || \n" +
			 "                NVL(AP_MATERNO, '') || ' ' || \n" +
			 "                NVL(NO_PERSONA, '') \n" +
			 "            )\n" +
			 "    END AS VALOR\n" +
			 "FROM SICOES_TM_SUPERVISORA\n" +
			 "WHERE :filtro IS NOT NULL AND\n" +
			 "    UPPER(\n" +
			 "        CASE \n" +
			 "            WHEN NO_RAZON_SOCIAL IS NOT NULL THEN NO_RAZON_SOCIAL\n" +
			 "            ELSE \n" +
			 "                TRIM(\n" +
			 "                   NVL(AP_PATERNO, '') || ' ' || \n" +
			 "                   NVL(AP_MATERNO, '') || ' ' || \n" +
			 "                   NVL(NO_PERSONA, '') \n" +
			 "                )\n" +
			 "        END\n" +
			 "    ) LIKE UPPER(:filtro || '%') ", nativeQuery = true)
	public List<Combo> listarContratistas(String filtro);

}
