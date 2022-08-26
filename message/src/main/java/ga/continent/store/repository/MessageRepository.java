package ga.continent.store.repository;

import ga.continent.store.entity.MessageEntity;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MessageRepository extends
        JpaRepository<MessageEntity, String>,
        RevisionRepository<MessageEntity, String, Long> {

    @Override
    @Query("select m from MessageEntity m " +
            "left join fetch m.comments c " +
            "where m.id = :message_id")
    Optional<MessageEntity> findById(@Param("message_id") String messageId);

    @EntityGraph(attributePaths = {"comments"})
    List<MessageEntity> findAllByTextContainingIgnoreCase(String prefixName);

    @EntityGraph(attributePaths = {"comments"})
    List<MessageEntity> findByAuthorIdIn(List<String> userIds, Sort sort);

}
