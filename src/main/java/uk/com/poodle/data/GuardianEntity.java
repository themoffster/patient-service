package uk.com.poodle.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.With;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.time.LocalDate;

import static javax.persistence.CascadeType.ALL;

@With
@Entity
@Getter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Table(name = "guardian")
public class GuardianEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    @OneToOne(cascade = ALL)
    private AddressEntity address;

    @OneToOne(cascade = ALL, orphanRemoval = true)
    private ContactDetailsEntity contactDetails;

    @Column(name = "dob")
    private LocalDate dob;

    @Column(name = "firstname")
    private String firstname;

    @Column(name = "lastname")
    private String lastname;

    @Column(name = "patient_id")
    private String patientId;

    @Column(name = "sex")
    private String sex;

    @OneToOne
    @JoinColumn(name = "relation_id")
    private RelationEntity relation;

}
