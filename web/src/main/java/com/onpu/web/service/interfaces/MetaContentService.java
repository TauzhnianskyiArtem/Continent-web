package com.onpu.web.service.interfaces;

import com.onpu.web.api.dto.MessageDto;
import com.onpu.web.api.dto.MetaDto;

import java.io.IOException;

public interface MetaContentService {

    void fillMeta(MessageDto message);

    MetaDto getMeta(String url) throws IOException;

}
