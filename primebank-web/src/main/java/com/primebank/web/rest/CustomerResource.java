package com.primebank.web.rest;

import com.primebank.core.dto.request.CustomerSaveRequestDTO;
import com.primebank.core.dto.response.ResponseDTO;
import com.primebank.core.entity.enums.Gender;
import com.primebank.ejb.service.UserService;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import java.io.InputStream;
import java.time.LocalDate;

@Path("/customers")
public class CustomerResource {

    @Inject
    private UserService userService;

    @POST
    @Path("/save")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseDTO<String> createCustomer(
            @FormDataParam("photo") InputStream uploadedInputStream,
            @FormDataParam("photo") FormDataContentDisposition fileDetails,
            @FormDataParam("fullName") String fullName,
            @FormDataParam("email") String email,
            @FormDataParam("address") String address,
            @FormDataParam("nic") String nic,
            @FormDataParam("birthCertificateNo") String birthCertificateNo,
            @FormDataParam("contactNo") String contactNo,
            @FormDataParam("dateOfBirth") String dateOfBirth,
            @FormDataParam("gender") String genderStr
            ) {

        try {

            byte[] imageBytes = uploadedInputStream != null ? uploadedInputStream.readAllBytes() : null;

            CustomerSaveRequestDTO dto = new CustomerSaveRequestDTO();
            dto.setFullName(fullName);
            dto.setEmail(email);
            dto.setAddress(address);
            dto.setNic(nic);
            dto.setBirthCertificateNo(birthCertificateNo);
            dto.setContactNo(contactNo);
            dto.setDateOfBirth(LocalDate.parse(dateOfBirth));
            dto.setGender(Gender.valueOf(genderStr.toUpperCase()));
            dto.setPhoto(imageBytes);

            return userService.createCustomer(dto);
        } catch (Exception e) {
            ResponseDTO<String> responseDTO = new ResponseDTO<>();
            responseDTO.setMessage(e.getMessage());

            return responseDTO;
        }
    }
}
