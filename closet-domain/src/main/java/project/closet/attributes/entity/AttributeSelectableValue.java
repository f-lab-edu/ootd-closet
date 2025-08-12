package project.closet.attributes.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.closet.base.BaseUpdatableEntity;

@Entity
@Table(name = "attribute_selectable_value")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AttributeSelectableValue extends BaseUpdatableEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "attribute_id", nullable = false)
    private Attribute attribute;

    @Column(name = "value", nullable = false, length = 100)
    private String value;

    public AttributeSelectableValue(Attribute attribute, String value) {
        this.attribute = attribute;
        this.value = value;
    }
}
