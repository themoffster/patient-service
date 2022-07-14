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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.List;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.LAZY;

@With
@Entity
@Getter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Table(name = "patient")
public class PatientEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    @OneToOne(cascade = ALL)
    private AddressEntity address;

    @OneToMany(cascade = ALL, fetch = LAZY, mappedBy = "patientId", orphanRemoval = true)
    private List<AppointmentEntity> appointments;

    @Column(name = "dob")
    private LocalDate dob;

    @Column(name = "firstname")
    private String firstname;

    @OneToMany(cascade = ALL, fetch = LAZY, mappedBy = "patientId", orphanRemoval = true)
    private List<GuardianEntity> guardians;

    @Column(name = "lastname")
    private String lastname;

    @Column(name = "sex")
    private String sex;

}
