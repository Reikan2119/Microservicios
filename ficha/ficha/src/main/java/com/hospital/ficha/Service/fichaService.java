package com.hospital.ficha.Service;

import java.util.List;
import com.hospital.ficha.Repository.fichaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service    
public class fichaService {

    @Autowired
    private fichaRepository fichaRepository;

    public List<com.hospital.ficha.Model.fichaModel> getAllFichas() {
        return fichaRepository.findAll();
    }

    public com.hospital.ficha.Model.fichaModel getFichaById(Integer id) {
        return fichaRepository.findById(id).orElse(null);
    }

    public com.hospital.ficha.Model.fichaModel createFicha(com.hospital.ficha.Model.fichaModel ficha) {
        return fichaRepository.save(ficha);
    }

    public com.hospital.ficha.Model.fichaModel updateFicha(Integer id, com.hospital.ficha.Model.fichaModel fichaDetails) {
        com.hospital.ficha.Model.fichaModel ficha = fichaRepository.findById(id).orElse(null);
        if (ficha != null) {
            ficha.setExaminacion(fichaDetails.getExaminacion());
            ficha.setNotas(fichaDetails.getNotas());
            ficha.setRiesgo(fichaDetails.getRiesgo());
            ficha.setDiagnostico(fichaDetails.getDiagnostico());
            return fichaRepository.save(ficha);
        }
        return null;
    }

    





}
