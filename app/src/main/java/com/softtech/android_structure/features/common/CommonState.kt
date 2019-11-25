package com.softtech.android_structure.features.common

import com.softtech.android_structure.domain.entities.ChatMessage
import com.softtech.android_structure.domain.entities.Room
import com.softtech.android_structure.domain.entities.User


/**
 * Created by mohamed abd elnaby on 16/April/2019
 */

sealed class CommonState<out T> {
    object LoadingShow : CommonState<Nothing>()
    object LoadingFinished : CommonState<Nothing>()
    object NoInternet : CommonState<Nothing>()
    data class Success<out R>(val data: R) : CommonState<R>()
    data class Error(val exception: Throwable) : CommonState<Nothing>()
}
sealed class LoginStates {
    data class SaveLoginData(val data: String) : LoginStates()
    object LoginSuccess : LoginStates()

}
sealed class ChatRoomStates{
    data class AddNewRoom(val room: Room) : ChatRoomStates()
    data class UpdateRoom(val room: Room) : ChatRoomStates()
    data class UpdateLastMessage(val lastMessage: ChatMessage) : ChatRoomStates()
    data class UpdateFrindInfo(val friend: User) : ChatRoomStates()

}
