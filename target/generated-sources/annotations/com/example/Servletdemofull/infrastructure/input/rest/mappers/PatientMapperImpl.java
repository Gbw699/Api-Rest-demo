package com.example.Servletdemofull.infrastructure.input.rest.mappers;

import com.example.Servletdemofull.infrastructure.input.rest.dtos.PatientDto;
import com.example.Servletdemofull.infrastructure.output.entity.Patient;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-07-06T10:52:12-0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 20.0.1 (Oracle Corporation)"
)
@Component
public class PatientMapperImpl implements PatientMapper {

    @Override
    public Patient fromUserDto(PatientDto patientDto) {
        if ( patientDto == null ) {
            return null;
        }

        Patient patient = new Patient();

        patient.setId( patientDto.getId() );
        patient.setName( patientDto.getName() );
        patient.setLastName( patientDto.getLastName() );
        patient.setEmail( patientDto.getEmail() );
        patient.setCellphoneNumber( patientDto.getCellphoneNumber() );

        return patient;
    }
}
