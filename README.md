## Installation

* Using git
    ```bash
    git clone git@github.com:Shedin04/interview-test-app.git
    ```
* Downloading archive
  https://github.com/Shedin04/interview-test-app/archive/refs/heads/main.zip

## Instruments and technologies

* JAVA 11 - programming language
* Maven - for project building and running test
* TestNG - testing framework
* REST Assured - for working with REST APIs
* Allure Report - for generating test reports
* Lombok - for reducing boilerplate code
* SnakeYAML - for parsing and generating YAML files
* SLF4J - logging
* FasterXML Jackson - JSON serializer/deserializer

## Running tests

* ### Locally
    1) Create ```resources/local.properties``` file and specify mandatory properties:
        ```properties
        properties.group={properties group from configuration.yaml, remote-swagger by default}
       ```
       [click here to read more about setting properties](#configuration-ways)

    2) Run ```mvn clean test -DthreadCount=5"``` from the terminal
       OR execute the single [test](src/test/java/com/interview) using IDE.

        Optional parameters description:
    - -DthreadCount=N - count of parallel threads, 3 by default

## Configuration ways

You can provide properties for tests in several ways. They are listed below in order of priority:

1) ```resources/local.properties``` - your local properties
2) System Properties - are set on the Java command line, e.g.: ```mvn test clean -Dproperties.group=remote-swagger```
3) Environment variables - are set in the OS, e.g.: in Linux - ```export HOME=/Users/Username``` or on
   Windows - ```SET WINDIR=C:\Windows```
4) Yaml properties - are obtained from the group in [configuration.yaml](src/main/resources/configuration.yaml)

## Reports generating

Follow the steps to generate the test report:

1) [Execute tests](#running-tests)
2) Run ```mvn allure:report```
3) Observe the generated report by opening [index.html](target/site/allure-maven-plugin/index.html)