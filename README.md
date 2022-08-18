# Sms77Notifier
The SMS77Notifier consumes new alerts from RabbitMQ Exchange and sends sms requests to 
the sms77.io api to send sms messages.
>**_NOTE:_** To use this service it's necessary to own a sms77.io account!

## Configruation
The entire configuration can be done via environment variables. The following settings can be used:

| **Environment Variable**           | **Default Value**             | **Explanation**                                                                           |
|------------------------------------|-------------------------------|-------------------------------------------------------------------------------------------|
| _SMS77NOTIFIER_HTTP_PORT_          | 8081                          | Quarkus HTTP Port                                                                         |
| _RABBITMQ_HOST_                    | localhost                     | RabbitMQ host                                                                             |
| _RABBITMQ_PORT_                    | 5672                          | Port of the RabbitMQ broker                                                               |
| _RABBITMQ_USER_                    | guest                         | RabbitMQ user                                                                             |
| _RABBITMQ_PW_                      | guest                         | RabbitMQ password                                                                         |
| _RABBITMQ_QUEUE_                   | sms77notifier-${quarkus.uuid} | RabbitMQ queue name for consumer (Default ends with random UUID)                          |
| _RABBITMQ_ALERTS_EXCHANGE_         | NewAlerts                     | RabbitMQ exchange, that publish new alerts                                                |
| _SMS77NOTIFIER_API_KEY_            | -                             | The generated api key from sms77.io                                                       |
| _SMS77NOTIFIER_SENDER_             | Feuerwehr                     | The sender of the sms message (see on your phone)                                         |
| _SMS77NOTIFIER_SMSGROUP_FULL_INFO_ | -                             | sms77 contact group, that should get full information about alerts                        |
| _SMS77NOTIFIER_SMSGROUP_MIN_INFO_  | -                             | sms77 contact group, that should only get notification without any additional information |

## Message types
### MIN_INFO (example)
```text
ALARM von LFK!
```
### FULL_INFO (example)
```text
ALARM von LFK!

Nr.: BWSt40002 
Art: TE TIERRETTUNG
Anrufer: Hasso / 0123456789
Ort: 0000 Musterhausen, Musterweg 1
Info: Tiger im Tank
```