[![Build Status](https://travis-ci.org/dnltsk/json-to-statsd-forwarder.svg?branch=master)](https://travis-ci.org/dnltsk/json-to-statsd-forwarder)
[![codebeat badge](https://codebeat.co/badges/85068f97-e56f-449b-b37a-c729859cfb14)](https://codebeat.co/projects/github-com-dnltsk-json-to-statsd-forwarder-master)
[![codecov](https://codecov.io/gh/dnltsk/json-to-statsd-forwarder/branch/master/graph/badge.svg)](https://codecov.io/gh/dnltsk/json-to-statsd-forwarder)

# json-to-statsd-forwarder
harvest json, flatten by keys, push to statsd

## test

`gradle test`

## build

`gradle build`

## start

`nohup java -Xms512m -Xmx512m -Dspring.profiles.active=prod -jar json-to-statsd-forwarder-0.0.1-SNAPSHOT.jar &`
