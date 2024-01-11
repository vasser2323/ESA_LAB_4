JMS — это стандартный Java API для отправки и получения сообщений между клиентами с использованием асинхронного шаблона сообщений. 
JMS поддерживает две модели взаимодействия:

 - Point-to-Point (P2P) — сообщения отправляются от одного отправителя к одному получателю через очередь.
 - Publish-Subscribe (Pub/Sub) — сообщения отправляются от одного отправителя ко многим подписчикам через тему.


В данной работе:
---------------------
Была добавлена таблица Logging - 
каждая запись описывает изменения в сущностях.

Структура таблицы Logging: 
    
| Event | Entity | Entity_id |

    Столбец EVENT содержит:
        CREATE_CUSTOMER,
        DELETE_CUSTOMER,
        CREATE_CARD,
        DELETE_CARD

Cтолбец Entity - содержит измененные объекты.

В качестве брокера сообщений использовался ActiveMQ.

MDB принимает сообщения, проверяет их структуру и записывает в бд.

Была добавлена таблица Mail - которая содержит
почтовый адрес для отправик оповещения и само условие для оповещения.

Структура: | mail | condition |

---------------------

Полученные результаты в данной лабораторной работе:

Использовалась программа Postman - сервис для создания, тестирования, документирования, публикации и обслуживания API.
С помощью него отправлялся POST запрос на почты добавленные в базу данных.
Для отправки уведомлений по почте использовалась библиотека gmail для отправки сообщений по протоколу SMTP.

POST - запрос: 

![image](https://github.com/vasser2323/ESA_LAB_4/assets/73202398/e6973379-d680-499f-8a8d-5286e59f3ec4)

Полученные уведомления:

CREATE_CARD:

![image](https://github.com/vasser2323/ESA_LAB_4/assets/73202398/3ca143ae-4142-45de-9738-08ea0cf4ebef)

DELETE_CARD:

![image](https://github.com/vasser2323/ESA_LAB_4/assets/73202398/4be03072-ecfa-4dab-b84e-c06842336f83)



