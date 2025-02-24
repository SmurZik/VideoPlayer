# Video Player App

## Описание
Приложение для получения видеороликов с возможностью просматривать их в полноэкранном режиме.

##  Функциональность  
-  Получение видеороликов с помощью PexelsAPI (работает только с VPN)
-  Отображение полученных видеороликов в списке с отображением миниатюры, названия и длительности ролика 
-  Экран воспроизведения с базовыми контролами (прогресс-бар, пауза, следующий/предыдущий ролик) и кнопкой для перехода в полноэкранный режим
-  Переход в полноэкранный режим с помощью кнопки или поворота устройства
-  Переключение между роликами из экрана воспроизведения
-  Кэширование списка роликов c помощью Room (если нет кэшированного списка, то сначала получаем из сети, затем кэшируем и отображаем)
-  Обновление списка свайпом вниз с использованием SwipeRefreshLayout
-  Обработка сетевых ошибок

##  Архитектура и технологии  
- **Архитектура:** MVVM + Clean Architecture  
- **Язык:** Kotlin  
- **Работа с сетью:** Retrofit  
- **Многопоточность:** Coroutines
- **Кэширование:** Room
- **Внедрение зависимостей:** Dagger Hilt
- **Тестирование:** Unit tests, Espresso
