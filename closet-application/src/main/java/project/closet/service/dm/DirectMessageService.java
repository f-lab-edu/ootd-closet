package project.closet.service.dm;

import java.time.Instant;
import java.util.UUID;
import project.closet.service.dto.request.DirectMessageCreateRequest;
import project.closet.service.dto.response.DirectMessageDto;
import project.closet.service.dto.response.DirectMessageDtoCursorResponse;

public interface DirectMessageService {

    DirectMessageDto sendMessage(DirectMessageCreateRequest directMessageCreateRequest);

    DirectMessageDtoCursorResponse getDirectMessages(UUID targetUserId, Instant cursor, UUID idAfter, int limit,
                                                     UUID loginUserId);
}
