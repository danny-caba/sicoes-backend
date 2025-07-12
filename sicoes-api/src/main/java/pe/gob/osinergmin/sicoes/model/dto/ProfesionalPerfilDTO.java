package pe.gob.osinergmin.sicoes.model.dto;

public class ProfesionalPerfilDTO {

    private Long idSupervisora;
    private String nombre;
    private String numeroDocumento;

    private ProfesionalPerfilDTO() {}

    public Long getIdSupervisora() { return idSupervisora; }
    public String getNombre() { return nombre; }
    public String getNumeroDocumento() { return numeroDocumento; }

    public static class Builder {
        private ProfesionalPerfilDTO dto;

        public Builder() {
            dto = new ProfesionalPerfilDTO();
        }

        public Builder idSupervisora(Long idSupervisora) {
            dto.idSupervisora = idSupervisora;
            return this;
        }

        public Builder nombre(String nombre) {
            dto.nombre = nombre;
            return this;
        }

        public Builder numeroDocumento(String numeroDocumento) {
            dto.numeroDocumento = numeroDocumento;
            return this;
        }

        public ProfesionalPerfilDTO build() {
            return dto;
        }
    }

    public static Builder builder() {
        return new Builder();
    }
}