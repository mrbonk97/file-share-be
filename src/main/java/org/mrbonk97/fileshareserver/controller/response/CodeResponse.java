package org.mrbonk97.fileshareserver.controller.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CodeResponse {
    private final String code;

    public static  CodeResponse of (String code) {
        return new CodeResponse(code);
    }
}
