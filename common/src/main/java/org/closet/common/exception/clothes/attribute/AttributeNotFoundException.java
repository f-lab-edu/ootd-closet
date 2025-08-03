package org.closet.common.exception.clothes.attribute;


import org.closet.common.exception.ErrorCode;

public class AttributeNotFoundException extends AttributeException {

    public AttributeNotFoundException(String id) {
        super(ErrorCode.ATTRIBUTE_DEFINITION_NOT_FOUND);
        addDetail("definitionId", id);
    }
}
