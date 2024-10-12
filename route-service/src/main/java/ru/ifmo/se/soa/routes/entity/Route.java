package ru.ifmo.se.soa.routes.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.util.Date;
import java.util.Objects;

@Entity
@NamedEntityGraph(
        name = "route-fetch-join",
        attributeNodes = {@NamedAttributeNode("from"), @NamedAttributeNode("to")}
)
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Route {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "varchar(255) check (trim(name) <> '')", nullable = false)
    private String name;

    @Column(columnDefinition = "date", nullable = false, updatable = false)
    private Date creationDate = new Date();

    @ManyToOne
    @JoinColumn(nullable = false)
    private Location from;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Location to;

    @Column(columnDefinition = "integer check (distance > 1)", nullable = false)
    private Integer distance;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o)
                .getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this)
                .getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Route route = (Route) o;
        return getId() != null && Objects.equals(getId(), route.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this)
                .getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
