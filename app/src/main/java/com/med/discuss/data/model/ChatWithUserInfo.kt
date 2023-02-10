package com.med.discuss.data.model

import com.med.discuss.data.db.entity.Chat
import com.med.discuss.data.db.entity.UserInfo

data class ChatWithUserInfo(
    var mChat: Chat,
    var mUserInfo: UserInfo
)
