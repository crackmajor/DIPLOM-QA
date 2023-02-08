[![Build status](https://ci.appveyor.com/api/projects/status/m8ii62ecqxrw0b91?svg=true)](https://ci.appveyor.com/project/crackmajor/diplom-qa)

[![Typing SVG](https://readme-typing-svg.herokuapp.com?font=Sigmar+One&duration=3000&pause=5000&color=1C9ACA&background=A79EA700&center=true&vCenter=true&width=500&lines=%F0%9F%9A%80%EF%B8%8FDiploma+project+under+development+%F0%9F%9A%80%EF%B8%8F)](https://git.io/typing-svg)

# Дипломный проект по профессии «Тестировщик»

Дипломный проект — автоматизация тестирования комплексного сервиса, взаимодействующего с СУБД и API Банка. Подробнее с заданием можно ознакомиться [тут](https://github.com/netology-code/qa-diploma).

[План автоматизации тестирования](Doc/Plan.md)

Перечень используемых инструментов:

    1. IntelliJ IDEA (Community Edition) - это IDE, интегрированная среда разработки.
    2. Amazon Corretto 11 - бесплатная многоплатформенная версия пакета средств разработки Open Java(OpenJDK), готовая к использованию в рабочей среде.
    3. JUnit - фреймворк для модульного тестирования программного обеспечения на языке Java.
    4. Lombok - библиотека для сокращения кода в классах и расширения функциональности языка Java.
    5. Selenide - инструмент для написания автоматических тестов.
    6. NodeJS - среда выполнения кода JavaScript
    7. Docker Desktop - автоматизация развертывания и управления приложениями в средах с поддержкой контейнеризации
    8. DBeaver - инструмент для работы с различными базами данных
    9. Allure - генератор отчётов тестирования
Процедура запуска автотестов:

    1. Клонирование репозитория
    2. Запуск контейнеров командой 'docker-compose up -d'
    3. Запуск aqa-shop.jar c MySQL командой в консоле 'java "-Dspring.datasource.url=jdbc:mysql://localhost:3306/app" -jar artifacts/aqa-shop.jar'
    4. Запуск aqa-shop.jar c PostgerSQL командой в консоле 'java "-Dspring.datasource.url=jdbc:postgresql://localhost:5432/app" -jar artifacts/aqa-shop.jar'
    5. Запустить автотесты командой в консоле './gradlew "-Ddb.url=jdbc:mysql://localhost:3306/app" clean test' для MySQL, либо командой в консоле './gradlew "-Ddb.url=jdbc:postgresql://localhost:5432/app" clean test' для PostgerSQL
    6. Для генерации отчета Allure запустить команду в консоле './gradlew allureServe'
[Отчётные документы по итогам тестирования](Doc/Report.md)

[Отчётные документы по итогам автоматизации](Doc/Summary.md)