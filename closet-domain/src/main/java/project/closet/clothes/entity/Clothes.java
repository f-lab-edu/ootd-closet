package project.closet.clothes.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;
import project.closet.base.BaseUpdatableEntity;
import project.closet.user.entity.User;

@Getter
@Entity
@Table(name = "clothes")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Clothes extends BaseUpdatableEntity {

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, optional = false)
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false, length = 50)
    private ClothesType type;

    @Column(name = "image_key")
    private String imageKey;

    @BatchSize(size = 100)
    @OneToMany(
        mappedBy = "clothes",
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    private List<ClothesAttribute> attributes = new ArrayList<>();

    public Clothes(User owner, String name, String imageKey, ClothesType type) {
        this.owner = owner;
        this.name = name;
        this.type = type;
        this.imageKey = imageKey;
    }

    public void updateDetails(String name, ClothesType type) {
        if (name != null && !name.isBlank()) {
            this.name = name;
        }
        if (type != null) {
            this.type = type;
        }
    }

    public void updateImageKey(String imageKey) {
        this.imageKey = imageKey;
    }

    // 연관관계 메소드일 것으로 예상함
    public void addAttribute(ClothesAttribute attribute) {
        attributes.add(attribute);
        attribute.setClothes(this);
    }

    public void removeAttribute(ClothesAttribute attribute) {
        attributes.remove(attribute);
        attribute.setClothes(null);
    }
}
