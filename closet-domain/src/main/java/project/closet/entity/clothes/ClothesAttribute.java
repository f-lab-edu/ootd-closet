package project.closet.entity.clothes;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.closet.entity.attributes.Attribute;
import project.closet.entity.base.BaseEntity;

@Entity
@Table(name = "clothes_attributes")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ClothesAttribute extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "clothes_id", nullable = false)
    private Clothes clothes;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "attribute_id", nullable = false)
    private Attribute attribute;

    @Column(name = "value", nullable = false, length = 50)
    private String value;

    public ClothesAttribute(Clothes clothes, Attribute attribute, String value) {
        this.clothes = clothes;
        this.attribute = attribute;
        this.value = value;
    }

    public ClothesAttribute(Attribute attribute, String value) {
        this.attribute = attribute;
        this.value = value;
    }

    // TODO 리팩토링 대상 : 1:N 에서 저장하는 방식은?
    public void setClothes(Clothes clothes) {
        this.clothes = clothes;
    }

    // TODO 리팩토링 대상 : value는 조인 테이블에 있어서 이렇게 할 경우 조인 테이블 값은 변경 안됨
    public void updateValue(String newValue) {
        if (newValue != null && !newValue.isBlank() && !this.value.equals(newValue)) {
            this.value = newValue;
        }
    }
}
