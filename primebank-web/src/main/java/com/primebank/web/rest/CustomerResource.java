package com.primebank.web.rest;

import com.primebank.core.dto.request.CustomerSaveRequestDTO;
import com.primebank.core.dto.response.ResponseDTO;
import com.primebank.core.entity.Customer;
import com.primebank.core.entity.enums.Gender;
import com.primebank.ejb.service.UserService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import java.io.InputStream;
import java.time.LocalDate;
import java.util.List;

@Path("/customers")
public class CustomerResource {

    @EJB
    private UserService userService;

    private CustomerSaveRequestDTO buildCustomerDto(
            InputStream uploadedInputStream,
            String fullName,
            String email,
            String address,
            String nic,
            String birthCertificateNo,
            String contactNo,
            String dateOfBirth,
            String genderStr) throws Exception{

        CustomerSaveRequestDTO dto = new CustomerSaveRequestDTO();
        dto.setFullName(fullName);
        dto.setEmail(email);
        dto.setAddress(address);
        dto.setNic(nic);
        dto.setBirthCertificateNo(birthCertificateNo);
        dto.setContactNo(contactNo);
        dto.setDateOfBirth(LocalDate.parse(dateOfBirth));
        dto.setGender(Gender.valueOf(genderStr.toUpperCase()));
        dto.setPhoto(uploadedInputStream != null ? uploadedInputStream.readAllBytes() : null);

        return dto;
    }

    @OPTIONS
    @Path("{any:.*}")
    public Response handlePreflight() {
        return Response.ok().build();
    }


    @RolesAllowed({"ADMIN", "TELLER", "MANAGER"})
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

            ResponseDTO<String> responseDTO = new ResponseDTO<>();
            if (fullName == null || fullName.isBlank()) {
                responseDTO.setMessage("Name is required");
                return responseDTO;
            }

            if (email == null || !email.matches("^[\\w.%+-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
                responseDTO.setMessage("Valid email is required");
                return responseDTO;
            }

            if (address == null || address.isBlank()) {
                responseDTO.setMessage("Address is required");
                return responseDTO;
            }

            if ((nic == null || nic.isBlank()) && (birthCertificateNo == null || birthCertificateNo.isBlank())) {
                responseDTO.setMessage("Either NIC or Birth Certificate No is required");
                return responseDTO;
            }

            if (contactNo == null || !contactNo.matches("^\\d{10}$")) {
                responseDTO.setMessage("Valid 10-digit contact number is required");
                return responseDTO;
            }

            if (dateOfBirth == null || dateOfBirth.isBlank()) {
                responseDTO.setMessage("Date of birth is required");
                return responseDTO;
            }

            if (genderStr == null || (!genderStr.equals("MALE") && !genderStr.equals("FEMALE"))) {
                responseDTO.setMessage("Gender must be either MALE or FEMALE");
                return responseDTO;
            }

            String fileName = fileDetails.getFileName().toLowerCase();
            if (!(fileName.endsWith(".jpg") || fileName.endsWith(".jpeg") || fileName.endsWith(".png"))) {
                responseDTO.setMessage("Only JPG, JPEG, or PNG images are allowed");
                return responseDTO;
            }

            final int MAX_SIZE = 5 * 1024 * 1024; // 5MB
            if (fileDetails.getSize() > MAX_SIZE) {
                responseDTO.setMessage("Image size should be less than 5MB");
                return responseDTO;
            }


            CustomerSaveRequestDTO dto = buildCustomerDto(uploadedInputStream, fullName, email, address, nic,
                    birthCertificateNo, contactNo, dateOfBirth, genderStr);

            return userService.createCustomer(dto);
        } catch (Exception e) {
            ResponseDTO<String> responseDTO = new ResponseDTO<>();
            responseDTO.setMessage(e.getMessage());

            return responseDTO;
        }
    }

    @RolesAllowed({"ADMIN","TELLER","MANAGER"})
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseDTO<Customer> getCustomer(@PathParam("id") String id){
        try {
            Long customerId = Long.parseLong(id);
            return userService.getCustomerById(customerId);
        } catch (NumberFormatException e) {
            return new ResponseDTO<>(null, false, "Invalid Customer ID");
        }
    }

    @RolesAllowed({"ADMIN","TELLER","MANAGER","AUDITOR"})
    @GET
    @Path("/all")
    public ResponseDTO<List<Customer>> getAllCustomers() {
        return userService.getAllCustomers();
    }

    @RolesAllowed({"ADMIN","TELLER","MANAGER"})
    @PUT
    @Path("/{id}")
    public ResponseDTO<String> updateCustomer(
            @PathParam("id") String id,
            @FormDataParam("photo") InputStream uploadedInputStream,
            @FormDataParam("photo") FormDataContentDisposition fileDetails,
            @FormDataParam("fullName") String fullName,
            @FormDataParam("email") String email,
            @FormDataParam("address") String address,
            @FormDataParam("nic") String nic,
            @FormDataParam("birthCertificateNo") String birthCertificateNo,
            @FormDataParam("contactNo") String contactNo,
            @FormDataParam("dateOfBirth") String dateOfBirth,
            @FormDataParam("gender") String genderStr) {

        try {
            CustomerSaveRequestDTO dto = buildCustomerDto(uploadedInputStream, fullName, email, address, nic,
                    birthCertificateNo, contactNo, dateOfBirth, genderStr);

            return userService.updateCustomer(Long.parseLong(id), dto);
        } catch (Exception e) {
            return new ResponseDTO<>(null, false, "Error: " + e.getMessage());
        }
    }

    @RolesAllowed({"ADMIN"})
    @DELETE
    @Path("/{id}")
    public ResponseDTO<String> deleteCustomer(@PathParam("id") String id) {
        try {
            Long customerId = Long.parseLong(id);
            return userService.deleteCustomer(customerId);
        } catch (NumberFormatException e) {
            return new ResponseDTO<>(null, false, "Invalid customer ID format");
        }
    }

    @GET
    @Path("/get-by-nic")
    @RolesAllowed({"ADMIN","TELLER","MANAGER"})
    public ResponseDTO<Customer> searchCustomerByNic(@QueryParam("nic") String nic) {
        return userService.searchCustomerByNic(nic);
    }

    @GET
    @Path("/get-by-name")
    @RolesAllowed({"ADMIN","TELLER","MANAGER"})
    public ResponseDTO<List<Customer>> searchCustomerByName(@QueryParam("name") String name) {
        return userService.searchCustomerByName(name);
    }

    @GET
    @Path("/exists")
    @RolesAllowed({"ADMIN","TELLER","MANAGER"})
    public boolean customerExistsByEmailOrNic(@QueryParam("email") String email,
                                              @QueryParam("nic") String nic) {
        return userService.customerExistsByEmailOrNic(email, nic);
    }
}
