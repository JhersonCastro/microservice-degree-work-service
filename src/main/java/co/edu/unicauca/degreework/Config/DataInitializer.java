package co.edu.unicauca.degreework.Config;

import co.edu.unicauca.degreework.DTO.CreateDegreeWorkDTO;
import co.edu.unicauca.degreework.Enum.Modality;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Set;

@Configuration
public class DataInitializer {

    private static final String DEGREEWORK_URL = "http://localhost:8082/api/degreework/create";

    @Bean
    CommandLineRunner initDegreeWorks() {
        return args -> {
            RestTemplate restTemplate = new RestTemplate();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            List<CreateDegreeWorkDTO> degreeWorks = List.of(
                    new CreateDegreeWorkDTO(
                            "Sistema de monitoreo sísmico",
                            "Análisis de ondas sísmicas mediante IA para detección temprana.",
                            1L, 2L,
                            Set.of(101L, 102L),
                            Modality.INVESTIGACION
                    ),
                    new CreateDegreeWorkDTO(
                            "Desarrollo de plataforma de pedidos en tiempo real",
                            "Aplicación web para gestión y automatización de pedidos con WebSockets.",
                            1L, 2L,
                            Set.of(103L),
                            Modality.PRACTICA_PROFESIONAL
                    ),
                    new CreateDegreeWorkDTO(
                            "Sistema de predicción de abandono universitario",
                            "Modelo de predicción usando aprendizaje automático y datos históricos.",
                            1L, 2L,
                            Set.of(104L, 105L),
                            Modality.INVESTIGACION
                    ),
                    new CreateDegreeWorkDTO(
                            "Control de inventarios con IoT",
                            "Sistema de sensores inteligentes para control de inventario automatizado.",
                            1L, 2L,
                            Set.of(106L),
                            Modality.PRACTICA_PROFESIONAL
                    ),
                    new CreateDegreeWorkDTO(
                            "Integración Coteriminal Plan para doble titulación",
                            "Implementación del modelo académico coteriminal plan en la universidad.",
                            1L, 2L,
                            Set.of(107L),
                            Modality.COTERMINAL_PLAN
                    )
            );

            for (CreateDegreeWorkDTO dto : degreeWorks) {
                try {
                    HttpEntity<CreateDegreeWorkDTO> request = new HttpEntity<>(dto, headers);
                    restTemplate.postForEntity(DEGREEWORK_URL, request, String.class);
                    System.out.println("[INFO] Trabajo de grado '" + dto.getTitle() + "' registrado exitosamente.");
                } catch (Exception e) {
                    System.out.println("[WARN] No se pudo registrar '" + dto.getTitle() + "': " + e.getMessage());
                }
            }

            System.out.println("[INFO] Proceso de inicialización de DegreeWorks finalizado.");
        };
    }
}
