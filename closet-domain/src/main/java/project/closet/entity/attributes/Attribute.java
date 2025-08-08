package project.closet.entity.attributes;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.closet.entity.base.BaseUpdatableEntity;

@Entity
@Table(name = "attributes")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Attribute extends BaseUpdatableEntity {

    @Column(name = "definition_name", nullable = false, unique = true, length = 50)
    private String definitionName;

    @OneToMany(mappedBy = "attribute", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AttributeSelectableValue> selectableValues = new ArrayList<>();

//    public Attribute(String definitionName, List<AttributeSelectableValue> selectableValues) {
//        this.definitionName = definitionName;
//        this.selectableValues = selectableValues;
//    }

    // TODO 리팩토링 대상, 문자열 -> 이넘 타입으로 받기
    public Attribute(String definitionName, List<String> values) {
        this.definitionName = definitionName;
        values.forEach(this::setSelectableValues);
    }

    public void updateDefinitionName(String newDefinitionName) {
        if (this.definitionName.equals(newDefinitionName)) {
            return;
        }
        this.definitionName = newDefinitionName;
    }

    public void addSelectableValues(List<String> values) {
        this.selectableValues.clear();
        values.forEach(this::setSelectableValues);
    }

    private void setSelectableValues(String value) {
        this.selectableValues.add(new AttributeSelectableValue(this, value));
    }
}
