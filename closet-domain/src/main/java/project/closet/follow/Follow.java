package project.closet.follow;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.closet.base.BaseEntity;
import project.closet.user.entity.User;

@Entity
@Table(name = "follows")
@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public class Follow extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "follower_id", nullable = false)
    private User follower;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "followee_id", nullable = false)
    private User followee;

    @Builder
    public Follow(User follower, User followee) {
        if (follower.getId().equals(followee.getId())) {
//            throw SelfFollowNotAllowedException.withUserId(follower.getId().toString());
            throw new IllegalArgumentException("Follower and Followee both have the same id");
        }
        this.follower = follower;
        this.followee = followee;
    }
}
