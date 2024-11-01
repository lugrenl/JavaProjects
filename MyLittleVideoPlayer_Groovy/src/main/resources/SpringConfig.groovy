import org.example.Computer
import org.example.MoviePlayer
import org.example.genres.Action
import org.example.genres.Comedy
import org.example.genres.Drama
import org.example.genres.Thriller

beans = {
    action(Action)
    comedy(Comedy)
    drama(Drama)
    thriller(Thriller)

    movieList(List) {
        // Создаем список непосредственно в конфигурации
        return [ref('action'), ref('comedy'), ref('drama'), ref('thriller')]
    }

    moviePlayer(MoviePlayer) { bean ->
        bean.scope = 'prototype'
        constructorArgs = [ref('movieList')]
    }

    computer(Computer) { bean ->
        constructorArgs = [ref('moviePlayer')]
    }
}

