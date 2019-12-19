package guru.springframework.sfgpetclinic.model;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
@MappedSuperclass
@EqualsAndHashCode(callSuper = false)
public class Person extends BaseEntity {

    @Column(name="first_name")
    @NotBlank
    private String firstName;
    @NotBlank
    @Column(name="last_name")
    private String lastName;
}
