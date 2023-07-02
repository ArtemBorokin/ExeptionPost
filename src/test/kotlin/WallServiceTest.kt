package ru.netology

import org.junit.Test

import org.junit.Assert.*
import org.junit.Before

class WallServiceTest {

    @Before
    fun clearBeforeTest() {
        WallService.clear()
    }

    @Test
    fun add() {
        val post = WallService.add(
            Post(
                authorId = 35,
                authorName = "Petrovych",
                text = "sldg elrbjl trjbn khtkj gskul",
                original = null,
                comments = null,
                reposts = null,
                copyHistory = null
            )
        )
        val post1 = WallService.add(
            Post(
                authorId = 1,
                authorName = "Alex",
                text = "dsgssdgedgbdbed",
                original = null,
                comments = null,
                reposts = null,
                copyHistory = null
            )
        )
        assertEquals(1, post.id)
        assertEquals(2, post1.id)
    }

    @Test
    fun update() {
        val post = WallService.add(
            Post(
                authorId = 35,
                authorName = "Petrovych",
                text = "sldg elrbjl trjbn khtkj gskul",
                original = null,
                comments = null,
                reposts = null,
                copyHistory = null
            )
        )
        //изменили ли мы уже существующую запись P.S. запись добавлена
        assertTrue(WallService.update(post))
        // Проверка на изменение несуществующей записи
        assertFalse(
            WallService.update(
                Post(
                    authorId = 12,
                    authorName = "Kolya Usypov",
                    text = "Привет",
                    original = null,
                    comments = null,
                    reposts = null,
                    copyHistory = null
                )
            )
        )
    }

    @Test(expected = PostNotFoundException::class)
    fun shouldThrow() {

        val post = Post(
            authorId = 35,
            authorName = "Petrovych",
            text = "sldg elrbjl trjbn khtkj gskul",
            original = null,
            comments = null,
            reposts = null,
            copyHistory = null
        )
        val post1 = Post(
            authorId = 35,
            authorName = "Petrovych",
            text = "sldg elrbjl trjbn khtkj gskul",
            original = null,
            comments = null,
            reposts = null,
            copyHistory = null
        )
        WallService.add(post1)
        val comment: Comment = WallService.createComment(post.id, Comment(2, 1, 0, "comment"))
    }

    @Test
    fun testCreateComment() {
        val post = Post(
            authorId = 35,
            authorName = "Petrovych",
            text = "sldg elrbjl trjbn khtkj gskul",
            original = null,
            comments = null,
            reposts = null,
            copyHistory = null
        )
        val post1 = Post(
            authorId = 35,
            authorName = "Petrovych",
            text = "sldg elrbjl trjbn khtkj gskul",
            original = null,
            comments = null,
            reposts = null,
            copyHistory = null
        )
        WallService.add(post1)
        val comment: Comment = WallService.createComment(post1.id, Comment(2, 1, 0, "comment"))
    }
}