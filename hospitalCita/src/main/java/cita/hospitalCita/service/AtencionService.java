package cita.hospitalCita.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cita.hospitalCita.client.DoctorClient;
import cita.hospitalCita.client.PacienteClient;
import cita.hospitalCita.dto.AtencionDetalleDTO;
import cita.hospitalCita.dto.DoctorDTO;
import cita.hospitalCita.dto.PacienteDTO;
import cita.hospitalCita.dto.TipoAtencionDTO;
import cita.hospitalCita.model.Atencion;
import cita.hospitalCita.model.TipoAtencion;
import cita.hospitalCita.repository.AtencionRepository;



@Service
public class AtencionService {

    @Autowired
    private AtencionRepository repository;

    @Autowired
    private PacienteClient pacienteClient;

    @Autowired
    private DoctorClient doctorClient;

    public List<Atencion> listar() {
        return repository.findAll();
    }

    public Atencion guardar(Atencion atencion) {
        return repository.save(atencion);
    }

    public Atencion buscarPorId(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Atención no encontrada"));
    }

    public AtencionDetalleDTO obtenerDetalle(Integer id) {

        Atencion atencion = repository.findById(id).orElseThrow(() -> new RuntimeException("Atención no encontrada"));
        PacienteDTO paciente = pacienteClient.obtenerPaciente(atencion.getPacienteId());
        if (paciente == null) {
            throw new RuntimeException("Paciente no encontrado");
        }
        DoctorDTO doctor = doctorClient.obtenerDoctor(atencion.getDoctorId());
        if (doctor == null) {
            throw new RuntimeException("Doctor no encontrado");
        }
        TipoAtencion tipo = atencion.getTipoAtencion();
        TipoAtencionDTO tipoDTO = new TipoAtencionDTO(
                tipo.getId(),
                tipo.getNombre()
        );

        
        AtencionDetalleDTO dto = new AtencionDetalleDTO();
        dto.setId(atencion.getId());
        dto.setFechaAtencion(atencion.getFechaAtencion());
        dto.setDiagnostico(atencion.getDiagnostico());
        dto.setPaciente(paciente);
        dto.setDoctor(doctor);
        dto.setTipoAtencion(tipoDTO);

        return dto;
    }
}