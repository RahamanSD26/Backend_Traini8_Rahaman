package com.Backend_Traini8.Rahaman.domain.center;

import com.Backend_Traini8.Rahaman.domain.BaseEntity;
import com.Backend_Traini8.Rahaman.dto.center.Address;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document
public class TrainingCenter extends BaseEntity {
    private String centerName;
    private String centerCode;
    private Address address;
    private Integer studentCapacity;
    private List<String> courseOffered;
    private String contactEmail;
    private String contactPhone;
}
