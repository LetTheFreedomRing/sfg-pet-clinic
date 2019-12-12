package guru.springframework.sfgpetclinic.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Table;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "specialities")
public class Speciality extends BaseEntity {

    String description;
}
