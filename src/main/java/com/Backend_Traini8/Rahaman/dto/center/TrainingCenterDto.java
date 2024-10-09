package com.Backend_Traini8.Rahaman.dto.center;

import com.Backend_Traini8.Rahaman.config.Constants;
import com.Backend_Traini8.Rahaman.dto.BaseEntityDto;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TrainingCenterDto extends BaseEntityDto {
    @NotBlank(message="ERR_ADMIN_0001")
    @Pattern(regexp = Constants.REGEX_CENTER_NAME, message = "ERR_ADMIN_0002")
    private String centerName;
    @Pattern(regexp = Constants.REGEX_CENTER_CODE, message = "ERR_ADMIN_0003")
    private String centerCode;
    private Address address;
    private Integer studentCapacity;
    private List<String> courseOffered;
    @Email
    private String contactEmail;
    private String contactPhone;
}
