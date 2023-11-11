package MTCompany.utils;

import MTCompany.model.UserRequestModel;


public interface ValidationRequest {

    String validateRequest(String fieldToValidate, UserRequestModel userRequestModel);

    void cleanWithNoValidate(String fieldName, UserRequestModel userRequestModel);
}

