package entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.Set;

@javax.persistence.Entity(name = "developers")
@Table
@Getter
@Setter
public class Developer extends Human {
    @Column(name = "age")
    private Integer age;
    @Column(name = "is_Man")
    private Boolean isMale;
    @Column(name = "salary")
    private Integer salary;
    @ManyToMany
    @JoinTable(name = "develop_proj",
            joinColumns = @JoinColumn(name = "id_developers"),
            inverseJoinColumns = @JoinColumn(name = "id_project"))
    private Set<Project> projectsSet;

    @Override
    public String toString() {
        return "Developer{" +
                " id=" + getId() +
                ", Surname=" + getSurName() +
                ", Name=" + getFirstName() +
                ", age=" + age +
                ", isMale=" + isMale +
                ", salary=" + salary +
                '}';
    }
}
