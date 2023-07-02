package ru.netology

import java.lang.RuntimeException

fun main() {
    val post = Post(
        authorId = 1,
        authorName = "Alex",
        text = "dsgssdgedgbdbed",
        original = null,
        comments = null,
        reposts = null,
        copyHistory = null
    )
    //WallService.add(post)
    val post1 = Post(
        authorId = 35,
        authorName = "Petrovych",
        text = "sldg elrbjl trjbn khtkj gskul",
        original = null,
        comments = null,
        reposts = null,
        copyHistory = null
    )
    var post2 = Post(
        authorId = 12,
        authorName = "Kolya Usypov",
        text = "Привет",
        comments = null,
        original = post1,
        reposts = null,
        copyHistory = null
    )
    WallService.add(post1)
    WallService.add(post2)

    var attach: Array<Attachments> = emptyArray()
    attach += Audio(1, 1, "Pevec", "title", 305, "url", 5, 2, 3, 0, false, true)
    attach += Photo(3, 5, "130", "604")


    val comment1: Comment = WallService.createComment(post1.id, Comment(1, 35, 0, "comment 1"))
    println(comment1)
    val comment: Comment = WallService.createComment(post.id, Comment(2, 1, 0, "comment", attachments = attach))
    println(comment)

}

object CorrectId {
    private var lastId = 0
    //смотрим в то место где у нас хранятся id постов и выдаём свободный

    fun getNewId(id: Int): Int {
        if (id == 0) {
            lastId += 1
            return lastId
        }

        return 0
    }

    fun clearId() {
        lastId = 0
    }
}

data class Post(
    val id: Int = CorrectId.getNewId(0),
    val original: Post?,
    val ownerId: Int = 0,       //Идентификатор владельца стены, на которой размещена запись.
    val fromId: Int = 0,        //Идентификатор автора записи (от чьего имени опубликована запись).
    val date: Int = 0,   //Время публикации записи в формате unixtime.
    val replyOwnerId: Int = 0, //Идентификатор владельца записи, в ответ на которую была оставлена текущая.
    val replyPostId: Int = 0,  //Идентификатор записи, в ответ на которую была оставлена текущая.
    val canDelete: Boolean = false, // Информация о том, может ли текущий пользователь удалить запись (1 — может, 0 — не может).
    val isPinned: Boolean = false, // Информация о том, что запись закреплена.
    val markedAsAds: Boolean = false, // Информация о том, содержит ли запись отметку "реклама" (1 — да, 0 — нет).
    val isFavorite: Boolean = false, // true, если объект добавлен в закладки у текущего пользователя.
    val postponedId: Int = 0, // Идентификатор отложенной записи. Это поле возвращается тогда, когда запись стояла на таймере.
    val postType: String = "post", //String  Тип записи, может принимать следующие значения: post, copy, reply, postpone, suggest.
    val signerId: Int = 0, //Идентификатор автора, если запись была опубликована от имени сообщества и подписана пользователем;
    val copyHistory: CopyHistory?,
    val views: Views = Views(),
    val comments: Comments?,
    val copyright: Copyright = Copyright(),
    val reposts: Reposts?,
    val geo: Geo = Geo(),
    val donut: Donut = Donut(),
    val authorId: Int,
    val authorName: String,
    val text: String,
    val likes: Likes = Likes(),
    val friendsOnly: Boolean = false,
    val canPin: Boolean = false,
    val canEdit: Boolean = true,
    val attachments: Array<Attachments> = emptyArray()
)

//interface Attachments {
abstract class Attachments

class AttachPhoto(
    val type: String = "photo"
)

class Photo(
    val id: Int, //Идентификатор фотографии.
    val ownerId: Int, //Идентификатор владельца фотографии.
    val photo130: String, //URL изображения для предпросмотра.
    val photo604: String, //URL полноразмерного изображения.
    val photo: AttachPhoto = AttachPhoto()
) : Attachments() {
    override fun toString(): String {
        return "type: ${photo.type}, id: $id, photo130: $photo130, photo604: $photo604"
    }
}

class AttachAudio(
    val type: String = "audio"
)

class Audio(
    val id: Int, //Идентификатор аудиозаписи.
    val ownerId: Int, //Идентификатор владельца аудиозаписи.
    val artist: String, //Исполнитель
    val title: String, //Название композиции.
    val duration: Int, //Длительность аудиозаписи в секундах.
    val url: String, //Ссылка на mp3.
    val lyricsId: Int, //Идентификатор текста аудиозаписи (если доступно).
    val albumId: Int, //Идентификатор альбома, в котором находится аудиозапись (если присвоен).
    val genreId: Int, //Идентификатор жанра из списка аудио жанров.
    val date: Int, //Дата добавления.
    val noSearch: Boolean, //если включена опция «Не выводить при поиске». Если опция отключена, поле не возвращается.
    val isHq: Boolean, // если аудио в высоком качестве.
    val audio: AttachAudio = AttachAudio()
) : Attachments() {
    override fun toString(): String {
        return "type: ${audio.type}, id: $id, artist: $artist, title: $title"
    }
}

class AttachVideo(
    val type: String = "video"
)

open class Video(
    val id: Int, //видеозаписи
    val ownerId: Int, //Идентификатор владельца видеозаписи.
    val title: String, //Название видеозаписи.
    val description: String, //Текст описания видеозаписи.
    val duration: String, //Длительность ролика  в секундах.
    val date: Int, //Дата создания видеозаписи в формате Unixtime.
    val comments: Int, //Количество комментариев к видеозаписи.
    val isPrivate: Boolean = true, //Поле возвращается, если видеозапись приватная (например, была загружена в личное сообщение), всегда содержит 1.
    val video: AttachVideo = AttachVideo()
) : Attachments() {
    override fun toString(): String {
        return "type: ${video.type}, id: $id, title: $title, description: $description"
    }
}

class AttachGraffiti(
    val type: String = "graffiti"
)

class Graffiti(
    val id: Int, //идентификатор граффити
    val ownerId: Int, //Идентификатор автора граффити.
    val photo130: String, //URL изображения для предпросмотра.
    val photo604: String, //URL полноразмерного изображения.
    val graffiti: AttachGraffiti = AttachGraffiti()
) : Attachments() {
    override fun toString(): String {
        return "type: ${graffiti.type}, id: $id, photo130: $photo130, photo604: $photo604"
    }
}

class AttachVikiPage(
    val type: String = "page"
)

open class VikiPage(
    val id: Int, //идентификатор вики-страницы.
    val vikiPage: AttachVikiPage = AttachVikiPage(),
    val groupId: Int, //Идентификатор группы, которой принадлежит вики-страница.
    val title: String //Название вики-страницы.
) : Attachments() {
    override fun toString(): String {
        return "type: ${vikiPage.type}, id: $id, title: $title"
    }
}

data class Donut(
    var isDonut: Boolean = false,
    var paidDuration: Int = 100,
    var canPublishFreeCopy: Boolean = true,
    val placeholder: Placeholder = Placeholder(),
    var editMode: String = "duration"
)

data class Placeholder(
    var name: String = "Смотрим на меня"
)

data class Reposts(
    var count: Int = 0,
    var userReposted: Boolean = false
)

data class Geo(
    val type: String = "", //Фонтан
    val coordinates: String = "", //"56.008772, 92.870401"
    val place: Places = Places()
)

data class Places(
    val name: String = ""//"Главный фонтан на Театральной площади г. Красноярска"
)

data class Comments(
    var count: Int = 0,
    var canPost: Boolean = true,
    var groupsCanPost: Boolean = true,
    var canClose: Boolean = false,
    var canOpen: Boolean = true
)

data class Copyright(
    val id: Int = 0, //тупо 0 потому что мы всего-лишьи имитируем ссылку извне
    val link: String = "",
    val name: String = "",
    val type: String = ""
)

data class Views(var count: Int = 0)

data class CopyHistory(
    var posts: Array<Post> = emptyArray()
)

data class Likes(
    var count: Int = 0,
    var userLikes: Boolean = false,
    var canLike: Boolean = true
)

data class Comment(
    val id: Int, //Идентификатор комментария.
    val fromId: Int, //Идентификатор автора комментария.
    val date: Int, //Дата создания комментария в формате Unixtime.
    val text: String,
    val parentsStack: Array<Int> = emptyArray(), //Массив идентификаторов родительских комментариев.
    val attachments: Array<Attachments> = emptyArray()
)

class PostNotFoundException(message: String) : RuntimeException(message)

object WallService {
    private var posts = emptyArray<Post>()
    private var comments = emptyArray<Comment>()

    fun createComment(postId: Int, comment: Comment): Comment {
        for (i in posts) {
            if (i.id == postId) {
                comments += comment
                return comments.last()
            }
        }
        throw PostNotFoundException("no post with id: $postId")

    }

    fun getPostString(postId: Int = 0) {
        when (postId) {
            0 -> for (i in posts) println(i.toString())
            else -> for (i in posts) if (i.id == postId) println(i.toString())
        }

    }

    fun clear() {
        CorrectId.clearId()
        posts = emptyArray()
    }

    fun add(post: Post): Post {
        posts += post
        return posts.last()
    }

    fun getPostAttach(postId: Int) {
        when (postId) {
            0 -> {
                println("У вас отсутствует корректный id поста.")
            }

            else -> {
                for (i in posts) {
                    if (i.id == postId) {
                        for (attach in i.attachments)
                            when (attach) {
                                is Audio -> println("audio:  $attach")
                                is Photo -> println("photo:  $attach")
                                is VikiPage -> println("viki page:  $attach")
                                is Video -> println("video:  $attach")
                                is Graffiti -> println("graffiti:  $attach")
                            }
                    }
                }
            }
        }

    }

    fun update(post: Post): Boolean {
        for ((index, i) in posts.withIndex()) {
            if (i.id == post.id) {
                posts[index] = post.copy(
                    authorName = post.authorName, text = post.text, likes = i.likes, friendsOnly = post.friendsOnly,
                    canPin = post.canPin, canEdit = post.canEdit
                )
                return true
            }
        }
        return false
    }

    fun likeById(postId: Int) {
        for ((index, post) in posts.withIndex()) {
            if (post.id == postId) {
                posts[index] = post.copy(likes = Likes(post.likes.count + 1, post.likes.userLikes, post.likes.canLike))
            }
        }
    }

}