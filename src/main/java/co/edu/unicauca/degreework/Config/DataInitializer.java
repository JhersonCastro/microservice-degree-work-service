package co.edu.unicauca.degreework.Config;

import co.edu.unicauca.degreework.Enum.Modality;
import co.edu.unicauca.degreework.Enum.Status;
import co.edu.unicauca.degreework.Model.DegreeWork;
import co.edu.unicauca.degreework.Repository.DegreeWorkRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Configuration
public class DataInitializer {

    @Autowired
    private DegreeWorkRepository degreeWorkRepository;

    @PostConstruct
    public void init() {
        if (degreeWorkRepository.count() == 0) {
            DegreeWork dw1 = new DegreeWork();
            dw1.setTitle("Sistema de monitoreo sísmico");
            dw1.setDescription("Análisis de ondas sísmicas mediante IA para detección temprana.");
            dw1.setIdDirector(1L);
            dw1.setIdCoordinator(2L);
            dw1.setStudentIds(Set.of(101L, 102L));
            dw1.setStatus(Status.CREATED);
            dw1.setModality(Modality.INVESTIGACION);
            dw1.setCreatedAt(LocalDateTime.now().minusDays(15));

            DegreeWork dw2 = new DegreeWork();
            dw2.setTitle("Desarrollo de plataforma de pedidos en tiempo real");
            dw2.setDescription("Aplicación web para gestión y automatización de pedidos con WebSockets.");
            dw2.setIdDirector(1L);
            dw2.setIdCoordinator(2L);
            dw2.setStudentIds(Set.of(103L));
            dw2.setStatus(Status.FORMAT_A);
            dw2.setModality(Modality.PRACTICA_PROFESIONAL);
            dw2.setCreatedAt(LocalDateTime.now().minusDays(10));

            DegreeWork dw3 = new DegreeWork();
            dw3.setTitle("Sistema de predicción de abandono universitario");
            dw3.setDescription("Modelo de predicción usando aprendizaje automático y datos históricos.");
            dw3.setIdDirector(1L);
            dw3.setIdCoordinator(2L);
            dw3.setStudentIds(Set.of(104L, 105L));
            dw3.setStatus(Status.FORMAT_A_ACCEPTED);
            dw3.setModality(Modality.INVESTIGACION);
            dw3.setCreatedAt(LocalDateTime.now().minusDays(7));

            DegreeWork dw4 = new DegreeWork();
            dw4.setTitle("Control de inventarios con IoT");
            dw4.setDescription("Sistema de sensores inteligentes para control de inventario automatizado.");
            dw4.setIdDirector(1L);
            dw4.setIdCoordinator(2L);
            dw4.setStudentIds(Set.of(106L));
            dw4.setStatus(Status.DRAFT);
            dw4.setModality(Modality.PRACTICA_PROFESIONAL);
            dw4.setCreatedAt(LocalDateTime.now().minusDays(5));

            DegreeWork dw5 = new DegreeWork();
            dw5.setTitle("Integración Coteriminal Plan para doble titulación");
            dw5.setDescription("Implementación del modelo académico coteriminal plan en la universidad.");
            dw5.setIdDirector(1L);
            dw5.setIdCoordinator(2L);
            dw5.setStudentIds(Set.of(107L));
            dw5.setStatus(Status.APROVED);
            dw5.setModality(Modality.COTERMINAL_PLAN);
            dw5.setCreatedAt(LocalDateTime.now().minusDays(2));

            degreeWorkRepository.saveAll(List.of(dw1, dw2, dw3, dw4, dw5));
            System.out.println("Datos iniciales de DegreeWork insertados correctamente.");
        } else {
            System.out.println("ℹDatos existentes detectados. No se insertaron nuevos registros.");
        }
    }
}
