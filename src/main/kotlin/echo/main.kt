package echo

import com.github.kotlintelegrambot.bot
import com.github.kotlintelegrambot.dispatch
import com.github.kotlintelegrambot.dispatcher.command
import com.github.kotlintelegrambot.dispatcher.text
import com.github.kotlintelegrambot.entities.ChatId
import com.github.kotlintelegrambot.entities.ParseMode
import com.github.kotlintelegrambot.network.fold

fun main() {
    val bot = bot {
        token = Apikey.miToken
        dispatch {
            text {
                print("Chat ID: ")
                println(message.chat.id)
                bot.sendMessage(chatId = ChatId.fromId(message.chat.id), text = text)
            }
            command("start") {

                val result = bot.sendMessage(chatId = ChatId.fromId(message.chat.id), text = "El bot se ha iniciado")

                result.fold({

                }, {
                    // do something with the error
                })
            }
        }
    }
    bot.startPolling()
}