package com.zhixin.zhfz.bacs.services.door;

import com.zhixin.zhfz.common.entity.MessageEntity;

public interface IZKDoorService {
    MessageEntity assignCard(String url, String pin, String cardNo);
}