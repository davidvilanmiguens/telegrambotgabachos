package dispatcher



import com.github.kotlintelegrambot.bot
import com.github.kotlintelegrambot.dispatch
import com.github.kotlintelegrambot.dispatcher.*
import com.github.kotlintelegrambot.dispatcher.message
import com.github.kotlintelegrambot.entities.ChatId
import com.github.kotlintelegrambot.entities.InlineKeyboardMarkup
import com.github.kotlintelegrambot.entities.KeyboardReplyMarkup
import com.github.kotlintelegrambot.entities.ParseMode.MARKDOWN
import com.github.kotlintelegrambot.entities.ParseMode.MARKDOWN_V2
import com.github.kotlintelegrambot.entities.ReplyKeyboardRemove
import com.github.kotlintelegrambot.entities.TelegramFile.ByUrl
import com.github.kotlintelegrambot.entities.dice.DiceEmoji
import com.github.kotlintelegrambot.entities.inlinequeryresults.InlineQueryResult
import com.github.kotlintelegrambot.entities.inlinequeryresults.InputMessageContent
import com.github.kotlintelegrambot.entities.inputmedia.InputMediaPhoto
import com.github.kotlintelegrambot.entities.inputmedia.MediaGroup
import com.github.kotlintelegrambot.entities.keyboard.InlineKeyboardButton
import com.github.kotlintelegrambot.entities.keyboard.KeyboardButton
import com.github.kotlintelegrambot.extensions.filters.Filter
import com.github.kotlintelegrambot.logging.LogLevel
import com.github.kotlintelegrambot.network.fold
/**
 * author: David Vilan Miguens
 *
 */

fun main() {

    val bot = bot {

        token = Apikey.miToken //el token relaciona el programa con el bot
        timeout = 30
        logLevel = LogLevel.Network.Body

        dispatch {

            //aparece el mensaje al enviar un sticker
            message(Filter.Sticker) {
                bot.sendMessage(ChatId.fromId(message.chat.id), text = "You have received an awesome sticker \\o/")
            }
            //aparece el mensaje cuando reenvias el mensaje del bot
            message(Filter.Reply or Filter.Forward){

                bot.sendMessage(ChatId.fromId(message.chat.id), text = "someone is replying or forwarding messages ...")
            }
            /**
             * @param démarrer Nombre del comando del bot
             * Comando que devuelve un mensaje cuando se inicai el bot
             */

            command("démarrer") {

                val result = bot.sendMessage(chatId = ChatId.fromId(update.message!!.chat.id), text = "Gabacho asqueroso, iniciado")

                result.fold(
                    {
                        // do something here with the response
                    },
                    {
                        // do something with the error
                    }
                )
            }
            /**
             * @param bonjour Nombre del comando del bot
             * Comando devuelve texto
             */

            command("bonjour") {

                val result = bot.sendMessage(chatId = ChatId.fromId(update.message!!.chat.id), text = "No me dirijas la palabra, ser inferior")

                result.fold(
                    {
                        // do something here with the response
                    },
                    {
                        // do something with the error
                    }
                )
            }
            /**
             * @param adios Nombre del comando del bot
             * Este comando te devuelve un texto
             */
            command("adieu") {

                val result = bot.sendMessage(chatId = ChatId.fromId(message.chat.id), text = "Por fin, un francés menos")

                result.fold({

                }, {
                    // do something with the error
                })
            }
            /**
             * @param comandoconargs Nombre comando del bot
             * Comando devuelve texto
             */
            command("commandWithArgs") {
                val joinedArgs = args.joinToString()/*Crea una cadena de todos los elementos separados usando un
                separador y usando el prefijo y sufijo dados si se suministran. Si la colección puede ser enorme, puede
                especificar un valor de límite no negativo, en cuyo caso solo se agregarán los primeros elementos de
                límite, seguidos de la cadena truncada (que por defecto es "...").*/

                val response = if (joinedArgs.isNotBlank()) joinedArgs else "Pas de texte"
                bot.sendMessage(chatId = ChatId.fromId(message.chat.id), text = response)
            }
            /**
             * @param markdown Nombre del comando del bot
             * Este comando te devuelve un texto
             */
            //Cambia el texto sin editor

            command("markdown") {
                val markdownText = "Fekts: *Markdown* Francia arderá"
                bot.sendMessage(
                    chatId = ChatId.fromId(message.chat.id),
                    text = markdownText,
                    parseMode = MARKDOWN
                )
            }
            /**
             * @param markdown2 Nombre del comando del bot
             * Este comando te devuelve un texto
             */
            //Version mejorada del marckdown.


            command("markdownV2") {
                val markdownV2Text = """
                    *bold \*text*
                    _italic \*text_
                    __underline__
                    ~strikethrough~
                    *bold _italic bold ~italic bold strikethrough~ __underline italic bold___ bold*
                    [inline URL](http://www.example.com/)
                    [inline mention of a user](tg://user?id=123456789)
                    `inline fixed-width code`
                    ```kotlin
                    fun main() {
                        println("Hello Kotlin!")
                    }
                    ```
                """.trimIndent()


                bot.sendMessage(
                    chatId = ChatId.fromId(message.chat.id),
                    text = markdownV2Text,
                    parseMode = MARKDOWN_V2
                )
            }
            /**
             * @param inlinebuttons Nombre del comando del bot
             * Retorna botones que se asocia texto o una ventana emerjente con texto
             */
            //sirve para crear dos botones

            command("inlineButtons") {
                val inlineKeyboardMarkup = InlineKeyboardMarkup.create(
                    //text texto del boton
                    listOf(InlineKeyboardButton.CallbackData(text = "Test Inline Button", callbackData = "testButton")),
                    listOf(InlineKeyboardButton.CallbackData(text = "Show alert", callbackData = "showAlert"))
                )
                bot.sendMessage(
                    chatId = ChatId.fromId(message.chat.id),
                    text = "Hello, inline buttons!",
                    replyMarkup = inlineKeyboardMarkup
                )
            }
            /**
             * @param f Nombre del comando del bot
             * Este comando retorna un  texto y dos botones situados en donde el usuario puede escribir
             */
            /*
            Envia tu direccion y número de teléfono, lamentablemente no lo hace n grupos, lo cual sería util para identificar y encontrar a los franceses que se escapan o se ocultan
            */

            command("userButtons") {
                val keyboardMarkup = KeyboardReplyMarkup(keyboard = generateUsersButton(), resizeKeyboard = true)
                bot.sendMessage(
                    chatId = ChatId.fromId(message.chat.id),
                    text = "Hello, users buttons!",
                    replyMarkup = keyboardMarkup
                )
            }
            /**
             * @param mediagroup Nombre del comando del bot
             * Este comando retorna un texto y fotos
             */
            //Poder mandar fotos con el bot

            command("mediaGroup") {
                bot.sendMediaGroup(
                    chatId = ChatId.fromId(message.chat.id),
                    mediaGroup = MediaGroup.from(
                        InputMediaPhoto(
                            media = ByUrl("https://www.sngular.com/wp-content/uploads/2019/11/Kotlin-Blog-1400x411.png"),
                            caption = "I come from an url :P"
                        ),
                        InputMediaPhoto(
                            media = ByUrl("https://www.sngular.com/wp-content/uploads/2019/11/Kotlin-Blog-1400x411.png"),
                            caption = "Me too!"
                        )
                    ),
                    replyToMessageId = message.messageId
                )
            }
            /**
             * @param quesepassetil Es el texto que aparece en el collbackData del boton asociado
             * Retorna el callbackQuery
             */
            //debe coincidir con el texto que se le ponga al boton EN callbackData

            callbackQuery("quesepassetil") {
                val chatId = callbackQuery.message?.chat?.id ?: return@callbackQuery
                bot.sendMessage(ChatId.fromId(chatId), callbackQuery.data)
            }
            /**
             * @param alerta Es el texto que aparece en el collbackData del boton asociado
             * Retorna el callbackQuery
             */

            callbackQuery(
                callbackData = "alerta",
                callbackAnswerText = "Gabacho detectado, repito, gabacho detectado, código azul-blanco-rojo ",
                callbackAnswerShowAlert = true
            ) {
                val chatId = callbackQuery.message?.chat?.id ?: return@callbackQuery
                bot.sendMessage(ChatId.fromId(chatId), callbackQuery.data)
            }

            text("Sin piernas, ni brazos") {
                bot.sendMessage(chatId = ChatId.fromId(message.chat.id), text = "Gabachos a pedazos!!!")
            }

            //te devuelve la latituid y longitud de te localizacion
            location {
                bot.sendMessage(
                    chatId = ChatId.fromId(message.chat.id),
                    text = "Your location is (${location.latitude}, ${location.longitude})",
                    replyMarkup = ReplyKeyboardRemove()
                )
            }

            //devuelve un mensaje de texto con tu nombre
            contact {
                bot.sendMessage(
                    chatId = ChatId.fromId(message.chat.id),
                    text = "Hello, ${contact.firstName} ${contact.lastName}",
                    replyMarkup = ReplyKeyboardRemove()
                )
            }

            channel {
                // Handle channel update
            }

            inlineQuery {
                val queryText = inlineQuery.query

                if (queryText.isBlank() or queryText.isEmpty()) return@inlineQuery

                val inlineResults = (0 until 5).map {
                    InlineQueryResult.Article(
                        id = it.toString(),
                        title = "$it. $queryText",
                        inputMessageContent = InputMessageContent.Text("$it. $queryText"),
                        description = "Add $it. before you word"
                    )
                }
                bot.answerInlineQuery(inlineQuery.id, inlineResults)
            }

            photos {
                bot.sendMessage(
                    chatId = ChatId.fromId(message.chat.id),
                    text = "No será ese de ahí francés no?"
                )
            }

            /**
             * @param dado nombre del comando
             * Hace un dado o una diana
             */
            command("diceAsDartboard") {
                bot.sendDice(ChatId.fromId(message.chat.id), DiceEmoji.Dartboard)
            }
            //cuando reenvias la diana a el bot por privado te devuelve la puntiacion

            dice {
                bot.sendMessage(ChatId.fromId(message.chat.id), "A dice ${dice.emoji.emojiValue} with value ${dice.value} has been received!")
            }

            //Cuando salga un error saltará un mensaje
            telegramError {
                println(error.getErrorMessage())
            }
        }
    }

    bot.startPolling()
}

fun generateUsersButton(): List<List<KeyboardButton>> {
    return listOf(
        listOf(KeyboardButton("Request location (not supported on desktop)", requestLocation = true)),
        listOf(KeyboardButton("Request contact", requestContact = true))
    )
}