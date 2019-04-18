package entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.PostLoad;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;
import java.util.Set;

@Log4j
@Entity(name = "project")
@Table(name = "projects")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Project extends Task {
    @Column(name = "date_of_start")
    @Basic
    @Temporal(TemporalType.DATE)
    private Date date;
    @Column(name = "cost")
    private Integer cost;
    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "projectsSet")
    private Set<Developer> developers;

    @Override
    public String toString() {
        return "Project{" +
                "id=" + getId() +
                ", name='" + getName() +
                ", date='" + date +
                ", cost=" + cost +
                '}';
    }

    @PostLoad
    private void writeLog() {
        log.info("Loading is finished");
    }
}
