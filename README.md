
# **Название работы: "Простая аукционная система"**

## Описание проекта: 
Курсовая работа в рамках обучения по профессии Java-разработчик.

Эта работа для проверки знаний в области разработки веб-приложений и Spring Boot, знание паттернов DTO и REST API, 
а также умение работать со Swagger-документацией, Spring Data JPA и Liquibase.

## Выполнены следующие функциональные требования для проекта:

- Программа позволяет создавать лоты и делать на них ставки. Ставки делаются через отправку имени ставящего в POST-запросе.
- Аукцион работает так: Создание лота -> Перевод лота в состояние «Запущены торги» -> Прием ставок по лоту -> Перевод лота в состояние «Торги окончены».
- Текущая цена лота вычисляется на основе стартовой цены и количества ставок на данный лот. 
- В API представлены несколько методов для получения аналитической информации по лотам, а также по работе с лотами и ставками.
- В API предусмотрен экспорт информации по лотам в виде CSV-файла. 
- Миграциями базы данных управляет Liquibase. 
- Все основные действия (создание лота или новой ставки, например) логируются.
- Запросы в БД оптимальны и не вызывают проблему N+1.

  ## 
