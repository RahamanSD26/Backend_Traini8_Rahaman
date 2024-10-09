package com.Backend_Traini8.Rahaman.dto.center;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    @NotBlank(message = "ERR_ADMIN_0021")
    private String address;
    @NotBlank(message = "ERR_ADMIN_0023")
    private String city;
    @NotBlank(message = "ERR_ADMIN_0024")
    private String state;
    @NotBlank(message = "ERR_ADMIN_0025")
    private String pinCode;
}
