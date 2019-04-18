package entities;

import enums.Gender;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.List;
import java.util.Set;

@ToString
@Entity(name = "developer")
@Table (name = "developers")
@Getter
@Setter
public class Developer extends Human {
    @Column(name = "age")
    private Integer age;
    @Column(name = "is_man", length = 6)
    @Enumerated (EnumType.STRING)
    private Gender isMale;
    @Column(name = "salary")
    private Integer salary;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "develop_proj",
            joinColumns = @JoinColumn(name = "id_developers"),
            inverseJoinColumns = @JoinColumn(name = "id_project"))
    private List<Project> projectsSet;
}
