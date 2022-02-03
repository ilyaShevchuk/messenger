# Домашнее задание к 7 лекции

## TL;DR
Написать ansible playbook, который:
* Запустит контейнер с базой данных
* Запустит контейнер с приложением

### Выполнение
1. Сделайте ветку `ansible` в репозитории с вашим проектом
2. В ветке `ansible` в корне нужно создать 2 файла
* `.gitlab-ci.yml` следующего содержания:
```yml
include:
  - project: 'school/2021-09/backend/python/homeworks/7' # или 'school/2021-09/backend/java/homeworks/7' 
    ref: master
    file: '.gitlab-ci.yml'
    
variables:
  APP_PORT: 8080 # порт, который слушает приложение при запуске
  LANG: python # или java
```
* `playbook.yml`, содержащий playbook для запуска контейнеров
3. Создайте MR ветки `ansible` в `master`
4. Проверка MR должна быть зеленой

## Нюансы
* В корне должен лежать `Dockerfile`, сборка контейнера будет выполняться командой `docker build -t $CI_REGISTRY_IMAGE .`
* Для указания образа внутри `playbook.yml` используйте `{{ lookup('env', 'CI_REGISTRY_IMAGE') }}`, аналогично для проброса портов можно использовать переменную окружения APP_PORT

## Как разрабатывать локально
1. Устанавливаем зависимости: `pip install ansible==4.7.0 docker==5.0.2 docker-compose==1.29.2`
2. Пишем `playbook.yml`
3. Запускаем `ansible-playbook`. Т.к. у нас нет похода по ssh, применять мы будем локально: `ansible-playbook -c local -i localhost playbook.yml`

## Варианты реализации
1. Описать явно в `playbook`'e `docker_network`, 2 `docker_container`, пробросить порты, задать обоим контейнерам общую сеть
2. Написать `docker-compose.yml` и применить его через `docker_compose` в `playbook`'e

## Подсказки
1. https://github.com/grigory51/shbr-devops/tree/master/presentation/03-run-ansible
2. https://docs.ansible.com/ansible/latest/collections/community/docker/docker_container_module.html
3. https://docs.ansible.com/ansible/latest/collections/community/docker/docker_network_module.html
4. https://docs.ansible.com/ansible/latest/collections/community/docker/docker_compose_module.html
5. https://docs.ansible.com/ansible/latest/collections/ansible/builtin/wait_for_module.html
