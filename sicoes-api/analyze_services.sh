#!/bin/bash

# Script para analizar interfaces de servicio y sus implementaciones
BASE_DIR="src/main/java/pe/gob/osinergmin/sicoes/service"
IMPL_DIR="$BASE_DIR/impl"

# Colores para output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m'

echo -e "${BLUE}========================================${NC}"
echo -e "${BLUE}  ANALISIS DE SERVICIOS E INTERFACES  ${NC}"
echo -e "${BLUE}========================================${NC}"
echo ""

# Arrays para almacenar resultados
declare -a interfaces_sin_impl
declare -a impl_sin_service_annotation

# Funcion para extraer el nombre de la interface desde el archivo
get_interface_name() {
    local file="$1"
    grep -o "public interface [A-Za-z_][A-Za-z0-9_]*" "$file" | awk '{print $3}'
}

# Funcion para verificar si un archivo es una interface
is_interface_file() {
    local file="$1"
    grep -q "public interface" "$file" 2>/dev/null
}

# Funcion para verificar si existe implementacion
implementation_exists() {
    local interface_name="$1"
    local impl_file="$IMPL_DIR/${interface_name}Impl.java"
    [[ -f "$impl_file" ]]
}

# Funcion para verificar la anotacion @Service
has_service_annotation() {
    local impl_file="$1"
    grep -q "@Service" "$impl_file" 2>/dev/null
}

echo -e "${YELLOW}1. Extrayendo interfaces de servicio...${NC}"
echo ""

# Buscar todas las interfaces en el directorio base (excluyendo impl/)
interface_count=0
for file in "$BASE_DIR"/*.java; do
    if [[ -f "$file" ]] && is_interface_file "$file"; then
        interface_name=$(get_interface_name "$file")
        if [[ -n "$interface_name" ]]; then
            ((interface_count++))
            echo "   Interface encontrada: $interface_name"
            
            # Verificar si existe implementacion
            if implementation_exists "$interface_name"; then
                impl_file="$IMPL_DIR/${interface_name}Impl.java"
                echo "     ✓ Implementacion encontrada: ${interface_name}Impl.java"
                
                # Verificar anotacion @Service
                if has_service_annotation "$impl_file"; then
                    echo "     ✓ Tiene anotacion @Service"
                else
                    echo "     ✗ NO tiene anotacion @Service"
                    impl_sin_service_annotation+=("${interface_name}Impl")
                fi
            else
                echo "     ✗ NO tiene implementacion"
                interfaces_sin_impl+=("$interface_name")
            fi
            echo ""
        fi
    fi
done

echo -e "${YELLOW}2. Verificando implementaciones adicionales en directorio impl/...${NC}"
echo ""

# Buscar implementaciones que podrian no tener interface correspondiente
for impl_file in "$IMPL_DIR"/*ServiceImpl.java; do
    if [[ -f "$impl_file" ]]; then
        impl_basename=$(basename "$impl_file" .java)
        interface_name=${impl_basename%Impl}
        interface_file="$BASE_DIR/${interface_name}.java"
        
        if [[ ! -f "$interface_file" ]] || ! is_interface_file "$interface_file"; then
            echo "   Implementacion sin interface: $impl_basename"
            
            # Verificar anotacion @Service de todas formas
            if has_service_annotation "$impl_file"; then
                echo "     ✓ Tiene anotacion @Service"
            else
                echo "     ✗ NO tiene anotacion @Service"
                impl_sin_service_annotation+=("$impl_basename")
            fi
            echo ""
        fi
    fi
done

echo -e "${BLUE}========================================${NC}"
echo -e "${BLUE}           RESUMEN DE RESULTADOS       ${NC}"
echo -e "${BLUE}========================================${NC}"
echo ""

echo -e "${GREEN}Total de interfaces encontradas: $interface_count${NC}"
echo ""

# Reportar interfaces sin implementacion
if [[ ${#interfaces_sin_impl[@]} -gt 0 ]]; then
    echo -e "${RED}INTERFACES SIN IMPLEMENTACION (${#interfaces_sin_impl[@]}):${NC}"
    for interface in "${interfaces_sin_impl[@]}"; do
        echo -e "  ${RED}✗${NC} $interface"
    done
    echo ""
else
    echo -e "${GREEN}✓ Todas las interfaces tienen implementacion${NC}"
    echo ""
fi

# Reportar implementaciones sin @Service
if [[ ${#impl_sin_service_annotation[@]} -gt 0 ]]; then
    echo -e "${RED}IMPLEMENTACIONES SIN @SERVICE (${#impl_sin_service_annotation[@]}):${NC}"
    for impl in "${impl_sin_service_annotation[@]}"; do
        echo -e "  ${RED}✗${NC} $impl"
    done
    echo ""
else
    echo -e "${GREEN}✓ Todas las implementaciones tienen @Service${NC}"
    echo ""
fi

# Estadisticas finales
total_issues=$((${#interfaces_sin_impl[@]} + ${#impl_sin_service_annotation[@]}))
if [[ $total_issues -eq 0 ]]; then
    echo -e "${GREEN}🎉 ¡Perfecto! No se encontraron problemas.${NC}"
else
    echo -e "${YELLOW}⚠️  Total de problemas encontrados: $total_issues${NC}"
fi

echo ""
echo -e "${BLUE}========================================${NC}"