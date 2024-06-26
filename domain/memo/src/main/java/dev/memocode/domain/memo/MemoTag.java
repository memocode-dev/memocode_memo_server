package dev.memocode.domain.memo;

import dev.memocode.domain.core.BaseEntity;
import dev.memocode.domain.tag.Tag;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import static jakarta.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

@Getter
@Entity
@SuperBuilder
@NoArgsConstructor(access = PROTECTED)
@Table(name = "memo_tag")
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class MemoTag extends BaseEntity {

    @ManyToOne(fetch = LAZY)
    @EqualsAndHashCode.Include
    @JoinColumn(name = "memo_id")
    private Memo memo;

    @ManyToOne(fetch = LAZY)
    @EqualsAndHashCode.Include
    @JoinColumn(name = "tag_id")
    private Tag tag;
}
