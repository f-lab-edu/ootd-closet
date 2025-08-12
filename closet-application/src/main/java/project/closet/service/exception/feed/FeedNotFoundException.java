package project.closet.service.exception.feed;

import java.util.UUID;
import project.closet.service.exception.ErrorCode;

public class FeedNotFoundException extends FeedException {

    public FeedNotFoundException() {
        super(ErrorCode.FEED_NOT_FOUND);
    }

    public static FeedNotFoundException withId(UUID feedId) {
        FeedNotFoundException exception = new FeedNotFoundException();
        exception.addDetail("feedId", feedId.toString());
        return exception;
    }
}
