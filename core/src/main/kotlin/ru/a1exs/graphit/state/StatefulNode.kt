package ru.a1exs.graphit.state

import ru.a1exs.graphit.core.ChatNode
import ru.a1exs.graphit.core.ChatUpdate

interface StatefulNode<U : ChatUpdate, Bot, State> : ChatNode<U, Bot>, StateMapper<U, State> {

    override fun receiveUpdate(chatUpdate: U, bot: Bot) = receiveUpdate(chatUpdate, bot, mapUpdateToState(chatUpdate))
    fun receiveUpdate(chatUpdate: U, bot: Bot, state: State)

}