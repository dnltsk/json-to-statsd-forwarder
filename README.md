# json-to-statsd-forwarder
harvest json, flatten by keys, push to statsd

## test

`gradle test`

## build

`gradle build`

## start

`nohup java -Dspring.profiles.active=prod -jar json-to-statsd-forwarder-0.0.1-SNAPSHOT.jar &`
