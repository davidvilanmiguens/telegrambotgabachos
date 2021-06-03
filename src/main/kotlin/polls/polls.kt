package polls



import Apikey.miToken
import com.github.kotlintelegrambot.bot
import com.github.kotlintelegrambot.dispatch
import com.github.kotlintelegrambot.dispatcher.command
import com.github.kotlintelegrambot.dispatcher.pollAnswer
import com.github.kotlintelegrambot.entities.ChatId
import com.github.kotlintelegrambot.entities.polls.PollType.QUIZ
import com.github.kotlintelegrambot.network.fold
fun main() {
    /**Instanciamos un objeto bot*/
    val bot = bot {

        /**Introducimos el token del bot*/
        /**Introducimos el token del bot*/
        token = Apikey.miToken
        dispatch {


            pollAnswer {
                println("${pollAnswer.user.username} has selected the option ${pollAnswer.optionIds.lastOrNull()} in the poll ${pollAnswer.pollId}")
            }

            command("start") {

                val result =
                    bot.sendMessage(chatId = ChatId.fromId(message.chat.id), text = "Gabacho asqueroso, iniciado")

                result.fold({

                }, {

                })
            }

            command("fekts") {
                bot.sendPoll(
                    chatId = ChatId.fromId(message.chat.id),
                    type = QUIZ,
                    question = "Que pasaría si ardiera Francia",
                    options = listOf(
                        "No diga eso, pobrecillos",
                        "El mundo sería un lugar mejor",
                        "Jaja funado ",
                        "Nos vemos en los juzgados"
                    ),
                    correctOptionId = 1,
                    isAnonymous = false,
                    openPeriod = 120,
                )
            }

            command("futuro") {
                bot.sendPoll(
                    chatId = ChatId.fromId(message.chat.id),
                    type = QUIZ,
                    question = "Deberían legalizar el asesinato",
                    options = listOf(
                        "No",
                        "Je me sens offensé",
                        "En caso de hacerlo hacia un frnacés, gratamente contesto que afirmo esa pregunta"
                    ),
                    correctOptionId = 2,
                    isAnonymous = false
                )
            }

            command("espagnol") {
                bot.sendPoll(
                    chatId = ChatId.fromId(message.chat.id),
                    type = QUIZ,
                    question = "Con que eres frances...",
                    options = listOf(
                        "Oui",
                        "Que dices puto colgao",
                        "Jamás traicionaria de esa manera a mi patria"
                    ),
                    correctOptionId = 0,
                    explanation = "FRANCES🤬🤢🚫"
                )
            }
        }
    }.startPolling()

}