package project.closet.service.exception.clothes.attribute;

import project.closet.service.exception.ErrorCode;

public class AttributeNotFoundException extends AttributeException {

    public AttributeNotFoundException(String id) {
        super(ErrorCode.ATTRIBUTE_DEFINITION_NOT_FOUND);
        addDetail("definitionId", id);
    }
}
