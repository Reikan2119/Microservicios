package doctor.hospitalDoctor.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import doctor.hospitalDoctor.model.Doctor;
import doctor.hospitalDoctor.repository.DoctorRepository;



@Service
public class DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;

    public List<Doctor> listar() {
        return doctorRepository.findAll();
    }

    public Doctor buscarPorId(Integer id) {
        return doctorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Doctor no encontrado"));
    }

    public Doctor guardar(Doctor doctor) {
        return doctorRepository.save(doctor);
    }

    public void eliminar(Integer id) {
        if (!doctorRepository.existsById(id)) {
            throw new RuntimeException("Doctor no existe");
        }
        doctorRepository.deleteById(id);
    }
}