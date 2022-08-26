package ga.continent.store.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.hibernate.envers.RelationTargetAuditMode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Data
@EqualsAndHashCode(of = {"id"})
@ToString(exclude = {"comments"})
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "message")
@Entity
@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
public class MessageEntity extends AuditingEntity {

    @Id
    String id;

    String text;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    UserEntity author;

    @NotAudited
    @OneToMany(mappedBy = "message", orphanRemoval = true)
    List<CommentEntity> comments = new ArrayList<>();

    String link;
    String linkTitle;
    String linkDescription;
    String linkCover;

    Long timestamp;
}
