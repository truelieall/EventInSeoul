package com.eventpage.common.apicall;

import com.fasterxml.jackson.databind.JsonNode;;

public interface RestHttpApi {

    JsonNode getEventResult();

    JsonNode getEventSubjcodeResult();

    JsonNode getResult(String ServiceName);

}
