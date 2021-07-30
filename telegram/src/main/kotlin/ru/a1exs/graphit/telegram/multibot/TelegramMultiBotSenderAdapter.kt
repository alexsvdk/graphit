package ru.a1exs.graphit.telegram.multibot

import org.telegram.telegrambots.meta.api.methods.polls.SendPoll
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto
import org.telegram.telegrambots.meta.bots.AbsSender
import ru.a1exs.graphit.multibot.MultiBotSender
import ru.a1exs.graphit.multibot.imageComponent
import ru.a1exs.graphit.multibot.message.MessageComponent
import ru.a1exs.graphit.multibot.pollComponent
import ru.a1exs.graphit.multibot.textComponent
import ru.a1exs.graphit.telegram.inputFile

class TelegramMultiBotSenderAdapter(
    val sender: AbsSender,
) : MultiBotSender {

    override fun sendMessage(chatId: String, data: List<MessageComponent>) {
        var textSent = false

        data.imageComponent?.let {
            val sendPhoto = SendPhoto.builder()
                .chatId(chatId)
                .photo(it.inputFile)
                .caption(data.textComponent?.text)
                .build()
            sender.execute(sendPhoto)
            textSent = true
        }

        data.pollComponent?.let {
            val sendPoll = SendPoll.builder()
                .chatId(chatId)
                .question(it.question ?: "")
                .isClosed(it.isClosed)
                .isAnonymous(it.isAnonymous)
                .options(it.voteData.map { it.optionName })
                .build()
            sender.execute(sendPoll)
        }

        if (!textSent) data.textComponent?.let {
            val sendMessage = SendMessage.builder()
                .chatId(chatId)
                .text(it.text)
                .build()
            sender.execute(sendMessage)
            textSent = true
        }

    }

    override fun updateMessage(messageId: String, data: List<MessageComponent>) {
        TODO("Not yet implemented")
    }

    override fun removeMessage(messageId: String) {
        TODO("Not yet implemented")
    }

}