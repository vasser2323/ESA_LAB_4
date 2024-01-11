---------------------
Была добавлена таблица Logging - 
каждая запись описывает изменения в сущностях.

Структура таблицы Logging: 
    
| Event | Entity | Entity_id |

    Столбец EVENT содержит:
        CREATE_CUSTOMER,
        DELETE_CUSTOMER,
        CREATE_CART,
        DELETE_CART

Cтолбец Entity - содержит измененные объекты.

В качестве брокера сообщений использовался ActiveMQ.

MDB принимает сообщения, проверяет их структуру и записывает в бд.

Была добавлена таблица Mail - которая содержит
почтовый адрес для отправик оповещения и само условие для оповещения.

Структура: | mail | condition |

---------------------