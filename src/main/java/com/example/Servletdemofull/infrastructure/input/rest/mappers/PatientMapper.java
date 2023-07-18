package com.example.Servletdemofull.infrastructure.input.rest.mappers;

import com.example.Servletdemofull.infrastructure.input.rest.dtos.PatientDto;
import com.example.Servletdemofull.infrastructure.output.entity.Patient;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PatientMapper {

    @Named("fromPatientDto")
    Patient fromPatientDto(PatientDto patientDto);

    @Named("fromPatient")
    PatientDto fromPatient(Patient patient);

}
