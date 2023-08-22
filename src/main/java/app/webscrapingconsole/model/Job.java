package app.webscrapingconsole.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.proxy.HibernateProxy;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "job")
public class Job {
    @Id
    @GeneratedValue(generator = "job_id_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "job_id_seq", sequenceName = "job_id_seq", allocationSize = 1)
    private Long id;
    private String title;
    @Column(columnDefinition = "TEXT")
    private String jobUrl;
    @Column(columnDefinition = "TEXT")
    private String jobApplicationUrl;
    private Long postedDate;
    @ManyToMany
    @JoinTable(name = "job_function",
               joinColumns = @JoinColumn(name = "job_id"),
               inverseJoinColumns = @JoinColumn(name = "function_id"))
    private Set<JobFunction> jobFunctions;
    @ManyToMany
    @JoinTable(name = "job_location",
               joinColumns = @JoinColumn(name = "job_id"),
               inverseJoinColumns = @JoinColumn(name = "location_id"))
    private List<Location> locations;
    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;
    @ManyToMany
    @JoinTable(name = "job_tag",
               joinColumns = @JoinColumn(name = "job_id"),
               inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private List<Tag> tags;
    @Column(columnDefinition = "TEXT")
    private String description;

    @Override
    public final boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null) {
            return false;
        }
        Class<?> oeffectiveclass = o instanceof HibernateProxy
                ? ((HibernateProxy) o).getHibernateLazyInitializer()
                .getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy
                ? ((HibernateProxy) this).getHibernateLazyInitializer()
                .getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oeffectiveclass) {
            return false;
        }
        Job job = (Job) o;
        return getId() != null && Objects.equals(getId(), job.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy
                ? ((HibernateProxy) this).getHibernateLazyInitializer()
                .getPersistentClass().hashCode() : getClass().hashCode();
    }

    @Override
    public String toString() {
        return "{\"id\":" + id + ","
                + "\"title\":\"" + title + "\","
                + "\"url\":\"" + jobUrl + "\","
                + "\"jobFunctions\":" + jobFunctions + ","
                + "\"locations\":" + locations + ","
                + "\"postedDate\":" + postedDate + ","
                + "\"tags\":" + tags + ","
                + "\"company\":" + company + ","
                + "\"jobApplicationUrl\":\"" + jobApplicationUrl + "\","
                + "\"description\":\"" + description + "\"}";
    }
}
