package com.pyomin.cool.service;

import com.pyomin.cool.dto.user.PageViewLogDto;

public interface PageViewService {

    void logPageView(PageViewLogDto dto);

}
