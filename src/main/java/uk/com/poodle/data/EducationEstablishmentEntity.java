package uk.com.poodle.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import uk.com.poodle.domain.EducationEstablishmentType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.EnumType.STRING;

@Entity
@Getter
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "education_establishment")
public class EducationEstablishmentEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    @OneToOne(cascade = ALL)
    private AddressEntity address;

    @OneToOne(cascade = ALL, orphanRemoval = true)
    private ContactDetailsEntity contactDetails;

    @Column(name = "name")
    private String name;

    @Enumerated(STRING)
    @Column(name = "type")
    private EducationEstablishmentType type;
}
